package com.jeeplus.modules.app.api.sms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.app.api.product.PropertiesUtil;
import com.jeeplus.modules.app.constant.AppNormalConstants;
import com.jeeplus.modules.app.entity.ClBnkCrd;
import com.jeeplus.modules.app.entity.ClIdCardInfo;
import com.jeeplus.modules.app.entity.ClUsrApply;


public class SmsCollectionUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(SmsCollectionUtil.class);
	
	private String url = PropertiesUtil.getString("smsurl");
	
	
	public String sendSms(String channelType, String smsPlatformType,String smsAccountType,String  msgId,String phones,String content,String sendTime,String organizationCode,String opType,String wapPushUrl) {
		if(StringUtils.isEmpty(channelType)) {
			channelType = "O";
		}
		if(StringUtils.isEmpty(smsPlatformType)) {
			smsPlatformType = "0";
		}
		if(StringUtils.isEmpty(smsAccountType)) {
			smsAccountType = "";
		}
		if(smsPlatformType.equals("1") && StringUtils.isEmpty(msgId)) {
			msgId = UUID.randomUUID().toString().replaceAll("-", "");
		}else {
			msgId = "";
		}
		if(smsPlatformType.equals("0") && StringUtils.isEmpty(organizationCode)) {
			organizationCode = "00060000";
		}else {
			organizationCode = "";
		}
		if(StringUtils.isEmpty(sendTime)) {
			sendTime = "";
		}
		if(StringUtils.isEmpty(organizationCode)) {
			organizationCode = "00060000";
		}
		if(StringUtils.isEmpty(wapPushUrl)) {
			wapPushUrl = "";
		}
		Map<String, String> params = new HashMap<String,String>();
		params.put("channelType", channelType);
		params.put("smsPlatformType", smsPlatformType);
		params.put("smsAccountType", smsAccountType);
		params.put("msgId", msgId);
		params.put("phones", phones);
		params.put("content", content);
		params.put("sendtime", sendTime);
		params.put("organizationCode", organizationCode);
		params.put("opType", opType);
		params.put("wapPushUrl", wapPushUrl);
		logger.info("发送短信:phones"+"="+phones+"&content="+content+"&sendTime="+sendTime+"&opType="+opType);
		String result = new HttpConUtils().doPost(url, params);
		logger.info("发送短信:phones="+phones+"&result="+result);
		return result;
	}
	
	
	public boolean sendSms(String phones,String content,String opType) {
		String result = sendSms("","","","",phones,content,"","",opType,"");
		JSONObject rj = JSONObject.parseObject(result);
		if("0".equals(rj.getString("result"))){
			return true;
		}
		return false;
	}
	
	//放款
	public String[] sendFk(ClIdCardInfo idCard, ClUsrApply usrApply, ClBnkCrd bankCard) {
		String name = idCard.getCUST_NAME();
		String amount = usrApply.getApplyAmt().toString();
		String bankName = bankCard.getAPPL_BANK_NAME();
		String bankNum = bankCard.getAPPL_CARD_NO();
		bankNum = bankNum.substring(bankNum.length()-4,bankNum.length());
		String hours = "2";
		
		String sex = idCard.getINDIV_SEX();
		String sendSex = getSendSex(sex);
		
		String[] params = {name,amount,bankName,bankNum,hours,sendSex};
		
		return params;
		
	}
	
	public boolean sendSms(String phones,int eventType,String[] params) {
		String content = getContentByConditonAndParams(eventType,params);
		if(StringUtils.isEmpty(content)) {
			return false;
		}
		String opString = getOpByConditon(eventType);
		if(StringUtils.isEmpty(opString)) {
			return false;
		}
		String result = sendSms("","","","",phones,content,"","",opString,"");
		JSONObject rj = JSONObject.parseObject(result);
		if("0".equals(rj.getString("result"))){
			return true;
		}
		return false;
	}
	
	
	public boolean sendSmsByTime(String phones,int eventType,String[] params,String time) {
		String content = getContentByConditonAndParams(eventType,params);
		if(StringUtils.isEmpty(content)) {
			return false;
		}
		String opString = getOpByConditon(eventType);
		if(StringUtils.isEmpty(opString)) {
			return false;
		}
		String result = sendSms("","","","",phones,content,time,"",opString,"");
		JSONObject rj = JSONObject.parseObject(result);
		if("0".equals(rj.getString("result"))){
			return true;
		}
		return false;
	}
	
	public String getOpByConditon(int eventType) {
		if(eventType == AppNormalConstants.LOGINORREGIST ) {
			String smsOptType = Global.getConfig("smsOptType");
			return smsOptType;
		} else if(eventType == AppNormalConstants.BINDBANKCARD ) {
			return "N02";
		} else if(eventType == AppNormalConstants.CREDIT_SUCC) {
			return "N03";
		} else if(eventType == AppNormalConstants.CREDIT_FAIL ) {
			return "N04";
		} else if(eventType == AppNormalConstants.LOAN ) {
			return "N05";
		} else if(eventType == AppNormalConstants.BILL ) {
			return "N06";
		} else if(eventType == AppNormalConstants.OVERDUE ) {
			return "N09";
		} else if(eventType == AppNormalConstants.CREDITREPORT_UPLD ) {
			return "N10";
		} else if(eventType == AppNormalConstants.BIND_BANKCARDREMINED) {
			return "N02";
		} else {
			return null;
		}
	}
	
	
	public String getContentByConditonAndParams(int eventType,String[] params) {
		if(eventType == AppNormalConstants.LOGINORREGIST && params.length == 1) {
			return "验证码为"+params[0]+"，请及时完成输入验证（切勿将验证码泄露给他人）。";
		} else if(eventType == AppNormalConstants.BINDBANKCARD && params.length == 1) {
			return "验证码为："+params[0]+"，该验证码用于绑定您的银行卡信息，5分钟内操作有效。如非本人操作请忽略。";
		} else if(eventType == AppNormalConstants.CREDIT_SUCC && params.length == 3) {
			return params[0]+params[2]+"，恭喜您通过审核，最高可借额度为"+params[1]+"元，快登录App提现吧~";
		} else if(eventType == AppNormalConstants.CREDIT_FAIL && params.length == 2) {
			return params[0]+params[1]+"，很遗憾，您本次的借款审核未通过。";
		} else if(eventType == AppNormalConstants.LOAN && params.length == 6) {
			return params[0]+params[5]+"，"+params[1]+"元借款已发放至您的"+params[2]+"卡（尾号"+params[3]+"）中，预计"+params[4]+"小时内到账，请注意查收。";
		} else if(eventType == AppNormalConstants.BILL && params.length == 6) {
			//return params[0]+params[5]+"，本期还款共"+params[1]+"元，银行将于"+params[2]+"当天14:00点开始自动扣款，请保持"+params[3]+"卡（尾号"+params[4]+"）内资金充足。";
			 return ""+params[0]+params[5]+"，"+"您尾号"+params[4]+"的"+""+params[3]+""+"银行卡本月应还款总金额"+params[1]+"元。请于"+params[2]+"日9:30前足额存入您的银行卡。您若未按时足额还款，可能会对您的征信记录产生不良影响。如您已还款，请忽略此短信。";
		} else if(eventType == AppNormalConstants.OVERDUE && params.length == 7) {
			return params[0]+params[6]+"，您已逾期"+params[1]+"天，应还总金额"+params[2]+"元（含"+params[3]+"元滞纳金），请保证您的"+params[4]+"卡（尾号"+params[5]+"）金额充足，尽快完成还款。";
		} else if(eventType == AppNormalConstants.CREDITREPORT_UPLD && params.length == 4) {
			return params[0]+params[3]+"，因您未在"+params[1]+"（当月还款日）缴纳当期月保费("+params[2]+")元。根据《征信业管理条例》规定，我司将您违约缴纳保费的相关信息报送央行征信数据库，特此告知。";
		} else if(eventType == AppNormalConstants.BIND_BANKCARDREMINED) {
			return "因合作金融机构支付渠道升级，为确保您的还款自动划扣操作正常，请您在还款日前打开APP重新绑定您原有的收款银行卡。感谢您的配合，如有问题，请致电400-151-5500咨询。";
		}else {
			return null;
		}
	}
	
	public String getSendSex(String sex) {
		sex = org.apache.commons.lang3.StringUtils.deleteWhitespace(sex);
		if("男".equals(sex)) {
			return "先生";
		} else if ("女".equals(sex)) {
			return "女士";
		} else {
			return "先生/女士";
		}
	}
	
}
