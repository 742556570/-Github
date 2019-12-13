package com.jeeplus.modules.app.api.loantrial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jeeplus.modules.app.api.loantrial.request.ReqLoanTrialVO;
import com.jeeplus.modules.app.api.loantrial.response.RespLoanTrialVO;
import com.jeeplus.modules.app.api.product.PropertiesUtil;
import com.jeeplus.modules.app.utils.HttpConUtils;


public class SynLoanTrial {
	 /**
     * 贷款申请试算
     * @author wangfz
     * @date 2018-2-24
     * @param RespLoanMessageVO
     * @return
     */
	private final static Logger logger = LoggerFactory.getLogger(SynLoanTrial.class);
	public RespLoanTrialVO synLoanTrial(ReqLoanTrialVO reqLoanTrialVO) {
		LoanTrialXmlHelper XmlHelper = new LoanTrialXmlHelper();
		//组装请求参数
		String xmlstr = XmlHelper.createAddXml(reqLoanTrialVO);
		String xmltoStr = "XXXXX;"+reqLoanTrialVO.getServiceId()+";"+xmlstr;
		logger.info("贷款申请试算请求参数："+xmltoStr);
		
		//请求接口
		HttpConUtils httpConUtils = new HttpConUtils();
		String reulst = httpConUtils.doPost(PropertiesUtil.getString("accountapiurl"), xmltoStr);
		
		logger.info("贷款申请试算返回接口结果："+reulst);
		//返回xml转化成bean对象
		RespLoanTrialVO respLoanTrialVO =XmlHelper.xml2BeanFromAdd(reulst);
		logger.info("贷款申请试算转化成bean对象："+respLoanTrialVO.toString());
		return respLoanTrialVO;
	}
}
