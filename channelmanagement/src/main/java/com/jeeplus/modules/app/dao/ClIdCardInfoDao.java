package com.jeeplus.modules.app.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.app.entity.ClIdCardInfo;

@MyBatisDao
public interface ClIdCardInfoDao extends CrudDao<ClIdCardInfo>{
	
	public List<ClIdCardInfo> getAllIdCardInfo();
	
	public ClIdCardInfo getByUsrCode(String USR_CDE);
	
	public ClIdCardInfo getByIdNo(String ID_NO);
	
	public ClIdCardInfo getValidByUsrCode(String USR_CDE);
	
	public int cntByUsrCode(String USR_CDE);
	
}
