package com.jeeplus.modules.app.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonObject;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.app.dao.ClUsrDao;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.pojo.ResponeJsonObj;

@Service
@Transactional(readOnly = false)
public class ClCheckPassWordService extends CrudService<ClUsrDao, ClUsr> {

	@Autowired
	private ClUsrDao clUsrDao;

	/**
	 * 校验支付密码是否存在
	 * 
	 * @param USR_CDE
	 * @return
	 */
	public String checkPayPwd(String USR_CDE) {
		String payPwd = clUsrDao.getByPayPwd(USR_CDE);
		JsonObject rd = new JsonObject();
		if (StringUtils.isEmpty(payPwd)) {
			rd.addProperty("isSetPwd", "false");
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "未设置支付密码", rd);
		} else {
			rd.addProperty("isSetPwd", "true");
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "支付密码已设置", rd);
		}

	}

	/**
	 * 校验支付密码
	 * @param USR_CDE
	 * @return
	 */

	public String getPayPwdByUsrCde(String USR_CDE) {
		 ClUsr clUsr = clUsrDao.getByUsrCode(USR_CDE);
		 return clUsr.getPAY_PPWD();
	}

	/**
	 * 设置支付密码
	 * 
	 * @param USR_CDE
	 * @return
	 */
	public String updatePayPwd(String USR_CDE, String PAY_PPWD) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("USR_CDE", USR_CDE);
		map.put("PAY_PPWD", PAY_PPWD);
		int setpayPwd = clUsrDao.updateByPayPwd(map);
		if (setpayPwd == 1) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "支付密码设置成功", null);
		} else {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "支付密码设置失败", null);
		}
	}
}
