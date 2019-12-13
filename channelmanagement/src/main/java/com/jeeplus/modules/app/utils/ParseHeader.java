package com.jeeplus.modules.app.utils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class ParseHeader {

	private HttpServletRequest request;

	public ParseHeader(HttpServletRequest request) {
		this.request = request;
	}

	public Map<String, String> getHeadersInfo() {

		Map<String, String> map = new HashMap<String, String>();

		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = headerNames.nextElement();
			String value = request.getHeader(key);
			map.put(key, value);
		}
		map.put("ip", getIpAddr(request));
		return map;
	}

	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		} else {
			return request.getRemoteAddr();
		}
	}
}
