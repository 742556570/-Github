package com.jeeplus.modules.app.api.fcpp;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.activation.MimetypesFileTypeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeeplus.common.config.Global;
import com.jeeplus.modules.app.api.product.PropertiesUtil;
/**
 * form-data  Post请求
 * 
 */
public class Verify {

	private final static Logger logger = LoggerFactory.getLogger(Verify.class);
	/**
	 * 上传文件图片 
	 */
	public String verify(String idcard_name,String idcard_number,String detal,String filePath1, String filePath2 ){
		String url = PropertiesUtil.getString("verifyUrl");//"https://api.megvii.com/faceid/v2/verify";
		Map<String, String> textMap = new HashMap<String, String>();
		HashMap<String,String> fileMap = new HashMap<>();
		textMap.put("api_key", PropertiesUtil.getString("api_key")/*"C0bl4hqXW3Q-RcTX4VKJoJ2eLbbXq5Bi"*/);
		textMap.put("api_secret", PropertiesUtil.getString("api_secret")/*"JiUMsHDp1RFidnucn6ArLz_2eAANPzca"*/);
		textMap.put("comparison_type", "1");
		textMap.put("face_image_type", "meglive");
		textMap.put("idcard_name",idcard_name);
		textMap.put("idcard_number",idcard_number);
		textMap.put("delta",detal);
		fileMap.put("image_best", filePath1);
		fileMap.put("image_env", filePath2);
		String contentType = "application/octet-stream";
		String isFPChkPersonOn = Global.getConfig("isFPChkPersonOn");
		String ret = "";
		if("true".equals(isFPChkPersonOn)) {
			ret = formUpload(url, textMap, fileMap,contentType);
		}else {
			ret = "{\"id_exceptions\": {\"id_photo_monochrome\": 0, \"id_attacked\": 0}, \"face_genuineness\": {\"mask_confidence\": 0.0, \"screen_replay_confidence\": 0.0, \"mask_threshold\": 0.5, \"synthetic_face_confidence\": 0.0, \"synthetic_face_threshold\": 0.5, \"screen_replay_threshold\": 0.5, \"face_replaced\": 0}, \"request_id\": \"1539588492,abc99f24-2665-46f8-b94b-956cde099db4\", \"time_used\": 1142, \"result_faceid\": {\"confidence\": 90.475, \"thresholds\": {\"1e-3\": 62.169, \"1e-5\": 74.399, \"1e-4\": 69.315, \"1e-6\": 78.038}}}";
		}
		logger.info("LIVEVERIFY,isFPChkPersonOn:"+isFPChkPersonOn+":"+idcard_number+":"+ret);
		return (ret);
	}

	/**
	 * 上传图片
	 */
	@SuppressWarnings("rawtypes")
	public String formUpload(String urlStr, Map<String, String> textMap,
			Map<String, String> fileMap,String contentType) {
		String res = "";
		HttpURLConnection conn = null;
		String BOUNDARY = "-----------------12345654321-----------"; 
		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(30000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
			conn.setRequestProperty("Content-Type",
					"multipart/form-data; boundary=" + BOUNDARY);
			conn.setRequestProperty("Charset", "UTF-8"); 
			OutputStream out = new DataOutputStream(conn.getOutputStream());
			if (textMap != null) {
				StringBuffer strBuf = new StringBuffer();
				Iterator iter = textMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					String inputName = (String) entry.getKey();
					String inputValue = (String) entry.getValue();
					if (inputValue == null) {
						continue;
					}
					strBuf.append("\r\n").append("--").append(BOUNDARY)
							.append("\r\n");
					strBuf.append("Content-Disposition: form-data; name=\""
							+ inputName + "\"\r\n\r\n");
					strBuf.append(inputValue);
				}
				out.write(strBuf.toString().getBytes("UTF-8"));
			}
			if (fileMap != null) {
				Iterator iter = fileMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					String inputName = (String) entry.getKey();
					String inputValue = (String) entry.getValue();
					if (inputValue == null) {
						continue;
					}
					File file = new File(inputValue);
					String filename = file.getName();
					StringBuffer strBuf = new StringBuffer();
					strBuf.append("\r\n").append("--").append(BOUNDARY)
							.append("\r\n");
					strBuf.append("Content-Disposition: form-data; name=\""
							+ inputName + "\"; filename=\"" + filename
							+ "\"\r\n");
					strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
					out.write(strBuf.toString().getBytes());
					DataInputStream in = new DataInputStream(
							new FileInputStream(file));
					int bytes = 0;
					byte[] bufferOut = new byte[1024];
					while ((bytes = in.read(bufferOut)) != -1) {
						out.write(bufferOut, 0, bytes);
					}
					in.close();
				}
			}
			byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
			out.write(endData);
			out.flush();
			out.close();
			int responseCode = conn.getResponseCode();
		    if (responseCode==200) {
		    	// 读取返回数据
				StringBuffer strBuf = new StringBuffer();
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						conn.getInputStream(),"UTF-8"));
				   
				String line = null;
				while ((line = reader.readLine()) != null) {
					strBuf.append(line).append("\n");
				}
				res = strBuf.toString();
				reader.close();
				reader = null;
			}else{
				StringBuffer error = new StringBuffer();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
						conn.getErrorStream(),"UTF-8"));
				String line1 = null;
				while ((line1=bufferedReader.readLine())!=null) {
				    error.append(line1).append("\n");
				}
				res=error.toString();
				bufferedReader.close();
				bufferedReader=null;
			}
				
		} catch (Exception e) {
			System.out.println("发送POST请求出错。" + e);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		}
		return res;
	}
	
}
