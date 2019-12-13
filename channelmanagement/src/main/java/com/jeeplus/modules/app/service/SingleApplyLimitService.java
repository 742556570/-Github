package com.jeeplus.modules.app.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.app.constant.AppCommonConstants;
import com.jeeplus.modules.app.dao.ClAppIdGenDao;
import com.jeeplus.modules.app.dao.ClUsrLoanDao;
import com.jeeplus.modules.app.dao.ClWithRecoverDao;
import com.jeeplus.modules.app.entity.ClWithRecover;
@Service
@Transactional(readOnly = false)
public class SingleApplyLimitService extends CrudService<ClWithRecoverDao, ClWithRecover> {

	@Autowired
	private ClWithRecoverDao clWithRecoverDao;
	@Autowired
	private ClAppIdGenDao clAppIdGenDao;	
	@Autowired
	private ClUsrLoanDao clUsrLoanDao;
	
	
	/**
	 * 状态开关
	 * @return
	 */
	public boolean singleApplyStatus() {
		String isCloseService = AppCommonConstants.getValStr("SingleApplyStatusService");
		if(!"true".equals(isCloseService)) {
			return false;
		}
			return true;
	}
	/**
	 * 温馨提示开关
	 * @return
	 */
	public boolean warmPromptStatus() {
		String warmPromptStatus = AppCommonConstants.getValStr("WarmPromptStatus");
		if(!"true".equals(warmPromptStatus)) {
			return false;
		}
			return true;
	}
	/**
	 * 判断此客户是否有未结清借款记录
	 * @return
	 */
	
	
	public int getSingleApply(String usrCde) {
		int flag = clUsrLoanDao.getSingleApply(usrCde);
		return flag;
	}


}
