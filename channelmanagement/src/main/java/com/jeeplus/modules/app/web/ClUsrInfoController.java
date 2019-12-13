package com.jeeplus.modules.app.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.app.api.dx.DXApi;
import com.jeeplus.modules.app.api.dx.RequestCustInfo;
import com.jeeplus.modules.app.constant.AppCommonConstants;
import com.jeeplus.modules.app.entity.*;
import com.jeeplus.modules.app.pojo.ResponeJsonObj;
import com.jeeplus.modules.app.service.ChChannelInfoService;
import com.jeeplus.modules.app.service.ChCityInfoService;
import com.jeeplus.modules.app.service.ChUsrApplyService;
import com.jeeplus.modules.app.service.ClApiInfoService;
import com.jeeplus.modules.app.service.ClUsrInfoService;
import com.jeeplus.modules.app.service.ClUsrService;
import com.jeeplus.modules.app.utils.AppDateUtils;

@Controller
@RequestMapping(value = "api/usrinfo")
public class ClUsrInfoController extends BaseController{
	
	@Autowired
	private ClUsrInfoService clUsrinfoService;
	
	@Autowired
	private ClUsrService clUsrService;
	
	@Autowired
	private ChUsrApplyService chUsrApplyService;
	
	@Autowired
	private ChChannelInfoService chChannelInfoService;
	
	@Autowired
	private ChCityInfoService chCityInfoService;
	@Autowired
	private ClApiInfoService apiService;
//	@ResponseBody
//	@RequestMapping(value = "addUsrInfo")
//	@Transactional(readOnly = false)
//	public String addUsrInfo(
//			@RequestParam(value="homeProvince" )String LIVE_PROVINCE, 
//			@RequestParam(value="hoPNum")String LIVE_PROVINCE_CODE,
//			@RequestParam(value="homeCity" )String LIVE_CITY,
//			@RequestParam(value="hoCNum" )String LIVE_CITY_CODE,
//			@RequestParam(value="homeArea" )String LIVE_AREA,
//			@RequestParam(value="hoANum" )String LIVE_AREA_CODE,
//			@RequestParam(value="homeAdd" )String LIVE_ADDR,
//			@RequestParam(value="company" )String INDIV_EMP_NAME, 
//			@RequestParam(value="coProvince" )String INDIV_EMP_PROVINCE,
//			@RequestParam(value="coPNum" )String INDIV_EMP_PROVINCE_CODE,
//			@RequestParam(value="coCity" )String INDIV_EMP_CITY,
//			@RequestParam(value="coCNum" )String INDIV_EMP_CITY_CODE,
//			@RequestParam(value="coArea" )String INDIV_EMP_AREA,
//			@RequestParam(value="coANum" )String INDIV_EMP_AREA_CODE,
//			@RequestParam(value="coAdd" )String INDIV_EMP_ADDR,
//			@RequestParam(value="position" )String DUTY,
//			@RequestParam(value="posNum" )String DUTY_CODE,
//			@RequestParam(value="coTel" )String INDIV_EMP_TEL,
//			@RequestParam(value="monIncome" )String MON_INCOME,
//			@RequestParam(value="hiDegree" )String HIGH_DEGREE,
//			HttpServletRequest request,
//			HttpServletResponse response) {
//		LIVE_ADDR = org.apache.commons.lang3.StringUtils.deleteWhitespace(LIVE_ADDR);
//		LIVE_ADDR = StringEscapeUtils.unescapeHtml4(LIVE_ADDR);
//		LIVE_ADDR = StringEscapeUtils.unescapeXml(LIVE_ADDR);
//		LIVE_ADDR = StringUtils.StringFilter(LIVE_ADDR);
//		
//		INDIV_EMP_NAME = org.apache.commons.lang3.StringUtils.deleteWhitespace(INDIV_EMP_NAME);
//		INDIV_EMP_NAME = StringEscapeUtils.unescapeHtml4(INDIV_EMP_NAME);
//		INDIV_EMP_NAME = StringEscapeUtils.unescapeXml(INDIV_EMP_NAME);
//		INDIV_EMP_NAME = StringUtils.StringFilter(INDIV_EMP_NAME);
//		
//		INDIV_EMP_ADDR = org.apache.commons.lang3.StringUtils.deleteWhitespace(INDIV_EMP_ADDR);
//		INDIV_EMP_ADDR = StringEscapeUtils.unescapeHtml4(INDIV_EMP_ADDR);
//		INDIV_EMP_ADDR = StringEscapeUtils.unescapeXml(INDIV_EMP_ADDR);
//		INDIV_EMP_ADDR = StringUtils.StringFilter(INDIV_EMP_ADDR);
//		MON_INCOME = org.apache.commons.lang3.StringUtils.deleteWhitespace(MON_INCOME);
//		HIGH_DEGREE = org.apache.commons.lang3.StringUtils.deleteWhitespace(HIGH_DEGREE);
//		
//		ClUsr clUsr = (ClUsr)request.getAttribute("usr");
//		ClUsrInfo clUsrInfo = new ClUsrInfo();
//		clUsrInfo.setUSR_CDE(clUsr.getUSR_CDE());
//		clUsrInfo.setLIVE_PROVINCE(LIVE_PROVINCE);
//		clUsrInfo.setLIVE_PROVINCE_CODE(LIVE_PROVINCE_CODE);
//		clUsrInfo.setLIVE_CITY(LIVE_CITY);
//		clUsrInfo.setLIVE_CITY_CODE(LIVE_CITY_CODE);
//		clUsrInfo.setLIVE_AREA(LIVE_AREA);
//		clUsrInfo.setLIVE_AREA_CODE(LIVE_AREA_CODE);
//		clUsrInfo.setLIVE_ADDR(StringUtils.delBlank(LIVE_ADDR));
//		clUsrInfo.setINDIV_EMP_NAME(StringUtils.delBlank(INDIV_EMP_NAME));
//		clUsrInfo.setINDIV_EMP_PROVINCE(INDIV_EMP_PROVINCE);
//		clUsrInfo.setINDIV_EMP_PROVINCE_CODE(INDIV_EMP_PROVINCE_CODE);
//		clUsrInfo.setINDIV_EMP_CITY(INDIV_EMP_CITY);
//		clUsrInfo.setINDIV_EMP_CITY_CODE(INDIV_EMP_CITY_CODE);
//		clUsrInfo.setINDIV_EMP_AREA(INDIV_EMP_AREA);
//		clUsrInfo.setINDIV_EMP_AREA_CODE(INDIV_EMP_AREA_CODE);
//		clUsrInfo.setINDIV_EMP_ADDR(StringUtils.delBlank(INDIV_EMP_ADDR));
//		clUsrInfo.setDUTY(DUTY);
//		clUsrInfo.setDUTY_CODE(DUTY_CODE);
//		clUsrInfo.setINDIV_EMP_TEL(INDIV_EMP_TEL);
//		clUsrInfo.setCRT_DT(DateUtils.getNowFullFmt());
//		clUsrInfo.setRemarks("");
//		clUsrInfo.setMON_INCOME(MON_INCOME);
//		clUsrInfo.setHIGH_DEGREE(HIGH_DEGREE);
//		
//		boolean r1 = clUsrinfoService.isUsrInfoExists(clUsr.getUSR_CDE());
//		
//		if(r1) {
//			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "用户信息已存在", null).toString();
//		}
//		
//		boolean result = clUsrinfoService.addUsrInfo(clUsrInfo);
//		
//		if(result) {
//			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "保存成功", null).toString();
//		}else {
//			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "保存失败", null).toString();
//		}
//	}
	
	
	@ResponseBody
	@RequestMapping(value = "addUsrInfo")
	@Transactional(readOnly = false)
	public String addUsrInfo(
			@RequestParam(value="custCityCode" )String custCityCode,
			@RequestParam(value="custCityName" )String custCityName,
			@RequestParam(value="custName" )String custName,
			@RequestParam(value="custTel" )String custTel,
			@RequestParam(value="custSecChannelCode" )String custSecChannelCode,
			@RequestParam(value="postTime" )String postTime,
			@RequestParam(value="custProductCode" )String custProductCode,
			@RequestParam(value="custProductName" )String custProductName,
			@RequestParam(value="custSecProductCode" )String custSecProductCode,
			@RequestParam(value="custSecProductName" )String custSecProductName,
			@RequestParam(value="loginIp" )String loginIp,
			HttpServletRequest request,
			HttpServletResponse response) {
		ClUsr clUsr = (ClUsr)request.getAttribute("usr");
		//24小时内同一IP地址提交申请次数限制
		int dayIpLimit = AppCommonConstants.getValInt("DAYIPLIMIT");
		//30日内同一IP地址提交申请次数限制
		int monthIpLimit = AppCommonConstants.getValInt("MONTHIPLIMIT");
		//24小时内同一IP地址提交申请次数
		int dayIpCount = clUsrService.getDayIpCount(loginIp);
		//30日内同一IP地址提交申请次数
		int monthIpCount = clUsrService.getMonthIpCount(loginIp);
		//判断24小时内同一IP地址提交申请次数（不含本次）是否大于或等于限制次数
		if(dayIpCount-1>=dayIpLimit) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请勿多次提交，过会再试", null).toString();
		}
		//判断30日内同一IP地址提交申请次数（不含本次）是否大于或等于限制次数
		if(monthIpCount-1>=monthIpLimit) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请勿多次提交，过会再试", null).toString();
		}
		String daytime = AppDateUtils.getNowAs_yMDHmsS().substring(0, 10); 
		// 今天的进件数查询
		int daycount = chChannelInfoService.getAllChannelDayCount(custSecChannelCode, daytime);
		
		///查看指定二级渠道编码是否有没有
	/*？？*/	ChChannelInfo chChannelInfoStatus = chChannelInfoService.getChannelStatusByChCde(custSecChannelCode);
        if(custCityName == null || custCityName.equalsIgnoreCase("")) {
			
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请选择城市信息。", null).toString();
			
		}
        
		ChCityInfo chCityInfo = chCityInfoService.getCityInfoByCityName(custCityName);
		
	// 	根据编码查看渠道信息 但是有俩个值的查询  一级二级渠道同时查询
	/*？？*/	ChChannelInfo chChannelInfo = chChannelInfoService.getAllChannelInfoByChCde(custSecChannelCode);
	
		int custTelCnt = chUsrApplyService.getAChUsrApplyCount(custTel);
		
	/*？？*/	List<ChKeyword> chKeyword = chUsrApplyService.getKeyWord(custSecChannelCode);
		
        if(chCityInfo == null) {
        	ClApiInfo apiInfo = new ClApiInfo(clUsr.getUSR_CDE(), custTel, custCityName, custCityName, DateUtils.getNowFullFmt(), 1,"对不起,您所在的城市暂未开放相关业务,感谢您的关注" , "fail", "对不起,您所在的城市暂未开放相关业务,感谢您的关注");
			apiService.addInvokeApiInfo(apiInfo);
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "对不起,您所在的城市暂未开放相关业务,感谢您的关注!", null).toString();
			
		}
        
        if(chChannelInfo == null && !custSecChannelCode.substring(0, 1).equalsIgnoreCase("A")) {
			
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "渠道标识有误", null).toString();
			
		}
        
        if(custTelCnt >= 1 && !custSecChannelCode.substring(0, 1).equalsIgnoreCase("A")) {
			
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "您已经申请过了，无需再次申请", null).toString();
			
		}
        
        if(custTelCnt >= 1 && custSecChannelCode.substring(0, 1).equalsIgnoreCase("A")) {
			
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "该手机号已注册", null).toString();
			
		}
		
		if(chChannelInfoStatus != null && daycount >= Integer.parseInt(chChannelInfoStatus.getCH_DAY_LIMIT()) && Integer.parseInt(chChannelInfoStatus.getCH_DAY_LIMIT()) != 0) {
			
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "提交申请失败，请稍后再试", null).toString();
			
		}
        if(chChannelInfoStatus != null && chChannelInfoStatus.getCH_STATUS().equalsIgnoreCase("1")) {
			
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "提交申请失败，请稍后再试", null).toString();
			
		}
        
        
		
			
		custCityCode = StringUtils.StringFilter(chCityInfo.getCITY_CDE());
		custCityName = StringUtils.StringFilter(custCityName);
		custName = StringUtils.StringFilter(custName);
		custTel = StringUtils.StringFilter(custTel);
		postTime = AppDateUtils.getNowAs_yMDHmsS();
		custProductCode = StringUtils.StringFilter(custProductCode);
		custProductName = StringUtils.StringFilter(custProductName);
		custSecProductCode = StringUtils.StringFilter(custSecProductCode);
		custSecProductName = StringUtils.StringFilter(custSecProductName);
		
		
		
		ChUsrApply chUsrInfo = new ChUsrApply();
		
		if(chKeyword.size() == 1) {
			
		chUsrInfo.setApplCde(UUID.randomUUID().toString().replaceAll("\\-", ""));
		chUsrInfo.setChannelCde(StringUtils.StringFilter(((ChKeyword)chKeyword.get(0)).getChannelCde()));
		chUsrInfo.setChannelName(StringUtils.StringFilter(((ChKeyword)chKeyword.get(0)).getChannelName()));
		chUsrInfo.setCustCityCde(custCityCode);
		chUsrInfo.setCustCityName(custCityName);
		chUsrInfo.setCustName(custName);
		chUsrInfo.setCustTel(custTel);
		chUsrInfo.setCustProductCde(custProductCode);
		chUsrInfo.setCustProductName(custProductName);
		chUsrInfo.setCustChannelCde(StringUtils.StringFilter(((ChKeyword)chKeyword.get(0)).getKeyWordParameter()));
		chUsrInfo.setCustChannelName(StringUtils.StringFilter(((ChKeyword)chKeyword.get(0)).getKeyWord()));
		chUsrInfo.setCustSecChannelCde(StringUtils.StringFilter(((ChKeyword)chKeyword.get(0)).getKeyWordParameter()));
		chUsrInfo.setCustSecChannelName(StringUtils.StringFilter(((ChKeyword)chKeyword.get(0)).getKeyWord()));
		chUsrInfo.setCustSecProductCde(custSecProductCode);
		chUsrInfo.setCustSecProductName(custSecProductName);
		chUsrInfo.setUsrCde(clUsr.getUSR_CDE());
		chUsrInfo.setCrtDt(postTime);
		
		}else if(chKeyword.size() > 1 && custSecChannelCode.substring(0, 1).equalsIgnoreCase("A")){
			
			chUsrInfo.setApplCde(UUID.randomUUID().toString().replaceAll("\\-", ""));
			chUsrInfo.setChannelCde(StringUtils.StringFilter("关键词搜索"));
			chUsrInfo.setChannelName(StringUtils.StringFilter("关键词搜索"));
			chUsrInfo.setCustCityCde(custCityCode);
			chUsrInfo.setCustCityName(custCityName);
			chUsrInfo.setCustName(custName);
			chUsrInfo.setCustTel(custTel);
			chUsrInfo.setCustProductCde(custProductCode);
			chUsrInfo.setCustProductName(custProductName);
			chUsrInfo.setCustChannelCde(StringUtils.StringFilter(custSecChannelCode));
			chUsrInfo.setCustChannelName(StringUtils.StringFilter(custSecChannelCode));
			chUsrInfo.setCustSecChannelCde(StringUtils.StringFilter(custSecChannelCode));
			chUsrInfo.setCustSecChannelName(StringUtils.StringFilter(custSecChannelCode));
			chUsrInfo.setCustSecProductCde(custSecProductCode);
			chUsrInfo.setCustSecProductName(custSecProductName);
			chUsrInfo.setUsrCde(clUsr.getUSR_CDE());
			chUsrInfo.setCrtDt(postTime);
			
		}else {
			
			chUsrInfo.setApplCde(UUID.randomUUID().toString().replaceAll("\\-", ""));
			chUsrInfo.setChannelCde(StringUtils.StringFilter(custSecChannelCode));
			chUsrInfo.setChannelName(StringUtils.StringFilter(chChannelInfoStatus.getCH_NAME()));
			chUsrInfo.setCustCityCde(custCityCode);
			chUsrInfo.setCustCityName(custCityName);
			chUsrInfo.setCustName(custName);
			chUsrInfo.setCustTel(custTel);
			chUsrInfo.setCustProductCde(custProductCode);
			chUsrInfo.setCustProductName(custProductName);
			chUsrInfo.setCustChannelCde(StringUtils.StringFilter(chChannelInfoStatus.getFA_CH_CDE()));
			chUsrInfo.setCustChannelName(StringUtils.StringFilter(chChannelInfoStatus.getFA_CH_NAME()));
			chUsrInfo.setCustSecChannelCde(custSecChannelCode);
			chUsrInfo.setCustSecChannelName(StringUtils.StringFilter(chChannelInfoStatus.getCH_NAME()));
			chUsrInfo.setCustSecProductCde(custSecProductCode);
			chUsrInfo.setCustSecProductName(custSecProductName);
			chUsrInfo.setUsrCde(clUsr.getUSR_CDE());
			chUsrInfo.setCrtDt(postTime);
			
		}
		
		boolean result = chUsrApplyService.insertChUsrApply(chUsrInfo);
		
		List<RequestCustInfo> requestCustInfoList = new ArrayList();
			RequestCustInfo requestCustInfo = new RequestCustInfo();
			requestCustInfo.setAge(null);
			requestCustInfo.setCardNo(null);
			requestCustInfo.setCardType(null);
			requestCustInfo.setCityNo(chUsrInfo.getCustCityCde());
			if(chUsrInfo.getYgyjUsrCde() == null) {
			    requestCustInfo.setCustID(chUsrInfo.getUsrCde());
			}else {
				requestCustInfo.setCustID(chUsrInfo.getYgyjUsrCde());	
			}
			requestCustInfo.setCustName(chUsrInfo.getCustName());
			requestCustInfo.setCustSex(null);
			requestCustInfo.setDataSource("115");  //网贷O2O
			requestCustInfo.setDutype(null);
			if(chUsrInfo.getCustProductCde().equalsIgnoreCase("001")) {
				requestCustInfo.setHasroom("1");
			}else if(chUsrInfo.getCustProductCde().equalsIgnoreCase("003")) {
				requestCustInfo.setHascar("1");
			}else if(chUsrInfo.getCustProductCde().equalsIgnoreCase("004")) {
				requestCustInfo.setHaspolicy("1");
			}
			requestCustInfo.setPhoneNo(chUsrInfo.getCustTel());
			requestCustInfo.setNameSource(chUsrInfo.getCustChannelName());
			requestCustInfoList.add(requestCustInfo);
			DXApi api = new DXApi();
			String resultDX = api.preDX(requestCustInfoList);
			if(resultDX.equalsIgnoreCase("成功")) {
				int transResult = chUsrApplyService.updateDxInfo(chUsrInfo.getApplCde(),AppDateUtils.getNowAs_yMDHmsS(),"成功");
			}else {			
				int transResult = chUsrApplyService.updateDxInfo(chUsrInfo.getApplCde(),AppDateUtils.getNowAs_yMDHmsS(),"失败");
			}
		
		if(result) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "保存成功", null).toString();
		}else {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "保存失败", null).toString();
		}
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "getUsrInfo")
	public String getUsrInfo(HttpServletRequest request, HttpServletResponse response) {
		ClUsr clUsr = (ClUsr)request.getAttribute("usr");
		
		ClUsrInfo usrInfo = clUsrinfoService.getUsrInfo(clUsr.getUSR_CDE());
		
		if(usrInfo == null) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "未查得", null).toString();
		} else {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "查询成功", clUsr).toString();
		}
		
	}
	  
	@ResponseBody
	@RequestMapping(value = "getDegree")
	public String getDegree(HttpServletRequest request, HttpServletResponse response) {
		
		String Degree = AppCommonConstants.getValStr("Degree");
		
		if(Degree == null) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "未查得", null).toString();
		} else {
			 JSONArray jsonArrayDegree = new JSONArray();
			 String[] Degreestrs=Degree.split(",");
			 for(int i=0,len=Degreestrs.length;i<len;i++){
				 String[] str = Degreestrs[i].toString().split("_");
				  JSONObject jo = new JSONObject();
				  jo.put("code",str[0]);
				  jo.put("name",str[1]);
	              jsonArrayDegree.add(jo);
				}
			return ResponeJsonObj.getResJsonObjFromArray(ResponeJsonObj.SUCCESS, "查询成功", jsonArrayDegree);
		}
		
		
		
	}
	
	
	@ResponseBody
	@RequestMapping(value = "addRemaindUsrInfo")
	public String addRemaindUsrInfo(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "USR_CDE") String USR_CDE,@RequestParam(value = "monIncome") String MON_INCOME,@RequestParam(value = "hiDegree") String HIGH_DEGREE) {
		
		
		ClUsrInfo clUsr = new ClUsrInfo();
        clUsr.setUSR_CDE(USR_CDE);
        clUsr.setMON_INCOME(MON_INCOME);
        clUsr.setHIGH_DEGREE(HIGH_DEGREE);
	
        int i = clUsrinfoService.updateUsrInfo(clUsr);
		if(i == 1) {
			
		    return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "修改成功", null).toString();
		
		}else {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "修改失败", null).toString();
		}
		
		
		
	}
	
	
	
//  新增加的代码
	@ResponseBody
	@RequestMapping(value="addUsrInfoCopy")
	@Transactional(readOnly = false)
	public String addUsrInfoCopy(
			@RequestParam(value="custCityCode")         String  custCityCode,
			@RequestParam(value="custCityName")         String  custCityName,
			@RequestParam(value="custName")             String  custName,
			@RequestParam(value="custTel")              String  custTel,
			@RequestParam(value="custSecChannelCode")   String  custSecChannelCode,
			@RequestParam(value="postTime")             String  postTime,
			@RequestParam(value="custProductCode")      String  custProductCode,
			@RequestParam(value="custProductName")      String  custProductName,
            @RequestParam(value="custSecProductCode")   String  custSecProductCode,
            @RequestParam(value="custSecProductName")   String  custSecProductName,
            @RequestParam(value="ygyjUsrCde")           String  ygyjUsrCde,
            HttpServletRequest request,
			HttpServletResponse response
			){
		ClUsr clUsr = (ClUsr)request.getAttribute("usr");
		if(custCityName==null||custCityName.equalsIgnoreCase(""))
		{
			
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请选择城市信息。", null).toString();
		}
		ChCityInfo chCityInfo = chCityInfoService.getCityInfoByCityName(custCityName);
		if(chCityInfo == null) {
        	ClApiInfo apiInfo = new ClApiInfo(clUsr.getUSR_CDE(), custTel, custCityName, custCityName, DateUtils.getNowFullFmt(), 1,"对不起,您所在的城市暂未开放相关业务,感谢您的关注" , "fail", "对不起,您所在的城市暂未开放相关业务,感谢您的关注");
			apiService.addInvokeApiInfo(apiInfo);
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "对不起,您所在的城市暂未开放相关业务,感谢您的关注!", null).toString();
			
		}
		ChChannelInfo chChannelInfo = chChannelInfoService.getAllChannelInfoByChCde(custSecChannelCode);
		   if(chChannelInfo == null && !custSecChannelCode.substring(0, 1).equalsIgnoreCase("A")) {
				
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "渠道标识有误", null).toString();
				
			}
		   int custTelCnt = chUsrApplyService.getAChUsrApplyCount(custTel); 
		   if(custTelCnt >= 1 && !custSecChannelCode.substring(0, 1).equalsIgnoreCase("A")) {
				
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "您已经申请过了，无需再次申请", null).toString();
				
			}
	        
	        if(custTelCnt >= 1 && custSecChannelCode.substring(0, 1).equalsIgnoreCase("A")) {
				
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "该手机号已注册", null).toString();
				
			}
	        
	        String daytime = AppDateUtils.getNowAs_yMDHmsS().substring(0, 10); 
			// 今天的进件数查询
			int daycount = chChannelInfoService.getAllChannelDayCount(custSecChannelCode, daytime);
			ChChannelInfo chChannelInfoStatus = chChannelInfoService.getChannelStatusByChCde(custSecChannelCode);
			if(chChannelInfoStatus != null && daycount >= Integer.parseInt(chChannelInfoStatus.getCH_DAY_LIMIT()) && Integer.parseInt(chChannelInfoStatus.getCH_DAY_LIMIT()) != 0) {
				
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "提交申请失败，请稍后再试", null).toString();
				
			}
			// 开关状态根据查询过来的值进行判断
		    if(chChannelInfoStatus != null && chChannelInfoStatus.getCH_STATUS().equalsIgnoreCase("1")) {
				
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "提交申请失败，请稍后再试", null).toString();
				
			}
	        
			
			//渠道编码传进去   疑问这个参数是不是跟这个无关custSecChannelCode=S20190322902460
		    
			List<ChKeyword> chKeyword = chUsrApplyService.getKeyWord(custSecChannelCode);
			
			
			
		return  null;
	}

	
	
	
	
	

}
