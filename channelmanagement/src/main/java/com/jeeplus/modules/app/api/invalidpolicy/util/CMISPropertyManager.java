package com.jeeplus.modules.app.api.invalidpolicy.util;

import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <p>
 * 	cmis.properties配置文件管理类
 * </p>
 * <p>
 * 	系统需要读该配置文件中的内容通过该类来获取，且该类由信贷产品部统一维护，不建议项目修改。 
 * </p>
 *
 * @author yuhq
 * @verion 1.0
 * @since 1.0
 *
 */
public class CMISPropertyManager {
	
	private static final Logger logger = LoggerFactory.getLogger(CMISPropertyManager.class); 
	
	private static CMISPropertyManager instance = new CMISPropertyManager();
	private static boolean isInit = false;//是否加载过
	private static String FILE_NAME = "cmis";
	
	private static String permissionFilePath = null;
	private static String datapermissionFilePath = null;
	private static String componentConfigFileDir = null;
	private static String agentConfigFileDir = null;
	private static String daoConfigFileDir = null;
	private static String moduleserviceConfigFileDir = null;
	private static String moduleInitializerConfigFileDir = null;//各模块在系统启动时需要加载的信息配置路径
	private static String logflag = null;
	private static String pluginUninstallBackUpPath = null;//插件／模块清理SQL文件的备份目录
	private static String cmisProjectPath = null;//工程目录的绝对路径，插件安装和卸载的时候使用
	
	private static ResourceBundle res = null;
	
	/**
	 * 获CMISProperty的实例
	 * @return CMISPropertyManager实例
	 */
	public static CMISPropertyManager getInstance(){
		init();
		return instance;
	}
	
	public static void init(){
		//如果加载过则直接返回
		if(isInit) return ;
		res = ResourceBundle.getBundle(FILE_NAME);
		try {
			permissionFilePath = res.getString("permission.file.path");
			permissionFilePath = ResourceUtils.getFile(permissionFilePath).getAbsolutePath();
			datapermissionFilePath = res.getString("datapermission.file.path");
			componentConfigFileDir = res.getString("component.config.file.dir");
			agentConfigFileDir = res.getString("agent.config.file.dir");
			daoConfigFileDir = res.getString("dao.config.file.dir");
			moduleserviceConfigFileDir = res.getString("moduleservice.config.file.dir");
			moduleInitializerConfigFileDir = res.getString("module.initializer.config.file.dir");
			logflag = res.getString("logflag");
			pluginUninstallBackUpPath = res.getString("pluginUninstallBackUpPath");
			cmisProjectPath = res.getString("cmisProjectPath");
		} catch (Exception e) {
			logger.error("CMIS-PROPERTIES,加载cmis.properties出错!",e);
		}
		isInit = true;
	
	}
	
	/**
	 * 直接根据properties的属性名获取值
	 * @param propName
	 * @return
	 */
	public String getPropertyValue(String propName){
		if(res==null) 
			res = ResourceBundle.getBundle(FILE_NAME);
		
		return res.getString(propName);
	}

	/**
	 * 获取权限文件的路径
	 * @return 权限文件的路径
	 */
	public String getPermissionFilePath() {
		return permissionFilePath;
	}

	/**
	 * 获取记录级权限文件路径
	 * @return 记录级权限文件路径
	 */
	public String getDatapermissionFilePath() {
		return datapermissionFilePath;
	}

	/**
	 * 获取组件注册文件路径
	 * @return 组件注册文件路径
	 */
	public String getComponentConfigFileDir() {
		return componentConfigFileDir;
	}

	/**
	 * 获取Agent注册文件路径
	 * @return Agent注册文件路径
	 */
	public String getAgentConfigFileDir() {
		return agentConfigFileDir;
	}

	/**
	 * 获取Dao注册文件路径
	 * @return Dao注册文件路径
	 */
	public String getDaoConfigFileDir() {
		return daoConfigFileDir;
	}

	/**
	 * 获取模块服务注册文件路径
	 * @return 模块服务注册文件路径
	 */
	public String getModuleserviceConfigFileDir() {
		return moduleserviceConfigFileDir;
	}

	/**
	 * don't know
	 * @return
	 */
	public String getLogflag() {
		return logflag;
	}

	/**
	 * 插件／模块清理SQL文件的备份目录
	 * @return 插件／模块清理SQL文件的备份目录
	 */
	public String getPluginUninstallBackUpPath() {
		return pluginUninstallBackUpPath;
	}

	/**
	 * 工程目录的绝对路径，插件安装和卸载的时候使用
	 * @return 工程目录的绝对路径，插件安装和卸载的时候使用
	 */
	public String getCmisProjectPath() {
		return cmisProjectPath;
	}
	
	/**
	 * 各模块在系统启动时需要加载的信息配置路径
	 * @return
	 */
	public String getModuleInitializerConfigFileDir() {
		return moduleInitializerConfigFileDir;
	}
	
}
