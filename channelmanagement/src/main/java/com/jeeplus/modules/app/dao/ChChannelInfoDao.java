package com.jeeplus.modules.app.dao;

import java.util.HashMap;
import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.app.entity.ChChannelInfo;
import com.jeeplus.modules.app.entity.ChUsrApply;

@MyBatisDao
public interface ChChannelInfoDao extends CrudDao<ChChannelInfo>{
	
	public List<ChChannelInfo> getAllChannelInfo();
	
	public List<ChChannelInfo> getAllSecChannelInfo();
	
	int insert(ChChannelInfo record);

    int insertSelective(ChChannelInfo record);
    
    public int getAllChannelDayCount(HashMap<String, String> map);
    
    public ChChannelInfo getChannelInfoByChCde(HashMap<String, String> map);

    public ChChannelInfo getChannelStatusByChCde(HashMap<String, String> map);
    
    public ChChannelInfo getAllChannelInfoByChCde(HashMap<String, String> map);
	


}
