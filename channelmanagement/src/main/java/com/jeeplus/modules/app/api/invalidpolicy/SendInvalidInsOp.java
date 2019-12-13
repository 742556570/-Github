package com.jeeplus.modules.app.api.invalidpolicy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
//import com.ecc.emp.core.EMPConstance;
//import com.ecc.emp.log.EMPLog;
import com.jeeplus.modules.app.api.invalidpolicy.util.BusServiceImple;
import com.jeeplus.modules.app.api.invalidpolicy.util.BusServiceInterface;
import com.jeeplus.modules.app.api.invalidpolicy.util.XmlUtil;
import com.jeeplus.modules.app.api.product.PropertiesUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <p>
 * <h2>简述</h2>
 * 		<ol>往核心发送无效保单接口，直接对接总线</ol>
 * <h2>功能描述</h2>
 * 		<ol>通知放款申请时，先发送信息到总线，再到核心</ol>
 * <h2>修改历史</h2>
 *    <ol>如有修改，添加修改历史</ol>
 * </p>
 * @author 作者liangyl
 * @2016-8-2 11:29:53
 * @version 1.0
 */
public class SendInvalidInsOp  {

	private static final Logger logger = LoggerFactory.getLogger(SendInvalidInsOp.class);
     /**
	 * 调用总线保单信息登记接口
	 * 核心系统 
	 * @param requestMap 里面需要包含参数appl_seq 表示哪笔数据进行保单信息的登记
	 * @return
     * @throws AsynException  
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	public JSONObject inputPolicy(String reqXmlStr) throws Exception{
		Map resMap = new HashMap();
		
		String groupXML = "";
		String respXML = "" ;

		//初始化接口组件
		try {
//			CMISModuleServiceFactory sefig = CMISModuleServiceFactory.getInstance();
//			sefig.init();
			BusServiceInterface acface= new BusServiceImple();
			String url = PropertiesUtil.getString("invalid_policy.url");
			String nameSpace = PropertiesUtil.getString("invalid_policy.namespace");
			
			//组装发送报文
//			if(lcApplCont.getIosNo().startsWith("C")){ //C开头是信贷的单子
//			reqXmlStr = acface.xmlFormatConver(groupXML);
//			}else if(lcApplCont.getIosNo().startsWith("D")){//D是车贷的单子
//				reqXmlStr = acface.xmlFormatConverForCD(groupXML);
//			}
			logger.info("发送无效保单信息请求地址"+url);
			logger.info("发送无效保单信息发送报文"+reqXmlStr);

			//获取返回报文
			respXML = acface.clientForCallBus(url,nameSpace,"transPolicy",reqXmlStr,null);
			//截取需要的报文字符，掉由于压缩产生的额外字节
			respXML = respXML.substring(respXML.indexOf("<"), respXML.lastIndexOf(">")+1);
			
			logger.info( "发送无效保单接口返回报文"+respXML);
			
			XmlUtil xml = new XmlUtil();
			Map respMap = new HashMap<String, Object>();
			Map repMap = new HashMap<String, Object>();
			Map resPoliGrMap = new HashMap<String, Object>();
			Map resPolicyMap = new HashMap<String, Object>();
			
			//返回报文换成map
			respMap = xml.xmlToMap(respXML, "");
			
			//截取返回报文map中的状态和错误原因
			repMap = (Map) respMap.get("TransPolicyResponse");
			resPoliGrMap = (Map) repMap.get("policyGroup");
			resPolicyMap = (Map) resPoliGrMap.get("policy");
			String isSucc = (String) resPolicyMap.get("isSucc");//获取返回报文成功状态
			if("true".equals(isSucc)){//成功
				resMap.put("clientFlag","success");
			}else{//失败
				String failMessage = (String) resPolicyMap.get("failCause");
				resMap.put("clientFlag", "fail");
				resMap.put("errorMsg", failMessage);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error( "发送无效保单接口失败:\n"+e.getMessage());
		}
		System.out.println(resMap);
		return JSONObject.parseObject(new Gson().toJson(resMap));
	}
	
	public String insertLogInfo(){
		
		
		return null;
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
	
	public static void main(String[] args) throws Exception {
		SendInvalidInsOp op = new SendInvalidInsOp();
		System.out.println("------------>"+op.inputPolicy(TestString.xml));
	}
}