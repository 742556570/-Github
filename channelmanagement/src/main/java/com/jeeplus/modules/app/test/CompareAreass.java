package com.jeeplus.modules.app.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;

public class CompareAreass {

	public static Map<String, String> getWF(String path) throws Exception{
		HashMap<String, String> maps =  new HashMap<String,String>();
		
		List<String> list = FileUtils.readLines(new File(path));
		
		for (String line : list) {
//			line = StringUtils.deleteWhitespace(line);
			String[] kvs = line.split("\t",-1);
			if(kvs.length == 2) {
				String k = kvs[0];
				String v = kvs[1];
				if(!maps.containsKey(k)) {
					maps.put(k, v);
					System.out.println(k+"\t"+v);
				}else {
					System.out.println("------>key exits  "+line);
				}
			} else {
				System.out.println("----->length != 2  "+line);
			}
		}
		
		
		return maps;
	}
	
	
	public static Map<String, String> getOv(String path) throws Exception{
		HashMap<String, String> maps =  new HashMap<String,String>();
		
		List<String> list = FileUtils.readLines(new File(path));
		String k = "";
		String v = "";
		for (String line : list) {
			
			if(line.contains("code")) {
				String tmp = StringUtils.deleteWhitespace(line).replaceAll("\"", "").replaceAll(",", "");
				String[] tmps = tmp.split(":",-1);
				k = tmps[1];
			}
			if(line.contains("name")) {
				String tmp = StringUtils.deleteWhitespace(line).replaceAll("\"", "").replaceAll(",", "");
				String[] tmps = tmp.split(":",-1);
				v = tmps[1];
			}
			if(StringUtils.isNotEmpty(k) && StringUtils.isNotEmpty(v)) {
				maps.put(k, v);
				System.out.println(k+"\t"+v);
				k="";
				v="";
			}
		}
		
		return maps;
	}
	
	
	public static void comp(Map<String, String> wf,Map<String, String> ov) {
		Set<String> wfKeys = wf.keySet();
		for (String wfkey : wfKeys) {
			if(!ov.containsKey(wfkey)) {
				System.out.println("差异，潍坊行政区划编码有，网贷无"+wfkey + ":" + wf.get(wfkey));
				continue;
			}else {
				String wfVal = wf.get(wfkey);
				String ovVal = ov.get(wfkey);
				if(!wfVal.equals(ovVal)) {
					System.out.println("差异，潍坊地名与网贷不同"+wfkey + ":" + wf.get(wfkey) + ":" + ov.get(wfkey));
					continue;
				}
			}
		}
		
		
		Set<String> ovKeys = ov.keySet();
		for (String ovkey : ovKeys) {
			if(!wf.containsKey(ovkey)) {
				System.out.println("差异，网贷行政区划编码有，潍坊无"+ovkey + ":" + ov.get(ovkey));
				continue;
			}else {
				String wfVal = wf.get(ovkey);
				String ovVal = ov.get(ovkey);
				if(!wfVal.equals(ovVal)) {
					System.out.println("差异，网贷地名与潍坊不同"+ovkey + ":" + ov.get(ovkey) + ":" + wf.get(ovkey));
					continue;
				}
			}
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		Map<String, String> ov = getOv("D:/WorkSpace/areas.json");
		
//		Map<String, String> wf = getWF("D:/WorkSpace/wf.txt");
//		
//		System.out.println(ov);
//		System.out.println(wf);
//		comp(wf,ov);
	}
}
