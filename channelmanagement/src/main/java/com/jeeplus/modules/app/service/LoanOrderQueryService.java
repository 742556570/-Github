package com.jeeplus.modules.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.app.dao.ClAppIdGenDao;
import com.jeeplus.modules.app.dao.ClUsrLoanDao;
import com.jeeplus.modules.app.entity.ClUsrLoan;

@Service
@Transactional(readOnly = false)
public class LoanOrderQueryService extends CrudService<ClUsrLoanDao, ClUsrLoan> {

	@Autowired
	private ClUsrLoanDao clUsrLoanDao;
	@Autowired
	private ClAppIdGenDao clAppIdGenDao;

	public boolean insertLoanOrder(ClUsrLoan clUsrLoan) {
		int i = clUsrLoanDao.insert(clUsrLoan);
		if (i > 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean getLoanOrderPolicy(String POLICY_NO) {
		int i = clUsrLoanDao.getLoanOrderPolicy(POLICY_NO);
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}

	public ClUsrLoan getselectByPrimaryKey(String POLICY_NO) {
		return clUsrLoanDao.selectByPrimaryKey(POLICY_NO);
	}

	public int getApplyId() {
		int ApplyId = clAppIdGenDao.getApplyId();
		return ApplyId;
	}

	public int updateByObj(ClUsrLoan clUsrLoan) {
		return clUsrLoanDao.updateByPrimaryKeySelective(clUsrLoan);
	}
	
	public List<ClUsrLoan> getByBeginDate(String date){
		return clUsrLoanDao.getByBeginDate(date);
	}
}
