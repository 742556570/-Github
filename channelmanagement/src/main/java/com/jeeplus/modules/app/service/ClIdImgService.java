package com.jeeplus.modules.app.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.app.dao.ClIdImgDao;
import com.jeeplus.modules.app.entity.ClIdImg;

@Service
@Transactional(readOnly = false)
public class ClIdImgService extends CrudService<ClIdImgDao,ClIdImg> {
	
	@Autowired
	private ClIdImgDao imgDao;
	
	public List<ClIdImg> getByUsrCode(String USR_CDE){
		return imgDao.getByUsrCode(USR_CDE);
	}
	
	
	public List<ClIdImg> cntByUsrTrdNo(String USR_CDE,String IMG_TRDNO){
		HashMap<String, String> params = new HashMap<String,String>();
		params.put("USR_CDE", USR_CDE);
		params.put("IMG_TRDNO",IMG_TRDNO);
		return imgDao.getByUsrTrdNo(params);
	}

	public ClIdImg getByUsrTrdNoImgType(String USR_CDE,String IMG_TRDNO,String IMG_TYPE){
		HashMap<String, String> params = new HashMap<String,String>();
		params.put("USR_CDE", USR_CDE);
		params.put("IMG_TRDNO",IMG_TRDNO);
		params.put("IMG_TYPE", IMG_TYPE);
		return imgDao.getByUsrTrdNoImgType(params);
	}
	
	public int addIdImg(String iMG_TRDNO, String uSR_CDE, String iMG_PATH_LC, String iMG_TYPE, String cRT_DT) {
		ClIdImg img = new ClIdImg(iMG_TRDNO, uSR_CDE, iMG_PATH_LC, iMG_TYPE, cRT_DT);
		return imgDao.insert(img);
	}
	
	public int update4FeatureByTrdno(ClIdImg clIdImg) {
		return imgDao.update4FeatureByTrdno(clIdImg);
	}

}
