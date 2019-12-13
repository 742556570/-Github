package com.jeeplus.modules.app.entity;

import java.util.Date;

import com.jeeplus.common.persistence.DataEntity;

public class ClUsrContract extends DataEntity<ClUsrContract> {
    private String contractCde;

    private String usrCde;

    private String step;

    private String templateId;

    private String policyNo;

    private String customerId;

    private String stCde;

    private String isUpyun;

    private Date crtDt;

    private Date mdfDt;
    
    private String dlUrl;

    private String vUrl;

    private String upyunUrl;
    
    private String imgDic;
   
    private String docTitle;
    
    private String conVer;

    public String getDlUrl() {
        return dlUrl;
    }

    public void setDlUrl(String dlUrl) {
        this.dlUrl = dlUrl == null ? null : dlUrl.trim();
    }

    public String getvUrl() {
        return vUrl;
    }

    public void setvUrl(String vUrl) {
        this.vUrl = vUrl == null ? null : vUrl.trim();
    }

    public String getUpyunUrl() {
        return upyunUrl;
    }

    public void setUpyunUrl(String upyunUrl) {
        this.upyunUrl = upyunUrl == null ? null : upyunUrl.trim();
    }
    public String getContractCde() {
        return contractCde;
    }

    public void setContractCde(String contractCde) {
        this.contractCde = contractCde == null ? null : contractCde.trim();
    }

    public String getUsrCde() {
        return usrCde;
    }

    public void setUsrCde(String usrCde) {
        this.usrCde = usrCde == null ? null : usrCde.trim();
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step == null ? null : step.trim();
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo == null ? null : policyNo.trim();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    public String getStCde() {
        return stCde;
    }

    public void setStCde(String stCde) {
        this.stCde = stCde == null ? null : stCde.trim();
    }

    public String getIsUpyun() {
        return isUpyun;
    }

    public void setIsUpyun(String isUpyun) {
        this.isUpyun = isUpyun == null ? null : isUpyun.trim();
    }

    public Date getCrtDt() {
        return crtDt;
    }

    public void setCrtDt(Date crtDt) {
        this.crtDt = crtDt;
    }

    public Date getMdfDt() {
        return mdfDt;
    }

    public void setMdfDt(Date mdfDt) {
        this.mdfDt = mdfDt;
    }

	public String getImgDic() {
		return imgDic;
	}

	public void setImgDic(String imgDic) {
		this.imgDic = imgDic;
	}

	public String getDocTitle() {
		return docTitle;
	}

	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}

	public String getConVer() {
		if(this.step.equals("3-2") || this.step.equals("5-1") || this.step.equals("5-2")) {
			return "1.1";
		}else if(this.step.equals("3-1") || this.step.equals("4-3") || this.step.equals("5-3"))  {
			return "1.2";
		}else {
			return "1.0";
		}
	}

	public void setConVer(String conVer) {
		this.conVer = conVer;
	}
    
	
    
}