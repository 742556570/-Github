package com.jeeplus.modules.app.entity;

import java.util.Date;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.StringUtils;

public class ChUsrApply extends DataEntity<ChUsrApply>{
	
    private Integer applSeq;

    private String applCde;

    private String channelCde;

    private String channelName;

    private String custCityCde;
    
    private String custCityName;
    
    private String usrCde;

    private String custName;

    private String custTel;
    
    private String custChannelCde;

    private String custChannelName;

    private String custSecChannelCde;

    private String custSecChannelName;

    private String crtDt;

    private String custProductCde;

    private String custProductName;
    
    private String custSecProductCde;
    
    private String custSecProductName;
    
    private Date dxDate;
    
    private String dxStatus;
    
    private String ygyjUsrCde;

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

    public String getChannelCde() {
        return channelCde;
    }

    public void setChannelCde(String channelCde) {
        this.channelCde = channelCde == null ? null : channelCde.trim();
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName == null ? null : channelName.trim();
    }

    public String getCustCityCde() {
        return custCityCde;
    }

    public void setCustCityCde(String custCityCde) {
        this.custCityCde = custCityCde == null ? null : custCityCde.trim();
    }

    public String getCustCityName() {
        return custCityName;
    }

    public void setCustCityName(String custCityName) {
        this.custCityName = custCityName == null ? null : custCityName.trim();
    }

    public String getUsrCde() {
        return usrCde;
    }

    public void setUsrCde(String usrCde) {
        this.usrCde = usrCde == null ? null : usrCde.trim();
    }
    
    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName == null ? null : custName.trim();
    }

    public String getCrtDt() {
        return crtDt;
    }

    public void setCrtDt(String crtDt) {
        this.crtDt = crtDt;
    }

    public String getCustTel() {
        return custTel;
    }

    public void setCustTel(String custTel) {
        this.custTel = custTel == null ? null : custTel.trim();
    }

    public String getCustChannelCde() {
        return custChannelCde;
    }

    public void setCustChannelCde(String custChannelCde) {
        this.custChannelCde = custChannelCde == null ? null : custChannelCde.trim();
    }

    public String getCustChannelName() {
        return custChannelName;
    }

    public void setCustChannelName(String custChannelName) {
        this.custChannelName = custChannelName == null ? null : custChannelName.trim();
    }

    public String getCustSecChannelCde() {
        return custSecChannelCde;
    }

    public void setCustSecChannelCde(String custSecChannelCde) {
        this.custSecChannelCde = custSecChannelCde == null ? null : custSecChannelCde.trim();
    }

    public String getCustSecChannelName() {
        return custSecChannelName;
    }

    public void setCustSecChannelName(String custSecChannelName) {
        this.custSecChannelName = custSecChannelName == null ? null : custSecChannelName.trim();
    }

    public String getCustProductCde() {
        return custProductCde;
    }

    public void setCustProductCde(String custProductCde) {
        this.custProductCde = custProductCde;
    }

    public String getCustProductName() {
        return custProductName;
    }

    public void setCustProductName(String custProductName) {
        this.custProductName = custProductName == null ? null : custProductName.trim();
    }

    public String getCustSecProductCde() {
        return custSecProductCde;
    }

    public void setCustSecProductCde(String custSecProductCde) {
        this.custSecProductCde = custSecProductCde == null ? null : custSecProductCde.trim();
    }

    public String getCustSecProductName() {
        return custSecProductName;
    }

    public void setCustSecProductName(String custSecProductName) {
        this.custSecProductName = custSecProductName == null ? null : custSecProductName.trim();
    }

	public Date getDxDate() {
		return dxDate;
	}

	public void setDxDate(Date dxDate) {
		this.dxDate = dxDate;
	}

	public String getYgyjUsrCde() {
		return ygyjUsrCde;
	}

	public void setYgyjUsrCde(String ygyjUsrCde) {
		this.ygyjUsrCde = ygyjUsrCde;
	}

	public String getDxStatus() {
		return dxStatus;
	}

	public void setDxStatus(String dxStatus) {
		this.dxStatus = dxStatus;
	}

	
	

    
}