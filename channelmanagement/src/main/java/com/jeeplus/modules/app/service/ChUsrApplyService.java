package com.jeeplus.modules.app.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.modules.app.dao.ChUsrApplyDao;
import com.jeeplus.modules.app.entity.*;


@Service
@Transactional(readOnly = false)
public class ChUsrApplyService {
	@Autowired
	private ChUsrApplyDao applyDao;
	
	
	public boolean insertChUsrApply(ChUsrApply chUsrApply) {

		int i = applyDao.insert(chUsrApply);
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public int getAChUsrApplyCount(String custTel){
		HashMap<String, String> params = new HashMap<String,String>();
		params.put("custTel", custTel);
		int Cnt = applyDao.getAChUsrApplyCount(params);
		return Cnt;
	}
	
	public List<ChUsrApply> getAChUsrApplyDx(String startTime,String endTime){
		HashMap<String, String> params = new HashMap<String,String>();
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		List<ChUsrApply> list = applyDao.getAChUsrApplyDx(params);
		return list;
	}
	
	public int updateDxInfo(String applCde,String time,String result){
		HashMap<String, String> params = new HashMap<String,String>();
		params.put("APPL_CDE", applCde);
		params.put("DX_DATE", time);
		params.put("DX_STATUS", result);
		int transresult = applyDao.updateDxInfo(params);
		return transresult;
	}
	
	public List<ChUsrApply> transToDxCompensate(){
//		HashMap<String, String> params = new HashMap<String,String>();
//		params.put("dayTime", dayTime);
		List<ChUsrApply> list = applyDao.transToDxCompensate();
		return list;
	}
	
	public List<ChUsrApply> transToDxCompensateHand(){
//		HashMap<String, String> params = new HashMap<String,String>();
//		params.put("dayTime", dayTime);
		List<ChUsrApply> list = applyDao.transToDxCompensateHand();
		return list;
	}
	
	public ChDonationInfo getDonationInfo(HashMap<String, String> map){
//		HashMap<String, String> params = new HashMap<String,String>();
//		params.put("dayTime", dayTime);
		ChDonationInfo chDonationInfoResult = applyDao.getDonationInfo(map);
		return chDonationInfoResult;
	}
	
	public List<ChKeyword> getKeyWord(String keyWord){
		HashMap<String, String> params = new HashMap<String,String>();
		params.put("keyWordParameter", keyWord);
		List<ChKeyword> chKeyword = applyDao.getKeyWord(params);
		return chKeyword;
	}
	
}
