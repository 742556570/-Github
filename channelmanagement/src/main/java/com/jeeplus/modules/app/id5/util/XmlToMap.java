package com.jeeplus.modules.app.id5.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
public class XmlToMap {
	/** 
     * xml转map 不带属性 
     * @param xmlStr 
     * @param needRootKey 是否需要在返回的map里加根节点键 
     * @return 
     * @throws DocumentException 
     */  
    public static Map xml2map(String xmlStr, boolean needRootKey) throws DocumentException {  
        Document doc = DocumentHelper.parseText(xmlStr);  
        Element root = doc.getRootElement();  
        Map<String, Object> map = (Map<String, Object>) xml2map(root);  
        if(root.elements().size()==0 && root.attributes().size()==0){  
            return map;  
        }  
        if(needRootKey){  
            //在返回的map里加根节点键（如果需要）  
            Map<String, Object> rootMap = new HashMap<String, Object>();  
            rootMap.put(root.getName(), map);  
            return rootMap;  
        }  
        return map;  
    } 
    /** 
     * xml转map 不带属性 
     * @param e 
     * @return 
     */  
    private static Map xml2map(Element e) {  
        Map map = new LinkedHashMap();  
        List list = e.elements();  
        if (list.size() > 0) {  
            for (int i = 0; i < list.size(); i++) {  
                Element iter = (Element) list.get(i);  
                List mapList = new ArrayList();  
  
                if (iter.elements().size() > 0) {  
                    Map m = xml2map(iter);  
                    if (map.get(iter.getName()) != null) {  
                        Object obj = map.get(iter.getName());  
                        if (!(obj instanceof List)) {  
                            mapList = new ArrayList();  
                            mapList.add(obj);  
                            mapList.add(m);  
                        }  
                        if (obj instanceof List) {  
                            mapList = (List) obj;  
                            mapList.add(m);  
                        }  
                        map.put(iter.getName(), mapList);  
                    } else  
                        map.put(iter.getName(), m);  
                } else {  
                    if (map.get(iter.getName()) != null) {  
                        Object obj = map.get(iter.getName());  
                        if (!(obj instanceof List)) {  
                            mapList = new ArrayList();  
                            mapList.add(obj);  
                            mapList.add(iter.getText());  
                        }  
                        if (obj instanceof List) {  
                            mapList = (List) obj;  
                            mapList.add(iter.getText());  
                        }  
                        map.put(iter.getName(), mapList);  
                    } else  
                        map.put(iter.getName(), iter.getText());  
                }  
            }  
        } else  
            map.put(e.getName(), e.getText());  
        return map;  
    } 
    public static void main(String[] args) throws DocumentException, IOException {  
        String textFromFile = FileUtils.readFileToString(new File("E:/workspace/return.xml"),"UTF-8");  
        Map<String, Object> map = xml2map(textFromFile, false);  ;  
        JSONObject json = JSONObject.fromObject(map); 
        JSONObject jsonMsssage =json.getJSONObject("message");
        JSONObject acctnoInfos =json.getJSONObject("acctnoInfos");
        JSONObject jsonAcctnoInfo =acctnoInfos.getJSONObject("acctnoInfo");
        
        String status = jsonMsssage.getString("status");
        String value = jsonMsssage.getString("value");
        String code = jsonAcctnoInfo.getString("code");
        String message = jsonAcctnoInfo.getString("message");
        System.out.println("status"+status);
        System.out.println("value"+value);
        System.out.println("code"+code);
        System.out.println("message"+message);
        System.out.println(json.toString()); // 格式化输出  
  
    }  
}
