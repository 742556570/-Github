package com.jeeplus.modules.app.dao;

import java.util.List;
import java.util.Map;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.app.entity.ClUsr;

@MyBatisDao
public interface ClUsrDao extends CrudDao<ClUsr>{
	public List<ClUsr> getAll();
	
	public ClUsr getByUsrCode(String USR_CDE);
	
	public ClUsr getByUsrTel(String USR_TEL);
	
	public ClUsr getByToken(String USR_TOKEN);
	
	public int countByUsrTel(String USR_TEL);
	
	public int updateByTel(ClUsr usr);
	
	public int updateUsrGrd(ClUsr usr);
	/**
	 * 获取支付密码
	 * @param USR_CDE
	 * @return
	 */
	public String getByPayPwd(String USR_CDE);
	/**
	 * 设置支付密码
	 * @param USR_CDE
	 * @return
	 */
	public int updateByPayPwd(Map<String, Object> map);

	/**
	 * 24小时内同一IP地址提交申请次数
	 * @param loginIp
	 * @return
	 */
	public int getDayIpCount(String USR_LAST_IP);

	/**
	 * 30日内同一IP地址提交申请次数
	 * @param loginIp
	 * @return
	 */
	public int getMonthIpCount(String USR_LAST_IP);

}
