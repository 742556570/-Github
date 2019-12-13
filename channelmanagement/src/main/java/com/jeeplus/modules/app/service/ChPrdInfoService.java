package com.jeeplus.modules.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.app.dao.ChPrdInfoDao;
import com.jeeplus.modules.app.entity.ChPrdInfo;

@Service
@Transactional(readOnly = false)
public class ChPrdInfoService extends CrudService<ChPrdInfoDao, ChPrdInfo> {

	@Autowired
	private ChPrdInfoDao chPrdInfoDao;

	public List<ChPrdInfo> getAllChPrdInfo(){
		List<ChPrdInfo> list = chPrdInfoDao.getAllPrdInfo();
		return list;
	}
	
	

}
