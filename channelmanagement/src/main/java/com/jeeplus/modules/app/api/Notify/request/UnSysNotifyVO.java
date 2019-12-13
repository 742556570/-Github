package com.jeeplus.modules.app.api.Notify.request;

import java.io.Serializable;

/**
 * 定时任务异步通知接口
 * @author 阳光保险
 *
 */
public class UnSysNotifyVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String jobcode; // 定时任务编号
	private String runres;// 执行结果代码
	private String resmsg; // 执行结果
	private String runtime; // 调用时间
	
	public String getJobcode() {
		return jobcode;
	}
	public void setJobcode(String jobcode) {
		this.jobcode = jobcode;
	}
	public String getRunres() {
		return runres;
	}
	public void setRunres(String runres) {
		this.runres = runres;
	}
	public String getResmsg() {
		return resmsg;
	}
	public void setResmsg(String resmsg) {
		this.resmsg = resmsg;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getRuntime() {
		return runtime;
	}
	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}
	
}
