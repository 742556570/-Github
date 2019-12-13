package com.jeeplus.modules.app.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.app.entity.ClUsrLmtamt;
@MyBatisDao
public interface ClUsrLmtamtDao extends CrudDao<ClUsrLmtamtDao> {
    int deleteByPrimaryKey(String usrCde);

    int insert(ClUsrLmtamt record);

    int insertSelective(ClUsrLmtamt record);

    ClUsrLmtamt selectByPrimaryKey(String usrCde);

    int updateByPrimaryKeySelective(ClUsrLmtamt record);

    int updateByPrimaryKey(ClUsrLmtamt record);
}