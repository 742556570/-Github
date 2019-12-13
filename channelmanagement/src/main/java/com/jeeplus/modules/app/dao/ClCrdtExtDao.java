package com.jeeplus.modules.app.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.app.entity.ClCrdtExt;

@MyBatisDao
public interface ClCrdtExtDao extends CrudDao<ClCrdtExt> {

	public ClCrdtExt getClCrdtExtBySeq(String CRDTEXT_SEQ);

	public ClCrdtExt getClCrdtExtByUsrCde(String USR_CDE);
	
	public ClCrdtExt getClCrdtExtByPolicyNo(String USR_APPSEQ);
	
	public List<ClCrdtExt> getClCrdtExtBySts(String STATUS);

	public int cntClCrdtExtByUsrCde(String USR_CDE);

	public int updateByAppSeq(ClCrdtExt clCrdtExt);

}
