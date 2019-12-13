package com.jeeplus.modules.app.api.fcpp;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import javax.net.ssl.SSLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jeeplus.modules.app.api.product.PropertiesUtil;


public class IdCardOcr {
	
	
	private final static Logger logger = LoggerFactory.getLogger(IdCardOcr.class);
//	{
//	    "birthday": {
//	        "year": "1988",
//	        "day": "7",
//	        "month": "2"
//	    },
//	    "name": "杨森",
//	    "race": "汉",
//	    "address": "北京市朝阳区双桥东路12号院16号楼3-304号",
//	    "time_used": 669,
//	    "gender": "男",
//	    "head_rect": {
//	        "rt": {
//	            "y": 0.19025736,
//	            "x": 0.80104166
//	        },
//	        "lt": {
//	            "y": 0.1920956,
//	            "x": 0.56927085
//	        },
//	        "lb": {
//	            "y": 0.6590074,
//	            "x": 0.56875
//	        },
//	        "rb": {
//	            "y": 0.6599265,
//	            "x": 0.79895836
//	        }
//	    },
//	    "request_id": "1511951789,e5851e7d-93d6-4720-9804-391b8e95d629",
//	    "id_card_number": "110105198802070414",
//	    "side": "front"
//	}
	
	
	
	
	public JsonObject idCardOcr2(InputStream ins) {
		JsonObject jsobj = new JsonObject();
		byte[] buff = getBytesFromInputStream(ins);
		String url = PropertiesUtil.getString("idcardUrl");//"https://api.faceid.com/faceid/v1/ocridcard";
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, byte[]> byteMap = new HashMap<>();
        map.put("api_key", PropertiesUtil.getString("api_key")/*"C0bl4hqXW3Q-RcTX4VKJoJ2eLbbXq5Bi"*/);
        map.put("api_secret", PropertiesUtil.getString("api_secret")/*"JiUMsHDp1RFidnucn6ArLz_2eAANPzca"*/);
        byteMap.put("image", buff);
        try{
            byte[] bacd = post(url, map, byteMap);
            String str = new String(bacd);
            logger.info(str);
            jsobj = new Gson().fromJson(str, JsonObject.class);
        }catch (Exception e) {
        	e.printStackTrace();
		}
        return jsobj;
	}
	
	
	public JsonObject idCardOcr(String filePath) {
		JsonObject jsobj = new JsonObject();
		File file = new File(filePath);
		byte[] buff = getBytesFromFile(file);
		String url = PropertiesUtil.getString("idcardUrl");//"https://api.faceid.com/faceid/v1/ocridcard";
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, byte[]> byteMap = new HashMap<>();
        map.put("api_key", PropertiesUtil.getString("api_key")/*"C0bl4hqXW3Q-RcTX4VKJoJ2eLbbXq5Bi"*/);
        map.put("api_secret", PropertiesUtil.getString("api_secret")/*"JiUMsHDp1RFidnucn6ArLz_2eAANPzca"*/);
        byteMap.put("image", buff);
        try{
            byte[] bacd = post(url, map, byteMap);
            String str = new String(bacd,"utf-8");
            logger.info(str);
            jsobj = new Gson().fromJson(str, JsonObject.class);
        }catch (Exception e) {
        	e.printStackTrace();
		}
        return jsobj;
	}
	
	private final static int CONNECT_TIME_OUT = 30000;
    private final static int READ_OUT_TIME = 50000;
    private String boundaryString = getBoundary();
    protected byte[] post(String url, HashMap<String, String> map, HashMap<String, byte[]> fileMap) throws Exception {
        HttpURLConnection conne;
        URL url1 = new URL(url);
        conne = (HttpURLConnection) url1.openConnection();
        conne.setDoOutput(true);
        conne.setUseCaches(false);
        conne.setRequestMethod("POST");
        conne.setConnectTimeout(CONNECT_TIME_OUT);
        conne.setReadTimeout(READ_OUT_TIME);
        conne.setRequestProperty("accept", "*/*");
        conne.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundaryString);
        conne.setRequestProperty("connection", "Keep-Alive");
        conne.setRequestProperty("user-agent", "Mozilla/4.0 (compatible;MSIE 6.0;Windows NT 5.1;SV1)");
        DataOutputStream obos = new DataOutputStream(conne.getOutputStream());
        Iterator iter = map.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<String, String> entry = (Map.Entry) iter.next();
            String key = entry.getKey();
            String value = entry.getValue();
            obos.writeBytes("--" + boundaryString + "\r\n");
            obos.writeBytes("Content-Disposition: form-data; name=\"" + key
                    + "\"\r\n");
            obos.writeBytes("\r\n");
            obos.writeBytes(value + "\r\n");
        }
        if(fileMap != null && fileMap.size() > 0){
            Iterator fileIter = fileMap.entrySet().iterator();
            while(fileIter.hasNext()){
                Map.Entry<String, byte[]> fileEntry = (Map.Entry<String, byte[]>) fileIter.next();
                obos.writeBytes("--" + boundaryString + "\r\n");
                obos.writeBytes("Content-Disposition: form-data; name=\"" + fileEntry.getKey()
                        + "\"; filename=\"" + encode(" ") + "\"\r\n");
                obos.writeBytes("\r\n");
                obos.write(fileEntry.getValue());
                obos.writeBytes("\r\n");
            }
        }
        obos.writeBytes("--" + boundaryString + "--" + "\r\n");
        obos.writeBytes("\r\n");
        obos.flush();
        obos.close();
        InputStream ins = null;
        int code = conne.getResponseCode();
        try{
            if(code == 200){
                ins = conne.getInputStream();
            }else{
                ins = conne.getErrorStream();
            }
        }catch (SSLException e){
            e.printStackTrace();
            return new byte[0];
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buff = new byte[4096];
        int len;
        while((len = ins.read(buff)) != -1){
            baos.write(buff, 0, len);
        }
        byte[] bytes = baos.toByteArray();
        ins.close();
        return bytes;
    }
    private String getBoundary() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < 32; ++i) {
            sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-".charAt(random.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_".length())));
        }
        return sb.toString();
    }
    private String encode(String value) throws Exception{
        return URLEncoder.encode(value, "UTF-8");
    }
    
    public byte[] getBytesFromFile(File f) {
        if (f == null) {
            return null;
        }
        try {
            FileInputStream stream = new FileInputStream(f);
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = stream.read(b)) != -1)
                out.write(b, 0, n);
            stream.close();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
        }
        return null;
    }
    
    public byte[] getBytesFromInputStream(InputStream in) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = in.read(b)) != -1)
                out.write(b, 0, n);
            in.close();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
        }
        return null;
    }
    
    public static void main(String[] args) {
    	new IdCardOcr().idCardOcr("D:/te1.jpg");
    	new IdCardOcr().idCardOcr("D:/te2.jpg");
    }
}
