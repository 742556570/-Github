package com.jeeplus.modules.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.app.dao.ClAppIdGenDao;
import com.jeeplus.modules.app.dao.ClWithRecoverDao;
import com.jeeplus.modules.app.entity.ClWithRecover;

@Service
@Transactional(readOnly = false)
public class WithRecoverQueryService extends CrudService<ClWithRecoverDao, ClWithRecover> {

	@Autowired
	private ClWithRecoverDao clWithRecoverDao;
	@Autowired
	private ClAppIdGenDao clAppIdGenDao;

	public boolean insertWithRecover(ClWithRecover clWithRecover) {
		int i = clWithRecoverDao.insert(clWithRecover);
		if (i > 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean updateSysStatebyPoliyNo(String POLICY_NO) {
		int i = clWithRecoverDao.updateSysStatebyPoliyNo(POLICY_NO);
		if (i > 0) {
			return true;
		} else {
			return false;
		}

	}

	public List<ClWithRecover> selectByPoliyNo(String POLICY_NO) {
		return  clWithRecoverDao.selectByPoliyNo(POLICY_NO);
	}

	public boolean selectByPoliyNoToSetLind(String POLICY_NO) {
		int i = clWithRecoverDao.selectByPoliyNoToSetLind(POLICY_NO);
		if (i > 0) {
			return true;
		} else {
			return false;
		}

	}
	public int getApplyId() {
		int ApplyId = clAppIdGenDao.getApplyId();
		return ApplyId;
	}

}
