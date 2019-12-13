package com.jeeplus.modules.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.app.dao.ClCrashLogDao;
import com.jeeplus.modules.app.entity.ClCrashLog;

@Service
@Transactional(readOnly = false)
public class ClCrashLogService extends CrudService<ClCrashLogDao, ClCrashLog> {

	@Autowired
	private ClCrashLogDao crashLogDao;

	public int addCrashLog(ClCrashLog clCrashLog) {
		return crashLogDao.insert(clCrashLog);
	}

}
