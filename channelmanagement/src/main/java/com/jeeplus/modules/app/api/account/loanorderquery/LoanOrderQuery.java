package com.jeeplus.modules.app.api.account.loanorderquery;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeeplus.modules.app.api.account.loanorderquery.request.BODY;
import com.jeeplus.modules.app.api.account.loanorderquery.request.HEADER;
import com.jeeplus.modules.app.api.account.loanorderquery.request.IN;
import com.jeeplus.modules.app.api.account.loanorderquery.request.RECORD;
import com.jeeplus.modules.app.api.account.loanorderquery.response.OUT;
import com.jeeplus.modules.app.api.account.utils.JaxbUtil;
import com.jeeplus.modules.app.api.product.PropertiesUtil;
import com.jeeplus.modules.app.utils.HttpConUtils;

public class LoanOrderQuery {
	private final static Logger logger = LoggerFactory.getLogger(LoanOrderQuery.class);

	/**
	 * 小额信贷订单查询
	 * 
	 * @param creditLoanApplyVO
	 * @throws ParseException
	 */
	public OUT loanOrderQuery(String CERTID) {
		LoanOrderQuery creditLoanApply = new LoanOrderQuery();
		String xmlStr = creditLoanApply.setLoanOrderQueryData(CERTID);
		logger.info("小额信用贷款请求参数" + xmlStr);
		// 请求接口
		HttpConUtils httpConUtils = new HttpConUtils();
		String reulst = httpConUtils.doPostXML(PropertiesUtil.getString("loanapiurl"), xmlStr);
		logger.info("小额信用贷款返回结果" + reulst);

		// 转化成bean对象
		OUT out = JaxbUtil.converyToJavaBean(reulst, OUT.class);

		return out;

	}

	/**
	 * 小额信贷订单查询
	 * 
	 * @param GUARANTYID
	 * @param USRCDE
	 * @param APPLYSUM
	 * @param PRD_CDE
	 * @return
	 */
	public String setLoanOrderQueryData(String CERTID) {
		RECORD RECORD = new RECORD();
		BODY BODY = new BODY();
		HEADER header = new HEADER();
		IN IN = new IN();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHMMSS");
		String date = sdf.format(new Date());
		String patch = "SD" + date + LoanOrderQuery.random6();
		header.setPATCH(patch);
		header.setTRANCODE("YG0045");
		header.setTRANTIME(date);
		header.setDATASOURCE("ISP");
		header.setTRANBANK("FOTIC");
		RECORD.setCERTID(CERTID);
		BODY.setRECORD(RECORD);
		IN.setHEADER(header);
		IN.setBODY(BODY);
		String xmlStr = JaxbUtil.convertToXml(IN);
		return xmlStr;
	}

	/**
	 * 生成6位随机数
	 * @return
	 */
	public static int random6() {
		int spy = 0;
		for (int i = 0; i <= 5; i++) { // 生成一个6位的序列号
			spy = (int) (Math.random() * 10);
		}
		return spy;
	}

}
