package com.jeeplus.modules.app.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.app.api.account.loanorderquery.LoanOrderQuery;
import com.jeeplus.modules.app.api.account.loanorderquery.response.LoanOrderDetail;
import com.jeeplus.modules.app.api.account.loanorderquery.response.OUT;
import com.jeeplus.modules.app.api.account.loanrepaymentplanbankquery.LoanRepaymentPlanBankQuery;
import com.jeeplus.modules.app.api.account.loanrepaymentplanbankquery.response.LoanRepaymentPlanBankResp;
import com.jeeplus.modules.app.constant.AppCommonConstants;
import com.jeeplus.modules.app.entity.ClIdCardInfo;
import com.jeeplus.modules.app.entity.ClUsrApply;
import com.jeeplus.modules.app.entity.ClUsrLoan;
import com.jeeplus.modules.app.utils.CacheHelper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional(readOnly = false)
public class ClUpdateBankPaymentService {

	private final static Logger logger = LoggerFactory.getLogger(ClUpdateBankPaymentService.class);

	@Autowired
	private LoanOrderQueryService loanOrderQueryService;

	@Autowired
	private ClUsrApplyService applyService;

	@Autowired
	private ClIdCardInfoService idCardInfoService;

	@Autowired
	private WithRecoverQueryService withRecoverQueryService;

	@Autowired
	private ClUsrApplyService usrApplyService;

	public void getLoanOrderBankQuery() {

		List<ClUsrApply> list = applyService.getUsrApplyLoaned();

		for (ClUsrApply clUsrApply : list) {
			String POLICY_NO = clUsrApply.getPolicyNo();
			String USR_CDE = clUsrApply.getUsrCde();
			if (StringUtils.isNotEmpty(POLICY_NO) && StringUtils.isNotEmpty(USR_CDE)) {
				try {
					logger.info("更新用户id:" + USR_CDE + "账单信息,开始");
					queryAndUpdate(USR_CDE);
					logger.info("更新用户id:" + USR_CDE + "账单信息,完毕");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.info("更新用户id:" + USR_CDE + "账单信息,异常");
				}
			}
		}

	}

	public void getLoanOrderBankQuery(String USR_CDE) {

		try {
			logger.info("更新用户id:" + USR_CDE + "账单信息,开始");
			queryAndUpdate(USR_CDE);
			logger.info("更新用户id:" + USR_CDE + "账单信息,完毕");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("更新用户id:" + USR_CDE + "账单信息,异常");
		}
	}

	public void queryAndUpdate(String USR_CDE) throws ParseException {
		List<ClUsrApply> applies = usrApplyService.getPassedByUsrCde(USR_CDE);
		List<ClUsrApply> applieswf = new ArrayList<ClUsrApply>();
		Set<String> passedApplyPolicyNo = new HashSet<String>();
		for (ClUsrApply clUsrApply : applies) {
			if (("WFCCB").equals(clUsrApply.getPayChannel())) {
				applieswf.add(clUsrApply);
			}
			passedApplyPolicyNo.add(clUsrApply.getPolicyNo());
		}
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
						usrApplyService.updateDebtByPolicyNo(deptS, time, policyNo);
					}

				}
			}
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
	 * 根据保单号修改申请表中装态
	 */
	private boolean updateApplyStatusByPolicyNo(String policyNo) {

		return usrApplyService.updateStatusByPolicyNo(policyNo);
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

}
