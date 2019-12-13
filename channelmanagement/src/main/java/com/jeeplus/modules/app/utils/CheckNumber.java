package com.jeeplus.modules.app.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jeeplus.common.utils.StringUtils;

/**
 * @author xy
 * @data
 */

public class CheckNumber {

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
    /*
	    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
	    联通：130、131、132、152、155、156、185、186
	    电信：133、153、180、189、（1349卫通）
	    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        //"[1]"代表第1位为数字1，"[3456789]"代表第二位可以为3、4、5、6、7、8、9中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex = "[1][3456789]\\d{9}";
        if (StringUtils.isEmpty(mobiles)) {
            return false;
        } else {
            return mobiles.matches(telRegex);
        }
    }

    public static boolean isMobileNum(String mobileNum){
        /**
         * 中国移动：China Mobile
         中国移动获得了198（0-9）
         * 134,135,136,137,138,139,147(数据卡),150,151,152,157,158,159,178,182,183,184,187,188
         */
        String CM_NUM = "^((198)|(13[4-9])|(147)|(15[0-2,7-9])|(17[8])|(18[2-4,7-8]))\\d{8}$";
        /**
         * 中国联通：China Unicom
         中国联通获得了166（0-9）号段（公众移动通信网网号）
         * 130,131,132,145(数据卡),155,156,175,176,185,186
         */
        String CU_NUM = "^((166)|(13[0-2])|(145)|(15[5-6])|(17[5,6])|(18[5,6]))\\d{8}$";
        /**
         * 中国电信：China Telecom
         * 133,149(数据卡),153,170[0-2],173,177,180,181,189
         中国电信获得了199（0-9）号段（公众移动通信网网号）
         */
        String CT_NUM = "^((199)|(133)|(149)|(153)|(17[3,7])|(18[0,1,9]))\\d{8}$";

        boolean isCm = matches(CM_NUM, mobileNum);
        boolean isCu = matches(CU_NUM, mobileNum);
        boolean isCt = matches(CT_NUM, mobileNum);

        if(isCm || isCu || isCt){
            return true;
        }

        return false;
    }

    private static boolean matches(String regular, String matcher){
        Pattern pattern = Pattern.compile(regular);
        Matcher m = pattern.matcher(matcher);
        return m.matches();
    }


    static final Pattern pattern = Pattern.compile("^[1-9]\\d{14}");
    static final Pattern pattern2 = Pattern.compile("^[1-9]\\d{16}[\\d,x,X]$");
    /**
     * 身份证号码验证
     * 不为空时，验证str是否为正确的身份证格式
     * @return
     */
    public static boolean maybeIsIdentityCard(String idCardNo) {
        boolean flag = true;
        String licenc = idCardNo;
        if (!licenc.equals("")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            /*
             * { 11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",
             * 21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",
             * 33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",
             * 42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",
             * 51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",
             * 63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外" }
             */
            String provinces = "11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65,71,81,82,91";

            Matcher matcher = pattern.matcher(licenc);
            Matcher matcher2 = pattern2.matcher(licenc);
            // 粗略判断
            if (!matcher.find() && !matcher2.find()) {
//                view.setError("身份证号必须为15或18位数字（最后一位可以为X）");
                flag = false;
            } else {
                // 判断出生地
                if (provinces.indexOf(licenc.substring(0, 2)) == -1) {
//                    view.setError("身份证号前两位不正确！");
                    flag = false;
                }

                // 判断出生日期
                if (licenc.length() == 15) {
                    String birth = "19" + licenc.substring(6, 8) + "-"
                            + licenc.substring(8, 10) + "-"
                            + licenc.substring(10, 12);
                    try {
                        Date birthday = sdf.parse(birth);
                        if (!sdf.format(birthday).equals(birth)) {
//                            view.setError("出生日期非法！");
                            flag = false;
                        }
                        if (birthday.after(new Date())) {
//                            view.setError("出生日期不能在今天之后！");
                            flag = false;
                        }
                    } catch (ParseException e) {
//                        view.setError("出生日期非法！");
                        flag = false;
                    }
                } else if (licenc.length() == 18) {
                    String birth = licenc.substring(6, 10) + "-"
                            + licenc.substring(10, 12) + "-"
                            + licenc.substring(12, 14);
                    try {
                        Date birthday = sdf.parse(birth);
                        if (!sdf.format(birthday).equals(birth)) {
//                            view.setError("出生日期非法！");
                            flag = false;
                        }
                        if (birthday.after(new Date())) {
//                            view.setError("出生日期不能在今天之后！");
                            flag = false;
                        }
                    } catch (ParseException e) {
//                        view.setError("出生日期非法！");
                        flag = false;
                    }
                } else {
//                    view.setError("身份证号位数不正确，请确认！");
                    flag = false;
                }
            }
        }
        return flag;
    }
}
