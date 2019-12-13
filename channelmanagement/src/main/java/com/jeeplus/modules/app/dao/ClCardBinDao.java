package com.jeeplus.modules.app.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.app.entity.ClCardBin;
@MyBatisDao
public interface ClCardBinDao extends CrudDao<ClCardBin>{
    int insert(ClCardBin record);
    
    public ClCardBin getByClCardBinId(String card);
    
    public ClCardBin getByClCardBinIdObj(String card);
   
    int insertSelective(ClCardBin record);
}