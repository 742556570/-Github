package com.jeeplus.modules.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.app.dao.ClCapChannelDao;
import com.jeeplus.modules.app.entity.ClCapChannel;

@Service
@Transactional(readOnly = false)
public class ClCapChannelService extends CrudService<ClCapChannelDao, ClCapChannel> {

	@Autowired
	private ClCapChannelDao clCapChannelDao;
	
	public ClCapChannel selectById(String CapSeq) {
		return clCapChannelDao.getByCapSeq(CapSeq);
	}
	
	public List<ClCapChannel> selectAll(){
		return clCapChannelDao.getAll();
	}
	
	
	public int addCapChannel(ClCapChannel capChannel) {
		return clCapChannelDao.insert(capChannel);
	}
	
	
	public int updateByCapSeq(ClCapChannel capChannel) {
		return clCapChannelDao.updateById(capChannel);
	}
	
}
