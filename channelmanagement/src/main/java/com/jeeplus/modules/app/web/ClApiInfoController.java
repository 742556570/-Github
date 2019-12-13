package com.jeeplus.modules.app.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jeeplus.modules.app.constant.AppCommonConstants;
import com.jeeplus.modules.app.constant.AppNormalConstants;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.pojo.ResponeJsonObj;
import com.jeeplus.modules.app.service.ClApiInfoService;

@Controller
@RequestMapping(value = "api/invokedInfo")
public class ClApiInfoController {

	@Autowired
	private ClApiInfoService apiService;


	@ResponseBody
	@RequestMapping(value = "getInfo")
	public String getInfo(
			@RequestParam(value="serviceName" )String serviceName,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		ClUsr clUsr = (ClUsr)request.getAttribute("usr");
		String usrCde = clUsr.getUSR_CDE();
		
		int allCnt = 0;
		int allCntLimit = 0;
		int todayCnt = 0;
		int edCntLimit = 0;
		
		if(serviceName.equals("ocr")) {
			allCnt = apiService.getInvokeCntTodayByApiNameAndUsrCode(AppNormalConstants.OCR_API_SERVICE_NAME, usrCde);
			todayCnt = apiService.getInvokeCntTodayByApiNameAndUsrCode(AppNormalConstants.OCR_API_SERVICE_NAME, usrCde);
			allCntLimit = AppCommonConstants.getValInt("USRIDCARDOCRLIMIT");
			edCntLimit = AppCommonConstants.getValInt("USRIDCARDOCRLIMITEACHDAY");
		}else if(serviceName.equals("verify")) {
			allCnt = apiService.getInvokeCntByApiNameAndUsrCode(AppNormalConstants.LIVE_API_SERVICE_NAME, clUsr.getUSR_CDE());
			todayCnt = apiService.getInvokeCntTodayByApiNameAndUsrCode(AppNormalConstants.LIVE_API_SERVICE_NAME, clUsr.getUSR_CDE());
			allCntLimit = AppCommonConstants.getValInt("USRLIVEVRIFYLIMIT");
			edCntLimit = AppCommonConstants.getValInt("USRLIVEVRIFYLIMITEACHDAY");
		}else if(serviceName.equals("bnkcrd")){
			allCnt = apiService.getInvokeCntByApiNameAndUsrTel(AppNormalConstants.SMS_API_VERIFY_BANKCARD, clUsr.getUSR_TEL());
			todayCnt = apiService.getInvokeCntTodayByApiNameAndUsrTel(AppNormalConstants.SMS_API_VERIFY_BANKCARD, clUsr.getUSR_TEL());
			allCntLimit = AppCommonConstants.getValInt("CHKBANKCARDLIMIT");
			edCntLimit = AppCommonConstants.getValInt("CHKBANKCARDLIMITEACHDAY");
		}else if(serviceName.equals("bnkcrdocr")){
			allCnt = apiService.getInvokeCntByApiNameAndUsrCode(AppNormalConstants.BOCR_API_SERVICE_NAME, usrCde);
			todayCnt = apiService.getInvokeCntTodayByApiNameAndUsrCode(AppNormalConstants.BOCR_API_SERVICE_NAME, usrCde);
			allCntLimit = AppCommonConstants.getValInt("BNKCRDOCRLIMIT");
			edCntLimit = AppCommonConstants.getValInt("BNKCRDOCRLIMITEACHDAY");
		}else if(serviceName.equals("fourchk")){
			allCnt = apiService.getInvokeCntByApiNameAndUsrCode(AppNormalConstants.FOUR_ELEMNET_SERIVE_NAME, usrCde);
			todayCnt = apiService.getInvokeCntTodayByApiNameAndUsrCode(AppNormalConstants.FOUR_ELEMNET_SERIVE_NAME, usrCde);
			allCntLimit = AppCommonConstants.getValInt("FOURCHKLIMIT");
			edCntLimit = AppCommonConstants.getValInt("FOURCHKLIMITEACHDAY");
		}else if(serviceName.equals("verifyFailDetail")) {
			Map<String, String> map = apiService.getLiveFieldTypeAndCnt(usrCde);
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", map);
		}else if(serviceName.equals("selfchk")) {
			allCnt = apiService.getInvokeCntByApiNameAndUsrCode(AppNormalConstants.CHKSELF_API_SERVICE_NAME, usrCde);
			todayCnt = apiService.getInvokeCntTodayByApiNameAndUsrCode(AppNormalConstants.CHKSELF_API_SERVICE_NAME, usrCde);
			allCntLimit = AppCommonConstants.getValInt("USRCHKSELFLIMIT");
			edCntLimit = AppCommonConstants.getValInt("USRCHKSELFLIMITEACHDAY");
		}else if(serviceName.equals("fkVerify")) {
			allCnt = apiService.getInvokeCntByApiNameAndUsrCode(AppNormalConstants.LOANVERIFYFACEFAIL, usrCde);
			todayCnt = apiService.getInvokeCntTodayByApiNameAndUsrCode(AppNormalConstants.LOANVERIFYFACEFAIL, usrCde);
			allCntLimit = AppCommonConstants.getValInt("LoanVerifyFaceFailAllLimit");
			edCntLimit = AppCommonConstants.getValInt("LoanVerifyFaceFailAllLimitEachDay");
		}
		
		JsonObject resultJson = new JsonObject();
		resultJson.addProperty("serviceName", serviceName);
		resultJson.addProperty("allCnt", allCnt);
		resultJson.addProperty("allCntLimit", allCntLimit);
		resultJson.addProperty("todayCnt", todayCnt);
		resultJson.addProperty("edCntLimit", edCntLimit);
		
		return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", resultJson);
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "getAllInfo")
	public String getAllInfo(
			HttpServletRequest request,
			HttpServletResponse response) {
		
		ClUsr clUsr = (ClUsr)request.getAttribute("usr");
		String usrCde = clUsr.getUSR_CDE();
		
		int allCnt = 0;
		int allCntLimit = 0;
		int todayCnt = 0;
		int edCntLimit = 0;
		JsonObject result = new JsonObject();
		
		JsonObject ocr = new JsonObject();
		allCnt = apiService.getInvokeCntTodayByApiNameAndUsrCode(AppNormalConstants.OCR_API_SERVICE_NAME, usrCde);
		todayCnt = apiService.getInvokeCntTodayByApiNameAndUsrCode(AppNormalConstants.OCR_API_SERVICE_NAME, usrCde);
		allCntLimit = AppCommonConstants.getValInt("USRIDCARDOCRLIMIT");
		edCntLimit = AppCommonConstants.getValInt("USRIDCARDOCRLIMITEACHDAY");
		ocr.addProperty("allCnt", allCnt);
		ocr.addProperty("todayCnt", todayCnt);
		ocr.addProperty("allCntLimit", allCntLimit);
		ocr.addProperty("edCntLimit", edCntLimit);
		result.add("ocr", ocr);
			
		JsonObject verify = new JsonObject();
		allCnt = apiService.getInvokeCntByApiNameAndUsrCode(AppNormalConstants.LIVE_API_SERVICE_NAME, clUsr.getUSR_CDE());
		todayCnt = apiService.getInvokeCntTodayByApiNameAndUsrCode(AppNormalConstants.LIVE_API_SERVICE_NAME, clUsr.getUSR_CDE());
		allCntLimit = AppCommonConstants.getValInt("USRLIVEVRIFYLIMIT");
		edCntLimit = AppCommonConstants.getValInt("USRLIVEVRIFYLIMITEACHDAY");
		verify.addProperty("allCnt", allCnt);
		verify.addProperty("todayCnt", todayCnt);
		verify.addProperty("allCntLimit", allCntLimit);
		verify.addProperty("edCntLimit", edCntLimit);
		result.add("verify", verify);
		
		JsonObject bnkcrd = new JsonObject();
		allCnt = apiService.getInvokeCntByApiNameAndUsrTel(AppNormalConstants.SMS_API_VERIFY_BANKCARD, clUsr.getUSR_TEL());
		todayCnt = apiService.getInvokeCntTodayByApiNameAndUsrTel(AppNormalConstants.SMS_API_VERIFY_BANKCARD, clUsr.getUSR_TEL());
		allCntLimit = AppCommonConstants.getValInt("CHKBANKCARDLIMIT");
		edCntLimit = AppCommonConstants.getValInt("CHKBANKCARDLIMITEACHDAY");
		bnkcrd.addProperty("allCnt", allCnt);
		bnkcrd.addProperty("todayCnt", todayCnt);
		bnkcrd.addProperty("allCntLimit", allCntLimit);
		bnkcrd.addProperty("edCntLimit", edCntLimit);
		result.add("bnkcrd", bnkcrd);
		
		JsonObject bnkcrdocr = new JsonObject();
		allCnt = apiService.getInvokeCntByApiNameAndUsrCode(AppNormalConstants.BOCR_API_SERVICE_NAME, usrCde);
		todayCnt = apiService.getInvokeCntTodayByApiNameAndUsrCode(AppNormalConstants.BOCR_API_SERVICE_NAME, usrCde);
		allCntLimit = AppCommonConstants.getValInt("BNKCRDOCRLIMIT");
		edCntLimit = AppCommonConstants.getValInt("BNKCRDOCRLIMITEACHDAY");
		bnkcrdocr.addProperty("allCnt", allCnt);
		bnkcrdocr.addProperty("todayCnt", todayCnt);
		bnkcrdocr.addProperty("allCntLimit", allCntLimit);
		bnkcrdocr.addProperty("edCntLimit", edCntLimit);
		result.add("bnkcrdocr", bnkcrdocr);
		
		JsonObject fourchk = new JsonObject();
		allCnt = apiService.getInvokeCntByApiNameAndUsrCode(AppNormalConstants.FOUR_ELEMNET_SERIVE_NAME, usrCde);
		todayCnt = apiService.getInvokeCntTodayByApiNameAndUsrCode(AppNormalConstants.FOUR_ELEMNET_SERIVE_NAME, usrCde);
		allCntLimit = AppCommonConstants.getValInt("FOURCHKLIMIT");
		edCntLimit = AppCommonConstants.getValInt("FOURCHKLIMITEACHDAY");
		fourchk.addProperty("allCnt", allCnt);
		fourchk.addProperty("todayCnt", todayCnt);
		fourchk.addProperty("allCntLimit", allCntLimit);
		fourchk.addProperty("edCntLimit", edCntLimit);
		result.add("fourchk", fourchk);
			
		Map<String, String> map = apiService.getLiveFieldTypeAndCnt(usrCde);
		result.add("verifyFailDetail", new Gson().fromJson(new Gson().toJson(map), JsonObject.class));
			
		JsonObject selfchk = new JsonObject();
		allCnt = apiService.getInvokeCntByApiNameAndUsrCode(AppNormalConstants.CHKSELF_API_SERVICE_NAME, usrCde);
		todayCnt = apiService.getInvokeCntTodayByApiNameAndUsrCode(AppNormalConstants.CHKSELF_API_SERVICE_NAME, usrCde);
		allCntLimit = AppCommonConstants.getValInt("USRCHKSELFLIMIT");
		edCntLimit = AppCommonConstants.getValInt("USRCHKSELFLIMITEACHDAY");
		selfchk.addProperty("allCnt", allCnt);
		selfchk.addProperty("todayCnt", todayCnt);
		selfchk.addProperty("allCntLimit", allCntLimit);
		selfchk.addProperty("edCntLimit", edCntLimit);
		result.add("selfchk", selfchk);
		
		JsonObject fkVerify = new JsonObject();
		allCnt = apiService.getInvokeCntByApiNameAndUsrCode(AppNormalConstants.LOANVERIFYFACEFAIL, usrCde);
		todayCnt = apiService.getInvokeCntTodayByApiNameAndUsrCode(AppNormalConstants.LOANVERIFYFACEFAIL, usrCde);
		allCntLimit = AppCommonConstants.getValInt("LoanVerifyFaceFailAllLimit");
		edCntLimit = AppCommonConstants.getValInt("LoanVerifyFaceFailAllLimit");
		selfchk.addProperty("allCnt", allCnt);
		selfchk.addProperty("todayCnt", todayCnt);
		selfchk.addProperty("allCntLimit", allCntLimit);
		selfchk.addProperty("edCntLimit", edCntLimit);
		result.add("fkVerify", fkVerify);
		
		
		return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", result);
	}
}
