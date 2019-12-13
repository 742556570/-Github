package com.jeeplus.modules.app.dao;

import com.jeeplus.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface ClAppIdGenDao  {
	public Integer getApplyId();
	
	public Integer getCrtId();
	
	public Integer getMidPltSeq();
	
	public Integer getTelSeq();
	
	public Integer getContractSeq();
}
