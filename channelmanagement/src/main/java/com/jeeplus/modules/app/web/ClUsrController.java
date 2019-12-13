package com.jeeplus.modules.app.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.common.utils.TimeUtils;
import com.jeeplus.modules.app.api.dx.DXApi;
import com.jeeplus.modules.app.api.dx.RequestCustInfo;
import com.jeeplus.modules.app.api.eventreprot.Reporter;
import com.jeeplus.modules.app.api.eventreprot.ReqJsonUtils;
import com.jeeplus.modules.app.api.fadada.FadadaDock;
import com.jeeplus.modules.app.api.image.ValidateCodeUtil;
import com.jeeplus.modules.app.api.image.ValidateCodeUtil.Validate;
import com.jeeplus.modules.app.constant.AppCommonConstants;
import com.jeeplus.modules.app.constant.AppNormalConstants;
import com.jeeplus.modules.app.dao.ClAppIdGenDao;
import com.jeeplus.modules.app.dao.ClCapChannelDao;
import com.jeeplus.modules.app.dao.ClConfigDao;
import com.jeeplus.modules.app.dao.ClCrdtExtDao;
import com.jeeplus.modules.app.dao.ClDealFailedDao;
import com.jeeplus.modules.app.dao.ClIdCardInfoDao;
import com.jeeplus.modules.app.dao.ClUsrApplyDao;
import com.jeeplus.modules.app.dao.ClUsrDao;
import com.jeeplus.modules.app.entity.ChChannelInfo;
import com.jeeplus.modules.app.entity.ChCityInfo;
import com.jeeplus.modules.app.entity.ChPrdInfo;
import com.jeeplus.modules.app.entity.ChUsrApply;
import com.jeeplus.modules.app.entity.ClApiInfo;
import com.jeeplus.modules.app.entity.ClCapChannel;
import com.jeeplus.modules.app.entity.ClCrdtExt;
import com.jeeplus.modules.app.entity.ClDealFailed;
import com.jeeplus.modules.app.entity.ClIdCardInfo;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.entity.ClUsrApply;
import com.jeeplus.modules.app.entity.ClUsrLmtamt;
import com.jeeplus.modules.app.pojo.ResponeJsonObj;
import com.jeeplus.modules.app.service.ChChannelInfoService;
import com.jeeplus.modules.app.service.ChCityInfoService;
import com.jeeplus.modules.app.service.ChPrdInfoService;
import com.jeeplus.modules.app.service.ChUsrApplyService;
import com.jeeplus.modules.app.service.ClApiInfoService;
import com.jeeplus.modules.app.service.ClBlackListService;
import com.jeeplus.modules.app.service.ClCreditExtService;
import com.jeeplus.modules.app.service.ClUsrLmtamtService;
import com.jeeplus.modules.app.service.ClUsrService;
import com.jeeplus.modules.app.service.SingleApplyLimitService;
import com.jeeplus.modules.app.tools.MD5Util;
import com.jeeplus.modules.app.utils.AppDateUtils;
import com.jeeplus.modules.app.utils.CacheHelper;
import com.jeeplus.modules.app.utils.CheckNumber;
import com.jeeplus.modules.app.utils.ParseHeader;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.app.entity.ChDonationInfo;


@Controller
@RequestMapping(value = "api/cst")
public class ClUsrController {
	
	private final static Logger logger = LoggerFactory.getLogger(ClUsrController.class);
	
	@Autowired
	private ClUsrService clUsrService;
	
	@Autowired
	private ClApiInfoService apiService;
	
	@Autowired
	private ClCreditExtService creditExtService;
	
	
	@Autowired
	private ClAppIdGenDao dao;
	
	
	@Autowired
	private ClConfigDao cfgDao;
	
	@Autowired
	private ClBlackListService blkService;
	
	
	@Autowired
	private ClDealFailedDao failedDao;
	
	
	@Autowired
	private ClUsrDao usrDao;
	
	@Autowired
	private ClCrdtExtDao crdtDao;
	
	@Autowired
	private ClUsrLmtamtService lmtAmtService;
	
	@Autowired
	private SingleApplyLimitService singleApplyLimitService;
	
	@Autowired
	private ChPrdInfoService chPrdInfoService;
	
	@Autowired
	private ChCityInfoService chCityInfoService;
	
	@Autowired
	private ChChannelInfoService chChannelInfoService;
	@Autowired
	private ChUsrApplyService chUsrApplyService;
	
	@ResponseBody
	@RequestMapping(value = "val")
	public String val(@RequestParam(required=true )String tel, 
			@RequestParam(required=true )String test,
			@RequestHeader HttpHeaders httpHeaders,
			HttpServletResponse response) {
		JsonObject resultJson = new JsonObject();
		resultJson.addProperty("tel", tel);
		if(clUsrService.valiMdnExists(tel)) {
			resultJson.addProperty("isExists", "true");
		} else {
			resultJson.addProperty("isExists", "false");
		}
		return resultJson.toString();
	}
	
	
	@ResponseBody
	@RequestMapping(value = "valiTelExists")
	public String valiTelExists(HttpServletRequest request, HttpServletResponse response) {
		String tel = request.getParameter("tel");
		JsonObject resultJson = new JsonObject();
		resultJson.addProperty("tel", tel);
		if(clUsrService.valiMdnExists(tel)) {
			resultJson.addProperty("isExists", "true");
		} else {
			resultJson.addProperty("isExists", "false");
		}
		return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", resultJson).toString();
	}
	
	
	@ResponseBody
	@RequestMapping(value = "sendValiNum")
	@Transactional(readOnly = false)
	public String sendValiNum(@RequestParam(required=true) String tel,
			HttpServletRequest request, HttpServletResponse response,HttpSession session) {
		
		boolean isMn = CheckNumber.isMobileNum(tel);

		if(isMn == false) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请输入正确手机号", null).toString();
		}
		
		
		boolean isBlk = blkService.getByPhone(tel);
		if(isBlk) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "账号异常", null).toString();
		}
		

		JsonObject resultJson = new JsonObject();
		resultJson.addProperty("tel", tel);
//		Random rnd = new Random();
//		int num = rnd.nextInt(8999) + 1000;
		System.out.println("测试开始..............");
        Validate v = ValidateCodeUtil.getRandomCode();     //直接调用静态方法，返回验证码对象
        System.out.println("测试结束..............");
        if(v!=null){
	      session.setAttribute("validate", v.getValue());	//将验证码值保存session
        }

        CacheHelper.setObject(AppCommonConstants.getValStr("CACHESCHEMA"), tel+"ValiNum", v.getValue()+"", AppCommonConstants.getValInt("CACHEREGSTTL"));
		resultJson.addProperty("validate", v.getBase64Str()+"");
//		CacheHelper.setObject(AppCommonConstants.getValStr("CACHESCHEMA"), tel+"ValiNum", num+"", AppCommonConstants.getValInt("CACHEREGSTTL"));
//		resultJson.addProperty("validate", num+"");
		return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", resultJson).toString();
	}
	
	
	@ResponseBody
	@RequestMapping(value = "sendRmdCode")
	@Transactional(readOnly = false)
	public String sendRmdCode(
			@RequestParam(required=true) String tel,
			@RequestParam(required=true) String valiNum,
			HttpServletRequest request, HttpServletResponse response) {
		
		boolean isMn = CheckNumber.isMobileNum(tel);
		if(isMn == false) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请输入正确手机号", null).toString();
		}
		Object obj = CacheHelper.getObject(AppCommonConstants.getValStr("CACHESCHEMA"), tel+"ValiNum");
		if(obj == null) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请刷新图形验证码再试", null).toString();
		}else {
			String cachedNum = (String)obj;
			if(!cachedNum.equals(valiNum)) {
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "图形验证码错误,请重试", null).toString();
			}
		}
		CacheHelper.removeObject(AppCommonConstants.getValStr("CACHESCHEMA"), tel+"ValiNum");
		boolean isBlk = blkService.getByPhone(tel);
		if(isBlk) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "账号异常", null).toString();
		}
		
		
		JsonObject resultJson = new JsonObject();
		resultJson.addProperty("tel", tel);
		int allCnt = apiService.getInvokeCntByApiNameAndUsrTel(AppNormalConstants.SMS_API_SERVICE_NAME, tel);
		int todayCnt = apiService.getInvokeCntTodayByApiNameAndUsrTel(AppNormalConstants.SMS_API_SERVICE_NAME, tel);
		if (allCnt >= AppCommonConstants.getValInt("USRSENDSMSLIMIT")) {
			resultJson.addProperty("result", "false");
			resultJson.addProperty("msg", "发送短信次数已耗尽");
			resultJson.addProperty("allCnt", allCnt);
			resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("USRSENDSMSLIMIT"));
			resultJson.addProperty("todayCnt", todayCnt);
			resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("USRSENDSMSLIMITEACHDAY"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", resultJson).toString();
		}
		if(todayCnt >= AppCommonConstants.getValInt("USRSENDSMSLIMITEACHDAY")) {
			resultJson.addProperty("result", "false");
			resultJson.addProperty("msg", "今天发送短信次数已用尽，请明天再试");
			resultJson.addProperty("allCnt", allCnt);
			resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("USRSENDSMSLIMIT"));
			resultJson.addProperty("todayCnt", todayCnt);
			resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("USRSENDSMSLIMITEACHDAY"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", resultJson).toString();
		}
		int rmdcde = clUsrService.sendSms(tel);
		if(rmdcde == -1) {
			resultJson.addProperty("result", "false");
			resultJson.addProperty("msg", "短信发送失败，请重试");
			resultJson.addProperty("allCnt", allCnt);
			resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("USRSENDSMSLIMIT"));
			resultJson.addProperty("todayCnt", todayCnt);
			resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("USRSENDSMSLIMITEACHDAY"));
		} else {
			resultJson.addProperty("result", "true");
			resultJson.addProperty("msg", "短信发送成功");
			clUsrService.cacheRmdCde(tel, new Integer(rmdcde).toString());
			resultJson.addProperty("allCnt", allCnt+1);
			resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("USRSENDSMSLIMIT"));
			resultJson.addProperty("todayCnt", todayCnt+1);
			resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("USRSENDSMSLIMITEACHDAY"));
		}
		return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", resultJson).toString();
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "enterSys")
	@Transactional(readOnly = false)
	public String enterSys(
			@RequestParam(required=true) String tel,
			@RequestParam(required=true) String smsCde,
			HttpServletRequest request, HttpServletResponse response) {
		
		boolean isMn = CheckNumber.isMobileNum(tel);
		if(isMn == false) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请输入正确手机号", null).toString();
		}
		
		ParseHeader ph = new ParseHeader(request);
    	Map<String, String> headerMap = ph.getHeadersInfo();
		
		JsonObject resultJson = new JsonObject();
		String cachedCde = clUsrService.getRmdCde(tel);
		if(cachedCde == null) {
			resultJson.addProperty("result", "failed");
			resultJson.addProperty("msg","短信验证码失效，请重试");
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "短信验证码失效，请重试", resultJson).toString().toString();
		}	else if(!cachedCde.equals(smsCde)) {
			int i = clUsrService.countFailSmsCde(tel);
			if(i >= 5) {
				resultJson.addProperty("result", "failed");
				resultJson.addProperty("msg","重复输入错误短信验证码次数过多,请重新发送短信验证码,稍后再试");
				clUsrService.rmvCde(tel);
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "重复输入短信错误验证码次数过多,请重新发送短信验证码,稍后再试", resultJson).toString().toString();
			}
			resultJson.addProperty("result", "failed");
			resultJson.addProperty("msg","短信验证码错误，请重试");
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "短信验证码错误，请重试", resultJson).toString().toString();
		}
		clUsrService.rmvCde(tel);
		if(!clUsrService.valiMdnExists(tel)) {
		//注册
			ClUsr usr = clUsrService.regist(tel,headerMap);
			if(usr == null) {
				resultJson.addProperty("result", "failed");
				resultJson.addProperty("msg","注册失败");
			}else {
				resultJson.addProperty("result", "true");
				resultJson.addProperty("msg","注册成功");
				resultJson.addProperty("token",usr.getUSR_TOKEN());
				resultJson.addProperty("user_id",usr.getUSR_CDE());
				resultJson.addProperty("register_time",usr.getCRT_DT());
				resultJson.addProperty("channel_id",usr.getUSR_SOURCE());
			}
			
		} else {
		//登陆
			ClUsr usr = clUsrService.login(tel,headerMap);
			if(usr == null) {
				resultJson.addProperty("result", "failed");
				resultJson.addProperty("msg","登陆失败");
			}else {
				resultJson.addProperty("result", "true");
				resultJson.addProperty("msg","登陆成功");
				resultJson.addProperty("token",usr.getUSR_TOKEN());
				resultJson.addProperty("user_id",usr.getUSR_CDE());
				resultJson.addProperty("channel_id",usr.getUSR_SOURCE());
				resultJson.addProperty("register_time",usr.getCRT_DT());
				Map<String, String> map = clUsrService.idCardInfo(usr.getUSR_CDE());
				resultJson.addProperty("id_no",map.get("id_no"));
				resultJson.addProperty("name",map.get("name"));
				String prd_code =creditExtService.queryCrditPrdId(usr.getUSR_CDE());
				resultJson.addProperty("prd_code",prd_code);
			}
		
		
		}
		return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", resultJson).toString();
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "exitrSys")
	public String exitrSys(@RequestHeader("token") String token,HttpServletRequest request, HttpServletResponse response) {
		ClUsr cachedUsr = CacheHelper.getCachedUsr(token);
		if(cachedUsr == null) {
			System.out.println("未登录");
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.UNLOGIN, "请登陆后重试", null);
		}
		String cachedToken = CacheHelper.getCachedToken(cachedUsr.getUSR_CDE());
		if(!cachedToken.equals(token)) {
			System.out.println("未登录");
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.UNLOGIN, "请登陆后重试", null);
		}
		CacheHelper.removeCachedUsr(token);
		return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", null).toString();
	}
	
	@ResponseBody
	@RequestMapping(value = "exitrSysApi")
	public String exitrSysApi(@RequestParam String tel,HttpServletRequest request, HttpServletResponse response) {
		ClUsr usr = usrDao.getByUsrTel(tel);
		if(usr == null) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "该手机号不存在", null);
		}
		String token = usr.getUSR_TOKEN();
		ClUsr cachedUsr = CacheHelper.getCachedUsr(token);
		if(cachedUsr == null) {
			
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "已登出", null);
		}
		String cachedToken = CacheHelper.getCachedToken(cachedUsr.getUSR_CDE());
		if(!cachedToken.equals(token)) {
			System.out.println("未登录");
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "已登出", null);
		}
		CacheHelper.removeCachedUsr(token);
		return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "已登出", null).toString();
	}
	
	
	@ResponseBody
	@RequestMapping(value = "chkUsrSts")
	public String chkUsrSts(@RequestHeader("token") String token,HttpServletRequest request, HttpServletResponse response) {
		ClUsr cachedUsr = CacheHelper.getCachedUsr(token);
		if(cachedUsr == null) {
			System.out.println("未登录");
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.UNLOGIN, "请登陆后重试", null);
		}
		String cachedToken = CacheHelper.getCachedToken(cachedUsr.getUSR_CDE());
		if(!cachedToken.equals(token)) {
			System.out.println("未登录");
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.UNLOGIN, "请登陆后重试", null);
		}
		
		
		String USR_CDE = cachedUsr.getUSR_CDE();
		int flag = singleApplyLimitService.getSingleApply(USR_CDE);
		String isLoaning = "false";
		if (flag >= 1) {
			isLoaning = "true";
		}
		
		String result = clUsrService.checkUsrStatus(cachedUsr,isLoaning);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "chkFkSts")
	public String chkFkSts(HttpServletRequest request, HttpServletResponse response) {
		
		String isCloseService = AppCommonConstants.getValStr("IsNeedShutdownLoanService");
		System.out.println("isCloseService="+isCloseService);
		JSONObject result = new JSONObject();
//		result.put("isOn", "false");
//		if("true".equals(isCloseService)) {
//			result.put("isOn", "false");
//			result.put("msg", "服务升级中，请稍后再试。");
//			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "服务升级中，请稍后再试。",result );
//		}
//		
//		String CloseServiceByTimeStart = AppCommonConstants.getValStr("ShutdownLoanServiceStart"); 
//		System.out.println("CloseServiceByTimeStart="+CloseServiceByTimeStart);
//		String startTimeStr = DateUtils.formatDate(new Date(), "yyyyMMdd")+CloseServiceByTimeStart+"00";
//		Date startTime = DateUtils.parseDate4ChkFkTime(startTimeStr);
//		if(new Date().getTime() - startTime.getTime() >= 0) {
//			result.put("isOn", "false");
//			result.put("msg", "服务升级中，请稍后再试。");
//			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "服务升级中，请稍后再试。", result);
//		}
//		
//		String CloseServiceByTimeEnd = AppCommonConstants.getValStr("ShutdownLoanServiceEnd"); 
//		System.out.println("CloseServiceByTimeEnd="+CloseServiceByTimeEnd);
//		String endTimeStr = DateUtils.formatDate(new Date(), "yyyyMMdd")+CloseServiceByTimeEnd+"00";
//		Date endTime = DateUtils.parseDate4ChkFkTime(endTimeStr);
//		if(new Date().getTime() - endTime.getTime() <= 0) {
//			result.put("isOn", "false");
//			result.put("msg", "服务升级中，请稍后再试。");
//			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "服务升级中，请稍后再试。", result);
//		}
		result.put("isOn", "true");
		return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", result);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "loopMsg")
	public String loopMsg(HttpServletRequest request, HttpServletResponse response) {
		String[] name = {"赵","李","张","刘","王","陈","杨","黄","周","吴"};
		String[] am = {"2000","5000","10000","15000","20000"};
		long nowtimestamp = new Date().getTime();
		JsonArray ja = new JsonArray();
		JsonObject resultJson = new JsonObject();
		for (int i = 0; i < 10; i++) {
			JsonObject ele = new JsonObject();
			ele.addProperty("name", name[new Random().nextInt(10)]);
			ele.addProperty("amount", am[new Random().nextInt(5)]);
			ele.addProperty("timestamp", new Random().nextInt(60));
			ja.add(ele);
		}
		resultJson.add("loopMsgs", ja);
		return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", resultJson).toString();
	}
	
	
	@ResponseBody
	@RequestMapping(value = "blocked5Mins")
	public String blocked5Mins(HttpServletRequest request, HttpServletResponse response) {
		try {
			Thread.sleep(1000*60*5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "end";
	}

	
	
	
	@ResponseBody
	@RequestMapping(value = "testCurrId")
	public String testCurrId(HttpServletRequest request, HttpServletResponse response) {
		Integer  a = dao.getApplyId();
		return a+"";
	}
	
	@ResponseBody
	@RequestMapping(value = "getRequestIp")
	public String  getRequestIp(HttpServletRequest request, HttpServletResponse response) {
		
		String ip = getIpAddr(request);
		JSONObject result = new JSONObject();
		result.put("ip", ip);
		return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", result);
		
	}
	
	
	@ResponseBody
	@RequestMapping(value = "refreshCfg")
	public String refreshCfg(HttpServletRequest request, HttpServletResponse response) {
		AppCommonConstants.refresh();
		return "refreshed";
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "threadtest")
	public String threadtest(HttpServletRequest request, HttpServletResponse response) {
		
		return "haha";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "testCache")
	public String testCache(HttpServletRequest request, HttpServletResponse response) {
		CacheHelper.setObject(AppCommonConstants.getValStr("CACHESCHEMA"), "test"+AppCommonConstants.getValStr("CACHETYPESUFFIX_REGS"), 12345, AppCommonConstants.getValInt("CACHEREGSTTL"));
		ClUsrApply apply = new ClUsrApply();
		apply.setApplSeq(123456);
		CacheHelper.setObject(AppCommonConstants.getValStr("CACHESCHEMA"), "apply_key", apply, AppCommonConstants.getValInt("CACHEVALIAPPLYTTL"));
		return "haha";
	}
	
	@ResponseBody
	@RequestMapping(value = "testFailedDao")
	public String testFailedDao(HttpServletRequest request, HttpServletResponse response) {
		ClDealFailed failed = new ClDealFailed();
		failed.setUSR_CDE("141158446576566272");
		failed.setPOLICY_NO("WD1739801803271214W");
		failed.setFAIL_STEP("InvaliPolicy");
		failed.setCRT_DT(DateUtils.getNowFullFmt());
		failed.setISRETRY("false");
		failed.setRETRY_DT(null);
		failed.setRETRY_TIMES(0);
		failed.setISDONE("false");
		failedDao.insert(failed);
		
		
		return "haha";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "testFailedUpdate")
	public String testFailedUpdate(HttpServletRequest request, HttpServletResponse response) {
		ClDealFailed failed = new ClDealFailed();
		failed.setUSR_CDE("141158446576566272");
		failed.setPOLICY_NO("WD1739801803271214W");
		failed.setFAIL_STEP("InvaliPolicy");
		failed.setCRT_DT(DateUtils.getNowFullFmt());
		failed.setISRETRY("true");
		failed.setRETRY_DT("2018-03-27 19:15:53.233");
		failed.setRETRY_TIMES(1);
		failed.setISDONE("true");
		failedDao.update(failed);
		
		
		return "haha";
	}
	
	@ResponseBody
	@RequestMapping(value = "testPath")
	public String testPath(HttpServletRequest request, HttpServletResponse response) {
		FadadaDock FadadaDock = new FadadaDock();
		
		return FadadaDock.getPath();
	}
	
	@ResponseBody
	@RequestMapping(value = "delFddCt")
	public String delFddCt(String step,HttpServletRequest request, HttpServletResponse response) {
		FadadaDock FadadaDock = new FadadaDock();
		FadadaDock.delFddCtByStep(step);
		return "";
	}
	
	@ResponseBody
	@RequestMapping(value = "uplFddCt")
	public String uploadFddCt(String step,HttpServletRequest request, HttpServletResponse response) {
		FadadaDock FadadaDock = new FadadaDock();
		FadadaDock.upFddCtByStep(step);
		return "";
	}
	
	@Autowired
	private ClUsrApplyDao applyDao;
	@Autowired
	private ClIdCardInfoDao idCardDao;
	
	@ResponseBody
	@RequestMapping(value = "testReport")
	public String testReport(String usrCde,HttpServletRequest request, HttpServletResponse response) {
		ClUsr usr = usrDao.getByUsrCode(usrCde);
		ClCrdtExt crdtExt = crdtDao.getClCrdtExtByUsrCde(usrCde);
		ClUsrLmtamt lmtAmt = lmtAmtService.getByUsrCde(usrCde);
		ClIdCardInfo idCardInfo = idCardDao.getByUsrCode(usrCde);
		List<ClUsrApply> list = applyDao.getByUsrCde(usrCde);
		String falg = "false";
		for (ClUsrApply clUsrApply : list) {
			if(StringUtils.isNotEmpty(clUsrApply.getDebtStatus())) {
				falg = "true";
			}
		}
		Reporter r = new Reporter();
		ReqJsonUtils jsonUtils = new ReqJsonUtils();
		String jsonStr = jsonUtils.getJsonStr(usr, idCardInfo, crdtExt, lmtAmt, falg);
		r.report(jsonStr);
		return "haha";
	}
	
//查询O2O产品信息
	@ResponseBody
	@RequestMapping(value = "getProductInfo")
	public String getProductInfo(HttpServletRequest request, HttpServletResponse response) {
		
		List<ChPrdInfo> chPrdList = chPrdInfoService.getAllChPrdInfo();
		List<ChChannelInfo> chChannelInfoList = chChannelInfoService.getAllSecChChannelInfo();
		
		if(chPrdList == null) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "未查得", null).toString();
		} else {
			 JSONArray jsonArrayChPrd = new JSONArray();
//			 JSONArray jsonArrayChChannel = new JSONArray();
			 for(ChPrdInfo chPrdInfo:chPrdList){
				  JSONObject jo = new JSONObject();
				  jo.put("productCode",chPrdInfo.getPRD_CDE());
				  jo.put("productName",chPrdInfo.getPRD_NAME());
				  jsonArrayChPrd.add(jo);
				}
//			 for(ChChannelInfo chChannelInfo:chChannelInfoList){
//				  JSONObject jo = new JSONObject();
//				  jo.put("secChCde",chChannelInfo.getCH_CDE());
//				  jo.put("version",chChannelInfo.getVERSION());
//				  jsonArrayChChannel.add(jo);
//				}
//			 jsonArrayChPrd.addAll(jsonArrayChChannel);
			return ResponeJsonObj.getResJsonObjFromArray(ResponeJsonObj.SUCCESS, "查询成功", jsonArrayChPrd);
		}
		
		
		
	}
	
	//查询O2O产品信息
		@ResponseBody
		@RequestMapping(value = "getsecChVersion")
		public String getsecChVersion(@RequestParam(value="secChCde" )String secChCde,HttpServletRequest request, HttpServletResponse response) {
			
			 JSONArray jsonArrayChChannel = new JSONArray();
			  
			if(secChCde.substring(0,1).equalsIgnoreCase("A")) {
				
				      JSONObject jo1 = new JSONObject();
					  jo1.put("secChCde",secChCde);
					  jo1.put("version","C");
					  jsonArrayChChannel.add(jo1);
					
				
			}else {
				
			List<ChChannelInfo> chChannelInfoList = chChannelInfoService.getAllSecChChannelInfo();
			
			if(chChannelInfoList == null) {
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "未查得", null).toString();
			} else {
				 
				 
				 for(ChChannelInfo chChannelInfo:chChannelInfoList){
					  JSONObject jo2 = new JSONObject();
					  jo2.put("secChCde",chChannelInfo.getCH_CDE());
					  jo2.put("version",chChannelInfo.getVERSION());
					  jsonArrayChChannel.add(jo2);
					}
				
			}	
		  }
			return ResponeJsonObj.getResJsonObjFromArray(ResponeJsonObj.SUCCESS, "查询成功", jsonArrayChChannel);
			
		}
	
	//查询O2O城市信息
		@ResponseBody
		@RequestMapping(value = "getCityCode")
		public String getCityCode(HttpServletRequest request, HttpServletResponse response) {
			
			List<ChCityInfo> chCityList = chCityInfoService.getAllChCityInfo();
			
			if(chCityList == null) {
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "未查得", null).toString();
			} else {
				 JSONArray jsonArrayChCity = new JSONArray();
				 for(ChCityInfo chCityInfo:chCityList){
					  JSONObject jo = new JSONObject();
					  jo.put("type",chCityInfo.getCITY_TYPE());
					  jo.put("name",chCityInfo.getCITY_NAME());
					  jo.put("code",chCityInfo.getCITY_CDE());
					  jo.put("pinyin",chCityInfo.getCITY_PINYIN());
					  jsonArrayChCity.add(jo);
					}
				return ResponeJsonObj.getResJsonObjFromArray(ResponeJsonObj.SUCCESS, "查询成功", jsonArrayChCity);
			}
			
			
			
		}
		
		//查询单渠道当天的申请次数
		@ResponseBody
		@RequestMapping(value = "getChannelCount")
	    public String getChannelCount(@RequestParam(value="channelCode" )String channelCode,HttpServletRequest request, HttpServletResponse response) {
					
			String postTime = AppDateUtils.getNowAs_yMDHmsS().substring(0, 10); 
			int todayCnt = chChannelInfoService.getAllChannelDayCount(channelCode,postTime);
			ChChannelInfo chChannelInfo = new ChChannelInfo();
			chChannelInfo = chChannelInfoService.getChannelInfoByChCde(channelCode);
					
			if(chChannelInfo == null ) {
				
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "未查得", null).toString();
				
			} else {
				JsonObject resultJson = new JsonObject();
				resultJson.addProperty("result", "true");
				resultJson.addProperty("msg", "成功");
				resultJson.addProperty("channelCode", channelCode);
				resultJson.addProperty("channelName", chChannelInfo.getCH_NAME());
				resultJson.addProperty("channelStatus", chChannelInfo.getCH_STATUS());
				resultJson.addProperty("dayLimit", chChannelInfo.getCH_DAY_LIMIT());
				resultJson.addProperty("todayCnt", todayCnt);
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "查询成功", resultJson);
					
			}
									
				
	}
		
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
				@RequestParam(value="ygyjUsrCde" )String ygyjUsrCde,
//				@RequestParam(value="loginIp" )String loginIp,
				HttpServletRequest request,
				HttpServletResponse response) {
			ClUsr clUsr = (ClUsr)request.getAttribute("usr");
//			//24小时内同一IP地址提交申请次数限制
//			int dayIpLimit = AppCommonConstants.getValInt("DAYIPLIMIT");
//			//30日内同一IP地址提交申请次数限制
//			int monthIpLimit = AppCommonConstants.getValInt("MONTHIPLIMIT");
//			//24小时内同一IP地址提交申请次数
//			int dayIpCount = clUsrService.getDayIpCount(loginIp);
//			//30日内同一IP地址提交申请次数
//			int monthIpCount = clUsrService.getMonthIpCount(loginIp);
//			//判断24小时内同一IP地址提交申请次数（不含本次）是否大于或等于限制次数
//			if(dayIpCount-1>=dayIpLimit) {
//				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请勿多次提交，过会再试", null).toString();
//			}
//			//判断30日内同一IP地址提交申请次数（不含本次）是否大于或等于限制次数
//			if(monthIpCount-1>=monthIpLimit) {
//				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请勿多次提交，过会再试", null).toString();
//			}
			String daytime = AppDateUtils.getNowAs_yMDHmsS().substring(0, 10); 
//			int daycount = chChannelInfoService.getAllChannelDayCount(custSecChannelCode, daytime);
//			ChChannelInfo chChannelInfoStatus = chChannelInfoService.getChannelStatusByChCde(custSecChannelCode);
//	        if(custCityName == null || custCityName.equalsIgnoreCase("")) {
//				
//				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请选择城市信息。", null).toString();
//				
//			}
//			ChCityInfo chCityInfo = chCityInfoService.getCityInfoByCityName(custCityName);
//			ChChannelInfo chChannelInfo = chChannelInfoService.getAllChannelInfoByChCde(custSecChannelCode);
//			int custTelCnt = chUsrApplyService.getAChUsrApplyCount(custTel);
//	        if(chCityInfo == null) {
//	        	ClApiInfo apiInfo = new ClApiInfo(clUsr.getUSR_CDE(), custTel, custCityName, custCityName, DateUtils.getNowFullFmt(), 1,"对不起,您所在的城市暂未开放相关业务,感谢您的关注" , "fail", "对不起,您所在的城市暂未开放相关业务,感谢您的关注");
//				apiService.addInvokeApiInfo(apiInfo);
//				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "对不起,您所在的城市暂未开放相关业务,感谢您的关注!", null).toString();
//				
//			}
	        
//	        if(chChannelInfo == null) {
//				
//				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "渠道标识有误", null).toString();
//				
//			}
	        
//	        if(custTelCnt >= 1) {
//				
//				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "您已经申请过了，无需再次申请", null).toString();
//				
//			}
			
//			if(daycount >= Integer.parseInt(chChannelInfoStatus.getCH_DAY_LIMIT()) && Integer.parseInt(chChannelInfoStatus.getCH_DAY_LIMIT()) != 0) {
//				
//				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "提交申请失败，请稍后再试", null).toString();
//				
//			}
//	        if(chChannelInfoStatus.getCH_STATUS().equalsIgnoreCase("1")) {
//				
//				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "提交申请失败，请稍后再试", null).toString();
//				
//			}
	        
	      
			
			custCityCode = StringUtils.StringFilter(custCityCode);
			custCityName = StringUtils.StringFilter(custCityName);
			custName = StringUtils.StringFilter(custName);
			custTel = StringUtils.StringFilter(custTel);
			postTime = AppDateUtils.getNowAs_yMDHmsS();
			custProductCode = StringUtils.StringFilter(custProductCode);
			custProductName = StringUtils.StringFilter(custProductName);
			custSecProductCode = StringUtils.StringFilter(custSecProductCode);
			custSecProductName = StringUtils.StringFilter(custSecProductName);
			
			
			
			ChUsrApply chUsrInfo = new ChUsrApply();
			chUsrInfo.setApplCde(UUID.randomUUID().toString().replaceAll("\\-", ""));
			chUsrInfo.setChannelCde(StringUtils.StringFilter(custSecChannelCode));
			chUsrInfo.setChannelName(StringUtils.StringFilter("阳光一家"));
			chUsrInfo.setCustCityCde(custCityCode);
			chUsrInfo.setCustCityName(custCityName);
			chUsrInfo.setCustName(custName);
			chUsrInfo.setCustTel(custTel);
			chUsrInfo.setCustProductCde(custProductCode);
			chUsrInfo.setCustProductName(custProductName);
			chUsrInfo.setCustChannelCde(custSecChannelCode);
			chUsrInfo.setCustChannelName("阳光一家");
			chUsrInfo.setCustSecChannelCde(custSecChannelCode);
			chUsrInfo.setCustSecChannelName("阳光一家");
			chUsrInfo.setCustSecProductCde(custSecProductCode);
			chUsrInfo.setCustSecProductName(custSecProductName);
//			chUsrInfo.setUsrCde(clUsr.getUSR_CDE());
			chUsrInfo.setCrtDt(postTime);
			chUsrInfo.setYgyjUsrCde(ygyjUsrCde);
			
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
		
		
//获取第三方公示信息
		
		@ResponseBody
		@RequestMapping(value = "getDonationInfo")
		@Transactional(readOnly = false)
		public String getDonationInfo(
				@RequestParam(value="doCde" )String doCde,
				@RequestParam(value="version" )String version,
				HttpServletRequest request,
				HttpServletResponse response) {
			ClUsr clUsr = (ClUsr)request.getAttribute("usr");
			String daytime = AppDateUtils.getNowAs_yMDHmsS().substring(0, 10); 
			HashMap<String, String> params = new HashMap<String,String>();
			if(version.equalsIgnoreCase("ZJYG")) {
				params.put("doType", "指尖阳光");
			}else if(version.equalsIgnoreCase("ZJBB")) {
				params.put("doType", "指尖&保呗");
			}else if(version.equalsIgnoreCase("YGBB")) {
				params.put("doType", "阳光保呗");
			}
			params.put("doCde", doCde);
			ChDonationInfo chDonationInfoResult = chUsrApplyService.getDonationInfo(params);
			int i = daytime.compareTo("2019-08-20");
			JsonObject resultJson = new JsonObject();
			JsonObject resultJson1 = new JsonObject();
			resultJson.addProperty("本公司网络平台全称", chDonationInfoResult.getDoBGSWLPTQC());
			resultJson.addProperty("本公司网络平台简称", chDonationInfoResult.getDoBGSWLPTJC());
			resultJson.addProperty("本公司网络平台网站地址", chDonationInfoResult.getDoBGSWLPTWZDZ());
			resultJson.addProperty("合作保险机构名称", chDonationInfoResult.getDoHZBXJGMC());
			resultJson.addProperty("本公司网络平台备案信息", chDonationInfoResult.getDoBGSWLPTBAXX());
			resultJson.addProperty("网络平台备案日期", chDonationInfoResult.getDoWLPTBASJ());
			resultJson.addProperty("业务合作范围", chDonationInfoResult.getDoYWHZFW());
			resultJson.addProperty("业务合作起始日期", chDonationInfoResult.getDoYWHZQSSJ());
			resultJson.addProperty("业务合作终止日期", chDonationInfoResult.getDoYWHZZZSJ());
			String status = "";
			if(chDonationInfoResult.getStatus().equalsIgnoreCase("0") && daytime.compareTo(chDonationInfoResult.getDoYWHZZZSJ()) <= 0 && daytime.compareTo(chDonationInfoResult.getDoYWHZQSSJ()) >=0) {
				status = "0";
			}else {
				status = "1";
			}
			resultJson1.addProperty("status", status);
			resultJson1.addProperty("version", version);
			List<JsonObject> list = new ArrayList();
			list.add(resultJson);
			list.add(resultJson1);
			return ResponeJsonObj.getResJsonObjFromArray(ResponeJsonObj.SUCCESS, "查询成功", list);
			
		}
		
		
		
		@ResponseBody
		@RequestMapping(value = "transToDxMidday")
		public String transToDxMidday(HttpServletRequest request, HttpServletResponse response) {
			ClUsr clUsr = (ClUsr)request.getAttribute("usr");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			Date nowDate = new Date();
			String startTime = df.format(nowDate)+" 00:00:00";
			String endTime = df.format(nowDate)+" 11:59:59";
			List<ChUsrApply> applyList = chUsrApplyService.getAChUsrApplyDx(startTime,endTime);
			if(applyList == null || applyList.size() == 0) {
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "未查得", null).toString();
			}else {
			DXApi api = new DXApi();
			List<RequestCustInfo> requestCustInfoList = new ArrayList();
			for(ChUsrApply ChUsrApply:applyList) {
				RequestCustInfo requestCustInfo = new RequestCustInfo();
				requestCustInfo.setAge(null);
				requestCustInfo.setCardNo(null);
				requestCustInfo.setCardType(null);
				requestCustInfo.setCityNo(ChUsrApply.getCustCityCde());
				if(ChUsrApply.getYgyjUsrCde() == null) {
				    requestCustInfo.setCustID(ChUsrApply.getUsrCde());
				}else {
					requestCustInfo.setCustID(ChUsrApply.getYgyjUsrCde());	
				}
				requestCustInfo.setCustName(ChUsrApply.getCustName());
				requestCustInfo.setCustSex(null);
				requestCustInfo.setDataSource("115");  //网贷O2O
				requestCustInfo.setDutype(null);
				if(ChUsrApply.getCustProductCde().equalsIgnoreCase("001")) {
					requestCustInfo.setHasroom("1");
				}else if(ChUsrApply.getCustProductCde().equalsIgnoreCase("003")) {
					requestCustInfo.setHascar("1");
				}else if(ChUsrApply.getCustProductCde().equalsIgnoreCase("004")) {
					requestCustInfo.setHaspolicy("1");
				}
				requestCustInfo.setPhoneNo(ChUsrApply.getCustTel());
				requestCustInfo.setNameSource(ChUsrApply.getCustChannelName());
				requestCustInfoList.add(requestCustInfo);
			}
				String result = api.preDX(requestCustInfoList);
				
				if(result.equalsIgnoreCase("成功")) {
					SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//设置日期格式
					
					for(ChUsrApply ChUsrApply:applyList) {
						
					int transResult = chUsrApplyService.updateDxInfo(ChUsrApply.getApplCde(),df1.format(new Date()),"成功");
					
					}
				}else {
					SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//设置日期格式
					
					for(ChUsrApply ChUsrApply:applyList) {
						
					int transResult = chUsrApplyService.updateDxInfo(ChUsrApply.getApplCde(),df1.format(new Date()),"失败");
					
					}
				}
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "查询成功", null).toString();
			}
			
		}
		
		@ResponseBody
		@RequestMapping(value = "transToDxMorning")
		public String transToDxMorning(HttpServletRequest request, HttpServletResponse response) {
			ClUsr clUsr = (ClUsr)request.getAttribute("usr");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			Date nowDate = new Date();
			String beforeDay = TimeUtils.getSpecifiedDayBefore(df.format(nowDate));
			String startTime = beforeDay+" 12:00:00";
			String endTime = beforeDay+" 23:59:59";
			List<ChUsrApply> applyList = chUsrApplyService.getAChUsrApplyDx(startTime,endTime);
			if(applyList == null || applyList.size() == 0) {
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "未查得", null).toString();
			}else {
			DXApi api = new DXApi();
			List<RequestCustInfo> requestCustInfoList = new ArrayList();
			for(ChUsrApply ChUsrApply:applyList) {
				RequestCustInfo requestCustInfo = new RequestCustInfo();
				requestCustInfo.setAge(null);
				requestCustInfo.setCardNo(null);
				requestCustInfo.setCardType(null);
				requestCustInfo.setCityNo(ChUsrApply.getCustCityCde());
				if(ChUsrApply.getYgyjUsrCde() == null) {
				    requestCustInfo.setCustID(ChUsrApply.getUsrCde());
				}else {
					requestCustInfo.setCustID(ChUsrApply.getYgyjUsrCde());	
				}
				requestCustInfo.setCustName(ChUsrApply.getCustName());
				requestCustInfo.setCustSex(null);
				requestCustInfo.setDataSource("115");  //网贷O2O
				requestCustInfo.setDutype(null);
				if(ChUsrApply.getCustProductCde().equalsIgnoreCase("001")) {
					requestCustInfo.setHasroom("1");
				}else if(ChUsrApply.getCustProductCde().equalsIgnoreCase("003")) {
					requestCustInfo.setHascar("1");
				}else if(ChUsrApply.getCustProductCde().equalsIgnoreCase("004")) {
					requestCustInfo.setHaspolicy("1");
				}
				requestCustInfo.setPhoneNo(ChUsrApply.getCustTel());
				requestCustInfo.setNameSource(ChUsrApply.getCustChannelName());
				requestCustInfoList.add(requestCustInfo);
			}
				String result = api.preDX(requestCustInfoList);
				
				if(result.equalsIgnoreCase("成功")) {
					SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//设置日期格式
					
					for(ChUsrApply ChUsrApply:applyList) {
						
					int transResult = chUsrApplyService.updateDxInfo(ChUsrApply.getApplCde(),AppDateUtils.getNowAs_yMDHmsS(),"成功");
					
					}
				}else {
					SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//设置日期格式
					
					for(ChUsrApply ChUsrApply:applyList) {
						
					int transResult = chUsrApplyService.updateDxInfo(ChUsrApply.getApplCde(),AppDateUtils.getNowAs_yMDHmsS(),"失败");
					
					}
				}
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "查询成功", null).toString();
			}
			
		}
		
		@ResponseBody
		@RequestMapping(value = "transToDxCompensate")
		public String transToDxCompensate(HttpServletRequest request, HttpServletResponse response) {
			List<ClApiInfo> apiInfoList  = apiService.findDxCompensateFail();
			if(apiInfoList.size() > 3) {   //自动补偿超过三次，不再自动补偿
				
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "电销补偿次数超过3次，不再自动补偿", null).toString();
				
			} else {
				
			ClUsr clUsr = (ClUsr)request.getAttribute("usr");
			List<ChUsrApply> applyList = chUsrApplyService.transToDxCompensate();
			if(applyList == null || applyList.size() == 0) {
				
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "未查得", null).toString();
				
			}else {
				
			DXApi api = new DXApi();
			List<RequestCustInfo> requestCustInfoList = new ArrayList();
			for(ChUsrApply ChUsrApply:applyList) {
				RequestCustInfo requestCustInfo = new RequestCustInfo();
				requestCustInfo.setAge(null);
				requestCustInfo.setCardNo(null);
				requestCustInfo.setCardType(null);
				requestCustInfo.setCityNo(ChUsrApply.getCustCityCde());
				if(ChUsrApply.getYgyjUsrCde() == null) {
				    requestCustInfo.setCustID(ChUsrApply.getUsrCde());
				}else {
					requestCustInfo.setCustID(ChUsrApply.getYgyjUsrCde());	
				}
				requestCustInfo.setCustName(ChUsrApply.getCustName());
				requestCustInfo.setCustSex(null);
				requestCustInfo.setDataSource("115");  //网贷O2O
				requestCustInfo.setDutype(null);
				if(ChUsrApply.getCustProductCde().equalsIgnoreCase("001")) {
					requestCustInfo.setHasroom("1");
				}else if(ChUsrApply.getCustProductCde().equalsIgnoreCase("003")) {
					requestCustInfo.setHascar("1");
				}else if(ChUsrApply.getCustProductCde().equalsIgnoreCase("004")) {
					requestCustInfo.setHaspolicy("1");
				}
				
				requestCustInfo.setPhoneNo(ChUsrApply.getCustTel());
				requestCustInfo.setNameSource(ChUsrApply.getCustChannelName());
				requestCustInfoList.add(requestCustInfo);
			}
				String result = api.preDX(requestCustInfoList);
				if(result.equalsIgnoreCase("成功")) {
					SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//设置日期格式
					
					for(ChUsrApply ChUsrApply:applyList) {
						
					int transResult = chUsrApplyService.updateDxInfo(ChUsrApply.getApplCde(),AppDateUtils.getNowAs_yMDHmsS(),"成功");
					
					}
					
					return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "自动补偿成功", null).toString();
				}else {
					SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//设置日期格式
					ClApiInfo apiInfo = new ClApiInfo("", "", "transToDxCompensate", "transToDxCompensate", DateUtils.getNowFullFmt(), 1,"电销补偿失败" , "fail", "电销补偿失败");
					apiService.addInvokeApiInfo(apiInfo);
					for(ChUsrApply ChUsrApply:applyList) {
						
					int transResult = chUsrApplyService.updateDxInfo(ChUsrApply.getApplCde(),AppDateUtils.getNowAs_yMDHmsS(),"失败");
					
					}
					
					return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "自动补偿失败", null).toString();
				}
				
				
			  }
			}
		}
	
		@ResponseBody
		@RequestMapping(value = "transToDxCompensateHand")
		public String transToDxCompensateHand(HttpServletRequest request, HttpServletResponse response) {
			
				
			ClUsr clUsr = (ClUsr)request.getAttribute("usr");
			List<ChUsrApply> applyList = chUsrApplyService.transToDxCompensateHand();
			if(applyList == null || applyList.size() == 0) {
				
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "未查得", null).toString();
				
			}else {
				
			DXApi api = new DXApi();
			List<RequestCustInfo> requestCustInfoList = new ArrayList();
			for(ChUsrApply ChUsrApply:applyList) {
				RequestCustInfo requestCustInfo = new RequestCustInfo();
				requestCustInfo.setAge(null);
				requestCustInfo.setCardNo(null);
				requestCustInfo.setCardType(null);
				requestCustInfo.setCityNo(ChUsrApply.getCustCityCde());
				if(ChUsrApply.getYgyjUsrCde() == null) {
				    requestCustInfo.setCustID(ChUsrApply.getUsrCde());
				}else {
					requestCustInfo.setCustID(ChUsrApply.getYgyjUsrCde());	
				}
				requestCustInfo.setCustName(ChUsrApply.getCustName());
				requestCustInfo.setCustSex(null);
				requestCustInfo.setDataSource("115");  //网贷O2O
				requestCustInfo.setDutype(null);
				if(ChUsrApply.getCustProductCde().equalsIgnoreCase("001")) {
					requestCustInfo.setHasroom("1");
				}else if(ChUsrApply.getCustProductCde().equalsIgnoreCase("003")) {
					requestCustInfo.setHascar("1");
				}else if(ChUsrApply.getCustProductCde().equalsIgnoreCase("004")) {
					requestCustInfo.setHaspolicy("1");
				}
				
				requestCustInfo.setPhoneNo(ChUsrApply.getCustTel());
				requestCustInfo.setNameSource(ChUsrApply.getCustChannelName());
				requestCustInfoList.add(requestCustInfo);
			}
				String result = api.preDX(requestCustInfoList);
				if(result.equalsIgnoreCase("成功")) {
					SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//设置日期格式
					
					for(ChUsrApply ChUsrApply:applyList) {
						
					int transResult = chUsrApplyService.updateDxInfo(ChUsrApply.getApplCde(),AppDateUtils.getNowAs_yMDHmsS(),"成功");
					
					}
					
					return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "手动补偿成功", null).toString();
				}else {
					SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//设置日期格式
					ClApiInfo apiInfo = new ClApiInfo("", "", "transToDxCompensate", "transToDxCompensate", DateUtils.getNowFullFmt(), 1,"电销补偿失败" , "fail", "电销补偿失败");
					apiService.addInvokeApiInfo(apiInfo);
					for(ChUsrApply ChUsrApply:applyList) {
						
					int transResult = chUsrApplyService.updateDxInfo(ChUsrApply.getApplCde(),AppDateUtils.getNowAs_yMDHmsS(),"失败");
					
					}
					
					return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "手动补偿失败", null).toString();
				}
				
				
			  }
			
		}
		
	@ResponseBody
	@RequestMapping(value = "getUsrStatus")
	public String getUsrStatus(HttpServletRequest request, HttpServletResponse response) {
		
		JSONObject json = parseReq(request);
		
		if(json==null || json.isEmpty()) {
			return new JsonArray().toString();
		}
		logger.info("电销查询用户状态,参数:"+json.toJSONString());
		JSONArray tels = json.getJSONArray("tels");
		
		JsonArray array =new JsonArray();
		
		for (int i = 0; i < tels.size(); i++) {
			String tel = tels.getString(i);
			ClUsr usr = clUsrService.getByTel(tel);
			if(usr == null) {
				continue;
			}
			JsonObject result = clUsrService.getUsrStatus(usr);
			if(result == null) {
				continue;
			}
			array.add(result);
		}
		
		logger.info("电销查询用户状态,结果:"+array.toString());
		
		return array.toString();
	}
	
	
	private JSONObject parseReq(HttpServletRequest request) {
		
		logger.info("电销查询用户状态");
		
		Map<String, Object> objs = null;
		try {
			// String json = request.getParameter("json");
			String json = null;
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
			
			String line = null;
			StringBuilder sb = new StringBuilder();
			objs = new HashMap<String, Object>();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			if (sb.length() == 0) {
				json = request.getParameter("json");
			} else {
				json = sb.toString();
				if(json.startsWith("=")){
					json = json.replace("=", "");
					json = URLDecoder.decode(json, "utf-8");
				}
				if(json.contains("=") && !json.startsWith("=")){
					String[] kvs = json.split("&");
					
					for (String kv : kvs) {
						String[] tmp = kv.split("=");
						if(tmp.length == 2){
							objs.put(tmp[0], URLDecoder.decode(tmp[1],"utf-8"));
						}
					}
					json = JSONObject.toJSONString(objs);
				}
			} 
			
			
			JSONObject obj = JSONObject.parseObject(json);
			
			
			return obj;
		}catch (Exception e) {
			// TODO: handle exception
		}
		return new JSONObject();
	}
	
	
	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		} else {
			return request.getRemoteAddr();
		}
	}
	
}
