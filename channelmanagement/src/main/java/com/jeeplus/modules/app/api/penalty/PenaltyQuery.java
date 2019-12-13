package com.jeeplus.modules.app.api.penalty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeeplus.modules.app.api.penalty.request.ReqPenaltyQueryVO;
import com.jeeplus.modules.app.api.penalty.response.RespPenaltyQueryVO;
import com.jeeplus.modules.app.api.product.PropertiesUtil;
import com.jeeplus.modules.app.api.withholdrecover.request.ReqWithholdRecoverlVO;
import com.jeeplus.modules.app.api.withholdrecover.response.RespWithholdRecoverlVO;
import com.jeeplus.modules.app.utils.HttpConUtils;

public class PenaltyQuery {
	/**
     * 违约金查询接口
     * @author saintlier
     * @date 2018-7-17 
     * @param ReqWithholdRecoverlVO
     * @return
     */
	private final static Logger logger = LoggerFactory.getLogger(PenaltyQuery.class);
	public RespPenaltyQueryVO penaltyQuery(String QUERY_DT,String LOAN_NO) {
		PenaltyQueryXmlHelper XmlHelper = new PenaltyQueryXmlHelper();
		ReqPenaltyQueryVO reqPenaltyQueryVO = setWithholdRecover(QUERY_DT,LOAN_NO);
		//组装请求参数
		String xmlstr = XmlHelper.createAddXml(reqPenaltyQueryVO);
		String xmltoStr = "XXXXX;"+reqPenaltyQueryVO.getServiceId()+";"+xmlstr;
		logger.info("违约金查询请求参数："+xmltoStr);
		
		//请求接口
		HttpConUtils httpConUtils = new HttpConUtils();
		String reulst = httpConUtils.doPost(PropertiesUtil.getString("accountapiurl"), xmltoStr);
		
		logger.info("违约金查询返回接口结果："+reulst);
		//返回xml转化成bean对象
		RespPenaltyQueryVO respPenaltyQueryVO =XmlHelper.xml2BeanFromAdd(reulst);
		
		logger.info("违约金查询返回接口转化成bean对象："+respPenaltyQueryVO.toString());
		return respPenaltyQueryVO;
	}
	
	public RespPenaltyQueryVO withholdRecover(ReqPenaltyQueryVO reqPenaltyQueryVO) {
		PenaltyQueryXmlHelper XmlHelper = new PenaltyQueryXmlHelper();
		//组装请求参数
		String xmlstr = XmlHelper.createAddXml(reqPenaltyQueryVO);
		String xmltoStr = "XXXXX;"+reqPenaltyQueryVO.getServiceId()+";"+xmlstr;
		logger.info("违约金查询请求参数："+xmltoStr);
		
		//请求接口
		HttpConUtils httpConUtils = new HttpConUtils();
		String reulst = httpConUtils.doPost(PropertiesUtil.getString("accountapiurl"), xmltoStr);
		
		logger.info("违约金查询返回接口结果："+reulst);
		//返回xml转化成bean对象
		RespPenaltyQueryVO respPenaltyQueryVO =XmlHelper.xml2BeanFromAdd(reulst);
		
		logger.info("违约金查询返回接口转化成bean对象："+respPenaltyQueryVO.toString());
		return respPenaltyQueryVO;
	}
	/**
	 * 参数设置
	 * @param QUERY_DT
	 * @return
	 */
	public ReqPenaltyQueryVO setWithholdRecover(String QUERY_DT,String LOAN_NO) {
		ReqPenaltyQueryVO reqPenaltyQueryVO = new ReqPenaltyQueryVO();
		reqPenaltyQueryVO.setServiceId("serv10000000158");
		reqPenaltyQueryVO.setQUERY_DT(QUERY_DT); //格式 yyyy-MM-dd
		reqPenaltyQueryVO.setLOAN_NO(LOAN_NO);
		return reqPenaltyQueryVO;
	}
	
	
	public static void main(String[] args) {
		PenaltyQuery pq = new PenaltyQuery();
		pq.penaltyQuery("", "CDasdasda123");
	}
}
