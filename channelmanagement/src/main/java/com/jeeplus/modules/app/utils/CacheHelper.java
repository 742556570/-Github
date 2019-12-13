package com.jeeplus.modules.app.utils;

import java.util.ArrayList;
import java.util.List;

import com.jeeplus.common.utils.CacheUtils;

import com.jeeplus.common.utils.JedisClusterUtils;
import com.jeeplus.common.utils.JedisUtils;
import com.jeeplus.modules.app.constant.AppCommonConstants;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.entity.ClUsrLoanDetail;

public class CacheHelper {
	
	public static void setObject(String schema,String key,Object value,int ttl){
		if("eacache".equals(AppCommonConstants.getValStr("CACHENAME"))){
			CacheUtils.put(schema, key, value,ttl);
		}else if ("jedisCluster".equals(AppCommonConstants.getValStr("CACHENAME"))){
			JedisClusterUtils.setObject(schema+"_"+key , value, ttl);
		}else if ("jedis".equals(AppCommonConstants.getValStr("CACHENAME"))) {
			JedisUtils.setObject(schema+"_"+key, value, ttl);
		}
	}
	
	
	public static void setObject(String schema,String key,Object value){
		if("eacache".equals(AppCommonConstants.getValStr("CACHENAME"))){
			CacheUtils.put(schema, key, value);
		
		}else if ("jedisCluster".equals(AppCommonConstants.getValStr("CACHENAME"))){
			JedisClusterUtils.setObject(schema+"_"+key , value, 0);
		}else if ("jedis".equals(AppCommonConstants.getValStr("CACHENAME"))) {
			JedisUtils.setObject(schema+"_"+key, value,0);
		}
	}
	
	
	public static Object getObject(String schema,String key){
		Object obj = null;
		if("eacache".equals(AppCommonConstants.getValStr("CACHENAME"))){
			obj = CacheUtils.get(schema,key);
		}else if ("jedisCluster".equals(AppCommonConstants.getValStr("CACHENAME"))){
			obj = JedisClusterUtils.getObject(schema+"_"+key);
		}else if ("jedis".equals(AppCommonConstants.getValStr("CACHENAME"))) {
			obj = JedisUtils.getObject(schema+"_"+key);
		}
		
		return obj;
	}
	
	public static void removeObject(String schema,String key){
		if("eacache".equals(AppCommonConstants.getValStr("CACHENAME"))){
			CacheUtils.remove(schema, key);
		}else if ("jedisCluster".equals(AppCommonConstants.getValStr("CACHENAME"))){
			JedisClusterUtils.delObject(schema+"_"+key);
		}else if ("jedis".equals(AppCommonConstants.getValStr("CACHENAME"))){
			JedisUtils.delObject(schema+"_"+key);
		}
	}
	
	
	public static List<Object> getListObject(String schema,String key){
		List<Object> list = null;
		if("eacache".equals(AppCommonConstants.getValStr("CACHENAME"))){
			Object tmp = CacheUtils.get(schema,key);
			if(tmp instanceof List){
				list = (List)tmp;
			}else{
				list = new ArrayList<Object>();
			}
		} else if ("jedisCluster".equals(AppCommonConstants.getValStr("CACHENAME"))){
			Object tmp = JedisClusterUtils.getObject(schema+"_"+key);
			if(tmp !=null && tmp instanceof List){
				list = (List)tmp;
			} else if(tmp == null){
				list = new ArrayList<Object>();
			}
		}else if ("jedis".equals(AppCommonConstants.getValStr("CACHENAME"))) {
			Object tmp = JedisUtils.getObject(schema+"_"+key);
			if(tmp instanceof List){
				list = (List)tmp;
			}else{
				list = new ArrayList<Object>();
			}
		}
		return list;
	}
	
	public static void setListLoanDetailObject(String schema,String key,ClUsrLoanDetail value,int ttl){
		List<ClUsrLoanDetail> list = null;
		if("eacache".equals(AppCommonConstants.getValStr("CACHENAME"))){
			Object tmp = CacheUtils.get(schema,key);
			if(tmp instanceof List){
				list = (List)tmp;
			}else{
				list = new ArrayList<ClUsrLoanDetail>();
			}
			list.add(value);
			CacheUtils.put(schema, key, list, ttl);
		}else if ("jedisCluster".equals(AppCommonConstants.getValStr("CACHENAME"))){
			Object tmp = JedisClusterUtils.getObject(schema+"_"+key);
			if(tmp !=null && tmp instanceof List){
				list = (List)tmp;
			}
			if(tmp == null){
				list = new ArrayList<ClUsrLoanDetail>();
			}
			list.add(value);
			JedisClusterUtils.setObject(schema+"_"+key, list, ttl);
		}else if ("jedis".equals(AppCommonConstants.getValStr("CACHENAME"))) {
			Object tmp = JedisUtils.getObject(schema+"_"+key);
			if(tmp instanceof List){
				list = (List)tmp;
			}else{
				list = new ArrayList<ClUsrLoanDetail>();
			}
			list.add(value);
			JedisUtils.setObject(schema+"_"+key, list, ttl);
		}
	}
	
	
	public static List<ClUsrLoanDetail> getListLoanDetailObject(String schema,String key){
		List<ClUsrLoanDetail> list = null;
		if("eacache".equals(AppCommonConstants.getValStr("CACHENAME"))){
			Object tmp = CacheUtils.get(schema,key);
			if(tmp instanceof List){
				list = (List)tmp;
			}else{
				list = new ArrayList<ClUsrLoanDetail>();
			}
		} else if ("jedisCluster".equals(AppCommonConstants.getValStr("CACHENAME"))){
			Object tmp = JedisClusterUtils.getObject(schema+"_"+key);
			if(tmp !=null && tmp instanceof List){
				list = (List)tmp;
			} else if(tmp == null){
				list = new ArrayList<ClUsrLoanDetail>();
			}
		}else if ("jedis".equals(AppCommonConstants.getValStr("CACHENAME"))) {
			Object tmp = JedisUtils.getObject(schema+"_"+key);
			if(tmp instanceof List){
				list = (List)tmp;
			}else{
				list = new ArrayList<ClUsrLoanDetail>();
			}
		}
		return list;
	}
	
	public static void setListObject(String schema,String key,Object value,int ttl){
		List<Object> list = null;
		if("eacache".equals(AppCommonConstants.getValStr("CACHENAME"))){
			Object tmp = CacheUtils.get(schema,key);
			if(tmp instanceof List){
				list = (List)tmp;
			}else{
				list = new ArrayList<Object>();
			}
			list.add(value);
			CacheUtils.put(schema, key, list, ttl);
		}else if ("jedisCluster".equals(AppCommonConstants.getValStr("CACHENAME"))){
			Object tmp = JedisClusterUtils.getObject(schema+"_"+key);
			if(tmp !=null && tmp instanceof List){
				list = (List)tmp;
			}
			if(tmp == null){
				list = new ArrayList<Object>();
			}
			list.add(value);
			JedisClusterUtils.setObject(schema+"_"+key, list, ttl);
		}else if ("jedis".equals(AppCommonConstants.getValStr("CACHENAME"))) {
			Object tmp = JedisUtils.getObject(schema+"_"+key);
			if(tmp instanceof List){
				list = (List)tmp;
			}else{
				list = new ArrayList<Object>();
			}
			list.add(value);
			JedisUtils.setObject(schema+"_"+key, list, ttl);
		}
	}
	public static void setListClear(String schema,String key,int ttl){
		List<Object> list = new ArrayList<Object>();
		if("eacache".equals(AppCommonConstants.getValStr("CACHENAME"))){
			CacheUtils.put(schema, key, list, ttl);
		}else if ("jedisCluster".equals(AppCommonConstants.getValStr("CACHENAME"))){
			JedisClusterUtils.setObject(schema+"_"+key, list, ttl);
		}else if ("jedis".equals(AppCommonConstants.getValStr("CACHENAME"))) {
			JedisUtils.setObject(schema+"_"+key, list, ttl);
		}
	}
	
	
	public static void setListObject(String schema,String key,Object value){
		List<Object> list = null;
		if("eacache".equals(AppCommonConstants.getValStr("CACHENAME"))){
			Object tmp = CacheUtils.get(schema,key);
			if(tmp instanceof List){
				list = (List)tmp;
			}else{
				list = new ArrayList<Object>();
			}
			list.add(value);
			CacheUtils.put(schema, key, list);
		}else if ("jedisCluster".equals(AppCommonConstants.getValStr("CACHENAME"))){
			Object  tmp = JedisClusterUtils.getObject(schema+"_"+key);
			if(tmp != null && tmp instanceof List){
				list = (List)tmp;
			}
			if(tmp == null){
				list = new ArrayList<Object>();
			}
			list.add(value);
			JedisClusterUtils.setObject(schema+"_"+key, list, 0);
		}
		else if ("jedis".equals(AppCommonConstants.getValStr("CACHENAME"))) {
			Object tmp = JedisUtils.getObject(schema+"_"+key);
			if(tmp !=null && tmp instanceof List){
				list = (List)tmp;
			}
			if(tmp == null){
				list = new ArrayList<Object>();
			}
			list.add(value);
			JedisUtils.setObject(schema+"_"+key, list, 0);
		}
	}
	
	
	public static ClUsr getCachedUsr(String token) {
		Object obj = getObject(AppCommonConstants.getValStr("CACHESCHEMA"), token+AppCommonConstants.getValStr("CACHETYPESUFFIX_LOGIN"));
		if(obj == null) {
			return null;
		}
		if(obj instanceof ClUsr) {
			return (ClUsr)obj;
		}else {
			return null;
		}
	}
	
	
	public static void removeCachedUsr(String token) {
		removeObject(AppCommonConstants.getValStr("CACHESCHEMA"), token+AppCommonConstants.getValStr("CACHETYPESUFFIX_LOGIN"));
	}

	public static String getCachedToken(String usrCde) {
		Object obj = getObject(AppCommonConstants.getValStr("CACHESCHEMA"), usrCde+AppCommonConstants.getValStr("CACHETYPESUFFIX_LOGIN"));
		if(obj == null) {
			return null;
		}
		if(obj instanceof String) {
			return (String)obj;
		}else {
			return null;
		}
	}
	
}
