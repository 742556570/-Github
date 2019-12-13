package com.jeeplus.modules.app.dao;

import java.util.HashMap;
import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.app.entity.ClPsnCHk;
@MyBatisDao
public interface ClPsnCHKDao extends CrudDao<ClPsnCHk> {
    int deleteByPrimaryKey(Integer mediaSeq);

    int insert(ClPsnCHk record);

    int insertSelective(ClPsnCHk record);

    ClPsnCHk selectByPrimaryKey(Integer mediaSeq);

    int updateByPrimaryKeySelective(ClPsnCHk record);

    int updateByPrimaryKey(ClPsnCHk record);
    
    List<ClPsnCHk> selectByUsrCde(String usrCde);
    
    List<ClPsnCHk> selectByUsrCdeAndStatus(String usrCde);
    
    List<ClPsnCHk> selectByUsrCdeAndTrdNo(HashMap<String, String> param);
}