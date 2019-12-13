package com.jeeplus.modules.app.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.app.entity.ClSyncDx;

@MyBatisDao
public interface ClSyncDxDao  extends CrudDao<ClSyncDx>{
	
	int cntByUsrCode(String USR_CDE);
	
	ClSyncDx getByUsrCode(String USR_CDE);
	
	int insert(ClSyncDx clSyncDx);

}
