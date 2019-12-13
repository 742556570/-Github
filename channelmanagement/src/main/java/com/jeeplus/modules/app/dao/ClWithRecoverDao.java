package com.jeeplus.modules.app.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.app.entity.ClWithRecover;
@MyBatisDao
public interface ClWithRecoverDao extends CrudDao<ClWithRecover> {
    int deleteByPrimaryKey(Integer loanSeq);

    int insert(ClWithRecover record);

    int insertSelective(ClWithRecover record);

    ClWithRecover selectByPrimaryKey(Integer loanSeq);

    int updateByPrimaryKeySelective(ClWithRecover record);

    int updateSysStatebyPoliyNo(String POLICY_NO);
    
    List<ClWithRecover> selectByPoliyNo(String POLICY_NO);
    
    int selectByPoliyNoToSetLind(String POLICY_NO);
    
    List<ClWithRecover> selectByPoliyNoWithoutStatus(String POLICY_NO); 
    
}