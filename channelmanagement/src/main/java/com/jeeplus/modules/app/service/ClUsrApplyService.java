package com.jeeplus.modules.app.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.modules.app.dao.ClUsrApplyDao;
import com.jeeplus.modules.app.entity.ClUsrApply;

@Service
@Transactional(readOnly = false)
public class ClUsrApplyService {
	@Autowired
	private ClUsrApplyDao applyDao;
	
	
	public List<ClUsrApply> getPassedByUsrCde(String usrCde){
		return applyDao.getPassedByUsrCde(usrCde);
	}
	
	public List<ClUsrApply> getByStCde(String ST_CDE){
		return applyDao.getByStCde(ST_CDE);
	}
	
	public List<ClUsrApply> getUsrApplyLoaned(){
		
		return applyDao.getUsrApplyLoaned();
		
	}
	
	public List<ClUsrApply> getUsrApplyLoaned(String usrCde){
		
		return applyDao.getUsrApplyLoanedByUsrCde(usrCde);
		
	}
	
	public boolean updateStatusByPolicyNo(String policyNo) {
		int i = applyDao.updateStatusByPolicyNo(policyNo);
		if(i>0) {
			return true;
		}else {
			return false;
		}
	}

	public List<ClUsrApply> unPassedAppliesByUsr(String usrCde){
		List<ClUsrApply> appies = applyDao.getUnPassedApplies(usrCde);
		return appies;
	}
	
	public List<ClUsrApply> getAllUsrApply(String date){
		List<ClUsrApply> appies = applyDao.getAllUsrApply(date);
		return appies;
	}
	
	public boolean updateDebtByPolicyNo(String debtStatus,String mdfDt,String policyNo) {
		HashMap< String, String> params = new HashMap<String,String>();
		params.put("debtStatus", debtStatus);
		params.put("mdfDt", mdfDt);
		params.put("policyNo", policyNo);
		int i = applyDao.updateDebtByPolicyNo(params);
		if(i > 0) {
			return true;
		}else {
			return false;
		}
	}
	
	public ClUsrApply getByPolicyNo(String policyNo) {
		return applyDao.getByPolicyNo(policyNo);
	}
	
	
	public List<ClUsrApply> getUsrApplyLoanedByUsrCdeAndBankCard(String usrCde,String bankCardNo){
		HashMap<String, String> params = new HashMap<String,String>();
		params.put("usrCde", usrCde);
		params.put("bankCardNo", bankCardNo);
		
		return applyDao.getUsrApplyLoanedByUsrCdeAndBankCard(params);
		
	}
	
	public boolean updateCardNoByPolicyNo(String bankCardNo,String mdfDt,String policyNo) {
		HashMap< String, String> params = new HashMap<String,String>();
		params.put("bankCardNo", bankCardNo);
		params.put("mdfDt", mdfDt);
		params.put("policyNo", policyNo);
		int i = applyDao.updateCardNoByPolicyNo(params);
		if(i > 0) {
			return true;
		}else {
			return false;
		}
	}
	
	public List<ClUsrApply> getApdAndNotLoaned(){
		return applyDao.getApdAndNotLoaned();
	}
	
	public void updateByPrimaryKeySelective(ClUsrApply usrApply) {
		applyDao.updateByPrimaryKeySelective(usrApply);
	}
}
