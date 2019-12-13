package com.jeeplus.modules.app.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.app.entity.ClBlackList;
@MyBatisDao
public interface ClBlackListDao extends CrudDao<ClBlackList> {

    ClBlackList selectByPrimaryKey(Integer blSeq);
    
    int selectByPhone(String  blphone);
    
    int selectByIDCardNo(String  blidcardno);
    
    int selectByName(String  blName);
}