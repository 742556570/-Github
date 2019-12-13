package com.jeeplus.modules.app.api.product;
import com.jeeplus.modules.app.api.product.request.ReqProductVO;
import com.jeeplus.modules.app.api.product.response.RespProductVO;
import com.jeeplus.modules.app.utils.HttpConUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SynProductMSGController {

	 /**
     * 同步产品信息，将产品信息新增到核算系统中
     * @author wangfz
     * @date 2018-2-24 10:41:10
     * @param RespLoanBankVO
     * @return
     */
	private final static Logger logger = LoggerFactory.getLogger(SynProductMSGController.class);
	public RespProductVO synProductMSG(ReqProductVO reqProductVO) {
		ProductXmlHelper XmlHelper = new ProductXmlHelper();
		//组装请求参数
		String xmlstr = XmlHelper.createAddXml(reqProductVO);
		String xmltoStr = "XXXXX;"+reqProductVO.getServiceId()+";"+xmlstr;
		logger.info("同步产品请求参数："+xmltoStr);
		
		//请求接口
		HttpConUtils httpConUtils = new HttpConUtils();
		String reulst = httpConUtils.doPost(PropertiesUtil.getString("accountapiurl"), xmltoStr);
		
		logger.info("同步产品返回接口结果："+reulst);
		//返回xml转化成bean对象
		RespProductVO respProductVO =XmlHelper.xml2BeanFromAdd(reulst);
		
		logger.info("同步产品转化成bean对象："+respProductVO.toString());
		return respProductVO;
	}
	
}
