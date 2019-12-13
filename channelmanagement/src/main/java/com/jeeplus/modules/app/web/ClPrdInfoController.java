package com.jeeplus.modules.app.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.app.entity.ClPrdInfo;
import com.jeeplus.modules.app.pojo.ResponeJsonObj;
import com.jeeplus.modules.app.service.ClPrdInfoService;

@Controller
@RequestMapping(value = "api/prd")
public class ClPrdInfoController  {

	@Autowired
	private ClPrdInfoService clPrdInfoService;

	@ResponseBody
	@RequestMapping(value = "getAllPrd")
	public String getAllPrd(HttpServletRequest request, HttpServletResponse response) {
		List<ClPrdInfo> result = clPrdInfoService.getAllClPrdInfo();
		if (result == null || result.size() == 0) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "未查得", null).toString();
		} else {
			return ResponeJsonObj.getResJsonObjFromArray(ResponeJsonObj.SUCCESS, "查询成功", result).toString();
		}

	}

	@ResponseBody
	@RequestMapping(value = "getPrdByPrdCde")
	public String getPrdByPrdCde(HttpServletRequest request, HttpServletResponse response) {
		JsonObject resultJson = new JsonObject();
		ClPrdInfo result = clPrdInfoService.getClPrdInfoByPrdCde("hqda");
		if(result == null) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "未查得", null).toString();
		}else {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "查询成功", result).toString();
		}

	}

}
