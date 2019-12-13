package com.jeeplus.modules.app.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.xstream.XStreamUtils;
import com.jeeplus.modules.app.api.account.guarantyidnotify.request.GuarantyidNotifyServerRequestVO;
import com.jeeplus.modules.app.task.UnGuarantyidNotifyServer;

@Controller
@RequestMapping(value = "api/guarantyidnotify")
public class TGuarantyidNotifyServer {

	@ResponseBody
	@RequestMapping(value = "process")
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String xmlStr = parseReq(request);

		GuarantyidNotifyServerRequestVO guarantyidNotifyServerRequestVO = XStreamUtils.covertXml2JavaBean(xmlStr,
				GuarantyidNotifyServerRequestVO.class);
		String guarantyID = guarantyidNotifyServerRequestVO.getGuarantyidNotifyServerBody().getGuarantyID();
		String payChannel = "WFCCB";

		// 创建一个线程池

		ExecutorService pool = Executors.newFixedThreadPool(2);

		// 创建返回值的任务

		Callable c1 = new UnGuarantyidNotifyServer(guarantyID, payChannel);

		// 执行任务并获取Future对象

		Future f1 = pool.submit(c1);

		// 关闭线程池

		pool.shutdown();

		return f1.get().toString();

	}

	/**
	 * 读取异步通知的数据
	 * 
	 * @param request
	 * @return
	 */
	private String parseReq(HttpServletRequest request) {

		BufferedReader br;
		StringBuilder sb;
		String xmlStr = "";
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
			String line = null;
			sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			xmlStr = sb.toString();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xmlStr;
	}
}
