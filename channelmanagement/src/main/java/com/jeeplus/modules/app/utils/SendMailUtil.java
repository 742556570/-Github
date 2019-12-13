package com.jeeplus.modules.app.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jeeplus.common.config.Global;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

/******************************************
 * @author: zhangyoupeng
 * @createDate: 2018/6/24 18:56
 * @company: (C) Copyright 信保数据部
 * @since: JDK 1.8
 * @Description: 发送邮件工具类
 ******************************************/
public class SendMailUtil {

    //收件人
    private static String to;

    //协议
    private static String smtp;

    //发送服务器服务器
    private static String host;

    //邮件发送人
    private static String sendName;
   
    //邮件发送人端口
    private static String sendPort;
   
    //邮件发送人名
    private static String userName;
   
    //邮件发送人密码
    private static String userPwd;
   
    
    static {
    	to = Global.getConfig("mail.to");
    	smtp = Global.getConfig("mail.smtp");
    	host = Global.getConfig("mail.host");
    	sendName = Global.getConfig("mail.sendName");
    	sendPort = Global.getConfig("mail.sendPort");
    	userName = Global.getConfig("mail.userName");
    	userPwd = Global.getConfig("mail.userPwd");
    	
    }
    

    /**
     * 邮件发送的方法
     *
     * @param subject 主题
     * @param content 内容
     * @return 成功或失败
     */
    public static boolean send(String subject, String content) {
        // 第一步：创建Session
        Properties props = new Properties();
        // 指定邮件的传输协议，smtp(Simple Mail Transfer Protocol 简单的邮件传输协议)
        props.put("mail.transport.protocol", smtp);
        // 指定邮件发送服务器服务器 "smtp.qq.com"
        props.put("mail.host", host);
        // 指定邮件的发送人(您用来发送邮件的服务器，比如您的163\sina等邮箱)
        props.put("mail.from", sendName);
        if (true) {
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.socketFactory.fallback", "false");
            props.put("mail.smtp.socketFactory.port", sendPort);
        }
        Session session = Session.getDefaultInstance(props);

        // 开启调试模式
        session.setDebug(true);
        try {
            // 第二步：获取邮件发送对象
            Transport transport = session.getTransport(smtp);
            // 连接邮件服务器，链接您的163、sina邮箱，用户名（不带@163.com，登录邮箱的邮箱账号，不是邮箱地址）、密码
            transport.connect(userName, userPwd);
            // 第三步：创建邮件消息体
            MimeMessage message = new MimeMessage(session);
            //设置自定义发件人昵称
            String nick="";
            try {
                nick=javax.mail.internet.MimeUtility.encodeText("网贷核心");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            message.setFrom(new InternetAddress(nick+" <"+sendName+">"));
            //设置发信人
            // message.setFrom(new InternetAddress(sendName));

            // 邮件的主题
            message.setSubject(subject);
            //收件人
            if(to.indexOf(",") > -1) {
                String[] mails = to.split(",");
                Address[] addresses = new Address[mails.length];
                for(int i=0;i<mails.length;i++) {
                    addresses[i] = new InternetAddress(mails[i]);
                }
                message.addRecipients(Message.RecipientType.TO, addresses);
            }else{
                Address toAddress = new InternetAddress(to);
                message.addRecipient(Message.RecipientType.TO, toAddress);
            }
            /*//抄送人
            Address ccAddress = new InternetAddress("first.lady@whitehouse.gov");
            message.addRecipient(Message.RecipientType.CC, ccAddress);*/
            // 邮件的内容
            message.setContent(content, "text/plain;charset=utf-8");
            // 邮件发送时间
            message.setSentDate(new Date());

            // 第四步：发送邮件
            // 第一个参数：邮件的消息体
            // 第二个参数：邮件的接收人，多个接收人用逗号隔开（test1@163.com,test2@sina.com）
            transport.sendMessage(message, InternetAddress.parse(to));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        // 您要发送给谁，标题，内容
        //SendMailUtil.send("*****@qq.com", "标题", "内容", "smtp", "smtp.qq.com", "发送人名", "端口号", "用户名", "密码");
        
    	String content = "以下提现单号超过2小时未处理："+"\r\n"+"WD2143201807081750W\r\n" + 
    			"WD2531401807011533W\r\n" + 
    			"WD2629601804251519W\r\n" + 
    			"WD2677801806071859W\r\n" + 
    			"WD2678001806072000W\r\n" + 
    			"WD2681001807031417W\r\n" + 
    			"WD2681101807031432W\r\n" + 
    			"WD2681501807041826W";
    	SendMailUtil.send("ceshi", content);
    }

}
