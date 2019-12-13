package com.jeeplus.modules.app.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.Callable;

import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.modules.app.api.account.guarantyidnotify.GuarantyidNotifyServer;
import com.jeeplus.modules.app.api.account.guarantyidnotify.response.GuarantyidNotifyResServerBody;
import com.jeeplus.modules.app.api.account.guarantyidnotify.response.GuarantyidNotifyServerResponseVO;
import com.jeeplus.modules.app.api.account.header.ServerHeader;
import com.jeeplus.modules.app.service.ClCheckLoanStatusService;

public class UnGuarantyidNotifyServer implements Callable{ 
	
	private ClCheckLoanStatusService clsService;
	
	private String policyNo;

	private String payChannel;
	public UnGuarantyidNotifyServer(String policyNo,String payChannel) {
		this.policyNo = policyNo;
		this.payChannel = payChannel;
	}


	@Override

    public Object call() throws Exception {
		String xmlStrRes="";
		GuarantyidNotifyResServerBody guarantyidNotifyResServerBody = new GuarantyidNotifyResServerBody();
		ServerHeader serverHeader =  new ServerHeader();
		GuarantyidNotifyServerResponseVO guarantyidNotifyServerResponseVO = new GuarantyidNotifyServerResponseVO();
		clsService = SpringContextHolder.getBean(ClCheckLoanStatusService.class);
		boolean status=clsService.checkAndDealFromPolicyNo(policyNo, payChannel);
		//boolean status=true;
		if(status){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
			String date = sdf.format(new Date());
			serverHeader.setChannelType("AsynLoanApply");
			serverHeader.setSeqNo(UUID.randomUUID().toString());
			serverHeader.setServiceType("YANGGUANG");
			serverHeader.setReqTime(date);
			guarantyidNotifyResServerBody.setResCode("0000");
			guarantyidNotifyResServerBody.setResMessage("成功");
			guarantyidNotifyServerResponseVO.setGuarantyidNotifyResServerBody(guarantyidNotifyResServerBody);
			guarantyidNotifyServerResponseVO.setServerHeader(serverHeader);
			GuarantyidNotifyServer GuarantyidNotifyServer = new GuarantyidNotifyServer();
			xmlStrRes = GuarantyidNotifyServer.guarantyidNotifyServer(guarantyidNotifyServerResponseVO);
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
			String date = sdf.format(new Date());
			serverHeader.setChannelType("AsynLoanApply");
			serverHeader.setSeqNo(UUID.randomUUID().toString());
			serverHeader.setServiceType("YANGGUANG");
			serverHeader.setReqTime(date);
			guarantyidNotifyResServerBody.setResCode("9999");
			guarantyidNotifyResServerBody.setResMessage("失败");
			guarantyidNotifyServerResponseVO.setGuarantyidNotifyResServerBody(guarantyidNotifyResServerBody);
			guarantyidNotifyServerResponseVO.setServerHeader(serverHeader);
			GuarantyidNotifyServer GuarantyidNotifyServer = new GuarantyidNotifyServer();
			xmlStrRes = GuarantyidNotifyServer.guarantyidNotifyServer(guarantyidNotifyServerResponseVO);

		}
		return xmlStrRes;

    } 
	
	

}
