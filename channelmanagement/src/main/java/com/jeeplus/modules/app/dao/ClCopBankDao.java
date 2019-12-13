package com.jeeplus.modules.app.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.app.entity.ClCopBank;

@MyBatisDao
public interface ClCopBankDao extends CrudDao<ClCopBank>{
    int deleteByPrimaryKey(String copBankNo);

    int insert(ClCopBank record);

    int insertSelective(ClCopBank record);

    ClCopBank selectByPrimaryKey(String copBankNo);

    int updateByPrimaryKeySelective(ClCopBank record);

    int updateByPrimaryKey(ClCopBank record);
}