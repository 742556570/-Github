package com.jeeplus.modules.app.web;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.modules.app.entity.ClUpdateVersion;
import com.jeeplus.modules.app.pojo.ResponeJsonObj;
import com.jeeplus.modules.app.service.ClUpdateVersionService;

/**
 * 强制更新接口
 * 
 * @author 阳光保险
 *
 */
@Controller
@RequestMapping(value = "api/sysCollection")
public class ClUpdateVersionController {

	@Autowired
	private ClUpdateVersionService clUpdateVersionService;
	/**
	 * 如果版本不一致需要强制更新
	 * @param request
	 * @param response
	 * @param CHANNEL
	 * @param DATA_SOURCE
	 * @param VERSION
	 * @return
	 * @throws ParseException
	 */
	@ResponseBody
	@RequestMapping(value = "getUpdateVersion")
	public String getWithholdRecover(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "CHANNEL") String CHANNEL, @RequestParam(value = "DATA_SOURCE") String DATA_SOURCE,
			@RequestParam(value = "VERSION") String VERSION) throws ParseException {
		
		ClUpdateVersion clUpdateVersion = clUpdateVersionService.selectByPrimaryKey(CHANNEL, DATA_SOURCE);
		if(!VERSION.equals(clUpdateVersion.getVersion())) {
			JSONObject param = new JSONObject();
			param.put("URL", clUpdateVersion.getUrl());
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "数据版本不一致需要强制更新", param);
		}
		return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "版本一致不需要更新", null);
	}

}
