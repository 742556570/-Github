package com.jeeplus.modules.app.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.app.entity.ClPrdBasicInfo;
@MyBatisDao
public interface ClPrdBasicInfoDao extends CrudDao<ClPrdBasicInfo>{
    int insert(ClPrdBasicInfo record);

    int insertSelective(ClPrdBasicInfo record);
    
    ClPrdBasicInfo getBasicInfoByPrdCde(String prdCde);
}