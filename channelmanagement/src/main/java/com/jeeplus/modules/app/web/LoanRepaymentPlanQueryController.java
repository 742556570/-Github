package com.jeeplus.modules.app.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.app.api.account.loanrepaymentplanbankquery.LoanRepaymentPlanBankQuery;
import com.jeeplus.modules.app.api.account.loanrepaymentplanbankquery.response.LoanRepaymentPlanBankDetail;
import com.jeeplus.modules.app.api.account.loanrepaymentplanbankquery.response.LoanRepaymentPlanBankResp;
import com.jeeplus.modules.app.api.account.loanrepaymentplanquery.LoanRepaymentPlanQuery;
import com.jeeplus.modules.app.api.account.loanrepaymentplanquery.response.LoanRepaymentPlanDetail;
import com.jeeplus.modules.app.api.account.loanrepaymentplanquery.response.LoanRepaymentPlanResp;
import com.jeeplus.modules.app.constant.AppCommonConstants;
import com.jeeplus.modules.app.entity.ClIdCardInfo;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.entity.ClUsrLoanDetail;
import com.jeeplus.modules.app.pojo.ResponeJsonObj;
import com.jeeplus.modules.app.service.ClFadadaParamsService;
import com.jeeplus.modules.app.service.ClIdCardInfoService;
import com.jeeplus.modules.app.service.ClUsrContractService;
import com.jeeplus.modules.app.service.LoanRepaymentPlanQueryService;
import com.jeeplus.modules.app.service.WithRecoverQueryService;
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
public class LoanRepaymentPlanQueryController {

	private final static Logger logger = LoggerFactory.getLogger(LoanRepaymentPlanQueryController.class);
	@Autowired
	private LoanRepaymentPlanQueryService loanRepaymentPlanQueryService;
	@Autowired
	private WithRecoverQueryService withRecoverQueryService;

	@Autowired
	private ClFadadaParamsService fpService;

	@Autowired
	private ClUsrContractService ucService;

	@Autowired
	private ClIdCardInfoService idcardInfoSerive;

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
	@RequestMapping(value = "getLoanRepaymentPlanQuery")
	@Transactional(readOnly = false)
	public String getLoanRepaymentPlanQuery(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "POLICY_NO") String POLICY_NO, @RequestParam(value = "USR_CDE") String USR_CDE,
			@RequestParam(value = "PAYCHANNEL") String PAYCHANNEL) throws ParseException {
		ClUsr clUsr = (ClUsr) request.getAttribute("usr");
	
		boolean resultOrder;

		if (("FOTIC").equals(PAYCHANNEL)) {
			List<LoanRepaymentPlanDetail> loanRepaymentPlanDetailList = new ArrayList<LoanRepaymentPlanDetail>();
			LoanRepaymentPlanQuery loanRepaymentPlanQuery = new LoanRepaymentPlanQuery();
			LoanRepaymentPlanResp planResp = loanRepaymentPlanQuery.loanRepaymentPlanQuery(POLICY_NO);

			logger.info("外贸信托贷款还款计划详情查询接口返回结果" + planResp.toString());
			if (planResp.getDataBody().getDataRecord().getHandleCode().equals("00006")) {
				loanRepaymentPlanQueryService.deleteLoanOrderPolicy(POLICY_NO);
				// 解析JSON串数组
				JSONArray jsonArray = JSONArray
						.fromObject(planResp.getDataBody().getDataRecord().getRepaymentDetails());
				CacheHelper.removeObject(AppCommonConstants.getValStr("CACHESCHEMA"), "LoanRepaymentPlan" + POLICY_NO);
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					// 解析JSON映射成bean对象
					LoanRepaymentPlanDetail detail = (LoanRepaymentPlanDetail) JSONObject.toBean(jsonObject,
							LoanRepaymentPlanDetail.class);
					// 如果追偿的表中追偿状态为Y
					boolean setLind = withRecoverQueryService
							.selectByPoliyNoToSetLind(String.valueOf(jsonObject.get("guarantyid")));
					if (setLind) {
						detail.setPayoffflag("1");
					}
					ClUsrLoanDetail clUsrLoanDetail = setLoanRepaymentPlanDetail(detail, USR_CDE);
					resultOrder = loanRepaymentPlanQueryService.insertLoanOrderDetail(clUsrLoanDetail);
					if (resultOrder) {
						logger.info("外贸信托贷款还款计划插入数据库成功" + resultOrder);

					}
					loanRepaymentPlanDetailList.add(detail);
					CacheHelper.setListObject(AppCommonConstants.getValStr("CACHESCHEMA"),
							"LoanRepaymentPlan" + POLICY_NO, detail, AppCommonConstants.getValInt("CACHEVALIAPPLYTTL"));
				}

				boolean isExist = ucService.isExistByPolicyNo(POLICY_NO, "YGSDPH80000011-1");
				if (!isExist) {
					ClIdCardInfo idcard = idcardInfoSerive.getByUsrCode(USR_CDE);
					String date = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
					net.sf.json.JSONObject paramsHt = net.sf.json.JSONObject
							.fromObject(fpService.getParams(USR_CDE, "YGSDPH80000011-1", POLICY_NO, date));
					ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), clUsr.getUSR_TEL(), paramsHt,
							"YGSDPH80000011-1", "还款事项提醒函", "1", "签章处", POLICY_NO, USR_CDE, "4-4", "", "CI06");
				}
				return ResponeJsonObj.getResJsonObjFromArray(ResponeJsonObj.SUCCESS, "成功", loanRepaymentPlanDetailList);
			}
		}
		if (("WFCCB").equals(PAYCHANNEL)) {
			List<LoanRepaymentPlanBankDetail> loanRepaymentPlanBankDetail = new ArrayList<LoanRepaymentPlanBankDetail>();
			LoanRepaymentPlanBankQuery loanOrderQueryBank = new LoanRepaymentPlanBankQuery();
			LoanRepaymentPlanBankResp planResp = loanOrderQueryBank.loanRepaymentPlanBankQuery(POLICY_NO, PAYCHANNEL);
			//缓存为空 查询数据库 并将数据放入缓存		
			boolean result= loanRepaymentPlanQueryService.getLoanOrderPolicy(POLICY_NO);
			logger.info("保单号" + POLICY_NO + "银行贷款还款计划详情查询接口返回结果" + planResp.toString());
			if (!result) {
				if (planResp.getDataBody().getDataRecord().getHandleCode().equals("00006")) {
					loanRepaymentPlanQueryService.deleteLoanOrderPolicy(POLICY_NO);
					// 解析JSON串数组
					JSONArray jsonArray = JSONArray
							.fromObject(planResp.getDataBody().getDataRecord().getRepaymentBankDetails());
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						// 解析JSON映射成bean对象
						LoanRepaymentPlanBankDetail detail = (LoanRepaymentPlanBankDetail) JSONObject.toBean(jsonObject,
								LoanRepaymentPlanBankDetail.class);
						// 如果追偿的表中追偿状态为Y
						boolean setLind = withRecoverQueryService
								.selectByPoliyNoToSetLind(String.valueOf(jsonObject.get("guarantyid")));
						if (setLind) {
							detail.setPayoffflag("1");
						}
						ClUsrLoanDetail clUsrLoanDetail = setLoanRepaymentBankPlanDetail(detail, USR_CDE);
						resultOrder = loanRepaymentPlanQueryService.insertLoanOrderDetail(clUsrLoanDetail);
						if (resultOrder) {
							logger.info("银行贷款还款计划插入数据库成功" + resultOrder);

						}

					}

				}
			}
	
			//  从DB中取值
			List<ClUsrLoanDetail> clUsrLoanDetailFromList = loanRepaymentPlanQueryService.getByPolicy(POLICY_NO);

			for(ClUsrLoanDetail clUsrLoanDetailFromWF:clUsrLoanDetailFromList){
				LoanRepaymentPlanBankDetail apploanRepaymentPlanBankDetail = setAppLoanRepaymentBankPlanDetail(clUsrLoanDetailFromWF);
				loanRepaymentPlanBankDetail.add(apploanRepaymentPlanBankDetail);
			}
			
			return ResponeJsonObj.getResJsonObjFromArray(ResponeJsonObj.SUCCESS, "成功", loanRepaymentPlanBankDetail);
		}

		return ResponeJsonObj.getResJsonObjFromArray(ResponeJsonObj.FAIL, "失败", null);
	}
	/**
	 * 封装app返回参数
	 * @param clUsrLoanDetailFromWF
	 * @param USR_CDE
	 * @return
	 * @throws ParseException
	 */
	private LoanRepaymentPlanBankDetail setAppLoanRepaymentBankPlanDetail(ClUsrLoanDetail clUsrLoanDetailFromWF)
			throws ParseException {
		LoanRepaymentPlanBankDetail appLoanRepaymentPlanBankDetail = new LoanRepaymentPlanBankDetail();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String payoffdate ="";
		String payoffflag = "";
		if (clUsrLoanDetailFromWF.getPayOffDate()!=null) {
			payoffdate = sdf.format(clUsrLoanDetailFromWF.getPayOffDate());
		}
		if (!StringUtils.isEmpty(clUsrLoanDetailFromWF.getPayOffFlag())) {
			payoffflag = clUsrLoanDetailFromWF.getPayOffFlag();
		}
		appLoanRepaymentPlanBankDetail.setGuarantyid(clUsrLoanDetailFromWF.getPolicyNo());
		appLoanRepaymentPlanBankDetail.setLoanacno(clUsrLoanDetailFromWF.getLoanAcno());
		appLoanRepaymentPlanBankDetail.setSterm(clUsrLoanDetailFromWF.getsTerm());
		appLoanRepaymentPlanBankDetail.setSdate(sdf.format(clUsrLoanDetailFromWF.getsDate()));
		appLoanRepaymentPlanBankDetail.setScapi(clUsrLoanDetailFromWF.getsCapi());
		appLoanRepaymentPlanBankDetail.setSinte(clUsrLoanDetailFromWF.getsInte());
		appLoanRepaymentPlanBankDetail.setSfine(clUsrLoanDetailFromWF.getsFine());
		appLoanRepaymentPlanBankDetail.setSinsuamt(clUsrLoanDetailFromWF.getsInsuamt());
		appLoanRepaymentPlanBankDetail.setScontamt(clUsrLoanDetailFromWF.getsContamt());
		appLoanRepaymentPlanBankDetail.setRcapi(clUsrLoanDetailFromWF.getrCapi());
		appLoanRepaymentPlanBankDetail.setRinte(clUsrLoanDetailFromWF.getrInte());
		appLoanRepaymentPlanBankDetail.setRfine(clUsrLoanDetailFromWF.getrFine());
		appLoanRepaymentPlanBankDetail.setRinsuamt(clUsrLoanDetailFromWF.getrInsuamt());
		appLoanRepaymentPlanBankDetail.setRcontamt(clUsrLoanDetailFromWF.getrContamt());
		appLoanRepaymentPlanBankDetail.setOverflag(clUsrLoanDetailFromWF.getOverFlag());
		appLoanRepaymentPlanBankDetail.setPayoffdate(payoffdate);
		appLoanRepaymentPlanBankDetail.setPayoffflag(payoffflag);
		appLoanRepaymentPlanBankDetail.setsPayLiqDam(clUsrLoanDetailFromWF.getsPayLiqDam());
		appLoanRepaymentPlanBankDetail.setrPayLiqDam(clUsrLoanDetailFromWF.getrPayLiqDam());
		appLoanRepaymentPlanBankDetail.setsRecPayLiqDam(clUsrLoanDetailFromWF.getsRecPayLiqDam());
		appLoanRepaymentPlanBankDetail.setrRecPayLiqDam(clUsrLoanDetailFromWF.getrRecPayLiqDam());

		return appLoanRepaymentPlanBankDetail;
	}
	/**
	 * 解析参数外贸信托 设值
	 * 
	 * @param job
	 * @return
	 * @throws ParseException
	 */
	private ClUsrLoanDetail setLoanRepaymentPlanDetail(LoanRepaymentPlanDetail detail, String USR_CDE)
			throws ParseException {
		int applyID = loanRepaymentPlanQueryService.getApplyId();
		ClUsrLoanDetail clUsrLoanDetail = new ClUsrLoanDetail();
		Date payoffdate = null;
		Date sdate = null;
		String payoffflag = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		if (!StringUtils.isEmpty(detail.getSdate())) {
			sdate = sdf.parse(detail.getSdate());
		}

		if (!StringUtils.isEmpty(detail.getPayoffdate())) {
			payoffdate = sdf.parse(detail.getPayoffdate());
		}
		if (!StringUtils.isEmpty(detail.getPayoffflag())) {
			payoffflag = detail.getPayoffflag();
		}
		clUsrLoanDetail.setLdSeq(applyID);
		clUsrLoanDetail.setApplCde("");
		clUsrLoanDetail.setUsrCde(USR_CDE);
		clUsrLoanDetail.setPolicyNo(detail.getGuarantyid());
		clUsrLoanDetail.setLoanAcno(detail.getLoanacno());
		clUsrLoanDetail.setsTerm(detail.getSterm());
		clUsrLoanDetail.setsDate(sdate);
		clUsrLoanDetail.setsCapi(detail.getScapi());
		clUsrLoanDetail.setsInte(detail.getSinte());
		clUsrLoanDetail.setsFine(detail.getSfine());
		clUsrLoanDetail.setsInsuamt(detail.getSinsuamt());
		clUsrLoanDetail.setsContamt(detail.getScontamt());
		clUsrLoanDetail.setrCapi(detail.getRcapi());
		clUsrLoanDetail.setrInte(detail.getRinte());
		clUsrLoanDetail.setrFine(detail.getRfine());
		clUsrLoanDetail.setrInsuamt(detail.getRinsuamt());
		clUsrLoanDetail.setrContamt(detail.getRcontamt());
		clUsrLoanDetail.setOverFlag(detail.getOverflag());
		clUsrLoanDetail.setPayOffDate(payoffdate);
		clUsrLoanDetail.setMdfDt(new Date());
		clUsrLoanDetail.setPayOffFlag(payoffflag);
		logger.info("贷款还款计划查询接口插入数据" + clUsrLoanDetail.toString());
		return clUsrLoanDetail;
	}

	/**
	 * 银行解析参数 设值
	 * 
	 * @param job
	 * @return
	 * @throws ParseException
	 */
	private ClUsrLoanDetail setLoanRepaymentBankPlanDetail(LoanRepaymentPlanBankDetail detail, String USR_CDE)
			throws ParseException {
		int applyID = loanRepaymentPlanQueryService.getApplyId();
		ClUsrLoanDetail clUsrLoanDetail = new ClUsrLoanDetail();
		Date payoffdate = null;
		Date sdate = null;
		String payoffflag = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		if (!StringUtils.isEmpty(detail.getSdate())) {
			sdate = sdf.parse(detail.getSdate());
		}

		if (!StringUtils.isEmpty(detail.getPayoffdate())) {
			payoffdate = sdf.parse(detail.getPayoffdate());
		}
		if (!StringUtils.isEmpty(detail.getPayoffflag())) {
			payoffflag = detail.getPayoffflag();
		}
		clUsrLoanDetail.setLdSeq(applyID);
		clUsrLoanDetail.setApplCde("");
		clUsrLoanDetail.setUsrCde(USR_CDE);
		clUsrLoanDetail.setPolicyNo(detail.getGuarantyid());
		clUsrLoanDetail.setLoanAcno(detail.getLoanacno());
		clUsrLoanDetail.setsTerm(detail.getSterm());
		clUsrLoanDetail.setsDate(sdate);
		clUsrLoanDetail.setsCapi(detail.getScapi());
		clUsrLoanDetail.setsInte(detail.getSinte());
		clUsrLoanDetail.setsFine(detail.getSfine());
		clUsrLoanDetail.setsInsuamt(detail.getSinsuamt());
		clUsrLoanDetail.setsContamt(detail.getScontamt());
		clUsrLoanDetail.setrCapi(detail.getRcapi());
		clUsrLoanDetail.setrInte(detail.getRinte());
		clUsrLoanDetail.setrFine(detail.getRfine());
		clUsrLoanDetail.setrInsuamt(detail.getRinsuamt());
		clUsrLoanDetail.setrContamt(detail.getRcontamt());
		clUsrLoanDetail.setOverFlag(detail.getOverflag());
		clUsrLoanDetail.setPayOffDate(payoffdate);
		clUsrLoanDetail.setMdfDt(new Date());
		clUsrLoanDetail.setPayOffFlag(payoffflag);
		clUsrLoanDetail.setsPayLiqDam(detail.getsPayLiqDam());
		clUsrLoanDetail.setrPayLiqDam(detail.getrPayLiqDam());
		clUsrLoanDetail.setsRecPayLiqDam(detail.getsRecPayLiqDam());
		clUsrLoanDetail.setrRecPayLiqDam(detail.getrRecPayLiqDam());
		logger.info("贷款还款计划查询接口插入数据" + clUsrLoanDetail.toString());
		return clUsrLoanDetail;
	}
}
