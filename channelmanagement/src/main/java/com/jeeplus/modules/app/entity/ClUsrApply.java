package com.jeeplus.modules.app.entity;

import java.util.Date;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.StringUtils;

public class ClUsrApply extends DataEntity<ClUsrApply>{
    private Integer applSeq;

    private String applCde;

    private String policyNo;

    private String prdCde;

    private String usrCde;
    
    private String loanAcno;

    private String applyAmt;

    private String applyTnr;
    
    private String applyFor;

    private String crtDt;

    private String trdResult;

    private String stCde;

    private String trdDetail;

    private String apprvAmt;

    private String apprvTnr;
    
    private String intRate;
    
    private String premiumRate;

    private String debtStatus="00";
    
    private String mdfDt;

    private String admUsr;

    private String admDetail;

    private String rmk;
    
    private String creditNo;
    
    private String bankCardNo;
    
    private String pmDt;
    
    private String payChannel;

    public Integer getApplSeq() {
        return applSeq;
    }

    public void setApplSeq(Integer applSeq) {
        this.applSeq = applSeq;
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

    public String getPrdCde() {
        return prdCde;
    }

    public void setPrdCde(String prdCde) {
        this.prdCde = prdCde == null ? null : prdCde.trim();
    }

    public String getUsrCde() {
        return usrCde;
    }

    public void setUsrCde(String usrCde) {
        this.usrCde = usrCde == null ? null : usrCde.trim();
    }

    public String getApplyAmt() {
        return applyAmt;
    }

    public void setApplyAmt(String applyAmt) {
        this.applyAmt = applyAmt == null ? null : applyAmt.trim();
    }

    public String getApplyTnr() {
        return applyTnr;
    }

    public void setApplyTnr(String applyTnr) {
        this.applyTnr = applyTnr == null ? null : applyTnr.trim();
    }

    public String getCrtDt() {
        return crtDt;
    }

    public void setCrtDt(String crtDt) {
        this.crtDt = crtDt;
    }

    public String getTrdResult() {
        return trdResult;
    }

    public void setTrdResult(String trdResult) {
        this.trdResult = trdResult == null ? null : trdResult.trim();
    }

    public String getStCde() {
        return stCde;
    }

    public void setStCde(String stCde) {
        this.stCde = stCde == null ? null : stCde.trim();
    }

    public String getTrdDetail() {
        return trdDetail;
    }

    public void setTrdDetail(String trdDetail) {
        this.trdDetail = trdDetail == null ? null : trdDetail.trim();
    }

    public String getApprvAmt() {
        return apprvAmt;
    }

    public void setApprvAmt(String apprvAmt) {
        this.apprvAmt = apprvAmt == null ? null : apprvAmt.trim();
    }

    public String getApprvTnr() {
        return apprvTnr;
    }

    public void setApprvTnr(String apprvTnr) {
        this.apprvTnr = apprvTnr == null ? null : apprvTnr.trim();
    }

    public String getMdfDt() {
        return mdfDt;
    }

    public void setMdfDt(String mdfDt) {
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

    public String getRmk() {
        return rmk;
    }

    public void setRmk(String rmk) {
        this.rmk = rmk == null ? null : rmk.trim();
    }

	public String getApplyFor() {
		return applyFor;
	}

	public void setApplyFor(String applyFor) {
		this.applyFor = applyFor;
	}

	public String getLoanAcno() {
		return loanAcno;
	}

	public void setLoanAcno(String loanAcno) {
		this.loanAcno = loanAcno;
	}
	

	public String getDebtStatus() {
		return debtStatus;
	}

	public void setDebtStatus(String debtStatus) {
		this.debtStatus = debtStatus==null?"00":debtStatus;
		if(StringUtils.isEmpty(this.debtStatus)) {
			debtStatus = "00";
		}
	}

	
	public String getCreditNo() {
		return creditNo;
	}

	public void setCreditNo(String creditNo) {
		this.creditNo = creditNo;
	}
	
	public String getIntRate() {
		return intRate;
	}

	public void setIntRate(String intRate) {
		this.intRate = intRate;
	}

	public String getPremiumRate() {
		return premiumRate;
	}

	public void setPremiumRate(String premiumRate) {
		this.premiumRate = premiumRate;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getPmDt() {
		return pmDt;
	}

	public void setPmDt(String pmDt) {
		this.pmDt = pmDt;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	@Override
	public String toString() {
		return "ClUsrApply [applSeq=" + applSeq + ", applCde=" + applCde + ", policyNo=" + policyNo + ", prdCde="
				+ prdCde + ", usrCde=" + usrCde + ", loanAcno=" + loanAcno + ", applyAmt=" + applyAmt + ", applyTnr="
				+ applyTnr + ", applyFor=" + applyFor + ", crtDt=" + crtDt + ", trdResult=" + trdResult + ", stCde="
				+ stCde + ", trdDetail=" + trdDetail + ", apprvAmt=" + apprvAmt + ", apprvTnr=" + apprvTnr
				+ ", intRate=" + intRate + ", premiumRate=" + premiumRate + ", debtStatus=" + debtStatus + ", mdfDt="
				+ mdfDt + ", admUsr=" + admUsr + ", admDetail=" + admDetail + ", rmk=" + rmk + ", creditNo=" + creditNo
				+ ", bankCardNo=" + bankCardNo + ", pmDt=" + pmDt + ", payChannel=" + payChannel + "]";
	}

	

    
}