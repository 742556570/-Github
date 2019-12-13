package com.jeeplus.modules.app.api.pm;


import javax.xml.namespace.QName;



import org.apache.axis.client.Call;  
import org.apache.axis.client.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.modules.app.api.fcpp.Verify2;
import com.jeeplus.modules.app.api.product.PropertiesUtil;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.encoding.XMLType;


public class PMApi {
	private final static Logger logger = LoggerFactory.getLogger(PMApi.class);
	
	public String callPM(String reqXML, String reqUrl, String reqNamespace,String reqMethod,String reqActionUri, String reqParamName) throws Exception {
		Service service = new Service(); 
		Call call = (Call) service.createCall();  
		call.setTargetEndpointAddress(new java.net.URL(reqUrl)); 
		call.setUseSOAPAction(true);  
		call.setSOAPActionURI(reqNamespace + reqActionUri); // action uri  
		call.setOperationName(new QName(reqNamespace, reqMethod));
		call.addParameter(new QName(reqNamespace, reqParamName), XMLType.XSD_STRING, ParameterMode.IN);
		call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
		Object[] params = new Object[] {reqXML};
		String result = (String) call.invoke(params);
		logger.info("PM参数:"+reqXML);
		logger.info("PM结果:"+result);
		return result;
	}
	
	
	
	public String preCredExt(String reqXml) {
		String url = PropertiesUtil.getString("pm.url");
		String namespace = PropertiesUtil.getString("pm.namespace"); 
		String actionUri = PropertiesUtil.getString("pm.actionUri"); 
		String op = PropertiesUtil.getString("pm.op");;
		String paramName = PropertiesUtil.getString("pm.paramName");;
		String result = "";
		try {
			result = callPM(reqXml,url,namespace,op,actionUri,paramName);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result = "";
		}
		logger.info("PM参数:"+reqXml);
		logger.info("PM结果:"+result);
		return result;
	}
	
	
	
	
	public String test() throws RemoteException, ServiceException, MalformedURLException{

//		String wdStr = "{\"code\":\"ok\",\"data\":{\"wd_jxl_honey_pot_report\":{\"_id\":\"20180411171010088cd9eeb9f\",\"be_is_familiar\":4,\"be_max\":75.33,\"be_mean\":47.79,\"be_median_familiar\":26,\"be_min\":19.17,\"be_not_familiar\":32,\"black_ratio\":0,\"blacklist_name_with_idcard\":false,\"blacklist_name_with_phone\":false,\"blacklist_update_time_name_idcard\":\"\",\"blacklist_update_time_name_phone\":\"\",\"call_cnt_be_all\":337,\"call_cnt_be_applied\":45,\"call_cnt_be_black\":-99,\"call_cnt_to_all\":448,\"call_cnt_to_applied\":59,\"call_cnt_to_black\":-99,\"channel\":\"ygxd_app\",\"cnt_all\":94,\"cnt_applied\":11,\"cnt_be_all\":62,\"cnt_be_applied\":8,\"cnt_be_black\":0,\"cnt_black\":0,\"cnt_black2\":329,\"cnt_router\":19,\"cnt_to_all\":66,\"cnt_to_applied\":9,\"cnt_to_black\":0,\"event_time\":\"2018-04-11 17:09:54.337\",\"finance_app_cnt\":-99,\"id_no\":\"610303199008230442\",\"if_fin_buy_pre6\":1,\"if_own_car\":0,\"if_pay_ins\":0,\"is_familiar\":7,\"loan_app_cnt\":-99,\"loan_number\":\"WD1016901804111709Y\",\"loan_or_finance_app_cnt\":-99,\"max\":75.33,\"mean\":48.42,\"median_familiar\":24,\"min\":11.55,\"mobile\":\"15927028569\",\"most_familiar_all\":33.25,\"most_familiar_applied\":61.2,\"most_familiar_be_all\":33.25,\"most_familiar_be_applied\":19.17,\"most_familiar_to_all\":33.25,\"most_familiar_to_applied\":61.2,\"name\":\"楼洁\",\"not_familiar\":63,\"phone_gray_score\":32.13,\"query_cnt180d\":0,\"query_cnt30d\":0,\"query_cnt360d\":0,\"query_cnt7d\":0,\"query_cnt90d\":0,\"query_org_cnt180d\":0,\"query_org_cnt30d\":0,\"query_org_cnt360d\":0,\"query_org_cnt7d\":0,\"query_org_cnt90d\":0,\"recent_time_be_all\":{\"$numberLong\":\"1460508340000\"},\"recent_time_be_applied\":-17407640,\"recent_time_be_black\":-99,\"recent_time_to_all\":{\"$numberLong\":\"1460460253000\"},\"recent_time_to_applied\":-107121640,\"recent_time_to_black\":-99,\"register_app_cnt\":-99,\"router_ratio\":0.20212767,\"social_influence\":97.21,\"social_liveness\":98.55,\"time_spent_be_all\":33133,\"time_spent_be_applied\":3312,\"time_spent_be_black\":-99,\"time_spent_to_all\":33571,\"time_spent_to_applied\":3253,\"time_spent_to_black\":-99,\"to_is_familiar\":16,\"to_max\":69.7,\"to_mean\":48.89,\"to_median_familiar\":39,\"to_min\":11.55,\"to_not_familiar\":11,\"ts\":\"2018-04-11 17:10:10.000\",\"use_idno_other_mobile_cnt\":1,\"use_idno_other_name_cnt\":0,\"use_mobile_other_idno_cnt\":1,\"use_mobile_other_name_cnt\":1,\"user_city\":\"宝鸡市\",\"user_id\":\"167996385512128512\",\"user_phone_city\":\"武汉\",\"user_phone_operator\":\"中国移动\",\"user_phone_province\":\"湖北\",\"user_province\":\"新疆\",\"user_region\":\"金台区\",\"weight_all\":196.74,\"weight_applied\":150.28,\"weight_be_all\":98.36,\"weight_be_applied\":61.53,\"weight_be_black\":-99,\"weight_black\":-99,\"weight_to_all\":98.38,\"weight_to_applied\":93.1,\"weight_to_black\":-99},\"wd_py_mobile_info\":{\"_id\":\"20180411171010102f614e409\",\"channel\":\"ygxd_app\",\"event_time\":\"2018-04-11 17:09:54.362\",\"id_no\":\"610303199008230442\",\"loan_number\":\"WD1016901804111709Y\",\"mobile\":\"15927028569\",\"mobile_check_result\":\"-99\",\"mobile_fee_level\":\"-99\",\"mobile_member_level\":\"-99\",\"mobile_online_time_length\":\"-99\",\"name\":\"楼洁\",\"ts\":\"2018-04-11 17:10:10.000\",\"user_id\":\"167996385512128512\"},\"wd_td_rule_detail\":{\"_id\":\"20180411171010198ca5d7a66\",\"addressHitXinLoanYuQiPayList\":false,\"applicantLendflatCnt12m\":-99,\"applicantLendflatCnt1m\":-99,\"applicantLendflatCnt3m\":-99,\"applicantLendflatCnt6m\":-99,\"applicantLendflatCnt7d\":-99,\"applyInfoAssoManyIdNoCnt3m\":-99,\"channel\":\"ygxd_app\",\"event_time\":\"2018-04-11 17:09:53.940\",\"familyHitXinLoanYuQiList\":false,\"friendHitXinLoanYuQiList\":false,\"idNoAssoManyApplyInfoCnt3m\":-99,\"idNoHitCourtActionNamelist\":false,\"idNoHitCourtEndCaseNamelist\":false,\"idNoHitCourtShixinNamelist\":false,\"idNoHitCrimeNamelist\":false,\"idNoHitDebitfirmNamelist\":false,\"idNoHitOweTaxNamelist\":false,\"idNoHitXinLoanYuQiList\":false,\"idNoNameHitCourtActionBlurlist\":false,\"idNoNameHitCourtEndCaseBlurlist\":false,\"idNoNameHitCourtShixinBlurlist\":false,\"idNoNameHittLoanYuQiBlurlist\":false,\"idNoZhuxueLoanArrearage\":false,\"id_no\":\"610303199008230442\",\"loanDeviceProxyRecog\":false,\"loan_number\":\"WD1016901804111709Y\",\"mobile\":\"15927028569\",\"mobileHitCommuLib\":false,\"mobileHitShamLib\":false,\"mobileHitXinLoanYuQiList\":false,\"mobileRegexError\":false,\"name\":\"楼洁\",\"ts\":\"2018-04-11 17:10:10.000\",\"user_id\":\"167996385512128512\"}},\"msg\":\"成功\",\"time\":\"2018-04-11 17:13:54.507\"}";
		
		
		
		String inputParam = TestString.xml;
		Service service = new Service();  
		String url = "http://10.7.128.142/ProcessManager.WebService.Online.SIGCN/ProcessManager.asmx";  //URL地址
		String namespace = "http://tempuri.org/"; 
		String actionUri = "CallProcessManager"; //Action路径  
		String op = "CallProcessManager"; //要调用的方法名  
		Call call = (Call) service.createCall();  
		call.setTargetEndpointAddress(new java.net.URL(url));  
		call.setUseSOAPAction(true);  
		call.setSOAPActionURI(namespace + actionUri); // action uri  
		call.setOperationName(new QName(namespace, op));// 设置要调用哪个方法  
		// 设置参数名称，具体参照从浏览器中看到的  
		call.addParameter(new QName(namespace, "applicationXML"), XMLType.XSD_STRING, ParameterMode.IN);  //设置请求参数及类型
		//call.setReturnType(new QName(namespace,"getinfo"),Model.class); //设置返回结果为是某个类
		call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置结果返回类型
		Object[] params = new Object[] {inputParam};  
		String result = (String) call.invoke(params); //方法执行后的返回值  
//		System.out.println(result);
		return result;
		}
	
	
	public static void main(String[] args) throws Exception {
		System.out.println(new PMApi().preCredExt(TestString.xml));
		
	}
}
