package com.jeeplus.modules.app.service;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.app.dao.ClUpdateVersionDao;
import com.jeeplus.modules.app.entity.ClUpdateVersion;

@Service
@Transactional(readOnly = false)
public class ClUpdateVersionService extends CrudService<ClUpdateVersionDao, ClUpdateVersion> {

	@Autowired
	private ClUpdateVersionDao clUpdateVersionDao;

	public ClUpdateVersion selectByPrimaryKey(String CHANNEL,String DATA_SOURCE) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("CHANNEL", CHANNEL);
		params.put("DATASOURCE", DATA_SOURCE);
		return  clUpdateVersionDao.selectByPrimaryKey(params);
	}


}
