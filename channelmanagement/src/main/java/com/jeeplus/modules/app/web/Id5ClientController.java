package com.jeeplus.modules.app.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.modules.app.id5.req.Id5Client;

/**
 * 验证四要素
 * 
 * @author 阳光保险
 *
 */
@Controller
@RequestMapping(value = "api/sysCollection")
public class Id5ClientController {


	/**
	 * 前端页面使用的接口
	 * @param request
	 * @param response
	 * @param username
	 * @param idcard
	 * @param phone
	 * @param bankcard
	 * @return
	 * @throws ParseException
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value = "getID5Client")

	public String getID5Client(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "username") String username,@RequestParam(value = "idcard") String idcard,
			@RequestParam(value = "phone") String phone,@RequestParam(value = "bankcard") String bankcard) throws ParseException, IOException {
		JSONObject resultJSONObject=new JSONObject();
		Id5Client Id5Client = new Id5Client();
		String param = username+","+bankcard+","+idcard+","+phone;
		net.sf.json.JSONObject json = Id5Client.buildRequestBackMap(param);
		if(json.get("code").equals("7")) {
			resultJSONObject.put("success",true);
			resultJSONObject.put("message",json.get("message").toString());
		}else{
	        resultJSONObject.put("success",false);
	        resultJSONObject.put("message",json.get("message").toString());
	    }
		this.writeToClient(response, resultJSONObject);
		return null;
	}
	/**
	 * 前后端使用的接口
	 * @param request
	 * @param response
	 * @param username
	 * @param idcard
	 * @param phone
	 * @param bankcard
	 * @return
	 * @throws ParseException
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value = "getID5ClientTO")

	public String getID5ClientTO(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "username") String username,@RequestParam(value = "idcard") String idcard,
			@RequestParam(value = "phone") String phone,@RequestParam(value = "bankcard")String bankcard,@RequestParam(value = "baffleFlg") String baffleFlg) throws ParseException, IOException {
		Id5Client Id5Client = new Id5Client();
		String param = username+","+bankcard+","+idcard+","+phone;
		String json = Id5Client.buildRequest(param, baffleFlg);
		return json;
	}
	/**
	 * AJAX异步请求时，异步向AJAX响应输出数据
	 * @param response
	 * @param modifyJSONObject
	 * @throws IOException
	 */
	public void writeToClient(HttpServletResponse response,JSONObject modifyJSONObject) throws IOException{
	 	response.setCharacterEncoding ("UTF-8");
	    PrintWriter out=response.getWriter();
	    out.println(modifyJSONObject.toString());
	    out.flush();
	    out.close();
	}
}
