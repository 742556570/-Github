package com.jeeplus.modules.app.api.Notify;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeeplus.modules.app.api.Notify.request.UnSysNotifyVO;
import com.jeeplus.modules.app.api.account.loanorderquery.LoanOrderQuery;
import com.jeeplus.modules.app.api.product.PropertiesUtil;
import com.jeeplus.modules.app.utils.HttpConUtils;

import net.sf.json.JSONObject;


public class UnSysNotify {
	 /**
	  * 定时任务异步通知
	  */
	private final static Logger logger = LoggerFactory.getLogger(UnSysNotify.class);
	public String UnSysNotify(UnSysNotifyVO unSysNotifyVO) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHMMSS");
		String date = sdf.format(new Date());
		String patch = "SD" + date + LoanOrderQuery.random6();
		//组装请求参数
		Map map = new HashMap();
		map.put("data", unSysNotifyVO);
		map.put("datasource", "ISP");
		map.put("patch", patch);
		map.put("trancode", "MAN0001");
		map.put("trantime", date);
		JSONObject json = JSONObject.fromObject(map);
		String reulst ="true";
		logger.info("定时任务异步通知参数："+json.toString());
		//请求接口
		/*HttpConUtils httpConUtils = new HttpConUtils();
		String reulst = httpConUtils.doPost(PropertiesUtil.getString("task.url"), json.toString());*/
		
		logger.info("定时任务异步通知结果："+reulst);
		

		return reulst;
	}
/*	public static void main(String[] args) {
		UnSysNotify UnSysNotify = new UnSysNotify();
		UnSysNotifyVO unSysNotifyVO = new UnSysNotifyVO();
		unSysNotifyVO.setJobcode("00000023");
		unSysNotifyVO.setResmsg("执行成功");
		unSysNotifyVO.setRunres("success");
		unSysNotifyVO.setRuntime("444");
		String reulst = UnSysNotify.UnSysNotify(unSysNotifyVO);
		System.out.println(reulst);
	}*/
}
