package com.jeeplus.modules.app.task;

import java.util.Date;

import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.modules.app.api.Notify.request.UnSysNotifyVO;
import com.jeeplus.modules.app.service.ClUpdatePaymentPlanService;

public class UnsyncUpdatePaymentPlan extends Thread{
	
	private ClUpdatePaymentPlanService updatePlanService;

	private TimingTaskCallBack cb = new TimingTaskCallBack();
	
	@Override
	public void run() {
		
		try {
			updatePlanService = SpringContextHolder.getBean(ClUpdatePaymentPlanService.class);
			long start = new Date().getTime();
			updatePlanService.getLoanRepaymentPlanQuery();
			long end = new Date().getTime();
			long runTime =end - start;
			UnSysNotifyVO unSysNotifyVO = new UnSysNotifyVO();
			unSysNotifyVO.setJobcode("SD000008");
			unSysNotifyVO.setResmsg("成功");
			unSysNotifyVO.setRunres("success");
			unSysNotifyVO.setRuntime(String.valueOf(runTime));
			cb.UnSysNotify(unSysNotifyVO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			long runTime = 0;
			UnSysNotifyVO unSysNotifyVO = new UnSysNotifyVO();
			unSysNotifyVO.setJobcode("SD000008");
			unSysNotifyVO.setResmsg("失败");
			unSysNotifyVO.setRunres("fail");
			unSysNotifyVO.setRuntime(String.valueOf(runTime));
			cb.UnSysNotify(unSysNotifyVO);
			e.printStackTrace();
		}
	}
	
	
	
	
}
