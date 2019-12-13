package com.jeeplus.modules.app.api.account.bankcard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.xstream.XStreamUtils;
import com.jeeplus.modules.app.api.account.bankcard.request.BankCardRequestBody;
import com.jeeplus.modules.app.api.account.bankcard.request.BankCardRequestRecord;
import com.jeeplus.modules.app.api.account.bankcard.request.BankCardRequestVO;
import com.jeeplus.modules.app.api.account.bankcard.response.BankCardResponseVO;
import com.jeeplus.modules.app.api.account.header.DataHeader;
import com.jeeplus.modules.app.api.product.PropertiesUtil;
import com.jeeplus.modules.app.utils.HttpConUtils;

/**
 * 修改银行扣款卡号
 **@author wangjun
 **@date 2018年7月3日
 **/
public class BankCardModifyService {

	private static Logger logger = LoggerFactory.getLogger(BankCardModifyService.class);
	
	public BankCardResponseVO modifyBankCard(BankCardRequestVO bankCardReq){
		
		// 1. 将请求vo转化为xml报文
		String reqXml ="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+XStreamUtils.convertBean2Xml(bankCardReq, BankCardRequestVO.class);
		// TODO 
		System.out.println(reqXml);
		// 2. httpClientUtils发送post请求
		HttpConUtils httpConUtils = new HttpConUtils();
		String resultXml = httpConUtils.doPostXML(PropertiesUtil.getString("loanapiurl"), reqXml);
		logger.info("应答报文是 ： "+resultXml);
		// 3. 将应答xml报文转化为应答VO对象
		BankCardResponseVO bandCardResp = XStreamUtils.covertXml2JavaBean(resultXml, BankCardResponseVO.class);
		return bandCardResp;
	}
	
	public static void main(String[] args){
		BankCardModifyService service = new BankCardModifyService();
		BankCardRequestVO bankCardReq = new BankCardRequestVO();
		BankCardRequestBody reqBody = new BankCardRequestBody();
		BankCardRequestRecord record = new BankCardRequestRecord();
		record.setBankAcctName("雪柳");
		record.setBankCardNo("234321345213566443");
		record.setBankCityCode("110100");
		record.setBankCityName("北京市");
		record.setBankCode("ABC");
		record.setBankMobile("13401184241");
		record.setBankOwerName("雪柳");
		record.setBankProvinceCode("110000");
		record.setBankProvinceName("北京市");
		record.setCertificateNo("110114197901010021");
		record.setCertificateType("20");
		record.setGuarantyId("C810720171116003461");
		record.setLoanId("000000000116425");//借据号
		record.setMobile("13401184241");
		record.setReceiveAcctNo("6123412345654098675");
		record.setReceiveBankCode("ABC");
		record.setReceiveBankName("雪柳");
		record.setBankName("农业银行");
		reqBody.setBankCardRequestRecord(record);
		bankCardReq.setBankdCardRequestBody(reqBody);
		DataHeader header = new DataHeader();
		bankCardReq.setDataHeader(header);
		header.setDataSource("ISP");
		header.setPatch("LN20180703135905009699");
		header.setTranBank("FOTIC");
		header.setTranCode("YG0008");
		header.setTranTime("20171120015905");
		BankCardResponseVO bandCardResp = service.modifyBankCard(bankCardReq);
		System.out.println(bandCardResp.toString());
	}
}
