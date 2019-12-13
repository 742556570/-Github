package com.jeeplus.modules.app.api.account.LoanPrePayment;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeeplus.common.xstream.XStreamUtils;
import com.jeeplus.modules.app.api.account.LoanPrePayment.request.BODY;
import com.jeeplus.modules.app.api.account.LoanPrePayment.request.HEADER;
import com.jeeplus.modules.app.api.account.LoanPrePayment.request.IN;
import com.jeeplus.modules.app.api.account.LoanPrePayment.request.RECORD;
import com.jeeplus.modules.app.api.account.LoanPrePayment.response.LoanPrepaymentResp;
import com.jeeplus.modules.app.api.account.loanorderquery.LoanOrderQuery;
import com.jeeplus.modules.app.api.account.utils.JaxbUtil;
import com.jeeplus.modules.app.api.loanChannel.SynLoanChannelController;
import com.jeeplus.modules.app.api.product.PropertiesUtil;
import com.jeeplus.modules.app.utils.HttpConUtils;

/**
 * 提前还款
 * 
 * @author 阳光保险
 *
 */
public class LoanPrePayment {
	private final static Logger logger = LoggerFactory.getLogger(SynLoanChannelController.class);

	/**
	 * 贷款提前还款
	 * 
	 * @param loanPrePaymentVO
	 */
	public LoanPrepaymentResp loanPrePayment(String GUARANTYID, String LNACCT, String TYPE, String CALDATE,
			String PAYMENTACCTNO) {
		LoanPrePayment loanPrePaymentController = new LoanPrePayment();
		String XMLStr = loanPrePaymentController.setLoanPrePaymentData(GUARANTYID, LNACCT, TYPE, CALDATE,
				PAYMENTACCTNO);
		logger.info("贷款提前还款请求参数" + XMLStr);
		// 请求接口
		HttpConUtils httpConUtils = new HttpConUtils();
		String reulstXml = httpConUtils.doPostXML(PropertiesUtil.getString("loanapiurl"), XMLStr);
		logger.info("贷款提前还款返回"+reulstXml);
		LoanPrepaymentResp loanPrepaymentResp = XStreamUtils.covertXml2JavaBean(reulstXml, LoanPrepaymentResp.class);
		return loanPrepaymentResp;

	}

	/**
	 * 提前还款
	 * 
	 * @param GUARANTYID
	 * @param USRCDE
	 * @param APPLYSUM
	 * @param PRD_CDE
	 * @return
	 */
	public String setLoanPrePaymentData(String GUARANTYID, String LNACCT, String TYPE, String CALDATE,
			String PAYMENTACCTNO) {
		RECORD RECORD = new RECORD();
		BODY BODY = new BODY();
		HEADER header = new HEADER();
		IN IN = new IN();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHMMSS");
		String date = sdf.format(new Date());
		String patch = "SD" + date + LoanOrderQuery.random6();
		header.setPATCH(patch);
		header.setTRANBANK("FOTIC");
		header.setTRANCODE("YG0007");
		header.setTRANTIME(date);
		header.setDATASOURCE("ISP");
		RECORD.setGUARANTYID(GUARANTYID);// 保单号
		RECORD.setLNACCT(LNACCT);// 借据号
		RECORD.setTYPE(TYPE);
		RECORD.setCALDATE(CALDATE);
		RECORD.setPAYMENTACCTNO(PAYMENTACCTNO);
		BODY.setRECORD(RECORD);
		IN.setHEADER(header);
		IN.setBODY(BODY);
		String xmlStr = JaxbUtil.convertToXml(IN);
		return xmlStr;
	}
}
