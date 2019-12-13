package com.jeeplus.modules.app.api.account.loanrepaymentplanquery.request;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *贷款还款计划查询请求参数vo
 * 
 * @author wangfz
 * @date 2018-2-24
 */
@XmlRootElement
public class HEADER {

	// header
	private String PATCH;
	private String TRANBANK;
	private String TRANCODE;
	private String TRANTIME;
	private String DATASOURCE;

	public String getPATCH() {
		return PATCH;
	}

	public void setPATCH(String pATCH) {
		PATCH = pATCH;
	}

	public String getTRANBANK() {
		return TRANBANK;
	}

	public void setTRANBANK(String tRANBANK) {
		TRANBANK = tRANBANK;
	}

	public String getTRANCODE() {
		return TRANCODE;
	}

	public void setTRANCODE(String tRANCODE) {
		TRANCODE = tRANCODE;
	}

	public String getTRANTIME() {
		return TRANTIME;
	}

	public void setTRANTIME(String tRANTIME) {
		TRANTIME = tRANTIME;
	}

	public String getDATASOURCE() {
		return DATASOURCE;
	}

	public void setDATASOURCE(String dATASOURCE) {
		DATASOURCE = dATASOURCE;
	}

}
