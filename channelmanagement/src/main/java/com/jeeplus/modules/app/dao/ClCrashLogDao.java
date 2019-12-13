package com.jeeplus.modules.app.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.app.entity.ClCrashLog;

@MyBatisDao
public interface ClCrashLogDao extends CrudDao<ClCrashLog>{
	
	public List<ClCrashLog> getAll();
	
	public List<ClCrashLog> getByCondition(ClCrashLog clCrashLog);
	
	public int insert(ClCrashLog clCrashLog);

}
