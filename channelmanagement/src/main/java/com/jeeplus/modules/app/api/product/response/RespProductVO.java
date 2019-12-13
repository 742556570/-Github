package com.jeeplus.modules.app.api.product.response;

import java.io.Serializable;

/**
 * 同步产品返回vo
 * 
 * @author wangfz
 * @date 2018-2-24
 */
public class RespProductVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String errorCode; // 返回码
	private String errorMsg; // 返回码信息

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	public RespProductVO(String errorCode, String errorMsg) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	@Override
	public String toString() {
		return "RespProductVO [errorCode=" + errorCode + ", errorMsg=" + errorMsg + "]";
	}

}
