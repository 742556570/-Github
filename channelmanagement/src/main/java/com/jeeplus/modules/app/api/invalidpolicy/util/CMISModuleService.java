package com.jeeplus.modules.app.api.invalidpolicy.util;

/**
 * 所有模块对外提供的服务的基类
 * 
 * @author yuhq
 *
 */
public class CMISModuleService {
	private String moduleName = null;
	private String id = null;
	private String describe = null;
	private String className = null;
	private String comproperty = null;
	
	/**
	 * 模块名称
	 * @return 模块名称
	 */
	public String getModuleName() {
		return moduleName;
	}
	
	/**
	 * 模块名称
	 * @param moduleName 模块名称
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	/**
	 * 服务ID
	 * @return 服务ID
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * 服务ID
	 * @param id 服务ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 服务描述
	 * @return 服务描述
	 */
	public String getDescribe() {
		return describe;
	}
	
	/**
	 * 服务描述
	 * @param describe 服务描述
	 */
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	/**
	 * 实现类
	 * @return 实现类
	 */
	public String getClassName() {
		return className;
	}
	
	/**
	 * 实例类
	 * @param className　实现类
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	
	/**
	 * 扩展信息
	 * @return 扩展信息
	 */
	public String getComproperty() {
		return comproperty;
	}
	
	/**
	 * 扩展信息
	 * @param comproperty 扩展信息
	 */
	public void setComproperty(String comproperty) {
		this.comproperty = comproperty;
	}
	
	
	
	
}
