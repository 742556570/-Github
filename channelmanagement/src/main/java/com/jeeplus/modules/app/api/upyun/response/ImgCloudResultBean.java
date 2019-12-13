package com.jeeplus.modules.app.api.upyun.response;

import java.io.Serializable;

/**
 * 阳光保险
 * 云影像提交结果返回对象
 * 将又拍云返回的路径及其他信息提交至云影像
 * 阳光保险集团外部系统与云影像系统对接-接口规范2.1.docx
 */

public class ImgCloudResultBean implements Serializable {
    /**
     * 成功标示	HANDLECODE	VARCHAR(100)	1  成功0  失败
     */
    public static final String HANDLECODE_SUCCESS = "1";
    /**
     * 成功标示	HANDLECODE	VARCHAR(100)	1  成功0  失败
     */
    public static final String HANDLECODE_FAIL = "0";
    //    申请流水号	APPLYCODE	VARCHAR(100)	影像类型
    private String APPLYCODE;
    //    成功标示	HANDLECODE	VARCHAR(100)	1  成功0  失败
    public  String HANDLECODE;
    //    错误描述	ERRORMESSAGE	VARCHAR(2048)	上传成功/错误信息
    public  String ERRORMESSAGE;

    public ImgCloudResultBean(String APPLYCODE, String HANDLECODE, String ERRORMESSAGE) {
        this.APPLYCODE = APPLYCODE;
        this.HANDLECODE = HANDLECODE;
        this.ERRORMESSAGE = ERRORMESSAGE;
    }

    public String getAPPLYCODE() {
        return APPLYCODE;
    }

    public void setAPPLYCODE(String APPLYCODE) {
        this.APPLYCODE = APPLYCODE;
    }

    public String getHANDLECODE() {
        return HANDLECODE;
    }

    public void setHANDLECODE(String HANDLECODE) {
        this.HANDLECODE = HANDLECODE;
    }

    public String getERRORMESSAGE() {
        return ERRORMESSAGE;
    }

    public void setERRORMESSAGE(String ERRORMESSAGE) {
        this.ERRORMESSAGE = ERRORMESSAGE;
    }
}
