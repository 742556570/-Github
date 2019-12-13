package com.jeeplus.modules.app.service;



import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.app.dao.ClUsrDao;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.pojo.ResponeJsonObj;

@Service
@Transactional(readOnly = false)
public class ClWithdrawalService extends CrudService<ClUsrDao, ClUsr> {

	@Autowired
	private ClUsrDao clUsrDao;
	
	/**
	 * 校验支付密码是否存在
	 * @param USR_CDE
	 * @return
	 */
	public String mywithdrawal(String USR_CDE) {
		String payPwd = clUsrDao.getByPayPwd(USR_CDE);
		if (payPwd == null) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "未设置支付密码", null);
		} else {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "支付密码已设置", null);
		}
		
	}
}
