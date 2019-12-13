package com.jeeplus.modules.app.task;

import java.util.Date;

import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.modules.app.api.Notify.request.UnSysNotifyVO;
import com.jeeplus.modules.app.service.ClDealFailCreditAutoService;
import com.jeeplus.modules.app.service.ClReportOutTimeBillsService;

public class UnsyncReportOutTimeBills extends Thread{ 
	
	private ClReportOutTimeBillsService dfca;
	
	private TimingTaskCallBack cb = new TimingTaskCallBack();
	
	@Override
	public void run() {
		try {
			dfca = SpringContextHolder.getBean(ClReportOutTimeBillsService.class);
			long start = new Date().getTime();
			dfca.process();
			long end = new Date().getTime();
			long runTime =end - start;
			UnSysNotifyVO unSysNotifyVO = new UnSysNotifyVO();
			unSysNotifyVO.setJobcode("SD000011");
			unSysNotifyVO.setResmsg("成功");
			unSysNotifyVO.setRunres("success");
			unSysNotifyVO.setRuntime(String.valueOf(runTime));
			cb.UnSysNotify(unSysNotifyVO);
		} catch (Exception e) {
			long runTime = 0;
			UnSysNotifyVO unSysNotifyVO = new UnSysNotifyVO();
			unSysNotifyVO.setJobcode("SD000010");
			unSysNotifyVO.setResmsg("失败");
			unSysNotifyVO.setRunres("fail");
			unSysNotifyVO.setRuntime(String.valueOf(runTime));
			cb.UnSysNotify(unSysNotifyVO);
			e.printStackTrace();
		}
		

	}
	
	

}
