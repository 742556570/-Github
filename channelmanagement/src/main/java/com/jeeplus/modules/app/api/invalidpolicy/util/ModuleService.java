package com.jeeplus.modules.app.api.invalidpolicy.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 	模块服务接口注解类
 * 	主要用于接口的描述
 * </p>
 * <p>
 * 	说明：serviceId与moduleId的区别：serviceId是接口实现类的注册ID，通过CMISModuleServiceFactory.getInstance().getModuleServiceById(serviceId)这种方式来获取
 * 	模块服务实例；moduleId是模块ID，一个模块可以有多个服务接口，即moudalId与serviceId是一对多的关系。
 * </p>
 * 
 * <p>注：需保证serviceId与moduleId不同，否则在展示模块服务接口树形图时会出错。
 * @author yuhq
 * @version 1.0
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ModuleService {
	
	/**
	 * 模块ID
	 * @return 模块ID
	 */
	String moduleId();
	
	/**
	 * 模块名称
	 * @return
	 */
	String moduleName();
	
	/**
	 * 服务ID
	 * @return 服务ID
	 */
	String serviceId();
	
	/**
	 * 类名
	 * @return 类名
	 */
	String className();
	
	/**
	 * 服务接口描述
	 * @return 
	 * @return 服务接口描述
	 */
	String serviceDesc();
	
}
