package com.jeeplus.modules.app.task;

import java.util.Date;

import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.modules.app.api.Notify.request.UnSysNotifyVO;
import com.jeeplus.modules.app.service.ClUpdatePaymentPlanService;
import com.jeeplus.modules.app.service.ClUpdatePaymentService;

public class UnsyncUpdateUsrLoanOrder extends Thread{
	
	private ClUpdatePaymentService updatePlanService;

	private String usrCde;
	
	public UnsyncUpdateUsrLoanOrder(String usrCde) {
		this.usrCde = usrCde;
	}
	
	
	@Override
	public void run() {
		
		try {
			updatePlanService = SpringContextHolder.getBean(ClUpdatePaymentService.class);
			updatePlanService.getLoanOrderQuery(usrCde);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
}
