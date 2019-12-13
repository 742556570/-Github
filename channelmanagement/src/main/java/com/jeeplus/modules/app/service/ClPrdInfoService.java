package com.jeeplus.modules.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.app.dao.ClPrdInfoDao;
import com.jeeplus.modules.app.entity.ClPrdInfo;

@Service
@Transactional(readOnly = false)
public class ClPrdInfoService extends CrudService<ClPrdInfoDao, ClPrdInfo> {

	@Autowired
	private ClPrdInfoDao clPrdInfoDao;

	public List<ClPrdInfo> getAllClPrdInfo(){
		List<ClPrdInfo> list = clPrdInfoDao.getAllPrdInfo();
		return list;
	}
	
	public ClPrdInfo getClPrdInfoByPrdCde(String prdCde) {
		ClPrdInfo clPrdInfo = clPrdInfoDao.getByPrdCde(prdCde);
		return clPrdInfo;
	}

}
