package com.jeeplus.modules.app.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.app.entity.ChPrdInfo;

@MyBatisDao
public interface ChPrdInfoDao extends CrudDao<ChPrdInfo>{
	
	public List<ChPrdInfo> getAllPrdInfo();
	


}
