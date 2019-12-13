package com.jeeplus.modules.app.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.app.entity.ClCapChannel;


@MyBatisDao
public interface ClCapChannelDao extends CrudDao<ClCapChannel>{

	
	public ClCapChannel getByCapSeq(String CAP_SEQ);
	
	public ClCapChannel getByUsrSource();
	
	public List<ClCapChannel> getAll();
	
	public int updateById(ClCapChannel clCapChannel);
}
