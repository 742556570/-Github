            package com.jeeplus.modules.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.app.dao.ClBlackListDao;
import com.jeeplus.modules.app.entity.ClBlackList;

@Service
@Transactional(readOnly = false)
public class ClBlackListService extends CrudService<ClBlackListDao, ClBlackList> {

	@Autowired
	private ClBlackListDao clBlackListDao;

	public boolean getByPhone(String blphone) {
		int i =clBlackListDao.selectByPhone(blphone);
		if (i > 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean getByIdCardNo(String blidcardno) {
		int i =clBlackListDao.selectByIDCardNo(blidcardno);
		if (i > 0) {
			return true;
		} else {
			return false;
		}

	}
	public boolean getByName(String blName) {
		int i =clBlackListDao.selectByIDCardNo(blName);
		if (i > 0) {
			return true;
		} else {
			return false;
		}
		
	}
}
