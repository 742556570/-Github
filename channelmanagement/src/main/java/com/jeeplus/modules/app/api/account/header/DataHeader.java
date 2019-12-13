package com.jeeplus.modules.app.api.account.header;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * @description 请求公共报文头
 *
 */

public class DataHeader {

	/**
	 * 流水号
	 */
	@XStreamAlias("PATCH")
	private String patch;
	
	/**
	 * 交易类型
	 */
	@XStreamAlias("TRANCODE")
	private String tranCode;
	
	/**
	 * 交易时间
	 */
	@XStreamAlias("TRANTIME")
	private String tranTime;
	
	/**
	 * 交易银行
	 */
	@XStreamAlias("TRANBANK")
	private String tranBank;
	
	/**
	 * 数据来源
	 */
	@XStreamAlias("DATASOURCE")
	private String dataSource;

	public String getPatch() {
		return patch;
	}

	public void setPatch(String patch) {
		this.patch = patch;
	}

	public String getTranCode() {
		return tranCode;
	}

	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}

	public String getTranTime() {
		return tranTime;
	}

	public void setTranTime(String tranTime) {
		this.tranTime = tranTime;
	}

	public String getTranBank() {
		return tranBank;
	}

	public void setTranBank(String tranBank) {
		this.tranBank = tranBank;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public String toString() {
		return "AccountDataHeader [patch=" + patch + ", tranCode=" + tranCode
				+ ", tranTime=" + tranTime + ", tranBank=" + tranBank
				+ ", dataSource=" + dataSource + "]";
	}
	
	
}
