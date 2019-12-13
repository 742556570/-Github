package com.jeeplus.modules.app.web;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.app.api.account.loanorderquery.LoanOrderQuery;
import com.jeeplus.modules.app.api.account.loanorderquery.response.LoanOrderDetail;
import com.jeeplus.modules.app.api.account.loanorderquery.response.OUT;
import com.jeeplus.modules.app.api.account.loanrepaymentplanbankquery.LoanRepaymentPlanBankQuery;
import com.jeeplus.modules.app.api.account.loanrepaymentplanbankquery.response.LoanRepaymentPlanBankResp;
import com.jeeplus.modules.app.constant.AppCommonConstants;
import com.jeeplus.modules.app.entity.ClIdCardInfo;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.entity.ClUsrApply;
import com.jeeplus.modules.app.entity.ClUsrLoan;
import com.jeeplus.modules.app.entity.ClUsrLoanDetail;
import com.jeeplus.modules.app.pojo.ResponeJsonObj;
import com.jeeplus.modules.app.service.ClIdCardInfoService;
import com.jeeplus.modules.app.service.ClUsrApplyService;
import com.jeeplus.modules.app.service.LoanOrderQueryService;
import com.jeeplus.modules.app.service.WithRecoverQueryService;
import com.jeeplus.modules.app.task.UnsyncUpdateUsrPaymentPlan;
import com.jeeplus.modules.app.utils.CacheHelper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * app提供查询订单账务系统接口
 * 
 * @author 阳光保险
 *
 */
@Controller
@RequestMapping(value = "api/collection")
public class LoanOrderQueryController {

	private final static Logger logger = LoggerFactory.getLogger(LoanOrderQueryController.class);
	@Autowired
	private LoanOrderQueryService loanOrderQueryService;
	@Autowired
	private ClIdCardInfoService idCardInfoService;
	@Autowired
	private ClUsrApplyService usrApplyService;

	@Autowired
	private WithRecoverQueryService withRecoverQueryService;

	/**
	 * 获取订单信息
	 * 
	 * @param request
	 * @param response
	 * @param USR_CDE
	 * @return
	 * @throws ParseException
	 */
	@ResponseBody
	@RequestMapping(value = "getLoanOrderQuery")
	@Transactional(readOnly = false)
	public String getLoanOrderQuery(HttpServletRequest request, HttpServletResponse response) throws ParseException {

		ClUsr clUsr = (ClUsr) request.getAttribute("usr");

		//String USR_CDE = clUsr.getUSR_CDE();
		String USR_CDE = "247049565293772800";

		UnsyncUpdateUsrPaymentPlan update = new UnsyncUpdateUsrPaymentPlan(USR_CDE);
		update.start();

		List<ClUsrApply> applies = usrApplyService.getPassedByUsrCde(USR_CDE);
		List<ClUsrApply> applieswf = new ArrayList<ClUsrApply>();
		Set<String> passedApplyPolicyNo = new HashSet<String>();
		boolean payFotic = false;
		boolean payWFCCB = false;
		for (ClUsrApply clUsrApply : applies) {
			if (("FOTIC").equals(clUsrApply.getPayChannel())) {
				payFotic = true;
			}
			if (("WFCCB").equals(clUsrApply.getPayChannel())) {
				payWFCCB = true;
				applieswf.add(clUsrApply);
			}
			passedApplyPolicyNo.add(clUsrApply.getPolicyNo());
		}
		List<LoanOrderDetail> LoanOrderDetailList = new ArrayList<LoanOrderDetail>();

		if (payFotic) {
			ClIdCardInfo clIdCardInfo = idCardInfoService.getByUsrCode(USR_CDE);
			LoanOrderQuery loanOrderQuery = new LoanOrderQuery();
			OUT OUT = loanOrderQuery.loanOrderQuery(clIdCardInfo.getID_NO());
			logger.info("外贸信托贷款还款计划查询接口返回结果" + OUT.toString());

			if (OUT.getBODY().getRECORD().getHANDLECODE().equals("00006")) {
				JSONArray json = JSONArray.fromObject(OUT.getBODY().getRECORD().getCONTENT()); // 首先把字符串转成
																								// JSONArray
																								// 对象
				if (json.size() > 0) {
					for (int i = 0; i < json.size(); i++) {
						JSONObject job = json.getJSONObject(i); // 遍历 jsonarray
																// 数组，把每一个对象转成
																// json
																// 对象
						LoanOrderDetail detail = (LoanOrderDetail) JSONObject.toBean(job, LoanOrderDetail.class);
						System.out.println(detail.toString());
						// 如果追偿的表中追偿状态为Y
						boolean setLind = withRecoverQueryService
								.selectByPoliyNoToSetLind(String.valueOf(job.get("guarantyid")));
						if (setLind) {
							detail.setAccflag("2");
							updateApplyStatusByPolicyNo(String.valueOf(job.get("guarantyid")));
						}
						ClUsrLoan clUsrLoan = setLoanOrderData(detail, USR_CDE);
						System.out.println(detail.toString());
						ClUsrLoan inDbUsrLoan = loanOrderQueryService
								.getselectByPrimaryKey(String.valueOf(job.get("guarantyid")));
						if (inDbUsrLoan == null) {
							boolean resultOrder = loanOrderQueryService.insertLoanOrder(clUsrLoan);
							if (resultOrder) {
								logger.info("外贸信托贷款订单列表插入数据库返回结果:" + resultOrder);
							}
						} else {
							loanOrderQueryService.updateByObj(clUsrLoan);
							String deptS = applyStsMappingZWSts(clUsrLoan.getAccFlag());
							String policyNo = clUsrLoan.getPolicyNo();
							String time = DateUtils.getNowFullFmt();
							if (!setLind) {
								usrApplyService.updateDebtByPolicyNo(deptS, time, policyNo);
							}
						}

						if (passedApplyPolicyNo.contains(detail.getGuarantyid())) {
							LoanOrderDetailList.add(detail);
						}
					}
				}
			}
		}

		if (payWFCCB) {
			for (ClUsrApply clUsrApplyWf : applieswf) {
				passedApplyPolicyNo.add(clUsrApplyWf.getPolicyNo());
				ClUsrLoan inDbUsrLoanisNull = loanOrderQueryService
						.getselectByPrimaryKey(String.valueOf(clUsrApplyWf.getPolicyNo()));
				if (inDbUsrLoanisNull == null) {
					LoanRepaymentPlanBankQuery loanOrderQueryBank = new LoanRepaymentPlanBankQuery();
					LoanRepaymentPlanBankResp planResp = loanOrderQueryBank.loanRepaymentPlanBankQuery(
							clUsrApplyWf.getPolicyNo().toString(), clUsrApplyWf.getPayChannel());
					logger.info("保单号" + clUsrApplyWf.getPolicyNo().toString() + "银行贷款订单接口返回结果" + planResp.toString());

					if (planResp.getDataBody().getDataRecord().getHandleCode().equals("00006")) {

						// 如果追偿的表中追偿状态为Y
						boolean setLind = withRecoverQueryService.selectByPoliyNoToSetLind(
								String.valueOf(planResp.getDataBody().getDataRecord().getGuarantyid()));
						if (setLind) {
							planResp.getDataBody().getDataRecord().setAccountStatus("2");
							updateApplyStatusByPolicyNo(
									String.valueOf(planResp.getDataBody().getDataRecord().getGuarantyid()));
						}
						ClUsrLoan clUsrLoan = setLoanOrderBankData(planResp, USR_CDE);
						ClUsrLoan inDbUsrLoan = loanOrderQueryService.getselectByPrimaryKey(
								String.valueOf(planResp.getDataBody().getDataRecord().getGuarantyid()));
						if (inDbUsrLoan == null) {
							boolean resultOrder = loanOrderQueryService.insertLoanOrder(clUsrLoan);
							if (resultOrder) {
								logger.info("银行贷款订单列表插入数据库返回结果:" + resultOrder);
							}
						} else {
							loanOrderQueryService.updateByObj(clUsrLoan);
							String deptS = applyStsMappingZWSts(clUsrLoan.getAccFlag());
							String policyNo = clUsrLoan.getPolicyNo();
							String time = DateUtils.getNowFullFmt();
							if (!setLind) {
								usrApplyService.updateDebtByPolicyNo(deptS, time, policyNo);
							}
						}

					}
				}
				
				ClUsrLoan inDbFromUsrLoan = loanOrderQueryService
						.getselectByPrimaryKey(String.valueOf(clUsrApplyWf.getPolicyNo()));
				// 返回app需要参数 从数据库中取参数
				if (passedApplyPolicyNo.contains(clUsrApplyWf.getPolicyNo())) {
					LoanOrderDetail detail = setDetail(inDbFromUsrLoan, clUsrApplyWf.getPayChannel());
					LoanOrderDetailList.add(detail);
				}

			}
		}

		List<LoanOrderDetail> unPassed = getUnPassedLoanOrders(USR_CDE);
		Set<String> keys = new HashSet<String>();
		for (LoanOrderDetail pass : LoanOrderDetailList) {
			keys.add(pass.getGuarantyid());
		}

		for (LoanOrderDetail unPass : unPassed) {
			if (keys.contains(unPass.getGuarantyid())) {
				continue;
			} else {
				LoanOrderDetailList.add(unPass);
			}
		}

		if (LoanOrderDetailList.size() > 0) {
			List<LoanOrderDetail> result = classifyList(LoanOrderDetailList);
			return ResponeJsonObj.getResJsonObjFromArray(ResponeJsonObj.SUCCESS, "成功", result);
		} else {
			return ResponeJsonObj.getResJsonObjFromArray(ResponeJsonObj.FAIL, "未查询到申请成功的订单", null);
		}
	}

	/**
	 * 解析参数银行参数 设值
	 * 
	 * @param job
	 * @return
	 * @throws ParseException
	 */
	private ClUsrLoan setLoanOrderBankData(LoanRepaymentPlanBankResp planResp, String USR_CDE) throws ParseException {
		int applyID = loanOrderQueryService.getApplyId();
		ClUsrLoan clUsrLoan = new ClUsrLoan();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date enddate = null;
		Date sdate = null;
		if (!StringUtils.isEmpty(planResp.getDataBody().getDataRecord().getStartDate())) {
			sdate = sdf.parse(planResp.getDataBody().getDataRecord().getStartDate());
		}

		if (!StringUtils.isEmpty(planResp.getDataBody().getDataRecord().getExpiryDate())) {
			enddate = sdf.parse(planResp.getDataBody().getDataRecord().getExpiryDate());
		}
		Date begindate = null;
		if (!StringUtils.isEmpty(planResp.getDataBody().getDataRecord().getStartDate())) {
			begindate = sdf.parse(planResp.getDataBody().getDataRecord().getStartDate());
		}
		clUsrLoan.setLoanSeq(applyID);
		clUsrLoan.setUsrCde(USR_CDE);
		clUsrLoan.setApplCde("");
		clUsrLoan.setLoanAcno(planResp.getDataBody().getDataRecord().getLoanAcNo());
		clUsrLoan.setEndDt(enddate);
		clUsrLoan.setsDate(sdate);
		clUsrLoan.setMdfDt(new Date());
		clUsrLoan.setPolicyNo(planResp.getDataBody().getDataRecord().getGuarantyid());
		clUsrLoan.setAccFlag(planResp.getDataBody().getDataRecord().getAccountStatus());
		clUsrLoan.setBal(planResp.getDataBody().getDataRecord().getRemainCapi());
		clUsrLoan.setBeginDt(begindate);
		clUsrLoan.setLoanAmt(new BigDecimal(planResp.getDataBody().getDataRecord().getLoanAmt()));
		clUsrLoan.setLoanTerm(planResp.getDataBody().getDataRecord().getLoanTerm());
		clUsrLoan.setRetuKind(planResp.getDataBody().getDataRecord().getRePayMent());
		// clUsrLoan.setsTerm(detail.getSterm());
		logger.info("银行借款订单列表插入数据库数据" + clUsrLoan.toString());
		return clUsrLoan;
	}

	/**
	 * 返回app需要参数
	 * 
	 * @param planResp
	 * @return
	 */
	private LoanOrderDetail setDetail(ClUsrLoan inDbUsrLoan, String payChannel) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		LoanOrderDetail detail = new LoanOrderDetail();
		detail.setLoanacno(inDbUsrLoan.getLoanAcno());
		detail.setGuarantyid(inDbUsrLoan.getPolicyNo());
		detail.setLoanamt(new BigDecimal(inDbUsrLoan.getLoanAmt().toString()));
		detail.setLoanterm(inDbUsrLoan.getLoanTerm());
		detail.setRetukind(inDbUsrLoan.getRetuKind());
		detail.setBegindate(sdf.format(inDbUsrLoan.getBeginDt()));
		detail.setEnddate(sdf.format(inDbUsrLoan.getEndDt()));
		detail.setSdate("");
		detail.setSterm("");
		detail.setBal(new BigDecimal(inDbUsrLoan.getBal()));
		detail.setAccflag(inDbUsrLoan.getAccFlag());
		detail.setPayChannel(payChannel);
		logger.info("app封装参数" + detail.toString());
		return detail;
	}

	/**
	 * 外贸信托解析参数 设值
	 * 
	 * @param job
	 * @return
	 * @throws ParseException
	 */
	private ClUsrLoan setLoanOrderData(LoanOrderDetail detail, String USR_CDE) throws ParseException {
		int applyID = loanOrderQueryService.getApplyId();
		ClUsrLoan clUsrLoan = new ClUsrLoan();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date enddate = null;
		Date sdate = null;
		if (!StringUtils.isEmpty(detail.getSdate())) {
			sdate = sdf.parse(detail.getSdate());
		}

		if (!StringUtils.isEmpty(detail.getEnddate())) {
			enddate = sdf.parse(detail.getEnddate());
		}
		Date begindate = null;
		if (!StringUtils.isEmpty(detail.getBegindate())) {
			begindate = sdf.parse(detail.getBegindate());
		}
		clUsrLoan.setLoanSeq(applyID);
		clUsrLoan.setUsrCde(USR_CDE);
		clUsrLoan.setApplCde("");
		clUsrLoan.setLoanAcno("");
		clUsrLoan.setEndDt(enddate);
		clUsrLoan.setsDate(sdate);
		clUsrLoan.setMdfDt(new Date());
		clUsrLoan.setPolicyNo(detail.getGuarantyid());
		clUsrLoan.setAccFlag(detail.getAccflag());
		clUsrLoan.setBal(detail.getBal().toString());
		clUsrLoan.setBeginDt(begindate);
		clUsrLoan.setLoanAmt(detail.getLoanamt());
		clUsrLoan.setLoanTerm(detail.getLoanterm());
		clUsrLoan.setRetuKind(detail.getRetukind());
		clUsrLoan.setsTerm(detail.getSterm());
		logger.info("外贸信托借款订单插入数据库数据" + clUsrLoan.toString());
		return clUsrLoan;
	}

	/**
	 * 根据保单号修改申请表中装态
	 */
	private boolean updateApplyStatusByPolicyNo(String policyNo) {

		return usrApplyService.updateStatusByPolicyNo(policyNo);
	}

	private List<LoanOrderDetail> getUnPassedLoanOrders(String usrCde) {
		List<ClUsrApply> clApplies = usrApplyService.unPassedAppliesByUsr(usrCde);
		List<LoanOrderDetail> targetList = new ArrayList<LoanOrderDetail>();
		for (ClUsrApply unPassedApply : clApplies) {
			String flag = "";
			if ("01".equals(unPassedApply.getStCde())) {
				flag = "5";
			} else if ("10".equals(unPassedApply.getStCde())) {
				flag = "6";
			} else if ("12".equals(unPassedApply.getStCde())) {
				flag = "7";
			} else if ("13".equals(unPassedApply.getStCde())) {
				flag = "8";
			} else {
				flag = "6";
			}
			LoanOrderDetail ld = new LoanOrderDetail();
			ld.setLoanacno(unPassedApply.getLoanAcno());
			ld.setGuarantyid(unPassedApply.getPolicyNo());
			ld.setLoanamt(new BigDecimal(unPassedApply.getApplyAmt()));
			ld.setLoanterm(unPassedApply.getApplyTnr());
			Date d = DateUtils.parseDate(unPassedApply.getCrtDt());
			ld.setBegindate(DateUtils.formatDate(d, "yyyyMMdd"));
			ld.setAccflag(flag);
			ld.setRetukind("");
			if (unPassedApply.getStCde().equals("01")) {
				ld.setAccflag("5");
			} else if (unPassedApply.getStCde().equals("10")) {
				ld.setAccflag("6");
			}
			targetList.add(ld);
		}

		return targetList;
	}

	private String applyStsMappingZWSts(String zWSts) {
		if ("0".equals(zWSts)) {
			return "30";
		} else if ("1".equals(zWSts)) {
			return "31";
		} else if ("2".equals(zWSts)) {
			return "40";
		} else if ("3".equals(zWSts)) {
			return "42";
		} else if ("4".equals(zWSts)) {
			return "41";
		}
		return "";
	}

	private List<LoanOrderDetail> classifyList(List<LoanOrderDetail> list) {
		/*
		 * 0-正常（还款中） 1-逾期（还款中） 2-正常结清（客户） 3-理赔中 4-理赔结清（阳光） 5 审核中 6 审核未通过 7 放款中 8
		 * 放款失败
		 */
		List<LoanOrderDetail> result = new ArrayList<LoanOrderDetail>();

		List<LoanOrderDetail> shenhe = new ArrayList<LoanOrderDetail>();

		List<LoanOrderDetail> shibai = new ArrayList<LoanOrderDetail>();

		List<LoanOrderDetail> lipei = new ArrayList<LoanOrderDetail>();

		List<LoanOrderDetail> jieqing = new ArrayList<LoanOrderDetail>();

		List<LoanOrderDetail> huankuan = new ArrayList<LoanOrderDetail>();

		for (LoanOrderDetail loanOrderDetail : list) {
			String status = loanOrderDetail.getAccflag();
			if ("0".equals(status) || "1".equals(status)) {
				huankuan.add(loanOrderDetail);
			} else if ("2".equals(status) || "4".equals(status)) {
				jieqing.add(loanOrderDetail);
			} else if ("3".equals(status)) {
				lipei.add(loanOrderDetail);
			} else if ("5".equals(status) || "7".equals(status)) {
				shenhe.add(loanOrderDetail);
			} else if ("6".equals(status)) {
				shibai.add(loanOrderDetail);
			} else if ("8".equals(status)) {
				shibai.add(loanOrderDetail);
			}
		}
		// 使用中
		List<LoanOrderDetail> syz = new ArrayList<LoanOrderDetail>();
		syz.addAll(shenhe);
		syz.addAll(huankuan);
		syz.addAll(lipei);
		// 未使用
		List<LoanOrderDetail> wsy = new ArrayList<LoanOrderDetail>();
		wsy.addAll(shibai);
		wsy.addAll(jieqing);

		List<LoanOrderDetail> syzSorted = sortByDt(syz);
		List<LoanOrderDetail> wsySorted = sortByDt(wsy);
		result.addAll(syzSorted);
		result.addAll(wsySorted);

		return result;

	}

	public List<LoanOrderDetail> sortByDt(List<LoanOrderDetail> list) {
		Collections.sort(list, new Comparator<LoanOrderDetail>() {

			@Override
			public int compare(LoanOrderDetail o1, LoanOrderDetail o2) {
				String dt1Str = o1.getBegindate();
				String dt2Str = o2.getBegindate();

				Date d1 = DateUtils.parseDate(dt1Str);
				Date d2 = DateUtils.parseDate(dt2Str);

				long i = (d2.getTime() - d1.getTime());
				if (i >= 0) {
					return 1;
				} else {
					return -1;
				}

			}
		});

		return list;
	}

}
