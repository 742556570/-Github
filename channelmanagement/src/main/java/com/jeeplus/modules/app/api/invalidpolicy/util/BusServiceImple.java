package com.jeeplus.modules.app.api.invalidpolicy.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.xml.namespace.QName;

import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * <p>
 * <h2>简述</h2>
 * 		<ol>客户端服务接口实现类:总线服务接口实现类</ol>
 * <h2>功能描述</h2>
 * 		<ol>请添加功能详细的描述</ol>
 * <h2>修改历史</h2>
 *    <ol>如有修改，添加修改历史</ol>
 * </p>
 * @author XuQZ
 * @2016-7-27 14:55:54
 * @version 1.0
 */
public class BusServiceImple extends CMISModuleService
 implements BusServiceInterface{
	
	private static final Logger logger = LoggerFactory.getLogger(BusServiceImple.class);

	@Override
	public String sendMsgToBus(String url, String nameSpace, String methodName,
			String xmlMsg,Connection connection) {
		// TODO Auto-generated method stub
		String reqXmlStr="";//请求报文
		String repXmlStr="";//响应报文
		//1.将Map转换成Xml
		
		//2.按总线报文格式拼装调用总线的报文
		reqXmlStr=xmlFormatConver(xmlMsg);
		//3.调用webservice发送报文
		repXmlStr = clientForCallBus(url,nameSpace,methodName,reqXmlStr,connection);
		//4.截取需要的报文字符，掉由于压缩产生的额外字节
		repXmlStr = repXmlStr.substring(repXmlStr.indexOf("<"), repXmlStr.lastIndexOf(">")+1);
		//5.返回报文字符串
		return repXmlStr;
	}
	
	/**
	 * 按拼装总线报文格式拼装报文
	 * @param sendMsg
	 * @return
	 */
	public String xmlFormatConver(String sendMsg) {

		StringBuilder sb = new StringBuilder();
		
		// 根据总线要求，获得uuid
		UUID uuid = UUID.randomUUID();
		String uid = uuid.toString().replaceAll("-", "");
		
		// 获取交易时间
		Date date = new Date();
		java.text.DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String dates = format.format(date).replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
		
		//截取掉自动组装生成的xml版本以及编码信息，如：<?xml version='1.0' encoding='utf-8'?>
		sendMsg=sendMsg.substring(sendMsg.indexOf("?>")+2);
		// 按照总线的格式要求，需要把报文按照总线的格式要求拼接
		
		sb.append("<?xml version='1.0' encoding='GBK'?>"
				+ "<TransPolicyRequest>" + // 总线要求xml格式中的root节点
				"<modeFlag>5</modeFlag>" + // 标志，固定值，由总线提供
				"<operateType>INPUT</operateType>" + // 操作类型，固定值，由总线提供
				"<policyCount>1</policyCount>" + // 需要跟总线确认
				"<sendName>xdxbpt</sendName>" + // 用户名，固定值：由总线提供
				"<sendPwd>xdxbpt</sendPwd>" + // 密码，固定值：由总线提供
				"<sendSeq>" + uid + "</sendSeq>" + // 标识符，使用java生成uuid
				"<sendTime>" + dates + "</sendTime>" + // 发送时间，请求接口时间
				"<sysFlag>XDXBPT</sysFlag>");// + // 系统标志，固定值，由总线提供
				//"<policyGroup>");
		// 把自己生成的报文拼装进去
		sb.append(sendMsg);
		//拼装总线要求的报文尾
		//sb.append("</policyGroup></TransPolicyRequest>");
		sb.append("</TransPolicyRequest>");
		
		return sb.toString();
	}


	/**
	 * 按拼装总线报文格式拼装报文(互信)
	 * @param sendMsg
	 * @return
	 */
	public String xmlFormatConverForHX(String sendMsg) {

		StringBuilder sb = new StringBuilder();

		// 根据总线要求，获得uuid
		UUID uuid = UUID.randomUUID();
		String uid = uuid.toString().replaceAll("-", "");

		// 获取交易时间
		Date date = new Date();
		java.text.DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String dates = format.format(date).replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");

		//截取掉自动组装生成的xml版本以及编码信息，如：<?xml version='1.0' encoding='utf-8'?>
		sendMsg=sendMsg.substring(sendMsg.indexOf("?>")+2);
		// 按照总线的格式要求，需要把报文按照总线的格式要求拼接

		sb.append("<?xml version=\'1.0\' encoding=\'GBK\'?>"
				+ "<TransPolicyRequest>" + // 总线要求xml格式中的root节点
				"<modeFlag>5</modeFlag>" + // 标志，固定值，由总线提供
				"<operateType>INPUT</operateType>" + // 操作类型，固定值，由总线提供
				"<policyCount>1</policyCount>" + // 需要跟总线确认
				"<sendName>xdxbpt</sendName>" + // 用户名，固定值：由总线提供
				"<sendPwd>xdxbpt</sendPwd>" + // 密码，固定值：由总线提供
				"<sendSeq>" + uid + "</sendSeq>" + // 标识符，使用java生成uuid
				"<sendTime>" + dates + "</sendTime>" + // 发送时间，请求接口时间
				"<sysFlag>XDXBPT</sysFlag>");// + // 系统标志，固定值，由总线提供
		//"<policyGroup>");
		// 把自己生成的报文拼装进去
		sb.append(sendMsg);
		//拼装总线要求的报文尾
		//sb.append("</policyGroup></TransPolicyRequest>");
		sb.append("</TransPolicyRequest>");

		return sb.toString();
	}

	/**
	 * 按拼装总线报文格式拼装报文(互信)
	 * @param sendMsg
	 * @return
	 */
	public String xmlFormatConverForCD(String sendMsg) {

		StringBuilder sb = new StringBuilder();

		// 根据总线要求，获得uuid
		UUID uuid = UUID.randomUUID();
		String uid = uuid.toString().replaceAll("-", "");

		// 获取交易时间
		Date date = new Date();
		java.text.DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String dates = format.format(date).replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");

		//截取掉自动组装生成的xml版本以及编码信息，如：<?xml version='1.0' encoding='utf-8'?>
		sendMsg=sendMsg.substring(sendMsg.indexOf("?>")+2);
		// 按照总线的格式要求，需要把报文按照总线的格式要求拼接

		sb.append("<?xml version=\'1.0\' encoding=\'GBK\'?>"
				+ "<TransPolicyRequest>" + // 总线要求xml格式中的root节点
				"<modeFlag>37</modeFlag>" + // 标志，固定值，由总线提供
				"<operateType>INPUT</operateType>" + // 操作类型，固定值，由总线提供
				"<policyCount>1</policyCount>" + // 需要跟总线确认
				"<sendName>XDXB</sendName>" + // 用户名，固定值：由总线提供
				"<sendPwd>123</sendPwd>" + // 密码，固定值：由总线提供
				"<sendSeq>" + uid + "</sendSeq>" + // 标识符，使用java生成uuid
				"<sendTime>" + dates + "</sendTime>" + // 发送时间，请求接口时间
				"<sysFlag>CD</sysFlag>");// + // 系统标志，固定值，由总线提供
		//"<policyGroup>");
		// 把自己生成的报文拼装进去
		sb.append(sendMsg);
		//拼装总线要求的报文尾
		//sb.append("</policyGroup></TransPolicyRequest>");
		sb.append("</TransPolicyRequest>");

		return sb.toString();
	}
	/**
	 * 调用总线Webservice
	 * @author XuQZ
	 * @param url 总线wsdl地址
	 * @param nameSpace 名空间
	 * @param methodName 方法名
	 * @param requestXML 请求报文
	 * @return
	 */
	public String clientForCallBus(String url,String nameSpace,String methodName,String requestXML,Connection connection){
		byte[] returnBytes = null;
		String response = "";//返回的报文
		RPCServiceClient serviceClient = null; 
		try {
			serviceClient = new RPCServiceClient(); 
			byte[] buf = requestXML.getBytes("GBK"); 
			Options options = serviceClient.getOptions(); 
			EndpointReference targetEPR = new EndpointReference(url);
			options.setTo(targetEPR);
			//options.setAction(nameSpace+methodName);
			options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
			options.setTimeOutInMilliSeconds(3 * 60 * 1000);// 等待时间
			Object[] opAddEntryArgs = new Object[] { ZipUtil.compress(buf) };// 调用方法入参
			Class[] classes = new Class[] { byte[].class };// 调用方法返回结果的类型
			QName opAddEntry = new QName(nameSpace, methodName);//transPolicy 调用方法名称救援单证新接口请求方法transDate
			returnBytes = (byte[]) serviceClient.invokeBlocking(opAddEntry,opAddEntryArgs, classes)[0];
			//解决Timeout waiting for connection
			serviceClient.cleanupTransport();
			response =  new String(ZipUtil.decompress(returnBytes),"GBK");
			
			//向接口交易记录表中插入请求交易记录
		} catch (Exception e) {
			//向接口交易记录表中插入请求交易记录
//			StringWriter sw = new StringWriter(); 
//            e.printStackTrace(new PrintWriter(sw, true)); 
//            String str = sw.toString(); 
            System.out.println("与总线交互时发生异常，详情为:");
            e.printStackTrace();
    		//向接口交易记录表中插入请求交易记录
		}
		return response;
	}
	
	/**
	 * 调用平台servlet
	 * @author XuQZ
	 * @param requestXML 请求报文
	 * @return
	 */
	public String clientForPlatBus(String requestXML){
		HttpURLConnection con=null;
		BufferedReader in=null;
		String resultJsonStr = "";
		try {
			
			
			byte[] data = requestXML.getBytes("UTF-8");
			String newData=new String(data,"UTF-8");	
			//String data = requestXML;
			String platServlet = CMISPropertyManager.getInstance().getPropertyValue("PTBchCdeUrl");
			URL httpUrl = new URL(platServlet);
			con = (HttpURLConnection) httpUrl.openConnection();
			con.setRequestMethod("POST");
			//con.setRequestProperty("Content-Type",   "multipart/form-data; charset=GBK; ");
			con.setDoOutput(true);
			con.setDoInput(true);
			PrintWriter out = new PrintWriter(con.getOutputStream());
			out.println(newData);
			out.flush();
			out.close();
			int responseCode = con.getResponseCode();
			logger.error("平台返回信息:"+responseCode);
			
			if(responseCode== HttpURLConnection.HTTP_OK){
				in = new BufferedReader(new InputStreamReader(
						con.getInputStream(), "utf-8"));
				String line;
				while ((line = in.readLine()) != null) {
					resultJsonStr += line;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return resultJsonStr;
		
	}
	
	/**
	 * 调用总线Webservice,发送解析报文都不压缩
	 * @author XuQZ
	 * @param url 总线wsdl地址
	 * @param nameSpace 名空间
	 * @param methodName 方法名
	 * @param requestXML 请求报文
	 * @return
	 */
	public String clientForCallBus2(String url,String nameSpace,String methodName,String requestXML,Connection connection){
		byte[] returnBytes = null;
		String response = "";//返回的报文
		RPCServiceClient serviceClient = null; 
		try {
			serviceClient = new RPCServiceClient(); 
			byte[] buf = requestXML.getBytes(); 
			Options options = serviceClient.getOptions(); 
			EndpointReference targetEPR = new EndpointReference(url);
			options.setTo(targetEPR);
			//options.setAction(nameSpace+methodName);
			options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
			options.setTimeOutInMilliSeconds(2 * 60 * 1000);// 等待时间
			//Object[] opAddEntryArgs = new Object[] { ZipUtil.compress(buf) };// 调用方法入参
			Object[] opAddEntryArgs = new Object[] { buf };
			Class[] classes = new Class[] { byte[].class };// 调用方法返回结果的类型
			QName opAddEntry = new QName(nameSpace, methodName);//transPolicy 调用方法名称救援单证新接口请求方法transDate
			returnBytes = (byte[]) serviceClient.invokeBlocking(opAddEntry,opAddEntryArgs, classes)[0];	
			//response =  new String(ZipUtil.decompress(returnBytes),"GBK");
			response =  new String(returnBytes,"GBK");
			
		} catch (Exception e) {
			//向接口交易记录表中插入请求交易记录
			/*StringWriter sw = new StringWriter(); 
            e.printStackTrace(new PrintWriter(sw, true)); 
            String str = sw.toString(); 
            System.out.println("与总线交互时发生异常，详情为:"+str);*/
            e.printStackTrace();
    		//向接口交易记录表中插入请求交易记录
		}
		return response;
	}
	
	
	/**
	 * 调用总线Webservice
	 * @author XuQZ
	 * @param url 总线wsdl地址
	 * @param nameSpace 名空间
	 * @param methodName 方法名
	 * @param requestXML 请求报文
	 * @return
	 */
	public String clientForCallBusConfig(String tradeCode,String tradeDesc, String url,String nameSpace,String methodName,String requestXML,Connection connection){
		byte[] returnBytes = null;
		String response = "";//返回的报文
		RPCServiceClient serviceClient = null; 
		try {
			serviceClient = new RPCServiceClient(); 
			byte[] buf = requestXML.getBytes("GBK"); 
			Options options = serviceClient.getOptions(); 
			EndpointReference targetEPR = new EndpointReference(url);
			options.setTo(targetEPR);
			//options.setAction(nameSpace+methodName);
			options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
			options.setTimeOutInMilliSeconds(3 * 60 * 1000);// 等待时间
			Object[] opAddEntryArgs = new Object[] { ZipUtil.compress(buf) };// 调用方法入参
			Class[] classes = new Class[] { byte[].class };// 调用方法返回结果的类型
			QName opAddEntry = new QName(nameSpace, methodName);//transPolicy 调用方法名称救援单证新接口请求方法transDate
			returnBytes = (byte[]) serviceClient.invokeBlocking(opAddEntry,opAddEntryArgs, classes)[0];	
			response =  new String(ZipUtil.decompress(returnBytes),"GBK");
			
			//向接口交易记录表中插入请求交易记录
		} catch (Exception e) {
			//向接口交易记录表中插入请求交易记录
			/*StringWriter sw = new StringWriter(); 
            e.printStackTrace(new PrintWriter(sw, true)); 
            String str = sw.toString(); 
            System.out.println("与总线交互时发生异常，详情为:"+str);*/
            e.printStackTrace();
    		//向接口交易记录表中插入请求交易记录
		}
		return response;
	}

	public String sendXmlPost(String requestXML,String url){
		HttpURLConnection con=null;
		BufferedReader in=null;
		String resultJsonStr = "";
		try {
			byte[] data = requestXML.getBytes("UTF-8");
			String newData=new String(data,"UTF-8");
			URL httpUrl = new URL(url);
			con = (HttpURLConnection) httpUrl.openConnection();
			con.setRequestMethod("POST");
			//con.setRequestProperty("Content-Type",   "multipart/form-data; charset=GBK; ");
			con.setDoOutput(true);
			con.setDoInput(true);
			PrintWriter out = new PrintWriter(con.getOutputStream());
			out.println(newData);
			out.flush();
			out.close();
			int responseCode = con.getResponseCode();
			logger.error("返回信息:"+responseCode);

			if(responseCode== HttpURLConnection.HTTP_OK){
				in = new BufferedReader(new InputStreamReader(
						con.getInputStream(), "utf-8"));
				String line;
				while ((line = in.readLine()) != null) {
					resultJsonStr += line;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return resultJsonStr;

	}
	
}