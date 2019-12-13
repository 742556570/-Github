package com.jeeplus.modules.app.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.app.api.account.CreditLoanApplyQuery.CreditLoanApplyQueryController;
import com.jeeplus.modules.app.api.account.CreditLoanApplyQuery.response.CreditLoanApplyQueryResp;
import com.jeeplus.modules.app.api.account.loanorderquery.LoanOrderQuery;
import com.jeeplus.modules.app.api.account.loanorderquery.response.LoanOrderDetail;
import com.jeeplus.modules.app.api.account.loanorderquery.response.OUT;
import com.jeeplus.modules.app.api.account.loanrepaymentplanbankquery.LoanRepaymentPlanBankQuery;
import com.jeeplus.modules.app.api.account.loanrepaymentplanbankquery.response.LoanRepaymentPlanBankResp;
import com.jeeplus.modules.app.dao.ClIdCardInfoDao;
import com.jeeplus.modules.app.dao.ClUsrApplyDao;
import com.jeeplus.modules.app.dao.ClUsrDao;
import com.jeeplus.modules.app.entity.ClIdCardInfo;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.entity.ClUsrApply;
import com.jeeplus.modules.app.entity.ClUsrLoan;
import com.jeeplus.modules.app.task.UnsyncUpdateUsrPaymentPlan;
import com.jeeplus.modules.app.web.WithholdRecoverController;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class ClCheckLoanStatusService {

	private final static Logger logger = LoggerFactory.getLogger(ClCheckLoanStatusService.class);

	@Autowired
	private ClIdCardInfoDao idCardDao;

	@Autowired
	private LoanOrderQueryService loanOrderQueryService;

	@Autowired
	private ClUsrLmtamtService usrLmtAmtService;

	@Autowired
	private ClUsrDao usrDao;

	@Autowired
	private ClUpdatePaymentService updatePayment;

	@Autowired
	private WithRecoverQueryService withRecoverQueryService;

	@Autowired
	private ClUsrApplyService usrApplyService;

	// 获取申请过 未放款的
	private List<ClUsrApply> getApdAndNotLoaned() {

		return usrApplyService.getApdAndNotLoaned();
	}

	// 获取放款状态
	private String getStatus(ClUsrApply apdNotLoan) {
		String policyNo = apdNotLoan.getPolicyNo();
		CreditLoanApplyQueryController query = new CreditLoanApplyQueryController();
		CreditLoanApplyQueryResp resp = query.creditLoanApplyQuery(policyNo);
		return resp.getDataBody().getDataRecord().getBusiStatus();

	}

	// 获取放款状态根据保单号
	private String getStatusFromPolicyNo(String policyNo) {
		CreditLoanApplyQueryController query = new CreditLoanApplyQueryController();
		CreditLoanApplyQueryResp resp = query.creditLoanApplyQuery(policyNo);
		return resp.getDataBody().getDataRecord().getBusiStatus();

	}

	private ClUsrLoan setLoanOrderData(LoanOrderDetail detail, String USR_CDE) {
		int applyID = loanOrderQueryService.getApplyId();
		ClUsrLoan clUsrLoan = new ClUsrLoan();
		Date begindate = DateUtils.parseDate(detail.getBegindate());
		// Date enddate = sdf.parse(detail.getEnddate());
		// Date sdate = sdf.parse(detail.getSdate());
		clUsrLoan.setLoanSeq(applyID);
		clUsrLoan.setUsrCde(USR_CDE);
		clUsrLoan.setApplCde("");
		clUsrLoan.setLoanAcno("");
		// clUsrLoan.setEndDt(enddate);
		// clUsrLoan.setsDate(sdate);
		clUsrLoan.setMdfDt(new Date());
		clUsrLoan.setPolicyNo(detail.getGuarantyid());
		clUsrLoan.setAccFlag(detail.getAccflag());
		clUsrLoan.setBal(detail.getBal().toString());
		clUsrLoan.setBeginDt(begindate);
		clUsrLoan.setLoanAmt(detail.getLoanamt());
		clUsrLoan.setLoanTerm(detail.getLoanterm());
		clUsrLoan.setRetuKind(detail.getRetukind());
		clUsrLoan.setsTerm(detail.getSterm());
		clUsrLoan.setLoanAcno(detail.getLoanacno());
		return clUsrLoan;
	}

	private void doBusiness(ClUsrApply apdNotLoan) {
		String USR_CDE = apdNotLoan.getUsrCde();
		ClIdCardInfo idcard = idCardDao.getByUsrCode(USR_CDE);
		String idNo = idcard.getID_NO();
		String targetPolicyNo = apdNotLoan.getPolicyNo();
		ClUsr usr = usrDao.getByUsrCode(USR_CDE);
		String LoanAcNO = "";

		LoanOrderQuery loanOrderQuery = new LoanOrderQuery();
		OUT OUT = loanOrderQuery.loanOrderQuery(idNo);

		if (OUT.getBODY().getRECORD().getHANDLECODE().equals("00006")) {
			JSONArray json = JSONArray.fromObject(OUT.getBODY().getRECORD().getCONTENT()); // 首先把字符串转成
																							// JSONArray
																							// 对象
			if (json.size() > 0) {
				for (int i = 0; i < json.size(); i++) {
					JSONObject job = json.getJSONObject(i); // 遍历 jsonarray
															// 数组，把每一个对象转成 json
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
							logger.info("小额信用贷款查询插入数据库成功" + resultOrder);
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

					if (targetPolicyNo.equals(clUsrLoan.getPolicyNo())) {// true
						LoanAcNO = clUsrLoan.getLoanAcno();
					}
				}
			}

			UnsyncUpdateUsrPaymentPlan update = new UnsyncUpdateUsrPaymentPlan(USR_CDE);
			update.start();

			if (StringUtils.isNotEmpty(LoanAcNO)) {
				apdNotLoan.setLoanAcno(LoanAcNO);
				apdNotLoan.setStCde("11");
				apdNotLoan.setDebtStatus("30");
				apdNotLoan.setRmk(DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
				usrApplyService.updateByPrimaryKeySelective(apdNotLoan);
			}

		}

	}
	
	private void doBusinessFromPolicyNo(String policyNo,String payChannel ) throws ParseException {
		String LoanAcNO = "";
		LoanRepaymentPlanBankQuery loanOrderQueryBank = new LoanRepaymentPlanBankQuery();
		LoanRepaymentPlanBankResp planResp = loanOrderQueryBank.loanRepaymentPlanBankQuery(
				policyNo, payChannel);
		logger.info("保单号" + policyNo + "银行贷款订单接口返回结果" + planResp.toString());

		if (planResp.getDataBody().getDataRecord().getHandleCode().equals("00006")) {

			// 如果追偿的表中追偿状态为Y
			boolean setLind = withRecoverQueryService.selectByPoliyNoToSetLind(
					String.valueOf(planResp.getDataBody().getDataRecord().getGuarantyid()));
			if (setLind) {
				planResp.getDataBody().getDataRecord().setAccountStatus("2");
				updateApplyStatusByPolicyNo(
						String.valueOf(planResp.getDataBody().getDataRecord().getGuarantyid()));
			}
			ClUsrLoan clUsrLoanBank = setLoanOrderBankData(planResp);
			ClUsrLoan inDbUsrLoan = loanOrderQueryService.getselectByPrimaryKey(
					String.valueOf(planResp.getDataBody().getDataRecord().getGuarantyid()));
			if (inDbUsrLoan == null) {
				boolean resultOrder = loanOrderQueryService.insertLoanOrder(clUsrLoanBank);
				if (resultOrder) {
					logger.info("银行贷款订单列表插入数据库返回结果:" + resultOrder);
				}
			} else {
				loanOrderQueryService.updateByObj(clUsrLoanBank);
				String deptS = applyStsMappingZWSts(clUsrLoanBank.getAccFlag());
				String policyNof = clUsrLoanBank.getPolicyNo();
				String time = DateUtils.getNowFullFmt();
				if (!setLind) {
					usrApplyService.updateDebtByPolicyNo(deptS, time, policyNof);
				}
			}
			LoanAcNO = clUsrLoanBank.getLoanAcno();

		}
		ClUsrApply apdNotLoan = usrApplyService.getByPolicyNo(policyNo);
		if (StringUtils.isNotEmpty(LoanAcNO)) {
			apdNotLoan.setLoanAcno(LoanAcNO);
			apdNotLoan.setStCde("11");
			apdNotLoan.setDebtStatus("30");
			apdNotLoan.setRmk(DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			usrApplyService.updateByPrimaryKeySelective(apdNotLoan);
		}
	}

	private void recoveryLmtFromPolicyNo(String policyNo) {
		ClUsrApply apdNotLoan = usrApplyService.getByPolicyNo(policyNo);
		String usrCde = apdNotLoan.getUsrCde();
		BigDecimal applyAmout = new BigDecimal(apdNotLoan.getApplyAmt());
		usrLmtAmtService.updateUsedAmtRecovery(usrCde, applyAmout);
		apdNotLoan.setStCde("13");
		apdNotLoan.setMdfDt(DateUtils.getNowFullFmt());
		apdNotLoan.setRmk(DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		usrApplyService.updateByPrimaryKeySelective(apdNotLoan);
	}

	private void recoveryLmt(ClUsrApply apdNotLoan) {
		String usrCde = apdNotLoan.getUsrCde();
		BigDecimal applyAmout = new BigDecimal(apdNotLoan.getApplyAmt());
		usrLmtAmtService.updateUsedAmtRecovery(usrCde, applyAmout);
		apdNotLoan.setStCde("13");
		apdNotLoan.setMdfDt(DateUtils.getNowFullFmt());
		apdNotLoan.setRmk(DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		usrApplyService.updateByPrimaryKeySelective(apdNotLoan);
	}
	public void checkAndDeal() {
		List<ClUsrApply> apdNoteLoaneds = getApdAndNotLoaned();
		logger.info("获取未处理的申请数 ：" + apdNoteLoaneds.size());
		for (ClUsrApply apdNotLoan : apdNoteLoaneds) {
			logger.info("检查保单号为 ：" + apdNotLoan.getPolicyNo() + "的申请");
			String status = getStatus(apdNotLoan);//
			logger.info("检查保单号为 ：" + apdNotLoan.getPolicyNo() + "的申请的放款状态为:" + status);
			if ("0".equals(status)) {
				// 0 处理中
				continue;
			} else if ("1".equals(status)) {
				// 放款成功 修改状态
				doBusiness(apdNotLoan);
			} else if ("2".equals(status)) {
				// 放款失败 恢复额度
				recoveryLmt(apdNotLoan);
			}
		}
	}

	public boolean checkAndDealFromPolicyNo(String policyNo,String payChannel) {

		String status = getStatusFromPolicyNo(policyNo);//
		if ("0".equals(status)) {
			// 0 处理中
			return true;
		} else if ("1".equals(status)) {
			// 放款成功 修改状态
			try {
				doBusinessFromPolicyNo(policyNo,payChannel);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		} else if ("2".equals(status)) {
			// 放款失败 恢复额度
			recoveryLmtFromPolicyNo(policyNo);
			return false;
		}
		return false;

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
	/**
	 * 解析参数银行参数 设值
	 * 
	 * @param job
	 * @return
	 * @throws ParseException
	 */
	private ClUsrLoan setLoanOrderBankData(LoanRepaymentPlanBankResp planResp) throws ParseException {
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

	private boolean updateApplyStatusByPolicyNo(String policyNo) {

		return usrApplyService.updateStatusByPolicyNo(policyNo);
	}

}
