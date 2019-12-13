package com.jeeplus.modules.app.dao;

import java.util.HashMap;
import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.app.entity.ClUsrApply;
@MyBatisDao
public interface ClUsrApplyDao extends CrudDao<ClUsrApplyDao> {
    int deleteByPrimaryKey(Integer applSeq);

    int insert(ClUsrApply record);

    int insertSelective(ClUsrApply record);

    ClUsrApply selectByPrimaryKey(Integer applSeq);
    
    ClUsrApply getByPolicyNo(String policyNo);

    int updateByPrimaryKeySelective(ClUsrApply record);

    int updateByPrimaryKeyWithBLOBs(ClUsrApply record);

    int updateByPrimaryKey(ClUsrApply record);
    
    List<ClUsrApply> getByUsrCde(String usrCde);
    
    List<ClUsrApply> getByStCde(String ST_CDE);
    
    List<ClUsrApply> getPassedByUsrCde(String usrCde);
    
    int updateStatusByPolicyNo(String policyNo);
    
    List<ClUsrApply> getUnPassedApplies(String usrCde);
    
    List<ClUsrApply> getAllUsrApply(String date);
    
    List<ClUsrApply> getApdAndNotLoaned();
    
    int updateDebtByPolicyNo(HashMap<String, String> params);
    
    List<ClUsrApply> getUsrApplyLoaned();
    
    List<ClUsrApply> getUsrApplyLoanedByUsrCde(String usrCde);
    
    List<ClUsrApply> getUsrApplyLoanedByUsrCdeAndBankCard(HashMap<String, String> params);
    
    int updateCardNoByPolicyNo(HashMap<String, String> params);
    
    List<ClUsrApply>   getNeedReGenContractByStep(HashMap<String, String> params);
}