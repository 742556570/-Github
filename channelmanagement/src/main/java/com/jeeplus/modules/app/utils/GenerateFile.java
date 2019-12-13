package com.jeeplus.modules.app.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class GenerateFile {
	public String setJsonData(String content,String filename) {
		OutputStreamWriter output = null;
        try {  
        	
            File f = new File(filename);  
            if (!f.exists()) {  
            	System.out.print("文件不存在");  
            	f.createNewFile();// 不存在则创建  
            }  
            output = new OutputStreamWriter(new FileOutputStream(f, true),"GBK"); 
            output.write(content);
            output.write("\r\n");
            output.flush();
            output.close();
            return "";
        } catch (Exception e) {  
            e.printStackTrace();  
  
        }  
        return null;
	}
	
	public String setData(String filename) {
        try {  
            File f = new File(filename);  
            f.createNewFile();// 不存在则创建  
            OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(f, true),"GBK"); 
            output.flush();
            output.close();
        } catch (Exception e) {  
            e.printStackTrace();  
  
        }  
        return null;
	}
}
