package com.jeeplus.modules.app.api.invalidpolicy;

/**
 * 无效保单默认参数
 * 
 * @author wangfz
 * @date 2018-2-24
 */
public class DefaultInvaildPolicyParam {

	public  final static String modeFlag ="5"; // 模板类型  默认5，与综合、分期一致。
	public final static String operateType ="INPUT"; //操作类型 默认“INPUT”，与综合、分期一致。
	public final static String policyCount ="1"; // 保单数  默认1，与综合、分期一致。
	public final static String sendName ="xdxbpt";// 发送保单用户  默认xdxbpt，与综合、分期一致。
	public final static String sendPwd ="xdxbpt";// 发送保单用户密码  默认xdxbpt，与综合、分期一致。
	public final static String sysFlag ="xdxbpt";// 系统标识  默认xdxbpt，与综合、分期一致。
	public final static String insuredPhoneNumber ="";// 保户联系电话 默认空，与综合保持一致
	public final static String endDate =" 2099-01-01";// 保单终止日期  默认 2099-01-01，与综合保持一致
	public final static String appliFlag = "2";// 投保人类型 固定为2 
	public final static String handler1Code= "1";// 销售人员代码1
	public final static String insureagrt = "";// 投保人农业性质 默认空，与综合保持一致。
	public final static String loanStartDate = "2013-06-01";// 起始居住日期（起） 默认2013-06-01，与综合保持一致。
	public final static String argueSolution = "1";//合同争议解决方案 1.诉讼2 仲裁（默认1，与综合保持一致）
	public final static String structure = "";// 房屋结构  默认为空，与综合保持一致。
	public final static String appliNature = "3";// 投保人类型 默认为3，与综合保持一致。
	public final static String loanNature = "2" ;// 贷款性质 默认2，与综合保持一致。
	public final static String insuredAddress = "";//银行网点地址 可以为空。如银行类的，需送银行网点；非银行类（如信托），送空。
	public final static String insuredNature = "4";// 保户性质 固定传4法人（默认），与综合保持一致
	public final static String agentCode = "";// 代理人代码  与34一致。
	public final static String isDisability = "";// 家人是否知晓此贷款 固定传1（默认），这个需与产险沟通，看是否能送空。
	public final static String perRepaidAmount ="";// 月总收入 需与产险沟通，看是否能送空。
	public final static String appliIdentifyType = "01";// 证件类型  证件类型身份证为01(默认)，与综合保持一致。
	public final static String houseVariety = "";// 房产类别  需与产险沟通，看是否能送空。
	public final static String addressCode = "";// 住址邮编 送空，与综合保持一致。
	public final static String insuredFlag = "1";// 被保人标志   固定传1(默认)，与综合保持一致。
	public final static String insuredType = "2";// 被保人类型  固定传2 团体（默认），与综合保持一致。
	public final static String mortgageSate = "";// 单位性质 需与产险沟通，看是否能送空。
	public final static String startDate = "2099-01-01";// 起保日期 送默认值2099-01-01，与综合保持一致。
	public final static String repaidType="";// 私营企业类型  送空，与综合保持一致。
	public final static String deliverDate = "";// 交付日期  先不传(默认)，与综合保持一致。
	public final static String appliType = "1";// 1.个人客户 2团体 这里默认为1（默认），与综合保持一致 
	public final static String education = "";//最高学历 
	public final static String appliPostCode ="";// 投保人邮编 默认空，与综合保持一致
	public final static String kindCode = "002";// 条款代码  综合默认“002”。需产品确认是否对此有额外条款。
	public final static String kindName = "个人贷款保证保险条款（2016版）";// 条款名称  综合默认“个人贷款保证保险条款（2016版）”。需产品确认是否对此有额外条款。
	public final static String repaidStartDate ="2099-01-01";// 起始服务日期 默认“2099-01-01”，与综合保持一致。
	public final static String appliLocalYear = "2000";// 申请人来申请地年份  默认“2000”，与综合保持一致。
	public final static String insuredMobile = "";// 电话  默认空，与综合保持一致
	public final static String marriage = "";// 婚姻状况 
	public final static String applyNum = "";// 申请人供养人数  默认为空，与综合保持一致
	public final static String limitedFee = "";// 默认为空，与综合保持一致。 
	public final static String insuredPostCode = "100100";// 银行网点邮编  值“100100”，与综合保持一致。
	public final static String addressNo = "1";// 默认为1（默认），与综合保持一致。 
	public final static String insuredIdentifyType = "08";// 被保人证件类型 默认08，与综合保持一致。
	public final static String perRepaidDate = "10";// 月支薪日 默认为“10”，与综合保持一致。
	
}
