package com.jeeplus.modules.app.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.app.entity.ClCopBankRate;

@MyBatisDao
public interface ClCopBankRateDao extends CrudDao<ClCopBankRate>{
    int deleteByPrimaryKey(String copBankNo);

    int insert(ClCopBankRate record);

    int insertSelective(ClCopBankRate record);

    ClCopBankRate selectByPrimaryKey(String copBankNo);

    int updateByPrimaryKeySelective(ClCopBankRate record);

    int updateByPrimaryKey(ClCopBankRate record);
}