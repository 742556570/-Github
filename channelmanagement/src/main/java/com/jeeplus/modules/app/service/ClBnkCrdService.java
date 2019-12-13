package com.jeeplus.modules.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.app.api.sms.SmsCollectionUtil;
import com.jeeplus.modules.app.constant.AppCommonConstants;
import com.jeeplus.modules.app.constant.AppNormalConstants;
import com.jeeplus.modules.app.dao.ClBnkCrdDao;
import com.jeeplus.modules.app.dao.ClUsrInfoDao;
import com.jeeplus.modules.app.entity.ClBnkCrd;
import com.jeeplus.modules.app.entity.ClUsrInfo;
import com.jeeplus.modules.app.pojo.ResponeJsonObj;
import com.jeeplus.modules.app.utils.CacheHelper;

@Service
@Transactional(readOnly = false)
public class ClBnkCrdService extends CrudService<ClBnkCrdDao, ClBnkCrd> {

	@Autowired
	private ClBnkCrdDao clBnkCrdDao;

	public String addBnkCrdJsonObj(ClBnkCrd clBnkCrd) {

		
		int i = clBnkCrdDao.insert(clBnkCrd);
		if (i > 0) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "保存成功", null);
		} else {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "保存失败", null);
		}
	}
	
	public boolean addBnkCrdBoolean(ClBnkCrd clBnkCrd) {

		if(isUsrExists(clBnkCrd.getUSR_CDE())) {
			return false;
		}
		int i = clBnkCrdDao.insert(clBnkCrd);
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean addBnkCrd(ClBnkCrd clBnkCrd) {

		if(isUsrBnkCrdExists(clBnkCrd.getUSR_CDE(),clBnkCrd.getAPPL_CARD_NO())) {
			return false;
		}
		int i = clBnkCrdDao.insert(clBnkCrd);
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isUsrBnkCrdExists(String usrCde,String cardNum) {
		HashMap<String, String> params = new HashMap<String,String>();
		params.put("USR_CDE", usrCde);
		params.put("APPL_CARD_NO",cardNum);
		int i = clBnkCrdDao.cntByUsrCodeAndCardNum(params);
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isUsrExists(String usrCde) {
		int i = clBnkCrdDao.cntByUsrCode(usrCde);
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public String getUsrBnkCrdJsonObj(String usrCde) {
		List<ClBnkCrd> bnkCrd = clBnkCrdDao.getByUsrCode(usrCde);
		
		if(bnkCrd == null || bnkCrd.size() == 0) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "未查得", null);
		}else {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "查询成功", bnkCrd);
		}
		
	}
	
//	public ClBnkCrd getUsrBnkCrd(String usrCde) {
//		return clBnkCrdDao.getOneByUsrCode(usrCde);
//	}
	
	public ClBnkCrd getUsrDefaultBnkCrd(String usrCde) {
		return clBnkCrdDao.getUsrDefaultCardByUsrCode(usrCde);
	}
	
	public ClBnkCrd getUsrDefaultBnkCrdWithOutSts(String usrCde) {
		return clBnkCrdDao.getUsrDefaultBnkCrdWithOutSts(usrCde);
	}
	
	public ClBnkCrd getUsrOldCardByUsrCode(String usrCde) {
		return clBnkCrdDao.getUsrOldCardByUsrCode(usrCde);
	}
	
	public String getUsrBnkCrd(String usrCde ,String crdNum) {
		JsonObject jsObj = new JsonObject();
		HashMap<String, String> params = new HashMap<String,String>();
		params.put("USR_CDE", usrCde);
		params.put("APPL_CARD_NO",crdNum);
		ClBnkCrd usrInfo = clBnkCrdDao.getByUsrCodeAndCardNum(params);
		Gson gson = new Gson();
		String jsonStr = gson.toJson(usrInfo);
		jsObj = gson.fromJson(jsonStr, JsonObject.class);
		return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "查询成功", jsObj);
	}
	
	public ClBnkCrd getBnkCrdByUsrAndCrdNum(String usrCde ,String crdNum) {
		JsonObject jsObj = new JsonObject();
		HashMap<String, String> params = new HashMap<String,String>();
		params.put("USR_CDE", usrCde);
		params.put("APPL_CARD_NO",crdNum);
		ClBnkCrd bnkCrd = clBnkCrdDao.getByUsrCodeAndCardNum(params);
		return bnkCrd;
	}
	
	
	public int sendBnkCrd(String usrTel) {
		String isOnline = Global.getConfig("isOnline");
		if(!"false".equals(isOnline)) {
			Random rnd = new Random();
			int num = rnd.nextInt(899999) + 100000;
			String[] params = {num+""};
			boolean sendResult = new SmsCollectionUtil().sendSms(usrTel, AppNormalConstants.BINDBANKCARD, params);
			if (sendResult) {
				CacheHelper.setObject(AppCommonConstants.getValStr("CACHESCHEMA"), usrTel+AppCommonConstants.getValStr("CACHETYPESUFFIX_VALIBNKCRD"), num, AppCommonConstants.getValInt("CACHEVALIBNKCRDTTL"));
				return num;
			} else {
				return -1;
			}
		}else {
			Random rnd = new Random();
			int num = 123456;//rnd.nextInt(8999) + 1000;
			String[] params = {num+""};
			boolean sendResult = true;//new SmsCollectionUtil().sendSms(usrTel, AppNormalConstants.BINDBANKCARD, params);
			if (sendResult) {
				CacheHelper.setObject(AppCommonConstants.getValStr("CACHESCHEMA"), usrTel+AppCommonConstants.getValStr("CACHETYPESUFFIX_VALIBNKCRD"), num, AppCommonConstants.getValInt("CACHEVALIBNKCRDTTL"));
				return num;
			} else {
				return -1;
			}
		}		
	}
	
	public String getCachedBnkCrdPhone(String usrTel) {
		String result = "";
		Object obj = CacheHelper.getObject(AppCommonConstants.getValStr("CACHESCHEMA"), usrTel+AppCommonConstants.getValStr("CACHETYPESUFFIX_VALIBNKCRD"));
		if(obj == null) {
			result = null;
		}else {
			result = obj.toString();
		}
		return result;
	}
	
	public void rmvCachedBnkCrdPhone(String usrTel) {
		CacheHelper.removeObject(AppCommonConstants.getValStr("CACHESCHEMA"), usrTel+AppCommonConstants.getValStr("CACHETYPESUFFIX_VALIBNKCRD"));
	}
	
	public int countFailSmsCde(String usrTel) {
		int i = 0;
		Object obj = CacheHelper.getObject(AppCommonConstants.getValStr("CACHESCHEMA"), usrTel+"countBankSmsFail");
		if(obj == null) {
			i = 0;
		}else {
			i = (int)obj;
		}
		i++;
		CacheHelper.setObject(AppCommonConstants.getValStr("CACHESCHEMA"), usrTel+"countBankSmsFail",i,500);
		return i;
	}
	
	public ClBnkCrd getOneByCardNo(String bankCardNo) {
		return clBnkCrdDao.getOneByBankNo(bankCardNo);
	}
	
	public List<ClBnkCrd> getUsrAllBankCard(String usrCde){
		return clBnkCrdDao.getByUsrCode(usrCde);
	}
	
	public ClBnkCrd getOneByBankNoAndBinded4FoticBind(String bankCardNo) {
		return clBnkCrdDao.getOneByBankNoAndBinded4FoticBind(bankCardNo);
	}
	
	public ClBnkCrd getOneByBankNoAndBindedWFCCBBind(String bankCardNo) {
		return clBnkCrdDao.getOneByBankNoAndBindedWFCCBBind(bankCardNo);
	}
	
	public int updateBySEQSelective(ClBnkCrd bnkCrd) {
		return clBnkCrdDao.updateBySEQSelective(bnkCrd);
	}
	
	public int deleteByCradNo(ClBnkCrd bnkCard) {
		return clBnkCrdDao.deleteByCardNo(bnkCard.getAPPL_CARD_NO());
	}
	
	public ClBnkCrd getOneByBankNoWithOutSts(String bankCardNo) {
		return clBnkCrdDao.getOneByBankNoWithOutSts(bankCardNo);
	}
}
	
