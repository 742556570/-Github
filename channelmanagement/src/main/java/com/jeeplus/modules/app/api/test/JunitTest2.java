package com.jeeplus.modules.app.api.test;
import java.text.ParseException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import com.jeeplus.modules.app.api.account.LoanPrePayment.request.BODY;
import com.jeeplus.modules.app.api.account.LoanPrePayment.request.HEADER;
import com.jeeplus.modules.app.api.account.LoanPrePayment.request.IN;
import com.jeeplus.modules.app.api.account.LoanPrePayment.request.RECORD;
import com.jeeplus.modules.app.api.account.utils.JaxbUtil;
import com.jeeplus.modules.app.api.product.PropertiesUtil;
import com.jeeplus.modules.app.utils.HttpConUtils;
@ContextConfiguration(locations = { "classpath*:spring-*.xml" }) 
public class JunitTest2 {
	@Test
	public void LoanPrePaymenttest() {
		RECORD RECORD = new RECORD();
		BODY BODY = new BODY();
		HEADER header = new HEADER();
		IN IN = new IN();
		header.setPATCH("ISP20171219152226579812");
		header.setTRANBANK("ZYXF");
		header.setTRANCODE("YG0007");
		header.setTRANTIME("20171219032226");
		header.setDATASOURCE("ISP");
		RECORD.setGUARANTYID("6217991660005472234");
		RECORD.setLNACCT("70100030001281206");
		RECORD.setTYPE("1");
		RECORD.setCALDATE("20171219");
		RECORD.setPAYMENTACCTNO("C832520170314050445");
		BODY.setRECORD(RECORD);
		IN.setHEADER(header);
		IN.setBODY(BODY);
		String xmlStr = JaxbUtil.convertToXml(IN);
		System.out.println(xmlStr);
		HttpConUtils httpConUtils = new HttpConUtils();
		String reulst = httpConUtils.doPostXML(PropertiesUtil.getString("loanapiurl"), xmlStr);
		System.out.println(reulst);
	}

}
