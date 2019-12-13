package com.jeeplus.modules.app.entity;

import com.jeeplus.common.persistence.DataEntity;

public class ClBlackList extends DataEntity<ClBlackList>{
    private Integer blSeq;

    private String blPhone;

    private String blIdcardno;
    
    private String blName;
    
    

    public Integer getBlSeq() {
        return blSeq;
    }

    public void setBlSeq(Integer blSeq) {
        this.blSeq = blSeq;
    }

    public String getBlPhone() {
        return blPhone;
    }

    public void setBlPhone(String blPhone) {
        this.blPhone = blPhone == null ? null : blPhone.trim();
    }

    public String getBlIdcardno() {
        return blIdcardno;
    }

    public void setBlIdcardno(String blIdcardno) {
        this.blIdcardno = blIdcardno == null ? null : blIdcardno.trim();
    }

	public String getBlName() {
		return blName;
	}

	public void setBlName(String blName) {
		this.blName = blName;
	}
    
    
}