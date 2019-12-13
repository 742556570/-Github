package com.jeeplus.modules.app.entity;

import java.math.BigDecimal;

import com.jeeplus.common.persistence.DataEntity;

public class ClCopBankRate extends DataEntity<ClCopBankRate>{
    private String copBankNo;

    private String ratePer;

    private String rateType;

    private BigDecimal baseRate;

    private String floatType;

    private BigDecimal floatRate;

    private BigDecimal loanIntRate;

    private String rateGutr;

    private String rateState;

    private String lastChgDt;

    private String lastChgUsr;

    private BigDecimal odIntRate;

    private Short copRepayDay;

    private Short copPayDay;

    private String belType;

    public String getCopBankNo() {
        return copBankNo;
    }

    public void setCopBankNo(String copBankNo) {
        this.copBankNo = copBankNo == null ? null : copBankNo.trim();
    }

    public String getRatePer() {
        return ratePer;
    }

    public void setRatePer(String ratePer) {
        this.ratePer = ratePer == null ? null : ratePer.trim();
    }

    public String getRateType() {
        return rateType;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType == null ? null : rateType.trim();
    }

    public BigDecimal getBaseRate() {
        return baseRate;
    }

    public void setBaseRate(BigDecimal baseRate) {
        this.baseRate = baseRate;
    }

    public String getFloatType() {
        return floatType;
    }

    public void setFloatType(String floatType) {
        this.floatType = floatType == null ? null : floatType.trim();
    }

    public BigDecimal getFloatRate() {
        return floatRate;
    }

    public void setFloatRate(BigDecimal floatRate) {
        this.floatRate = floatRate;
    }

    public BigDecimal getLoanIntRate() {
        return loanIntRate;
    }

    public void setLoanIntRate(BigDecimal loanIntRate) {
        this.loanIntRate = loanIntRate;
    }

    public String getRateGutr() {
        return rateGutr;
    }

    public void setRateGutr(String rateGutr) {
        this.rateGutr = rateGutr == null ? null : rateGutr.trim();
    }

    public String getRateState() {
        return rateState;
    }

    public void setRateState(String rateState) {
        this.rateState = rateState == null ? null : rateState.trim();
    }

    public String getLastChgDt() {
        return lastChgDt;
    }

    public void setLastChgDt(String lastChgDt) {
        this.lastChgDt = lastChgDt == null ? null : lastChgDt.trim();
    }

    public String getLastChgUsr() {
        return lastChgUsr;
    }

    public void setLastChgUsr(String lastChgUsr) {
        this.lastChgUsr = lastChgUsr == null ? null : lastChgUsr.trim();
    }

    public BigDecimal getOdIntRate() {
        return odIntRate;
    }

    public void setOdIntRate(BigDecimal odIntRate) {
        this.odIntRate = odIntRate;
    }

    public Short getCopRepayDay() {
        return copRepayDay;
    }

    public void setCopRepayDay(Short copRepayDay) {
        this.copRepayDay = copRepayDay;
    }

    public Short getCopPayDay() {
        return copPayDay;
    }

    public void setCopPayDay(Short copPayDay) {
        this.copPayDay = copPayDay;
    }

    public String getBelType() {
        return belType;
    }

    public void setBelType(String belType) {
        this.belType = belType == null ? null : belType.trim();
    }
}