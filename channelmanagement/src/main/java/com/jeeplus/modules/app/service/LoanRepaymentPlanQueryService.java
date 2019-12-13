package com.jeeplus.modules.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.app.dao.ClAppIdGenDao;
import com.jeeplus.modules.app.dao.ClUsrLoanDetailDao;
import com.jeeplus.modules.app.entity.ClUsrLoanDetail;

@Service
@Transactional(readOnly = false)
public class LoanRepaymentPlanQueryService extends CrudService<ClUsrLoanDetailDao, ClUsrLoanDetail> {

	@Autowired
	private ClUsrLoanDetailDao clUsrLoanDetailDao;
	@Autowired
	private ClAppIdGenDao clAppIdGenDao;

	public boolean insertLoanOrderDetail(ClUsrLoanDetail clUsrLoanDetail) {
		int i = clUsrLoanDetailDao.insert(clUsrLoanDetail);
		if (i > 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean getLoanOrderPolicy(String POLICY_NO) {
		int i = clUsrLoanDetailDao.getLoanOrderPolicy(POLICY_NO);
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}
	public boolean deleteLoanOrderPolicy(String POLICY_NO) {
		int i = clUsrLoanDetailDao.deleteLoanOrderPolicy(POLICY_NO);
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
	
	public List<ClUsrLoanDetail> getByPolicy (String POLICY_NO){
		return clUsrLoanDetailDao.getByPolicy(POLICY_NO);
	} 
}
