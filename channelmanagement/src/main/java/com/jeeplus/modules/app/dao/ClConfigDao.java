package com.jeeplus.modules.app.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.app.entity.ClConfig;

@MyBatisDao
public interface ClConfigDao extends CrudDao<ClConfig>{
	
	public List<ClConfig> getAllConfig();
	
	public ClConfig getByConfigId(String id);
	
	public int updateById(ClConfig clConfig);

}
