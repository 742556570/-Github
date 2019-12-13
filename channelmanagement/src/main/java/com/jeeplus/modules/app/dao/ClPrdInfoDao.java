package com.jeeplus.modules.app.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.app.entity.ClPrdInfo;

@MyBatisDao
public interface ClPrdInfoDao extends CrudDao<ClPrdInfo>{
	
	public List<ClPrdInfo> getAllPrdInfo();
	
	public ClPrdInfo getByPrdSeq(String PRD_SEQ);
	
	public ClPrdInfo getByPrdCde(String PRD_CDE);
	
	public int updateByPrdSeq(ClPrdInfo clPrdInfo);
	
	public int updateByPrdCde(ClPrdInfo clPrdInfo);

}
