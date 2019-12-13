package com.jeeplus.modules.app.entity;

import com.jeeplus.common.persistence.DataEntity;

public class ClConfig extends DataEntity<ClConfig> {

	private String key; // 配置key
	private String val; // 配置val
	private String type; // 配置val数据类型
	private String comment; // 配置说明
	private String creatdate; //创建时间
	private String creatusr; //创建用户
	private String mdfdate; //修改时间
	private String mdfusr; //修改用户

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public ClConfig() {
		super();
	}

	public ClConfig(String key, String val, String type, String comment) {
		super();
		this.key = key;
		this.val = val;
		this.type = type;
		this.comment = comment;
	}

}
