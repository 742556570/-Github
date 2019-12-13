package com.jeeplus.modules.app.dao;

import java.util.List;
import java.util.Map;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.app.entity.ClPremiumDetail;

@MyBatisDao
public interface ClPremiumDetailDao extends CrudDao<ClPremiumDetailDao>{
	
	ClPremiumDetail getByPrdAndTrem(Map<String, String> map);
	
	List<ClPremiumDetail> getByPrdCde(String prdCde);
}
