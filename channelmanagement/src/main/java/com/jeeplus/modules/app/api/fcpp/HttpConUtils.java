package com.jeeplus.modules.app.api.fcpp;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
  
public class HttpConUtils {  
    private static PoolingHttpClientConnectionManager connMgr;  
    private static RequestConfig requestConfig;  
    private static final int MAX_TIMEOUT = 1000 * 60 * 5;  
  
    static {  
        // 设置连接池  
        connMgr = new PoolingHttpClientConnectionManager();  
        // 设置连接池大小  
        connMgr.setMaxTotal(100);  
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());  
  
        RequestConfig.Builder configBuilder = RequestConfig.custom();  
        // 设置连接超时  
        configBuilder.setConnectTimeout(MAX_TIMEOUT);  
        // 设置读取超时  
        configBuilder.setSocketTimeout(MAX_TIMEOUT);  
        // 设置从连接池获取连接实例的超时  
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);  
        // 在提交请求之前 测试连接是否可用  
//        configBuilder.setStaleConnectionCheckEnabled(true);  
        requestConfig = configBuilder.build();  
    }  
  
    /** 
     * 发送 GET 请求（HTTP），不带输入数据 
     * @param url 
     * @return 
     */  
    public String doGet(String url) {  
        return doGet(url, new HashMap<String, String>());  
    }  
  
    /** 
     * 发送 GET 请求（HTTP），K-V形式 
     * @param url 
     * @param params 
     * @return 
     */  
    public String doGet(String url, Map<String, String> params) {  
        String apiUrl = url;  
        StringBuffer param = new StringBuffer();  
        int i = 0;  
        for (String key : params.keySet()) {  
            if (i == 0)  
                param.append("?");  
            else  
                param.append("&");  
            param.append(key).append("=").append(params.get(key));  
            i++;  
        }  
        apiUrl += param;  
        String result = null;  
        HttpClient httpclient = new DefaultHttpClient();  
        try {  
            HttpGet httpPost = new HttpGet(apiUrl);  
            HttpResponse response = httpclient.execute(httpPost);  
            int statusCode = response.getStatusLine().getStatusCode();  
  
            System.out.println("执行状态码 : " + statusCode);  
  
            HttpEntity entity = response.getEntity();  
            if (entity != null) {  
                InputStream instream = entity.getContent();  
                result = IOUtils.toString(instream, "UTF-8");  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return result;  
    }  
  
    /** 
     * 发送 POST 请求（HTTP），不带输入数据 
     * @param apiUrl 
     * @return 
     */  
    public String doPost(String apiUrl) {  
        return doPost(apiUrl, new HashMap<String, String>());  
    }  
  
    /** 
     * 发送 POST 请求（HTTP），K-V形式 
     * @param apiUrl API接口URL 
     * @param params 参数map 
     * @return 
     */  
    public String doPost(String apiUrl, Map<String, String> params) {  
        CloseableHttpClient httpClient = HttpClients.createDefault();  
        String httpStr = null;  
        HttpPost httpPost = new HttpPost(apiUrl);  
        CloseableHttpResponse response = null;  
  
        try {  
            httpPost.setConfig(requestConfig);  
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());  
            for (Map.Entry<String, String> entry : params.entrySet()) {  
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry  
                        .getValue().toString());  
                pairList.add(pair);  
            }  
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));  
            response = httpClient.execute(httpPost);  
            System.out.println(response.toString());  
            HttpEntity entity = response.getEntity();  
            httpStr = EntityUtils.toString(entity, "UTF-8");  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (response != null) {  
                try {  
                    EntityUtils.consume(response.getEntity());  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return httpStr;  
    }  
  
    /** 
     * 发送 POST 请求（HTTP），JSON形式 
     * @param apiUrl 
     * @param json json对象 
     * @return 
     */  
    public String doPostJSON(String apiUrl, Object json) {  
        CloseableHttpClient httpClient = HttpClients.createDefault();  
        String httpStr = null;  
        HttpPost httpPost = new HttpPost(apiUrl);  
        CloseableHttpResponse response = null;  
  
        try {  
            httpPost.setConfig(requestConfig);  
            StringEntity stringEntity = new StringEntity(JSONObject.toJSONString(json),"UTF-8");//解决中文乱码问题  
            stringEntity.setContentEncoding("UTF-8");  
            stringEntity.setContentType("application/json");  
            httpPost.setEntity(stringEntity);  
            response = httpClient.execute(httpPost);  
            HttpEntity entity = response.getEntity();  
//            System.out.println(response.getStatusLine().getStatusCode());  
            httpStr = EntityUtils.toString(entity, "UTF-8");  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (response != null) {  
                try {  
                    EntityUtils.consume(response.getEntity());  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return httpStr;  
    }  
    
    
    /** 
     * 发送 POST 请求（HTTP），JSON形式 
     * @param apiUrl 
     * @param json json对象 
     * @return 
     */  
    public String doPost(String apiUrl, String text) {  
        CloseableHttpClient httpClient = HttpClients.createDefault();  
        String httpStr = null;  
        HttpPost httpPost = new HttpPost(apiUrl);  
        CloseableHttpResponse response = null;  
  
        try {  
            httpPost.setConfig(requestConfig);  
            StringEntity stringEntity = new StringEntity(text,"UTF-8");//解决中文乱码问题  
            stringEntity.setContentEncoding("UTF-8");  
            stringEntity.setContentType("text/plain");  
            httpPost.setEntity(stringEntity);  
            response = httpClient.execute(httpPost);  
            HttpEntity entity = response.getEntity();  
//            System.out.println(response.getStatusLine().getStatusCode());  
            httpStr = EntityUtils.toString(entity, "UTF-8");  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (response != null) {  
                try {  
                    EntityUtils.consume(response.getEntity());  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return httpStr;  
    }  
  
    /** 
     * 发送 SSL POST 请求（HTTPS），K-V形式 
     * @param apiUrl API接口URL 
     * @param params 参数map 
     * @return 
     */  
    public String doPostSSL(String apiUrl, Map<String, Object> params) {  
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory()).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();  
        HttpPost httpPost = new HttpPost(apiUrl);  
        CloseableHttpResponse response = null;  
        String httpStr = null;  
  
        try {  
            httpPost.setConfig(requestConfig);  
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());  
            for (Map.Entry<String, Object> entry : params.entrySet()) {  
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry  
                        .getValue().toString());  
                pairList.add(pair);  
            }  
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("utf-8")));  
            response = httpClient.execute(httpPost);  
            int statusCode = response.getStatusLine().getStatusCode();  
            if (statusCode != HttpStatus.SC_OK) {  
                return null;  
            }  
            HttpEntity entity = response.getEntity();  
            if (entity == null) {  
                return null;  
            }  
            httpStr = EntityUtils.toString(entity, "utf-8");  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (response != null) {  
                try {  
                    EntityUtils.consume(response.getEntity());  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return httpStr;  
    }  
  
    /** 
     * 发送 SSL POST 请求（HTTPS），JSON形式 
     * @param apiUrl API接口URL 
     * @param json JSON对象 
     * @return 
     */  
    public String doPostSSLJSON(String apiUrl, String json) {  
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory()).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();  
        HttpPost httpPost = new HttpPost(apiUrl);  
        CloseableHttpResponse response = null;  
        String httpStr = null;  
        System.out.println(json);
        try {  
            httpPost.setConfig(requestConfig);  
            StringEntity stringEntity = new StringEntity(json,"UTF-8");//解决中文乱码问题  
            stringEntity.setContentEncoding("UTF-8");  
            stringEntity.setContentType("application/json");  
            httpPost.setEntity(stringEntity); 
            response = httpClient.execute(httpPost);  
            int statusCode = response.getStatusLine().getStatusCode();  
            if (statusCode != HttpStatus.SC_OK) {  
                return null;  
            }  
            HttpEntity entity = response.getEntity();  
            if (entity == null) {  
                return null;  
            }  
            httpStr = EntityUtils.toString(entity, "utf-8");  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (response != null) {  
                try {  
                    EntityUtils.consume(response.getEntity());  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return httpStr;  
    }  
    
    
    
    
    public String doPostSSLJSON2(String apiUrl, String json) throws ClientProtocolException, IOException {  
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory()).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();  
        HttpPost httpPost = new HttpPost(apiUrl);  
        CloseableHttpResponse response = null;  
        String httpStr = null;  
        System.out.println(json);
        httpPost.setConfig(requestConfig);  
        StringEntity stringEntity = new StringEntity(json,"UTF-8");//解决中文乱码问题  
        stringEntity.setContentEncoding("UTF-8");  
        stringEntity.setContentType("application/json");  
        httpPost.setEntity(stringEntity); 
        response = httpClient.execute(httpPost);  
        int statusCode = response.getStatusLine().getStatusCode();  
        if (statusCode != HttpStatus.SC_OK) {  
            return null;  
        }  
        HttpEntity entity = response.getEntity();  
        if (entity == null) {  
            return null;  
        }  
        httpStr = EntityUtils.toString(entity, "utf-8");  
        if (response != null) {  
	        try {  
	            EntityUtils.consume(response.getEntity());  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        }  
        return httpStr;  
    }  
    /**  
     * 发送xml数据请求到server端  
     * @param url xml请求数据地址  
     * @param xmlString 发送的xml数据流  
     * @return null发送失败，否则返回响应内容  
     */    
    /** 
     * 发送 SSL POST 请求（HTTPS），JSON形式 
     * @param apiUrl API接口URL 
     * @param json JSON对象 
     * @return 
     */  
    public String doPostSSLXML(String apiUrl, String xmlFile) {  
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory()).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();  
        HttpPost httpPost = new HttpPost(apiUrl);  
        CloseableHttpResponse response = null;  
        String httpStr = null;  
        System.out.println(xmlFile);
        try {  
            httpPost.setConfig(requestConfig);  
            StringEntity stringEntity = new StringEntity(xmlFile,"UTF-8");//解决中文乱码问题  
            stringEntity.setContentEncoding("UTF-8");  
            httpPost.setEntity(stringEntity); 
            response = httpClient.execute(httpPost);  
            int statusCode = response.getStatusLine().getStatusCode();  
            if (statusCode != HttpStatus.SC_OK) {  
                return null;  
            }  
            HttpEntity entity = response.getEntity();  
            if (entity == null) {  
                return null;  
            }  
            httpStr = EntityUtils.toString(entity, "utf-8");  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (response != null) {  
                try {  
                    EntityUtils.consume(response.getEntity());  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return httpStr;  
    }  
  
    /** 
     * 创建SSL安全连接 
     * 
     * @return 
     */  
    private SSLConnectionSocketFactory createSSLConnSocketFactory() {  
        SSLConnectionSocketFactory sslsf = null;  
        try {  
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {  
  
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {  
                    return true;  
                }  
            }).build();  
            sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {  
  
//                @Override  
                public boolean verify(String arg0, SSLSession arg1) {  
                    return true;  
                }  
  
//                @Override  
                public void verify(String host, SSLSocket ssl) throws IOException {  
                }  
  
//                @Override  
                public void verify(String host, X509Certificate cert) throws SSLException {  
                }  
  
//                @Override  
                public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {  
                }  
            });  
        } catch (GeneralSecurityException e) {  
            e.printStackTrace();  
        }  
        return sslsf;  
    }  
    
    /** 
     * 测试方法 
     * @param args 
     */  
    public static void main(String[] args) throws Exception {  
//    	String result = new HttpConUtils().doPost("http://223.202.198.165:9006/api/credit/fd/getApplyList", new HashMap<String,String>(){{
//    		put("token","12313ahdslakhdad");
//    		put("page","1");
//    		put("pageSize","20");
//    	}});
//    	
//    	System.out.println(result);
    	
    	
    	String test = "XXXXX;servP0000100012;<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"yes\"?>\r\n" + 
    			"<msgbody>\r\n" + 
    			"    <serviceId>servP0000100012</serviceId>\r\n" + 
    			"    <TYP_CDE>TEST11</TYP_CDE>\r\n" + 
    			"    <ACCT_ROLE_DESC>测试</ACCT_ROLE_DESC>\r\n" + 
    			"    <OPT_TYP>UPDATE</OPT_TYP>\r\n" + 
    			"</msgbody> ";
    	
    	String result = new HttpConUtils().doPost("http://10.8.203.63:9001/ycloans/Cmis2YcloansHttpChannel",test);
    	System.out.println(result);
    }  
}
