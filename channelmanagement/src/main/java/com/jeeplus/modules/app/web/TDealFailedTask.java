package com.jeeplus.modules.app.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.modules.app.api.fcpp.IdCardOcr;
import com.jeeplus.modules.app.api.upyun.Result;
import com.jeeplus.modules.app.api.upyun.request.ImgCloudConstant;
import com.jeeplus.modules.app.constant.AppCommonConstants;
import com.jeeplus.modules.app.constant.AppNormalConstants;
import com.jeeplus.modules.app.entity.ClApiInfo;
import com.jeeplus.modules.app.entity.ClIdCardInfo;
import com.jeeplus.modules.app.entity.ClIdImg;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.pojo.ResponeJsonObj;
import com.jeeplus.modules.app.service.ClApiInfoService;
import com.jeeplus.modules.app.service.ClBlackListService;
import com.jeeplus.modules.app.service.ClCreditExtService;
import com.jeeplus.modules.app.service.ClDealFailedService;
import com.jeeplus.modules.app.service.ClIdCardInfoService;
import com.jeeplus.modules.app.service.ClIdImgService;
import com.jeeplus.modules.app.service.UpYunService;
import com.jeeplus.modules.app.task.UnsyncDealApply;
import com.jeeplus.modules.app.task.UnsyncDealFailed;

@Controller
@RequestMapping(value = "api/dealfail")
public class TDealFailedTask {
	
	
	
	@ResponseBody
	@RequestMapping(value = "process")
	public String process(
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		UnsyncDealFailed udf = new UnsyncDealFailed();
		udf.start();
		return "done";

		
	}
	

}
