package com.jeeplus.modules.app.api.invalidpolicy.util;

import java.sql.Connection;


/**
 * <p>
 * <h2>简述</h2>
 * 		<ol>客户端服务接口实现类:调用总线服务接口</ol>
 * <h2>功能描述</h2>
 * 		<ol>请添加功能详细的描述</ol>
 * <h2>修改历史</h2>
 *    <ol>如有修改，添加修改历史</ol>
 * </p>
 * @author XuQZ
 * @2016-7-27 14:54:18
 * @version 1.0
 */
@ModuleService(serviceId="BusService",serviceDesc="总线服务",
				moduleId="com.yucheng.cmis.retailloan.intfservermanagement.client",moduleName="客户端",className="com.yucheng.cmis.retailloan.intfservermanagement.client.msi.BusServiceInterface")
public interface BusServiceInterface {
	
	/**
	 * 发送交易至总线（公共方法）
	 * @param url 总线wsdl地址
	 * @param nameSpace 
	 * @param methodName 方法名
	 * @param xmlMsg 
	 * @return Xml字符串
	 */
	public String sendMsgToBus(String url, String nameSpace, String methodName, String xmlMsg, Connection connection);
	
	/**
	 * 发送交易至总线(自己组报文头)
	 * @param url 总线wsdl地址
	 * @param nameSpace  名空间
	 * @param methodName 方法名
	 * @param xmlMsg  交易报文
	 * @param connection
	 * @return  Xml字符串
	 */
	public String clientForCallBus(String url, String nameSpace, String methodName, String xmlMsg, Connection connection);
	
	/**
	 * 发送交易至平台(自己组报文头)
	 * @param xmlMsg  交易报文
	 * @param connection
	 * @return  Xml字符串
	 */
	public String clientForPlatBus(String xmlMsg);
	
	/**
	 * 拼装报文头
	 * @param xmlMsg
	 * @return
	 */
	public String xmlFormatConver(String xmlMsg);

	/**
	 * 拼装报文头
	 * @param xmlMsg(互信)
	 * @return
	 */
	String xmlFormatConverForHX(String var1);
	
	/**
	 * 拼装报文头
	 * @param xmlMsg(车贷)
	 * @return
	 */
	String xmlFormatConverForCD(String var1);
	
	
	/**
	 * 发送交易至总线(自己组报文头)，发送解析报文不压缩
	 * @param url 总线wsdl地址
	 * @param nameSpace  名空间
	 * @param methodName 方法名
	 * @param xmlMsg  交易报文
	 * @param connection
	 * @return  Xml字符串
	 */
	public String clientForCallBus2(String url, String nameSpace, String methodName, String xmlMsg, Connection connection);

	
	/**
	 * 调用总线 Webservice
	 * @author liqs
	 * @param tradeCode 申请编号/保单号 + 接口名称
	 * @param tradeDesc 接口名称中文描述
	 * @param url 总线wsdl地址
	 * @param nameSpace 名空间
	 * @param methodName 方法名
	 * @param requestXML 请求报文
	 * @return
	 */
	public String clientForCallBusConfig(String tradeCode, String tradeDesc, String url, String nameSpace, String methodName, String requestXML, Connection connection);

	public String sendXmlPost(String xmlMsg,String url);

}