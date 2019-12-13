package com.jeeplus.modules.app.api.account.CreditLoanApply.request;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 小额信用贷款申请请求参数vo
 * 
 * @author wangfz
 * @date 2018-2-24
 */
@XmlRootElement
public class RECORD  {

    //body
	private String GUARANTYID;// 唯一键值
	private String APPLYADD;// 申请地点 文本
	private String CERTTYPE;// 证件类型 CertType1 身份证2 户口簿3 护照4 军官证6 返乡证7 异常身份证99 其他5 驾驶执照
	private String CERTID;// 证件号 校验规则：1、身份证需校验合法性；2、港澳居民证符合长度为11位数字，且第一位为H或M
	private String CUSTOMERNAME;// 姓名 与证件原件上名称保持一致
	private String SEX;// 性别 0 女 1 男
	private String BIRTHDAY;// 生日 YYYY-MM-DD
	private String TELNO;// 联系电话
							// 格式：区号-号码-分机,区号-号码-分机校验规则：1、区号：3-4位数字组成，其数据应被包含在国内地区号码表中2、本地号码：6-8位数字组成；3、分机：0-6位数字组成；4、使用“-”做为国家码、区号、电话号码以及分机之间的分隔符
							// 分期为非必传
	private String MOBILE;// 移动电话 校验规则：须为11位数字
	private String ZIPCODE;// 邮政编码 校验规则：须为6位数字
	private String POSTADD;// 通讯地址 字符和数字，长度不能小于16个字节
	private String APPLYPURPOSE;// 申请用途 文本，不少于2个汉字
	private String APPLYSUM;// 申请金额
	private String APPLYCURRENCY;// 申请币种 控制：仅允许人民币 Currency01 人民币
	private String APPLYTERMMONTH;// 申请期限 单位：月
	private String PAYMENTTYPE;// 还款帐户类型 PayAcctType1阳光卡2活期存折
	private String PAYMENTACCTNO;// 还款帐户 校验规则：1、阳光卡：16位2、活期存折：17位
	private String PAYMETHOD;// 还款方式 等本等息需新增 PayMethod1 等额还款法2 等额本金还款4先还息后本息法5 分次付息一次还本6 -等本等息
	private String SUBLOANTERM;// 子还款方式 PayMethod1 等额还款法2 等额本金还款
	private String SUBPAYMETHOD;// 子期限
	private String DEBITTYPE;// 扣款日类别 PayDayType1对日
	private String MONREPAYDAY;// 扣款日期 1-31
	private String MARRIAGE;// 婚姻状况 Marriage1：未婚2：已婚有子女3：已婚无子女9：其他
	private String EDUEXPERIENCE;// 学历 EducationExperience10 博士 20 硕士 30 大专 40 大学本科 60 高中及中专 70 初中及以下 分期为非必传
	private String HUKOU;// 户籍 详见附件
	private String INCOMEFLAG;// 个人收入标识 IncomeFlag1 工薪人士2 自雇人士3 无收入来源
	// 分期为非必传
	private String MONTHLYWAGES;// 个人月收入 单位：元 分期为非必传
	private String FAMILYSTATUS;// 居住状况 LivingStatus1 商业按揭房2 无按揭购房3 公积金按揭购房4 自建房5 租用6 暂住8 单位住房7 亲属住房
	private String CITYLIVEDSTARTYEAR;// 居住年限
	private String FAMILYADD;// 家庭住址 校验规则：须为字符和数字，长度不能小于16个字节
	private String FAMILYZIP;// 家庭邮编 校验规则：须为6位数字
	private String FAMILYTEL;// 住宅电话
								// 格式：区号-号码-分机,区号-号码-分机校验规则：1、区号：3-4位数字组成，其数据应被包含在国内地区号码表中；2、本地号码：6-8位数字组成；3、分机：0-6位数字组成；4、使用“-”做为国家码、区号、电话号码以及分机之间的分隔符
								// 分期不校验
	private String UNITTYPE;// 单位性质 代码表CompanyProperty1 机关事业单位 2 国有股份 3 外资企业 4 合资企业 8 社会团体 7 个体 6 私营企业 5
							// 民营企业
	private String UNITKIND;// 单位所属行业 IndustryType<参照国标一级行业大类>A 农、林、牧、渔业 B 采矿业 C 制造业 D 电力、热力、燃气及水生产和供应业 E
							// 建筑业 F 批发和零售业 G 交通运输、仓储和邮政业 H 住宿和餐饮业 I 信息传输、软件和信息技术服务业 J 金融业 K 房地产业 L 租赁和商务服务业
							// M 科学研究和技术服务业 N 水利、环境和公共设施管理业 O 居民服务、修理和其他服务业 P 教育 Q 卫生和社会工作 R 文化、体育和娱乐业 S
							// 公共管理、社会保障和社会组织 T 国际组织 z 未知
	private String EMPLOYMENT;// 工作单位 校验规则：须长度大于4个汉字
	private String STAFF;// 职务 HeadShip0 高管人员 1 中级管理人员 2 基层管理人员 3 一般员工 4 销售/中介/业务代?5 营业员/服务员
	private String COMPANYADD;// 单位地址 校验规则：须为字符和数字，长度不能小于16个字节
	private String COMPANYZIP;// 公司邮编 校验规则：须为6位数字
	private String COMPANYTEL;// 单位电话
								// 格式：区号-号码-分机,区号-号码-分机校验规则：1、区号：3-4位数字组成，其数据应被包含在国内地区号码表中；2、本地号码：6-8位数字组成；3、分机：0-6位数字组成；4、使用“-”做为国家码、区号、电话号码以及分机之间的分隔符
	private String INSURERCODE;// 保险公司代码 13 阳光保险公司
	private String INSURERNAME;// 保险公司名称 如：平安保险公司
	private String INSURERNAMEDETAIL;// 保险公司详细名称 VA(60) 如：中国平安保险（集团）股份有限公司
	private String POLICYHOLDER;// 投保人
	private String BENEFICIARY;// 受益人
	private String INSUREDTOTALAMT;// 保险金额
	private String GUARANTYCURRENCY;// 保险币种 Currency01 人民币
	private String INSUREBEGINDATE;// 保险开始日期 YYYYMMDD
	private String INSUREENDDATE;// 保险到期日期 YYYYMMDD
	private String PAYBEGINDATE;// 保险交费日期 YYYYMMDD
	private String CONFIRMVALUE;// 保费
	private String VOUCHVALUE;// 保险金额（弃用） 预留字段，可为空
	private String OPERATEORGID;// 经办机构 ) 光大银行网点号
	private String INSURANCECONTACTS;// 保险公司联系人
	private String INSURANCETELEPHONE;// 保险公司联系电话
	private String IMAGEID;// 影像ID 光大银行影像系统生成
	private String PAYKIND;// 缴费方式 PAPayKind10期缴20趸缴30-趸缴+期缴（房贷新增）
	private String ACCTTYPE;// 贷款类型 0：非追加贷款1：追加贷款
	private String BORROWERTYPE;// 借款人类型 当“PurposeType”为“1经营”时：必输 1、法定代表人2、自然人股东3、其他4、个体工商户5、合伙企业合伙人6、个人独资企业出资人
	private String ENTERPRISENAME;// 用款企业名称 当“PurposeType”为“1经营”时:必输
	private String RECEIPTSPROVE;// 用款企业收入来源证明材料 1-日记账簿2-银行流水 3-其他
	private String ENTERPRISEINCOMEMON;// 用款企业月度收入额
	private String ACCOUNTINCOMEYEAR;// 近一年内经营账户收入之和
	private String SHAREPCT;// 借款人控股比例 单位：%
	private String LICENSENO;// 用款企业营业执照
	private String DEPARTID;// 用款企业组织机构代码
	private String INDUSTRYTYPE;// 用款企业所属行业 当“PurposeType”为“1”时:必输 IndustryType<参照国标行业大类>A 农、林、牧、渔业 B 采矿业 C 制造业
								// D 电力、热力、燃气及水生产和供应业 E 建筑业 F 批发和零售业 G 交通运输、仓储和邮政业 H 住宿和餐饮业 I 信息传输、软件和信息技术服务业 J
								// 金融业 K 房地产业 L 租赁和商务服务业 M 科学研究和技术服务业 N 水利、环境和公共设施管理业 O 居民服务、修理和其他服务业 P 教育 Q
								// 卫生和社会工作 R 文化、体育和娱乐业 S 公共管理、社会保障和社会组织 T 国际组织 z 未知
	private String PURPOSETYPE;// 贷款用途标识 1、经营2、消费
	private String INSURERATE;// 保费率 交易银行为信托时传 期缴保费费率
	private String  PREINSURATE;// 趸缴保费费率 交易银行为信托时传 趸缴保费费率 信托专用字段
	private String PREMIUMPAYTYPE;// 保费缴费方式 01、 期缴02、 趸交
	private String PRODUCTTYPE;// 保险产品类型 01、 信用贷02、 车抵贷
	private String APPLYNO;// 申请号
	private String CONTRACTNO;// 合同号
	private String PRODID;// 产品编号 1001:私营业主贷1002:阳光内部员工贷1003:受薪贷1004:物业贷
	// 中间业务平台需要增加小贷和外部银行系统编码的映射
	private String PRODNAME;// 产品名称
	private String STORIED;// 门店编号
	private String STORIEDNAME;// 门店名称
	private String HOMEADDRPROV;// 住宅地址（省）代码
	private String HOMEADDRCITY;// 住宅地址（市）代码
	private String HOMEADDRDISTRICT;// 住宅地址（区）代码
	private String EMPLOYEDTYPE;// 受雇类型 1. 受薪2. 自雇
	private String WORKYEAR;// 工作年限(年)
	private String WORKMONTH;// 工作年限(月)
	private String RELAPHONE1;// 第1联系人手机号（家人）
	private String RELATION1;// 第1联系人关系 1:父母5:子女8:兄弟姐妹99:其他
	private String EMAIL;// 邮箱
	private String COUNTRY;// 国籍
	private String DUEDATE;// 证件有效期 YYYY/MM/DD 空
	private String EDUEXPERIANCE;// 最高学历
	private String EDUDEGREE;// 最高学位
	private String POSITION;// 职称 )
	private String FAMILYCITY;// 家庭所在行政区划代码 见附件：所在行政区划代码
	private String COMPANYADDHEAD;// 单位所在行政区划代码 见附件：所在行政区划代码
	private String  BANKCODE;// 银行代码 信托银行专用字段(其他银行不用添加)
	private String QUDLAY;// 渠道来源
	private String  BANKNAME;// 银行名称 精确到支行例如：中国建设银行北京建国支行渠道来源为中信信托必传，中信信托房抵贷产品必须（支付通道金联需要）
	private String  BANKPROVINCE;// 开户行-省 例如：北京市 渠道来源为中信信托必传，中信信托房抵贷产品必须（支付通道金联需要）
	private String  BANKCITY;// 开户行-市 例如：北京市 渠道来源为中信信托必传，中信信托房抵贷产品必须（支付通道金联需要）
	private String  CONSULTRATE;// 咨询费费率 按月分期收取 房贷二期新增
	private String SUBAPPMANNO;// 共有人 房贷新增
	private String APPLYMANNO;// 共借人 房贷新增
	private String BUSIMODE;// 业务模式 01-信保模式02-普惠模式 新增：系统默认01-信保模式
	private String MONCOMFEERATE;// 月综合服务费比例 普惠：审批月综合费率 单位：%
	private String CONSERVFEERATE;// 首期服务费费率 普惠：首期服务费比例 单位：%
	private String LOANTYPE;// 默认：0-非追加贷款
	private String CONMANAFEERATE;// 期缴服务费费率 普惠：期缴服务费比例 单位：%
	private String XTERM;// X期限 还款方式为5-组合还款（教育分期X+Y（必传），教育分期专用 5-组合还款（教育分期X+Y（必传,X+Y=总期数），教育分期专用
	private String YTERM;// Y期限 还款方式为5-组合还款（教育分期X+Y（必传），教育分期专用 5-组合还款（教育分期X+Y（必传,X+Y=总期数），教育分期专用

	
	public String getLOANTYPE() {
		return LOANTYPE;
	}

	public void setLOANTYPE(String lOANTYPE) {
		LOANTYPE = lOANTYPE;
	}

	public String getGUARANTYID() {
		return GUARANTYID;
	}

	public void setGUARANTYID(String gUARANTYID) {
		GUARANTYID = gUARANTYID;
	}

	public String getAPPLYADD() {
		return APPLYADD;
	}

	public void setAPPLYADD(String aPPLYADD) {
		APPLYADD = aPPLYADD;
	}

	public String getCERTTYPE() {
		return CERTTYPE;
	}

	public void setCERTTYPE(String cERTTYPE) {
		CERTTYPE = cERTTYPE;
	}

	public String getCERTID() {
		return CERTID;
	}

	public void setCERTID(String cERTID) {
		CERTID = cERTID;
	}

	public String getCUSTOMERNAME() {
		return CUSTOMERNAME;
	}

	public void setCUSTOMERNAME(String cUSTOMERNAME) {
		CUSTOMERNAME = cUSTOMERNAME;
	}

	public String getSEX() {
		return SEX;
	}

	public void setSEX(String sEX) {
		SEX = sEX;
	}

	public String getBIRTHDAY() {
		return BIRTHDAY;
	}

	public void setBIRTHDAY(String bIRTHDAY) {
		BIRTHDAY = bIRTHDAY;
	}

	public String getTELNO() {
		return TELNO;
	}

	public void setTELNO(String tELNO) {
		TELNO = tELNO;
	}

	public String getMOBILE() {
		return MOBILE;
	}

	public void setMOBILE(String mOBILE) {
		MOBILE = mOBILE;
	}

	public String getZIPCODE() {
		return ZIPCODE;
	}

	public void setZIPCODE(String zIPCODE) {
		ZIPCODE = zIPCODE;
	}

	public String getPOSTADD() {
		return POSTADD;
	}

	public void setPOSTADD(String pOSTADD) {
		POSTADD = pOSTADD;
	}

	public String getAPPLYPURPOSE() {
		return APPLYPURPOSE;
	}

	public void setAPPLYPURPOSE(String aPPLYPURPOSE) {
		APPLYPURPOSE = aPPLYPURPOSE;
	}

	public String getAPPLYSUM() {
		return APPLYSUM;
	}

	public void setAPPLYSUM(String aPPLYSUM) {
		APPLYSUM = aPPLYSUM;
	}

	public String getAPPLYCURRENCY() {
		return APPLYCURRENCY;
	}

	public void setAPPLYCURRENCY(String aPPLYCURRENCY) {
		APPLYCURRENCY = aPPLYCURRENCY;
	}

	public String getAPPLYTERMMONTH() {
		return APPLYTERMMONTH;
	}

	public void setAPPLYTERMMONTH(String aPPLYTERMMONTH) {
		APPLYTERMMONTH = aPPLYTERMMONTH;
	}

	public String getPAYMENTTYPE() {
		return PAYMENTTYPE;
	}

	public void setPAYMENTTYPE(String pAYMENTTYPE) {
		PAYMENTTYPE = pAYMENTTYPE;
	}

	public String getPAYMENTACCTNO() {
		return PAYMENTACCTNO;
	}

	public void setPAYMENTACCTNO(String pAYMENTACCTNO) {
		PAYMENTACCTNO = pAYMENTACCTNO;
	}

	public String getPAYMETHOD() {
		return PAYMETHOD;
	}

	public void setPAYMETHOD(String pAYMETHOD) {
		PAYMETHOD = pAYMETHOD;
	}

	public String getSUBLOANTERM() {
		return SUBLOANTERM;
	}

	public void setSUBLOANTERM(String sUBLOANTERM) {
		SUBLOANTERM = sUBLOANTERM;
	}

	public String getSUBPAYMETHOD() {
		return SUBPAYMETHOD;
	}

	public void setSUBPAYMETHOD(String sUBPAYMETHOD) {
		SUBPAYMETHOD = sUBPAYMETHOD;
	}

	public String getDEBITTYPE() {
		return DEBITTYPE;
	}

	public void setDEBITTYPE(String dEBITTYPE) {
		DEBITTYPE = dEBITTYPE;
	}

	public String getMONREPAYDAY() {
		return MONREPAYDAY;
	}

	public void setMONREPAYDAY(String mONREPAYDAY) {
		MONREPAYDAY = mONREPAYDAY;
	}

	public String getMARRIAGE() {
		return MARRIAGE;
	}

	public void setMARRIAGE(String mARRIAGE) {
		MARRIAGE = mARRIAGE;
	}

	public String getEDUEXPERIENCE() {
		return EDUEXPERIENCE;
	}

	public void setEDUEXPERIENCE(String eDUEXPERIENCE) {
		EDUEXPERIENCE = eDUEXPERIENCE;
	}

	public String getHUKOU() {
		return HUKOU;
	}

	public void setHUKOU(String hUKOU) {
		HUKOU = hUKOU;
	}

	public String getINCOMEFLAG() {
		return INCOMEFLAG;
	}

	public void setINCOMEFLAG(String iNCOMEFLAG) {
		INCOMEFLAG = iNCOMEFLAG;
	}

	public String getMONTHLYWAGES() {
		return MONTHLYWAGES;
	}

	public void setMONTHLYWAGES(String mONTHLYWAGES) {
		MONTHLYWAGES = mONTHLYWAGES;
	}

	public String getFAMILYSTATUS() {
		return FAMILYSTATUS;
	}

	public void setFAMILYSTATUS(String fAMILYSTATUS) {
		FAMILYSTATUS = fAMILYSTATUS;
	}

	public String getCITYLIVEDSTARTYEAR() {
		return CITYLIVEDSTARTYEAR;
	}

	public void setCITYLIVEDSTARTYEAR(String cITYLIVEDSTARTYEAR) {
		CITYLIVEDSTARTYEAR = cITYLIVEDSTARTYEAR;
	}

	public String getFAMILYADD() {
		return FAMILYADD;
	}

	public void setFAMILYADD(String fAMILYADD) {
		FAMILYADD = fAMILYADD;
	}

	public String getFAMILYZIP() {
		return FAMILYZIP;
	}

	public void setFAMILYZIP(String fAMILYZIP) {
		FAMILYZIP = fAMILYZIP;
	}

	public String getFAMILYTEL() {
		return FAMILYTEL;
	}

	public void setFAMILYTEL(String fAMILYTEL) {
		FAMILYTEL = fAMILYTEL;
	}

	public String getUNITTYPE() {
		return UNITTYPE;
	}

	public void setUNITTYPE(String uNITTYPE) {
		UNITTYPE = uNITTYPE;
	}

	public String getUNITKIND() {
		return UNITKIND;
	}

	public void setUNITKIND(String uNITKIND) {
		UNITKIND = uNITKIND;
	}

	public String getEMPLOYMENT() {
		return EMPLOYMENT;
	}

	public void setEMPLOYMENT(String eMPLOYMENT) {
		EMPLOYMENT = eMPLOYMENT;
	}

	public String getSTAFF() {
		return STAFF;
	}

	public void setSTAFF(String sTAFF) {
		STAFF = sTAFF;
	}

	public String getCOMPANYADD() {
		return COMPANYADD;
	}

	public void setCOMPANYADD(String cOMPANYADD) {
		COMPANYADD = cOMPANYADD;
	}

	public String getCOMPANYZIP() {
		return COMPANYZIP;
	}

	public void setCOMPANYZIP(String cOMPANYZIP) {
		COMPANYZIP = cOMPANYZIP;
	}

	public String getCOMPANYTEL() {
		return COMPANYTEL;
	}

	public void setCOMPANYTEL(String cOMPANYTEL) {
		COMPANYTEL = cOMPANYTEL;
	}

	public String getINSURERCODE() {
		return INSURERCODE;
	}

	public void setINSURERCODE(String iNSURERCODE) {
		INSURERCODE = iNSURERCODE;
	}

	public String getINSURERNAME() {
		return INSURERNAME;
	}

	public void setINSURERNAME(String iNSURERNAME) {
		INSURERNAME = iNSURERNAME;
	}

	public String getINSURERNAMEDETAIL() {
		return INSURERNAMEDETAIL;
	}

	public void setINSURERNAMEDETAIL(String iNSURERNAMEDETAIL) {
		INSURERNAMEDETAIL = iNSURERNAMEDETAIL;
	}

	public String getPOLICYHOLDER() {
		return POLICYHOLDER;
	}

	public void setPOLICYHOLDER(String pOLICYHOLDER) {
		POLICYHOLDER = pOLICYHOLDER;
	}

	public String getBENEFICIARY() {
		return BENEFICIARY;
	}

	public void setBENEFICIARY(String bENEFICIARY) {
		BENEFICIARY = bENEFICIARY;
	}

	public String getINSUREDTOTALAMT() {
		return INSUREDTOTALAMT;
	}

	public void setINSUREDTOTALAMT(String iNSUREDTOTALAMT) {
		INSUREDTOTALAMT = iNSUREDTOTALAMT;
	}

	public String getGUARANTYCURRENCY() {
		return GUARANTYCURRENCY;
	}

	public void setGUARANTYCURRENCY(String gUARANTYCURRENCY) {
		GUARANTYCURRENCY = gUARANTYCURRENCY;
	}

	public String getINSUREBEGINDATE() {
		return INSUREBEGINDATE;
	}

	public void setINSUREBEGINDATE(String iNSUREBEGINDATE) {
		INSUREBEGINDATE = iNSUREBEGINDATE;
	}

	public String getINSUREENDDATE() {
		return INSUREENDDATE;
	}

	public void setINSUREENDDATE(String iNSUREENDDATE) {
		INSUREENDDATE = iNSUREENDDATE;
	}

	public String getPAYBEGINDATE() {
		return PAYBEGINDATE;
	}

	public void setPAYBEGINDATE(String pAYBEGINDATE) {
		PAYBEGINDATE = pAYBEGINDATE;
	}

	public String getCONFIRMVALUE() {
		return CONFIRMVALUE;
	}

	public void setCONFIRMVALUE(String cONFIRMVALUE) {
		CONFIRMVALUE = cONFIRMVALUE;
	}

	public String getVOUCHVALUE() {
		return VOUCHVALUE;
	}

	public void setVOUCHVALUE(String vOUCHVALUE) {
		VOUCHVALUE = vOUCHVALUE;
	}

	public String getOPERATEORGID() {
		return OPERATEORGID;
	}

	public void setOPERATEORGID(String oPERATEORGID) {
		OPERATEORGID = oPERATEORGID;
	}

	public String getINSURANCECONTACTS() {
		return INSURANCECONTACTS;
	}

	public void setINSURANCECONTACTS(String iNSURANCECONTACTS) {
		INSURANCECONTACTS = iNSURANCECONTACTS;
	}

	public String getINSURANCETELEPHONE() {
		return INSURANCETELEPHONE;
	}

	public void setINSURANCETELEPHONE(String iNSURANCETELEPHONE) {
		INSURANCETELEPHONE = iNSURANCETELEPHONE;
	}

	public String getIMAGEID() {
		return IMAGEID;
	}

	public void setIMAGEID(String iMAGEID) {
		IMAGEID = iMAGEID;
	}

	public String getPAYKIND() {
		return PAYKIND;
	}

	public void setPAYKIND(String pAYKIND) {
		PAYKIND = pAYKIND;
	}

	public String getACCTTYPE() {
		return ACCTTYPE;
	}

	public void setACCTTYPE(String aCCTTYPE) {
		ACCTTYPE = aCCTTYPE;
	}

	public String getBORROWERTYPE() {
		return BORROWERTYPE;
	}

	public void setBORROWERTYPE(String bORROWERTYPE) {
		BORROWERTYPE = bORROWERTYPE;
	}

	public String getENTERPRISENAME() {
		return ENTERPRISENAME;
	}

	public void setENTERPRISENAME(String eNTERPRISENAME) {
		ENTERPRISENAME = eNTERPRISENAME;
	}

	public String getRECEIPTSPROVE() {
		return RECEIPTSPROVE;
	}

	public void setRECEIPTSPROVE(String rECEIPTSPROVE) {
		RECEIPTSPROVE = rECEIPTSPROVE;
	}

	public String getENTERPRISEINCOMEMON() {
		return ENTERPRISEINCOMEMON;
	}

	public void setENTERPRISEINCOMEMON(String eNTERPRISEINCOMEMON) {
		ENTERPRISEINCOMEMON = eNTERPRISEINCOMEMON;
	}

	public String getACCOUNTINCOMEYEAR() {
		return ACCOUNTINCOMEYEAR;
	}

	public void setACCOUNTINCOMEYEAR(String aCCOUNTINCOMEYEAR) {
		ACCOUNTINCOMEYEAR = aCCOUNTINCOMEYEAR;
	}

	public String getSHAREPCT() {
		return SHAREPCT;
	}

	public void setSHAREPCT(String sHAREPCT) {
		SHAREPCT = sHAREPCT;
	}

	public String getLICENSENO() {
		return LICENSENO;
	}

	public void setLICENSENO(String lICENSENO) {
		LICENSENO = lICENSENO;
	}

	public String getDEPARTID() {
		return DEPARTID;
	}

	public void setDEPARTID(String dEPARTID) {
		DEPARTID = dEPARTID;
	}

	public String getINDUSTRYTYPE() {
		return INDUSTRYTYPE;
	}

	public void setINDUSTRYTYPE(String iNDUSTRYTYPE) {
		INDUSTRYTYPE = iNDUSTRYTYPE;
	}

	public String getPURPOSETYPE() {
		return PURPOSETYPE;
	}

	public void setPURPOSETYPE(String pURPOSETYPE) {
		PURPOSETYPE = pURPOSETYPE;
	}

	public String getINSURERATE() {
		return INSURERATE;
	}

	public void setINSURERATE(String iNSURERATE) {
		INSURERATE = iNSURERATE;
	}



	public String getPREINSURATE() {
		return PREINSURATE;
	}

	public void setPREINSURATE(String pREINSURATE) {
		PREINSURATE = pREINSURATE;
	}

	public String getPREMIUMPAYTYPE() {
		return PREMIUMPAYTYPE;
	}

	public void setPREMIUMPAYTYPE(String pREMIUMPAYTYPE) {
		PREMIUMPAYTYPE = pREMIUMPAYTYPE;
	}

	public String getPRODUCTTYPE() {
		return PRODUCTTYPE;
	}

	public void setPRODUCTTYPE(String pRODUCTTYPE) {
		PRODUCTTYPE = pRODUCTTYPE;
	}

	public String getAPPLYNO() {
		return APPLYNO;
	}

	public void setAPPLYNO(String aPPLYNO) {
		APPLYNO = aPPLYNO;
	}

	public String getCONTRACTNO() {
		return CONTRACTNO;
	}

	public void setCONTRACTNO(String cONTRACTNO) {
		CONTRACTNO = cONTRACTNO;
	}

	public String getPRODID() {
		return PRODID;
	}

	public void setPRODID(String pRODID) {
		PRODID = pRODID;
	}

	public String getPRODNAME() {
		return PRODNAME;
	}

	public void setPRODNAME(String pRODNAME) {
		PRODNAME = pRODNAME;
	}

	public String getSTORIED() {
		return STORIED;
	}

	public void setSTORIED(String sTORIED) {
		STORIED = sTORIED;
	}

	public String getSTORIEDNAME() {
		return STORIEDNAME;
	}

	public void setSTORIEDNAME(String sTORIEDNAME) {
		STORIEDNAME = sTORIEDNAME;
	}

	public String getHOMEADDRPROV() {
		return HOMEADDRPROV;
	}

	public void setHOMEADDRPROV(String hOMEADDRPROV) {
		HOMEADDRPROV = hOMEADDRPROV;
	}

	public String getHOMEADDRCITY() {
		return HOMEADDRCITY;
	}

	public void setHOMEADDRCITY(String hOMEADDRCITY) {
		HOMEADDRCITY = hOMEADDRCITY;
	}

	public String getHOMEADDRDISTRICT() {
		return HOMEADDRDISTRICT;
	}

	public void setHOMEADDRDISTRICT(String hOMEADDRDISTRICT) {
		HOMEADDRDISTRICT = hOMEADDRDISTRICT;
	}

	public String getEMPLOYEDTYPE() {
		return EMPLOYEDTYPE;
	}

	public void setEMPLOYEDTYPE(String eMPLOYEDTYPE) {
		EMPLOYEDTYPE = eMPLOYEDTYPE;
	}

	public String getWORKYEAR() {
		return WORKYEAR;
	}

	public void setWORKYEAR(String wORKYEAR) {
		WORKYEAR = wORKYEAR;
	}

	public String getWORKMONTH() {
		return WORKMONTH;
	}

	public void setWORKMONTH(String wORKMONTH) {
		WORKMONTH = wORKMONTH;
	}

	public String getRELAPHONE1() {
		return RELAPHONE1;
	}

	public void setRELAPHONE1(String rELAPHONE1) {
		RELAPHONE1 = rELAPHONE1;
	}

	public String getRELATION1() {
		return RELATION1;
	}

	public void setRELATION1(String rELATION1) {
		RELATION1 = rELATION1;
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	public String getCOUNTRY() {
		return COUNTRY;
	}

	public void setCOUNTRY(String cOUNTRY) {
		COUNTRY = cOUNTRY;
	}

	public String getDUEDATE() {
		return DUEDATE;
	}

	public void setDUEDATE(String dUEDATE) {
		DUEDATE = dUEDATE;
	}

	public String getEDUEXPERIANCE() {
		return EDUEXPERIANCE;
	}

	public void setEDUEXPERIANCE(String eDUEXPERIANCE) {
		EDUEXPERIANCE = eDUEXPERIANCE;
	}

	public String getEDUDEGREE() {
		return EDUDEGREE;
	}

	public void setEDUDEGREE(String eDUDEGREE) {
		EDUDEGREE = eDUDEGREE;
	}

	public String getPOSITION() {
		return POSITION;
	}

	public void setPOSITION(String pOSITION) {
		POSITION = pOSITION;
	}

	public String getFAMILYCITY() {
		return FAMILYCITY;
	}

	public void setFAMILYCITY(String fAMILYCITY) {
		FAMILYCITY = fAMILYCITY;
	}

	public String getCOMPANYADDHEAD() {
		return COMPANYADDHEAD;
	}

	public void setCOMPANYADDHEAD(String cOMPANYADDHEAD) {
		COMPANYADDHEAD = cOMPANYADDHEAD;
	}

	

	public String getBANKCODE() {
		return BANKCODE;
	}

	public void setBANKCODE(String bANKCODE) {
		BANKCODE = bANKCODE;
	}

	public String getQUDLAY() {
		return QUDLAY;
	}

	public void setQUDLAY(String qUDLAY) {
		QUDLAY = qUDLAY;
	}

	public String getBANKNAME() {
		return BANKNAME;
	}

	public void setBANKNAME(String bANKNAME) {
		BANKNAME = bANKNAME;
	}

	public String getBANKPROVINCE() {
		return BANKPROVINCE;
	}

	public void setBANKPROVINCE(String bANKPROVINCE) {
		BANKPROVINCE = bANKPROVINCE;
	}

	public String getBANKCITY() {
		return BANKCITY;
	}

	public void setBANKCITY(String bANKCITY) {
		BANKCITY = bANKCITY;
	}

	public String getCONSULTRATE() {
		return CONSULTRATE;
	}

	public void setCONSULTRATE(String cONSULTRATE) {
		CONSULTRATE = cONSULTRATE;
	}

	public String getSUBAPPMANNO() {
		return SUBAPPMANNO;
	}

	public void setSUBAPPMANNO(String sUBAPPMANNO) {
		SUBAPPMANNO = sUBAPPMANNO;
	}

	public String getAPPLYMANNO() {
		return APPLYMANNO;
	}

	public void setAPPLYMANNO(String aPPLYMANNO) {
		APPLYMANNO = aPPLYMANNO;
	}

	public String getBUSIMODE() {
		return BUSIMODE;
	}

	public void setBUSIMODE(String bUSIMODE) {
		BUSIMODE = bUSIMODE;
	}

	public String getMONCOMFEERATE() {
		return MONCOMFEERATE;
	}

	public void setMONCOMFEERATE(String mONCOMFEERATE) {
		MONCOMFEERATE = mONCOMFEERATE;
	}

	public String getCONSERVFEERATE() {
		return CONSERVFEERATE;
	}

	public void setCONSERVFEERATE(String cONSERVFEERATE) {
		CONSERVFEERATE = cONSERVFEERATE;
	}

	public String getCONMANAFEERATE() {
		return CONMANAFEERATE;
	}

	public void setCONMANAFEERATE(String cONMANAFEERATE) {
		CONMANAFEERATE = cONMANAFEERATE;
	}

	public String getXTERM() {
		return XTERM;
	}

	public void setXTERM(String xTERM) {
		XTERM = xTERM;
	}

	public String getYTERM() {
		return YTERM;
	}

	public void setYTERM(String yTERM) {
		YTERM = yTERM;
	}



}
