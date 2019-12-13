package com.jeeplus.modules.app.task;


import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.modules.app.api.rulesengine.pojo.DecisionReq;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.service.ClApplyService;
import com.jeeplus.modules.app.service.ClApplyServiceBak;

public class UnsyncDealApply extends Thread{
	
	private ClApplyService applyService;
	
	private ClApplyServiceBak applyServiceBak;
	
	private ClUsr usr;
	
	private String policyNO;
	
	private DecisionReq od;
	
	public UnsyncDealApply(ClUsr usr,String policyNO,DecisionReq od) {
		this.usr = usr;
		this.policyNO = policyNO;
		this.od = od;
	}

	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			
			System.out.println("------>start Thread for :"+usr.getUSR_CDE());
			applyService = SpringContextHolder.getBean(ClApplyService.class);
			applyService.realApply(usr,policyNO,od);
			
//			applyServiceBak = SpringContextHolder.getBean(ClApplyServiceBak.class);
//			applyServiceBak.realApply(usr,policyNO,od);
			System.out.println("------>end Thread for :"+usr.getUSR_CDE());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
