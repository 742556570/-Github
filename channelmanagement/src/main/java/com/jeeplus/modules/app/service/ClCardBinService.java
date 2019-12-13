package com.jeeplus.modules.app.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.app.pojo.ResponeJsonObj;
import com.jeeplus.modules.app.dao.ClCardBinDao;
import com.jeeplus.modules.app.entity.ClCardBin;
@Service
@Transactional(readOnly = false)
public class ClCardBinService extends CrudService<ClCardBinDao,ClCardBin> {

	@Autowired
	private ClCardBinDao clCardBinDao;
	
	public String getByClCardBinId(String card){
		
		ClCardBin clCardBin = clCardBinDao.getByClCardBinId(card);
		if(clCardBin!=null) {
			JsonObject rd = new JsonObject();
			rd.add("carbin", new Gson().fromJson(new Gson().toJson(clCardBin), JsonObject.class));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "卡bin库返回成功", rd);
		}else {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "卡bin库查询失败", null);
		}
		
		
	}
	
	
public ClCardBin getByClCardBinIdObj(String card){
		ClCardBin clCardBin = clCardBinDao.getByClCardBinIdObj(card);
		return clCardBin;
	}
}
