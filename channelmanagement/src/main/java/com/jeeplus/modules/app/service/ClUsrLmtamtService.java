package com.jeeplus.modules.app.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.modules.app.dao.ClUsrLmtamtDao;
import com.jeeplus.modules.app.dao.ClUsrLoanDao;
import com.jeeplus.modules.app.dao.ClUsrLoanDetailDao;
import com.jeeplus.modules.app.dao.ClWithRecoverDao;
import com.jeeplus.modules.app.entity.ClUsrLmtamt;
import com.jeeplus.modules.app.entity.ClUsrLoan;
import com.jeeplus.modules.app.entity.ClUsrLoanDetail;
import com.jeeplus.modules.app.entity.ClWithRecover;

@Service
@Transactional(readOnly = false)
public class ClUsrLmtamtService {
	
	@Autowired
	private ClUsrLmtamtDao lmtAmtDao;
	
	@Autowired
	private ClUsrLoanDetailDao usrLoanDetailDao;
	
	@Autowired
	private ClWithRecoverDao withDao;
	
	
	public int add(String usrCde, BigDecimal crdtAmt) {
		ClUsrLmtamt lmtAmt = new ClUsrLmtamt();
		lmtAmt.setUsrCde(usrCde);
		lmtAmt.setCreditAmount(crdtAmt);
		lmtAmt.setUsedAmount(new BigDecimal("0"));
		lmtAmt.setRepayAmount(new BigDecimal("0"));
		lmtAmt.setMdfDt(new Date());
		return lmtAmtDao.insert(lmtAmt);
	}
	
	public ClUsrLmtamt getByUsrCde(String usrCde) {
		ClUsrLmtamt lmtAmt = lmtAmtDao.selectByPrimaryKey(usrCde);
		return lmtAmt;
	}

	public int updateCreditAmt(String usrCde, BigDecimal crdtAmt) {
		ClUsrLmtamt lmtAmt = new ClUsrLmtamt();
		lmtAmt.setUsrCde(usrCde);
		lmtAmt.setCreditAmount(crdtAmt);
		lmtAmt.setMdfDt(new Date());
		return lmtAmtDao.updateByPrimaryKeySelective(lmtAmt);
	}
	
	public int updateUsedAmt(String usrCde,BigDecimal usedAmt) {
		ClUsrLmtamt old = lmtAmtDao.selectByPrimaryKey(usrCde);
		BigDecimal used = old.getUsedAmount().add(usedAmt);
		old.setUsedAmount(used);
		old.setMdfDt(new Date());
		return lmtAmtDao.updateByPrimaryKeySelective(old);
	}
	
	
	public int updateUsedAmtOnce(String usrCde,BigDecimal usedAmt) {
		ClUsrLmtamt old = lmtAmtDao.selectByPrimaryKey(usrCde);
		old.setUsedAmount(usedAmt);
		old.setMdfDt(new Date());
		return lmtAmtDao.updateByPrimaryKeySelective(old);
	}
	
	
	public int updateUsedAmtRecovery(String usrCde,BigDecimal usedAmt) {
		ClUsrLmtamt old = lmtAmtDao.selectByPrimaryKey(usrCde);
		BigDecimal used = old.getUsedAmount().subtract(usedAmt);
		old.setUsedAmount(used);
		old.setMdfDt(new Date());
		return lmtAmtDao.updateByPrimaryKeySelective(old);
	}
	
	
	public int updateRepayAmt(String usrCde, BigDecimal repaytAmt) {
		ClUsrLmtamt lmtAmt = new ClUsrLmtamt();
		lmtAmt.setUsrCde(usrCde);
		lmtAmt.setRepayAmount(repaytAmt);
		lmtAmt.setMdfDt(new Date());
		return lmtAmtDao.updateByPrimaryKeySelective(lmtAmt);
	}
	
	public int syncRepayAmt(String usrCde) {
		List<ClUsrLoanDetail> details = usrLoanDetailDao.getByUsrCde(usrCde);
		BigDecimal newRepay = new BigDecimal("0");
		
		HashSet<String> policyNos = new HashSet<String>();
		
		for (ClUsrLoanDetail clUsrLoanDetail : details) {
			String payoffflag = clUsrLoanDetail.getPayOffFlag();
			if("1".equals(payoffflag)){
				newRepay = newRepay.add(clUsrLoanDetail.getrCapi());
			}else if("2".equals(payoffflag)){
				policyNos.add(clUsrLoanDetail.getPolicyNo());
			}
			
		}
		
		for (String policyNo : policyNos) {
			List<ClWithRecover> withsRecover = withDao.selectByPoliyNoWithoutStatus(policyNo);
			for (ClWithRecover clWithRecover : withsRecover) {
				newRepay = newRepay.add(new BigDecimal(clWithRecover.getCapital()));
			}
		}
		
		return updateRepayAmt(usrCde,newRepay);
	}
	
}
