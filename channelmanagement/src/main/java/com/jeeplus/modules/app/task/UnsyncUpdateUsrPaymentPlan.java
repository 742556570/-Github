package com.jeeplus.modules.app.task;

import java.util.Date;

import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.modules.app.api.Notify.request.UnSysNotifyVO;
import com.jeeplus.modules.app.service.ClUpdatePaymentPlanService;

public class UnsyncUpdateUsrPaymentPlan extends Thread {
	
	private ClUpdatePaymentPlanService updatePlanService;
	
	private String usrCde;
	
	public UnsyncUpdateUsrPaymentPlan(String usrCde) {
		this.usrCde = usrCde;
	}

	@Override
	public void run() {
		
		try {
			updatePlanService = SpringContextHolder.getBean(ClUpdatePaymentPlanService.class);
			updatePlanService.getLoanRepaymentPlanQueryByUsr(usrCde);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
