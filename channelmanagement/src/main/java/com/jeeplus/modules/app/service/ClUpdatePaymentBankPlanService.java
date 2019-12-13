package com.jeeplus.modules.app.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.app.api.account.loanrepaymentplanbankquery.LoanRepaymentPlanBankQuery;
import com.jeeplus.modules.app.api.account.loanrepaymentplanbankquery.response.LoanRepaymentPlanBankDetail;
import com.jeeplus.modules.app.api.account.loanrepaymentplanbankquery.response.LoanRepaymentPlanBankResp;
import com.jeeplus.modules.app.constant.AppCommonConstants;
import com.jeeplus.modules.app.entity.ClUsrApply;
import com.jeeplus.modules.app.entity.ClUsrLoanDetail;
import com.jeeplus.modules.app.utils.CacheHelper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional(readOnly = false)
public class ClUpdatePaymentBankPlanService {

	private final static Logger logger = LoggerFactory.getLogger(ClUpdatePaymentBankPlanService.class);

	@Autowired
	private LoanRepaymentPlanQueryService loanRepaymentPlanQueryService;

	@Autowired
	private ClUsrApplyService applayService;

	public void getLoanRepaymentPlanQueryByUsr(String usrCde) {

		List<ClUsrApply> list = applayService.getUsrApplyLoaned(usrCde);
		for (ClUsrApply clUsrApply : list) {
			String POLICY_NO = clUsrApply.getPolicyNo();
			String USR_CDE = clUsrApply.getUsrCde();
			if (StringUtils.isNotEmpty(POLICY_NO) && StringUtils.isNotEmpty(USR_CDE)) {
				try {
					queryAndUpdate(POLICY_NO, USR_CDE);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public void getLoanRepaymentBankPlanQuery() {

		List<ClUsrApply> list = applayService.getUsrApplyLoaned();

		for (ClUsrApply clUsrApply : list) {
			String POLICY_NO = clUsrApply.getPolicyNo();
			String USR_CDE = clUsrApply.getUsrCde();
			if (StringUtils.isNotEmpty(POLICY_NO) && StringUtils.isNotEmpty(USR_CDE)) {
				try {
					queryAndUpdate(POLICY_NO, USR_CDE);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public boolean queryAndUpdate(String POLICY_NO, String USR_CDE) throws ParseException {
		boolean resultOrder;
		LoanRepaymentPlanBankQuery loanOrderQueryBank = new LoanRepaymentPlanBankQuery();
		LoanRepaymentPlanBankResp planResp = loanOrderQueryBank.loanRepaymentPlanBankQuery(POLICY_NO, "WFCCB");
		logger.info("保单号" + POLICY_NO + "银行贷款还款计划详情查询接口返回结果" + planResp.toString());
		
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
					ClUsrLoanDetail clUsrLoanDetail = setLoanRepaymentBankPlanDetail(detail, USR_CDE);
					loanRepaymentPlanQueryService.deleteLoanOrderPolicy(POLICY_NO);
					resultOrder = loanRepaymentPlanQueryService.insertLoanOrderDetail(clUsrLoanDetail);
					if (resultOrder) {
						logger.info("银行贷款还款计划插入数据库成功" + resultOrder);
					}
				}
			
			return true;
		}
		return false;
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
		logger.info("贷款还款计划查询接口插入数据" + clUsrLoanDetail.toString());
		return clUsrLoanDetail;
	}

}
