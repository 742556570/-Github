package com.jeeplus.modules.sys.monitor;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 
 * <p>
 * Title: 安硕接口日志监控实体
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @company Coffee-Ease
 * @author yys
 * @date 2017年7月3日
 * @time 下午4:50:03
 */
public class AsMonitor implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8715853991280928911L;

	
	private String uuid;
	private String mdn;
	private String querytime;
	private String url;
	private String servicename;
	private String elapsed;
	private String params;
	private String result;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getMdn() {
		return mdn;
	}
	public void setMdn(String mdn) {
		this.mdn = mdn;
	}
	public String getQuerytime() {
		return querytime;
	}
	public void setQuerytime(String querytime) {
		this.querytime = querytime;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getServicename() {
		return servicename;
	}
	public void setServicename(String servicename) {
		this.servicename = servicename;
	}
	public String getElapsed() {
		return elapsed;
	}
	public void setElapsed(String elapsed) {
		this.elapsed = elapsed;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	public AsMonitor() {
		super();
	}

	public AsMonitor(String uuid, String mdn, String querytime, String url, String servicename, String elapsed,String params, String result) {
		super();
		this.uuid = uuid;
		this.mdn = mdn;
		this.querytime = querytime;
		this.url = url;
		this.servicename = servicename;
		this.elapsed = elapsed;
		if (params != null && params.length() > 500) {
			this.params = params.substring(0, 500);
		} else {
			this.params = params;
		}
		if (result != null && result.length() > 500) {
			this.result = result.substring(0, 500);
		} else {
			this.result = result;
		}
	}
	
	public String toJsonString(){
        return JSON.toJSONString(this);
    }
}
