package com.jeeplus.modules.app.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.app.dao.ChCityInfoDao;
import com.jeeplus.modules.app.entity.ChChannelInfo;
import com.jeeplus.modules.app.entity.ChCityInfo;

@Service
@Transactional(readOnly = false)
public class ChCityInfoService extends CrudService<ChCityInfoDao, ChCityInfo> {

	@Autowired
	private ChCityInfoDao chCityInfoDao;

	public List<ChCityInfo> getAllChCityInfo(){
		List<ChCityInfo> list = chCityInfoDao.getAllCityInfo();
		return list;
	}
	
	public ChCityInfo getCityInfoByCityName(String cityName){
		HashMap<String, String> params = new HashMap<String,String>();
		params.put("CITY_NAME", cityName);
		ChCityInfo chCityInfo = chCityInfoDao.getCityInfoByCityName(params);
		return chCityInfo;
	}

}
