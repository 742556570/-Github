package com.jeeplus.modules.app.entity;

import java.util.Date;

import com.jeeplus.common.persistence.DataEntity;

public class ClPsnCHk extends DataEntity<ClPsnCHk>{
    /**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;

	private Integer mediaSeq;

    private String usrCde;

    private String trdNo;

    private String mediaPath;

    private String mediaPathLc;
    
    private String scores;

    private String mediaDesc;

    private String crtDt;

    private String ckinfo;

    public Integer getMediaSeq() {
        return mediaSeq;
    }

    public void setMediaSeq(Integer mediaSeq) {
        this.mediaSeq = mediaSeq;
    }

    public String getUsrCde() {
        return usrCde;
    }

    public void setUsrCde(String usrCde) {
        this.usrCde = usrCde == null ? null : usrCde.trim();
    }

    public String getTrdNo() {
        return trdNo;
    }

    public void setTrdNo(String trdNo) {
        this.trdNo = trdNo == null ? null : trdNo.trim();
    }

    public String getMediaPath() {
        return mediaPath;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath == null ? null : mediaPath.trim();
    }

    public String getMediaPathLc() {
        return mediaPathLc;
    }

    public void setMediaPathLc(String mediaPathLc) {
        this.mediaPathLc = mediaPathLc == null ? null : mediaPathLc.trim();
    }

    public String getMediaDesc() {
        return mediaDesc;
    }

    public void setMediaDesc(String mediaDesc) {
        this.mediaDesc = mediaDesc == null ? null : mediaDesc.trim();
    }

    public String getCrtDt() {
        return crtDt;
    }

    public void setCrtDt(String crtDt) {
        this.crtDt = crtDt;
    }

    public String getCkinfo() {
        return ckinfo;
    }

    public void setCkinfo(String ckinfo) {
        this.ckinfo = ckinfo == null ? null : ckinfo.trim();
    }

	public String getScores() {
		return scores;
	}

	public void setScores(String scores) {
		this.scores = scores;
	}
    
    
}