package com.jeeplus.modules.app.utils;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

public class OutputJson implements Serializable{

    /**
     * 返回客户端统一格式，包括状态码，提示信息，以及业务数据
     */
    private static final long serialVersionUID = 1L;
    //状态码
    private String status;
    //必要的提示信息
    private String msg;
    //业务数据
    private Object responseData;

    public OutputJson(String status,String message,Object data){
        this.status = status;
        this.msg = message;
        this.responseData = data;
    }
   
    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getResponseData() {
		return responseData;
	}

	public void setResponseData(Object responseData) {
		this.responseData = responseData;
	}

	public String toString(){
        if(null == this.responseData){
            this.setResponseData(new Object());
        }
        return JSON.toJSONString(this);
    }
}