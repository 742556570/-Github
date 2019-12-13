package com.jeeplus.modules.app.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.modules.app.api.fadada.FadadaDock;
import com.jeeplus.modules.app.service.ClUsrContractService;

@Controller
@RequestMapping(value = "api/sysCollection")
public class FadadaDockController {
	@Autowired
	private ClUsrContractService clUsrContractService;
	/**
	 * 执行法大大上传合同模板 所有的合同模板
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "exFadadaDock")
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {

		FadadaDock fadadaDock = new FadadaDock();
		String result = fadadaDock.ContractTemplateTransmission();

		return result;

	}
	/**
	 * 法大大上传又拍云
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "fadadaDownLoadToUpYun")
	public String fadadaDownLoadToUpYun(HttpServletRequest request, HttpServletResponse response,@RequestParam(defaultValue="",required=false)String dt) throws Exception {
		
		String result = clUsrContractService.fadadaDownLoadToUpYun(dt);
		
		return result;
		
	}
}
