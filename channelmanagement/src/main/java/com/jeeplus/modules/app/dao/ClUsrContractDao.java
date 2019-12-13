package com.jeeplus.modules.app.dao;

import java.util.List;
import java.util.Map;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.app.entity.ClUsrContract;
@MyBatisDao
public interface ClUsrContractDao extends CrudDao<ClUsrContract>{
    int insert(ClUsrContract record);

    int insertSelective(ClUsrContract record);
    
    List<ClUsrContract> getUsrContractByDate(String date);
    
    int updateByusrCde(Map<String, String> params);
    
    int isExistByPolicyNo(Map<String, String> params);
    
    ClUsrContract getLatestContractByUsrAndStep(Map<String, String> params);
}