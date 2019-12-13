package com.jeeplus.modules.app.api.rulesengine;

import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.modules.app.api.pm.PMApi;
import com.jeeplus.modules.app.api.product.PropertiesUtil;
import com.jeeplus.modules.app.api.rulesengine.pojo.DecisionReq;

public class RulesEngineApi {
	
	private final static Logger logger = LoggerFactory.getLogger(RulesEngineApi.class);
	
	private HttpConUtils conn = new HttpConUtils();

	public boolean submit() {
		return true;
	}


	//放款决策
	public JSONObject applyDecision(DecisionReq decisionReq) {
		
		logger.info("决策引擎决策，参数"+JSONObject.toJSONString(decisionReq));
		String url = PropertiesUtil.getString("re.decision.url");
		
		String resp = conn.doPostJSON(url, decisionReq);
		logger.info("决策引擎决策，结果"+resp);
		JSONObject jsonObj = JSONObject.parseObject(resp);
		String code = jsonObj.getString("code");
		JSONObject data = jsonObj.getJSONObject("data");
		if(data == null) {
			return jsonObj;
		}
		String strategyCode = data.getString("strategyCode");
		String flowCode = data.getString("flowCode");
		String loanNumber = data.getString("loanNumber");
		String result = "";
		String time ="";
		String rejectCode = "";
		if("OK".equalsIgnoreCase(code)) {
			result = data.getString("result");
			time = jsonObj.getString("time");
			rejectCode = data.getString("rejectCode");
		}else if ("ERROR".equals(code)) {
			result = "ERROR";
			time = jsonObj.getString("time");
		}
		
		
		JSONObject respJson = new JSONObject();
		respJson.put("code", code);
		respJson.put("result", result);
		respJson.put("time", time);
		respJson.put("rejectCode", rejectCode);
		respJson.put("strategyCode", strategyCode);
		respJson.put("flowCode", flowCode);
		
		respJson.put("loanNumber", loanNumber);
		logger.info("决策引擎决策，处理后结果"+respJson.toJSONString());
		return respJson;
	}
	
	//获取衍生数据
	public String getDerivedData(String usercde,String id_no,String loan_number) {
		String url =  PropertiesUtil.getString("re.derived.url");
		JSONObject obj = new JSONObject();
		obj.put("user_id", usercde);
		obj.put("idNo", id_no);
		obj.put("loan_number", loan_number);
		
		logger.info("决策引擎衍生数据，参数"+obj.toJSONString());
		
		String resp = conn.doPostJSON(url, obj);
		logger.info("决策引擎衍生数据，结果"+resp);
		JSONObject jsonObj = JSONObject.parseObject(resp);
		return jsonObj.toJSONString();
	}
	
	
	public JSONObject getDerivedDataJSONObject(String usercde,String id_no,String loan_number) {
		String url = PropertiesUtil.getString("re.derived.url");
		JSONObject obj = new JSONObject();
		obj.put("user_id", usercde);
		obj.put("idNo", id_no);
		obj.put("loan_number", loan_number);
		
		logger.info("决策引擎衍生数据，参数"+obj.toJSONString());
		
		String resp = conn.doPostJSON(url, obj);
		logger.info("决策引擎衍生数据，结果"+resp);
		JSONObject jsonObj = JSONObject.parseObject(resp);
		return jsonObj;
	}
	
	//获取额度
	public JSONObject getLimit(String usrCde,String id_no) {
		String url = PropertiesUtil.getString("re.white.url");
		JSONObject reqJson = new JSONObject();
		JSONObject respJson = new JSONObject();
		reqJson.put("id_no", id_no);
		logger.info("决策引擎获取额度，参数"+reqJson);
		String resp = conn.doPostJSON(url, reqJson);
		logger.info("决策引擎获取额度，结果"+resp);
		JSONObject jsonObj = JSONObject.parseObject(resp);
		
		String code = jsonObj.getString("code");
		String time = jsonObj.getString("time");
		time = time.substring(0, time.indexOf("."));
		if("ok".equalsIgnoreCase(code)) {
			respJson.put("code", "ok");
			JSONObject data = jsonObj.getJSONObject("data");
			if(data.isEmpty()) {
				respJson.put("amount", "0.00");
				respJson.put("isWhiteList", "N");
			}else {
				String amount = data.getString("amount");
				respJson.put("isWhiteList", "Y");
				if(StringUtils.isEmpty(amount) || amount.equalsIgnoreCase("null")) {
					amount = "0.00";
				}
				respJson.put("amount", amount);
			}
		} else {
			respJson.put("code", "error");
		}
		respJson.put("time", time);
		
		return respJson;
	}
	
	
	public static void main(String[] args) {
		
//		DecisionReq od = new DecisionReq();
//		od.setStrategy_code("CL_CP_0005_00000016");
//		od.setApp_key("wd");
//		od.setPro_type("xd");//待定
//		od.setPrd_code("JZFQ");
//		od.setToken("");
//		od.setOrg_code("");
//		od.setChannel("FOTIC");
//		
//		od.setSession_id("");
//		od.setMessage_id("");
//		od.setUser_id("157903429618892800");
//		od.setName("李东亮");
//		od.setId_no("612322198710023036");
//		od.setLoan_number("201802071555025636103683e");
//		od.setMobile("18812345678");
//		od.setTime("2018-03-13 14:08:00.000");
//		od.setApp_version("1.0.0");
//		od.setOs_version("Android 4.4.4");
//		od.setSdk_version("");
//		
//		
//		
//		JSONObject obj = new JSONObject();
//		obj.put("idNo", "612322198710023036");
//		obj.put("idCardType", "0");
//		obj.put("name", "李东亮");
//		obj.put("mobile", "18812345678");
//		obj.put("bankCardNo", "6217720000111122222");
//		obj.put("contactName", "黄明乾");
//		obj.put("contactIdNumber", "");
//		obj.put("contactMobile", "13171365879");
//		obj.put("contactRelation", "coworker");
		
//		obj.put("appl_seq", "282756");
//		obj.put("process_type", "01");
		
//		od.setOther(obj);
		
//		System.out.println(new RulesEngineApi().applyDecision(od));
		
		
//		String useCde = "157903429618892800";
		String useCde = "157903asdasqiwjeqwe";
//		String id_no = "612322198710023036";
		String id_no = "100100198901130123";
//		String loan_number = "20180207155540685153f96e";
		String loan_number = "WD2018031315460910456Y";
//		
//		
//		
//		System.out.println(new RulesEngineApi().getDerivedData(useCde, id_no, loan_number));

		System.out.println(new RulesEngineApi().getLimit("", id_no));
	}
}
