package com.jeeplus.modules.app.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.jeeplus.common.persistence.DataEntity;

public class ClUsrLoanDetail extends DataEntity<ClUsrLoanDetail>{
    private Integer ldSeq;

    private String usrCde;

    private String applCde;

    private String loanAcno;

    private String policyNo;

    private String sTerm;

    private Date sDate;

    private BigDecimal sCapi;

    private BigDecimal sInte;

    private BigDecimal sFine;

    private BigDecimal sInsuamt;

    private BigDecimal sContamt;

    private BigDecimal rCapi;

    private BigDecimal rInte;

    private BigDecimal rFine;

    private BigDecimal rInsuamt;

    private BigDecimal rContamt;

    private String overFlag;

    private String payOffFlag;

    private Date payOffDate;

    private Date mdfDt;
    
    private BigDecimal sPenalty;
    
    private BigDecimal rPenalty;
	// 提前结清应还违约金
	private String sPayLiqDam;
	// 提前结清实还违约金
	private String rPayLiqDam;
	// 追偿应还违约金
	private String sRecPayLiqDam;
	// 追偿实还违约金
	private String rRecPayLiqDam;

    public Integer getLdSeq() {
        return ldSeq;
    }

    public void setLdSeq(Integer ldSeq) {
        this.ldSeq = ldSeq;
    }

    public String getUsrCde() {
        return usrCde;
    }

    public void setUsrCde(String usrCde) {
        this.usrCde = usrCde == null ? "" : usrCde.trim();
    }

    public String getApplCde() {
        return applCde;
    }

    public void setApplCde(String applCde) {
        this.applCde = applCde == null ? "" : applCde.trim();
    }

    public String getLoanAcno() {
        return loanAcno;
    }

    public void setLoanAcno(String loanAcno) {
        this.loanAcno = loanAcno == null ? "" : loanAcno.trim();
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo == null ? "" : policyNo.trim();
    }

    public String getsTerm() {
        return sTerm;
    }

    public void setsTerm(String sTerm) {
        this.sTerm = sTerm == null ? "" : sTerm.trim();
    }

    public Date getsDate() {
        return sDate;
    }

    public void setsDate(Date sDate) {
        this.sDate = sDate;
    }

    public BigDecimal getsCapi() {
        return sCapi;
    }

    public void setsCapi(BigDecimal sCapi) {
        this.sCapi = sCapi == null ? new BigDecimal("0.00") : sCapi;
    }

    public BigDecimal getsInte() {
        return sInte ;
    }

    public void setsInte(BigDecimal sInte) {
        this.sInte = sInte  == null ? new BigDecimal("0.00") : sInte;
    }

    public BigDecimal getsFine() {
        return sFine;
    }

    public void setsFine(BigDecimal sFine) {
        this.sFine = sFine == null ? new BigDecimal("0.00") : sFine;
    }

    public BigDecimal getsInsuamt() {
        return sInsuamt;
    }

    public void setsInsuamt(BigDecimal sInsuamt) {
        this.sInsuamt = sInsuamt == null ? new BigDecimal("0.00") : sInsuamt;
    }

    public BigDecimal getsContamt() {
        return sContamt;
    }

    public void setsContamt(BigDecimal sContamt) {
        this.sContamt = sContamt  == null ? new BigDecimal("0.00") : sContamt;
    }

    public BigDecimal getrCapi() {
        return rCapi;
    }

    public void setrCapi(BigDecimal rCapi) {
        this.rCapi = rCapi== null ? new BigDecimal("0.00") : rCapi;
    }

    public BigDecimal getrInte() {
        return rInte;
    }

    public void setrInte(BigDecimal rInte) {
        this.rInte = rInte == null ? new BigDecimal("0.00") : rInte;
    }

    public BigDecimal getrFine() {
        return rFine;
    }

    public void setrFine(BigDecimal rFine) {
        this.rFine = rFine == null ? new BigDecimal("0.00") : rFine;
    }

    public BigDecimal getrInsuamt() {
        return rInsuamt;
    }

    public void setrInsuamt(BigDecimal rInsuamt) {
        this.rInsuamt = rInsuamt== null ? new BigDecimal("0.00") : rInsuamt;
    }

    public BigDecimal getrContamt() {
        return rContamt;
    }

    public void setrContamt(BigDecimal rContamt) {
        this.rContamt = rContamt== null ? new BigDecimal("0.00") : rContamt;
    }

    public String getOverFlag() {
        return overFlag;
    }

    public void setOverFlag(String overFlag) {
        this.overFlag = overFlag == null ? "" : overFlag.trim();
    }

    public String getPayOffFlag() {
        return payOffFlag;
    }

    public void setPayOffFlag(String payOffFlag) {
        this.payOffFlag = payOffFlag == null ? "" : payOffFlag.trim();
    }

    public Date getPayOffDate() {
        return payOffDate;
    }

    public void setPayOffDate(Date payOffDate) {
        this.payOffDate = payOffDate;
    }

    public Date getMdfDt() {
        return mdfDt;
    }

    public void setMdfDt(Date mdfDt) {
        this.mdfDt = mdfDt;
    }

	public BigDecimal getrPenalty() {
		return rPenalty;
	}

	public void setrPenalty(BigDecimal rPenalty) {
		this.rPenalty = rPenalty;
	}

	public BigDecimal getsPenalty() {
		return sPenalty;
	}

	public void setsPenalty(BigDecimal sPenalty) {
		this.sPenalty = sPenalty;
	}

	public String getsPayLiqDam() {
		return sPayLiqDam;
	}

	public void setsPayLiqDam(String sPayLiqDam) {
		this.sPayLiqDam = sPayLiqDam;
	}

	public String getrPayLiqDam() {
		return rPayLiqDam;
	}

	public void setrPayLiqDam(String rPayLiqDam) {
		this.rPayLiqDam = rPayLiqDam;
	}

	public String getsRecPayLiqDam() {
		return sRecPayLiqDam;
	}

	public void setsRecPayLiqDam(String sRecPayLiqDam) {
		this.sRecPayLiqDam = sRecPayLiqDam;
	}

	public String getrRecPayLiqDam() {
		return rRecPayLiqDam;
	}

	public void setrRecPayLiqDam(String rRecPayLiqDam) {
		this.rRecPayLiqDam = rRecPayLiqDam;
	}

	@Override
	public String toString() {
		return "ClUsrLoanDetail [ldSeq=" + ldSeq + ", usrCde=" + usrCde + ", applCde=" + applCde + ", loanAcno="
				+ loanAcno + ", policyNo=" + policyNo + ", sTerm=" + sTerm + ", sDate=" + sDate + ", sCapi=" + sCapi
				+ ", sInte=" + sInte + ", sFine=" + sFine + ", sInsuamt=" + sInsuamt + ", sContamt=" + sContamt
				+ ", rCapi=" + rCapi + ", rInte=" + rInte + ", rFine=" + rFine + ", rInsuamt=" + rInsuamt
				+ ", rContamt=" + rContamt + ", overFlag=" + overFlag + ", payOffFlag=" + payOffFlag + ", payOffDate="
				+ payOffDate + ", mdfDt=" + mdfDt + ", sPenalty=" + sPenalty + ", rPenalty=" + rPenalty
				+ ", sPayLiqDam=" + sPayLiqDam + ", rPayLiqDam=" + rPayLiqDam + ", sRecPayLiqDam=" + sRecPayLiqDam
				+ ", rRecPayLiqDam=" + rRecPayLiqDam + "]";
	}
    
	
    
}