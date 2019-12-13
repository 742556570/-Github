package com.jeeplus.modules.sys.monitor;

import javax.servlet.http.HttpServletRequest;

public class MonitorUtils {
    
    /**
     * 
     * 功能描述：获取真实的IP地址
     * 
     * @param request
     * @return
     * @author guoyx
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (!isnull(ip) && ip.contains(",")) {
            String[] ips = ip.split(",");
            ip = ips[ips.length - 1];
        }
        // 转换IP 格式
        if (!isnull(ip)) {
            ip = ip.replace(".", "_");
        }
        return ip;
    }
    /**
     * str空判断
     * 
     * @param str
     * @return
     * @author guoyx
     */
    public static boolean isnull(String str) {
        if (null == str || str.equalsIgnoreCase("null") || str.equals("")) {
            return true;
        } else
            return false;
    }
}
