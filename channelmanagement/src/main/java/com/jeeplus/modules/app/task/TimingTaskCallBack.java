package com.jeeplus.modules.app.task;

import com.jeeplus.modules.app.api.Notify.UnSysNotify;
import com.jeeplus.modules.app.api.Notify.request.UnSysNotifyVO;

public class TimingTaskCallBack {
	
	
	public void UnSysNotify(UnSysNotifyVO unSysNotifyVO) {
		
		
		UnSysNotify UnSysNotify = new UnSysNotify();
		unSysNotifyVO.setJobcode(unSysNotifyVO.getJobcode());
		unSysNotifyVO.setResmsg(unSysNotifyVO.getResmsg());
		unSysNotifyVO.setRunres(unSysNotifyVO.getRunres());
		unSysNotifyVO.setRuntime(unSysNotifyVO.getRuntime());
		String reulst = UnSysNotify.UnSysNotify(unSysNotifyVO);
	}
	
}
