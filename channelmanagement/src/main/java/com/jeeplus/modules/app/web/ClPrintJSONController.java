package com.jeeplus.modules.app.web;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping(value = "api/printJSON")
public class ClPrintJSONController {
	/**
	 * 打印日志信息。
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "setJsonData")
	public String setJsonData(
			@RequestParam(value="content",required = false )String content,
			HttpServletRequest request,
			HttpServletResponse response) {
		String str = new String(); //原有txt内容  
        String s1 = new String();//内容更新  
        try {  
            File f = new File("/home/cashloan/logs/json.txt");  
            if (!f.exists()) {  
            	System.out.print("文件不存在");  
            	f.createNewFile();// 不存在则创建  
            }  
//            BufferedReader input = new BufferedReader(new FileReader(f));  
//  
//            while ((str = input.readLine()) != null) {  
//                s1 += str + "\n";  
//            }  
//            input.close();  
//            s1 += content;  
  
            BufferedWriter output = new BufferedWriter(new FileWriter(f,true));  
            output.write(s1+"\n");
            output.flush();
            output.close();
        } catch (Exception e) {  
            e.printStackTrace();  
  
        }  
        return null;
	}
}
