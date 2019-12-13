package com.jeeplus.modules.app.dao;

import java.util.HashMap;
import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.app.entity.ClUsrCnts;
import com.jeeplus.modules.app.entity.ClUsrInfo;

@MyBatisDao
public interface ClUsrInfoDao extends CrudDao<ClUsrInfo> {

	public ClUsrInfo getByUsrCode(String USR_CDE);

	public int cntByUsrCode(String USR_CDE);
	
	public int  updateByusrcde(ClUsrInfo clUsrInfo);

}
