package com.jeeplus.modules.app.api.account.paychannel;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jeeplus.modules.app.api.account.paychannel.request.ReqPayChannelBankCardVO;
import com.jeeplus.modules.app.api.product.PropertiesUtil;
import com.jeeplus.modules.app.constant.AppCommonConstants;
import com.jeeplus.modules.app.utils.HttpConUtils;

import net.sf.json.JSONObject;

/**
 * 渠道接口 支付通道接口
 * 
 * @author 阳光保险
 *
 */
public class PayChannelBankCard {
	private final static Logger logger = LoggerFactory.getLogger(PayChannelBankCard.class);

	/**
	 * 渠道验证四要素接口申请接口
	 * 
	 * @param reqPayChannelBankCardVO
	 */
	public JSONObject payChannelBankCard(ReqPayChannelBankCardVO reqPayChannelBankCardVO) {
		JSONObject json = setPayChannelBankCardData(reqPayChannelBankCardVO);
		logger.info("渠道验证四要素接口申请接口请求参数" + json.toString());
		// 请求接口
		HttpConUtils httpConUtils = new HttpConUtils();
		String reulstjson = httpConUtils.doPostJSON(PropertiesUtil.getString("loanapiurl"), json);
		JSONObject jsonResult = JSONObject.fromObject(reulstjson);
		logger.info("渠道验证四要素接口申请接口返回" + jsonResult.toString());
		return jsonResult;
	}

	/**
	 * 签约确认接口
	 */
	public JSONObject payChannelBankCardSign(String signNo, String verifyCode) {
		JSONObject json = setPayChannelBankCardSignData(signNo, verifyCode);
		logger.info("签约确认接口请求参数" + json.toString());
		// 请求接口
		HttpConUtils httpConUtils = new HttpConUtils();
		String reulstjson = httpConUtils.doPostJSON(PropertiesUtil.getString("loanapiurl"), json);
		JSONObject jsonResult = JSONObject.fromObject(reulstjson);
		logger.info("签约确认接口返回" + jsonResult.toString());
		return jsonResult;
	}

	/**
	 * 渠道验证四要素查询接口
	 * 
	 * @param reqPayChannelBankCardVO
	 * @return
	 */

	public JSONObject payChannelBankCardQuery(ReqPayChannelBankCardVO reqPayChannelBankCardVO,String signNo) {
		JSONObject json = setPayChannelBankCardQueryData(reqPayChannelBankCardVO,signNo);
		logger.info("渠道验证四要素查询接口请求参数" + json.toString());
		// 请求接口
		HttpConUtils httpConUtils = new HttpConUtils();
		String reulstjson = httpConUtils.doPostJSON(PropertiesUtil.getString("loanapiurl"), json);
		JSONObject jsonResult = JSONObject.fromObject(reulstjson);
		logger.info("渠道验证四要素查询接口返回" + jsonResult.toString());
		return jsonResult;
	}

	/**
	 * 渠道重复发送短信接口
	 * 
	 * @param reqPayChannelBankCardVO
	 * @return
	 */

	public JSONObject payChannelBankCardverifyCode(String signNo) {
		JSONObject json = setPayChannelBankCardverifyCode(signNo);
		logger.info("渠道重复发送短信接口请求参数" + json.toString());
		// 请求接口
		HttpConUtils httpConUtils = new HttpConUtils();
		String reulstjson = httpConUtils.doPostJSON(PropertiesUtil.getString("loanapiurl"), json);
		JSONObject jsonResult = JSONObject.fromObject(reulstjson);
		logger.info("渠道重复发送短信接口返回" + jsonResult.toString());
		return jsonResult;
	}

	/**
	 * 渠道验证四要素申请接口请求数据
	 * 
	 * @param reqPayChannelBankCardVO
	 * @return
	 */
	public JSONObject setPayChannelBankCardData(ReqPayChannelBankCardVO reqPayChannelBankCardVO) {
		reqPayChannelBankCardVO.setIdType("0");
		reqPayChannelBankCardVO.setAcType("11");
		reqPayChannelBankCardVO.setBankCode(reqPayChannelBankCardVO.getBankCode());
		JSONObject json = new JSONObject();
		JSONObject jsonBank = JSONObject.fromObject(reqPayChannelBankCardVO);
		jsonBank.put("projNo", AppCommonConstants.getValStr("projNo"));
		jsonBank.put("brNo", "002");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHMMSS");
		String date = sdf.format(new Date());
		String patch = "SD" + date + random6();
		json.put("patch", patch);
		json.put("datasource", "PHFQ");
		json.put("trancode", "YG0056");
		json.put("tranbank", "FOTIC");
		json.put("trantime", date);
		json.put("data", jsonBank);
		return json;
	}

	/**
	 * 渠道验证四要素查询接口请求数据
	 * 
	 * @param reqPayChannelBankCardVO
	 * @return
	 */
	public JSONObject setPayChannelBankCardQueryData(ReqPayChannelBankCardVO reqPayChannelBankCardVO,String signNo) {
		reqPayChannelBankCardVO.setIdType("0");
		reqPayChannelBankCardVO.setBankCode(reqPayChannelBankCardVO.getBankCode());
		JSONObject json = new JSONObject();
		JSONObject jsonBank = JSONObject.fromObject(reqPayChannelBankCardVO);
		jsonBank.remove("acType");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHMMSS");
		String date = sdf.format(new Date());
		String patch = "SD" + date + random6();
		json.put("patch", patch);
		json.put("datasource", "PHFQ");
		json.put("trancode", "YG0059");
		json.put("tranbank", "FOTIC");
		json.put("trantime", date);
		json.put("signNo", signNo);
		json.put("data", jsonBank);
		return json;
	}

	/**
	 * 渠道验证四要素确定接口请求数据
	 * 
	 * @param signNo
	 * @param verifyCode
	 * @return
	 */

	public JSONObject setPayChannelBankCardSignData(String signNo, String verifyCode) {

		JSONObject json = new JSONObject();
		JSONObject jsonBank = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHMMSS");
		String date = sdf.format(new Date());
		String patch = "SD" + date + random6();
		json.put("patch", patch);
		json.put("datasource", "PHFQ");
		json.put("trancode", "YG0057");
		json.put("tranbank", "FOTIC");
		json.put("trantime", date);
		jsonBank.put("signNo", signNo);
		jsonBank.put("verifyCode", verifyCode);
		json.put("data", jsonBank);
		return json;
	}

	/**
	 * 渠道验证短信重发接口
	 * 
	 * @param signNo
	 * @return
	 */

	public JSONObject setPayChannelBankCardverifyCode(String signNo) {
		JSONObject json = new JSONObject();
		JSONObject jsonBank = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHMMSS");
		String date = sdf.format(new Date());
		String patch = "SD" + date + random6();
		json.put("patch", patch);
		json.put("datasource", "PHFQ");
		json.put("trancode", "YG0058");
		json.put("tranbank", "FOTIC");
		json.put("trantime", date);
		jsonBank.put("signNo", signNo);
		json.put("data", jsonBank);
		return json;
	}

	/**
	 * 转换成对应的银行编码
	 * 
	 * @param bankCode
	 * @return
	 */
//	public String bankCodeTran(String bankCode) {
//		if (bankCode.equals("CCB")) {
//			return "005";
//		} else if (bankCode.equals("ICBC")) {
//			return "002";
//		} else if (bankCode.equals("BOC")) {
//			return "004";
//		} else if (bankCode.equals("BCOM")) {
//			return "006";
//		} else if (bankCode.equals("CEB")) {
//			return "012";
//		} else if (bankCode.equals("POST")) {
//			return "015";
//		} else if (bankCode.equals("CGB")) {
//			return "019";
//		} else if (bankCode.equals("CIB")) {
//			return "011";
//		} else if (bankCode.equals("CITIC")) {
//			return "008";
//		} else if (bankCode.equals("SPDB")) {
//			return "009";
//		} else if (bankCode.equals("CMBC")) {
//			return "014";
//		}
//		return bankCode;
//	}
	/**
	 * 生成8位随机数
	 * @return
	 */
	public static int random6() {
		return (int)((Math.random()*9+1)*100000);
	}
	public static void main(String[] args) {
		PayChannelBankCard PayChannelBankCard = new PayChannelBankCard();
		ReqPayChannelBankCardVO ReqPayChannelBankCardVO = new ReqPayChannelBankCardVO();
		//签约申请例子
//		ReqPayChannelBankCardVO.setAcName("郭永立");
//		ReqPayChannelBankCardVO.setAcNo("6212261715000660532");
//		ReqPayChannelBankCardVO.setBankCode("ICBC");//银行编码
//		ReqPayChannelBankCardVO.setIdNo("412823198705021235");//
//		ReqPayChannelBankCardVO.setPhoneNo("15810556274");//手机号
//		ReqPayChannelBankCardVO.setCardChn("CL0001");//渠道 CL0001中金支付  CL0002广银联
//
//		JSONObject json1 = PayChannelBankCard.payChannelBankCard(ReqPayChannelBankCardVO);
//		System.out.println(json1.toString());
		//30000000000005010042
		//签约成功查询
		ReqPayChannelBankCardVO.setAcName("左瑞宝");
		ReqPayChannelBankCardVO.setAcNo("6217000010050835241");
		ReqPayChannelBankCardVO.setBankCode("CCB");//银行编码
		ReqPayChannelBankCardVO.setIdNo("131028198804222075");//
		ReqPayChannelBankCardVO.setPhoneNo("15313033398");//手机号
		JSONObject json2 =  PayChannelBankCard.payChannelBankCardQuery(ReqPayChannelBankCardVO,"30000000000005010144"); 
		System.out.println(json2.toString());
		//签约确认接口 
		/*JSONObject json3 =  PayChannelBankCard.payChannelBankCardSign("30000000000005010144", "111111");
        System.out.println(json3.toString());*/
        
		//短信重发接口
		/*JSONObject json4 =  PayChannelBankCard.payChannelBankCardverifyCode("30000000000005010144");
		System.out.println(json4.toString());*/
	}
}
