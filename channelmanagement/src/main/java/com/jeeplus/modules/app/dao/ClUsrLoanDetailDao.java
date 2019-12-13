package com.jeeplus.modules.app.dao;

import java.util.HashMap;
import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.app.entity.ClUsrLoanDetail;
@MyBatisDao
public interface ClUsrLoanDetailDao extends CrudDao<ClUsrLoanDetail>{
    int deleteLoanOrderPolicy(String POLICY_NO);

    ClUsrLoanDetail selectByPrimaryKey(String POLICY_NO);

    
    int getLoanOrderPolicy(String POLICY_NO);
    
    
    List<ClUsrLoanDetail> getByUsrCde(String usrCde);
    
    List<ClUsrLoanDetail> getByPolicy(String POLICY_NO);
    
    
    List<ClUsrLoanDetail> getByStartAndEndDate(HashMap<String, String> params);
}