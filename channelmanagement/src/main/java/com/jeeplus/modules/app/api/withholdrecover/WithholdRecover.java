package com.jeeplus.modules.app.api.withholdrecover;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeeplus.modules.app.api.product.PropertiesUtil;
import com.jeeplus.modules.app.api.withholdrecover.request.ReqWithholdRecoverlVO;
import com.jeeplus.modules.app.api.withholdrecover.response.RespWithholdRecoverlVO;
import com.jeeplus.modules.app.utils.HttpConUtils;

public class WithholdRecover {
	/**
     * 代扣追偿返回接口接口
     * @author wangfz
     * @date 2018-2-24 
     * @param ReqWithholdRecoverlVO
     * @return
     */
	private final static Logger logger = LoggerFactory.getLogger(WithholdRecover.class);
	public RespWithholdRecoverlVO withholdRecover(String QUERY_DT,String QUERY_TYPE) {
		WithholdRecoverXmlHelper XmlHelper = new WithholdRecoverXmlHelper();
		ReqWithholdRecoverlVO reqWithholdRecoverlVO = setWithholdRecover(QUERY_DT,QUERY_TYPE);
		//组装请求参数
		String xmlstr = XmlHelper.createAddXml(reqWithholdRecoverlVO);
		String xmltoStr = "XXXXX;"+reqWithholdRecoverlVO.getServiceId()+";"+xmlstr;
		logger.info("代扣追偿请求参数："+xmltoStr);
		
		//请求接口
		HttpConUtils httpConUtils = new HttpConUtils();
		String reulst = httpConUtils.doPost(PropertiesUtil.getString("accountapiurl"), xmltoStr);
		
		logger.info("代扣追偿返回接口结果："+reulst);
		//返回xml转化成bean对象
		RespWithholdRecoverlVO respWithholdRecoverlVO =XmlHelper.xml2BeanFromAdd(reulst);
		
		logger.info("代扣追偿返回接口转化成bean对象："+respWithholdRecoverlVO.toString());
		return respWithholdRecoverlVO;
	}
	
	public RespWithholdRecoverlVO withholdRecover(ReqWithholdRecoverlVO reqWithholdRecoverlVO) {
		WithholdRecoverXmlHelper XmlHelper = new WithholdRecoverXmlHelper();
		//组装请求参数
		String xmlstr = XmlHelper.createAddXml(reqWithholdRecoverlVO);
		String xmltoStr = "XXXXX;"+reqWithholdRecoverlVO.getServiceId()+";"+xmlstr;
		logger.info("代扣追偿请求参数："+xmltoStr);
		
		//请求接口
		HttpConUtils httpConUtils = new HttpConUtils();
		String reulst = httpConUtils.doPost(PropertiesUtil.getString("accountapiurl"), xmltoStr);
		
		logger.info("代扣追偿返回接口结果："+reulst);
		//返回xml转化成bean对象
		RespWithholdRecoverlVO respWithholdRecoverlVO =XmlHelper.xml2BeanFromAdd(reulst);
		
		logger.info("代扣追偿返回接口转化成bean对象："+respWithholdRecoverlVO.toString());
		return respWithholdRecoverlVO;
	}
	/**
	 * 参数设置
	 * @param QUERY_DT
	 * @return
	 */
	public ReqWithholdRecoverlVO setWithholdRecover(String QUERY_DT,String QUERY_TYPE) {
		ReqWithholdRecoverlVO reqWithholdRecoverlVO = new ReqWithholdRecoverlVO();
		reqWithholdRecoverlVO.setServiceId("serv10000000156");
		reqWithholdRecoverlVO.setBEL_TYPE("07");
		reqWithholdRecoverlVO.setQUERY_DT(QUERY_DT); //格式 yyyy-MM-dd
		reqWithholdRecoverlVO.setQUERY_TYPE(QUERY_TYPE);
		return reqWithholdRecoverlVO;
	}
}
