package com.jeeplus.modules.app.api.account.loanrepaymentplanfromHS;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.jeeplus.common.xstream.XStreamUtils;
import com.jeeplus.modules.app.api.account.loanrepaymentplanbankquery.response.LoanRepaymentPlanBankResp;
import com.jeeplus.modules.app.api.account.loanrepaymentplanfromHS.request.MsgBody;
import com.jeeplus.modules.app.api.account.loanrepaymentplanfromHS.response.LoanRepaymentPlanFromHSResp;
import com.jeeplus.modules.app.api.account.utils.JaxbUtil;
import com.jeeplus.modules.app.utils.HttpConUtils;

/**
 * 核算还款计划
 * 
 * @author wangfz
 * @date 2019-01-02 
 */
@Controller
public class LoanRepaymentPlanFromHS {
	private final static Logger logger = LoggerFactory.getLogger(LoanRepaymentPlanFromHS.class);

	public LoanRepaymentPlanFromHSResp LoanRepaymentPlanFromHS(String GUARANTYID,String TRANBANK) {

		LoanRepaymentPlanFromHS loanRepaymentPlanFromHS = new LoanRepaymentPlanFromHS();
		String xmlStr ="XXXXX;serv10000000158;"+loanRepaymentPlanFromHS.setLoanRepaymentHSData(GUARANTYID);
		logger.info("核算还款计划查询请求参数" + xmlStr);
		// 请求接口
		HttpConUtils httpConUtils = new HttpConUtils();
		//String reulstXml = httpConUtils.doPostXML(PropertiesUtil.getString("loanapiurl"), xmlStr);
		String reulstXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><OUT><HEADER><PATCH>SD201811261511884</PATCH><TRANCODE>YG0046</TRANCODE><TRANTIME>20181126160050</TRANTIME><DATASOURCE>ISP</DATASOURCE></HEADER><BODY><RECORD><GUARANTYID>WD379551811261453W</GUARANTYID><HANDLECODE>00006</HANDLECODE><HANDLEINFO>受理成功</HANDLEINFO><LOANACNO>000000000117014</LOANACNO><LOANAMT>5000</LOANAMT><LOANTERM>6</LOANTERM><REPAYMENT>5000</REPAYMENT><STARTDATE>20181212</STARTDATE><EXPIRYDATE>20181212</EXPIRYDATE><REMAINCAPI>5000</REMAINCAPI><ACCOUNTSTATUS>1</ACCOUNTSTATUS><CONTENT>[{\"guarantyid\":\"WD379551811261453W\",\"loanacno\":\"000000000117014\",\"overflag\":\"0\",\"payoffdate\":\"\",\"payoffflag\":\"0\",\"rcapi\":0,\"rcontamt\":0,\"rfine\":0,\"rinsuamt\":0,\"rinte\":0,\"scapi\":819.04,\"scontamt\":0,\"sdate\":\"20181226\",\"sfine\":430.16,\"sinsuamt\":87.5,\"sinte\":34.58,\"sterm\":\"1\",\"sPayLiqDam\":\"1\",\"rPayLiqDam\":\"1\",\"sRecPayLiqDam\":\"1\",\"rRecPayLiqDam\":\"1\"},{\"guarantyid\":\"WD379551811261453W\",\"loanacno\":\"000000000117014\",\"overflag\":\"0\",\"payoffdate\":\"\",\"payoffflag\":\"0\",\"rcapi\":0,\"rcontamt\":0,\"rfine\":0,\"rinsuamt\":0,\"rinte\":0,\"scapi\":824.7,\"scontamt\":0,\"sdate\":\"20190126\",\"sfine\":0,\"sinsuamt\":87.5,\"sinte\":28.92,\"sterm\":\"2\",\"sPayLiqDam\":\"1\",\"rPayLiqDam\":\"1\",\"sRecPayLiqDam\":\"1\",\"rRecPayLiqDam\":\"1\"},{\"guarantyid\":\"WD379551811261453W\",\"loanacno\":\"000000000117014\",\"overflag\":\"0\",\"payoffdate\":\"\",\"payoffflag\":\"0\",\"rcapi\":0,\"rcontamt\":0,\"rfine\":0,\"rinsuamt\":0,\"rinte\":0,\"scapi\":830.41,\"scontamt\":0,\"sdate\":\"20190226\",\"sfine\":0,\"sinsuamt\":87.5,\"sinte\":23.21,\"sterm\":\"3\",\"sPayLiqDam\":\"1\",\"rPayLiqDam\":\"1\",\"sRecPayLiqDam\":\"1\",\"rRecPayLiqDam\":\"1\"},{\"guarantyid\":\"WD379551811261453W\",\"loanacno\":\"000000000117014\",\"overflag\":\"0\",\"payoffdate\":\"\",\"payoffflag\":\"0\",\"rcapi\":0,\"rcontamt\":0,\"rfine\":0,\"rinsuamt\":0,\"rinte\":0,\"scapi\":836.15,\"scontamt\":0,\"sdate\":\"20190326\",\"sfine\":0,\"sinsuamt\":87.5,\"sinte\":17.47,\"sterm\":\"4\",\"sPayLiqDam\":\"1\",\"rPayLiqDam\":\"1\",\"sRecPayLiqDam\":\"1\",\"rRecPayLiqDam\":\"1\"},{\"guarantyid\":\"WD379551811261453W\",\"loanacno\":\"000000000117014\",\"overflag\":\"0\",\"payoffdate\":\"\",\"payoffflag\":\"0\",\"rcapi\":0,\"rcontamt\":0,\"rfine\":0,\"rinsuamt\":0,\"rinte\":0,\"scapi\":841.93,\"scontamt\":0,\"sdate\":\"20190426\",\"sfine\":0,\"sinsuamt\":87.5,\"sinte\":11.69,\"sterm\":\"5\",\"sPayLiqDam\":\"1\",\"rPayLiqDam\":\"1\",\"sRecPayLiqDam\":\"1\",\"rRecPayLiqDam\":\"1\"},{\"guarantyid\":\"WD379551811261453W\",\"loanacno\":\"000000000117014\",\"overflag\":\"0\",\"payoffdate\":\"\",\"payoffflag\":\"0\",\"rcapi\":0,\"rcontamt\":0,\"rfine\":0,\"rinsuamt\":0,\"rinte\":0,\"scapi\":847.77,\"scontamt\":0,\"sdate\":\"20190526\",\"sfine\":0,\"sinsuamt\":87.5,\"sinte\":5.86,\"sterm\":\"6\",\"sPayLiqDam\":\"1\",\"rPayLiqDam\":\"1\",\"sRecPayLiqDam\":\"1\",\"rRecPayLiqDam\":\"1\"}]</CONTENT></RECORD></BODY></OUT>";
		logger.info("核算还款计划查询返回结果" + reulstXml);
		LoanRepaymentPlanFromHSResp planResp = XStreamUtils.covertXml2JavaBean(reulstXml, LoanRepaymentPlanFromHSResp.class);

		return planResp;
	}

	
	public String setLoanRepaymentHSData(String GUARANTYID) {
		MsgBody MsgBody = new MsgBody();
		MsgBody.setGuarantyId(GUARANTYID);
		MsgBody.setServiceId("serv10000000158");
		MsgBody.setChannelId("06");
		String xmlStr = JaxbUtil.convertToXml(MsgBody);
		return xmlStr;
	}
	public static void main(String[] args) {
		String reulstXml = "<?xml version=\"1.0\" encoding=\"gb2312\" ?>"
				+ "<msgall>"
				+ "<errorCode>00000</errorCode><errorMsg>success</errorMsg><sendTime>2018-03-02 15:00:00</sendTime><guarantyId>C832520181218447459</guarantyId><billNo>468461346845455</billNo><loanAmt>60000.00</loanAmt><tnr>36</tnr><startDate>2018-01-01</startDate><endDate>2021-01-01</endDate><remainCapital>20000.00</remainCapital><loanDebtSts>0</loanDebtSts>"
				+ "<PayShdList>"
				+ "<MX><perdNo>2</perdNo><dueDt>2018-04-01</dueDt><normPrcpAmt>1000.00</normPrcpAmt><normIntAmt>800.00</normIntAmt><odIntAmt>0.00</odIntAmt><holdFeeAmt>100.00</holdFeeAmt><serviceFee>10.00</serviceFee><setlPrcp>1000.00</setlPrcp><setlNormInt>800.00</setlNormInt><setlOdIntAmt>0.00</setlOdIntAmt><setlFeeAmt>100.00</setlFeeAmt><setlServiceFee>10.00</setlServiceFee><psOdInd>N</psOdInd><setlInd>Y</setlInd><setlDate>2018-04-01</setlDate><prePayFeeAmt>10.00</prePayFeeAmt><setlPrePayFeeAmt>10.00</setlPrePayFeeAmt><subrogationFeeAmt>10.00</subrogationFeeAmt><setlSubrogationFeeAmt>10.00</setlSubrogationFeeAmt>"
				+ "</MX>"
				+ "<MX><perdNo>1</perdNo><dueDt>2018-03-01</dueDt><normPrcpAmt>1000.00</normPrcpAmt><normIntAmt>800.00</normIntAmt><odIntAmt>0.00</odIntAmt><holdFeeAmt>100.00</holdFeeAmt><serviceFee>10.00</serviceFee><setlPrcp>1000.00</setlPrcp><setlNormInt>800.00</setlNormInt><setlOdIntAmt>0.00</setlOdIntAmt><setlFeeAmt>100.00</setlFeeAmt><setlServiceFee>10.00</setlServiceFee><psOdInd>N</psOdInd><setlInd>Y</setlInd><setlDate>2018-03-01</setlDate><prePayFeeAmt>10.00</prePayFeeAmt><setlPrePayFeeAmt>10.00</setlPrePayFeeAmt><subrogationFeeAmt>10.00</subrogationFeeAmt><setlSubrogationFeeAmt>10.00</setlSubrogationFeeAmt>"
				+ "</MX>"
				+ "</PayShdList>"
				+ "</msgall>";
		LoanRepaymentPlanFromHSResp planResp = XStreamUtils.covertXml2JavaBean(reulstXml, LoanRepaymentPlanFromHSResp.class);
		//System.out.println(planResp.toString());
		System.out.println(planResp.toString());
	}
}
