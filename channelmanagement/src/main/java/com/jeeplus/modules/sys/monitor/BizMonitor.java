package com.jeeplus.modules.sys.monitor;

import java.io.Serializable;

/**
 * 
 * <p>
 * Title: 业务日志监控实体
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @company Coffee-Ease
 * @author duanyuntao
 * @date 2017年7月3日
 * @time 下午4:50:03
 */
public class BizMonitor implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3627245731142951230L;

	/**
	 * 用户ID（userid）
	 */
	private String userid;

	/**
	 * 用户号码（用户注册手机号码）
	 */
	private String mobile;
	/**
	 * 请求时间（yyyyMMdd HH:mm:ss）
	 */
	private String querytime;
	/**
	 * 访问URL（客户端请求URL）
	 */
	private String url;
	/**
	 * 访问方法（http请求方法（post，get））
	 */
	private String method;
	/**
	 * 来源IP（客户端ip地址）
	 */
	private String ip;
	/**
	 * 设备号（移动端必填，设备唯一标识）
	 */
	private String imei;
	/**
	 * 系统类型（操作系统类型）
	 */
	private String system;
	/**
	 * 系统版本（操作系统版本）
	 */
	private String systemver;
	/**
	 * 客户端分辨率（操作系统分辨率）
	 */
	private String clientpixel;
	/**
	 * 客户端版本（容易学客户端版本）
	 */
	private String clientver;
	/**
	 * 业务类型（{@link com.jeeplus.modules.sys.monitor.BizTypeEnums}）
	 */
	private String biztype;
	/**
	 * 业务状态（业务状态：操作成功，失败）
	 */
	private String bizstatus;
	/**
	 * 返回描述（错误描述）
	 */
	private String returndesc;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getSystemver() {
		return systemver;
	}

	public void setSystemver(String systemver) {
		this.systemver = systemver;
	}

	public String getClientpixel() {
		return clientpixel;
	}

	public void setClientpixel(String clientpixel) {
		this.clientpixel = clientpixel;
	}

	public String getClientver() {
		return clientver;
	}

	public void setClientver(String clientver) {
		this.clientver = clientver;
	}

	public String getBiztype() {
		return biztype;
	}

	public void setBiztype(String biztype) {
		this.biztype = biztype;
	}

	public String getBizstatus() {
		return bizstatus;
	}

	public void setBizstatus(String bizstatus) {
		this.bizstatus = bizstatus;
	}

	public String getReturndesc() {
		return returndesc;
	}

	public void setReturndesc(String returndesc) {
		this.returndesc = returndesc;
	}

}
