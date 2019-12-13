package com.jeeplus.modules.app.web;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.jeeplus.modules.app.api.product.PropertiesUtil;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.entity.ClUsrApply;
import com.jeeplus.modules.app.pojo.ResponeJsonObj;
import com.jeeplus.modules.app.service.ClUsrApplyService;
import com.jeeplus.modules.app.service.ClUsrService;
import com.jeeplus.modules.app.service.CollectionService;
import com.jeeplus.modules.app.utils.FtpConManager;
import com.jeeplus.modules.app.utils.GenerateFile;

/**
 * 催收系统接口 
 * @author 阳光保险
 *
 */
@Controller
@RequestMapping(value = "api/sysCollection")
public class CollectionController {
	private final static Logger logger = LoggerFactory.getLogger(CollectionController.class);

	@Autowired
	private ClUsrApplyService clUsrApplyService;
	@Autowired
	private CollectionService collectionService;
	@Autowired
	private ClUsrService clUsrService;

	/**
	 * 定时任务同步催收
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getSysCollection")
	public String getSysCollection(@RequestParam(defaultValue="",required=false)String dt, HttpServletRequest request, HttpServletResponse response) {
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
		List<ClUsrApply> clUsrApplyList=clUsrApplyService.getAllUsrApply(sdf.format(date));
		for (ClUsrApply clUsrApply:clUsrApplyList) {
			ClUsr clUsr = clUsrService.getByUsrCde(clUsrApply.getUsrCde());
			collectionService.sysCollection(clUsr, clUsrApply.getPolicyNo());
		}
		//如果list为空 要给催收送四个基本的空文件
		if(clUsrApplyList.size()==0) {
			SimpleDateFormat sdfCon = new SimpleDateFormat("YYYYMMdd");
			Date dateCON=new Date();  
			Calendar calendarcon = Calendar.getInstance();  
			calendarcon.setTime(dateCON);  
			calendarcon.add(Calendar.DAY_OF_MONTH, -1);  
			dateCON = calendarcon.getTime();  
			String dateCon = sdfCon.format(dateCON);
			String ICT_CUSTOMER = dateCon + "ICT_CUSTOMER.txt";
			String ICT_POLICY = dateCon + "ICT_POLICY.txt";
			String ICT_ADDRESS = dateCon + "ICT_ADDRESS.txt";
			String ICT_CONTACT = dateCon + "ICT_CONTACT.txt";
			String fileNameOK = dateCon + ".ok";
			// 生成文件
			this.SFtp(ICT_CUSTOMER);
			this.SFtp(ICT_POLICY);
			this.SFtp(ICT_ADDRESS);
			this.SFtp(ICT_CONTACT);
			// 生成文件后缀为ok
			this.SFtp(fileNameOK);
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
	 * 定时任务同步催收
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "genCsFileByPolicyNo")
	public String genCsFileByPolicyNo(@RequestParam(defaultValue="",required=false)String policyNo, HttpServletRequest request, HttpServletResponse response) {
		long startTime = System.currentTimeMillis();    //获取开始时间
        ClUsrApply apply = clUsrApplyService.getByPolicyNo(policyNo);
		List<ClUsrApply> clUsrApplyList= new ArrayList<ClUsrApply>();
		if(apply != null) {
			clUsrApplyList.add(apply);
		}
		for (ClUsrApply clUsrApply:clUsrApplyList) {
			ClUsr clUsr = clUsrService.getByUsrCde(clUsrApply.getUsrCde());
			collectionService.getFile(clUsr, clUsrApply.getPolicyNo());
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
	/**
	 * 将文件传输到ftp
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	private boolean SFtp(String fileName) {
		GenerateFile generateFile = new GenerateFile();
		String url = PropertiesUtil.getString("baseurl");
		// 生成文件放在本地
		generateFile.setData( url + fileName);
		// 将本地的文件传输到ftp上面
		FtpConManager.getInstance().login(PropertiesUtil.getString("ipurl"), PropertiesUtil.getString("name"),
				PropertiesUtil.getString("password"));
		boolean flag =  FtpConManager.getInstance().uploadFile(url + fileName, fileName);
		System.out.println("操作结果:" + flag);
		return flag;
	}
	
	
	
	private void genFile(String fileName) {
		GenerateFile generateFile = new GenerateFile();
		String url = PropertiesUtil.getString("baseurl");
		// 生成文件放在本地
		generateFile.setData( url + fileName);
		// 将本地的文件传输到ftp上面
	}
	
}
