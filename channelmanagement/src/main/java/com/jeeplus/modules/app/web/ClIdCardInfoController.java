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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.app.api.fcpp.IdCardOcr;
import com.jeeplus.modules.app.api.rulesengine.RulesEngineApi;
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
import com.jeeplus.modules.app.service.ClFadadaParamsService;
import com.jeeplus.modules.app.service.ClIdCardInfoService;
import com.jeeplus.modules.app.service.ClIdImgService;
import com.jeeplus.modules.app.service.ClUsrContractService;
import com.jeeplus.modules.app.service.UpYunService;

@Controller
@RequestMapping(value = "api/idcard")
public class ClIdCardInfoController {
	
	@Autowired
	private ClIdCardInfoService idCardInfoService;
	
	@Autowired
	private ClIdImgService idImgService;
	
	@Autowired
	private UpYunService upYunService;
	
	@Autowired
	private ClApiInfoService apiService;
	
	@Autowired
	private ClCreditExtService creditExtService;
	
	@Autowired
	private ClBlackListService blkService;
	
	@Autowired
	private ClUsrContractService ucService;
	
	@Autowired
	private ClFadadaParamsService fpService;
	
	private IdCardOcr idCardOcr = new IdCardOcr();
	
	
	@ResponseBody
	@RequestMapping(value = "idCardOcr")
	public String idCardOcr(@RequestParam MultipartFile file1,
			@RequestParam MultipartFile file2,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		

		ClUsr clUsr = (ClUsr)request.getAttribute("usr");
		String usrCde = clUsr.getUSR_CDE();
		
		int allCnt = apiService.getInvokeCntTodayByApiNameAndUsrCode(AppNormalConstants.OCR_API_SERVICE_NAME, usrCde);
		int todayCnt = apiService.getInvokeCntTodayByApiNameAndUsrCode(AppNormalConstants.OCR_API_SERVICE_NAME, usrCde);
		if(allCnt >= AppCommonConstants.getValInt("USRIDCARDOCRLIMIT")) {
			JsonObject responseData = new JsonObject();
			responseData.addProperty("allCnt", allCnt);
			responseData.addProperty("allCntLimit", AppCommonConstants.getValInt("USRIDCARDOCRLIMIT"));
			responseData.addProperty("todayCnt", todayCnt);
			responseData.addProperty("edCntLimit", AppCommonConstants.getValInt("USRIDCARDOCRLIMITEACHDAY"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.OVERALLCNT, "您的识别次数已用完", responseData);
		}
		if(todayCnt >= AppCommonConstants.getValInt("USRIDCARDOCRLIMITEACHDAY")) {
			JsonObject responseData = new JsonObject();
			responseData.addProperty("allCnt", allCnt);
			responseData.addProperty("allCntLimit", AppCommonConstants.getValInt("USRIDCARDOCRLIMIT"));
			responseData.addProperty("todayCnt", todayCnt+1);
			responseData.addProperty("edCntLimit", AppCommonConstants.getValInt("USRIDCARDOCRLIMITEACHDAY"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.OVERDAYCNT, "您今天的识别数已用完,请明天再试", responseData);
		}
		String trd_no = UUID.randomUUID().toString().replace("-", "");
		
		File localfile = new File(AppCommonConstants.getValStr("Local_FilePath")+"/idcard/"+clUsr.getUSR_TEL()+"/"+trd_no+"_front.jpg");
		localfile.mkdirs();
		file1.transferTo(localfile);
		
		File localfile2 = new File(AppCommonConstants.getValStr("Local_FilePath")+"/idcard/"+clUsr.getUSR_TEL()+"/"+trd_no+"_back.jpg");
		localfile2.mkdirs();
		file2.transferTo(localfile2);
		
		
		int a = idImgService.addIdImg(trd_no, usrCde, localfile.getAbsolutePath(), "front", DateUtils.getNowFullFmt());
		int b = idImgService.addIdImg(trd_no, usrCde, localfile2.getAbsolutePath(), "back", DateUtils.getNowFullFmt());
		if(a == 1 && b == 1) {
			long start = new Date().getTime();
			JsonObject front = idCardOcr.idCardOcr(localfile.getAbsolutePath());
			JsonObject back = idCardOcr.idCardOcr(localfile2.getAbsolutePath());
			long end = new Date().getTime();
			JsonObject responseData = new JsonObject();
			responseData.addProperty("trdNo", trd_no);
			responseData.addProperty("allCnt", allCnt+1);
			responseData.addProperty("allCntLimit", AppCommonConstants.getValInt("USRIDCARDOCRLIMIT"));
			responseData.addProperty("todayCnt", todayCnt+1);
			responseData.addProperty("edCntLimit", AppCommonConstants.getValInt("USRIDCARDOCRLIMITEACHDAY"));
			responseData.add("front", front);
			responseData.add("back", back);
			ClApiInfo apiInfo = new ClApiInfo(usrCde, clUsr.getUSR_TEL(), AppNormalConstants.OCR_API_NAME, AppNormalConstants.OCR_API_SERVICE_NAME, DateUtils.getNowFullFmt(), (int)(end-start),trd_no , "SUCCESS", responseData.toString());
			apiService.addInvokeApiInfo(apiInfo);
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", responseData);
		}else {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "图片存储失败", null);
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value = "addIdCardInfo")
	@Transactional(readOnly = false)
	public String addIdCardInfo( 
			@RequestParam(value="name") String CUST_NAME,
			@RequestParam(value="IDNum") String ID_NO,
			@RequestParam(value="gender") String INDIV_SEX,
			@RequestParam(value="birthDay") String BIRTHDAY_DATE,
			@RequestParam(value="race") String INDIV_NATION,
			@RequestParam(value="address") String LIVE_ADDR,
			@RequestParam(value="validDate") String VALID_DATE,
			@RequestParam(value="issuedBy") String SIGN_DEPT,
			@RequestParam(value="trdNo") String trdNo,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
 		ClUsr clUsr = (ClUsr)request.getAttribute("usr");
		boolean isBlk = blkService.getByIdCardNo(ID_NO);
		if(isBlk) {
			ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "账号异常", null);
		}
 		
 		CUST_NAME = StringUtils.delBlank(CUST_NAME);
 		String ucrCde =clUsr.getUSR_CDE();
		ClIdCardInfo cardInfo = new ClIdCardInfo();
		cardInfo.setUSR_CDE(clUsr.getUSR_CDE());
		cardInfo.setCUST_NAME(CUST_NAME);
		cardInfo.setID_NO(ID_NO);
		cardInfo.setINDIV_SEX(INDIV_SEX);
		cardInfo.setBIRTHDAY_DATE(BIRTHDAY_DATE);
		cardInfo.setINDIV_NATION(INDIV_NATION);
		cardInfo.setLIVE_ADDR(LIVE_ADDR);
		cardInfo.setUSR_TEL(clUsr.getUSR_TEL());
		String[] tmp = VALID_DATE.split("-");
		if(tmp.length != 2) {
			ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "有效期解析错误", null);
		}
		
		cardInfo.setVALID_DATE1(tmp[0]);
		if("长期".equals(tmp[1])) {
			cardInfo.setVALID_DATE2("9999-12-31");
		}else {
			cardInfo.setVALID_DATE2(tmp[1]);
		}
		
		cardInfo.setSIGN_DEPT(SIGN_DEPT);
		ClIdImg front = idImgService.getByUsrTrdNoImgType(ucrCde, trdNo, "front");
		ClIdImg back = idImgService.getByUsrTrdNoImgType(ucrCde, trdNo, "back");
		
		File frontFile = new File(front.getIMG_PATH_LC());
		File backFile = new File(back.getIMG_PATH_LC());
		
		Result rfront = upYunService.UploadFile(ucrCde, 1, frontFile);
		Result rback = upYunService.UploadFile(ucrCde, 1, backFile);
		
		if(!rfront.isSucceed() || !rback.isSucceed()) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "上传图片失败", null);
		}
		
		
		cardInfo.setFORNT_IMGPTH_LC(front.getIMG_PATH_LC());
		cardInfo.setBACK_IMGPTH_LC(back.getIMG_PATH_LC());
		cardInfo.setFORNT_IMGPTH((rfront.getMsg()));
		cardInfo.setBACK_IMGPTH((rback.getMsg()));
		cardInfo.setCRT_DT(DateUtils.getNowFullFmt());
		JSONObject prd = creditExtService.queryPrdInfo(clUsr, ID_NO);
		
		String result = idCardInfoService.saveUsrIdCardInfoJsonObj(cardInfo,prd).toString();
		String date  = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		net.sf.json.JSONObject params =  net.sf.json.JSONObject.fromObject(fpService.getParams(ucrCde, "YGSDPH80000002-1", "",date));
		params.put("custName", CUST_NAME);
		params.put("idNo", ID_NO);
		ucService.fadadaContract(CUST_NAME, ID_NO, clUsr.getUSR_TEL(), params, "YGSDPH80000002-1", "身份验证授权书", "1", "签章处", "", clUsr.getUSR_CDE(), "2-1", "","CI02");
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "getIdCardInfo")
	public String getIdCardInfo( 
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		ClUsr clUsr = (ClUsr)request.getAttribute("usr");
		
		String result = idCardInfoService.getByUsrCodeAndValidateJsonObj(clUsr.getUSR_CDE());
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "svCdInfo")
	public String svCdInfo( 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ClUsr clUsr = (ClUsr)request.getAttribute("usr");
		
	   return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "查询成功", null);
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "demo")
	public String demo(@RequestParam MultipartFile[] file,HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pathRoot = request.getSession().getServletContext().getRealPath("");  
        String path=""; 
        List<String> listImagePath=new ArrayList<String>();  
		for (MultipartFile myfile : file) { 
			if(!myfile.isEmpty()){  
				System.out.println("文件长度: " + myfile.getSize());  
				System.out.println("文件类型: " + myfile.getContentType());  
				System.out.println("文件名称: " + myfile.getName());  
				System.out.println("文件原名: " + myfile.getOriginalFilename());  
				System.out.println("========================================");  
				myfile.transferTo(new File("D:/"+myfile.getOriginalFilename()));
				//可以使用FileUtils来保存文件，这里不再列出代码
				//FileUtils.copyInputStreamToFile()方法会自动关闭IO流
				//FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, myfile.getOriginalFilename()));  
			}   
		}
	
	
		return "";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "prePrd")
	public String prePrd(HttpServletRequest request, HttpServletResponse response) {
		ClUsr clUsr = (ClUsr)request.getAttribute("usr");
		JsonObject jo = new JsonObject();
		
		jo.addProperty("prdName", "小保呗");
		jo.addProperty("mount", "2000");
		jo.addProperty("rate", "2%~3%");
		jo.addProperty("dueTime", "12");
		ClIdCardInfo id_card = idCardInfoService.getByUsrCode(clUsr.getUSR_CDE());
		if(id_card  == null) {
			return  ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "查询成功", jo);
		}
		RulesEngineApi re = new RulesEngineApi();
		JSONObject lmt = re.getLimit(clUsr.getUSR_CDE(), id_card.getID_NO());
		String mount = lmt.getString("amount");
		jo.addProperty("mount", mount);
		
		return  ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "查询成功", jo);
	}
	
	public String dealUrl(String tmp) {
		int i = tmp.indexOf("//");
		
		String start = tmp.substring(0,i+2);
		
		String end = tmp.substring(i+2,tmp.length()).replaceAll("//", "/");
		return start+end;
	}

}
