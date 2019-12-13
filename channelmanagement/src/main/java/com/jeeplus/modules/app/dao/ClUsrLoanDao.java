package com.jeeplus.modules.app.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.app.entity.ClUsrLoan;
@MyBatisDao
public interface ClUsrLoanDao extends CrudDao<ClUsrLoan>{
    int deleteByPrimaryKey(Integer loanSeq);

    ClUsrLoan selectByPrimaryKey(String POLICY_NO);
    
    int getLoanOrderPolicy(String POLICY_NO);
    
    List<ClUsrLoan> getByUsrCde(String usrCde);
    
    int updateByPrimaryKeySelective(ClUsrLoan clUsrLoan);
    
    List<String> getTels2SendBindBankCardRemind();

    
    List<ClUsrLoan> getByBeginDate(String date);
    
    int getSingleApply(String usrCde);
    
    int getUsrLoanFotic(String usrCde);

}