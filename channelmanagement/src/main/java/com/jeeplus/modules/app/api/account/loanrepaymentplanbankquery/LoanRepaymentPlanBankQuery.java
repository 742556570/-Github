package com.jeeplus.modules.app.api.account.loanrepaymentplanbankquery;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.jeeplus.common.xstream.XStreamUtils;
import com.jeeplus.modules.app.api.account.loanorderquery.LoanOrderQuery;
import com.jeeplus.modules.app.api.account.loanrepaymentplanbankquery.request.BODY;
import com.jeeplus.modules.app.api.account.loanrepaymentplanbankquery.request.HEADER;
import com.jeeplus.modules.app.api.account.loanrepaymentplanbankquery.request.IN;
import com.jeeplus.modules.app.api.account.loanrepaymentplanbankquery.request.RECORD;
import com.jeeplus.modules.app.api.account.loanrepaymentplanbankquery.response.LoanRepaymentPlanBankResp;
import com.jeeplus.modules.app.api.account.utils.JaxbUtil;
import com.jeeplus.modules.app.utils.HttpConUtils;

/**
 * 
 * @author 阳光保险 
 * 贷款还款计划查询请求银行
 */
@Controller
public class LoanRepaymentPlanBankQuery {
	private final static Logger logger = LoggerFactory.getLogger(LoanRepaymentPlanBankQuery.class);

	public LoanRepaymentPlanBankResp loanRepaymentPlanBankQuery(String GUARANTYID,String TRANBANK) {

		LoanRepaymentPlanBankQuery loanRepaymentPlanBankQuery = new LoanRepaymentPlanBankQuery();
		String xmlStr = loanRepaymentPlanBankQuery.setLoanRepaymentPlanQueryData(GUARANTYID,TRANBANK);
		logger.info("银行贷款还款计划查询请求参数" + xmlStr);
		// 请求接口
		HttpConUtils httpConUtils = new HttpConUtils();
		//String reulstXml = httpConUtils.doPostXML(PropertiesUtil.getString("loanapiurl"), xmlStr);
		String reulstXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><OUT><HEADER><PATCH>SD201811261511884</PATCH><TRANCODE>YG0046</TRANCODE><TRANTIME>20181126160050</TRANTIME><DATASOURCE>ISP</DATASOURCE></HEADER><BODY><RECORD><GUARANTYID>WD379551811261453W</GUARANTYID><HANDLECODE>00006</HANDLECODE><HANDLEINFO>受理成功</HANDLEINFO><LOANACNO>000000000117014</LOANACNO><LOANAMT>5000</LOANAMT><LOANTERM>6</LOANTERM><REPAYMENT>5000</REPAYMENT><STARTDATE>20181212</STARTDATE><EXPIRYDATE>20181212</EXPIRYDATE><REMAINCAPI>5000</REMAINCAPI><ACCOUNTSTATUS>1</ACCOUNTSTATUS><CONTENT>[{\"guarantyid\":\"WD379551811261453W\",\"LOANANNO\":\"000000000117014\",\"overflag\":\"0\",\"payoffdate\":\"\",\"payoffflag\":\"0\",\"rcapi\":0,\"rcontamt\":0,\"rfine\":0,\"rinsuamt\":0,\"rinte\":0,\"scapi\":819.04,\"scontamt\":0,\"sdate\":\"20181226\",\"sfine\":430.16,\"sinsuamt\":87.5,\"sinte\":34.58,\"sterm\":\"1\",\"sPayLiqDam\":\"1\",\"rPayLiqDam\":\"1\",\"sRecPayLiqDam\":\"1\",\"rRecPayLiqDam\":\"1\"},{\"guarantyid\":\"WD379551811261453W\",\"LOANACNO\":\"000000000117014\",\"overflag\":\"0\",\"payoffdate\":\"\",\"payoffflag\":\"0\",\"rcapi\":0,\"rcontamt\":0,\"rfine\":0,\"rinsuamt\":0,\"rinte\":0,\"scapi\":824.7,\"scontamt\":0,\"sdate\":\"20190126\",\"sfine\":0,\"sinsuamt\":87.5,\"sinte\":28.92,\"sterm\":\"2\",\"sPayLiqDam\":\"1\",\"rPayLiqDam\":\"1\",\"sRecPayLiqDam\":\"1\",\"rRecPayLiqDam\":\"1\"},{\"guarantyid\":\"WD379551811261453W\",\"LOANACNO\":\"000000000117014\",\"overflag\":\"0\",\"payoffdate\":\"\",\"payoffflag\":\"0\",\"rcapi\":0,\"rcontamt\":0,\"rfine\":0,\"rinsuamt\":0,\"rinte\":0,\"scapi\":830.41,\"scontamt\":0,\"sdate\":\"20190226\",\"sfine\":0,\"sinsuamt\":87.5,\"sinte\":23.21,\"sterm\":\"3\",\"sPayLiqDam\":\"1\",\"rPayLiqDam\":\"1\",\"sRecPayLiqDam\":\"1\",\"rRecPayLiqDam\":\"1\"},{\"guarantyid\":\"WD379551811261453W\",\"LOANACNO\":\"000000000117014\",\"overflag\":\"0\",\"payoffdate\":\"\",\"payoffflag\":\"0\",\"rcapi\":0,\"rcontamt\":0,\"rfine\":0,\"rinsuamt\":0,\"rinte\":0,\"scapi\":836.15,\"scontamt\":0,\"sdate\":\"20190326\",\"sfine\":0,\"sinsuamt\":87.5,\"sinte\":17.47,\"sterm\":\"4\",\"sPayLiqDam\":\"1\",\"rPayLiqDam\":\"1\",\"sRecPayLiqDam\":\"1\",\"rRecPayLiqDam\":\"1\"},{\"guarantyid\":\"WD379551811261453W\",\"LOANACNO\":\"000000000117014\",\"overflag\":\"0\",\"payoffdate\":\"\",\"payoffflag\":\"0\",\"rcapi\":0,\"rcontamt\":0,\"rfine\":0,\"rinsuamt\":0,\"rinte\":0,\"scapi\":841.93,\"scontamt\":0,\"sdate\":\"20190426\",\"sfine\":0,\"sinsuamt\":87.5,\"sinte\":11.69,\"sterm\":\"5\",\"sPayLiqDam\":\"1\",\"rPayLiqDam\":\"1\",\"sRecPayLiqDam\":\"1\",\"rRecPayLiqDam\":\"1\"},{\"guarantyid\":\"WD379551811261453W\",\"LOANACNO\":\"000000000117014\",\"overflag\":\"0\",\"payoffdate\":\"\",\"payoffflag\":\"0\",\"rcapi\":0,\"rcontamt\":0,\"rfine\":0,\"rinsuamt\":0,\"rinte\":0,\"scapi\":847.77,\"scontamt\":0,\"sdate\":\"20190526\",\"sfine\":0,\"sinsuamt\":87.5,\"sinte\":5.86,\"sterm\":\"6\",\"sPayLiqDam\":\"1\",\"rPayLiqDam\":\"1\",\"sRecPayLiqDam\":\"1\",\"rRecPayLiqDam\":\"1\"}]</CONTENT></RECORD></BODY></OUT>";
		logger.info("银行贷款还款计划查询返回结果" + reulstXml);
		LoanRepaymentPlanBankResp planResp = XStreamUtils.covertXml2JavaBean(reulstXml, LoanRepaymentPlanBankResp.class);

		return planResp;
	}

	/**
	 * 小额信贷订单查询银行请求参数
	 * @param GUARANTYID
	 * @param TRANBANK
	 * @return
	 */
	public String setLoanRepaymentPlanQueryData(String GUARANTYID,String TRANBANK) {
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
		header.setTRANBANK(TRANBANK);
		RECORD.setGUARANTYID(GUARANTYID);
		BODY.setRECORD(RECORD);
		IN.setHEADER(header);
		IN.setBODY(BODY);
		String xmlStr = JaxbUtil.convertToXml(IN);
		return xmlStr;
	}
	public static void main(String[] args) {
		String reulstXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><OUT><HEADER><PATCH>SD201811261511884</PATCH><TRANCODE>YG0046</TRANCODE><TRANTIME>20181126160050</TRANTIME><DATASOURCE>ISP</DATASOURCE></HEADER><BODY><RECORD><GUARANTYID>WD379551811261453W</GUARANTYID><HANDLECODE>00006</HANDLECODE><HANDLEINFO>受理成功</HANDLEINFO><LOANACNO>000000000117014</LOANACNO><LOANAMT>5000</LOANAMT><LOANTERM>6</LOANTERM><REPAYMENT>5000</REPAYMENT><STARTDATE>20181212</STARTDATE><EXPIRYDATE>20181212</EXPIRYDATE><REMAINCAPI>5000</REMAINCAPI><ACCOUNTSTATUS>1</ACCOUNTSTATUS><CONTENT>[{\"guarantyid\":\"WD379551811261453W\",\"LOANANNO\":\"000000000117014\",\"overflag\":\"0\",\"payoffdate\":\"\",\"payoffflag\":\"0\",\"rcapi\":0,\"rcontamt\":0,\"rfine\":0,\"rinsuamt\":0,\"rinte\":0,\"scapi\":819.04,\"scontamt\":0,\"sdate\":\"20181226\",\"sfine\":430.16,\"sinsuamt\":87.5,\"sinte\":34.58,\"sterm\":\"1\",\"sPayLiqDam\":\"1\",\"rPayLiqDam\":\"1\",\"sRecPayLiqDam\":\"1\",\"rRecPayLiqDam\":\"1\"},{\"guarantyid\":\"WD379551811261453W\",\"LOANACNO\":\"000000000117014\",\"overflag\":\"0\",\"payoffdate\":\"\",\"payoffflag\":\"0\",\"rcapi\":0,\"rcontamt\":0,\"rfine\":0,\"rinsuamt\":0,\"rinte\":0,\"scapi\":824.7,\"scontamt\":0,\"sdate\":\"20190126\",\"sfine\":0,\"sinsuamt\":87.5,\"sinte\":28.92,\"sterm\":\"2\",\"sPayLiqDam\":\"1\",\"rPayLiqDam\":\"1\",\"sRecPayLiqDam\":\"1\",\"rRecPayLiqDam\":\"1\"},{\"guarantyid\":\"WD379551811261453W\",\"LOANACNO\":\"000000000117014\",\"overflag\":\"0\",\"payoffdate\":\"\",\"payoffflag\":\"0\",\"rcapi\":0,\"rcontamt\":0,\"rfine\":0,\"rinsuamt\":0,\"rinte\":0,\"scapi\":830.41,\"scontamt\":0,\"sdate\":\"20190226\",\"sfine\":0,\"sinsuamt\":87.5,\"sinte\":23.21,\"sterm\":\"3\",\"sPayLiqDam\":\"1\",\"rPayLiqDam\":\"1\",\"sRecPayLiqDam\":\"1\",\"rRecPayLiqDam\":\"1\"},{\"guarantyid\":\"WD379551811261453W\",\"LOANACNO\":\"000000000117014\",\"overflag\":\"0\",\"payoffdate\":\"\",\"payoffflag\":\"0\",\"rcapi\":0,\"rcontamt\":0,\"rfine\":0,\"rinsuamt\":0,\"rinte\":0,\"scapi\":836.15,\"scontamt\":0,\"sdate\":\"20190326\",\"sfine\":0,\"sinsuamt\":87.5,\"sinte\":17.47,\"sterm\":\"4\",\"sPayLiqDam\":\"1\",\"rPayLiqDam\":\"1\",\"sRecPayLiqDam\":\"1\",\"rRecPayLiqDam\":\"1\"},{\"guarantyid\":\"WD379551811261453W\",\"LOANACNO\":\"000000000117014\",\"overflag\":\"0\",\"payoffdate\":\"\",\"payoffflag\":\"0\",\"rcapi\":0,\"rcontamt\":0,\"rfine\":0,\"rinsuamt\":0,\"rinte\":0,\"scapi\":841.93,\"scontamt\":0,\"sdate\":\"20190426\",\"sfine\":0,\"sinsuamt\":87.5,\"sinte\":11.69,\"sterm\":\"5\",\"sPayLiqDam\":\"1\",\"rPayLiqDam\":\"1\",\"sRecPayLiqDam\":\"1\",\"rRecPayLiqDam\":\"1\"},{\"guarantyid\":\"WD379551811261453W\",\"LOANACNO\":\"000000000117014\",\"overflag\":\"0\",\"payoffdate\":\"\",\"payoffflag\":\"0\",\"rcapi\":0,\"rcontamt\":0,\"rfine\":0,\"rinsuamt\":0,\"rinte\":0,\"scapi\":847.77,\"scontamt\":0,\"sdate\":\"20190526\",\"sfine\":0,\"sinsuamt\":87.5,\"sinte\":5.86,\"sterm\":\"6\",\"sPayLiqDam\":\"1\",\"rPayLiqDam\":\"1\",\"sRecPayLiqDam\":\"1\",\"rRecPayLiqDam\":\"1\"}]</CONTENT></RECORD></BODY></OUT>";
		logger.info("银行贷款还款计划查询返回结果" + reulstXml);
		LoanRepaymentPlanBankResp planResp = XStreamUtils.covertXml2JavaBean(reulstXml, LoanRepaymentPlanBankResp.class);
		System.out.println(planResp.getDataBody().getDataRecord().toString());
	}
}
