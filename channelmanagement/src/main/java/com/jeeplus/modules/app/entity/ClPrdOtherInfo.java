package com.jeeplus.modules.app.entity;

import com.jeeplus.common.persistence.DataEntity;

public class ClPrdOtherInfo extends DataEntity<ClPrdOtherInfo>{
    private String prdCde;

    private String kindCde;

    private String kindName;

    private String sumAmount;

    private String businessNature;

    private String secureCde;

    private String secureName;

    private String channelCde;

    private String channelNam;

    private String handler1Cde;

    private String operatorCde;

    private String comCed;

    private String makeCom;

    private String crtDt;

    private String crtUsr;

    private String mdfDt;

    private String mdfUsr;

    public String getPrdCde() {
        return prdCde;
    }

    public void setPrdCde(String prdCde) {
        this.prdCde = prdCde == null ? null : prdCde.trim();
    }

    public String getKindCde() {
        return kindCde;
    }

    public void setKindCde(String kindCde) {
        this.kindCde = kindCde == null ? null : kindCde.trim();
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName == null ? null : kindName.trim();
    }

    public String getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(String sumAmount) {
        this.sumAmount = sumAmount == null ? null : sumAmount.trim();
    }

    public String getBusinessNature() {
        return businessNature;
    }

    public void setBusinessNature(String businessNature) {
        this.businessNature = businessNature == null ? null : businessNature.trim();
    }

    public String getSecureCde() {
        return secureCde;
    }

    public void setSecureCde(String secureCde) {
        this.secureCde = secureCde == null ? null : secureCde.trim();
    }

    public String getSecureName() {
        return secureName;
    }

    public void setSecureName(String secureName) {
        this.secureName = secureName == null ? null : secureName.trim();
    }

    public String getChannelCde() {
        return channelCde;
    }

    public void setChannelCde(String channelCde) {
        this.channelCde = channelCde == null ? null : channelCde.trim();
    }

    public String getChannelNam() {
        return channelNam;
    }

    public void setChannelNam(String channelNam) {
        this.channelNam = channelNam == null ? null : channelNam.trim();
    }

    public String getHandler1Cde() {
        return handler1Cde;
    }

    public void setHandler1Cde(String handler1Cde) {
        this.handler1Cde = handler1Cde == null ? null : handler1Cde.trim();
    }

    public String getOperatorCde() {
        return operatorCde;
    }

    public void setOperatorCde(String operatorCde) {
        this.operatorCde = operatorCde == null ? null : operatorCde.trim();
    }

    public String getComCed() {
        return comCed;
    }

    public void setComCed(String comCed) {
        this.comCed = comCed == null ? null : comCed.trim();
    }

    public String getMakeCom() {
        return makeCom;
    }

    public void setMakeCom(String makeCom) {
        this.makeCom = makeCom == null ? null : makeCom.trim();
    }

    public String getCrtDt() {
        return crtDt;
    }

    public void setCrtDt(String crtDt) {
        this.crtDt = crtDt == null ? null : crtDt.trim();
    }

    public String getCrtUsr() {
        return crtUsr;
    }

    public void setCrtUsr(String crtUsr) {
        this.crtUsr = crtUsr == null ? null : crtUsr.trim();
    }

    public String getMdfDt() {
        return mdfDt;
    }

    public void setMdfDt(String mdfDt) {
        this.mdfDt = mdfDt == null ? null : mdfDt.trim();
    }

    public String getMdfUsr() {
        return mdfUsr;
    }

    public void setMdfUsr(String mdfUsr) {
        this.mdfUsr = mdfUsr == null ? null : mdfUsr.trim();
    }
}