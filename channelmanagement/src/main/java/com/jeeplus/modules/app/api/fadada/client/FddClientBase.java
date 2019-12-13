/*
  版权所有：深圳法大大网络科技有限公司
  Copyright 2015 fadada.com Inc.
  All right reserved.
 ====================================================
  文件名称: FddClientBase.java
  修订记录：
  No    日期				作者(操作:具体内容)
  1.    Dec 18, 2015			Mocuishle(创建:创建文件)
 ====================================================
  类描述：(说明未实现或其它不应生成javadoc的内容)

 */
package com.jeeplus.modules.app.api.fadada.client;


import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;

import com.jeeplus.modules.app.api.fadada.client.common.FddClient;
import com.jeeplus.modules.app.api.fadada.util.crypt.FddEncryptTool;
import com.jeeplus.modules.app.api.fadada.util.http.HttpsUtil;

/**
 * <h3>概要:</h3>法大大基础接口调用类 <br>
 * <h3>功能:</h3>
 * <ol>
 * <li>个人CA证书申请接口</li>
 * <li>文档传输接口</li>
 * <li>合同模板传输接口</li>
 * <li>合同生成接口</li>
 * <li>文档签署接口（手动签署模式）</li>
 * <li>文档签署接口（自动签署模式）</li>
 * <li>客户签署状态查询接口</li>
 * <li>归档接口</li>
 * </ol>
 * <h3>履历:</h3>
 * <ol>
 * <li>2017年3月15日[zhoujy] 新建</li>
 * </ol>
 */
public class FddClientBase extends FddClient {

    /**
     * 概要：FddClientBase类的构造函数
     * @param appId   请传入贵平台的appId
     * @param secret  请传入贵平台的secret
     * @param version 版本号，默认2.0
     * @param url     接口地址
     */
    public FddClientBase(String appId, String secret, String version, String url) {
        super(appId, secret, version, url);
    }

    /**
     * 调用个人ca注册
     * @param customer_name 名称
     * @param email         邮箱
     * @param id_card       证件号码
     * @param ident_type    证件类型
     * @param mobile        手机号
     */
    public String invokeSyncPersonAuto(String customer_name, String email, String id_card, String ident_type, String mobile) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            String msgDigest;
            // Base64(SHA1(appid+md5(timestamp)+SHA1(appsecret)))
            String sha1 = FddEncryptTool.sha1(super.getAppId() + FddEncryptTool.md5Digest(timeStamp) + FddEncryptTool.sha1(super.getSecret()));
            msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes()));

            params.add(new BasicNameValuePair("customer_name", customer_name));
            params.add(new BasicNameValuePair("email", email));
            String id_mobile = FddEncryptTool.encrypt(id_card + "|" + mobile, super.getSecret());
            params.add(new BasicNameValuePair("ident_type", ident_type));
            params.add(new BasicNameValuePair("id_mobile", id_mobile));

            params.add(new BasicNameValuePair("app_id", super.getAppId()));
            params.add(new BasicNameValuePair("timestamp", timeStamp));
            params.add(new BasicNameValuePair("v", super.getVersion()));
            params.add(new BasicNameValuePair("msg_digest", msgDigest));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return HttpsUtil.doCaPost(super.getURLOfSyncPersonAuto(), params);
    }

    /**
     * 调用文档传输接口
     * @param contract_id 合同编号
     * @param doc_title   合同标题
     * @param file        合同文件,与doc_url两个只传一个
     * @param doc_url     合同文件URL（公网）地址
     * @param doc_type    合同类型（.pdf）
     */
    public String invokeUploadDocs(String contract_id, String doc_title, File file, String doc_url, String doc_type) {
        MultipartEntity reqEntity = new MultipartEntity(); // 对请求的表单域进行填充
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            String msgDigest;
            // Base64(SHA1(app_id+md5(timestamp)+SHA1(app_secret+ contract_id )))
            String sha1 = FddEncryptTool.sha1(super.getAppId() + FddEncryptTool.md5Digest(timeStamp) + FddEncryptTool.sha1(super.getSecret() + contract_id));
            msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes()));

            if (file != null) {
                FileBody fileBody = new FileBody(file); // 创建待处理的文件
                reqEntity.addPart("file", fileBody);
            }
            if (doc_url != null) {
                doc_url = doc_url.replaceAll(" ", "%20");
                reqEntity.addPart("doc_url", new StringBody(doc_url, Charset.forName(HttpsUtil.charset)));
            }
            reqEntity.addPart("contract_id", new StringBody(contract_id, Charset.forName(HttpsUtil.charset)));
            reqEntity.addPart("doc_title", new StringBody(doc_title, Charset.forName(HttpsUtil.charset)));
            reqEntity.addPart("doc_type", new StringBody(doc_type, Charset.forName(HttpsUtil.charset)));

            reqEntity.addPart("app_id", new StringBody(super.getAppId()));
            reqEntity.addPart("v", new StringBody(super.getVersion()));
            reqEntity.addPart("timestamp", new StringBody(timeStamp));
            reqEntity.addPart("msg_digest", new StringBody(msgDigest));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return HttpsUtil.doPost(super.getURLOfUploadDocs(), reqEntity);
    }

    /**
     * 调用上传合同模板接口
     * @param template_id 模板ID
     * @param file        模板文件
     * @param doc_url     模板URL
     */
    public String invokeUploadTemplate(String template_id, File file, String doc_url) {
        MultipartEntity reqEntity = new MultipartEntity(); // 对请求的表单域进行填充
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            String msgDigest;
            // Base64(SHA1(app_id+md5(timestamp)+SHA1(app_secret+ template_id )))
            String sha1 = FddEncryptTool.sha1(super.getAppId() + FddEncryptTool.md5Digest(timeStamp) + FddEncryptTool.sha1(super.getSecret() + template_id));
            msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes()));

            reqEntity.addPart("template_id", new StringBody(template_id, Charset.forName(HttpsUtil.charset)));
            if (null != file) {
                FileBody fileBody = new FileBody(file); // 创建待处理的文件
                reqEntity.addPart("file", fileBody);
            }
            if (null != doc_url) {
                doc_url = doc_url.replaceAll(" ", "%20");
                reqEntity.addPart("doc_url", new StringBody(doc_url, Charset.forName(HttpsUtil.charset)));
            }

            reqEntity.addPart("app_id", new StringBody(super.getAppId()));
            reqEntity.addPart("v", new StringBody(super.getVersion()));
            reqEntity.addPart("timestamp", new StringBody(timeStamp));
            reqEntity.addPart("msg_digest", new StringBody(msgDigest));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return HttpsUtil.doPost(super.getURLOfUploadTemplate(), reqEntity);
    }

    /**
     * 删除合同模板接口
     * @param template_id 模板ID
     * @param doc_url     模板URL
     */
    public String invokeTemplateDelete(String template_id) {
        MultipartEntity reqEntity = new MultipartEntity(); // 对请求的表单域进行填充
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            String msgDigest;
            String sha1 = FddEncryptTool.sha1(super.getAppId() + FddEncryptTool.md5Digest(timeStamp) + FddEncryptTool.sha1(super.getSecret() + template_id));
            msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes()));

            reqEntity.addPart("template_id", new StringBody(template_id, Charset.forName(HttpsUtil.charset)));
            reqEntity.addPart("app_id", new StringBody(super.getAppId()));
            reqEntity.addPart("v", new StringBody(super.getVersion()));
            reqEntity.addPart("timestamp", new StringBody(timeStamp));
            reqEntity.addPart("msg_digest", new StringBody(msgDigest));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return HttpsUtil.doPost(super.getURLinvokeTemplateDelete(), reqEntity);
    }
    /**
     * 调用生成合同接口
     * @param template_id    合同模板编号
     * @param contract_id    合同编号
     * @param doc_title      签署文档标题
     * @param font_size      字体大小 不传则为默认值9
     * @param font_type      字体类型 0-宋体；1-仿宋；2-黑体；3-楷体；4-微软雅黑
     * @param parameter_map  填充内容
     * @param dynamic_tables 动态表单
     */
    public String invokeGenerateContract(String template_id, String contract_id, String doc_title, String font_size, String font_type, String parameter_map, String dynamic_tables) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            String msgDigest;
            // Base64(SHA1(app_id+md5(timestamp)+SHA1(app_secret+ template_id + contract_id ) +parameter_map))
            String sha1 = FddEncryptTool.sha1(super.getAppId() + FddEncryptTool.md5Digest(timeStamp) + FddEncryptTool.sha1(super.getSecret() + template_id + contract_id) + parameter_map);
            msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes()));

            params.add(new BasicNameValuePair("template_id", template_id));
            params.add(new BasicNameValuePair("doc_title", doc_title));
            params.add(new BasicNameValuePair("contract_id", contract_id));
            params.add(new BasicNameValuePair("font_size", font_size));
            params.add(new BasicNameValuePair("font_type", font_type));
            params.add(new BasicNameValuePair("parameter_map", parameter_map));
            params.add(new BasicNameValuePair("dynamic_tables", dynamic_tables));

            params.add(new BasicNameValuePair("app_id", super.getAppId()));
            params.add(new BasicNameValuePair("timestamp", timeStamp));
            params.add(new BasicNameValuePair("v", super.getVersion()));
            params.add(new BasicNameValuePair("msg_digest", msgDigest));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return HttpsUtil.doPost(super.getURLOfGenerateContract(), params);
    }

    /**
     * 调用签署接口（手动签模式）
     * @param transaction_id 交易号，长度小于等于32位
     * @param customer_id    客户编号
     * @param contract_id    合同编号
     * @param doc_title      文档标题
     * @param sign_keyword   定位关键字
     * @param return_url     跳转地址
     * @param notify_url     异步通知地址
     */
    public String invokeExtSign(String transaction_id, String customer_id, String contract_id, String doc_title, String sign_keyword, String return_url, String notify_url) {
        String timeStamp = HttpsUtil.getTimeStamp();
        StringBuilder sb = new StringBuilder(super.getURLOfExtSign());
        try {
            String msgDigest;
            // Base64(SHA1(app_id+md5(transaction_id+timestamp)+SHA1(app_secret+ customer_id)))
            String sha1 = FddEncryptTool.sha1(super.getAppId() + FddEncryptTool.md5Digest(transaction_id + timeStamp) + FddEncryptTool.sha1(super.getSecret() + customer_id));
            msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes()));

            sb.append("?app_id=").append(super.getAppId());
            sb.append("&v=").append(super.getVersion());
            sb.append("&timestamp=").append(timeStamp);
            sb.append("&transaction_id=").append(transaction_id);
            sb.append("&customer_id=").append(customer_id);
            sb.append("&contract_id=").append(contract_id);
            sb.append("&doc_title=").append(URLEncoder.encode(doc_title, HttpsUtil.charset));
            sb.append("&sign_keyword=").append(URLEncoder.encode(sign_keyword, HttpsUtil.charset));
            sb.append("&return_url=").append(URLEncoder.encode(return_url, HttpsUtil.charset));
            sb.append("&notify_url=").append(URLEncoder.encode(notify_url, HttpsUtil.charset));
            sb.append("&msg_digest=").append(msgDigest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    /**
     * 调用签署接口（自动签模式） - 关键字定位
     * @param transaction_id   交易号
     * @param customer_id      客户编号
     * @param client_role      客户角色
     * @param contract_id      合同编号
     * @param doc_title        文档标题
     * @param sign_keyword     定位关键字
     * @param keyword_strategy 签章策略
     * @param notify_url       异步通知地址
     */
    public String invokeExtSignAuto(String transaction_id, String customer_id, String client_role, String contract_id, String doc_title, String sign_keyword, String keyword_strategy, String
            notify_url) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            String msgDigest;
            // Base64(SHA1(app_id+md5(transaction_id+timestamp)+SHA1(app_secret+ customer_id)))
            String sha1 = FddEncryptTool.sha1(super.getAppId() + FddEncryptTool.md5Digest(transaction_id + timeStamp) + FddEncryptTool.sha1(super.getSecret() + customer_id));
            msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes()));

            params.add(new BasicNameValuePair("transaction_id", transaction_id));
            params.add(new BasicNameValuePair("customer_id", customer_id));
            params.add(new BasicNameValuePair("client_role", client_role));
            params.add(new BasicNameValuePair("contract_id", contract_id));
            params.add(new BasicNameValuePair("doc_title", doc_title));
            params.add(new BasicNameValuePair("position_type", "0"));
            params.add(new BasicNameValuePair("sign_keyword", sign_keyword));
            params.add(new BasicNameValuePair("keyword_strategy", keyword_strategy));
            params.add(new BasicNameValuePair("notify_url", notify_url));

            params.add(new BasicNameValuePair("app_id", super.getAppId()));
            params.add(new BasicNameValuePair("timestamp", timeStamp));
            params.add(new BasicNameValuePair("v", super.getVersion()));
            params.add(new BasicNameValuePair("msg_digest", msgDigest));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return HttpsUtil.doPost(super.getURLOfExtSignAuto(), params);
    }

    /**
     * 调用签署接口（自动签模式） - 坐标定位
     * @param transaction_id      交易号
     * @param customer_id         客户编号
     * @param client_role         客户角色
     * @param contract_id         合同编号
     * @param doc_title           文档标题
     * @param signature_positions 定位坐标
     * @param notify_url          异步通知地址
     */
    public String invokeExtSignAutoXY(String transaction_id, String customer_id, String client_role, String contract_id, String doc_title, String signature_positions, String notify_url) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            String msgDigest;
            // Base64(SHA1(app_id+md5(transaction_id+timestamp)+SHA1(app_secret+ customer_id)))
            String sha1 = FddEncryptTool.sha1(super.getAppId() + FddEncryptTool.md5Digest(transaction_id + timeStamp) + FddEncryptTool.sha1(super.getSecret() + customer_id));
            msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes()));

            params.add(new BasicNameValuePair("transaction_id", transaction_id));
            params.add(new BasicNameValuePair("customer_id", customer_id));
            params.add(new BasicNameValuePair("client_role", client_role));
            params.add(new BasicNameValuePair("contract_id", contract_id));
            params.add(new BasicNameValuePair("doc_title", doc_title));
            params.add(new BasicNameValuePair("position_type", "1"));
            params.add(new BasicNameValuePair("signature_positions", signature_positions));
            params.add(new BasicNameValuePair("notify_url", notify_url));

            params.add(new BasicNameValuePair("app_id", super.getAppId()));
            params.add(new BasicNameValuePair("timestamp", timeStamp));
            params.add(new BasicNameValuePair("v", super.getVersion()));
            params.add(new BasicNameValuePair("msg_digest", msgDigest));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return HttpsUtil.doPost(super.getURLOfExtSignAuto(), params);
    }

    /**
     * 调用客户签署状态查询接口
     * @param contract_id 合同编号
     * @param customer_id 客户编号
     */
    public String invokeQuerySignStatus(String contract_id, String customer_id) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            String msgDigest;
            // Base64(SHA1(app_id+md5(timestamp)+SHA1(app_secret+ contract_id+customer_id)))
            String sha1 = FddEncryptTool.sha1(super.getAppId() + FddEncryptTool.md5Digest(timeStamp) + FddEncryptTool.sha1(super.getSecret() + contract_id + customer_id));
            msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes())).trim();

            params.add(new BasicNameValuePair("contract_id", contract_id));
            params.add(new BasicNameValuePair("customer_id", customer_id));

            params.add(new BasicNameValuePair("app_id", super.getAppId()));
            params.add(new BasicNameValuePair("timestamp", timeStamp));
            params.add(new BasicNameValuePair("v", super.getVersion()));
            params.add(new BasicNameValuePair("msg_digest", msgDigest));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return HttpsUtil.doPost(super.getURLOfQuerySignStatus(), params);
    }

    /**
     * 调用合同归档
     * @param contract_id 合同编号
     */
    public String invokeContractFilling(String contract_id) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            String msgDigest;
            // Base64(SHA1(app_id+md5(timestamp)+SHA1(app_secret+ contract_id )))
            String sha1 = FddEncryptTool.sha1(super.getAppId() + FddEncryptTool.md5Digest(timeStamp) + FddEncryptTool.sha1(super.getSecret() + contract_id));
            msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes()));

            params.add(new BasicNameValuePair("contract_id", contract_id));

            params.add(new BasicNameValuePair("app_id", super.getAppId()));
            params.add(new BasicNameValuePair("timestamp", timeStamp));
            params.add(new BasicNameValuePair("v", super.getVersion()));
            params.add(new BasicNameValuePair("msg_digest", msgDigest));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return HttpsUtil.doPost(super.getURLOfContractFilling(), params);
    }

}
