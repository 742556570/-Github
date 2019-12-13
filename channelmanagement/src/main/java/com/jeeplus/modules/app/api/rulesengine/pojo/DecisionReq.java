package com.jeeplus.modules.app.api.rulesengine.pojo;

public class DecisionReq {

	private String strategy_code; //策略编号
	private String app_key; // wd
	private String pro_type; // 产品类型wd
	private String prd_code;// 产品代码
	private String token;// 鉴权标识
	private String org_code;// 商户编号
	private String session_id;// 用户会话id
	private String message_id;// 消息id
	private String user_id;// 用户id
	private String name;// 姓名
	private String id_no;// 身份证号
	private String loan_number;// 进件编号 WDXXXXXXXX
	private String bank_channel;//光大-CEB  信托-FOTIC 中邮-ZYXF 华夏-HXB
	private String mobile;// 手机号
	private String time;// 发送时间 yyyy-MM-dd HH:mm:ss:SSS
	private String app_version;// app版本号
	private String os_version;// 终端操作系统版本号
	private String sdk_version;// sdk版本号
	private String channel;
	private Object other;// 其他字段
	public String getStrategy_code() {
		return strategy_code;
	}
	public void setStrategy_code(String strategy_code) {
		this.strategy_code = strategy_code;
	}
	public String getApp_key() {
		return app_key;
	}
	public void setApp_key(String app_key) {
		this.app_key = app_key;
	}
	public String getPro_type() {
		return pro_type;
	}
	public void setPro_type(String pro_type) {
		this.pro_type = pro_type;
	}
	public String getPrd_code() {
		return prd_code;
	}
	public void setPrd_code(String prd_code) {
		this.prd_code = prd_code;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getOrg_code() {
		return org_code;
	}
	public void setOrg_code(String org_code) {
		this.org_code = org_code;
	}
	public String getSession_id() {
		return session_id;
	}
	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}
	public String getMessage_id() {
		return message_id;
	}
	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId_no() {
		return id_no;
	}
	public void setId_no(String id_no) {
		this.id_no = id_no;
	}
	public String getLoan_number() {
		return loan_number;
	}
	public void setLoan_number(String loan_number) {
		this.loan_number = loan_number;
	}
	public String getBank_channel() {
		return bank_channel;
	}
	public void setBank_channel(String bank_channel) {
		this.bank_channel = bank_channel;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getApp_version() {
		return app_version;
	}
	public void setApp_version(String app_version) {
		this.app_version = app_version;
	}
	public String getOs_version() {
		return os_version;
	}
	public void setOs_version(String os_version) {
		this.os_version = os_version;
	}
	public String getSdk_version() {
		return sdk_version;
	}
	public void setSdk_version(String sdk_version) {
		this.sdk_version = sdk_version;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public Object getOther() {
		return other;
	}
	public void setOther(Object other) {
		this.other = other;
	}
	@Override
	public String toString() {
		return "DecisionReq [strategy_code=" + strategy_code + ", app_key=" + app_key + ", pro_type=" + pro_type
				+ ", prd_code=" + prd_code + ", token=" + token + ", org_code=" + org_code + ", session_id="
				+ session_id + ", message_id=" + message_id + ", user_id=" + user_id + ", name=" + name + ", id_no="
				+ id_no + ", loan_number=" + loan_number + ", bank_channel=" + bank_channel + ", mobile=" + mobile
				+ ", time=" + time + ", app_version=" + app_version + ", os_version=" + os_version + ", sdk_version="
				+ sdk_version + ", channel=" + channel + ", other=" + other + "]";
	}






}
