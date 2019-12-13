package com.jeeplus.modules.app.dao;

import java.util.HashMap;
import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.app.entity.ClIdImg;


@MyBatisDao
public interface ClIdImgDao extends CrudDao<ClIdImg>{
	
	public List<ClIdImg> getByUsrCode(String USR_CDE);
	
	public List<ClIdImg> getByUsrTrdNo(HashMap<String, String> params);

	public ClIdImg getByUsrTrdNoImgType(HashMap<String, String> params);
	
	public int update4FeatureByTrdno(ClIdImg clIdImg);
}
