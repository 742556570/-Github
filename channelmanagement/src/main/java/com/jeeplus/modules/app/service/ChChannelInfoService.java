package com.jeeplus.modules.app.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.app.dao.ChChannelInfoDao;
import com.jeeplus.modules.app.entity.ChChannelInfo;
import com.jeeplus.modules.app.entity.ChUsrApply;

@Service
@Transactional(readOnly = false)
public class ChChannelInfoService extends CrudService<ChChannelInfoDao, ChChannelInfo> {

	@Autowired
	private ChChannelInfoDao chChannelInfoDao;

	public List<ChChannelInfo> getAllChChannelInfo(){
		List<ChChannelInfo> list = chChannelInfoDao.getAllChannelInfo();
		return list;
	}
	
	public List<ChChannelInfo> getAllSecChChannelInfo(){
		List<ChChannelInfo> list = chChannelInfoDao.getAllSecChannelInfo();
		return list;
	}
	
	public boolean insertChChannelApply(ChChannelInfo chChannelInfo) {

		int i = chChannelInfoDao.insert(chChannelInfo);
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public int getAllChannelDayCount(String channelCode,String daytime){
		HashMap<String, String> params = new HashMap<String,String>();
		params.put("CH_SEC_CDE", channelCode);
		params.put("DAY_TIME",daytime);
		int todayCnt = chChannelInfoDao.getAllChannelDayCount(params);
		return todayCnt;
	}
	
	public ChChannelInfo getChannelInfoByChCde(String channelCode){
		HashMap<String, String> params = new HashMap<String,String>();
		params.put("CH_CDE", channelCode);
		ChChannelInfo chChannelInfo = chChannelInfoDao.getChannelInfoByChCde(params);
		return chChannelInfo;
	}
	
	public ChChannelInfo getChannelStatusByChCde(String channelCode){
		HashMap<String, String> params = new HashMap<String,String>();
		params.put("CH_CDE", channelCode);
		ChChannelInfo chChannelInfo = chChannelInfoDao.getChannelStatusByChCde(params);
		return chChannelInfo;
	}
	
	public ChChannelInfo getAllChannelInfoByChCde(String channelCode){
		HashMap<String, String> params = new HashMap<String,String>();
		params.put("CH_CDE", channelCode);
		params.put("FA_CH_CDE",channelCode);
		ChChannelInfo chChannelInfo = chChannelInfoDao.getAllChannelInfoByChCde(params);
		return chChannelInfo;
	}

}
