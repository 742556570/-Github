package com.jeeplus.modules.app.api.account.CreditLoanApplyQuery;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeeplus.common.xstream.XStreamUtils;
import com.jeeplus.modules.app.api.account.CreditLoanApplyQuery.request.BODY;
import com.jeeplus.modules.app.api.account.CreditLoanApplyQuery.request.HEADER;
import com.jeeplus.modules.app.api.account.CreditLoanApplyQuery.request.IN;
import com.jeeplus.modules.app.api.account.CreditLoanApplyQuery.request.RECORD;
import com.jeeplus.modules.app.api.account.CreditLoanApplyQuery.response.CreditLoanApplyQueryResp;
import com.jeeplus.modules.app.api.account.loanorderquery.LoanOrderQuery;
import com.jeeplus.modules.app.api.account.utils.JaxbUtil;
import com.jeeplus.modules.app.api.product.PropertiesUtil;
import com.jeeplus.modules.app.utils.HttpConUtils;
import com.thoughtworks.xstream.XStream;
/**
 * 小额信用贷款查询
 * @author 阳光保险
 *
 */
public class CreditLoanApplyQueryController {
	private final static Logger logger = LoggerFactory.getLogger(CreditLoanApplyQueryController.class);

	/**
	 * 小额信贷申请查询
	 * @param creditLoanApplyVO
	 */
	public CreditLoanApplyQueryResp creditLoanApplyQuery(String GUARANTYID) {
		CreditLoanApplyQueryController creditLoanApplyController = new CreditLoanApplyQueryController();
		String xmlStr =creditLoanApplyController.setCreditLoanApplyQueryData(GUARANTYID);
		logger.info("小额信用贷款查询请求参数"+xmlStr);
		//请求接口
		HttpConUtils httpConUtils = new HttpConUtils();
		String reulstXml = httpConUtils.doPostXML(PropertiesUtil.getString("loanapiurl"), xmlStr);
		logger.info("小额信用贷款查询返回结果"+reulstXml);
		CreditLoanApplyQueryResp queryResp = XStreamUtils.covertXml2JavaBean(reulstXml, CreditLoanApplyQueryResp.class);
		return queryResp;
	}
	/**
	 * 小额信用贷款申请查询
	 * @param GUARANTYID
	 * @param USRCDE
	 * @param APPLYSUM
	 * @param PRD_CDE
	 * @return
	 */
	public String setCreditLoanApplyQueryData(String GUARANTYID) {
		RECORD RECORD = new RECORD();
		BODY BODY = new BODY();
		HEADER header = new HEADER();
		IN IN = new IN();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHMMSS");
		String date = sdf.format(new Date());
		String patch = "SD" + date + LoanOrderQuery.random6();
		header.setPATCH(patch);
		header.setTRANCODE("YG0044");
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
