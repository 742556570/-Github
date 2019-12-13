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
import com.jeeplus.modules.app.api.product.PropertiesUtil;
/**
 * form-data  Post请求
 * 
 */
public class VerifyH5 {
	
	
	/**
	 * 获取token
	 */
	
	public String getToken(String idcard_name,String idcard_number,String biz_no) {
		
		String url = PropertiesUtil.getString("getToken");
		Map<String, String> textMap = new HashMap<String, String>();
		HashMap<String,String> fileMap = new HashMap<>();
		textMap.put("api_key", PropertiesUtil.getString("api_key")/*"C0bl4hqXW3Q-RcTX4VKJoJ2eLbbXq5Bi"*/);
		textMap.put("api_secret", PropertiesUtil.getString("api_secret")/*"JiUMsHDp1RFidnucn6ArLz_2eAANPzca"*/);
		textMap.put("return_url", "http://10.7.128.33:9090/h5/index.html#/Face");
		textMap.put("notify_url", "http://10.7.128.33:9090/h5/index.html#/Face");
		textMap.put("biz_no", biz_no);
		textMap.put("comparison_type", "1");
		textMap.put("idcard_mode", "0");
		textMap.put("idcard_name",idcard_name);
		textMap.put("idcard_number",idcard_number);
		String contentType = "application/octet-stream";
		String ret = formUpload(url, textMap, fileMap,contentType);
		return (ret);
	}
	
	
	
	
public String getResult(String biz_id) {
		
		String url = PropertiesUtil.getString("getResult");
		Map<String, String> textMap = new HashMap<String, String>();
		HashMap<String,String> fileMap = new HashMap<>();
		textMap.put("api_key", PropertiesUtil.getString("api_key")/*"C0bl4hqXW3Q-RcTX4VKJoJ2eLbbXq5Bi"*/);
		textMap.put("api_secret", PropertiesUtil.getString("api_secret")/*"JiUMsHDp1RFidnucn6ArLz_2eAANPzca"*/);
		textMap.put("biz_id", biz_id);
		textMap.put("return_image", "1");
		String ret = new HttpConUtils().doGet(url, textMap);
		return (ret);
	}
	
	
	


	/**
	 * 上传文件图片 
	 */
	public String verify(String idcard_name,String idcard_number,String filePath ){
		String url = PropertiesUtil.getString("verifyUrl");//"https://api.megvii.com/faceid/v2/verify";
		Map<String, String> textMap = new HashMap<String, String>();
		HashMap<String,String> fileMap = new HashMap<>();
		textMap.put("api_key", PropertiesUtil.getString("api_key")/*"C0bl4hqXW3Q-RcTX4VKJoJ2eLbbXq5Bi"*/);
		textMap.put("api_secret", PropertiesUtil.getString("api_secret")/*"JiUMsHDp1RFidnucn6ArLz_2eAANPzca"*/);
		textMap.put("comparison_type", "1");
		textMap.put("face_image_type", "raw_image");
		textMap.put("idcard_name",idcard_name);
		textMap.put("idcard_number",idcard_number);
		fileMap.put("image", filePath);
		String contentType = "application/octet-stream";
		String ret = formUpload(url, textMap, fileMap,contentType);
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
	
	public static void main(String[] args) {
		//System.out.println(new VerifyH5().getToken("卢金龙", "142733199603263619", "163230958089142272"));
		//{"time_used": 63, "token": "86b7ae772f3f3276d3c3de8bc872bbce", "request_id": "1528198688,91e0e492-0788-4d42-af5a-1c28dffe96bb", "expired_time": 1528202288, "biz_id": "1528198688,7ded0445-38ed-443c-8c54-5cc422d56d65"}
		System.out.println(new VerifyH5().getResult("1528963405,e219bde0-30c2-4f40-934f-9410869427dc"));
		//{"status": "NOT_STARTED", "time_used": 59, "biz_info": {"biz_no": "163230958089142272", "biz_extra_data": "", "biz_id": "1528198688,7ded0445-38ed-443c-8c54-5cc422d56d65"}, "request_id": "1528200010,b1aed12a-1f19-4cf1-b7b1-c18e5742630b"}
	}
	
}
