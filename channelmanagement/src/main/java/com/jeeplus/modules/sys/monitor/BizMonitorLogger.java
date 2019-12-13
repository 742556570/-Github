package com.jeeplus.modules.sys.monitor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.utils.StringUtils;

/**
 * 
 * <p>Title: 业务日志监控输出</p>
 * <p>Description:</p>
 * @company Coffee-Ease
 * @author duanyuntao
 * @date 2017年7月3日
 * @time 下午4:50:19
 */
public class BizMonitorLogger {
    private static final Logger logger = LoggerFactory.getLogger(BizMonitorLogger.class);
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    public static void monitor(){
        Map<String, Object> params = new HashMap<String, Object>();
        
        BizMonitor monitor = new BizMonitor();
        
    }

}
