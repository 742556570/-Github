package com.jeeplus.modules.app.pojo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jeeplus.modules.app.entity.ClPrdInfo;
import com.jeeplus.modules.app.service.ClApplyService;

public class ResponeJsonObj {
	
	private final static Logger logger = LoggerFactory.getLogger(ResponeJsonObj.class); 
	
	public static final String FAIL = "000";
	public static final String IDCARDEXISTS = "002";
	public static final String OVERALLCNT = "003";
	public static final String OVERDAYCNT = "004";
	public static final String SUCCESS = "100";
	public static final String PROCESSING = "102";
	public static final String PROCESSED = "103";
	public static final String UNLOGIN = "996";
	public static final String CloseLoanService = "333";
	
	public static final String MulitSubmit = "666";

	public static final String EXCEPTION = "900";
	public static final String OUTOFTIME = "901";
	public static final String WEIHU = "997";
	
	
	public static final String[] skipFields = { "remarks", "createBy", "createDate", "updateBy", "updateDate",
			"delFlag", "id", "currentUser", "page", "sqlMap", "isNewRecord" };

	public static ExclusionStrategy myExclusionStrategy = new ExclusionStrategy() {

		@Override
		public boolean shouldSkipClass(Class<?> clazz) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean shouldSkipField(FieldAttributes fieldAttr) {
			// TODO Auto-generated method stub
			String fieldAttrName = fieldAttr.getName();
			for (String string : skipFields) {
				if (fieldAttrName.equals(string)) {
					return true;
				}
			}
			return false;
		}

	};

	public static Gson gson = new GsonBuilder().setExclusionStrategies(myExclusionStrategy).create();

	public static String getResJsonObj(String status, String msg, JsonObject data) {
		
		if(data == null) {
			data = new JsonObject();
		}
		JsonObject resultObj = new JsonObject();
		resultObj.addProperty("status", status);
		resultObj.addProperty("msg", msg);
		resultObj.add("responseData", data);
		return gson.toJson(resultObj);
	}

	public static String getResJsonObj(String status, String msg, Object data) {
		if(data == null) {
			data = new Object();
		}
		JsonObject resultObj = new JsonObject();
		String dataJsonStr = gson.toJson(data);
		JsonObject responseData = gson.fromJson(dataJsonStr, JsonObject.class);
		resultObj.addProperty("status", status);
		resultObj.addProperty("msg", msg);
		resultObj.add("responseData", responseData);
		String result = gson.toJson(resultObj);
		logger.info("result:"+result);
		return result;
	}

	public static String getResJsonObjFromArray(String status, String msg, List<?> data) {
		if(data == null) {
			data = new ArrayList();
		}
		JsonObject resultObj = new JsonObject();
		String dataJsonStr = gson.toJson(data);
		JsonArray responseData = gson.fromJson(dataJsonStr, JsonArray.class);
		resultObj.addProperty("status", status);
		resultObj.addProperty("msg", msg);
		resultObj.add("responseData", responseData);
		String result = gson.toJson(resultObj);
		logger.info("result:"+result);
		return result;
	}

	
	public static void main(String[] args) {
		System.out.println(getResJsonObj(SUCCESS,"提交授信申请成功",null));
		System.out.println(getResJsonObj(FAIL,"提交授信申请失败",new ClPrdInfo()));
		
		
		System.out.println((new ClPrdInfo()));
		
	}
}
