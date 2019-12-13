package com.jeeplus.modules.app.entity;

import java.util.Date;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.StringUtils;

public class ChDonationInfo extends DataEntity<ChDonationInfo>{
	
    private Integer doSeq;

    private String doCde;

    private String doName;

    private String doType;

    private String doBGSWLPTQC;
    
    private String doBGSWLPTJC;
    
    private String doBGSWLPTWZDZ;

    private String doHZBXJGMC;

    private String doBGSWLPTBAXX;
    
    private String doWLPTBASJ;

    private String doYWHZFW;

    private String doYWHZQSSJ;

    private String doYWHZZZSJ;

    private String status;

    private String doLink;

    

    public Integer getDolSeq() {
        return doSeq;
    }

    public void setDolSeq(Integer doSeq) {
        this.doSeq = doSeq;
    }
    
    public String getDoCde() {
        return doCde;
    }

    public void setDoCde(String doCde) {
        this.doCde = doCde;
    }
    
    public String getDoName() {
        return doName;
    }

    public void setDoName(String doName) {
        this.doName = doName;
    }
	
    
    public String getDoType() {
        return doType;
    }

    public void setDoType(String doType) {
        this.doType = doType;
    }
    
    
    public String getDoBGSWLPTQC() {
        return doBGSWLPTQC;
    }

    public void setDoBGSWLPTQC(String doBGSWLPTQC) {
        this.doBGSWLPTQC = doBGSWLPTQC;
    }
    
    
    public String getDoBGSWLPTJC() {
        return doBGSWLPTJC;
    }

    public void setDoBGSWLPTJC(String doBGSWLPTJC) {
        this.doBGSWLPTJC = doBGSWLPTJC;
    }
	
    
    public String getDoBGSWLPTWZDZ() {
        return doBGSWLPTWZDZ;
    }

    public void setDoBGSWLPTWZDZ(String doBGSWLPTWZDZ) {
        this.doBGSWLPTWZDZ = doBGSWLPTWZDZ;
    }
    
    public String getDoHZBXJGMC() {
        return doHZBXJGMC;
    }

    public void setDoHZBXJGMC(String doHZBXJGMC) {
        this.doHZBXJGMC = doHZBXJGMC;
    }
    
    public String getDoBGSWLPTBAXX() {
        return doBGSWLPTBAXX;
    }

    public void setDoBGSWLPTBAXX(String doBGSWLPTBAXX) {
        this.doBGSWLPTBAXX = doBGSWLPTBAXX;
    }
    
    public String getDoWLPTBASJ() {
        return doWLPTBASJ;
    }

    public void setDoWLPTBASJ(String doWLPTBASJ) {
        this.doWLPTBASJ = doWLPTBASJ;
    }

    public String getDoYWHZFW() {
        return doYWHZFW;
    }

    public void setDoYWHZFW(String doYWHZFW) {
        this.doYWHZFW = doYWHZFW;
    }
    
    public String getDoYWHZQSSJ() {
        return doYWHZQSSJ;
    }

    public void setDoYWHZQSSJ(String doYWHZQSSJ) {
        this.doYWHZQSSJ = doYWHZQSSJ;
    }
    
    public String getDoYWHZZZSJ() {
        return doYWHZZZSJ;
    }

    public void setDoYWHZZZSJ(String doYWHZZZSJ) {
        this.doYWHZZZSJ = doYWHZZZSJ;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getDoLink() {
        return doLink;
    }

    public void setDoLink(String doLink) {
        this.doLink = doLink;
    }
    
}