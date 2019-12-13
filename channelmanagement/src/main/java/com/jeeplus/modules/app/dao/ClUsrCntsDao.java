package com.jeeplus.modules.app.dao;

import java.util.HashMap;
import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.app.entity.ClUsrCnts;

@MyBatisDao
public interface ClUsrCntsDao extends CrudDao<ClUsrCnts>{
	
	public List<ClUsrCnts> getByUsrCode(String USR_CDE);
	
	public List<ClUsrCnts> getByUsrCodeDisplay(String USR_CDE);
	
	public int cntUsrRelMob(HashMap<String, String> params);
	
}
