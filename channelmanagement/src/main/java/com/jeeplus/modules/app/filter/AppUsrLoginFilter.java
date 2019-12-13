package com.jeeplus.modules.app.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.app.constant.AppCommonConstants;
import com.jeeplus.modules.app.constant.AppNormalConstants;
import com.jeeplus.modules.app.dao.ClUsrCntsDao;
import com.jeeplus.modules.app.dao.ClUsrDao;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.pojo.ResponeJsonObj;
import com.jeeplus.modules.app.tools.MD5Util;
import com.jeeplus.modules.app.utils.AppDateUtils;
import com.jeeplus.modules.app.utils.CacheHelper;
import com.jeeplus.modules.app.utils.ParseHeader;
import com.jeeplus.modules.app.web.LoanPrePaymentController;

/**
 * Servlet Filter implementation class AppUsrLoginFilter
 */
public class AppUsrLoginFilter extends OncePerRequestFilter {
	private final static Logger logger = LoggerFactory.getLogger(AppUsrLoginFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String isValidateModel = AppCommonConstants.getValStr("isValidateModel");
		String uri = request.getRequestURI();
		String isAllDown = AppCommonConstants.getValStr("isAllDown");
		if(isAllDown.equals("true")) {
			String[] nopass =  new String[] {"api/apply/getApplyParam","api/apply/loanTrialTest","api/apply/loanTrial","api/payPwd/setPayPwd","api/creditExt/applyCreditExt","api/creditExt/bcCreditExt","api/creditExt/queryCreditExt","api/idcard/idCardOcr","api/idcard/addIdCardInfo","api/idcard/getIdCardInfo","api/idcard/prePrd","api/prd/getAllPrd","api/prd/getPrdByPrdCde","api/uploadfaceimg/verifyImg","api/uploadfaceimg/verifyFaceImg","api/uploadfaceimg/fkVerifyFaceImg","api/uploadfaceimg/addfaceimage","api/uploadfaceimg/getFaceImgByUsr","api/uploadfaceimg/getFaceImgByUsrAndTrdNo","api/uploadfaceimg/getToken","api/uploadfaceimg/getResult","api/usrcnts/addUsrCnts","api/usrcnts/getUsrCnts","api/usrinfo/addUsrInfo","api/usrinfo/getUsrInfo","api/withdrawal/getWithdrawalCache","api/collection/getLoanTrial"};
			for (String s : nopass) {
				if(uri.indexOf(s) >= 0) {
					PrintWriter out = response.getWriter();
					out.print(ResponeJsonObj.getResJsonObj(ResponeJsonObj.WEIHU, "服务升级中，敬请期待", null).toString());
					out.flush();
					return;
				}
			}
		}
		
		
		
		ClUsrDao dao = SpringContextHolder.getBean(ClUsrDao.class);
		String targetTels  = AppCommonConstants.getValStr("targetTels");
		
		System.out.println("isValidateModel="+isValidateModel);
		System.out.println("targetTels="+targetTels);
		 // 不过滤的uri
        String[] notFilter = new String[] { "api/guarantyidnotify","api/collection","api/reCredit", "api/cst","api/prd","api/cardbin","api/crashlog","derivedDataCallBack","api/dealfail","api/checkloan","api/sysCollection","api/sysProduct","api/updatePayPlan","api/sendSms","api/creditExt/bcCreditExt","api/updateLoanOrder","api/upImgsAndCa","api/reportOutTimeBills","api/sysCreditReport","api/sysCollection/getWithholdRecover","api/applylimit","api/regencontract"};
  
        // 请求的uri
        String queryStr = request.getQueryString();
        
        String token_pre = request.getHeader("token");
        System.out.println("token_pre="+token_pre);
        
        Enumeration<String> enu=request.getParameterNames(); 
        String params = "";
        while(enu.hasMoreElements()){  
	        String key = enu.nextElement();  
	        String val = request.getParameter(key);  
	        params=params+"&"+key+"="+val;
        }
       
		
        String fullUri = uri+params;
        logger.info(new Date().getTime()+":"+token_pre +":"+ fullUri);
        if(StringUtils.isNotEmpty(token_pre)) {
        	String cacheKey = MD5Util.hmacSign(token_pre +":"+ fullUri);
        	Object obj = CacheHelper.getObject(AppNormalConstants.DISTINCTURLSCHEMA, cacheKey);
        	if(obj != null) {
        		PrintWriter out = response.getWriter();
    			out.print(ResponeJsonObj.getResJsonObj(ResponeJsonObj.MulitSubmit, "重复提交", null).toString());
    			out.flush();
    			//    			filterChain.doFilter(request, response);
    			logger.info("distinct url : " + token_pre +":"+ fullUri);
    			return;
        	}else {
        		 CacheHelper.setObject(AppNormalConstants.DISTINCTURLSCHEMA, cacheKey,token_pre +":"+ fullUri,2);
        	}
        }
        String tel = request.getParameter("tel");
        logger.info(new Date().getTime()+":"+tel +":"+ fullUri);
        if(StringUtils.isNotEmpty(tel)) {
        	String cacheKey = MD5Util.hmacSign(tel +":"+ fullUri);
        	Object obj = CacheHelper.getObject(AppNormalConstants.DISTINCTURLSCHEMA, cacheKey);
        	if(obj != null) {
        		PrintWriter out = response.getWriter();
    			out.print(ResponeJsonObj.getResJsonObj(ResponeJsonObj.MulitSubmit, "重复提交 ", null).toString());
    			out.flush();
//    			filterChain.doFilter(request, response);
    			logger.info("distinct url : " + tel +":"+ fullUri);
    			return;
        	}else {
        		 CacheHelper.setObject(AppNormalConstants.DISTINCTURLSCHEMA, cacheKey,tel +":"+ fullUri,2);
        	}
        }
        // uri中包含background时才进行过滤
        if (uri.indexOf("api") != -1) {
            // 是否过滤
            boolean doFilter = true;
            for (String s : notFilter) {
                if (uri.indexOf(s) != -1) {
                    // 如果uri中包含不过滤的uri，则不进行过滤
                    doFilter = false;
                    break;
                }
            }
            if (doFilter) {
                // 执行过滤
                // 从session中获取登录者实体
            	ParseHeader ph = new ParseHeader(request);
            	Map<String, String> headerMap = ph.getHeadersInfo();
            	String token = headerMap.get("token");
            	if(token == null || "".equals(token)) {
            		token = request.getParameter("token");
            	}
            	ClUsr cachedUsr = CacheHelper.getCachedUsr(token);
            	if(cachedUsr == null) {
//            		PrintWriter out = response.getWriter();
//            		out.print(ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "未登陆", null).toString());
//            		cachedUsr = dao.getByToken(token);
//            		if(cachedUsr == null) {
            		System.out.println("未登录");
            			PrintWriter out = response.getWriter();
                		out.print(ResponeJsonObj.getResJsonObj(ResponeJsonObj.UNLOGIN, "未登陆", null).toString());
                		out.flush();
                		return;
//                		 filterChain.doFilter(request, response);
//	            	}else {
//            			request.setAttribute("usr", cachedUsr);
//            			filterChain.doFilter(request, response);
//            		}
            	}else {
            		String cachedToken = CacheHelper.getCachedToken(cachedUsr.getUSR_CDE());
            		if(!cachedToken.equals(token)) {
            			System.out.println("未登录");
            			PrintWriter out = response.getWriter();
            			out.print(ResponeJsonObj.getResJsonObj(ResponeJsonObj.UNLOGIN, "请登陆后重试", null).toString());
            			out.flush();
//            			filterChain.doFilter(request, response);
            			return;
            		}
            		cachedUsr.setUSR_LAST_DT(AppDateUtils.getNowAs_yMDHmsS());
            		cachedUsr.setUSR_LAST_IP(headerMap.get("ip"));
            		cachedUsr.setUSR_PLTFM(headerMap.get("os"));
            		cachedUsr.setUSR_OS(headerMap.get("source"));
            		cachedUsr.setUSR_OSVER(headerMap.get("systemver"));
            		cachedUsr.setUSR_CLNTVER(headerMap.get("appversion"));
            		String imei = "";
            		if(StringUtils.isNotEmpty(headerMap.get("uid"))) {
            			imei = headerMap.get("uid");
            		} else if (StringUtils.isNotEmpty(headerMap.get("devId"))) {
            			imei = headerMap.get("devId");
            		}
            		cachedUsr.setUSR_IMEI(imei);
            		cachedUsr.setUSR_SOURCE(headerMap.get("yyqd"));
            		cachedUsr.setAPP_CHANNEL(headerMap.get("channelsource"));
            		request.setAttribute("usr", cachedUsr);
//            		if (uri.contains("api/apply/loanTrial")) {
            			dao.update(cachedUsr);
//            		}
            		filterChain.doFilter(request, response);
            		return;
            	}
            } else {
                // 如果不执行过滤，则继续
                filterChain.doFilter(request, response);
                return;
            }
        } else {
            // 如果uri中不包含background，则继续
        	 filterChain.doFilter(request, response);
        	 return;
        }
	}
	
	private boolean isTarget(String current,String target) {
		if(StringUtils.isEmpty(current)) {
			return false;
		}
		String[] tmps = target.split(",",-1);
		for (String temp : tmps) {
			if(temp.contains(current)) {
				return true;
			}
		}
		return false;
	}
  
}

