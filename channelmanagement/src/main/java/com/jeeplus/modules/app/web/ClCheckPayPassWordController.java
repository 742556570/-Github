package com.jeeplus.modules.app.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.service.ClCheckPassWordService;
import com.jeeplus.modules.app.tools.MD5Util;

@Controller
@RequestMapping(value = "api/payPwd")
public class ClCheckPayPassWordController {

	@Autowired
	private ClCheckPassWordService clCheckPassWordService;
    /**
     * 校验支付密码是否存在
     * @param request
     * @param response
     * @return
     */
	@ResponseBody
	@RequestMapping(value = "checkPayPwd")
	public String checkPayPwd(HttpServletRequest request, HttpServletResponse response) {
		ClUsr clUsr = (ClUsr) request.getAttribute("usr");

		String usrCde = clUsr.getUSR_CDE();
		
		String result = clCheckPassWordService.checkPayPwd(usrCde);

		return result;

	}
    /**
     * 设置支付密码
     * @param request
     * @param response
     * @return
     */
	@ResponseBody
	@RequestMapping(value = "setPayPwd")
	public String setPayPwd(HttpServletRequest request, HttpServletResponse response,@RequestParam(value="payppwd" )String PAY_PPWD
			,@RequestParam(value="PAYPWDSIGN" )String PAYPWDSIGN) {
		ClUsr clUsr = (ClUsr) request.getAttribute("usr");
		String result="失败";
		String usrCde = clUsr.getUSR_CDE();
		String PAY_PPWDSIGN = MD5Util.hmacSign(PAY_PPWD);
		if(PAY_PPWDSIGN.equals(PAYPWDSIGN)) {
			result = clCheckPassWordService.updatePayPwd(usrCde, PAY_PPWDSIGN);
		}
		return result;

	}
}
