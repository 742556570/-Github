package com.jeeplus.modules.app.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.jeeplus.common.persistence.DataEntity;

public class ClUsrLmtamt extends DataEntity<ClUsrLmtamt>{
    private String usrCde;

    private BigDecimal creditAmount;

    private BigDecimal usedAmount;

    private BigDecimal repayAmount;

    private Date mdfDt;

    private String admUsr;

    private String admDetail;

    public String getUsrCde() {
        return usrCde;
    }

    public void setUsrCde(String usrCde) {
        this.usrCde = usrCde == null ? null : usrCde.trim();
    }

    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }

    public BigDecimal getUsedAmount() {
        return usedAmount;
    }

    public void setUsedAmount(BigDecimal usedAmount) {
        this.usedAmount = usedAmount;
    }

    public BigDecimal getRepayAmount() {
        return repayAmount;
    }

    public void setRepayAmount(BigDecimal repayAmount) {
        this.repayAmount = repayAmount;
    }

    public Date getMdfDt() {
        return mdfDt;
    }

    public void setMdfDt(Date mdfDt) {
        this.mdfDt = mdfDt;
    }

    public String getAdmUsr() {
        return admUsr;
    }

    public void setAdmUsr(String admUsr) {
        this.admUsr = admUsr == null ? null : admUsr.trim();
    }

    public String getAdmDetail() {
        return admDetail;
    }

    public void setAdmDetail(String admDetail) {
        this.admDetail = admDetail == null ? null : admDetail.trim();
    }
}