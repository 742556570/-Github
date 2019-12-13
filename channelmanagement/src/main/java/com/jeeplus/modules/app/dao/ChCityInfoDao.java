package com.jeeplus.modules.app.dao;

import java.util.HashMap;
import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.app.entity.ChCityInfo;

@MyBatisDao
public interface ChCityInfoDao extends CrudDao<ChCityInfo>{
	
	public List<ChCityInfo> getAllCityInfo();
	
	public ChCityInfo getCityInfoByCityName(HashMap<String, String> map);
	


}
