package com.jeeplus.modules.app.entity;

import com.jeeplus.common.persistence.DataEntity;

public class ClWithRecover extends DataEntity<ClWithRecover>{
    private Integer loanSeq;

    private String policyCount;

    private String policyNo;

    private String bankCode;

    private String checkDate;

    private String planFee;

    private String capital;

    private String intamt;

    private String premium;

    private String penalsum;
    
    private String sysstate;
    
    private String setlInd;
    
    private String diffType;

    public Integer getLoanSeq() {
        return loanSeq;
    }

    public void setLoanSeq(Integer loanSeq) {
        this.loanSeq = loanSeq;
    }

    public String getPolicyCount() {
        return policyCount;
    }

    public void setPolicyCount(String policyCount) {
        this.policyCount = policyCount == null ? null : policyCount.trim();
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo == null ? null : policyNo.trim();
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate == null ? null : checkDate.trim();
    }

    public String getPlanFee() {
        return planFee;
    }

    public void setPlanFee(String planFee) {
        this.planFee = planFee == null ? null : planFee.trim();
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital == null ? null : capital.trim();
    }

    public String getIntamt() {
        return intamt;
    }

    public void setIntamt(String intamt) {
        this.intamt = intamt == null ? null : intamt.trim();
    }

    public String getPremium() {
        return premium;
    }

    public void setPremium(String premium) {
        this.premium = premium == null ? null : premium.trim();
    }

    public String getPenalsum() {
        return penalsum;
    }

    public void setPenalsum(String penalsum) {
        this.penalsum = penalsum == null ? null : penalsum.trim();
    }

	public String getSysstate() {
		return sysstate;
	}

	public void setSysstate(String sysstate) {
		this.sysstate = sysstate;
	}

	public String getSetlInd() {
		return setlInd;
	}

	public void setSetlInd(String setlInd) {
		this.setlInd = setlInd;
	}

	public String getDiffType() {
		return diffType;
	}

	public void setDiffType(String diffType) {
		this.diffType = diffType;
	}
    
    
}