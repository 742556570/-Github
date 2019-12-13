package com.jeeplus.modules.app.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.modules.app.entity.ClCapChannel;
import com.jeeplus.modules.app.entity.ClIdCardInfo;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.pojo.ResponeJsonObj;
import com.jeeplus.modules.app.service.ClCapChannelService;
import com.jeeplus.modules.app.service.ClIdCardInfoService;
import com.jeeplus.modules.app.service.ClUsrService;
@Controller
@RequestMapping(value = "api/withdrawal")
public class ClWithdrawalController {
	@Autowired
	private ClIdCardInfoService clIdCardInfoService;
	@Autowired
	private ClUsrService clUsrService;
	@Autowired
	private ClCapChannelService  capChannelService;
    /**
     * 提现接口给app提供数据
     * @param request
     * @param response
     * @return
     */
	@ResponseBody
	@RequestMapping(value = "getWithdrawalCache")
	public String mywithdrawal(HttpServletRequest request, HttpServletResponse response,@RequestParam(value="USR_TOKEN" )String USR_TOKEN) {
		ClUsr clUsr = (ClUsr)request.getAttribute("usr");
		ClIdCardInfo clIdCardInfo = clIdCardInfoService.getByUsrCode(clUsr.getUSR_CDE());
		ClCapChannel capChannel = capChannelService.selectById("1");
		if(clIdCardInfo != null) {
			clIdCardInfo.setFORNT_IMGPTH_LC("");
			clIdCardInfo.setBACK_IMGPTH_LC("");
		}
		if(capChannel != null) {
			clIdCardInfo.setDETAIL(capChannel.getCAP_INSTU_NAME());
		}
		return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "成功", clIdCardInfo);
	}
}
