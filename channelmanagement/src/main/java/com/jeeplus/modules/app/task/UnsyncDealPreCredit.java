package com.jeeplus.modules.app.task;


import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.modules.app.api.rulesengine.pojo.DecisionReq;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.service.ClApplyService;
import com.jeeplus.modules.app.service.ClPmPreCreditService;

public class UnsyncDealPreCredit extends Thread{
	
	private ClPmPreCreditService preCreditService;
	
	private ClUsr usr;
	
	private String policyNO;
	
	private DecisionReq od;
	
	public UnsyncDealPreCredit(ClUsr usr,String policyNO,DecisionReq od) {
		this.usr = usr;
		this.policyNO = policyNO;
		this.od = od;
	}

	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			Thread.sleep(3000);
			System.out.println("------>start Thread for :"+usr.getUSR_CDE());
			preCreditService = SpringContextHolder.getBean(ClPmPreCreditService.class);
			preCreditService.pmPreCredit(usr,policyNO,od);
			System.out.println("------>end Thread for :"+usr.getUSR_CDE());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
