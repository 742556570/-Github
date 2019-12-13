package com.jeeplus.modules.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.modules.app.dao.ClPremiumDetailDao;
import com.jeeplus.modules.app.entity.ClPremiumDetail;

@Service
@Transactional(readOnly = false)
public class ClPremiumDetailService {

	@Autowired
	private ClPremiumDetailDao premiumDetailDao;
	
	
	public List<ClPremiumDetail> getByPrdCde(String prdCde){
		return premiumDetailDao.getByPrdCde(prdCde);
	}
	
	public ClPremiumDetail getByPrdAndTrem(Map<String, String> params) {
		return premiumDetailDao.getByPrdAndTrem(params);
	}
}
