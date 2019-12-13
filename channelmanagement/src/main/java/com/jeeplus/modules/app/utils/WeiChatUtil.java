package com.jeeplus.modules.app.utils;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.config.Global;

import org.apache.commons.httpclient.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.net.SocketTimeoutException;

/******************************************
 * @author: zhangyoupeng
 * @createDate: 2018/6/25 19:49
 * @company: (C) Copyright 信保数据部
 * @since: JDK 1.8
 * @Description: 微信发送工具类
 ******************************************/
public class WeiChatUtil {

    private static final String token = "275ca6b50ae411e8bdpf8c859021128a";

    //微信地址
    public static String message_url;
    //微信账号
    public static String accounts;
    
    
    static {
    	message_url = Global.getConfig("wei.message_url");
    	accounts = Global.getConfig("wei.accounts");
    }
    

    /**
     *  发送微信通知
     * @param message 微信内容
     * @return
     */
    public static void send(String message) {
        JSONObject paramObject = new JSONObject();
        paramObject.put("token", token);
        //收信息人
        paramObject.put("person", accounts);
        paramObject.put("message", message);
        try{
            //发送Post请求
            HttpConUtils httpClient = new HttpConUtils();
            String result = httpClient.doPost(message_url, paramObject.toJSONString());
            if (result == null) {

            } else {
                JSONObject resultObject = JSONObject.parseObject(result);
                if ("ERROR".equals(resultObject.getString("status"))) {
                    String msg = resultObject.getString("msg");
                    System.out.println(msg);
                }
            }
        }catch(Exception e) {
           e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // 您要发送给谁，标题，内容
        //SendMailUtil.send("*****@qq.com", "标题", "内容", "smtp", "smtp.qq.com", "发送人名", "端口号", "用户名", "密码");
        WeiChatUtil.send("测试内容");
    }

}
