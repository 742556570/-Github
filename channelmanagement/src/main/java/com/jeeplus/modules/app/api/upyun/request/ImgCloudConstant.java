package com.jeeplus.modules.app.api.upyun.request;

import com.jeeplus.modules.app.api.upyun.PropertiesUtil;

/**
 * 阳光保险
 * 云影像常量类
 * 将又拍云返回的路径及其他信息提交至云影像
 * 阳光保险集团外部系统与云影像系统对接-接口规范2.1.docx
 */

public class ImgCloudConstant {
    /**
     * 正式测试环境不一样，记得切换
     * 云影像服务器IP
     * 测试Ip:port: 10.8.199.188:8001
     * 生产Ip:port: 10.10.0.69:7001
     */
    private static final String IP = "http://10.8.199.188:8001";
    /**
     * 提交图片信息借口名
     */
    public static final String ADD_API = PropertiesUtil.getString("apiurl");
    /**
     * 传入标示	APP_CODE	VARCHAR(10)	传入标示（默认传CREDIT）
     */
    public static final String DEFAULT_APP_CODE = "CREDIT";

    /**
     * 影像操作人	CREATE_USER	VARCHAR(50)	影像上传操作人/影像删除操作人。
     * ACTION=’DELETE’ 时，传影像删除操作人。
     * ACTION=’ADD’ 时，传影像上传操作人。
     * 暂时默认穿空
     */
    public static final String DEFAULT_CREATE_USER = "";

    /**
     * 影像相关信息(PAGE，必填)
     * 文件类型	TYPE	VARCHAR(50)	01  图片 02  文件
     */
    public static final String PAGE_TYPE_IMG = "01";
    /**
     * 影像相关信息(PAGE，必填)
     * 文件类型	TYPE	VARCHAR(50)	01  图片 02  文件
     */
    public static final String PAGE_TYPE_FILE = "02";
    /**
     * 影像相关信息(PAGE，必填)
     * 是否删除	ACTION 	ADD  新增 DELETE    删除
     */
    public static final String PAGE_ACTION_ADD = "ADD";
  /**
     * 影像相关信息(PAGE，必填)
     * 是否删除	ACTION 	ADD  新增 DELETE    删除
     */
    public static final String PAGE_ACTION_DELETE = "DELETE";


    /**
     * 网贷  影像类型字典项
     * 详见影像下载接口.doc
     */
    public static class ImgDic {
        /**
         * 身份证明 id
         */
        public static final String ID_IDCARD = "CI01";
        /**
         * 身份证明 名称
         */
        public static final String NAME_IDCARD = "身份证明";
        /**
         * 人证 id
         */
        public static final String ID_WITNESSES = "CI02";
        /**
         * 人证 名称
         */
        public static final String NAME_WITNESSES = "人证";
        /**
         * 数据证书 id
         */
        public static final String ID_OTHER_CERTIFICATES = "CI03";
        /**
         * 数据证书  名称
         */
        public static final String NAME_OTHER_CERTIFICATES = "其他证明";
        /**
         * face++照片 id
         */
        public static final String ID_FACE_PP = "CI04";
        /**
         * face++照片 名称
         */
        public static final String NAME_FACE_PP = "face++照片";
        /**
         * 征信查询授权书 id
         */
        public static final String ID_CREDIT_INVESTIGATION = "CI06";
        /**
         * 征信查询授权书 名称
         */
        public static final String NAME_CREDIT_INVESTIGATION = "征信查询授权书";
        /**
         * 投保单 id
         */
        public static final String ID_INSURANCE = "CI05";
        /**
         * 投保单 名称
         */
        public static final String NAME_INSURANCE = "投保单";
        
        /**
         * 投保单 名称
         */
        public static final String NAME_PERSONQUERY = "个人信息查询使用授权书";
        /**
         * 保单 id
         */
        public static final String ID_PERSONQUERY = "CI08";
        /**
         * 保单 id
         */
        public static final String ID_POLICY = "CI12";
        
        
        /**
         * 保单 名称
         */
        public static final String NAME_POLICY = "保单";
        /**
         * 合同类
         */
        public static final String ID_CONTRACT = "CI11";
        /**
         * 合同类 名称
         */
        public static final String NAME_CONTRACT = "合同类";
        /**
         * 代扣授权委托书 id
         */
        public static final String ID_WITHHOLD = "CI13";
        /**
         * 代扣授权委托书 名称
         */
        public static final String NAME_WITHHOLD = "代扣授权委托书";

        /**
         * 其他 id
         */
        public static final String ID_OTHER = "CI07";
        /**
         * 其他 名称
         */
        public static final String NAME_OTHER = "其他";
    }
}
