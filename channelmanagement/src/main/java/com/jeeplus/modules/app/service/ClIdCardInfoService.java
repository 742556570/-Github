package com.jeeplus.modules.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.app.api.rulesengine.RulesEngineApi;
import com.jeeplus.modules.app.dao.ClIdCardInfoDao;
import com.jeeplus.modules.app.dao.ClPrdInfoDao;
import com.jeeplus.modules.app.entity.ClIdCardInfo;
import com.jeeplus.modules.app.entity.ClPrdInfo;
import com.jeeplus.modules.app.pojo.ResponeJsonObj;

@Service
@Transactional(readOnly = false)
public class ClIdCardInfoService extends CrudService<ClIdCardInfoDao, ClIdCardInfo> {

	@Autowired
	private ClIdCardInfoDao clIdCardInfoDao;
	
	public ClIdCardInfo getByUsrIdNo(String ID_NO) {
		
		return clIdCardInfoDao.getByIdNo(ID_NO);
		
	}

	
	public String getByUsrCodeJsonObj(String USR_CDE){
		ClIdCardInfo list = clIdCardInfoDao.getByUsrCode(USR_CDE);
		if(list == null ) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "未查询到结果", null); 
		}else {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "查询成功", list);
		}
	} 
	
	public ClIdCardInfo getByUsrCode(String USR_CDE){
		ClIdCardInfo list = clIdCardInfoDao.getByUsrCode(USR_CDE);
		return list;
	}
	
	
	public ClIdCardInfo getByUsrCodeAndValidate(String USR_CDE) {
		ClIdCardInfo idCardInfo = clIdCardInfoDao.getValidByUsrCode(USR_CDE);
		return idCardInfo;
	}
	
	
	public String getByUsrCodeAndValidateJsonObj(String USR_CDE) {
		ClIdCardInfo idCardInfo = clIdCardInfoDao.getValidByUsrCode(USR_CDE);
		if(idCardInfo == null) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "未查询到结果", null);
		}else {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "查询成功", idCardInfo);
		}
	}
	
	public synchronized String saveUsrIdCardInfoJsonObj(ClIdCardInfo idCardInfo,JSONObject prd) {
		
		int i = clIdCardInfoDao.cntByUsrCode(idCardInfo.getUSR_CDE());
		
		if(i > 0) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "此身份证已被认证，请使用认证时的手机号登陆", null);
		}else {
			ClIdCardInfo tmp = clIdCardInfoDao.getByIdNo(idCardInfo.getID_NO());
			if(tmp != null) {
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.IDCARDEXISTS, "此身份证已被认证，请使用认证时的手机号登陆", null);
			}
			i = clIdCardInfoDao.insert(idCardInfo);
			if(i > 0) {
				HashMap<String, Object> map = new HashMap<String,Object>();
				idCardInfo.setBACK_IMGPTH_LC("");
				idCardInfo.setFORNT_IMGPTH_LC("");
				map.put("idCardInfo", idCardInfo);
				map.put("prd", prd);
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "保存成功", map);
			}else {
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "保存失败", null);
			}
		}
		
	}
	
	public boolean saveUsrIdCardInfo(ClIdCardInfo idCardInfo) {
		
		int i = clIdCardInfoDao.cntByUsrCode(idCardInfo.getUSR_CDE());
		
		if(i > 0) {
			return true;
		}else {
			return false;
		}
		
	}
	
	
	
}
