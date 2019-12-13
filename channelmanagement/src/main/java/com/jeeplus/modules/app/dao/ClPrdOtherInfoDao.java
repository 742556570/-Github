package com.jeeplus.modules.app.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.app.entity.ClPrdOtherInfo;
@MyBatisDao
public interface ClPrdOtherInfoDao extends CrudDao<ClPrdOtherInfo>{
    int insert(ClPrdOtherInfo record);

    int insertSelective(ClPrdOtherInfo record);
    
    ClPrdOtherInfo getOtherInfoByPrdCde(String prdCde);
}