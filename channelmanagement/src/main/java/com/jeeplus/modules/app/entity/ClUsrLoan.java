package com.jeeplus.modules.app.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.jeeplus.common.persistence.DataEntity;

public class ClUsrLoan extends DataEntity<ClUsrLoan>{
	public ClUsrLoan() {
		super();
	}
    private Integer loanSeq;

    private String usrCde;

    private String applCde;

    private String policyNo;

    private String loanAcno;

    private BigDecimal loanAmt;

    private String loanTerm;

    private String retuKind;

    private Date beginDt;

    private Date endDt;

    private String sTerm;

    private Date sDate;

    private String bal;

    private String accFlag;

    private Date mdfDt;

    public Integer getLoanSeq() {
        return loanSeq;
    }

    public void setLoanSeq(Integer loanSeq) {
        this.loanSeq = loanSeq;
    }

    public String getUsrCde() {
        return usrCde;
    }

    public void setUsrCde(String usrCde) {
        this.usrCde = usrCde == null ? null : usrCde.trim();
    }

    public String getApplCde() {
        return applCde;
    }

    public void setApplCde(String applCde) {
        this.applCde = applCde == null ? null : applCde.trim();
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo == null ? null : policyNo.trim();
    }

    public String getLoanAcno() {
        return loanAcno;
    }

    public void setLoanAcno(String loanAcno) {
        this.loanAcno = loanAcno == null ? null : loanAcno.trim();
    }

    public BigDecimal getLoanAmt() {
        return loanAmt;
    }

    public void setLoanAmt(BigDecimal loanAmt) {
        this.loanAmt = loanAmt;
    }

    public String getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(String loanTerm) {
        this.loanTerm = loanTerm == null ? null : loanTerm.trim();
    }

    public String getRetuKind() {
        return retuKind;
    }

    public void setRetuKind(String retuKind) {
        this.retuKind = retuKind == null ? null : retuKind.trim();
    }

    public Date getBeginDt() {
        return beginDt;
    }

    public void setBeginDt(Date beginDt) {
        this.beginDt = beginDt;
    }

    public Date getEndDt() {
        return endDt;
    }

    public void setEndDt(Date endDt) {
        this.endDt = endDt;
    }

    public String getsTerm() {
        return sTerm;
    }

    public void setsTerm(String sTerm) {
        this.sTerm = sTerm == null ? null : sTerm.trim();
    }

    public Date getsDate() {
        return sDate;
    }

    public void setsDate(Date sDate) {
        this.sDate = sDate;
    }

    public String getBal() {
        return bal;
    }

    public void setBal(String bal) {
        this.bal = bal == null ? null : bal.trim();
    }

    public String getAccFlag() {
        return accFlag;
    }

    public void setAccFlag(String accFlag) {
        this.accFlag = accFlag == null ? null : accFlag.trim();
    }

    public Date getMdfDt() {
        return mdfDt;
    }

    public void setMdfDt(Date mdfDt) {
        this.mdfDt = mdfDt;
    }

	public ClUsrLoan(Integer loanSeq, String usrCde, String applCde, String policyNo, String loanAcno,
			BigDecimal loanAmt, String loanTerm, String retuKind, Date beginDt, Date endDt, String sTerm, Date sDate,
			String bal, String accFlag, Date mdfDt) {
		super();
		this.loanSeq = loanSeq;
		this.usrCde = usrCde;
		this.applCde = applCde;
		this.policyNo = policyNo;
		this.loanAcno = loanAcno;
		this.loanAmt = loanAmt;
		this.loanTerm = loanTerm;
		this.retuKind = retuKind;
		this.beginDt = beginDt;
		this.endDt = endDt;
		this.sTerm = sTerm;
		this.sDate = sDate;
		this.bal = bal;
		this.accFlag = accFlag;
		this.mdfDt = mdfDt;
	}

	@Override
	public String toString() {
		return "ClUsrLoan [loanSeq=" + loanSeq + ", usrCde=" + usrCde + ", applCde=" + applCde + ", policyNo="
				+ policyNo + ", loanAcno=" + loanAcno + ", loanAmt=" + loanAmt + ", loanTerm=" + loanTerm
				+ ", retuKind=" + retuKind + ", beginDt=" + beginDt + ", endDt=" + endDt + ", sTerm=" + sTerm
				+ ", sDate=" + sDate + ", bal=" + bal + ", accFlag=" + accFlag + ", mdfDt=" + mdfDt + "]";
	}
    
}