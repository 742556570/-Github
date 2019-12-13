package com.jeeplus.modules.app.api.loanChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeeplus.modules.app.api.loanChannel.request.ReqLoanChannelVO;
import com.jeeplus.modules.app.api.loanChannel.response.RespLoanChannelVO;
import com.jeeplus.modules.app.api.product.PropertiesUtil;
import com.jeeplus.modules.app.utils.HttpConUtils;


public class SynLoanChannelController {
	 /**
     * 放款渠道同步到核算系统
     * @author wangfz
     * @date 2018-2-24 10:41:10
     * @param RespLoanMessageVO
     * @return
     */
	private final static Logger logger = LoggerFactory.getLogger(SynLoanChannelController.class);
	public RespLoanChannelVO synProductMSG(ReqLoanChannelVO reqLoanBankVO) {
		LoanChannelXmlHelper XmlHelper = new LoanChannelXmlHelper();
		//组装请求参数
		String xmlstr = XmlHelper.createAddXml(reqLoanBankVO);
		String xmltoStr = "XXXXX;"+reqLoanBankVO.getServiceId()+";"+xmlstr;
		logger.info("放款渠道同步请求参数："+xmltoStr);
		
		//请求接口
		HttpConUtils httpConUtils = new HttpConUtils();
		String reulst = httpConUtils.doPost(PropertiesUtil.getString("accountapiurl"), xmltoStr);
		
		logger.info("放款渠道同步返回接口结果："+reulst);
		//返回xml转化成bean对象
		RespLoanChannelVO respLoanBankVO =XmlHelper.xml2BeanFromAdd(reulst);
		
		logger.info("放款渠道同步转化成bean对象："+respLoanBankVO.toString());
		return respLoanBankVO;
	}
}
