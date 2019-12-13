package com.jeeplus.modules.app.api.loanMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jeeplus.modules.app.api.loanMessage.request.ReqLoanMessageVO;
import com.jeeplus.modules.app.api.loanMessage.response.RespLoanMessageVO;
import com.jeeplus.modules.app.api.product.PropertiesUtil;
import com.jeeplus.modules.app.utils.HttpConUtils;


public class SynLoanMessageController {
	 /**
     * 放款信息同步到核算系统
     * @author wangfz
     * @date 2018-2-24 10:41:10
     * @param RespLoanMessageVO
     * @return
     */
	private final static Logger logger = LoggerFactory.getLogger(SynLoanMessageController.class);
	public RespLoanMessageVO synLoanMessage(ReqLoanMessageVO reqLoanMessageVO) {
		LoanMessageXmlHelper XmlHelper = new LoanMessageXmlHelper();
		//组装请求参数
		String xmlstr = XmlHelper.createAddXml(reqLoanMessageVO);
		String xmltoStr = "XXXXX;"+reqLoanMessageVO.getServiceId()+";"+xmlstr;
		logger.info("放款信息同步请求参数："+xmltoStr);
		
		//请求接口
		HttpConUtils httpConUtils = new HttpConUtils();
		String reulst = httpConUtils.doPost(PropertiesUtil.getString("accountapiurl"), xmltoStr);
		
		logger.info("放款信息同步返回接口结果："+reulst);
		//返回xml转化成bean对象
		RespLoanMessageVO respLoanMessageVO =XmlHelper.xml2BeanFromAdd(reulst);
		
		logger.info("放款信息同步转化成bean对象："+respLoanMessageVO.toString());
		return respLoanMessageVO;
	}
}
