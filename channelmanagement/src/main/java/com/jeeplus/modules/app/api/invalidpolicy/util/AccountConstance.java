package com.jeeplus.modules.app.api.invalidpolicy.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @描述：<p>核算接口常量声明</p>
 * @author: yucheng
 * @date:2014-11-10
 * @version:1.0
 */

public class AccountConstance {
	
	public final static String servP0000100011  = "servP0000100011";
	public final static String servP0000100012  = "servP0000100012";
	public final static String serv10000000122  = "serv10000000122";
	public final static String serv10000000023  = "serv10000000023";
	public final static String serv10000000024  = "serv10000000024";
	public final static String serv10000000029  = "serv10000000029";
	
	public final static String serv10000100027  = "serv10000100027";
	public final static String serv10000000030  = "serv10000000030";
	public final static String serv10000000031  = "serv10000000031";
	public final static String serv10000000035  = "serv10000000035";
	public final static String serv10000100040  = "serv10000100040";
	
	public final static String serv10000100041  = "serv10000100041";
	public final static String serv10000100042  = "serv10000100042";
	public final static String serv10000100043  = "serv10000100043";
	public final static String serv10000100032  = "serv10000100032";
	public final static String serv10000100033  = "serv10000100033";
	
	public final static String serv10000100039  = "serv10000100039";
	public final static String serv10000100044  = "serv10000100044";
	public final static String serv10000100028  = "serv10000100028";
	public final static String serv10000100029  = "serv10000100029";
	public final static String serv10000100030  = "serv10000100030";
	
	public final static String servP0000100002  = "servP0000100002";
	public final static String servP0000100004  = "servP0000100004";
	public final static String servP0000100005  = "servP0000100005";
	public final static String servP0000100006  = "servP0000100006";
	public final static String servP0000100001  = "servP0000100001";
	
	public final static String servP0000100003  = "servP0000100003";
	public final static String serv10000000099  = "serv10000000099";
	public final static String serv10000000045  = "serv10000000045";
	public final static String serv10000000046  = "serv10000000046";
	public final static String servP0000100007  = "servP0000100007";
	
	public final static String serv10000000040  = "serv10000000040";
	public final static String serv10000100049  = "serv10000100049";
	public final static String serv10000000053  = "serv10000000053";
	public final static String serv10000000153  = "serv10000000153";
	public final static String serv10000000154  = "serv10000000154";
	
	public final static String servP0000100008  = "servP0000100008";
	public final static String serv10000c000041  = "serv10000000041";
	public final static String serv10000100050  = "serv10000100050";
	public final static String serv10000100052  = "serv10000100052";
	public final static String serv10000100053  = "serv10000100053";
	
	public final static String serv10000100054  = "serv10000100054";
	public final static String serv10000000038  = "serv10000000038";
	public final static String serv10000000056  = "serv10000000056";
	public final static String serv10000000057  = "serv10000000057";
	public final static String serv10000100056  = "serv10000100056";
	
	public final static String servP0000100009  = "servP0000100009";
//	public final static String servP0000100010  = "servP0000100010";
//	public final static String serv10000100036  = "serv10000100036";
//	public final static String serv10000000058  = "serv10000000058";
//	public final static String serv10000000060  = "serv10000000060";
	
//	public final static String serv10000000061  = "serv10000000061";
//	public final static String serv10000000062  = "serv10000000062";
//	public final static String serv10000000063  = "serv10000000063";
//	public final static String serv10000000068  = "serv10000000068";
//	public final static String serv10000000070  = "serv10000000070";
	
	public final static String serv10000100045  = "serv10000100045";
	public final static String serv10000100046  = "serv10000100046";
//	public final static String agent000001  = "agent000001";
//	public final static String agent000002  = "agent000002";
	public final static String serv10000200517  = "serv10000200517";
	
//	public final static String serv10000100048  = "serv10000100048";
//	public final static String serv10000000032  = "serv10000000032";
//	public final static String serv10000200518  = "serv10000200518";
//	public final static String serv10000200519  = "serv10000200519";
//	public final static String serv10000200520  = "serv10000200520";
	
	
	//接口描述
	public  static Map<String,String> interfaces = new HashMap<String,String>();
	static{
		interfaces.put("servP0000100011","合作银行批量同步接口"); //BanksSynchBatchRequestMessage
		interfaces.put("servP0000100012","产品同步接口"); //PrdSynchBatchRequestMessage
		interfaces.put("serv10000000122","贷款发放接口"); //LendingRequestMessage
		interfaces.put("serv10000000023","贷款展期接口"); //LoanExtensionRequestMessage
		interfaces.put("serv10000000024","贷款核销接口"); //VerificationCancelRequestMessage
		interfaces.put("serv10000000029","还款方式查询接口"); //QueryRepayMethodRequestMessage
		
		interfaces.put("serv10000100027","还款试算接口"); //LoanTryCacuRequestMessage
		interfaces.put("serv10000000030","记帐查询接口"); //QueryKeepAccountsRequestMessage
		interfaces.put("serv10000000031","撤消接口"); //CorrectionRequestMessage
		interfaces.put("serv10000000035","单项减值确认接口"); //IndividualImpairmentRequestMessage
		interfaces.put("serv10000100040","主动还款试算接口"); //ActiveRepaymentTrialRequestMessage
		
		interfaces.put("serv10000100041","主动还款接口"); //ActiveRepaymentRequestMessage
		interfaces.put("serv10000100042","贷款停息接口"); //LoanPauseRequestMessage
		interfaces.put("serv10000100043","欠款查询接口"); //QueryArrearsRequestMessage
		interfaces.put("serv10000100032","单项减值确认和计提接口"); //IndivImpairProvisionRequestMessage
		interfaces.put("serv10000100033","组合减值计提接口"); //CombinedImpairmentRequestMessage
		
		interfaces.put("serv10000100039","日终步骤处理接口"); //TriggerAcctTaskRequestMessage
		interfaces.put("serv10000100044","日终状态查询接口"); //QueryAcctStsTaskRequestMessage
		interfaces.put("serv10000100028","退货接口"); //ReturnGoodsRequestMessage
		interfaces.put("serv10000100029","还款计划调整接口"); //MtdDtlChgRequestMessage
		interfaces.put("serv10000100030","还款账户变更接口"); //ChgAcctNoRequestMessage
		
		interfaces.put("servP0000100002","费用类型参数修改接口"); //FeeTypChgRequestMessage
		interfaces.put("servP0000100004","还款顺序参数修改接口"); //PRpymOrdChgRequestMessage
		interfaces.put("servP0000100005","会计分录参数修改接口"); //PLoanTypGlHdrChgRequestMessage
		interfaces.put("servP0000100006","会计科目参数修改接口"); //SGlAcctNoChgRequestMessage
		interfaces.put("servP0000100001","核算规则参数修改接口"); //PLoanTypGlChgRequestMessage
		
		interfaces.put("servP0000100003","还款方式参数修改接口"); //MtdTypChgRequestMessage
		interfaces.put("serv10000000099","授权交易处理接口"); //AuthorizationRequestMessage
		interfaces.put("serv10000000045","贷款发放授权查询接口"); //LoanAuthorizationRequestMessage
		interfaces.put("serv10000000046","主动还款授权查询接口"); //RepayAuthorizationRequestMessage
		interfaces.put("servP0000100007","利率表配置接口"); //SIntRatConfigRequestMessage
		
		interfaces.put("serv10000000040","贷款平移接口"); //LoanTranslationRequestMessage
		interfaces.put("serv10000100049","可撤销交易查询接口"); //QueryRevokeRequestMessage
		interfaces.put("serv10000000053","贷款承诺授权接口"); //PromiseAuthorizationRequestMessage
		interfaces.put("serv10000000153","贷款承诺签发授权查询接口"); //QueryPromiseRequestMessage
		interfaces.put("serv10000000154","还款计划调整试算接口"); //ChgRepaymentTrialRequestMessage
		
		interfaces.put("servP0000100008","系统参数加载接口"); //SystemParamLoadRequestMessage
		interfaces.put("serv10000000041","收费交易接口"); //ChargeDealRequestMessage
		interfaces.put("serv10000100050","账务交易流水查询接口"); //TransactionFlowRequestMessage
		interfaces.put("serv10000100052","客户还款记录查询接口"); //PaymentHistoryRequestMessage
		interfaces.put("serv10000100053","贷款信息查询接口"); //LoanInfoRequestMessage
		
		interfaces.put("serv10000100054","费用记录查询接口"); //FeeHistoryRequestMessage
		interfaces.put("serv10000000038","利息减免接口"); //ReturnRatecutsRequestMessage
		interfaces.put("serv10000000056","贷款产品变更接口"); //ProductChangesRequestMessage
		interfaces.put("serv10000000057","资产证券化卖出/赎回接口"); //LmRpTransRequestMessage
		interfaces.put("serv10000100056","还款计划查询接口"); //QueryLmPmShdRequestMessage
		
		interfaces.put("servP0000100009","分支机构信息同步接口"); //SBchSynchBatchRequestMessage
		interfaces.put("servP0000100010","金融机构信息同步接口");
		interfaces.put("serv10000100036","资产卖出/收回接口");
		interfaces.put("serv10000000058","贷款承诺/信贷证明结清接口");
		interfaces.put("serv10000000060","贷款信息变更接口");
		
		interfaces.put("serv10000000061","放款本金调整接口");
		interfaces.put("serv10000000062","手工形态转移接口");
		interfaces.put("serv10000000063","预约利率调整接口");
		interfaces.put("serv10000000068","保证金变更接口");
		interfaces.put("serv10000000070","未生效利率调整撤销接口");
		
		interfaces.put("serv10000100045","手续费用减免交易接口");
		interfaces.put("serv10000100046","贷款结息调整接口");
		interfaces.put("agent000001","贷款结息调整接口");
		interfaces.put("agent000002","查询日志步骤接口");
		interfaces.put("serv10000200517","联机账务交易检查接口");
		
		interfaces.put("serv10000100048","支付交易接口");
		interfaces.put("serv10000000032","撤销支付交易接口");
		interfaces.put("serv10000200518","查询核算的日终配置接口");
		interfaces.put("serv10000200519","查询核算的分户账信息接口");
		interfaces.put("serv10000200520","催收理赔后结清接口");
		interfaces.put("37","发送总线无效保单接口");
		
		interfaces.put("YG0003","个人小额信用贷款申请接口"); //SendGuarantyYG0003Op
		interfaces.put("YG0005","取消贷款申请请求接口"); //CancelLoanYG0005Op
		interfaces.put("YG0007","提前还款接口"); //PrePaymentYG0007Op
		interfaces.put("YG0008","银行卡修改接口"); //BankCardYG0008Op
		interfaces.put("YG0010","交易状态查询接口"); //LoanStatutYG0010Op
		interfaces.put("YG0009","实时扣款接口"); //DeductionsYG0009Op
	}
	
	
	
	
	
	
	/**
	 * 操作标示:新增
	 */
	public final static String OPT_TYP_INSERT  = "INSERT";
	/**
	 * 操作标示:更新
	 */
	public final static String OPT_TYP_UPDATE  = "UPDATE";
	/**
	 * 操作标示:删除
	 */
	public final static String OPT_TYP_DELETE  = "DELETE";
	
	/**
	 * 核算接口代码描述:调用成功返回
	 */
	public final static String LOAN_ERROR_CODE_00000 = "00000";
	
	/**
	 * 核算接口代码描述:调用没有注册的服务
	 */
	public final static String LOAN_ERROR_CODE_10001 = "10001";
	
	/**
	 * 核算接口代码描述:调用服务所对应的渠道不正确
	 */
	public final static String LOAN_ERROR_CODE_10002 = "10002";
	
	/**
	 * 核算接口代码描述:调用服务所对应的其它
	 */
	public final static String LOAN_ERROR_CODE_90000 = "90000";
	
	/**
	 * 核算接口代码描述:调用服务所对应的渠道报文保存异常
	 */
	public final static String LOAN_ERROR_CODE_20001 = "20001";
	
	/**
	 * 会计核算规则
	 */
	public final static String LOAN_GL_RULE = "GL_RULE";
	/**
	 * 会计科目设置
	 */
	public final static String LOAN_GL_ACCT = "GL_ACCT";
	/**
	 * 会计分录设置
	 */
	public final static String LOAN_GL_SETUP = "GL_SETUP";
	/**
	 * 核算产品规则映射
	 */
	public final static String LOAN_GL_MAP = "GL_MAP";
	/**
	 * 还款顺序设置
	 */
	public final static String LOAN_PAYM_ORD = "PAYM_ORD";
	
	/**
	 * 证件类型与清算系统转换
	 */
	public  static Map<String,String> idTypTrans = new HashMap<String,String>();
	static{
		idTypTrans.put("00","20");//员工代码
		idTypTrans.put("20","01");//身份证
		idTypTrans.put("23","03");//军官证
		idTypTrans.put("25","06");//港澳居民通行证
		idTypTrans.put("26","04");//台胞证
		idTypTrans.put("2X","20");//其他证件
		idTypTrans.put("30","20");//组织机构代码证
		
	}
	
	/**
	 * 中间业务平台接口 方法名与交易码 转换
	 */
	public  static Map<String,String> BIPTrans = new HashMap<String,String>();
	static{
		BIPTrans.put("sendGuaranty","YG0003");
		BIPTrans.put("cancelLoan","YG0005");
		BIPTrans.put("prePayment","YG0007");
		BIPTrans.put("bankCard","YG0008");
		BIPTrans.put("loanStatut","YG0010");
		BIPTrans.put("deductions","YG0009");
	}
	
}
