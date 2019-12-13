package com.jeeplus.modules.app.entity;

import com.jeeplus.common.persistence.DataEntity;

public class ClCopBank  extends DataEntity<ClCopBank> {
    private String copBankNo;

    private String copBankName;

    private String copBankStatus;

    private String copCodeType;

    private String copCodeNo;

    private String copAccountNo;

    private String copAccountName;

    private String copOpenId;

    private String copOpenName;

    private Long copRepayDay;

    private Long copPayDay;

    private Long copAllowDay;

    private String copType;

    private String copRelaBank;

    private String copScopeDesc;

    private String copScopeId;

    private String lastChgDt;

    private String lastChgUsr;

    private String copAddr;

    private String copAddrCode;

    private String contOrPolicy;

    public String getCopBankNo() {
        return copBankNo;
    }

    public void setCopBankNo(String copBankNo) {
        this.copBankNo = copBankNo == null ? null : copBankNo.trim();
    }

    public String getCopBankName() {
        return copBankName;
    }

    public void setCopBankName(String copBankName) {
        this.copBankName = copBankName == null ? null : copBankName.trim();
    }

    public String getCopBankStatus() {
        return copBankStatus;
    }

    public void setCopBankStatus(String copBankStatus) {
        this.copBankStatus = copBankStatus == null ? null : copBankStatus.trim();
    }

    public String getCopCodeType() {
        return copCodeType;
    }

    public void setCopCodeType(String copCodeType) {
        this.copCodeType = copCodeType == null ? null : copCodeType.trim();
    }

    public String getCopCodeNo() {
        return copCodeNo;
    }

    public void setCopCodeNo(String copCodeNo) {
        this.copCodeNo = copCodeNo == null ? null : copCodeNo.trim();
    }

    public String getCopAccountNo() {
        return copAccountNo;
    }

    public void setCopAccountNo(String copAccountNo) {
        this.copAccountNo = copAccountNo == null ? null : copAccountNo.trim();
    }

    public String getCopAccountName() {
        return copAccountName;
    }

    public void setCopAccountName(String copAccountName) {
        this.copAccountName = copAccountName == null ? null : copAccountName.trim();
    }

    public String getCopOpenId() {
        return copOpenId;
    }

    public void setCopOpenId(String copOpenId) {
        this.copOpenId = copOpenId == null ? null : copOpenId.trim();
    }

    public String getCopOpenName() {
        return copOpenName;
    }

    public void setCopOpenName(String copOpenName) {
        this.copOpenName = copOpenName == null ? null : copOpenName.trim();
    }

    public Long getCopRepayDay() {
        return copRepayDay;
    }

    public void setCopRepayDay(Long copRepayDay) {
        this.copRepayDay = copRepayDay;
    }

    public Long getCopPayDay() {
        return copPayDay;
    }

    public void setCopPayDay(Long copPayDay) {
        this.copPayDay = copPayDay;
    }

    public Long getCopAllowDay() {
        return copAllowDay;
    }

    public void setCopAllowDay(Long copAllowDay) {
        this.copAllowDay = copAllowDay;
    }

    public String getCopType() {
        return copType;
    }

    public void setCopType(String copType) {
        this.copType = copType == null ? null : copType.trim();
    }

    public String getCopRelaBank() {
        return copRelaBank;
    }

    public void setCopRelaBank(String copRelaBank) {
        this.copRelaBank = copRelaBank == null ? null : copRelaBank.trim();
    }

    public String getCopScopeDesc() {
        return copScopeDesc;
    }

    public void setCopScopeDesc(String copScopeDesc) {
        this.copScopeDesc = copScopeDesc == null ? null : copScopeDesc.trim();
    }

    public String getCopScopeId() {
        return copScopeId;
    }

    public void setCopScopeId(String copScopeId) {
        this.copScopeId = copScopeId == null ? null : copScopeId.trim();
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

    public String getCopAddr() {
        return copAddr;
    }

    public void setCopAddr(String copAddr) {
        this.copAddr = copAddr == null ? null : copAddr.trim();
    }

    public String getCopAddrCode() {
        return copAddrCode;
    }

    public void setCopAddrCode(String copAddrCode) {
        this.copAddrCode = copAddrCode == null ? null : copAddrCode.trim();
    }

    public String getContOrPolicy() {
        return contOrPolicy;
    }

    public void setContOrPolicy(String contOrPolicy) {
        this.contOrPolicy = contOrPolicy == null ? null : contOrPolicy.trim();
    }
}