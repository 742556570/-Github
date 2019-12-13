package com.jeeplus.modules.app.api.account.loanrepaymentplanfromHS.response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("MX")
public class LoanRepaymentPlanFromHSDataBody {

	// 应还期次
	@XStreamAlias("perdNo")
	private String perdNo;
	// 本期应还日期
	@XStreamAlias("dueDt")
	private String dueDt;
	// 应还本金
	@XStreamAlias("normPrcpAmt")
	private BigDecimal normPrcpAmt;
	// 应还利息
	@XStreamAlias("normIntAmt")
	private BigDecimal normIntAmt;
	// 应还罚息
	@XStreamAlias("odIntAmt")
	private BigDecimal odIntAmt;
	// 应还保费
	@XStreamAlias("holdFeeAmt")
	private BigDecimal holdFeeAmt;
	// 应还服务费
	@XStreamAlias("serviceFee")
	private BigDecimal serviceFee;
	// 实还本金
	@XStreamAlias("setlPrcp")

	private BigDecimal setlPrcp;
	// 实还利息
	@XStreamAlias("setlNormInt")
	private BigDecimal setlNormInt;
	// 实还罚息
	@XStreamAlias("setlOdIntAmt")
	private BigDecimal setlOdIntAmt;
	// 实还保费
	@XStreamAlias("setlFeeAmt")
	private BigDecimal setlFeeAmt;
	// 实还服务费
	@XStreamAlias("setlServiceFee")
	private BigDecimal setlServiceFee;
	// 拖欠标识
	@XStreamAlias("psOdInd")
	private String psOdInd;
	// 结清标识
	@XStreamAlias("setlInd")

	private String setlInd;
	// 结清日期
	@XStreamAlias("setlDate")
	private String setlDate;
	// 提前结清应还违约金
	@XStreamAlias("prePayFeeAmt")
	private BigDecimal prePayFeeAmt;
	// 提前结清实还违约金
	@XStreamAlias("setlPrePayFeeAmt")
	private BigDecimal setlPrePayFeeAmt;
	// 追偿应还违约金
	@XStreamAlias("subrogationFeeAmt")
	private BigDecimal subrogationFeeAmt;
	// 追偿实还违约金
	@XStreamAlias("setlSubrogationFeeAmt")
	private BigDecimal setlSubrogationFeeAmt;

}
