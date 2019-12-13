package com.jeeplus.modules.app.api.invalidpolicy.util;



public class CMISConstance {

	/**
	 * 失败：fail
	 */
	public static String FAIL = "fail";
	
	/**
	 * 成功：success
	 */
	public static String SUCCESS = "success";
	
	/**
	 * 是：1
	 */
	public static final String YES = "1"; 
	
	/**
	 * 否：2
	 */
	public static final String NO = "2"; 
	
	/**
	 * 是：Y
	 */
	public static final String Y = "Y"; 
	
	/**
	 * 否：N
	 */
	public static final String N = "N"; 
	
	/**
	 * 当前缺省的运行数据库服务名称
	 */
	public static String ATTR_DATASOURCE = "dataSource";
	
	/**
	 * 当前缺省的历史数据库服务名称
	 */
	public static String ATTR_DATASOURCE_HISTORY = "dataSource";
	
	/**
	 * 当前缺省的工作流的运行数据库服务名称
	 */
	public static String ATTR_WORKFLOW_DATASOURCE = "dataSource";
	
	/**
	 * 当前缺省的工作流的历史数据库服务名称
	 */
	public static String ATTR_WORKFLOW_DATASOURCE_HISTORY = "dataSource";
	
	/**
	 * 当前缺省的表模型操作的服务名称
	 */
	public static String ATTR_TABLEMODELDAO = "tableModelDAO";
	
	/**
	 * 当前缺省的表模型加载的服务名称
	 */
	public static String ATTR_TABLEMODELLOADER = "tableModelLoader";
	
	/**
	 * 当前缺省的记录级权限的服务名称
	 */
	public static String ATTR_RECORDRESTRICT = "recordRestrict";
	
	/**
	 * 当前缺省的序列号生成的服务名称
	 */
	public static String ATTR_SEQUENCESERVICE = "sequenceService";
	
	
	/**
	 * 当前请求的OP类的方法名
	 */
	public static String OP_ATTR_METHOD= "method";
	
	/**
	 * 当前缺省的主键生成的服务名称
	 */
	public static String ATTR_PRIMARYKEYSERVICE = "pkGeneratorSet";
	
	/**
	 * 当前缺省的properties文件操作的服务名称
	 */
	public static String ATTR_PROPERTIESFILESERVICE = "propertiesFileService";
	
	/**
	 * 当前缺省的参数定义的服务名称
	 */
	public static String ATTR_PARAMSERVICE = "paramService";
	
	/**
	 * 当前缺省的字典数据的服务名称
	 */
	public static String ATTR_DICSERVICE = "dataDicService";
	
	/**
	 * 当前缺省的树形字典数据的服务名称
	 */
	public static String ATTR_TREEDICSERVICE = "treeDicService";
	
	/**
	 * 当前缺省的资源操作的服务名称
	 */
	public static String ATTR_RESOURCESERVICE = "resourceService";
	
	/**
	 * 当前缺省的保存JSON返回字符串的数据域名称
	 */
	public static String ATTR_RET_JSONDATANAME = "retJSONTreeDataName";
	
	/**
	 * 数据字典表所在的集合名称
	 */
	public static String ATTR_DICTDATANAME = "dictColl";
	
	/**
	 * 工作流表单的实现类
	 */
	public static String ATTR_ECHAINFORMS = "com.ecc.echain.forms.FormIPM";

	public static String ATTR_ROLEPERMISSIONSERVICE = "rolePermissionService";
	
	public static String ATTR_PERMISSIONACCESS = "permissionAccessController";
	
	//财务报表的标签配置缓存服务的服务名称
	public static String ATTR_RPTSERVICE="rptService";
	
	//用于日志分类模块
	public static String CMIS_PERMISSION = "permission";
	
	public static String CMIS_WORKFLOW = "workflow";
	
	public static String CMIS_SHUFFLE = "shuffle";
	
	//用于设置权限文件的路径
	public static String PERMISSIONFILE_PATH;
	/**
	 * 财务报表标签对象
	 */
	//财报样式对象
	public static String CMIS_RPTSTYLE="rptstyle";
	//客户财报对象 
	public static String CMIS_FNCSTATBASE="fncstatbase";
	
	//报表状态
	public static String CMIS_STATFLG0 = "报表状态: 未存储";
	public static String CMIS_STATFLG1 = "报表状态: 已暂存";
	public static String CMIS_STATFLG2 = "报表状态: 已保存";
	
	//componetHelp List 标志
	public static String CMIS_LIST_IND="listInd";
	
	
	
	/**
	 * 信贷业务开发平台模块初始化机制
	 */
	public final static String CMIS_INITIALIZER = "CMIS_MOD_INIT";
	
	/**
	 * <p>使用档板</p>
	 * <p>描述：在调用模块服务时，动态的决定是调用档板服务还是模块实现的服务</p>
	 */
	public final static String MODULE_CONFIG_TYPE_01="1";
	
	/**
	 * <p>不使用档板</p>
	 * <p>描述：在调用模块服务时，动态的决定是调用档板服务还是模块实现的服务</p>
	 */
	public final static String MODULE_CONFIG_TYPE_02="2";
	
	
	/**
	 * <p>模块服务后缀</p>
	 * <p>描述：在使用档板时，在标准的服务ID后加上后缀，以便系统能准备定位到档板服务</p>
	 */
	public final static String BAFFLE_SUFFIX = "Baffle";

	/**
	 * <p>页面服务Action</p>
	 * <p>描述：在调用页面服务时统一通过该Action中转，以便调用档板还是实现的服务</p>
	 */
	public final static String JSP_SERVICE_ID = "getModuleJSPService.do";
	
	/**
	 * <p>页面服务Action中模块ID的参数名</p>
	 * <p>描述：在调用页面服务时统一通过该Action中转，该常量是模块ID的参数名， 如:getModuleJSPService.do?moduleId=xxxxx</p>
	 */
	public final static String JSP_SERVICE_MODULE_PARAM_NAME = "moduleId";
	
	/**
	 * <p>资源权限管理模块ID</p>
	 */
	public final static String PERMISSION_MODULE_ID = "permission";

	/**
	 * <p>组织机构管理模块ID</p>
	 */
	public final static String ORGANIZATION_MODULE_ID = "organization";
	
	/**
	 * <p>财务报表模块ID</p>
	 */
	public final static String FNS_MODULE_ID = "financialstatements";
	
	
	/**
	 * <p>系统当前登录人ID保存在会话中ID</p>
	 */
	public final static String ATTR_CURRENTUSERID = "currentUserId";
	
	/**
	 * <p>系统当前登录人名称保存在会话中的ID
	 */
	public final static String ATTR_CURRENTUSERNAME = "currentUserName";
	
	/**
	 * <p>系统当前登录机构ID保存在会话中ID</p>
	 */
	public final static String ATTR_ORGID = "organNo";
	
	/**
	 * <p>系统当前登录机构名称保存在会话中ID</p>
	 */
	public final static String ATTR_ORGNAME = "organName";
	
	/**
	 * 系统当前登录法人机构ID
	 */
	public final static String ATTR_ARTI_ORGANNO = "artiOrganNo";
	
	/**
	 * 系统当前登录人拥有岗位ID列表（多个以;分隔）
	 */
	public final static String ATTR_DUTYNO_LIST = "dutyNoList";
	
	/**
	 * 系统当前登录人拥有岗位名称列表（多个以,分隔）
	 */
	public final static String ATTR_DUTYNAME_LIST = "dutyNameList";
	
	/**
	 * 系统当前登录人拥有角色ID列表（多个以,分隔）
	 */
	public final static String ATTR_ROLENO_LIST = "roleNoList";
	
	/**
	 * 系统当前登录人拥有角色名称列表（多个以;分隔）
	 */
	public final static String ATTR_ROLENAME_LIST = "roleNameList";
	
	/**
	 * 系统当前menu
	 */
	public final static String ATTR_MENUID = "menuId";
	
	/**
	 * 当前营业日期
	 */
	public final static String OPENDAY = "OPENDAY";

	/**
	 * 上一营业日期
	 */
	public final static String LAST_OPENDAY = "LAST_OPENDAY";
	
	/**
	 * 定义金融机构代码
	 */
	public static final String INSTU_CDE="instuCde";
	
	/**
	 * 定义银行代码
	 * 光大银行	01
	 * 广发银行	02
	 * 华夏银行	03
	 * 南京银行	04
	 * 信托银行	05
	 * 马上金融	06
	 * 徽商零售（线下）	07
	 * 中油消费	08
	 */
	public static final String GDYH="01";
	
	
	/**
	 * 任务失败最大执行次数
	 */
	public static final Integer EXEC_TASK_TIME=3;
	
	/**
	 * 缺省金融机构代码
	 */
	public static final String INSTU_CDE_DEFAULT_VALUE="900000000";
	
	/**
	 * 是否主管用户
	 */
	public static final String MANAG_IND="managInd";
	
	/**
	 * 是否外部机构用户
	 */
	public static final String EXT_IND="extInd";
	
	/**
	 * 是否外部机构用户：行外用户
	 */
	public static final String EXT_IND_Y = "Y";
	
	/**
	 * 是否外部机构用户：行内用户
	 */
	public static final String EXT_IND_N = "N";
	
	/**
	 *  特殊角色
	 */
	public static final String SPECIAL_ROLE="special_role";
	
	/**
	 * HttpServletResponse 对象
	 */
	public static final String SERVLET_RESPONSE = "_ServletResponse";
	
	/**
	 * wfReturnUrl 流程审批结束后跳转页面的请求链接。
	 */
	public static final String WFRETURNURL ="wfReturnUrl";

	/**
	 * 异步放款结果通知状态（不可重新发起放款申请）
	 */
	public static final String[] APPROVE_RESULT_FAIl = {"9999","9011","1121","1138","1200","1122","1202","1203"};

	/**
	 * 异步放款结果通知状态（可重新发起放款申请）
	 */
	public static final String[] APPROVE_RESULT_AGAIN = {"9010"};

	/**
	 * 异步放款结果通知失败原因 缺少参数
	 */
	public static final String APPROVE_FAIl_PARAMTER_ERROR = "1101";

	/**
	 * 异步放款结果通知失败原因 内部处理失败
	 */
	public static final String APPROVE_FAIl_SYSTER_ERROR = "1102";

	/**
	 * 异步放款结果通知成功
	 */
	public static final String APPROVE_SUCCESS_SYSTERM = "0000";
}
