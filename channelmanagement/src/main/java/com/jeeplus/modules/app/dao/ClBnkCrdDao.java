package com.jeeplus.modules.app.dao;

import java.util.HashMap;
import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.app.entity.ClBnkCrd;

@MyBatisDao
public interface ClBnkCrdDao extends CrudDao<ClBnkCrd> {

	public List<ClBnkCrd> getByUsrCode(String USR_CDE);

	//用户支持多银行卡不允许使用此方法
//	public ClBnkCrd getOneByUsrCode(String USR_CDE);
	
	public ClBnkCrd getUsrDefaultCardByUsrCode(String USR_CDE);
	
	public ClBnkCrd getUsrOldCardByUsrCode(String USR_CDE);
	
	public ClBnkCrd getOneByBankNo(String APPL_CARD_NO);
	
	public ClBnkCrd getOneByBankNoWithOutSts(String APPL_CARD_NO);
	
	public ClBnkCrd getByUsrCodeAndCardNum(HashMap<String, String> params);

	public int cntByUsrCodeAndCardNum(HashMap<String, String> params);
	
	public int cntByUsrCode(String usrCde);
	
	public int updateBankCardApply(HashMap<String, String> params);
	
	public int updateBankIsBind(HashMap<String, String> params);
	
	public int updateBySEQSelective(ClBnkCrd clBnkCrd);
	
	public int updateBankRePre(ClBnkCrd clBnkCrd);
	
	public ClBnkCrd getOneByBankNoAndBinded4FoticBind(String APPL_CARD_NO);
	
	public ClBnkCrd getOneByBankNoAndBindedWFCCBBind(String APPL_CARD_NO);
	
	public int deleteByCardNo(String APPL_CARD_NO);
	
	public ClBnkCrd getUsrDefaultBnkCrdWithOutSts(String USR_CDE);

}
