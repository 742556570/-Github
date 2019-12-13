package com.jeeplus.modules.app.api.invalidpolicy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeeplus.modules.app.api.invalidpolicy.request.ReqInvaildPolicyVO;
import com.jeeplus.modules.app.api.product.PropertiesUtil;
import com.jeeplus.modules.app.utils.HttpConUtils;

public class InvaildPolicyController {
	/**
     * 无效保单接口
     * @author wangfz
     * @date 2018-2-24 10:41:10
     * @param RespLoanMessageVO
     * @return
     */
	private final static Logger logger = LoggerFactory.getLogger(InvaildPolicyController.class);
	public void InvaildPolicy(ReqInvaildPolicyVO reqInvaildPolicyVO) {
		InvaildPolicyXmlHelper XmlHelper = new InvaildPolicyXmlHelper();
		//组装请求参数
		String xmlstr = XmlHelper.createAddXml(reqInvaildPolicyVO);
		String xmltoStr = "XXXXX;"+xmlstr;
		logger.info("无效保单请求参数："+xmltoStr);
		
		//请求接口
		HttpConUtils httpConUtils = new HttpConUtils();
		String reulst = httpConUtils.doPost(PropertiesUtil.getString("accountapiurl"), xmltoStr);
		
		logger.info("无效保单返回接口结果："+reulst);
		//返回xml转化成bean对象
		//RespLoanChannelVO respLoanBankVO =XmlHelper.xml2BeanFromAdd(reulst);
		
		//logger.info("放款渠道同步转化成bean对象："+respLoanBankVO.toString());
		//return respLoanBankVO;
	}
}
