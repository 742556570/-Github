package com.jeeplus.modules.app.api.account.loanrepaymentplanquery;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.jeeplus.common.xstream.XStreamUtils;
import com.jeeplus.modules.app.api.account.loanorderquery.LoanOrderQuery;
import com.jeeplus.modules.app.api.account.loanrepaymentplanquery.request.BODY;
import com.jeeplus.modules.app.api.account.loanrepaymentplanquery.request.HEADER;
import com.jeeplus.modules.app.api.account.loanrepaymentplanquery.request.IN;
import com.jeeplus.modules.app.api.account.loanrepaymentplanquery.request.RECORD;
import com.jeeplus.modules.app.api.account.loanrepaymentplanquery.response.LoanRepaymentPlanResp;
import com.jeeplus.modules.app.api.account.utils.JaxbUtil;
import com.jeeplus.modules.app.api.product.PropertiesUtil;
import com.jeeplus.modules.app.utils.HttpConUtils;

/**
 * 
 * @author 阳光保险
 * 贷款还款计划查询请求controller
 */
@Controller
public class LoanRepaymentPlanQuery {
	private final static Logger logger = LoggerFactory.getLogger(LoanRepaymentPlanQuery.class);

	/**
	 * 查询
	 * @param creditLoanApplyVO
	 */
	public LoanRepaymentPlanResp loanRepaymentPlanQuery(String GUARANTYID) {

		LoanRepaymentPlanQuery loanRepaymentPlanQueryController = new LoanRepaymentPlanQuery();
		String xmlStr =loanRepaymentPlanQueryController.setLoanRepaymentPlanQueryData(GUARANTYID);
		logger.info("贷款还款计划查询请求参数"+xmlStr);
		//请求接口
		HttpConUtils httpConUtils = new HttpConUtils();
		String reulstXml = httpConUtils.doPostXML(PropertiesUtil.getString("loanapiurl"), xmlStr);
		logger.info("贷款还款计划查询返回结果"+reulstXml);
		LoanRepaymentPlanResp planResp=XStreamUtils.covertXml2JavaBean(reulstXml, LoanRepaymentPlanResp.class);

		return planResp;
	}
	/**
	 * 小额信贷订单查询
	 * @param GUARANTYID
	 * @param USRCDE
	 * @param APPLYSUM
	 * @param PRD_CDE
	 * @return
	 */
	public String setLoanRepaymentPlanQueryData(String GUARANTYID) {
		RECORD RECORD = new RECORD();
		BODY BODY = new BODY();
		HEADER header = new HEADER();
		IN IN = new IN();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHMMSS");
		String date = sdf.format(new Date());
		String patch = "SD" + date + LoanOrderQuery.random6();
		header.setPATCH(patch);
		header.setTRANCODE("YG0046");
		header.setTRANTIME(date);
		header.setDATASOURCE("ISP");
		header.setTRANBANK("FOTIC");
		RECORD.setGUARANTYID(GUARANTYID);
		BODY.setRECORD(RECORD);
		IN.setHEADER(header);
		IN.setBODY(BODY);
		String xmlStr = JaxbUtil.convertToXml(IN);
		return xmlStr; 
	}
}
