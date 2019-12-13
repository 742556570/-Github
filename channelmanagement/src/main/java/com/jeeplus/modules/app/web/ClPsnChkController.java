package com.jeeplus.modules.app.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.app.api.fcpp.FaceCompare;
import com.jeeplus.modules.app.api.fcpp.Verify;
import com.jeeplus.modules.app.api.fcpp.Verify2;
import com.jeeplus.modules.app.api.fcpp.VerifyH5;
import com.jeeplus.modules.app.api.upyun.Result;
import com.jeeplus.modules.app.api.upyun.request.ImgCloudConstant;
import com.jeeplus.modules.app.constant.AppCommonConstants;
import com.jeeplus.modules.app.constant.AppNormalConstants;
import com.jeeplus.modules.app.dao.ClIdCardInfoDao;
import com.jeeplus.modules.app.entity.ClApiInfo;
import com.jeeplus.modules.app.entity.ClIdCardInfo;
import com.jeeplus.modules.app.entity.ClPsnCHk;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.pojo.ResponeJsonObj;
import com.jeeplus.modules.app.service.ClApiInfoService;
import com.jeeplus.modules.app.service.ClFadadaParamsService;
import com.jeeplus.modules.app.service.ClIdCardInfoService;
import com.jeeplus.modules.app.service.ClIdImgService;
import com.jeeplus.modules.app.service.ClPsnChkService;
import com.jeeplus.modules.app.service.ClUsrContractService;
import com.jeeplus.modules.app.service.UpYunService;

/**
 * 上传人脸识别图片
 * 
 * @author wangfengzhao
 * @date 2017年12月13
 *
 */
@Controller
@RequestMapping(value = "api/uploadfaceimg")
public class ClPsnChkController {

	@Autowired
	private ClPsnChkService clPsnChkService;
	
	@Autowired
	private UpYunService upYunService;
	
	@Autowired
	private ClApiInfoService apiService;
	
	@Autowired
	private ClIdImgService idImgService;
	
	@Autowired
	private ClIdCardInfoService idCardInfoService;
	
	@Autowired
	private ClFadadaParamsService fpService;
	
	@Autowired
	private ClUsrContractService ucService;
	
	Verify v = new Verify();
	
	Verify2 v2 = new Verify2();
	
	VerifyH5 h5 = new VerifyH5(); 
	
	@ResponseBody
	@RequestMapping(value = "verifyImg")
	public String verifyImg(@RequestParam(value = "image") MultipartFile image,
			@RequestParam(value = "idcard_name") String idcard_name, 
			@RequestParam(value = "idcard_number") String idcard_number,
			HttpServletRequest request, HttpServletResponse response) throws Exception, IOException {
		ClUsr clUsr = (ClUsr) request.getAttribute("usr");
		String usrCde = clUsr.getUSR_CDE();
		
		int allCnt = apiService.getInvokeCntByApiNameAndUsrCode(AppNormalConstants.CHKSELF_API_SERVICE_NAME, clUsr.getUSR_CDE());
		int todayCnt = apiService.getInvokeCntTodayByApiNameAndUsrCode(AppNormalConstants.CHKSELF_API_SERVICE_NAME, clUsr.getUSR_CDE());
		if(allCnt >=  AppCommonConstants.getValInt("USRCHKSELFLIMIT")) {
			JsonObject cpr = new JsonObject();
			cpr.addProperty("allCnt", allCnt);
			cpr.addProperty("allCntLimit", AppCommonConstants.getValInt("USRCHKSELFLIMIT"));
			cpr.addProperty("todayCnt", todayCnt);
			cpr.addProperty("edCntLimit", AppCommonConstants.getValInt("USRCHKSELFLIMITEACHDAY"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.OVERALLCNT, "您的认证次数已用完", cpr);
		}
		
		if(todayCnt >= AppCommonConstants.getValInt("USRCHKSELFLIMITEACHDAY")) {
			JsonObject cpr = new JsonObject();
			cpr.addProperty("allCnt", allCnt);
			cpr.addProperty("allCntLimit", AppCommonConstants.getValInt("USRCHKSELFLIMIT"));
			cpr.addProperty("todayCnt", todayCnt);
			cpr.addProperty("edCntLimit", AppCommonConstants.getValInt("USRCHKSELFLIMITEACHDAY"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.OVERDAYCNT, "您今天的认证次数已用完，请明天再试", cpr);
		}
		String trd_no = UUID.randomUUID().toString().replace("-", "");
		MultipartFile lcMediaPathLc1 = image;
		File localfrontfile1 = new File(
				AppCommonConstants.getValStr("Local_FilePath") + "/psnchk/" + usrCde + "/"+trd_no+"_face1.jpg");
		localfrontfile1.mkdirs();
		lcMediaPathLc1.transferTo(localfrontfile1);
		
		
		int a = idImgService.addIdImg(trd_no, usrCde, localfrontfile1.getAbsolutePath(), "ID_FACE_IMG",
				DateUtils.getNowFullFmt());
		
		if (a > 0) {
			long start = new Date().getTime();
			String s = v2.verify(idcard_name, idcard_number, localfrontfile1.getAbsolutePath());
			long end = new Date().getTime();
			JsonObject responseData = new JsonObject();
			JsonObject fppRes = new Gson().fromJson(s, JsonObject.class);
			responseData.addProperty("trdNo", trd_no);
			responseData.addProperty("allCnt", allCnt + 1);
			responseData.addProperty("allCntLimit", AppCommonConstants.getValInt("USRCHKSELFLIMIT"));
			responseData.addProperty("todayCnt", todayCnt + 1);
			responseData.addProperty("edCntLimit", AppCommonConstants.getValInt("USRCHKSELFLIMITEACHDAY"));
			responseData.add("fppRes", fppRes);
			ClApiInfo apiInfo = new ClApiInfo(usrCde, clUsr.getUSR_TEL(), AppNormalConstants.OCR_API_NAME,
					AppNormalConstants.CHKSELF_API_SERVICE_NAME, DateUtils.getNowFullFmt(), (int) (end - start), trd_no,
					"SUCCESS", responseData.toString());
			apiService.addInvokeApiInfo(apiInfo);
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", responseData);
		} else {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "图片存储失败", null);
		}
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "verifyFaceImg")
	public String verifyFaceImg(
//			@RequestParam(value = "trdNo" , required = false) String trdNo,
			@RequestParam(value = "image_best") MultipartFile image_best,
			@RequestParam(value = "image_env") MultipartFile image_env,
//			@RequestParam(value = "idcard_name" , required = false) String idcard_name, 
//			@RequestParam(value = "idcard_number", required = false) String idcard_number,
			@RequestParam(value = "delta") String delta,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		
		ClUsr clUsr = (ClUsr) request.getAttribute("usr");
		
		Map<String, String> map = getNameAndId(clUsr.getUSR_CDE());
		if(map == null) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请先完成身份证认证", null);
		}
		String idcard_name = map.get("name");
		String idcard_number = map.get("idNo");
		String trdNo = UUID.randomUUID().toString().replaceAll("-", "");
		
		int allCnt = apiService.getInvokeCntByApiNameAndUsrCode(AppNormalConstants.LIVE_API_SERVICE_NAME, clUsr.getUSR_CDE());
		int todayCnt = apiService.getInvokeCntTodayByApiNameAndUsrCode(AppNormalConstants.LIVE_API_SERVICE_NAME, clUsr.getUSR_CDE());
		if(allCnt >=  AppCommonConstants.getValInt("USRLIVEVRIFYLIMIT")) {
			JsonObject cpr = new JsonObject();
			cpr.addProperty("allCnt", allCnt);
			cpr.addProperty("allCntLimit", AppCommonConstants.getValInt("USRLIVEVRIFYLIMIT"));
			cpr.addProperty("todayCnt", todayCnt);
			cpr.addProperty("edCntLimit", AppCommonConstants.getValInt("USRLIVEVRIFYLIMITEACHDAY"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.OVERALLCNT, "您的认证次数已用完", cpr);
		}
		
		if(todayCnt >= AppCommonConstants.getValInt("USRLIVEVRIFYLIMITEACHDAY")) {
			JsonObject cpr = new JsonObject();
			cpr.addProperty("allCnt", allCnt);
			cpr.addProperty("allCntLimit", AppCommonConstants.getValInt("USRLIVEVRIFYLIMIT"));
			cpr.addProperty("todayCnt", todayCnt);
			cpr.addProperty("edCntLimit", AppCommonConstants.getValInt("USRLIVEVRIFYLIMITEACHDAY"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.OVERDAYCNT, "您今天的认证次数已用完，请明天再试", cpr);
		}
		
		ClPsnCHk clPsnCHk1 = new ClPsnCHk();
		String usrCde = clUsr.getUSR_CDE();
		clPsnCHk1.setUsrCde(usrCde);
		clPsnCHk1.setTrdNo(trdNo);

		clPsnCHk1.setCrtDt(DateUtils.getNowFullFmt());
		clPsnCHk1.setCkinfo("pre_verify");

		MultipartFile lcMediaPathLc1 = image_best;
		File localfrontfile1 = new File(
				AppCommonConstants.getValStr("Local_FilePath") + "/psnchk/" + usrCde + "/"+trdNo+"_face1.jpg");
		localfrontfile1.mkdirs();
		lcMediaPathLc1.transferTo(localfrontfile1);

		clPsnCHk1.setMediaPath(null);
		clPsnCHk1.setMediaPathLc(
				AppCommonConstants.getValStr("Local_FilePath") + "/psnchk/" + usrCde + "/"+trdNo+"_face1.jpg");
		clPsnCHk1.setMediaDesc("");
		
		
		ClPsnCHk clPsnCHk2 = new ClPsnCHk();
		clPsnCHk2.setUsrCde(usrCde);
		clPsnCHk2.setTrdNo(trdNo);

		clPsnCHk2.setCrtDt(DateUtils.getNowFullFmt());
		clPsnCHk2.setCkinfo("pre_verify");

		MultipartFile lcMediaPathLc2 = image_env;
		File localfrontfile2 = new File(
				AppCommonConstants.getValStr("Local_FilePath") + "/psnchk/" + usrCde + "/"+trdNo+"_face2.jpg");
		localfrontfile2.mkdirs();
		lcMediaPathLc2.transferTo(localfrontfile2);
		
		clPsnCHk2.setMediaPath(null);
		clPsnCHk2.setMediaPathLc(
				AppCommonConstants.getValStr("Local_FilePath") + "/psnchk/" + usrCde + "/"+trdNo+"_face2.jpg");
		clPsnCHk2.setMediaDesc("");

		long start = new Date().getTime();
//		JsonObject cpr = fc.faceCompare(idcard_name, idcard_number, delta, localfrontfile1.getAbsolutePath(), localfrontfile2.getAbsolutePath());
		String s = v.verify(idcard_name, idcard_number, delta, localfrontfile1.getAbsolutePath(), localfrontfile2.getAbsolutePath());
		long end = new Date().getTime();
		System.out.println(s);
		JsonObject fppRes = new Gson().fromJson(s, JsonObject.class);
		Map<String, String> fppResMap = clPsnChkService.dealJson(fppRes);
		String scores = fppResMap.get("scores");
		clPsnCHk1.setScores(scores);
		clPsnCHk2.setScores(scores);
		JsonObject cpr = new JsonObject();
		List<ClPsnCHk> list = new ArrayList<ClPsnCHk>();
		list.add(clPsnCHk1);
		list.add(clPsnCHk2);
		String result = clPsnChkService.saveClPsnChkImageJsonObj(list);
		JsonObject save = new Gson().fromJson(result, JsonObject.class);
		if(fppResMap.get("result").equals("true")) {
			ClApiInfo apiInfo = new ClApiInfo(usrCde, clUsr.getUSR_TEL(), AppNormalConstants.LIVE_API_NAME, AppNormalConstants.LIVE_API_SERVICE_NAME, DateUtils.getNowFullFmt(), (int)(end-start),idcard_name+", "+idcard_number+"," +localfrontfile1.getAbsolutePath()+","+ localfrontfile2.getAbsolutePath(), "SUCCESS", s);
			apiService.addInvokeApiInfo(apiInfo);
		}else {
			ClApiInfo apiInfo = new ClApiInfo(usrCde, clUsr.getUSR_TEL(), AppNormalConstants.LIVE_API_NAME, AppNormalConstants.LIVE_API_SERVICE_NAME, DateUtils.getNowFullFmt(), (int)(end-start),idcard_name+", "+idcard_number+"," +localfrontfile1.getAbsolutePath()+","+ localfrontfile2.getAbsolutePath(), "FAIL", s);
			apiService.addInvokeApiInfo(apiInfo);
		}
		Map<String, String> detail = apiService.getLiveFieldTypeAndCnt(usrCde);
		JsonObject detailJson = new Gson().fromJson(new Gson().toJson(detail), JsonObject.class);
		cpr.addProperty("result", fppResMap.get("result"));
		cpr.addProperty("msg", fppResMap.get("msg"));
		cpr.addProperty("allCnt", allCnt+1);
		cpr.addProperty("allCntLimit", AppCommonConstants.getValInt("USRLIVEVRIFYLIMIT"));
		cpr.addProperty("todayCnt", todayCnt+1);
		cpr.addProperty("edCntLimit", AppCommonConstants.getValInt("USRLIVEVRIFYLIMITEACHDAY"));
		cpr.add("detail", detailJson);
		cpr.add("fppRes", fppRes);
		cpr.add("list", new Gson().fromJson(new Gson().toJson(list), JsonArray.class));
		String date  = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		net.sf.json.JSONObject params =  net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000003-1", "",date));
		ucService.fadadaContract(params.getString("custName"), params.getString("idNo"), clUsr.getUSR_TEL(), params, "YGSDPH80000003-1", "数字证书确认及授权书", "1", "签章处", "", clUsr.getUSR_CDE(), "2-2", "","CI02");
		
		System.out.println(ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", cpr).toString());
		return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", cpr).toString();
	}
	
	
	@ResponseBody
	@RequestMapping(value = "fkVerifyFaceImg")
	public String fkVerifyFaceImg(
//			@RequestParam(value = "trdNo" , required = false) String trdNo,
			@RequestParam(value = "image_best") MultipartFile image_best,
			@RequestParam(value = "image_env") MultipartFile image_env,
//			@RequestParam(value = "idcard_name" , required = false) String idcard_name, 
//			@RequestParam(value = "idcard_number", required = false) String idcard_number,
			@RequestParam(value = "delta") String delta,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		
		ClUsr clUsr = (ClUsr) request.getAttribute("usr");
		
		Map<String, String> map = getNameAndId(clUsr.getUSR_CDE());
		if(map == null) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请先完成身份证认证", null);
		}
		String idcard_name = map.get("name");
		String idcard_number = map.get("idNo");
		String trdNo = UUID.randomUUID().toString().replaceAll("-", "");
		
		int allCnt = apiService.getInvokeCntByApiNameAndUsrCode(AppNormalConstants.LOANVERIFYFACEFAIL, clUsr.getUSR_CDE());
		int todayCnt = apiService.getInvokeCntTodayByApiNameAndUsrCode(AppNormalConstants.LOANVERIFYFACEFAIL, clUsr.getUSR_CDE());
		if(allCnt >=  AppCommonConstants.getValInt("LoanVerifyFaceFailAllLimit")) {
			JsonObject cpr = new JsonObject();
			cpr.addProperty("allCnt", allCnt);
			cpr.addProperty("allCntLimit", AppCommonConstants.getValInt("LoanVerifyFaceFailAllLimit"));
			cpr.addProperty("todayCnt", todayCnt);
			cpr.addProperty("edCntLimit", AppCommonConstants.getValInt("LoanVerifyFaceFailAllLimitEachDay"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.OVERALLCNT, "您的提现认证允许失败次数已用完", cpr);
		}
		
		if(todayCnt >= AppCommonConstants.getValInt("LoanVerifyFaceFailAllLimitEachDay")) {
			JsonObject cpr = new JsonObject();
			cpr.addProperty("allCnt", allCnt);
			cpr.addProperty("allCntLimit", AppCommonConstants.getValInt("LoanVerifyFaceFailAllLimit"));
			cpr.addProperty("todayCnt", todayCnt);
			cpr.addProperty("edCntLimit", AppCommonConstants.getValInt("LoanVerifyFaceFailAllLimitEachDay"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.OVERDAYCNT, "您今天的提现认证允许失败次数已用完，请明天再试", cpr);
		}
		
		ClPsnCHk clPsnCHk1 = new ClPsnCHk();
		String usrCde = clUsr.getUSR_CDE();
		clPsnCHk1.setUsrCde(usrCde);
		clPsnCHk1.setTrdNo(trdNo);

		clPsnCHk1.setCrtDt(DateUtils.getNowFullFmt());
		clPsnCHk1.setCkinfo("pre_verify");

		MultipartFile lcMediaPathLc1 = image_best;
		File localfrontfile1 = new File(
				AppCommonConstants.getValStr("Local_FilePath") + "/psnchk/" + usrCde + "/"+trdNo+"_face1.jpg");
		localfrontfile1.mkdirs();
		lcMediaPathLc1.transferTo(localfrontfile1);

		clPsnCHk1.setMediaPath(null);
		clPsnCHk1.setMediaPathLc(
				AppCommonConstants.getValStr("Local_FilePath") + "/psnchk/" + usrCde + "/"+trdNo+"_face1.jpg");
		clPsnCHk1.setMediaDesc("");
		
		
		ClPsnCHk clPsnCHk2 = new ClPsnCHk();
		clPsnCHk2.setUsrCde(usrCde);
		clPsnCHk2.setTrdNo(trdNo);

		clPsnCHk2.setCrtDt(DateUtils.getNowFullFmt());
		clPsnCHk2.setCkinfo("pre_verify");

		MultipartFile lcMediaPathLc2 = image_env;
		File localfrontfile2 = new File(
				AppCommonConstants.getValStr("Local_FilePath") + "/psnchk/" + usrCde + "/"+trdNo+"_face2.jpg");
		localfrontfile2.mkdirs();
		lcMediaPathLc2.transferTo(localfrontfile2);
		
		clPsnCHk2.setMediaPath(null);
		clPsnCHk2.setMediaPathLc(
				AppCommonConstants.getValStr("Local_FilePath") + "/psnchk/" + usrCde + "/"+trdNo+"_face2.jpg");
		clPsnCHk2.setMediaDesc("");

		long start = new Date().getTime();
//		JsonObject cpr = fc.faceCompare(idcard_name, idcard_number, delta, localfrontfile1.getAbsolutePath(), localfrontfile2.getAbsolutePath());
		String s = v.verify(idcard_name, idcard_number, delta, localfrontfile1.getAbsolutePath(), localfrontfile2.getAbsolutePath());
		long end = new Date().getTime();
		System.out.println(s);
		JsonObject fppRes = new Gson().fromJson(s, JsonObject.class);
		Map<String, String> fppResMap = clPsnChkService.dealJson(fppRes);
		String scores = fppResMap.get("scores");
		clPsnCHk1.setScores(scores);
		clPsnCHk2.setScores(scores);
		JsonObject cpr = new JsonObject();
		List<ClPsnCHk> list = new ArrayList<ClPsnCHk>();
		list.add(clPsnCHk1);
		list.add(clPsnCHk2);
		String result = clPsnChkService.saveClPsnChkImageJsonObj(list);
		JsonObject save = new Gson().fromJson(result, JsonObject.class);
		if(fppResMap.get("result").equals("true")) {
			ClApiInfo apiInfo = new ClApiInfo(usrCde, clUsr.getUSR_TEL(), AppNormalConstants.LIVE_API_NAME, AppNormalConstants.LIVE_API_SERVICE_NAME+"_3", DateUtils.getNowFullFmt(), (int)(end-start),idcard_name+", "+idcard_number+"," +localfrontfile1.getAbsolutePath()+","+ localfrontfile2.getAbsolutePath(), "SUCCESS", s);
			apiService.addInvokeApiInfo(apiInfo);
		}else {
			ClApiInfo apiInfo = new ClApiInfo(usrCde, clUsr.getUSR_TEL(), AppNormalConstants.LIVE_API_NAME, AppNormalConstants.LIVE_API_SERVICE_NAME+"_3", DateUtils.getNowFullFmt(), (int)(end-start),idcard_name+", "+idcard_number+"," +localfrontfile1.getAbsolutePath()+","+ localfrontfile2.getAbsolutePath(), "FAIL", s);
			apiService.addInvokeApiInfo(apiInfo);
			apiInfo = new ClApiInfo(usrCde, clUsr.getUSR_TEL(), AppNormalConstants.LIVE_API_NAME, AppNormalConstants.LOANVERIFYFACEFAIL, DateUtils.getNowFullFmt(), (int)(end-start),idcard_name+", "+idcard_number, "FAIL", s); 
			apiService.addInvokeApiInfo(apiInfo);
		}
		Map<String, String> detail = apiService.getLiveFieldTypeAndCnt(usrCde);
		JsonObject detailJson = new Gson().fromJson(new Gson().toJson(detail), JsonObject.class);
		cpr.addProperty("result", fppResMap.get("result"));
		cpr.addProperty("msg", fppResMap.get("msg"));
		cpr.addProperty("allCnt", allCnt+1);
		cpr.addProperty("allCntLimit", AppCommonConstants.getValInt("LoanVerifyFaceFailAllLimit"));
		cpr.addProperty("todayCnt", todayCnt+1);
		cpr.addProperty("edCntLimit", AppCommonConstants.getValInt("LoanVerifyFaceFailAllLimitEachDay"));
		cpr.add("detail", detailJson);
		cpr.add("fppRes", fppRes);
		cpr.add("list", new Gson().fromJson(new Gson().toJson(list), JsonArray.class));
		String date  = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		net.sf.json.JSONObject params =  net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000003-1", "",date));
		ucService.fadadaContract(params.getString("custName"), params.getString("idNo"), clUsr.getUSR_TEL(), params, "YGSDPH80000003-1", "数字证书确认及授权书", "1", "签章处", "", clUsr.getUSR_CDE(), "2-2", "","CI02");
		
		System.out.println(ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", cpr).toString());
		return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", cpr).toString();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * 保存人脸识别的图片，本地一份，远程服务器一份
	 * 
	 * @param mediaSeq
	 * @param usrCde
	 * @param trdNo
	 * @param mediaPath
	 * @param mediaPathLc
	 * @param mediaDesc
	 * @param crtDt
	 * @param ckinfo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "addfaceimage")
	@Transactional(readOnly = false)
	public String addFaceImage(
			@RequestParam(value = "trdNo",required = false) String trdNo,
			@RequestParam(value = "mediaDesc") String mediaDesc, 
			@RequestParam(value = "ckinfo" ,required=false) String ckinfo,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ClUsr clUsr = (ClUsr) request.getAttribute("usr");
		HashMap<String, String> param = new HashMap<String,String>();
		param.put("usrCde", clUsr.getUSR_CDE());
		param.put("trdNo", trdNo);
		List<ClPsnCHk> clPsnChks = clPsnChkService.getListByUsrAndTrdNo(param);
		if(clPsnChks.size() < 2) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请先完成活体识别", null);
		}
		
		File image_bestFile = new File(clPsnChks.get(0).getMediaPathLc());
		File image_envFile = new File(clPsnChks.get(1).getMediaPathLc());
		
		ClPsnCHk clPsnCHk1 = new ClPsnCHk();
		String usrCde = clUsr.getUSR_CDE();
		clPsnCHk1.setCkinfo(ckinfo);
		 
		clPsnCHk1.setMediaDesc(mediaDesc);
		
		ClPsnCHk clPsnCHk2 = new ClPsnCHk();
		clPsnCHk2.setCkinfo(ckinfo);
		
		clPsnCHk2.setMediaDesc(mediaDesc);
		
		Result rimage_best = upYunService.UploadFile(clUsr.getUSR_CDE(), 1, image_bestFile);
		Result rimage_env = upYunService.UploadFile(clUsr.getUSR_CDE(), 1, image_envFile);
		
		if(!rimage_best.isSucceed() || !rimage_env.isSucceed()) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "上传图片失败", null);
		}
		
		clPsnCHk1.setMediaPath(dealUrl(ImgCloudConstant.ADD_API+"/"+rimage_best.getMsg()));
		clPsnCHk2.setMediaPath(dealUrl(ImgCloudConstant.ADD_API+"/"+rimage_env.getMsg()));
		
		clPsnCHk1.setMediaSeq(clPsnChks.get(0).getMediaSeq());
		clPsnCHk2.setMediaSeq(clPsnChks.get(1).getMediaSeq());
		
		
		List<ClPsnCHk> list = new ArrayList<ClPsnCHk>();
		list.add(clPsnCHk1);
		list.add(clPsnCHk2);
		boolean result = clPsnChkService.updateByPrimaryKeySelective(list);
		clPsnCHk1.setMediaPathLc("");
		clPsnCHk2.setMediaPathLc("");
		list.clear();
		list.add(clPsnCHk1);
		list.add(clPsnCHk2);
		if(result) {
			return ResponeJsonObj.getResJsonObjFromArray(ResponeJsonObj.SUCCESS, "保存成功", list);
		}else {
			return ResponeJsonObj.getResJsonObjFromArray(ResponeJsonObj.FAIL, "保存失败", null);
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value = "getFaceImgByUsr")
	public String getFaceImage(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ClUsr clUsr = (ClUsr) request.getAttribute("usr");
		String usrCde = clUsr.getUSR_CDE();
		
		String result = clPsnChkService.getByUsrCde(usrCde);
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "getFaceImgByUsrAndTrdNo")
	public String getFaceImageByTrdNo(
			@RequestParam(value = "trdNo") String trdNo,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ClUsr clUsr = (ClUsr) request.getAttribute("usr");
		String usrCde = clUsr.getUSR_CDE();
		
		HashMap<String, String> param = new HashMap<String,String>();
		param.put("usrCde", usrCde);
		param.put("trdNo", trdNo);
		
		String result = clPsnChkService.getByUsrAndTrdNo(param);
		return result;
	}
	
	public String dealUrl(String tmp) {
		int i = tmp.indexOf("//");
		
		String start = tmp.substring(0,i+2);
		
		String end = tmp.substring(i+2,tmp.length()).replaceAll("//", "/");
		return start+end;
	}
	
	
	public Map<String, String> getNameAndId(String usrCde) {
		HashMap<String, String> map = new HashMap<String,String>();
		ClIdCardInfo idCardInfo = idCardInfoService.getByUsrCode(usrCde);
		if(idCardInfo == null) {
			return null;
		}
		map.put("name", idCardInfo.getCUST_NAME());
		map.put("idNo",idCardInfo.getID_NO());
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "getToken")
	public String getToken(HttpServletRequest request, HttpServletResponse response) {
		
		
		ClUsr clUsr = (ClUsr) request.getAttribute("usr");
		
		Map<String, String> map = getNameAndId(clUsr.getUSR_CDE());
		if(map == null) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请先完成身份证认证", null);
		}
		String idcard_name = map.get("name");
		String idcard_number = map.get("idNo");
		String trdNo = UUID.randomUUID().toString().replaceAll("-", "");
		
		String date  = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		net.sf.json.JSONObject params =  net.sf.json.JSONObject.fromObject(fpService.getParams(clUsr.getUSR_CDE(), "YGSDPH80000003-1", "",date));
		ucService.fadadaContract(params.getString("custName"), params.getString("idNo"), clUsr.getUSR_TEL(), params, "YGSDPH80000003-1", "数字证书确认及授权书", "1", "签章处", "", clUsr.getUSR_CDE(), "2-2", "","CI02");
		int allCnt = apiService.getInvokeCntByApiNameAndUsrCode(AppNormalConstants.LIVE_API_SERVICE_NAME, clUsr.getUSR_CDE());
		int todayCnt = apiService.getInvokeCntTodayByApiNameAndUsrCode(AppNormalConstants.LIVE_API_SERVICE_NAME, clUsr.getUSR_CDE());
		if(allCnt >=  AppCommonConstants.getValInt("USRLIVEVRIFYLIMIT")) {
			JsonObject cpr = new JsonObject();
			cpr.addProperty("allCnt", allCnt);
			cpr.addProperty("allCntLimit", AppCommonConstants.getValInt("USRLIVEVRIFYLIMIT"));
			cpr.addProperty("todayCnt", todayCnt);
			cpr.addProperty("edCntLimit", AppCommonConstants.getValInt("USRLIVEVRIFYLIMITEACHDAY"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.OVERALLCNT, "您的认证次数已用完", cpr);
		}
		
		if(todayCnt >= AppCommonConstants.getValInt("USRLIVEVRIFYLIMITEACHDAY")) {
			JsonObject cpr = new JsonObject();
			cpr.addProperty("allCnt", allCnt);
			cpr.addProperty("allCntLimit", AppCommonConstants.getValInt("USRLIVEVRIFYLIMIT"));
			cpr.addProperty("todayCnt", todayCnt);
			cpr.addProperty("edCntLimit", AppCommonConstants.getValInt("USRLIVEVRIFYLIMITEACHDAY"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.OVERDAYCNT, "您今天的认证次数已用完，请明天再试", cpr);
		}
		
		ClPsnCHk clPsnCHk1 = new ClPsnCHk();
		String usrCde = clUsr.getUSR_CDE();
		clPsnCHk1.setUsrCde(usrCde);
		clPsnCHk1.setTrdNo(trdNo);

		clPsnCHk1.setCrtDt(DateUtils.getNowFullFmt());
		clPsnCHk1.setCkinfo("getToken");

		

		long start = new Date().getTime();
//		JsonObject cpr = fc.faceCompare(idcard_name, idcard_number, delta, localfrontfile1.getAbsolutePath(), localfrontfile2.getAbsolutePath());
		String s = h5.getToken(idcard_name, idcard_number, trdNo);
		long end = new Date().getTime();
		System.out.println(s);
		JsonObject result = new Gson().fromJson(s, JsonObject.class);
		
		
		
		if(result.has("biz_id") && result.has("token")) {
			String biz_id = result.get("biz_id").getAsString();
			String token = result.get("token").getAsString();
			
			JSONObject j = new JSONObject();
			j.put("biz_id", biz_id);
			j.put("token", token);
			clPsnCHk1.setMediaDesc(j.toJSONString());
			List<ClPsnCHk> list = new ArrayList<ClPsnCHk>();
			list.add(clPsnCHk1);
			String saveResult = clPsnChkService.saveClPsnChkImageJsonObj(list);
			ClApiInfo apiInfo = new ClApiInfo(usrCde, clUsr.getUSR_TEL(), AppNormalConstants.LIVE_API_NAME, AppNormalConstants.GET_TOKEN, DateUtils.getNowFullFmt(), (int)(end-start),idcard_name+", "+idcard_number+","+trdNo , "SUCCESS", s);
			apiService.addInvokeApiInfo(apiInfo);
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", j);
		} else {
			ClApiInfo apiInfo = new ClApiInfo(usrCde, clUsr.getUSR_TEL(), AppNormalConstants.LIVE_API_NAME, AppNormalConstants.GET_TOKEN, DateUtils.getNowFullFmt(), (int)(end-start),idcard_name+", "+idcard_number+"," +trdNo, "FAIL", s);
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "", null);
		}
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "getResult")
	public String getResult(
			@RequestParam(value = "biz_id") String biz_id,
			@RequestParam(value = "step") String step,/*1、资料填写人脸识别2、设置密码人脸识别3、提现人脸识别*/
			HttpServletRequest request, HttpServletResponse response) {
		ClUsr clUsr = (ClUsr) request.getAttribute("usr");
		String usrCde = clUsr.getUSR_CDE();
		Map<String, String> map = getNameAndId(clUsr.getUSR_CDE());
		if(map == null) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请先完成身份证认证", null);
		}
		String idcard_name = map.get("name");
		String idcard_number = map.get("idNo");
		
		
		
		
		
		List<ClPsnCHk> list = clPsnChkService.getListByUsrCde(clUsr.getUSR_CDE());
		ClPsnCHk target = null;
		int step3FailedCountT = 0;
		int step3FailedCountA = 0;
		String nowDateStr = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		for (ClPsnCHk clPsnCHk : list) {
			
			if(clPsnCHk.getCkinfo().equals("getToken")) {
				String jstr = clPsnCHk.getMediaDesc();
				JSONObject jo = JSONObject.parseObject(jstr);
				String tmp = jo.getString("biz_id");
				if(tmp.equals(biz_id)) {
					target = clPsnCHk;
					break;
				}
			}
			
			String clDate = clPsnCHk.getCrtDt();
			if("3".equals(clPsnCHk.getMediaDesc()) && "false".equals(clPsnCHk.getCkinfo()) ) {
				step3FailedCountA ++;
				if(clDate.contains(nowDateStr)) {
					step3FailedCountT ++;
				}
			}
			
		}
		
		if(target == null) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请先获取token", null);
		}
		if(step3FailedCountA >= AppCommonConstants.getValInt("LoanVerifyFaceFailAllLimit")) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "您的提现认证允许失败次数已用完", null);
		}
		if(step3FailedCountT >= AppCommonConstants.getValInt("LoanVerifyFaceFailAllLimitEachDay")) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "您今天的提现认证允许失败次数已用完，请明天再试", null);
		}
		
		

		long start = new Date().getTime();
//		JsonObject cpr = fc.faceCompare(idcard_name, idcard_number, delta, localfrontfile1.getAbsolutePath(), localfrontfile2.getAbsolutePath());
		String s = h5.getResult(biz_id);
		long end = new Date().getTime();
		System.out.println(s);
		JSONObject result = JSONObject.parseObject(s);
		
		String status = result.getString("status");
		
		JSONObject rj = new JSONObject();
		
		if(status.equals("OK")) {
			ClApiInfo apiInfo = new ClApiInfo(usrCde, clUsr.getUSR_TEL(), AppNormalConstants.LIVE_API_NAME, AppNormalConstants.GET_RESULT, DateUtils.getNowFullFmt(), (int)(end-start),idcard_name+", "+idcard_number+","+biz_id , "SUCCESS", s);
			apiService.addInvokeApiInfo(apiInfo);
			
			Map<String, String> dMap = clPsnChkService.dealJsonGetResult(result);
			String verifyResult = dMap.get("result");
			target.setScores(dMap.get("scores"));
			JSONObject images = result.getJSONObject("images");
			String base64imgStr = images.getString("image_best");
			String[] imgInfo = base64imgStr.split(",");
			if(imgInfo.length != 2) {
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "保存图片失败", null);
			}
			String imgdata = imgInfo[1];
			File localfrontfiledir = new File(
					AppCommonConstants.getValStr("Local_FilePath") + "/psnchk/" + usrCde );
			if(!localfrontfiledir.exists()) {
				localfrontfiledir.mkdirs();
			}
			File jpg = new File(localfrontfiledir, "face1.jpg");
			if(!jpg.exists()) {
				try {
					jpg.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
					return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "保存图片失败", null);
				}
			}
			try {
				byte[] bt = Base64.getDecoder().decode(imgdata);
				FileOutputStream fos = new FileOutputStream(jpg);
				fos.write(bt);
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "保存图片失败", null);
			}
			
			target.setMediaPathLc(jpg.getAbsolutePath());
			target.setMediaDesc(step);
			if("false".equals(verifyResult)) {
				target.setCkinfo("getResultFail");
				rj.put("result", "false");
				rj.put("msg", dMap.get("msg"));
				rj.put("fccResult", result);
				List<ClPsnCHk> updateClPsns = new ArrayList<ClPsnCHk>();
				updateClPsns.add(target);
				boolean updateRes = clPsnChkService.updateByPrimaryKeySelective(updateClPsns);
				
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "验证失败", rj);
			} else {
				target.setCkinfo("true");
				try {
					Result rimage_best = upYunService.UploadFile(clUsr.getUSR_CDE(), 1, jpg);
					if(!rimage_best.isSucceed()) {
						return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "上传图片失败", null);
					}
					target.setMediaPath(dealUrl(ImgCloudConstant.ADD_API+"/"+rimage_best.getMsg()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "上传云影像失败", null);
				}
				List<ClPsnCHk> updateClPsns = new ArrayList<ClPsnCHk>();
				updateClPsns.add(target);
				boolean updateRes = clPsnChkService.updateByPrimaryKeySelective(updateClPsns);
				rj.put("result", "true");
				rj.put("msg", "ok");
				rj.put("fccResult", result);
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "验证通过", rj);
			}
			
			
			
		} else {
			ClApiInfo apiInfo = new ClApiInfo(usrCde, clUsr.getUSR_TEL(), AppNormalConstants.LIVE_API_NAME, AppNormalConstants.GET_RESULT, DateUtils.getNowFullFmt(), (int)(end-start),idcard_name+", "+idcard_number+","+biz_id , "FAIL", s);
			apiService.addInvokeApiInfo(apiInfo);
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "验证失败", status);
		}
	}

}
