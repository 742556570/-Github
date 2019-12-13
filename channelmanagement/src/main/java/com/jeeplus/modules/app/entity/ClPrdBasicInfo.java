package com.jeeplus.modules.app.entity;

import java.math.BigDecimal;

import com.jeeplus.common.persistence.DataEntity;

public class ClPrdBasicInfo extends DataEntity<ClPrdBasicInfo>  {
    private String prdCde;

    private String prdName;

    private String prdType;

    private String docId;

    private String wfiSign;

    private Short payTerm;

    private String contModel;

    private String guarModel;

    private String payMode;

    private String wfiAuth;

    private BigDecimal maxLoanAmt;

    private BigDecimal minLoanAmt;

    private String repayMode;

    private String validDate;

    private String validTime;

    private String prdStatus;

    private String invalidDate;

    private String invalidTime;

    private String prdMemo;

    private String inputId;

    private String inputDate;

    private String lastInputId;

    private String lastModifyDate;

    private String prdSeq;

    private BigDecimal maxLoanRate;

    private BigDecimal minLoanRate;

    private String ptStatus;

    private String ltStatus;

    private String lstStatus;

    private String zyStatus;

    private String sdStatus;

    private String gwStatus;

    private String lsdStatus;

    private String belType;

    private BigDecimal firServiceRate;

    private BigDecimal payServiceRate;

    private String guarCate;

    private BigDecimal guarEmpMaxAmt;

    private BigDecimal guarEmpMinAmt;

    private BigDecimal guarSocietyMaxAmt;

    private BigDecimal guarSocietyMinAmt;

    private BigDecimal grtMaxamtYear;

    private BigDecimal whiteMinLoanRate;
	
	private String bankId;

    private String suitableStore;

    private String supportChannel;

    private String synSys;

    private String suitableStoreId;

    private String supportChannelId;

    private String synSysId;

    private String srcSys;

    public String getPrdCde() {
        return prdCde;
    }

    public void setPrdCde(String prdCde) {
        this.prdCde = prdCde == null ? null : prdCde.trim();
    }

    public String getPrdName() {
        return prdName;
    }

    public void setPrdName(String prdName) {
        this.prdName = prdName == null ? null : prdName.trim();
    }

    public String getPrdType() {
        return prdType;
    }

    public void setPrdType(String prdType) {
        this.prdType = prdType == null ? null : prdType.trim();
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId == null ? null : docId.trim();
    }

    public String getWfiSign() {
        return wfiSign;
    }

    public void setWfiSign(String wfiSign) {
        this.wfiSign = wfiSign == null ? null : wfiSign.trim();
    }

    public Short getPayTerm() {
        return payTerm;
    }

    public void setPayTerm(Short payTerm) {
        this.payTerm = payTerm;
    }

    public String getContModel() {
        return contModel;
    }

    public void setContModel(String contModel) {
        this.contModel = contModel == null ? null : contModel.trim();
    }

    public String getGuarModel() {
        return guarModel;
    }

    public void setGuarModel(String guarModel) {
        this.guarModel = guarModel == null ? null : guarModel.trim();
    }

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode == null ? null : payMode.trim();
    }

    public String getWfiAuth() {
        return wfiAuth;
    }

    public void setWfiAuth(String wfiAuth) {
        this.wfiAuth = wfiAuth == null ? null : wfiAuth.trim();
    }

    public BigDecimal getMaxLoanAmt() {
        return maxLoanAmt;
    }

    public void setMaxLoanAmt(BigDecimal maxLoanAmt) {
        this.maxLoanAmt = maxLoanAmt;
    }

    public BigDecimal getMinLoanAmt() {
        return minLoanAmt;
    }

    public void setMinLoanAmt(BigDecimal minLoanAmt) {
        this.minLoanAmt = minLoanAmt;
    }

    public String getRepayMode() {
        return repayMode;
    }

    public void setRepayMode(String repayMode) {
        this.repayMode = repayMode == null ? null : repayMode.trim();
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate == null ? null : validDate.trim();
    }

    public String getValidTime() {
        return validTime;
    }

    public void setValidTime(String validTime) {
        this.validTime = validTime == null ? null : validTime.trim();
    }

    public String getPrdStatus() {
        return prdStatus;
    }

    public void setPrdStatus(String prdStatus) {
        this.prdStatus = prdStatus == null ? null : prdStatus.trim();
    }

    public String getInvalidDate() {
        return invalidDate;
    }

    public void setInvalidDate(String invalidDate) {
        this.invalidDate = invalidDate == null ? null : invalidDate.trim();
    }

    public String getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(String invalidTime) {
        this.invalidTime = invalidTime == null ? null : invalidTime.trim();
    }

    public String getPrdMemo() {
        return prdMemo;
    }

    public void setPrdMemo(String prdMemo) {
        this.prdMemo = prdMemo == null ? null : prdMemo.trim();
    }

    public String getInputId() {
        return inputId;
    }

    public void setInputId(String inputId) {
        this.inputId = inputId == null ? null : inputId.trim();
    }

    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate == null ? null : inputDate.trim();
    }

    public String getLastInputId() {
        return lastInputId;
    }

    public void setLastInputId(String lastInputId) {
        this.lastInputId = lastInputId == null ? null : lastInputId.trim();
    }

    public String getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(String lastModifyDate) {
        this.lastModifyDate = lastModifyDate == null ? null : lastModifyDate.trim();
    }

    public String getPrdSeq() {
        return prdSeq;
    }

    public void setPrdSeq(String prdSeq) {
        this.prdSeq = prdSeq == null ? null : prdSeq.trim();
    }

    public BigDecimal getMaxLoanRate() {
        return maxLoanRate;
    }

    public void setMaxLoanRate(BigDecimal maxLoanRate) {
        this.maxLoanRate = maxLoanRate;
    }

    public BigDecimal getMinLoanRate() {
        return minLoanRate;
    }

    public void setMinLoanRate(BigDecimal minLoanRate) {
        this.minLoanRate = minLoanRate;
    }

    public String getPtStatus() {
        return ptStatus;
    }

    public void setPtStatus(String ptStatus) {
        this.ptStatus = ptStatus == null ? null : ptStatus.trim();
    }

    public String getLtStatus() {
        return ltStatus;
    }

    public void setLtStatus(String ltStatus) {
        this.ltStatus = ltStatus == null ? null : ltStatus.trim();
    }

    public String getLstStatus() {
        return lstStatus;
    }

    public void setLstStatus(String lstStatus) {
        this.lstStatus = lstStatus == null ? null : lstStatus.trim();
    }

    public String getZyStatus() {
        return zyStatus;
    }

    public void setZyStatus(String zyStatus) {
        this.zyStatus = zyStatus == null ? null : zyStatus.trim();
    }

    public String getSdStatus() {
        return sdStatus;
    }

    public void setSdStatus(String sdStatus) {
        this.sdStatus = sdStatus == null ? null : sdStatus.trim();
    }

    public String getGwStatus() {
        return gwStatus;
    }

    public void setGwStatus(String gwStatus) {
        this.gwStatus = gwStatus == null ? null : gwStatus.trim();
    }

    public String getLsdStatus() {
        return lsdStatus;
    }

    public void setLsdStatus(String lsdStatus) {
        this.lsdStatus = lsdStatus == null ? null : lsdStatus.trim();
    }

    public String getBelType() {
        return belType;
    }

    public void setBelType(String belType) {
        this.belType = belType == null ? null : belType.trim();
    }

    public BigDecimal getFirServiceRate() {
        return firServiceRate;
    }

    public void setFirServiceRate(BigDecimal firServiceRate) {
        this.firServiceRate = firServiceRate;
    }

    public BigDecimal getPayServiceRate() {
        return payServiceRate;
    }

    public void setPayServiceRate(BigDecimal payServiceRate) {
        this.payServiceRate = payServiceRate;
    }

    public String getGuarCate() {
        return guarCate;
    }

    public void setGuarCate(String guarCate) {
        this.guarCate = guarCate == null ? null : guarCate.trim();
    }

    public BigDecimal getGuarEmpMaxAmt() {
        return guarEmpMaxAmt;
    }

    public void setGuarEmpMaxAmt(BigDecimal guarEmpMaxAmt) {
        this.guarEmpMaxAmt = guarEmpMaxAmt;
    }

    public BigDecimal getGuarEmpMinAmt() {
        return guarEmpMinAmt;
    }

    public void setGuarEmpMinAmt(BigDecimal guarEmpMinAmt) {
        this.guarEmpMinAmt = guarEmpMinAmt;
    }

    public BigDecimal getGuarSocietyMaxAmt() {
        return guarSocietyMaxAmt;
    }

    public void setGuarSocietyMaxAmt(BigDecimal guarSocietyMaxAmt) {
        this.guarSocietyMaxAmt = guarSocietyMaxAmt;
    }

    public BigDecimal getGuarSocietyMinAmt() {
        return guarSocietyMinAmt;
    }

    public void setGuarSocietyMinAmt(BigDecimal guarSocietyMinAmt) {
        this.guarSocietyMinAmt = guarSocietyMinAmt;
    }

    public BigDecimal getGrtMaxamtYear() {
        return grtMaxamtYear;
    }

    public void setGrtMaxamtYear(BigDecimal grtMaxamtYear) {
        this.grtMaxamtYear = grtMaxamtYear;
    }

    public BigDecimal getWhiteMinLoanRate() {
        return whiteMinLoanRate;
    }

    public void setWhiteMinLoanRate(BigDecimal whiteMinLoanRate) {
        this.whiteMinLoanRate = whiteMinLoanRate;
    }
	
	public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId == null ? null : bankId.trim();
    }

    public String getSuitableStore() {
        return suitableStore;
    }

    public void setSuitableStore(String suitableStore) {
        this.suitableStore = suitableStore == null ? null : suitableStore.trim();
    }

    public String getSupportChannel() {
        return supportChannel;
    }

    public void setSupportChannel(String supportChannel) {
        this.supportChannel = supportChannel == null ? null : supportChannel.trim();
    }

    public String getSynSys() {
        return synSys;
    }

    public void setSynSys(String synSys) {
        this.synSys = synSys == null ? null : synSys.trim();
    }

    public String getSuitableStoreId() {
        return suitableStoreId;
    }

    public void setSuitableStoreId(String suitableStoreId) {
        this.suitableStoreId = suitableStoreId == null ? null : suitableStoreId.trim();
    }

    public String getSupportChannelId() {
        return supportChannelId;
    }

    public void setSupportChannelId(String supportChannelId) {
        this.supportChannelId = supportChannelId == null ? null : supportChannelId.trim();
    }

    public String getSynSysId() {
        return synSysId;
    }

    public void setSynSysId(String synSysId) {
        this.synSysId = synSysId == null ? null : synSysId.trim();
    }

    public String getSrcSys() {
        return srcSys;
    }

    public void setSrcSys(String srcSys) {
        this.srcSys = srcSys == null ? null : srcSys.trim();
    }
}