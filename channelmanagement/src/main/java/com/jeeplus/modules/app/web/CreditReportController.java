package com.jeeplus.modules.app.web;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.app.api.Notify.UnSysNotify;
import com.jeeplus.modules.app.api.Notify.request.UnSysNotifyVO;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.entity.ClUsrApply;
import com.jeeplus.modules.app.entity.ClUsrLoan;
import com.jeeplus.modules.app.pojo.ResponeJsonObj;
import com.jeeplus.modules.app.service.ClUsrApplyService;
import com.jeeplus.modules.app.service.ClUsrService;
import com.jeeplus.modules.app.service.CreditReportService;
import com.jeeplus.modules.app.service.LoanOrderQueryService;

/**
 * 同步征信上报信息
 * @author 阳光保险
 *
 */
@Controller
@RequestMapping(value = "api/sysCreditReport")
public class CreditReportController {
	private final static Logger logger = LoggerFactory.getLogger(CreditReportController.class);

	@Autowired
	private ClUsrApplyService clUsrApplyService;
	@Autowired
	private CreditReportService creditReportService;
	@Autowired
	private ClUsrService clUsrService;
	@Autowired
	private LoanOrderQueryService loanService;
	/**
	 * 定时任务huo
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getCreditReport")
	public String getCreditReport(@RequestParam(defaultValue="",required=false)String dt, HttpServletRequest request, HttpServletResponse response) {
		long startTime = System.currentTimeMillis();    //获取开始时间
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        
	    Date date=new Date();  
        if(StringUtils.isNotEmpty(dt)) {
        	date = DateUtils.parseDate(dt);
        }
	    Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.add(Calendar.DAY_OF_MONTH, -1);  
        date = calendar.getTime();  
//		List<ClUsrApply> clUsrApplyList=clUsrApplyService.getAllUsrApply(sdf.format(date));
//		if(clUsrApplyList.size() > 0) {
//			for (ClUsrApply clUsrApply:clUsrApplyList) {
//				ClUsr clUsr = clUsrService.getByUsrCde(clUsrApply.getUsrCde());
//				creditReportService.getFile(clUsr, clUsrApply.getPolicyNo());
//			}
//			creditReportService.SFtpAll();
//		}
        
        List<ClUsrLoan> clUsrLoanList=loanService.getByBeginDate(sdf.format(date));
		if(clUsrLoanList.size() > 0) {
			for (ClUsrLoan clUsrLoan:clUsrLoanList) {
				ClUsr clUsr = clUsrService.getByUsrCde(clUsrLoan.getUsrCde());
				creditReportService.getFile(clUsr, clUsrLoan.getPolicyNo());
			}
			creditReportService.SFtpAll();
		}
		long endTime = System.currentTimeMillis();    //获取结束时间
		logger.info("程序运行时间：" + (endTime - startTime) + "ms");    //输出程序运行时间
		long runTime =endTime - startTime;
		UnSysNotifyVO unSysNotifyVO = new UnSysNotifyVO();
		unSysNotifyVO.setJobcode("SD000001");
		unSysNotifyVO.setResmsg("成功");
		unSysNotifyVO.setRunres("success");
		unSysNotifyVO.setRuntime(String.valueOf(runTime));
		this.UnSysNotify(unSysNotifyVO);
		return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "成功", null);
	}

	/**
	 * 目前是假的通知
	 * @param unSysNotifyVO
	 */
	public void UnSysNotify(UnSysNotifyVO unSysNotifyVO) {
		UnSysNotify UnSysNotify = new UnSysNotify();
	
		unSysNotifyVO.setJobcode(unSysNotifyVO.getJobcode());
		unSysNotifyVO.setResmsg(unSysNotifyVO.getResmsg());
		unSysNotifyVO.setRunres(unSysNotifyVO.getRunres());
		unSysNotifyVO.setRuntime(unSysNotifyVO.getRuntime());
		String reulst = UnSysNotify.UnSysNotify(unSysNotifyVO);
		logger.info("程序执行结果" + reulst);   
	}
}
