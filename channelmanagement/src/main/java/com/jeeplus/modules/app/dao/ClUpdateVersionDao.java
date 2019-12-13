package com.jeeplus.modules.app.dao;

import java.util.Map;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.app.entity.ClUpdateVersion;
@MyBatisDao
public interface ClUpdateVersionDao extends CrudDao<ClUpdateVersion>{

	ClUpdateVersion selectByPrimaryKey(Map<String, String> params);

}