package com.jeeplus.modules.app.api.eventreprot;

import java.util.HashMap;  
import java.util.Map;

import com.jeeplus.modules.app.api.product.PropertiesUtil;  
//对接口进行测试  
public class Reporter {  
    private String url = PropertiesUtil.getString("er.url");
    private String charset = "utf-8";  
    private HttpClientUtil httpClientUtil = null;  
      
    public Reporter(){  
        httpClientUtil = new HttpClientUtil();  
    }  
      
    public String report(String json){  
        String httpOrgCreateTestRtn = httpClientUtil.doPost(url,json,charset);  
        System.out.println("result:"+httpOrgCreateTestRtn);
        return httpOrgCreateTestRtn;
    }  
      
    public static void main(String[] args){  
        Reporter main = new Reporter();  
    }  
}  