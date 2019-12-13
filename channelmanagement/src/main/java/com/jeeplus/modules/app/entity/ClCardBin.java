package com.jeeplus.modules.app.entity;

import java.util.Date;

import com.jeeplus.common.persistence.DataEntity;

public class ClCardBin extends DataEntity<ClCardBin>  {
    private String cardbank;

    private String orgcode;

    private String cardname;

    private String cardabb;

    private String cardbin;

    private Integer cardtype;

    private Date creatdate;

    private Date updatedate;

    public String getCardbank() {
        return cardbank;
    }

    public void setCardbank(String cardbank) {
        this.cardbank = cardbank == null ? null : cardbank.trim();
    }

    public String getOrgcode() {
        return orgcode;
    }

    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode == null ? null : orgcode.trim();
    }

    public String getCardname() {
        return cardname;
    }

    public void setCardname(String cardname) {
        this.cardname = cardname == null ? null : cardname.trim();
    }

    public String getCardabb() {
        return cardabb;
    }

    public void setCardabb(String cardabb) {
        this.cardabb = cardabb == null ? null : cardabb.trim();
    }

    public String getCardbin() {
        return cardbin;
    }

    public void setCardbin(String cardbin) {
        this.cardbin = cardbin == null ? null : cardbin.trim();
    }

    public Integer getCardtype() {
        return cardtype;
    }

    public void setCardtype(Integer cardtype) {
        this.cardtype = cardtype;
    }

    public Date getCreatdate() {
        return creatdate;
    }

    public void setCreatdate(Date creatdate) {
        this.creatdate = creatdate;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }
}