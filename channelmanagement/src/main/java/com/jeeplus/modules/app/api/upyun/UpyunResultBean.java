package com.jeeplus.modules.app.api.upyun;

import java.io.Serializable;

/**
 * Created by zrb on 2017/12/19.
 * 又拍云 上传结果对象
 */

public class UpyunResultBean implements Serializable{
    /**
     * 状态码，200 为成功
     */
    public static final int CODE_SUCCESS = 200;

    /**
     * 图片格式  png/jpeg 等
     */
    private String imagetype;
    /**
     * 图片帧数
     */
    private int imageframes;
    /**
     * 图片高度
     */
    private int imageheight;
    /**
     * 状态码，200 为成功
     */
    private int code;
    /**
     * 文件大小
     */
    private int file_size;
    /**
     * 图片宽度
     */
    private int imagewidth;
    /**
     * 图片url
     */
    private String url;
    /**
     * 时间戳
     */
    private long time;
    /**
     * 结果信息
     */
    private String message;
    /**
     * 文件类型
     */
    private String mimetype;

    public String getImagetype() {
        return imagetype;
    }

    public void setImagetype(String imagetype) {
        this.imagetype = imagetype;
    }

    public int getImageframes() {
        return imageframes;
    }

    public void setImageframes(int imageframes) {
        this.imageframes = imageframes;
    }

    public int getImageheight() {
        return imageheight;
    }

    public void setImageheight(int imageheight) {
        this.imageheight = imageheight;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getFile_size() {
        return file_size;
    }

    public void setFile_size(int file_size) {
        this.file_size = file_size;
    }

    public int getImagewidth() {
        return imagewidth;
    }

    public void setImagewidth(int imagewidth) {
        this.imagewidth = imagewidth;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }
}
