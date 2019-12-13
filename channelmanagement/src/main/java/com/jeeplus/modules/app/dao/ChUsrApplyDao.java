package com.jeeplus.modules.app.dao;

import java.util.HashMap;
import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.app.entity.ChUsrApply;
import com.jeeplus.modules.app.entity.ClBnkCrd;
import com.jeeplus.modules.app.entity.ClUsrLoanDetail;
import com.jeeplus.modules.app.entity.ChDonationInfo;
import com.jeeplus.modules.app.entity.*;

@MyBatisDao
public interface ChUsrApplyDao extends CrudDao<ChUsrApplyDao> {
    int deleteByPrimaryKey(Integer applSeq);

    int insert(ChUsrApply record);

    int insertSelective(ChUsrApply record);
    
    int getAChUsrApplyCount(HashMap<String, String> map);
    
    List<ChUsrApply> getAChUsrApplyDx(HashMap<String, String> map);

    public int updateDxInfo(HashMap<String, String> map);
    
    List<ChUsrApply> transToDxCompensate();
    
    List<ChUsrApply> transToDxCompensateHand();
    
    ChDonationInfo getDonationInfo(HashMap<String, String> map);
    
    List<ChKeyword> getKeyWord(HashMap<String, String> map);
    
}