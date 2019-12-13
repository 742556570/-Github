package com.jeeplus.modules.app.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.app.entity.ClDealFailed;

@MyBatisDao
public interface ClDealFailedDao extends CrudDao<ClDealFailed>{
	
	public List<ClDealFailed> getALL();
	
	public List<ClDealFailed> getNotDone();
	
	public List<ClDealFailed> getByUsrCode(String USR_CDE);
	
	public ClDealFailed getByUsrPolicyNO(String POLICY_NO);
	
	public int updateByPolicyNO(ClDealFailed clDealFailed);
}
