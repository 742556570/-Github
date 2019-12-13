package com.jeeplus.modules.app.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.app.api.account.loanrepaymentplanquery.LoanRepaymentPlanQuery;
import com.jeeplus.modules.app.api.account.loanrepaymentplanquery.response.LoanRepaymentPlanDetail;
import com.jeeplus.modules.app.api.account.loanrepaymentplanquery.response.LoanRepaymentPlanResp;
import com.jeeplus.modules.app.constant.AppCommonConstants;
import com.jeeplus.modules.app.entity.ClIdCardInfo;
import com.jeeplus.modules.app.entity.ClUsrApply;
import com.jeeplus.modules.app.entity.ClUsrLoanDetail;
import com.jeeplus.modules.app.utils.CacheHelper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional(readOnly = false)
public class ClUpdatePaymentPlanService {

	private final static Logger logger = LoggerFactory.getLogger(ClUpdatePaymentPlanService.class);
	
	@Autowired
	private LoanRepaymentPlanQueryService loanRepaymentPlanQueryService ;
	
	@Autowired
	private ClUsrApplyService applayService;
	
	@Autowired
	private WithRecoverQueryService withRecoverQueryService;
	
	@Autowired
	private ClFadadaParamsService fpService;
	
	@Autowired
	private ClUsrContractService ucService;
	
	@Autowired
	private ClIdCardInfoService idcardInfoSerive;
	
	
	public void getLoanRepaymentPlanQueryByUsr(String usrCde) {
		
		List<ClUsrApply> list = applayService.getUsrApplyLoaned(usrCde);
		for (ClUsrApply clUsrApply : list) {
			String POLICY_NO= clUsrApply.getPolicyNo();
			String USR_CDE = clUsrApply.getUsrCde();
			if(StringUtils.isNotEmpty(POLICY_NO) && StringUtils.isNotEmpty(USR_CDE)) {
				queryAndUpdate(POLICY_NO,USR_CDE);
			}
		}
		
		
	}
	
	
	
	
	
	public void getLoanRepaymentPlanQuery()  {
		
		List<ClUsrApply> list = applayService.getUsrApplyLoaned();
		
		for (ClUsrApply clUsrApply : list) {
			String POLICY_NO= clUsrApply.getPolicyNo();
			String USR_CDE = clUsrApply.getUsrCde();
			if(StringUtils.isNotEmpty(POLICY_NO) && StringUtils.isNotEmpty(USR_CDE)) {
				queryAndUpdate(POLICY_NO,USR_CDE);
			}
		}
		
	}
	
	
	
	
	public boolean queryAndUpdate(String POLICY_NO,String USR_CDE) {
		boolean resultOrder;
		LoanRepaymentPlanQuery loanRepaymentPlanQuery = new LoanRepaymentPlanQuery();
		LoanRepaymentPlanResp planResp = loanRepaymentPlanQuery.loanRepaymentPlanQuery(POLICY_NO);
		List<LoanRepaymentPlanDetail> loanRepaymentPlanDetailList = new ArrayList<LoanRepaymentPlanDetail>();
		logger.info("贷款还款计划查询接口返回结果" + planResp.toString());
		if (planResp.getDataBody().getDataRecord().getHandleCode().equals("00006")) {
			loanRepaymentPlanQueryService.deleteLoanOrderPolicy(POLICY_NO);
		    // 解析JSON串数组
			JSONArray jsonArray = JSONArray.fromObject(planResp.getDataBody().getDataRecord().getRepaymentDetails()); 
			//如果追偿的表中追偿状态为Y 
			
			for(int i=0;i<jsonArray.size();i++){
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				// 解析JSON映射成bean对象
				LoanRepaymentPlanDetail detail = (LoanRepaymentPlanDetail)JSONObject.toBean(jsonObject, LoanRepaymentPlanDetail.class);
				//如果追偿的表中追偿状态为Y 
				boolean setLind = withRecoverQueryService.selectByPoliyNoToSetLind(String.valueOf(jsonObject.get("guarantyid")));
				if(setLind){
					detail.setPayoffflag("1");
				}
				ClUsrLoanDetail clUsrLoanDetail = setLoanRepaymentPlanDetail(detail,USR_CDE);
					resultOrder = loanRepaymentPlanQueryService.insertLoanOrderDetail(clUsrLoanDetail);
					if (resultOrder) {
						logger.info("小额信用贷款插入数据库成功" + resultOrder);
						
					}
				loanRepaymentPlanDetailList.add(detail);
				CacheHelper.setListObject(AppCommonConstants.getValStr("CACHESCHEMA"), "LoanRepaymentPlan"+POLICY_NO, detail, AppCommonConstants.getValInt("CACHEVALIAPPLYTTL"));
			}
			boolean isExist = ucService.isExistByPolicyNo(POLICY_NO, "YGSDPH80000011-1");
			if(!isExist) {
				String date  = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
				ClIdCardInfo idcard = idcardInfoSerive.getByUsrCode(USR_CDE);
				net.sf.json.JSONObject paramsHt =  net.sf.json.JSONObject.fromObject(fpService.getParams(USR_CDE, "YGSDPH80000011-1", POLICY_NO,date));
				ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), idcard.getUSR_TEL(), paramsHt, "YGSDPH80000011-1", "还款事项提醒函", "1", "签章处", POLICY_NO, USR_CDE, "4-4", "","CI06");
			}
			return true;
		}

		return false;
	}
	
	/**
	 * 解析参数 设值
	 * @param job
	 * @return
	 * @throws ParseException
	 */
	private ClUsrLoanDetail setLoanRepaymentPlanDetail(LoanRepaymentPlanDetail detail,String USR_CDE)  {
		int applyID = loanRepaymentPlanQueryService.getApplyId();
		ClUsrLoanDetail clUsrLoanDetail = new ClUsrLoanDetail();
		Date payoffdate = null;
		Date sdate = null;
		String payoffflag = "";
		if(!StringUtils.isEmpty(detail.getSdate())){
			 sdate = DateUtils.parseDate(detail.getSdate());
		}
		
		if(!StringUtils.isEmpty(detail.getPayoffdate())){
			 payoffdate = DateUtils.parseDate(detail.getPayoffdate());
		}
		if(!StringUtils.isEmpty(detail.getPayoffflag())){
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
