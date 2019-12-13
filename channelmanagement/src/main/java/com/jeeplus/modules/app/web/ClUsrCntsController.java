package com.jeeplus.modules.app.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.entity.ClUsrCnts;
import com.jeeplus.modules.app.pojo.ResponeJsonObj;
import com.jeeplus.modules.app.service.ClUsrCntsService;
import com.jeeplus.modules.app.utils.CheckNumber;

@Controller
@RequestMapping(value = "api/usrcnts")
public class ClUsrCntsController {

	@Autowired
	private ClUsrCntsService clUsrCntsService;

	@ResponseBody
	@RequestMapping(value = "addUsrCnts")
	@Transactional(readOnly = false)
	public String addUsrCnts(@RequestParam String relationName, @RequestParam String relationPhone,
			@RequestParam String relation, @RequestParam(required = false,defaultValue="") String otherName,
			@RequestParam(required = false,defaultValue="") String otherPhone, @RequestParam(required = false,defaultValue="") String otherRelation,
			HttpServletRequest request, HttpServletResponse response) {
		relationName = org.apache.commons.lang3.StringUtils.deleteWhitespace(relationName);
		relationName = StringEscapeUtils.unescapeHtml4(relationName);
		relationName = StringEscapeUtils.unescapeXml(relationName);
		relationName = StringUtils.StringFilter(relationName);
		
		otherName = org.apache.commons.lang3.StringUtils.deleteWhitespace(otherName);
		otherName = StringEscapeUtils.unescapeHtml4(otherName);
		otherName = StringEscapeUtils.unescapeXml(otherName);
		otherName = StringUtils.StringFilter(otherName);

		if (!StringUtils.isNumber(relationPhone)) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "紧急联系人手机号含有非数字字符,请重试", null);
		}
		boolean isMn = CheckNumber.isMobileNum(relationPhone);
		if(isMn == false) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "手机号码不正确", null).toString();
		}

		if (StringUtils.isNotEmpty(otherPhone)) {
			if (!StringUtils.isNumber(otherPhone)) {
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "联系人手机号含有非数字字符,请重试", null);
			}
			isMn = CheckNumber.isMobileNum(otherPhone);
			if(isMn == false) {
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "手机号码不正确", null).toString();
			}
		}

		ClUsr clUsr = (ClUsr) request.getAttribute("usr");

		if (!clUsrCntsService.chkUsrCntsMdn(clUsr.getUSR_CDE(), relationPhone)) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "紧急联系人电话已存在", null).toString();
		}

		if (otherPhone != null && !clUsrCntsService.chkUsrCntsMdn(clUsr.getUSR_CDE(), otherPhone)) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "联系人电话已存在", null).toString();
		}

		ClUsrCnts clUsrCnts1 = new ClUsrCnts();
		clUsrCnts1.setUSR_CDE(clUsr.getUSR_CDE());
		clUsrCnts1.setREL_RELATION(relation);
		clUsrCnts1.setREL_NAME(StringUtils.delBlank(relationName));
		clUsrCnts1.setREL_MOBILE(relationPhone);
		clUsrCnts1.setCRT_DT(DateUtils.getNowFullFmt());
		clUsrCnts1.setIS_DSPL("1");
		clUsrCnts1.setLVL("first");

		if (otherRelation == null) {
			otherRelation = "";
		}
		if (otherName == null) {
			otherName = "";
		}
		if (otherPhone == null) {
			otherPhone = "";
		}
		ClUsrCnts clUsrCnts2 = new ClUsrCnts();
		clUsrCnts2.setUSR_CDE(clUsr.getUSR_CDE());
		clUsrCnts2.setREL_RELATION(otherRelation);
		clUsrCnts2.setREL_NAME(StringUtils.delBlank(otherName));
		clUsrCnts2.setREL_MOBILE(otherPhone);
		clUsrCnts2.setCRT_DT(DateUtils.getNowFullFmt());
		clUsrCnts2.setIS_DSPL("1");
		clUsrCnts2.setLVL("second");

		List<ClUsrCnts> list = new ArrayList<ClUsrCnts>();
		list.add(clUsrCnts1);
		list.add(clUsrCnts2);

		boolean result = clUsrCntsService.addCusrCnts(list);

		if (result) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "保存成功", null).toString();
		} else {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "保存失败", null).toString();
		}
	}

	@ResponseBody
	@RequestMapping(value = "getUsrCnts")
	public String getUsrCnts(HttpServletRequest request, HttpServletResponse response) {
		ClUsr clUsr = (ClUsr) request.getAttribute("usr");

		JsonObject data = clUsrCntsService.getUsrCntsJsonObj(clUsr.getUSR_CDE());

		return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "查询成功", data).toString();
	}

}
