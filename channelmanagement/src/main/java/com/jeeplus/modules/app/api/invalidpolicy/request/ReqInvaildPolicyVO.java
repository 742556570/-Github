package com.jeeplus.modules.app.api.invalidpolicy.request;

import java.io.Serializable;
import java.util.List;

/**
 * 无效保单请求参数vo
 * 
 * @author wangfz
 * @date 2018-2-24
 */
public class ReqInvaildPolicyVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String sendSeq;// 发送保单唯一编码
	private String sendTime;// 发送时间
	private String businessNature;// 业务来源
	private String channelType;// 进单渠道
	private String shortRate;// 短期费率
	private String insuredIdentifyNumber;// 保户标识 传放款银行的统一信用代码，产品提供
	private String appliName;// 申请人姓名
	private String amount;// 贷款签约金额
	private String signDate;// 签单日期
	private String handler1Code;// 销售人员代码1
	private String loanEndDate;// 起始居住日期（止）
	private String agreementNo;// 代理协议号
	private String addressName;// 详细地址，现住房地址 贷款人现住地址
	private String appliPhoneNumber;// 投保人电话号码，家庭电话
	private String inputDate;// 录入日期 进件日期，与综合保持一致
	private String pledgeName;// 职位级别 送中文
	private String riskCode;// 险种
	private String agentCode;// 代理人代码 与34一致。
	private String appliMobile;// 申请人手机号码
	private String disRate;// 手续费率
	private String handlerCode;// 销售人员代码 同22保持一致。不为空。
	private String loanAmount;// 贷款金额（签约金额）
	private String loanUsage;// 贷款用途 生产生活所需 2日常生活消费 32提高生活质量 1购物 4教育支出 8医疗 16其他(请说明) 32经营类型(请说明)
								// 32放款渠道为ZYXF时家装 2婚庆 32旅游 1教育 8 其他(请说明) 32
	private String appliAddress;// 详细地址，现住房地址
	private String insuredName;// 放款渠道中文名
	private String payTimes;// 分期缴费次数 趸交为1；期缴按一个保单号对应的还款期数给固定的期数。
	private String comCode;// 销售人员对应组的核心代码 需产品确认，销售人员对应组的核心代码，核心表中必须存在，否则无法送入。
	private String rate;// 费率 月保费费率，每一个保单对应的每月保费的费率值。百分号不送。
	private String appliIdentifyNumber;// 证件号码
	private String policyNo;// 保单号
	private String sumPremium;// 一个保单对应的总保费金额。
	private String sumAmount;// 总保险金额
	private String disabilityPart;// 单位地址
	private String operatorCode;// 录单人员代码
	private String operatesite;// 系统唯一标识
	private String makeCom;// 受理机构,对应的核心代码
	private String mortgageNo;// 单位电话
	List<ReqItemHouseDetailVO> itemHouseDetail;// 房产信息
	List<ReqLinkPersonDetailVO> linkPersonDetail;// 联系人信息

	public String getSendSeq() {
		return sendSeq;
	}

	public void setSendSeq(String sendSeq) {
		this.sendSeq = sendSeq;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getBusinessNature() {
		return businessNature;
	}

	public void setBusinessNature(String businessNature) {
		this.businessNature = businessNature;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getShortRate() {
		return shortRate;
	}

	public void setShortRate(String shortRate) {
		this.shortRate = shortRate;
	}

	public String getInsuredIdentifyNumber() {
		return insuredIdentifyNumber;
	}

	public void setInsuredIdentifyNumber(String insuredIdentifyNumber) {
		this.insuredIdentifyNumber = insuredIdentifyNumber;
	}

	public String getAppliName() {
		return appliName;
	}

	public void setAppliName(String appliName) {
		this.appliName = appliName;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getSignDate() {
		return signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}

	public String getHandler1Code() {
		return handler1Code;
	}

	public void setHandler1Code(String handler1Code) {
		this.handler1Code = handler1Code;
	}

	public String getLoanEndDate() {
		return loanEndDate;
	}

	public void setLoanEndDate(String loanEndDate) {
		this.loanEndDate = loanEndDate;
	}

	public String getAgreementNo() {
		return agreementNo;
	}

	public void setAgreementNo(String agreementNo) {
		this.agreementNo = agreementNo;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public String getAppliPhoneNumber() {
		return appliPhoneNumber;
	}

	public void setAppliPhoneNumber(String appliPhoneNumber) {
		this.appliPhoneNumber = appliPhoneNumber;
	}

	public String getInputDate() {
		return inputDate;
	}

	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}

	public String getPledgeName() {
		return pledgeName;
	}

	public void setPledgeName(String pledgeName) {
		this.pledgeName = pledgeName;
	}

	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getAppliMobile() {
		return appliMobile;
	}

	public void setAppliMobile(String appliMobile) {
		this.appliMobile = appliMobile;
	}

	public String getDisRate() {
		return disRate;
	}

	public void setDisRate(String disRate) {
		this.disRate = disRate;
	}

	public String getHandlerCode() {
		return handlerCode;
	}

	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getLoanUsage() {
		return loanUsage;
	}

	public void setLoanUsage(String loanUsage) {
		this.loanUsage = loanUsage;
	}

	public String getAppliAddress() {
		return appliAddress;
	}

	public void setAppliAddress(String appliAddress) {
		this.appliAddress = appliAddress;
	}

	public String getInsuredName() {
		return insuredName;
	}

	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	public String getPayTimes() {
		return payTimes;
	}

	public void setPayTimes(String payTimes) {
		this.payTimes = payTimes;
	}

	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getAppliIdentifyNumber() {
		return appliIdentifyNumber;
	}

	public void setAppliIdentifyNumber(String appliIdentifyNumber) {
		this.appliIdentifyNumber = appliIdentifyNumber;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getSumPremium() {
		return sumPremium;
	}

	public void setSumPremium(String sumPremium) {
		this.sumPremium = sumPremium;
	}

	public String getSumAmount() {
		return sumAmount;
	}

	public void setSumAmount(String sumAmount) {
		this.sumAmount = sumAmount;
	}

	public String getDisabilityPart() {
		return disabilityPart;
	}

	public void setDisabilityPart(String disabilityPart) {
		this.disabilityPart = disabilityPart;
	}

	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	public String getOperatesite() {
		return operatesite;
	}

	public void setOperatesite(String operatesite) {
		this.operatesite = operatesite;
	}

	public String getMakeCom() {
		return makeCom;
	}

	public void setMakeCom(String makeCom) {
		this.makeCom = makeCom;
	}

	public String getMortgageNo() {
		return mortgageNo;
	}

	public void setMortgageNo(String mortgageNo) {
		this.mortgageNo = mortgageNo;
	}

	public List<ReqItemHouseDetailVO> getItemHouseDetail() {
		return itemHouseDetail;
	}

	public void setItemHouseDetail(List<ReqItemHouseDetailVO> itemHouseDetail) {
		this.itemHouseDetail = itemHouseDetail;
	}

	public List<ReqLinkPersonDetailVO> getLinkPersonDetail() {
		return linkPersonDetail;
	}

	public void setLinkPersonDetail(List<ReqLinkPersonDetailVO> linkPersonDetail) {
		this.linkPersonDetail = linkPersonDetail;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "ReqInvaildPolicyVO [sendSeq=" + sendSeq + ", sendTime=" + sendTime + ", businessNature="
				+ businessNature + ", channelType=" + channelType + ", shortRate=" + shortRate
				+ ", insuredIdentifyNumber=" + insuredIdentifyNumber + ", appliName=" + appliName + ", amount=" + amount
				+ ", signDate=" + signDate + ", handler1Code=" + handler1Code + ", loanEndDate=" + loanEndDate
				+ ", agreementNo=" + agreementNo + ", addressName=" + addressName + ", appliPhoneNumber="
				+ appliPhoneNumber + ", inputDate=" + inputDate + ", pledgeName=" + pledgeName + ", riskCode="
				+ riskCode + ", agentCode=" + agentCode + ", appliMobile=" + appliMobile + ", disRate=" + disRate
				+ ", handlerCode=" + handlerCode + ", loanAmount=" + loanAmount + ", loanUsage=" + loanUsage
				+ ", appliAddress=" + appliAddress + ", insuredName=" + insuredName + ", payTimes=" + payTimes
				+ ", comCode=" + comCode + ", rate=" + rate + ", appliIdentifyNumber=" + appliIdentifyNumber
				+ ", policyNo=" + policyNo + ", sumPremium=" + sumPremium + ", sumAmount=" + sumAmount
				+ ", disabilityPart=" + disabilityPart + ", operatorCode=" + operatorCode + ", operatesite="
				+ operatesite + ", makeCom=" + makeCom + ", mortgageNo=" + mortgageNo + ", itemHouseDetail="
				+ itemHouseDetail + ", linkPersonDetail=" + linkPersonDetail + "]";
	}

}
