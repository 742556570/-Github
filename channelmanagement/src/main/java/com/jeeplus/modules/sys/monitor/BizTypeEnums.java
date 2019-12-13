package com.jeeplus.modules.sys.monitor;

/**
 * 
 * <p>Title: 业务类型枚举</p>
 * <p>Description:</p>
 * @company Coffee-Ease
 * @author duanyuntao
 * @date 2017年7月3日
 * @time 下午4:50:41
 */
public enum BizTypeEnums {

    //APP进件
    APP_REGISITER("10000","/api/ex/member/register","注册"),
    APP_LOGIN("10001","/api/ex/member/login","登陆"),
    APP_VERIFY_OCR("10002","/api/member/realNameVerified","实名认证（OCR）"),
    APP_VERIFY("10003","","实名认证（公安部校验）"),
    APP_INFO_PERFECT("10004","/api/credit/fd/saveUserBaseInfo","资料完善"),
    APP_AUTH3("10005","","三联认证"),
    APP_AUTH2("10006","","二联认证"),
    APP_CARD_BIND("10007","/api/bankcard/submitNoAgree","银行卡绑定"),
    APP_CREDIT_APPLY("10008","/api/credit/fd/apply","进件申请"),
    APP_AUDIT_RESULT("10009","","审批结果"),
    APP_BATCH_BUCKLE("10010","","批量代扣"),
    APP_SELF_PAYMENT("10011","","主动还款"),
    APP_DECLINE("10012","","降班"),
    APP_REFUND_EDU("10013","","退费（机构退费）"),
    APP_REFUND_USER("10014","","退费（退费到学员银行卡）"),
    //PC进件开始  /a/creditapply
    PC_REGISITER("10000","/f/merchant/register","注册"),
    PC_LOGIN("10001","/a/login","登陆"),
    PC_VERIFY_OCR("10002","","实名认证（OCR）"),
    PC_VERIFY("10003","","实名认证（公安部校验）"),
    PC_INFO_PERFECT("10004","/a/creditapply/baseinfo","资料完善"),
    PC_AUTH3("10005","","三联认证"),
    PC_AUTH2("10006","","二联认证"),
    PC_CARD_BIND("10007","/a/creditapply","银行卡绑定"),
    PC_CREDIT_APPLY("10008","/a/creditapply/apply","进件申请"),
    PC_AUDIT_RESULT("10009","/a/creditapply/applyresult","审批结果"),
    PC_BATCH_BUCKLE("10010","","批量代扣"),
    PC_SELF_PAYMENT("10011","","主动还款"),
    PC_DECLINE("10012","","降班"),
    PC_REFUND_EDU("10013","","退费（机构退费）"),
    PC_REFUND_USER("10014","","退费（退费到学员银行卡）");
    
    /**
     * 业务编码
     */
    private String bizCode;
    /**
     * 业务URI
     */
    private String bizURI;
    /**
     * 业务描述
     */
    private String bizDescription;
    
    private BizTypeEnums(String bizCode, String bizURI, String bizDescription) {
        this.bizCode = bizCode;
        this.bizURI = bizURI;
        this.bizDescription = bizDescription;
    }
    
    public String getBizCode() {
        return bizCode;
    }
    public String getBizURI() {
        return bizURI;
    }
    public String getBizDescription() {
        return bizDescription;
    }
    
    public static String getBizCode(String bizURI){
        if("".equals(bizURI)){
            return null;
        }else{
            for (BizTypeEnums bizType : BizTypeEnums.values()) {
                if(bizURI.equals(bizType.getBizURI())){
                    return bizType.getBizCode();
                }
            }
        }
        return null;
    }
}
