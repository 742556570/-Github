package com.jeeplus.modules.app.api.pm;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.app.entity.ClCapChannel;
import com.jeeplus.modules.app.entity.ClIdCardInfo;
import com.jeeplus.modules.app.entity.ClPrdInfo;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.entity.ClUsrApply;
import com.jeeplus.modules.app.entity.ClUsrInfo;
import com.jeeplus.modules.app.utils.AgeUtils;

public class PMXmlStringUtils2 {
	
	static String inJson = "{\"code\":\"ok\",\"data\":{\"wd_anti_spoofing\":{\"_id\":\"201803281458501645aa6854b\",\"channel\":\"wdapp\",\"event_time\":\"2018-03-28 14:58:39.353\",\"face_score\":\"86.227\",\"fail_times\":\"0\",\"id_no\":\"370403199106226629\",\"is_attack\":\"否\",\"mask_times\":\"0\",\"mobile\":\"13500000001\",\"name\":\"黄明乾\",\"screen_times\":\"0\",\"software_times\":\"0\",\"ts\":\"2018-03-28 14:58:50.000\",\"user_id\":\"163662154510106624\"},\"wd_apply_submit\":{\"_id\":\"201803291134034862142b6ad\",\"apply_divice_cnt\":0,\"apply_divice_cnt_1d\":0,\"apply_divice_cnt_6m\":0,\"channel\":\"wdapp\",\"current_divice_apply_cnt\":0,\"event_time\":\"1970-01-02 02:11:04.459\",\"id_no\":\"370403199106226629\",\"if_use_simulator\":\"否\",\"install_finance_app_cnt\":0,\"install_loan_app_cnt\":0,\"install_lottery_app_cnt\":0,\"install_shares_app_cnt\":0,\"install_virtual_app_cnt\":0,\"loan_number\":\"WD1938501803291128W\",\"mobile\":\"13500000001\",\"name\":\"黄明乾\",\"same_gps_apply_cnt\":0,\"same_gps_apply_cnt_1d\":0,\"same_gps_apply_cnt_6m\":0,\"same_ip_apply_cnt\":0,\"same_ip_apply_cnt_1d\":0,\"same_ip_apply_cnt_6m\":0,\"same_wfmac_apply_cnt\":0,\"same_wfmac_apply_cnt_1d\":0,\"same_wfmac_apply_cnt_6m\":0,\"ts\":\"2018-03-29 11:34:00.000\",\"user_id\":\"163662154510106624\"},\"wd_bank_match_blacklist\":{\"_id\":\"20180328145920096184ba1cc\",\"bank_card\":\"6226220126775542\",\"bank_mobile\":\"13500000001\",\"channel\":\"wdapp\",\"event_time\":\"2018-03-28 14:59:11.810\",\"id_no\":\"370403199106226629\",\"loan_number\":\"\",\"mobile\":\"13500000001\",\"ts\":\"2018-03-28 14:59:20.000\",\"user_id\":\"163662154510106624\"},\"wd_career\":{\"_id\":\"2018032815004023518574ce5\",\"channel\":\"wdapp\",\"company_if_hit_ban_list\":false,\"event_time\":\"2018-03-28 15:00:27.039\",\"gps_city\":\"北京市\",\"gps_province\":\"北京市\",\"house_city\":\"天津市\",\"house_city_grade\":\"一线城市\",\"id_no\":\"370403199106226629\",\"mobile\":\"13500000001\",\"name\":\"黄明乾\",\"same_company_apply_cnt\":0,\"same_company_apply_cnt_1d\":0,\"same_company_apply_cnt_6m\":0,\"ts\":\"2018-03-28 15:00:40.000\",\"user_id\":\"163662154510106624\",\"work_city\":\"北京市\",\"work_city_grade\":\"一线城市\"},\"wd_career_match_blacklist\":{\"_id\":\"201803281500402595819e8db\",\"channel\":\"wdapp\",\"event_time\":\"2018-03-28 15:00:27.039\",\"home_address\":\"天津-天津市-和平区海淀区\",\"id_no\":\"370403199106226629\",\"loan_number\":\"\",\"mobile\":\"13500000001\",\"ts\":\"2018-03-28 15:00:40.000\",\"user_id\":\"163662154510106624\",\"work_address\":\"北京-北京市-东城区朝阳区\"},\"wd_contact_match_blacklist\":{\"_id\":\"2018032814595893572a0bb3\",\"channel\":\"wdapp\",\"emergency_contact\":\"185-0177-8899,+86 135 2185 6456\",\"event_time\":\"2018-03-28 14:59:34.365\",\"id_no\":\"370403199106226629\",\"loan_number\":\"\",\"mobile\":\"13500000001\",\"ts\":\"2018-03-28 14:59:40.000\",\"user_id\":\"163662154510106624\"},\"wd_inner_contact\":{\"_id\":\"20180328145958924267ed6ba\",\"applicant_assoApply_if_highRisk\":false,\"applicant_contact_assoApply_if_highRisk\":false,\"applicant_contact_if_apply\":false,\"applicant_contact_if_asContact_apply\":false,\"applicant_contact_if_mutual_contact\":false,\"applicant_if_asContact_apply\":false,\"channel\":\"wdapp\",\"event_time\":\"2018-03-28 14:59:34.365\",\"friend_mobile\":\"+86 135 2185 6456\",\"id_no\":\"370403199106226629\",\"intimate_mobile\":\"185-0177-8899\",\"mobile\":\"13500000001\",\"name\":\"黄明乾\",\"ts\":\"2018-03-28 14:59:40.000\",\"user_id\":\"163662154510106624\"},\"wd_jxl_honey_pot_report\":{\"_id\":\"20180329113402281ddaecb8\",\"be_is_familiar\":0,\"be_max\":-99,\"be_mean\":-99,\"be_median_familiar\":0,\"be_min\":-99,\"be_not_familiar\":0,\"black_ratio\":0,\"blacklist_name_with_idcard\":false,\"blacklist_name_with_phone\":false,\"blacklist_update_time_name_idcard\":\"\",\"blacklist_update_time_name_phone\":\"\",\"call_cnt_be_all\":-99,\"call_cnt_be_applied\":-99,\"call_cnt_be_black\":-99,\"call_cnt_to_all\":1,\"call_cnt_to_applied\":1,\"call_cnt_to_black\":-99,\"channel\":\"ygxd_app\",\"cnt_all\":1,\"cnt_applied\":1,\"cnt_be_all\":0,\"cnt_be_applied\":0,\"cnt_be_black\":0,\"cnt_black\":0,\"cnt_black2\":0,\"cnt_router\":0,\"cnt_to_all\":1,\"cnt_to_applied\":1,\"cnt_to_black\":0,\"event_time\":\"2018-03-29 11:28:28.272\",\"finance_app_cnt\":2,\"id_no\":\"370403199106226629\",\"if_fin_buy_pre6\":1,\"if_own_car\":0,\"if_pay_ins\":0,\"is_familiar\":0,\"loan_app_cnt\":2,\"loan_number\":\"WD1938501803291128W\",\"loan_or_finance_app_cnt\":2,\"max\":48.84,\"mean\":48.84,\"median_familiar\":0,\"min\":48.84,\"mobile\":\"13500000001\",\"most_familiar_all\":48.84,\"most_familiar_applied\":48.84,\"most_familiar_be_all\":-99,\"most_familiar_be_applied\":-99,\"most_familiar_to_all\":48.84,\"most_familiar_to_applied\":48.84,\"name\":\"黄明乾\",\"not_familiar\":1,\"phone_gray_score\":72.87,\"query_cnt180d\":0,\"query_cnt30d\":0,\"query_cnt360d\":0,\"query_cnt7d\":0,\"query_cnt90d\":0,\"query_org_cnt180d\":0,\"query_org_cnt30d\":0,\"query_org_cnt360d\":0,\"query_org_cnt7d\":0,\"query_org_cnt90d\":0,\"recent_time_be_all\":-99,\"recent_time_be_applied\":-99,\"recent_time_be_black\":-99,\"recent_time_to_all\":{\"$numberLong\":\"1473503300000\"},\"recent_time_to_applied\":329517472,\"recent_time_to_black\":-99,\"register_app_cnt\":6,\"router_ratio\":0,\"social_influence\":0,\"social_liveness\":38.68,\"time_spent_be_all\":-99,\"time_spent_be_applied\":-99,\"time_spent_be_black\":-99,\"time_spent_to_all\":554,\"time_spent_to_applied\":554,\"time_spent_to_black\":-99,\"to_is_familiar\":0,\"to_max\":48.84,\"to_mean\":48.84,\"to_median_familiar\":1,\"to_min\":48.84,\"to_not_familiar\":0,\"ts\":\"2018-03-29 11:34:00.000\",\"use_idno_other_mobile_cnt\":1,\"use_idno_other_name_cnt\":0,\"use_mobile_other_idno_cnt\":20,\"use_mobile_other_name_cnt\":19,\"user_city\":\"枣庄市\",\"user_id\":\"163662154510106624\",\"user_phone_city\":\"广州\",\"user_phone_operator\":\"中国移动\",\"user_phone_province\":\"广东\",\"user_province\":\"山东省\",\"user_region\":\"薛城区\",\"weight_all\":62.59,\"weight_applied\":62.59,\"weight_be_all\":-99,\"weight_be_applied\":-99,\"weight_be_black\":-99,\"weight_black\":-99,\"weight_to_all\":62.59,\"weight_to_applied\":62.59,\"weight_to_black\":-99},\"wd_login\":{\"_id\":\"201803281837509875faa896d\",\"apply_divice_cnt\":0,\"apply_divice_cnt_1d\":0,\"apply_divice_cnt_6m\":0,\"channel\":\"wdapp\",\"deviceId\":\"0bd68a1d-43a4-3c67-9be4-df0108f3dedf\",\"event_time\":\"1970-01-01 09:20:15.278\",\"id_no\":\"370403199106226629\",\"ip\":\"10.63.107.111\",\"loan_number\":\"\",\"mobile\":\"13500000001\",\"ts\":\"2018-03-28 18:37:50.000\",\"user_id\":\"163662154510106624\"},\"wd_login_match_blacklist\":{\"_id\":\"2018032818375104963ba791c\",\"channel\":\"wdapp\",\"deviceId\":\"0bd68a1d-43a4-3c67-9be4-df0108f3dedf\",\"event_time\":\"1970-01-01 09:20:15.278\",\"id_no\":\"370403199106226629\",\"ip\":\"10.63.107.111\",\"loan_number\":\"\",\"mobile\":\"13500000001\",\"ts\":\"2018-03-28 18:37:50.000\",\"user_id\":\"163662154510106624\"},\"wd_ocr\":{\"_id\":\"201803281458301795462ac0e\",\"age\":\"26\",\"channel\":\"wdapp\",\"city\":\"枣庄市\",\"event_time\":\"2018-03-28 14:58:18.107\",\"huji_city_grade\":\"四线城市\",\"id_no\":\"370403199106226629\",\"mobile\":\"13500000001\",\"name\":\"黄明乾\",\"nation\":\"汉\",\"province\":\"山东省\",\"ts\":\"2018-03-28 14:58:30.000\",\"user_id\":\"163662154510106624\",\"valid_date\":\"2037.09.05\"},\"wd_ocr_match_blacklist\":{\"_id\":\"2018032814583019162c06013\",\"channel\":\"wdapp\",\"event_time\":\"2018-03-28 14:58:18.107\",\"id_no\":\"370403199106226629\",\"loan_number\":\"\",\"mobile\":\"13500000001\",\"name\":\"黄明乾\",\"resident_address\":\"山东省枣庄市薛城区永福南路22号院电焊厂宿舍1号楼2单元101室\",\"ts\":\"2018-03-28 14:58:30.000\",\"user_id\":\"163662154510106624\"},\"wd_pbc\":{\"_id\":\"2018032911340175746662893\",\"certificateNo\":\"-99\",\"channel\":\"FOTIC\",\"employer\":\"-99\",\"event_time\":\"2018-03-29 11:28:28.627\",\"fiveLevel\":0,\"homeTelephoneNo\":\"02012121212\",\"id_no\":\"370403199106226629\",\"lcs_12m_m2_counts\":0,\"lcs_12m_m4_counts\":0,\"lcs_24m_pastDue_state_maxs\":0,\"lcs_3m_pastDue_state_maxs\":0,\"lcs_6m_m2_counts\":0,\"lcs_6m_m3_counts\":0,\"lcs_pastDueAmount\":0,\"lcs_pastDueFrequency_count\":0,\"loan_number\":\"WD1938501803291128W\",\"ls_12m_m2_counts\":0,\"ls_12m_m4_counts\":0,\"ls_24m_pastDue_state_maxs\":0,\"ls_3m_pastDue_state_maxs\":0,\"ls_6m_m2_counts\":0,\"ls_6m_m3_counts\":0,\"ls_pastDueAmount\":0,\"ls_pastDueFrequency_count\":0,\"mobile\":\"13500000001\",\"mobileTelephoneNo\":\"18510001000\",\"name\":\"黄明乾\",\"new_address\":\"广东省广州市番禺区市桥街西丽路西丽园５街１号\",\"new_employerAddress\":\"广东省广州市番禺区市桥禺山西路333号\",\"new_employerName\":\"广州市番禺金怡酒店\",\"officeTelephoneNo\":\"02134802222\",\"pbc_24m_pastDue_counts\":0,\"pbc_querys_num_12m\":11,\"pbc_querys_num_3m\":1,\"pbc_querys_num_6m\":3,\"pbc_validCard_count\":4,\"telephoneNo\":\"-99\",\"ts\":\"2018-03-29 11:34:00.000\",\"user_id\":\"163662154510106624\"},\"wd_pbc_match_blacklist\":{\"_id\":\"2018032911340189513185d68\",\"address\":\"广东省广州市番禺区市桥街西丽路西丽园５街１号,中国广东广州番禺区市桥西丽路西丽园五街一号401,广州市市桥西丽路西丽园五街一号4楼401室,广东省广州市番禺区市桥街西丽路西丽园５街１号,广州市番禺区市桥西丽园五街1号202室\",\"certificateNo\":\"-99\",\"channel\":\"FOTIC\",\"employer\":\"-99\",\"employerName_3m\":\"-99\",\"employerName_all\":\"广州市番禺金怡酒店,金怡酒店,广州酷儿麦动漫科技有限公司,广州市君御酒店有限公司,银座酒店\",\"event_time\":\"2018-03-29 11:28:28.627\",\"homeTelephoneNo\":\"02012121212\",\"id_no\":\"370403199106226629\",\"loan_number\":\"WD1938501803291128W\",\"mobile\":\"13500000001\",\"mobileTelephoneNo\":\"18510001000\",\"new_employerName\":\"广州市番禺金怡酒店\",\"officeTelephoneNo\":\"02134802222\",\"telephoneNo\":\"-99\",\"ts\":\"2018-03-29 11:34:00.000\",\"user_id\":\"163662154510106624\"},\"wd_py_mobile_info\":{\"_id\":\"201803291134017211e9c37f0\",\"channel\":\"ygxd_app\",\"event_time\":\"2018-03-29 11:28:28.000\",\"id_no\":\"370403199106226629\",\"loan_number\":\"WD1938501803291128W\",\"mobile\":\"13500000001\",\"mobile_check_result\":\"-99\",\"mobile_fee_level\":\"-99\",\"mobile_member_level\":\"-99\",\"mobile_online_time_length\":\"-99\",\"name\":\"黄明乾\",\"ts\":\"2018-03-29 11:34:00.000\",\"user_id\":\"163662154510106624\"},\"wd_relationship\":{\"_id\":\"2018032814595891562e1d5ea\",\"channel\":\"wdapp\",\"contact_loan_app\":0,\"event_time\":\"2018-03-28 14:59:34.365\",\"id_no\":\"370403199106226629\",\"is_contacts_match_namekey\":false,\"mobile\":\"13500000001\",\"msg_risk_if\":false,\"msg_risk_type\":\"-99\",\"name\":\"黄明乾\",\"rate_1m\":-99,\"rate_7d\":-99,\"ts\":\"2018-03-28 14:59:40.000\",\"user_id\":\"163662154510106624\",\"users_distinct\":10},\"wd_td_rule_detail\":{\"_id\":\"201803291134018772598bc18\",\"addressHitXinLoanYuQiPayList\":false,\"applicantLendflatCnt12m\":-99,\"applicantLendflatCnt1m\":-99,\"applicantLendflatCnt3m\":-99,\"applicantLendflatCnt6m\":-99,\"applicantLendflatCnt7d\":-99,\"applyInfoAssoManyIdNoCnt3m\":-99,\"channel\":\"ygxd_app\",\"event_time\":\"2018-03-29 11:28:28.385\",\"familyHitXinLoanYuQiList\":false,\"friendHitXinLoanYuQiList\":false,\"idNoAssoManyApplyInfoCnt3m\":-99,\"idNoHitCourtActionNamelist\":false,\"idNoHitCourtEndCaseNamelist\":false,\"idNoHitCourtShixinNamelist\":false,\"idNoHitCrimeNamelist\":false,\"idNoHitDebitfirmNamelist\":false,\"idNoHitOweTaxNamelist\":false,\"idNoHitXinLoanYuQiList\":false,\"idNoNameHitCourtActionBlurlist\":false,\"idNoNameHitCourtEndCaseBlurlist\":false,\"idNoNameHitCourtShixinBlurlist\":false,\"idNoNameHittLoanYuQiBlurlist\":false,\"idNoZhuxueLoanArrearage\":false,\"id_no\":\"370403199106226629\",\"loanDeviceProxyRecog\":\"-99\",\"loan_number\":\"WD1938501803291128W\",\"mobile\":\"13500000001\",\"mobileHitCommuLib\":false,\"mobileHitShamLib\":false,\"mobileHitXinLoanYuQiList\":false,\"mobileRegexError\":false,\"name\":\"黄明乾\",\"ts\":\"2018-03-29 11:34:00.000\",\"user_id\":\"163662154510106624\"},\"wd_yl\":{\"_id\":\"201803291134017215a5981ba\",\"applicant_activated_city\":\"北京市\",\"applicant_common_used_city\":\"1000\",\"avg_deal_amount_6m\":\"1636.93\",\"bank_deal_amount_3m\":\"800\",\"card_level\":\"null\",\"channel\":\"ygxd_app\",\"consume_freedom_classify\":\"1\",\"convert_into_cash_model_score\":\"3\",\"event_time\":\"2018-03-29 11:28:28.038\",\"id_no\":\"370403199106226629\",\"loan_number\":\"WD1938501803291128W\",\"mobile\":\"13500000001\",\"name\":\"黄明乾\",\"public_trade_amount_12m\":\"16269.6\",\"risk_model_score\":\"569\",\"save_deal_amount_12m\":\"3100\",\"trade_amount_lottery_1m\":\"0\",\"trade_amount_stable\":\"3\",\"trade_amount_standard_dev6m\":\"5399.17\",\"trade_count_lottery_1m\":\"0\",\"ts\":\"2018-03-29 11:34:00.000\",\"user_id\":\"163662154510106624\"}},\"msg\":\"成功\",\"time\":\"2018-03-29 11:36:15.773\"}\r\n" + 
			"";
	

	public static String getPMRequestXML(ClCapChannel capChannel, ClPrdInfo prdInfo,ClUsrApply usrApply,ClUsr usr, ClUsrInfo usrInfo,
			ClIdCardInfo idCardInfo, JSONObject usrCnts, JSONObject wdData,BigDecimal premium) {
		
		
		
		
		JSONObject wd_anti_spoofing = wdData.getJSONObject("wd_anti_spoofing");
		JSONObject wd_apply_submit = wdData.getJSONObject("wd_apply_submit");
		JSONObject wd_bank_match_blacklist = wdData.getJSONObject("wd_bank_match_blacklist");
		JSONObject wd_career = wdData.getJSONObject("wd_career");
		JSONObject wd_career_match_blacklist = wdData.getJSONObject("wd_career_match_blacklist");
		JSONObject wd_contact_match_blacklist = wdData.getJSONObject("wd_contact_match_blacklist");
		JSONObject wd_inner = wdData.getJSONObject("wd_inner");
		JSONObject wd_inner_contact = wdData.getJSONObject("wd_inner_contact");
		JSONObject wd_login = wdData.getJSONObject("wd_login");
		JSONObject wd_login_match_blacklist = wdData.getJSONObject("wd_login_match_blacklist");
		JSONObject wd_ocr = wdData.getJSONObject("wd_ocr");
		JSONObject wd_ocr_match_blacklist = wdData.getJSONObject("wd_ocr_match_blacklist");
		JSONObject wd_pbc = wdData.getJSONObject("wd_pbc");
		JSONObject wd_pbc_match_blacklist = wdData.getJSONObject("wd_pbc_match_blacklist");
		JSONObject wd_relationship = wdData.getJSONObject("wd_relationship");
		JSONObject wd_td_rule_detail = wdData.getJSONObject("wd_td_rule_detail");
		JSONObject wd_jxl_honey_pot_report = wdData.getJSONObject("wd_jxl_honey_pot_report");
		JSONObject wd_yl = wdData.getJSONObject("wd_yl");
		JSONObject wd_py_mobile_info = wdData.getJSONObject("wd_py_mobile_info");
		
		
		
		String mask_times = "";
		String screen_times = "";
		
		if(wd_anti_spoofing != null && !wd_anti_spoofing.isEmpty()) {
			mask_times = wd_anti_spoofing.getString("mask_times")== null ? "" : wd_anti_spoofing.getString("mask_times");
			screen_times = wd_anti_spoofing.getString("screen_times")== null ? "" : wd_anti_spoofing.getString("screen_times");
		}
		
		String currentOverdueAccount = "";
		String IsOver30dOverdue6m = "";
		String PremiumOverdueMonth6m = "";
		String PremiumTimeOver7dCnt = "";
		String IsClaimClient = "";
		String IsForHighRiskRefuse = "";
		String NoSettleLoanCnt = "";
		String NetLoanMaxOverDays3m = "";
		String Applicant_If_AsContact_Apply = "";
		String Applicant_Contact_If_Apply = "";
		String Applicant_Contact_If_AsContact_Apply = "";
		String Applicant_Contact_If_Mutual_Contact = "";
		String Applicant_Contact_AssoApply_If_HighRisk = "";
		String Contact_Has_LoanApp_Cnt = "0";
		if(wd_inner!=null && !wd_inner.isEmpty()) {
			currentOverdueAccount = wd_inner.getString("currentOverdueAccount") == null ? "" : wd_inner.getString("currentOverdueAccount");
			IsOver30dOverdue6m = wd_inner.getString("isOver30dOverdue6m") == null ? "" : wd_inner.getString("isOver30dOverdue6m");
			PremiumOverdueMonth6m = wd_inner.getString("premiumOverdueMonth6m") == null ? "" : wd_inner.getString("premiumOverdueMonth6m");
			PremiumTimeOver7dCnt = wd_inner.getString("premiumTimeOver7dCnt") == null ? "" : wd_inner.getString("premiumTimeOver7dCnt");
			IsClaimClient = wd_inner.getString("isClaimClient") == null ? "" : wd_inner.getString("isClaimClient");
			IsForHighRiskRefuse = wd_inner.getString("isForHighRiskRefuse") == null ? "" : wd_inner.getString("isForHighRiskRefuse");
			NoSettleLoanCnt = wd_inner.getString("noSettleLoanCnt") == null ? "" : wd_inner.getString("noSettleLoanCnt");
			NetLoanMaxOverDays3m = wd_inner.getString("netLoanMaxOverDays3m") == null ? "" : wd_inner.getString("netLoanMaxOverDays3m");
			Applicant_If_AsContact_Apply = wd_inner.getString("applicant_if_asContact_apply") == null ? "" : wd_inner.getString("applicant_if_asContact_apply");
		}
		
		if(wd_inner_contact!=null && !wd_inner_contact.isEmpty()) {
			Applicant_Contact_If_Apply = wd_inner_contact.getString("applicant_contact_if_apply") == null ? "" : wd_inner_contact.getString("applicant_contact_if_apply");
			Applicant_Contact_If_AsContact_Apply = wd_inner_contact.getString("applicant_contact_if_asContact_apply") == null ? "" : wd_inner_contact.getString("applicant_contact_if_asContact_apply");
			Applicant_Contact_If_Mutual_Contact = wd_inner_contact.getString("applicant_contact_if_mutual_contact") == null ? "" : wd_inner_contact.getString("applicant_contact_if_mutual_contact");
			Applicant_Contact_AssoApply_If_HighRisk = wd_inner_contact.getString("applicant_contact_assoApply_if_highRisk") == null ? "" : wd_inner_contact.getString("applicant_contact_assoApply_if_highRisk");
			Contact_Has_LoanApp_Cnt = wd_inner_contact.getString("contact_has_loanApp_Cnt") == null ? "0" : wd_inner_contact.getString("contact_has_loanApp_Cnt");
		}
		
		
		String Mobile_Check_Result = "";
		String Mobile_Online_Time_Length = "0";
		if(wd_py_mobile_info==null && !wd_py_mobile_info.isEmpty()) {
			Mobile_Check_Result = wd_py_mobile_info.getString("mobile_check_result") == null ? "" : wd_py_mobile_info.getString("mobile_check_result");
			Mobile_Online_Time_Length = wd_py_mobile_info.getString("mobile_online_time_length") == null ? "0" : wd_py_mobile_info.getString("mobile_online_time_length");
		}
		
		String Install_Loan_App_Cnt = "0";
		String Install_Shares_App_Cnt = "0";
		String Install_Lottery_App_Cnt = "0";
		String Install_Finance_App_Cnt = "0";
		String Install_Virtual_App_Cnt = "0";
		String Current_Divice_Apply_Cnt = "0";
		String Apply_Divice_Cnt = "0";
		String Same_Gps_Apply_Cnt = "0";
		String Same_Ip_Apply_Cnt = "0";
		String Same_Wfmac_Apply_Cnt = "0";
		String Same_Gps_Apply_Cnt_6m = "0";
		String Same_Ip_Apply_Cnt_6m = "0";
		String Same_Wfmac_Apply_Cnt_6m = "0";
		String Same_Gps_Apply_Cnt_1d = "0";
		String Same_Ip_Apply_Cnt_1d = "0";
		String Same_Wfmac_Apply_Cnt_1d = "0";
		String If_Use_Simulator = "";
		if(wd_apply_submit!=null && !wd_apply_submit.isEmpty()) {
			Install_Loan_App_Cnt = wd_apply_submit.getString("install_loan_app_cnt") == null ? "0" :  wd_apply_submit.getString("install_loan_app_cnt");
			Install_Shares_App_Cnt = wd_apply_submit.getString("install_shares_app_cnt") == null ? "0" :  wd_apply_submit.getString("install_shares_app_cnt");
			Install_Lottery_App_Cnt =wd_apply_submit.getString("install_lottery_app_cnt") == null ? "0" :  wd_apply_submit.getString("install_lottery_app_cnt");
			Install_Finance_App_Cnt = wd_apply_submit.getString("install_finance_app_cnt") == null ? "0" :  wd_apply_submit.getString("install_finance_app_cnt");
			Install_Virtual_App_Cnt = wd_apply_submit.getString("install_virtual_app_cnt") == null ? "0" :  wd_apply_submit.getString("install_virtual_app_cnt");
			Current_Divice_Apply_Cnt = wd_apply_submit.getString("current_divice_apply_cnt") == null ? "0" :  wd_apply_submit.getString("current_divice_apply_cnt");
			Apply_Divice_Cnt = wd_apply_submit.getString("apply_divice_cnt") == null ? "0" :  wd_apply_submit.getString("apply_divice_cnt");
			Same_Gps_Apply_Cnt = wd_apply_submit.getString("same_gps_apply_cnt") == null ? "0" :  wd_apply_submit.getString("same_gps_apply_cnt");
			Same_Ip_Apply_Cnt = wd_apply_submit.getString("same_ip_apply_cnt") == null ? "0" :  wd_apply_submit.getString("same_ip_apply_cnt");
			Same_Wfmac_Apply_Cnt = wd_apply_submit.getString("same_wfmac_apply_cnt") == null ? "0" :  wd_apply_submit.getString("same_wfmac_apply_cnt");
			Same_Gps_Apply_Cnt_6m = wd_apply_submit.getString("same_gps_apply_cnt_6m") == null ? "0" :  wd_apply_submit.getString("same_gps_apply_cnt_6m");
			Same_Ip_Apply_Cnt_6m = wd_apply_submit.getString("same_ip_apply_cnt_6m") == null ? "0" :  wd_apply_submit.getString("same_ip_apply_cnt_6m");
			Same_Wfmac_Apply_Cnt_6m = wd_apply_submit.getString("same_wfmac_apply_cnt_6m") == null ? "0" :  wd_apply_submit.getString("same_wfmac_apply_cnt_6m");
			Same_Gps_Apply_Cnt_1d = wd_apply_submit.getString("same_gps_apply_cnt_1d") == null ? "0" :  wd_apply_submit.getString("same_gps_apply_cnt_1d");
			Same_Ip_Apply_Cnt_1d = wd_apply_submit.getString("same_ip_apply_cnt_1d") == null ? "0" :  wd_apply_submit.getString("same_ip_apply_cnt_1d"); 
			Same_Wfmac_Apply_Cnt_1d = wd_apply_submit.getString("same_wfmac_apply_cnt_1d") == null ? "0" :  wd_apply_submit.getString("same_wfmac_apply_cnt_1d");
			If_Use_Simulator = wd_apply_submit.getString("if_use_simulator") == null ? "" :  wd_apply_submit.getString("if_use_simulator");
		}
		
		String Same_Company_Apply_Cnt = "0";
		String Same_Company_Apply_Cnt_6m = "0";
		String Same_Company_Apply_Cnt_1d ="0";
		String Work_City_Grade = "";
		String House_City_Grade = "";
		String Gps_City_Match_Ctiy = "";
		String Gps_City = "";
		String Company_If_Hit_Ban_List = "";
		if(wd_career != null && !wd_career.isEmpty()) {
			Same_Company_Apply_Cnt = wd_career.getString("same_company_apply_cnt") == null ? "0" :  wd_career.getString("same_company_apply_cnt");
			Same_Company_Apply_Cnt_6m = wd_career.getString("same_wfmac_apply_cnt_6m") == null ? "0" :  wd_career.getString("same_wfmac_apply_cnt_6m");
			Same_Company_Apply_Cnt_1d = wd_career.getString("same_company_apply_cnt_1d") == null ? "0" :  wd_career.getString("same_company_apply_cnt_1d");
			Work_City_Grade = wd_career.getString("work_city_grade") == null ? "" :  wd_career.getString("work_city_grade");
			House_City_Grade = wd_career.getString("house_city_grade") == null ? "" :  wd_career.getString("house_city_grade");
			Gps_City_Match_Ctiy = wd_career.getString("gps_city_match_ctiy") == null ? "" :  wd_career.getString("gps_city_match_ctiy");
			Gps_City = wd_career.getString("gps_city") == null ? "" :  wd_career.getString("gps_city");
			Company_If_Hit_Ban_List = wd_career.getString("company_if_hit_ban_list") == null ? "" :  wd_career.getString("company_if_hit_ban_list");
		}
		
		
		String Msg_Risk_If = "";
		String Msg_Risk_Type = "";
		String Users_Distinct = "0";
		String Rate_7d = "0";
		String Rate_1m = "0";
		if(wd_relationship != null && !wd_relationship.isEmpty()) {
			Msg_Risk_If = wd_relationship.getString("msg_risk_if") == null ? "":wd_relationship.getString("msg_risk_if");
			Msg_Risk_Type = wd_relationship.getString("msg_risk_type") == null ? "":wd_relationship.getString("msg_risk_type");
			Users_Distinct = wd_relationship.getString("users_distinct") == null ? "0":wd_relationship.getString("users_distinct"); 
			Rate_7d = wd_relationship.getString("rate_7d") == null ? "0":wd_relationship.getString("rate_7d");
			Rate_1m = wd_relationship.getString("rate_1m") == null ? "0":wd_relationship.getString("rate_1m");
		}
		
		String Apply_Divice_Cnt_6m = "0";
		String Apply_Divice_Cnt_1d = "0";
		if(wd_login != null && !wd_login.isEmpty()) {
			Apply_Divice_Cnt_6m =  wd_login.getString("apply_divice_cnt_6m") == null ? "0":wd_login.getString("apply_divice_cnt_6m");
			Apply_Divice_Cnt_1d = wd_login.getString("apply_divice_cnt_1d") == null ? "0":wd_login.getString("apply_divice_cnt_1d");
		}
		
		String Lcs_12m_M4_Counts = "0";
		String Lcs_12m_M2_Counts = "0";
		String Ls_12m_M4_Counts = "0";
		String Ls_12m_M2_Counts = "0";
		String Pbc_24m_PastDue_Counts = "0";
		String Pbc_Querys_Num_6m = "0";
		String Pbc_Querys_Num_3m = "0";
		String Lcs_PastDueFrequency_Count = "0";
		String Lcs_PastDueAmount = "0";
		String Ls_PastDueFrequency_Count = "0";
		String Ls_PastDueAmount = "0";
		if(wd_pbc != null && !wd_pbc.isEmpty()) {
			Lcs_12m_M4_Counts =  wd_pbc.getString("lcs_12m_m4_counts") == null ? "0":wd_pbc.getString("lcs_12m_m4_counts");
			Lcs_12m_M2_Counts = wd_pbc.getString("lcs_12m_m2_counts") == null ? "0":wd_pbc.getString("lcs_12m_m2_counts");
			Ls_12m_M4_Counts = wd_pbc.getString("ls_12m_m4_counts") == null ? "0":wd_pbc.getString("ls_12m_m4_counts");
			Ls_12m_M2_Counts = wd_pbc.getString("ls_12m_m2_counts") == null ? "0":wd_pbc.getString("ls_12m_m2_counts");
			Pbc_24m_PastDue_Counts = wd_pbc.getString("pbc_24m_pastDue_counts") == null ? "0":wd_pbc.getString("pbc_24m_pastDue_counts");
			Pbc_Querys_Num_6m = wd_pbc.getString("pbc_querys_num_6m") == null ? "0":wd_pbc.getString("pbc_querys_num_6m");
			Pbc_Querys_Num_3m = wd_pbc.getString("pbc_querys_num_3m") == null ? "0":wd_pbc.getString("pbc_querys_num_3m");
			Lcs_PastDueFrequency_Count = wd_pbc.getString("lcs_pastDueFrequency_count") == null ? "0":wd_pbc.getString("lcs_pastDueFrequency_count");
			Lcs_PastDueAmount = wd_pbc.getString("lcs_pastDueAmount") == null ? "0":wd_pbc.getString("lcs_pastDueAmount");
			Ls_PastDueFrequency_Count = wd_pbc.getString("ls_pastDueFrequency_count") == null ? "0":wd_pbc.getString("ls_pastDueFrequency_count");
			Ls_PastDueAmount = wd_pbc.getString("ls_pastDueAmount") == null ? "0":wd_pbc.getString("ls_pastDueAmount");
		}
		
		
		String ApplicantLendflatCnt7d = "0";
		String ApplicantLendflatCnt1m = "0";
		String ApplicantLendflatCnt3m = "0";
		String ApplicantLendflatCnt6m = "0";
		String ApplicantLendflatCnt12m = "0";
		String IdNoAssoManyApplyInfoCnt3m = "0";
		String ApplyInfoAssoManyIdNoCnt3m = "0";
		if(wd_td_rule_detail != null && !wd_td_rule_detail.isEmpty()) {
			ApplicantLendflatCnt7d = wd_td_rule_detail.getString("applicantLendflatCnt7d") == null ? "0":wd_td_rule_detail.getString("applicantLendflatCnt7d"); 
			ApplicantLendflatCnt1m = wd_td_rule_detail.getString("applicantLendflatCnt1m") == null ? "0":wd_td_rule_detail.getString("applicantLendflatCnt1m");
			ApplicantLendflatCnt3m = wd_td_rule_detail.getString("applicantLendflatCnt3m") == null ? "0":wd_td_rule_detail.getString("applicantLendflatCnt3m");
			ApplicantLendflatCnt6m = wd_td_rule_detail.getString("applicantLendflatCnt6m") == null ? "0":wd_td_rule_detail.getString("applicantLendflatCnt6m");
			ApplicantLendflatCnt12m = wd_td_rule_detail.getString("applicantLendflatCnt12m") == null ? "0":wd_td_rule_detail.getString("applicantLendflatCnt12m");
			IdNoAssoManyApplyInfoCnt3m = wd_td_rule_detail.getString("idNoAssoManyApplyInfoCnt3m") == null ? "0":wd_td_rule_detail.getString("idNoAssoManyApplyInfoCnt3m");
			ApplyInfoAssoManyIdNoCnt3m = wd_td_rule_detail.getString("applyInfoAssoManyIdNoCnt3m") == null ? "0":wd_td_rule_detail.getString("applyInfoAssoManyIdNoCnt3m");
		}
		
		String Register_App_Cnt = "0";
		String Loan_App_Cnt = "0";
		String Finance_App_Cnt ="0";
		String Loan_Or_Finance_App_Cnt = "0";
		String If_Own_Car = "0";
		String If_Pay_Ins = "0";
		String If_Fin_Buy_Pre6 = "0";
		String Query_Org_Cnt30d = "0";
		String Query_Cnt30d = "0";
		String Query_Cnt90d = "0";
		String Query_Org_Cnt90d = "0";
		String Query_Org_Cnt7d = "0";
		String Query_Cnt7d = "0";
		String Query_Org_Cnt180d = "0";
		String Query_Cnt180d = "0";
		String Query_Org_Cnt360d = "0";
		String Query_Cnt360d = "0";
		String Use_Mobile_Other_Name_Cnt = "0";
		String Use_Mobile_Other_Idno_Cnt = "0";
		String Use_Idno_Other_Mobile_Cnt = "0";
		String Use_Idno_Other_Name_Cnt = "0";
		String Blacklist_Name_With_Phone = "false";
		String Blacklist_Update_Time_Name_Phone = "";
		String Blacklist_Name_With_Idcard = "false";
		String Blacklist_Update_Time_name_Idcard = "";
		String Phone_Gray_Score = "0";
		String Social_Liveness = "0";
		String Social_Influence = "0";
		String User_Phone_Province = "";
		String User_City = "";
		String User_Phone_City ="";
		String User_Region ="";
		String User_Phone_Operator ="";
		String User_Province = "";
		String Cnt_To_All = "0";
		String Cnt_Be_All = "0";
		String Cnt_All = "0";
		String Cnt_Router = "0";
		String Router_Ratio = "0";
		String Cnt_To_Black = "0";
		String Cnt_Be_Black = "0";
		String Cnt_Black = "0";
		String Black_Ratio = "0";
		String Cnt_Black2 = "0";
		String Cnt_To_Applied = "0";
		String Cnt_Be_Applied ="0";
		String Cnt_Applied = "0";
		String Recent_Time_To_All ="";
		String Recent_Time_Be_All="";
		String Recent_Time_To_Black = "";
		String Recent_Time_Be_Black = "";
		String Recent_Time_To_Applied = "";
		String Recent_Time_Be_Applied = "";
		String Call_Cnt_To_All = "0";
		String Call_Cnt_Be_All = "0";
		String Call_Cnt_To_Black = "0";
		String Call_Cnt_Be_Black = "0";
		String Call_Cnt_To_Applied = "0";
		String Call_Cnt_Be_Applied = "0";
		String Time_Spent_To_All = "0";
		String Time_Spent_Be_All = "0";
		String Time_Spent_To_Black = "0";
		String Time_Spent_Be_Black = "0";
		String Time_Spent_To_Applied = "0";
		String Time_Spent_Be_Applied = "0";
		String Weight_To_All = "0";
		String Weight_Be_All = "0";
		String Weight_To_Black = "0";
		String Weight_Be_Black = "0";
		String Weight_Black = "0";
		String Weight_To_Applied = "0";
		String Weight_Be_Applied = "0";
		String Weight_Applied = "0";
		String Most_Familiar_To_All = "0";
		String Most_Familiar_Be_All = "0";
		String Most_Familiar_All = "0";
		String Most_Familiar_To_Applied = "0";
		String Most_Familiar_Be_Applied = "0";
		String Most_Familiar_Applied = "0";
		String To_Max = "0";
		String To_Mean = "0";
		String To_Min = "0";
		String Be_Max = "0";
		String Be_Mean = "0";
		String Be_Min = "0";
		String To_Is_Familiar = "0";
		String To_Median_Familiar = "0";
		String To_Not_Familiar = "0";
		String Be_Is_Familiar = "0";
		String Be_Median_Familiar = "0";
		String Be_Not_Familiar = "0";
		String Is_Familiar = "0";
		String Median_Familiar = "0";
		String Not_Familiar = "0";
		String Weight_All = "0";
		if(wd_jxl_honey_pot_report != null && !wd_jxl_honey_pot_report.isEmpty()) {
			Register_App_Cnt = wd_jxl_honey_pot_report.getString("register_app_cnt") == null ? "0":wd_jxl_honey_pot_report.getString("register_app_cnt");
			Loan_App_Cnt = wd_jxl_honey_pot_report.getString("loan_app_cnt") == null ? "0":wd_jxl_honey_pot_report.getString("loan_app_cnt");
			Finance_App_Cnt = wd_jxl_honey_pot_report.getString("finance_app_cnt") == null ? "0":wd_jxl_honey_pot_report.getString("finance_app_cnt");
			Loan_Or_Finance_App_Cnt = wd_jxl_honey_pot_report.getString("loan_or_finance_app_cnt") == null ? "0":wd_jxl_honey_pot_report.getString("loan_or_finance_app_cnt");
			If_Own_Car = wd_jxl_honey_pot_report.getString("if_own_car") == null ? "0":wd_jxl_honey_pot_report.getString("if_own_car");
			If_Pay_Ins = wd_jxl_honey_pot_report.getString("if_pay_ins") == null ? "0":wd_jxl_honey_pot_report.getString("if_pay_ins");
			If_Fin_Buy_Pre6 = wd_jxl_honey_pot_report.getString("if_fin_buy_pre6") == null ? "0":wd_jxl_honey_pot_report.getString("if_fin_buy_pre6");
			Query_Org_Cnt30d = wd_jxl_honey_pot_report.getString("query_org_cnt30d") == null ? "0":wd_jxl_honey_pot_report.getString("query_org_cnt30d");
			Query_Cnt30d = wd_jxl_honey_pot_report.getString("query_cnt30d") == null ? "0":wd_jxl_honey_pot_report.getString("query_cnt30d");
			Query_Cnt90d = wd_jxl_honey_pot_report.getString("query_cnt90d") == null ? "0":wd_jxl_honey_pot_report.getString("query_cnt90d");
			Query_Org_Cnt90d = wd_jxl_honey_pot_report.getString("query_org_cnt90d") == null ? "0":wd_jxl_honey_pot_report.getString("query_org_cnt90d");
			Query_Org_Cnt7d = wd_jxl_honey_pot_report.getString("query_org_cnt7d") == null ? "0":wd_jxl_honey_pot_report.getString("query_org_cnt7d");
			Query_Cnt7d = wd_jxl_honey_pot_report.getString("query_cnt7d") == null ? "0":wd_jxl_honey_pot_report.getString("query_cnt7d");
			Query_Org_Cnt180d = wd_jxl_honey_pot_report.getString("query_org_cnt180d") == null ? "0":wd_jxl_honey_pot_report.getString("query_org_cnt180d");
			Query_Cnt180d = wd_jxl_honey_pot_report.getString("query_cnt180d") == null ? "0":wd_jxl_honey_pot_report.getString("query_cnt180d");
			Query_Org_Cnt360d = wd_jxl_honey_pot_report.getString("query_org_cnt360d") == null ? "0":wd_jxl_honey_pot_report.getString("query_org_cnt360d");
			Query_Cnt360d = wd_jxl_honey_pot_report.getString("query_cnt360d") == null ? "0":wd_jxl_honey_pot_report.getString("query_cnt360d");
			Use_Mobile_Other_Name_Cnt = wd_jxl_honey_pot_report.getString("use_mobile_other_name_cnt") == null ? "0":wd_jxl_honey_pot_report.getString("use_mobile_other_name_cnt");
			Use_Mobile_Other_Idno_Cnt = wd_jxl_honey_pot_report.getString("use_mobile_other_idno_cnt") == null ? "0":wd_jxl_honey_pot_report.getString("use_mobile_other_idno_cnt");
			Use_Idno_Other_Mobile_Cnt = wd_jxl_honey_pot_report.getString("use_idno_other_mobile_cnt") == null ? "0":wd_jxl_honey_pot_report.getString("use_idno_other_mobile_cnt");
			Use_Idno_Other_Name_Cnt = wd_jxl_honey_pot_report.getString("use_idno_other_name_cnt") == null ? "0":wd_jxl_honey_pot_report.getString("use_idno_other_name_cnt");
			Blacklist_Name_With_Phone = wd_jxl_honey_pot_report.getString("blacklist_name_with_phone") == null ? "false":wd_jxl_honey_pot_report.getString("blacklist_name_with_phone"); 
			Blacklist_Update_Time_Name_Phone = wd_jxl_honey_pot_report.getString("blacklist_update_time_name_phone") == null ? "":wd_jxl_honey_pot_report.getString("blacklist_update_time_name_phone");
			Blacklist_Name_With_Idcard = wd_jxl_honey_pot_report.getString("blacklist_name_with_idcard") == null ? "false":wd_jxl_honey_pot_report.getString("blacklist_name_with_idcard");
			Blacklist_Update_Time_name_Idcard =  wd_jxl_honey_pot_report.getString("blacklist_update_time_name_idcard") == null ? "":wd_jxl_honey_pot_report.getString("blacklist_update_time_name_idcard");
			Phone_Gray_Score = wd_jxl_honey_pot_report.getString("phone_gray_score") == null ? "0":wd_jxl_honey_pot_report.getString("phone_gray_score");
			Social_Liveness = wd_jxl_honey_pot_report.getString("social_liveness") == null ? "0":wd_jxl_honey_pot_report.getString("social_liveness");
			Social_Influence = wd_jxl_honey_pot_report.getString("social_influence") == null ? "0":wd_jxl_honey_pot_report.getString("social_influence");
			User_Phone_Province = wd_jxl_honey_pot_report.getString("user_phone_province") == null ? "":wd_jxl_honey_pot_report.getString("user_phone_province");
			User_City = wd_jxl_honey_pot_report.getString("user_city") == null ? "":wd_jxl_honey_pot_report.getString("user_city");
			User_Phone_City = wd_jxl_honey_pot_report.getString("user_phone_city") == null ? "":wd_jxl_honey_pot_report.getString("user_phone_city");
			User_Region = wd_jxl_honey_pot_report.getString("user_region") == null ? "":wd_jxl_honey_pot_report.getString("user_region");
			User_Phone_Operator = wd_jxl_honey_pot_report.getString("user_phone_operator") == null ? "":wd_jxl_honey_pot_report.getString("user_phone_operator");
			User_Province = wd_jxl_honey_pot_report.getString("user_province") == null ? "":wd_jxl_honey_pot_report.getString("user_province");
			Cnt_To_All = wd_jxl_honey_pot_report.getString("cnt_to_all") == null ? "0":wd_jxl_honey_pot_report.getString("cnt_to_all");
			Cnt_Be_All = wd_jxl_honey_pot_report.getString("cnt_be_all") == null ? "0":wd_jxl_honey_pot_report.getString("cnt_be_all");
			Cnt_All = wd_jxl_honey_pot_report.getString("cnt_all") == null ? "0":wd_jxl_honey_pot_report.getString("cnt_all");
			Cnt_Router = wd_jxl_honey_pot_report.getString("cnt_router") == null ? "0":wd_jxl_honey_pot_report.getString("cnt_router");
			Router_Ratio = wd_jxl_honey_pot_report.getString("router_ratio") == null ? "0":wd_jxl_honey_pot_report.getString("router_ratio");
			Cnt_To_Black = wd_jxl_honey_pot_report.getString("cnt_to_black") == null ? "0":wd_jxl_honey_pot_report.getString("cnt_to_black");
			Cnt_Be_Black = wd_jxl_honey_pot_report.getString("cnt_be_black") == null ? "0":wd_jxl_honey_pot_report.getString("cnt_be_black");
			Cnt_Black = wd_jxl_honey_pot_report.getString("cnt_black") == null ? "0":wd_jxl_honey_pot_report.getString("cnt_black");
			Black_Ratio = wd_jxl_honey_pot_report.getString("Black_Ratio") == null ? "0":wd_jxl_honey_pot_report.getString("Black_Ratio");
			Cnt_Black2 = wd_jxl_honey_pot_report.getString("cnt_black2") == null ? "0":wd_jxl_honey_pot_report.getString("cnt_black2");
			Cnt_To_Applied = wd_jxl_honey_pot_report.getString("cnt_to_applied") == null ? "0":wd_jxl_honey_pot_report.getString("cnt_to_applied");
			Cnt_Be_Applied = wd_jxl_honey_pot_report.getString("cnt_be_applied") == null ? "0":wd_jxl_honey_pot_report.getString("cnt_be_applied");
			Cnt_Applied  = wd_jxl_honey_pot_report.getString("cnt_applied") == null ? "0":wd_jxl_honey_pot_report.getString("cnt_applied");
			Recent_Time_To_All = wd_jxl_honey_pot_report.getString("recent_time_to_all") == null ? "":wd_jxl_honey_pot_report.getString("recent_time_to_all");
			Recent_Time_Be_All = wd_jxl_honey_pot_report.getString("recent_time_be_all") == null ? "":wd_jxl_honey_pot_report.getString("recent_time_be_all");
			Recent_Time_To_Black = wd_jxl_honey_pot_report.getString("recent_time_to_black") == null ? "":wd_jxl_honey_pot_report.getString("recent_time_to_black");
			Recent_Time_Be_Black = wd_jxl_honey_pot_report.getString("recent_time_be_black") == null ? "":wd_jxl_honey_pot_report.getString("recent_time_be_black");
			Recent_Time_To_Applied = wd_jxl_honey_pot_report.getString("recent_time_to_applied") == null ? "":wd_jxl_honey_pot_report.getString("recent_time_to_applied");
			Recent_Time_Be_Applied = wd_jxl_honey_pot_report.getString("recent_time_be_applied") == null ? "":wd_jxl_honey_pot_report.getString("recent_time_be_applied");
			Call_Cnt_To_All = wd_jxl_honey_pot_report.getString("call_cnt_to_all") == null ? "0":wd_jxl_honey_pot_report.getString("call_cnt_to_all");
			Call_Cnt_Be_All = wd_jxl_honey_pot_report.getString("call_cnt_be_all") == null ? "0":wd_jxl_honey_pot_report.getString("call_cnt_be_all");
			Call_Cnt_To_Black = wd_jxl_honey_pot_report.getString("call_cnt_to_black") == null ? "0":wd_jxl_honey_pot_report.getString("call_cnt_to_black");
			Call_Cnt_Be_Black = wd_jxl_honey_pot_report.getString("call_cnt_be_black") == null ? "0":wd_jxl_honey_pot_report.getString("call_cnt_be_black");
			Call_Cnt_To_Applied = wd_jxl_honey_pot_report.getString("call_cnt_to_applied") == null ? "0":wd_jxl_honey_pot_report.getString("call_cnt_to_applied");
			Call_Cnt_Be_Applied = wd_jxl_honey_pot_report.getString("call_cnt_be_applied") == null ? "0":wd_jxl_honey_pot_report.getString("call_cnt_be_applied");
			Time_Spent_To_All = wd_jxl_honey_pot_report.getString("time_spent_to_all") == null ? "0":wd_jxl_honey_pot_report.getString("time_spent_to_all");
			Time_Spent_Be_All = wd_jxl_honey_pot_report.getString("time_spent_be_all") == null ? "0":wd_jxl_honey_pot_report.getString("time_spent_be_all");
			Time_Spent_To_Black = wd_jxl_honey_pot_report.getString("time_spent_to_black") == null ? "0":wd_jxl_honey_pot_report.getString("time_spent_to_black");
			Time_Spent_Be_Black = wd_jxl_honey_pot_report.getString("time_spent_be_black") == null ? "0":wd_jxl_honey_pot_report.getString("time_spent_be_black");
			Time_Spent_To_Applied = wd_jxl_honey_pot_report.getString("time_spent_to_applied") == null ? "0":wd_jxl_honey_pot_report.getString("time_spent_to_applied");
			Time_Spent_Be_Applied = wd_jxl_honey_pot_report.getString("time_spent_be_applied") == null ? "0":wd_jxl_honey_pot_report.getString("time_spent_be_applied");
			Weight_To_All = wd_jxl_honey_pot_report.getString("weight_to_all") == null ? "0":wd_jxl_honey_pot_report.getString("weight_to_all");
			Weight_Be_All = wd_jxl_honey_pot_report.getString("weight_be_all") == null ? "0":wd_jxl_honey_pot_report.getString("weight_be_all");
			Weight_To_Black = wd_jxl_honey_pot_report.getString("weight_to_black") == null ? "0":wd_jxl_honey_pot_report.getString("weight_to_black");
			Weight_Be_Black = wd_jxl_honey_pot_report.getString("weight_be_black") == null ? "0":wd_jxl_honey_pot_report.getString("weight_be_black");
			Weight_Black = wd_jxl_honey_pot_report.getString("weight_black") == null ? "0":wd_jxl_honey_pot_report.getString("weight_black");
			Weight_To_Applied = wd_jxl_honey_pot_report.getString("weight_to_applied") == null ? "0":wd_jxl_honey_pot_report.getString("weight_to_applied");
			Weight_Be_Applied = wd_jxl_honey_pot_report.getString("weight_be_applied") == null ? "0":wd_jxl_honey_pot_report.getString("weight_be_applied");
			Weight_Applied = wd_jxl_honey_pot_report.getString("weight_applied") == null ? "0":wd_jxl_honey_pot_report.getString("weight_applied");
			Weight_All = wd_jxl_honey_pot_report.getString("weight_all") == null ? "0":wd_jxl_honey_pot_report.getString("weight_all");
			Most_Familiar_To_All = wd_jxl_honey_pot_report.getString("most_familiar_to_all") == null ? "0":wd_jxl_honey_pot_report.getString("most_familiar_to_all");
			Most_Familiar_Be_All = wd_jxl_honey_pot_report.getString("most_familiar_be_all") == null ? "0":wd_jxl_honey_pot_report.getString("most_familiar_be_all");
			Most_Familiar_All = wd_jxl_honey_pot_report.getString("most_familiar_all") == null ? "0":wd_jxl_honey_pot_report.getString("most_familiar_all");
			Most_Familiar_To_Applied = wd_jxl_honey_pot_report.getString("most_familiar_to_applied") == null ? "0":wd_jxl_honey_pot_report.getString("most_familiar_to_applied");
			Most_Familiar_Be_Applied = wd_jxl_honey_pot_report.getString("most_familiar_be_applied") == null ? "0":wd_jxl_honey_pot_report.getString("most_familiar_be_applied");
			Most_Familiar_Applied = wd_jxl_honey_pot_report.getString("most_familiar_applied") == null ? "0":wd_jxl_honey_pot_report.getString("most_familiar_applied");
			To_Max = wd_jxl_honey_pot_report.getString("to_max") == null ? "0":wd_jxl_honey_pot_report.getString("to_max");
			To_Mean = wd_jxl_honey_pot_report.getString("to_mean") == null ? "0":wd_jxl_honey_pot_report.getString("to_mean");
			To_Min = wd_jxl_honey_pot_report.getString("to_min") == null ? "0":wd_jxl_honey_pot_report.getString("to_min");
			Be_Max = wd_jxl_honey_pot_report.getString("be_max") == null ? "0":wd_jxl_honey_pot_report.getString("be_max");
			Be_Mean = wd_jxl_honey_pot_report.getString("be_mean") == null ? "0":wd_jxl_honey_pot_report.getString("be_mean");
			Be_Min = wd_jxl_honey_pot_report.getString("be_min") == null ? "0":wd_jxl_honey_pot_report.getString("be_min");
			To_Is_Familiar = wd_jxl_honey_pot_report.getString("to_is_familiar") == null ? "0":wd_jxl_honey_pot_report.getString("to_is_familiar");
			To_Median_Familiar = wd_jxl_honey_pot_report.getString("to_median_familiar") == null ? "0":wd_jxl_honey_pot_report.getString("to_median_familiar");
			To_Not_Familiar = wd_jxl_honey_pot_report.getString("to_not_familiar") == null ? "0":wd_jxl_honey_pot_report.getString("to_not_familiar"); 
			Be_Is_Familiar = wd_jxl_honey_pot_report.getString("be_is_familiar") == null ? "0":wd_jxl_honey_pot_report.getString("be_is_familiar"); 
			Be_Median_Familiar = wd_jxl_honey_pot_report.getString("be_median_familiar") == null ? "0":wd_jxl_honey_pot_report.getString("be_median_familiar");
			Be_Not_Familiar = wd_jxl_honey_pot_report.getString("be_not_familiar") == null ? "0":wd_jxl_honey_pot_report.getString("be_not_familiar");
			Is_Familiar = wd_jxl_honey_pot_report.getString("is_familiar") == null ? "0":wd_jxl_honey_pot_report.getString("is_familiar");
			Median_Familiar = wd_jxl_honey_pot_report.getString("median_familiar") == null ? "0":wd_jxl_honey_pot_report.getString("median_familiar");
			Not_Familiar = wd_jxl_honey_pot_report.getString("not_familiar") == null ? "0":wd_jxl_honey_pot_report.getString("not_familiar");
		}
		
		String Apply_Limit = "";
		String Loan_Period = "";
		String Loan_Usage = "";
		if (usrApply != null) {
			Apply_Limit = StringUtils.isEmpty(usrApply.getApplyAmt()) ? "0" : usrApply.getApplyAmt();
			Loan_Period = StringUtils.isEmpty(usrApply.getApplyTnr()) ? "0" : usrApply.getApplyTnr();
			Loan_Usage =  StringUtils.isEmpty(usrApply.getApplyFor()) ? "" : usrApply.getApplyFor();
		}
		
		String Loan_Rate = "";
		String Loan_Type = "";
		if(prdInfo != null) {
			Loan_Rate = prdInfo.getINT_RAT().toString();
			Loan_Type = prdInfo.getPRD_CDE();
		}
		
		
		String Premium = "";
		String Branch = "";
		String Sales_Channel = "";
		String Sales_Person = "";
		String Report_User = "";
		if(capChannel != null) {
			Premium = new BigDecimal(Apply_Limit).multiply(new BigDecimal(capChannel.getSUM_AMOUNT())).subtract(new BigDecimal(Apply_Limit)).toString();
			Branch = capChannel.getCOM_CED();
			Sales_Channel = capChannel.getMAKE_COM();
			Sales_Person = capChannel.getCHANNEL_CDE();
			Report_User = capChannel.getOPERATOR_CDE();
		}
		String Name = "";
		String Gender = "";
		String Nation = "";
		String Age = "";
		String Birthday = "";
		String Id_Type = "身份证";
		String Id_Number = "";
		if(idCardInfo != null) {
		 Name = idCardInfo.getCUST_NAME();
			 Gender = idCardInfo.getINDIV_SEX();
			 Nation = idCardInfo.getINDIV_NATION();
			 Age = "";//getAge(idCardInfo.getBIRTHDAY_DATE())+"";
			 Birthday = "";//getBirthFormat(idCardInfo.getBIRTHDAY_DATE());
			 Id_Type = "身份证";
			 Id_Number = idCardInfo.getID_NO();
		}
		String Mobile = "";
		if(usr != null) {
			Mobile = usr.getUSR_TEL();
		}
		
		String Home_Address_State = "";
		String Home_Address_City = "";
		String Home_Address_County = "";
		String Home_Address_Street_Address = "";
		String Company_Name = "";
		String Company_Address_State ="";
		String Company_Address_City = "";
		String Company_Address_County = "";
		String Company_Address_Street_Address = "";
		String Company_Phone = "";
		if(usrInfo!=null) {
			 Home_Address_State = usrInfo.getLIVE_PROVINCE();
			 Home_Address_City = usrInfo.getLIVE_CITY();
			 Home_Address_County = usrInfo.getLIVE_AREA();
			 Home_Address_Street_Address = usrInfo.getLIVE_ADDR();
			 Company_Name = usrInfo.getINDIV_EMP_NAME();
			 Company_Address_State = usrInfo.getINDIV_EMP_PROVINCE();
			 Company_Address_City = usrInfo.getINDIV_EMP_CITY();
			 Company_Address_County = usrInfo.getINDIV_EMP_AREA();
			 Company_Address_Street_Address = usrInfo.getINDIV_EMP_ADDR();
			 Company_Phone = usrInfo.getINDIV_EMP_TEL();
		}
		
		String Cnts_Name1 = "";
		String Cnts_Relation1 = "";
		String Cnts_Mobile1 = "";
		if(usrCnts!=null) {
		 Cnts_Name1 = usrCnts.getString("relationName");
		 Cnts_Relation1 = usrCnts.getString("relation");
		 Cnts_Mobile1 = usrCnts.getString("relationPhone");
		}
		String PBOC_NAME = Name;
		String PBOC_Gender = Gender;
		String PBOC_IDType="身份证";
		String PBOC_IDNO=Id_Number;
		String PBOC_Birthday=Birthday;
		
		
		String xml = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append( 
				"<ProcessManagerRequest>") .append(  
				"	<Application_Header>") .append( 
				"		<Channel>WDL</Channel>") .append( 
				"		<CallType>WD2</CallType>") .append(
				"		<ApplicationNumber>WD1</ApplicationNumber>") .append(
				"	</Application_Header>") .append(
				"	<APS_Applications>") .append(
				"		<APS_Application>") .append(
				"			<Organisation_Code>SIG</Organisation_Code>") .append(
				"			<Country_Code>CN</Country_Code>") .append(
				"			<Group_Member_Code>组织成员码</Group_Member_Code>") .append(
				"			<Application_Number>WD2018031215432110003</Application_Number>") .append(
				"			<Apply_Channel>来源渠道</Apply_Channel>") .append(
				"			<Application_Date>2018-03-12T15:43:21</Application_Date>") .append(
				"			<Application_Type>WDL</Application_Type>") .append(
				"			<Apply_Limit>") .append(Apply_Limit) .append("</Apply_Limit>") .append(
				"			<Loan_Period>") .append(Loan_Period) .append("</Loan_Period>") .append(
				"			<Loan_Usage>") .append(Loan_Usage) .append("</Loan_Usage>") .append(
				"			<Loan_Rate>") .append(Loan_Rate) .append("</Loan_Rate>") .append(
				"			<Premium>") .append(Premium) .append("</Premium>") .append(
				"			<Branch>") .append(Branch) .append("</Branch> <!--  虚拟门店代码  -->  ") .append(
				"			<Final_Decision>pass</Final_Decision> <!--  待确认 pass reject ？-->") .append(
				"			<Final_Decision_Date>2018-03-12T15:48:21</Final_Decision_Date> <!--  待确认 -->") .append(
				"			<Final_Limit_Reason_Code></Final_Limit_Reason_Code> <!--  待确认 CL_CP_0005_00000016 -->") .append(
				"			<Final_Limit>0</Final_Limit> <!--  待确认 -->") .append(
				"			<Workflow_Status></Workflow_Status>") .append(
				"			<Sales_Channel>") .append(Sales_Channel) .append("</Sales_Channel>  <!--  渠道 --> ") .append(
				"			<Sales_Person>") .append(Sales_Person) .append("</Sales_Person>  <!--  虚拟销售人员  --> ") .append(
				"			<Loan_Type>") .append(Loan_Type) .append("</Loan_Type><!-- TQD -->") .append(
				"			<Sales_Level></Sales_Level> <!--无-->") .append(
				"			<Shop_Recommended></Shop_Recommended> <!--无-->") .append(
				"			<Channel_Company_Name></Channel_Company_Name><!--无-->") .append(
				"			<Cancel_Reapplication_Flag></Cancel_Reapplication_Flag><!--无-->") .append(
				"			<Equivalent_Yearly_Insurance_Premium>0</Equivalent_Yearly_Insurance_Premium><!--无-->") .append(
				"			<Required_Payment_Year></Required_Payment_Year><!--无-->") .append(
				"			<Payment_frequency></Payment_frequency><!--无-->") .append(
				"			<Date_Of_Policy>2018-03-12T15:57:17</Date_Of_Policy>") .append(
				"			<External_Debt>0</External_Debt>") .append(
				"			<City_Level></City_Level><!--无-->") .append(
				"			<Is_State_Enterprises></Is_State_Enterprises><!--无-->") .append(
				"			<Is_SIG_Policy></Is_SIG_Policy><!--无-->") .append(
				"			<Equivalent_Yearly_Insurance_Premium2>0</Equivalent_Yearly_Insurance_Premium2><!--无-->") .append(
				"			<Required_Payment_Year2></Required_Payment_Year2><!--无-->") .append(
				"			<Payment_frequency2></Payment_frequency2><!--无-->") .append(
				"			<Date_Of_Policy2></Date_Of_Policy2><!--无-->") .append(
				"			<Is_SIG_Policy2></Is_SIG_Policy2><!--无-->") .append(
				"			<Equivalent_Yearly_Insurance_Premium3>0</Equivalent_Yearly_Insurance_Premium3><!--无-->") .append(
				"			<Required_Payment_Year3></Required_Payment_Year3><!--无-->") .append(
				"			<Payment_frequency3></Payment_frequency3><!--无-->") .append(
				"			<Date_Of_Policy3></Date_Of_Policy3><!--无-->") .append(
				"			<Is_SIG_Policy3></Is_SIG_Policy3><!--无-->") .append(
				"			<Customer_Type></Customer_Type><!--  无 -->") .append(
				"			<Application_Branch_Code></Application_Branch_Code><!--无-->") .append(
				"			<Application_Branch></Application_Branch><!--无-->") .append(
				"			<Sales_Name></Sales_Name><!--无-->") .append(
				"			<Four_Channel_Name></Four_Channel_Name><!--无-->") .append(
				"			<Fenqi_Product_Code></Fenqi_Product_Code>") .append(
				"			<Fenqi_Payment_Method></Fenqi_Payment_Method>") .append(
				"			<Guarantee_Method></Guarantee_Method>") .append(
				"			<Channel_Type></Channel_Type>") .append(
				"			<Business_Origin></Business_Origin>") .append(
				"			<Sale_Channel></Sale_Channel><!--无-->") .append(
				"			<First_Audit_Guarantee_Method></First_Audit_Guarantee_Method><!--无-->") .append(
				"			<Apply_Stores_Province></Apply_Stores_Province><!--无-->") .append(
				"			<Apply_Stores_City></Apply_Stores_City><!--无-->") .append(
				"			<Apply_Stores_Distinct></Apply_Stores_Distinct><!--无-->") .append(
				"			<Report_User>") .append(Report_User) .append("</Report_User><!-- 录单员代码？ -->") .append(
				"			<Report_Team></Report_Team><!--无-->") .append(
				"			<Report_Reason></Report_Reason><!--无-->") .append(
				"			<Report_Note></Report_Note><!--无-->") .append(
				"			<Apply_City></Apply_City><!--无-->") .append(
				"		</APS_Application>") .append(
				"	</APS_Applications>") .append(
				"	<APS_Applicants>") .append(
				"		<APS_Applicant>") .append(
//				"			<Name>") .append(Name) .append("</Name>") .append(
//				"			<Gender>") .append(Gender) .append("</Gender>") .append(
//				"			<Nation>") .append(Nation) .append("</Nation>") .append(
//				"			<Age>") .append(Age) .append("</Age>") .append(
//				"			<Birthday>") .append(Birthday) .append("</Birthday>") .append(
//				"			<Id_Type>") .append(Id_Type) .append("</Id_Type><!--码值  或者  身份证-->") .append(
//				"			<Id_Number>") .append(Id_Number) .append("</Id_Number>") .append(
				"			<Marriage></Marriage><!--无-->") .append(
				"			<Education></Education><!--无-->") .append(
//				"			<Home_Address_State>") .append(Home_Address_State) .append("</Home_Address_State>") .append(
//				"			<Home_Address_City>") .append(Home_Address_City) .append("</Home_Address_City>") .append(
//				"			<Home_Address_County>") .append(Home_Address_County) .append("</Home_Address_County>") .append(
//				"			<Home_Address_Street_Address>") .append(Home_Address_Street_Address) .append("</Home_Address_Street_Address>") .append(
				"			<Home_Address_Zip></Home_Address_Zip><!--无-->") .append(
				"			<Home_Phone></Home_Phone><!--无-->") .append(
//				"			<Mobile>") .append(Mobile) .append("</Mobile>") .append(
				"			<Email></Email><!--无-->") .append(
				"			<Is_Private_Employer>0</Is_Private_Employer>") .append(
//				"			<Company_Name>") .append(Company_Name) .append("</Company_Name>") .append(
//				"			<Company_Address_State>") .append(Company_Address_State) .append("</Company_Address_State>") .append(
//				"			<Company_Address_City>") .append(Company_Address_City) .append("</Company_Address_City>") .append(
//				"			<Company_Address_County>") .append(Company_Address_County) .append("</Company_Address_County>") .append(
//				"			<Company_Address_Street_Address>") .append(Company_Address_Street_Address) .append("</Company_Address_Street_Address>") .append(
				"			<Company_Address_Zip></Company_Address_Zip><!--无-->") .append(
//				"			<Company_Phone>") .append(Company_Phone) .append("</Company_Phone><!--无-->") .append(
				"			<Department></Department><!--无-->") .append(
				"			<Industry_Code></Industry_Code><!--无-->") .append(
				"			<Position_Code></Position_Code><!--无-->") .append(
				"			<Employed_Date></Employed_Date><!--无-->") .append(
				"			<Base_Salary>0</Base_Salary><!--无-->") .append(
				"			<Other_Income>0</Other_Income><!--无-->") .append(
				"			<Total_Income>0</Total_Income><!--无-->") .append(
				"			<Payment_Date>0</Payment_Date><!--无-->") .append(
				"			<Foster_Count>0</Foster_Count><!--无-->") .append(
				"			<Has_Social_Security>0</Has_Social_Security><!--无-->") .append(
				"			<Has_Vehicle>0</Has_Vehicle><!--无-->") .append(
				"			<Has_Property>0</Has_Property><!--无-->") .append(
				"			<Has_PBOC>0</Has_PBOC><!--无-->") .append(
				"			<Has_Bank_Statement>0</Has_Bank_Statement><!--无-->") .append(
				"			<Plate_Number></Plate_Number><!--无-->") .append(
				"			<Additional_Mobile></Additional_Mobile><!--无-->") .append(
				"			<Additional_Company_Phone></Additional_Company_Phone><!--无-->") .append(
				"			<Spouse_Name></Spouse_Name><!--无-->") .append(
				"			<Spouse_Mobile></Spouse_Mobile><!--无-->") .append(
				"			<Vehicle_Type></Vehicle_Type><!--无-->") .append(
				"			<Enterprise_Type></Enterprise_Type><!--无-->") .append(
				"			<Job_Grading></Job_Grading><!--无-->") .append(
				"			<Businese_Condition></Businese_Condition><!--无-->") .append(
				"			<Has_Multi_Property></Has_Multi_Property><!--无-->") .append(
				"			<Verification_Income>0</Verification_Income><!--无-->") .append(
				"			<Monthly_Total_Income>0</Monthly_Total_Income><!--无-->") .append(
				"			<City_Class></City_Class><!--无-->") .append(
				"			<Company_Nature></Company_Nature><!--无-->") .append(
				"			<Property_Number></Property_Number><!--无-->") .append(
				"			<Rent_Addr_Province></Rent_Addr_Province><!--无-->") .append(
				"			<Rent_Addr_City></Rent_Addr_City><!--无-->") .append(
				"			<Rent_Addr_District></Rent_Addr_District><!--无-->") .append(
				"			<Rent_Addr_Detail></Rent_Addr_Detail><!--无-->") .append(
				"			<Decorate_Addr></Decorate_Addr><!--无-->") .append(
				"			<Frame_Number></Frame_Number><!--无-->") .append(
				"			<ID_Start_Date>2018-03-06</ID_Start_Date><!--无-->") .append(
				"			<ID_Expire_Date>2018-03-06</ID_Expire_Date><!--无-->") .append(
				"			<ID_Address_Province></ID_Address_Province><!--无-->") .append(
				"			<ID_Address_City></ID_Address_City><!--无-->") .append(
				"			<ID_Address_District></ID_Address_District><!--无-->") .append(
				"			<ID_Address_Detail></ID_Address_Detail><!--无-->") .append(
				"			<Occupation_Classing></Occupation_Classing><!--无-->") .append(
				"			<Employ_Date>2018-03-06</Employ_Date><!--无-->") .append(
				"			<Inhabit_Start_Date>2018-03-06</Inhabit_Start_Date><!--无-->") .append(
				"			<In_Insurance_City_Date>2018-03-06</In_Insurance_City_Date><!--无-->") .append(
				"			<Private_Business_Owner_Premises></Private_Business_Owner_Premises><!--无-->") .append(
				"			<Private_Business_Equity_Rate></Private_Business_Equity_Rate><!--无-->") .append(
				"			<Pay_Off_Method></Pay_Off_Method><!--无-->") .append(
				"			<registDate></registDate><!--无-->") .append(
				"			<registFund>0</registFund><!--无-->") .append(
				"			<fundCurrency></fundCurrency><!--无-->") .append(
				"			<artificialName></artificialName><!--无-->") .append(
				"			<manageBeginDate></manageBeginDate><!--无-->") .append(
				"			<manageEndDate></manageEndDate><!--无-->") .append(
				"			<corpTypeID></corpTypeID><!--无-->") .append(
				"			<corpTypeCaption></corpTypeCaption><!--无-->") .append(
				"			<staffNumber>0</staffNumber><!--无-->") .append(
				"			<tradeCode></tradeCode><!--无-->") .append(
				"			<Is_shareholder></Is_shareholder><!--无-->") .append(
				"			<Contributive_Proportion>0</Contributive_Proportion><!--无-->") .append(
				"			<Is_Same_Legal_Name></Is_Same_Legal_Name><!--无-->") .append(
				"			<Is_Exist_important_Person></Is_Exist_important_Person><!--无-->") .append(
				"			<logoutDate></logoutDate><!--无-->") .append(
				"			<revokeDate></revokeDate><!--无-->") .append(
				"			<StatusID></StatusID><!--无-->") .append(
				"			<statusCaption></statusCaption><!--无-->") .append(
				"		</APS_Applicant>") .append(
				"	</APS_Applicants>") .append(
				"	<APS_Contacts>") .append(
				"		<APS_Contact>") .append(
//				"			<Name>") .append(Cnts_Name1) .append("</Name>") .append(
				"			<Gender></Gender><!--无-->") .append(
				"			<Id_Number></Id_Number><!--无-->") .append(
				"			<Age></Age><!--无-->") .append(
//				"			<Relation>") .append(Cnts_Relation1) .append("</Relation>") .append(
				"			<Contact_Home_Phone></Contact_Home_Phone><!--无-->") .append(
//				"			<Contact_Mobile>") .append(Cnts_Mobile1) .append("</Contact_Mobile>") .append(
				"			<Contact_Company_Phone></Contact_Company_Phone><!--无-->") .append(
				"			<Additional_Mobile></Additional_Mobile><!--无-->") .append(
				"			<Additional_Company_Phone></Additional_Company_Phone><!--无-->") .append(
				"		</APS_Contact>") .append(
				"	</APS_Contacts>") .append(
				"	<PBOC_Personal_Infos>") .append(
				"		<PBOC_Personal_Info>") .append(
//				"			<Name>") .append(PBOC_NAME) .append("</Name>") .append(
//				"			<Gender>") .append(PBOC_Gender) .append("</Gender>") .append(
//				"			<ID_Type>") .append(PBOC_IDType) .append("</ID_Type>") .append(
//				"			<ID_Number>") .append(PBOC_IDNO) .append("</ID_Number>") .append(
//				"			<Birthday>") .append(PBOC_Birthday) .append("</Birthday>") .append(
				"			<Education></Education>") .append(
				"			<Education_Degree></Education_Degree><!--无-->") .append(
				"			<Mailing_Address></Mailing_Address>") .append(
				"			<ID_Address></ID_Address><!--无-->") .append(
				"			<Home_Phone_Number></Home_Phone_Number><!--无-->") .append(
				"			<Company_Phone_Number></Company_Phone_Number>") .append(
				"			<Cell_Phone_Number></Cell_Phone_Number>") .append(
				"			<Marital_State></Marital_State><!--无-->") .append(
				"			<Spouse_Name></Spouse_Name><!--无-->") .append(
				"			<Spouse_ID_Type></Spouse_ID_Type><!--无-->") .append(
				"			<Spouse_ID_Number></Spouse_ID_Number><!--无-->") .append(
				"			<Spouse_Company></Spouse_Company><!--无-->") .append(
				"			<Spouse_Contact_Number></Spouse_Contact_Number><!--无-->") .append(
				"		</PBOC_Personal_Info>") .append(
				"	</PBOC_Personal_Infos>" ) .append(
				"	<APS_OtherInfo3s>") .append(
				"		<APS_OtherInfo3>") .append(
				"			<Merchant_ID></Merchant_ID><!--无-->") .append(
				"			<Product_Category></Product_Category><!--无-->") .append(
				"			<Product_Category_Detail></Product_Category_Detail><!--无-->") .append(
				"			<Co_Org_City></Co_Org_City><!--无-->") .append(
				"			<Agency_Rating></Agency_Rating><!--无-->") .append(
				"			<Org_Status></Org_Status><!--无-->") .append(
				"			<Bank_Card></Bank_Card><!--无-->") .append(
				"			<Bank_Mobile></Bank_Mobile><!--无-->") .append(
				"			<ID_Validation></ID_Validation><!--无-->") .append(
				"			<ID_Compare>0</ID_Compare><!--无-->") .append(
				"			<Liveness_Identification></Liveness_Identification><!--无-->") .append(
				"			<Public_Security_Query>0</Public_Security_Query><!--无-->") .append(
				"			<Register_Address_Province></Register_Address_Province><!--无-->") .append(
				"			<Register_Address_City></Register_Address_City><!--无-->") .append(
				"			<Register_Address_District></Register_Address_District><!--无-->") .append(
				"			<Register_Address_Detail></Register_Address_Detail><!--无-->") .append(
				"			<Income_Source></Income_Source><!--无-->") .append(
				"		</APS_OtherInfo3>") .append(
				"	</APS_OtherInfo3s>") .append(
				"	<APS_Wangdai_Variable>") .append(
				"		<APS_Wangdai_Variable>") .append(
				"			<Fail_Times>0</Fail_Times>") .append(
				"			<Is_ID_Impersonated>false</Is_ID_Impersonated>") .append(
				"			<Software_Times>0</Software_Times><!-- 衍生数据，活体 -->") .append(
				"			<Mask_Times>") .append(mask_times) .append("</Mask_Times><!-- 衍生数据，活体 -->") .append(
				"			<Screen_Times>") .append(screen_times) .append("</Screen_Times><!-- 衍生数据，活体 -->") .append(
				"			<CurrentOverdueAccount>") .append(currentOverdueAccount) .append("</CurrentOverdueAccount><!--衍生数据 内匹-->") .append(
				"			<IsOver30dOverdue6m>") .append(IsOver30dOverdue6m) .append("</IsOver30dOverdue6m><!--衍生数据 内匹-->") .append(
				"			<PremiumOverdueMonth6m>") .append(PremiumOverdueMonth6m) .append("</PremiumOverdueMonth6m><!--衍生数据 内匹-->") .append(
				"			<PremiumTimeOver7dCnt>") .append(PremiumTimeOver7dCnt) .append("</PremiumTimeOver7dCnt><!--衍生数据 内匹-->") .append(
				"			<IsClaimClient>") .append(IsClaimClient) .append("</IsClaimClient><!--衍生数据 内匹-->") .append(
				"			<IsForHighRiskRefuse>") .append(IsForHighRiskRefuse) .append("</IsForHighRiskRefuse><!--衍生数据 内匹-->") .append(
				"			<NoSettleLoanCnt>") .append(NoSettleLoanCnt) .append("</NoSettleLoanCnt><!--衍生数据 内匹  未结清贷款笔数-->") .append(
				"			<NetLoanMaxOverDays3m>") .append(NetLoanMaxOverDays3m) .append("</NetLoanMaxOverDays3m><!-- 衍生数据 内匹 网贷放款近3个月最大逾期天数 -->") .append(
				"			<Applicant_If_AsContact_Apply>") .append(Applicant_If_AsContact_Apply) .append("</Applicant_If_AsContact_Apply><!--衍生数据 内匹-->") .append(
				"			<Applicant_Contact_If_Apply>") .append(Applicant_Contact_If_Apply) .append("</Applicant_Contact_If_Apply><!--衍生数据 内匹-->") .append(
				"			<Applicant_Contact_If_AsContact_Apply>") .append(Applicant_Contact_If_AsContact_Apply) .append("</Applicant_Contact_If_AsContact_Apply><!--衍生数据 内匹-->") .append(
				"			<Applicant_Contact_If_Mutual_Contact>") .append(Applicant_Contact_If_Mutual_Contact) .append("</Applicant_Contact_If_Mutual_Contact><!--衍生数据 内匹-->") .append(
				"			<Applicant_Contact_AssoApplicat_If_HighRisk></Applicant_Contact_AssoApplicat_If_HighRisk><!--无-->") .append(
				"			<Applicant_Contact_AssoApply_If_HighRisk>") .append(Applicant_Contact_AssoApply_If_HighRisk) .append("</Applicant_Contact_AssoApply_If_HighRisk><!--衍生数据 内匹-->") .append(
				"			<Mobile_Check_Result>") .append(Mobile_Check_Result) .append("</Mobile_Check_Result><!--衍生数据 鹏元-->") .append(
				"			<Mobile_Online_Time_Length>") .append(Mobile_Online_Time_Length) .append("</Mobile_Online_Time_Length><!--衍生数据 鹏元-->") .append(
				"			<Install_Loan_App_Cnt>") .append(Install_Loan_App_Cnt) .append("</Install_Loan_App_Cnt><!-- 衍生数据 借款申请衍生 -->") .append(
				"			<Install_Shares_App_Cnt>") .append(Install_Shares_App_Cnt) .append("</Install_Shares_App_Cnt><!-- 衍生数据 借款申请衍生 -->") .append(
				"			<Install_Lottery_App_Cnt>") .append(Install_Lottery_App_Cnt) .append("</Install_Lottery_App_Cnt><!-- 衍生数据 借款申请衍生 -->") .append(
				"			<Install_Finance_App_Cnt>") .append(Install_Finance_App_Cnt) .append("</Install_Finance_App_Cnt><!-- 衍生数据 借款申请衍生 -->") .append(
				"			<Install_Virtual_App_Cnt>") .append(Install_Virtual_App_Cnt) .append("</Install_Virtual_App_Cnt><!-- 衍生数据 借款申请衍生 -->") .append(
				"			<Contact_Has_LoanApp_Cnt>") .append(Contact_Has_LoanApp_Cnt) .append("</Contact_Has_LoanApp_Cnt><!-- 衍生数据 内匹 联系衍生结果 -->") .append(
				"			<Msg_Risk_If>") .append(Msg_Risk_If) .append("</Msg_Risk_If><!-- 衍生数据 紧急联系人结果 -->") .append(
				"			<Msg_Risk_Type>") .append(Msg_Risk_Type) .append("</Msg_Risk_Type><!-- 衍生数据 紧急联系人结果 -->") .append(
				"			<Current_Divice_Apply_Cnt>") .append(Current_Divice_Apply_Cnt) .append("</Current_Divice_Apply_Cnt><!-- 衍生数据 借款申请衍生 -->") .append(
				"			<Apply_Divice_Cnt>") .append(Apply_Divice_Cnt) .append("</Apply_Divice_Cnt><!-- 衍生数据 借款申请衍生 -->") .append(
				"			<Same_Gps_Apply_Cnt>") .append(Same_Gps_Apply_Cnt) .append("</Same_Gps_Apply_Cnt><!-- 衍生数据 借款申请衍生 -->") .append(
				"			<Same_Ip_Apply_Cnt>") .append(Same_Ip_Apply_Cnt) .append("</Same_Ip_Apply_Cnt><!-- 衍生数据 借款申请衍生 -->") .append(
				"			<Same_Wfmac_Apply_Cnt>") .append(Same_Wfmac_Apply_Cnt) .append("</Same_Wfmac_Apply_Cnt><!-- 衍生数据 借款申请衍生 -->") .append(
				"			<Same_Company_Apply_Cnt>") .append(Same_Company_Apply_Cnt) .append("</Same_Company_Apply_Cnt><!-- 衍生数据 职业信息衍生 -->") .append(
				"			<Apply_Divice_Cnt_6m>") .append(Apply_Divice_Cnt_6m) .append("</Apply_Divice_Cnt_6m><!-- 衍生数据 登录衍生 -->") .append(
				"			<Same_Gps_Apply_Cnt_6m>") .append(Same_Gps_Apply_Cnt_6m) .append("</Same_Gps_Apply_Cnt_6m><!-- 衍生数据 借款申请衍生 -->") .append(
				"			<Same_Ip_Apply_Cnt_6m>") .append(Same_Ip_Apply_Cnt_6m) .append("</Same_Ip_Apply_Cnt_6m><!-- 衍生数据 借款申请衍生 -->") .append(
				"			<Same_Wfmac_Apply_Cnt_6m>") .append(Same_Wfmac_Apply_Cnt_6m) .append("</Same_Wfmac_Apply_Cnt_6m><!-- 衍生数据 借款申请衍生 -->") .append(
				"			<Same_Company_Apply_Cnt_6m>") .append(Same_Company_Apply_Cnt_6m) .append("</Same_Company_Apply_Cnt_6m><!-- 衍生数据 职业信息衍生 -->") .append(
				"			<Apply_Divice_Cnt_1d>") .append(Apply_Divice_Cnt_1d) .append("</Apply_Divice_Cnt_1d><!-- 衍生数据 登录衍生 -->") .append(
				"			<Same_Gps_Apply_Cnt_1d>") .append(Same_Gps_Apply_Cnt_1d) .append("</Same_Gps_Apply_Cnt_1d><!-- 衍生数据 借款申请衍生 -->") .append(
				"			<Same_Ip_Apply_Cnt_1d>") .append(Same_Ip_Apply_Cnt_1d) .append("</Same_Ip_Apply_Cnt_1d><!-- 衍生数据 借款申请衍生 -->") .append(
				"			<Same_Wfmac_Apply_Cnt_1d>") .append(Same_Wfmac_Apply_Cnt_1d) .append("</Same_Wfmac_Apply_Cnt_1d><!-- 衍生数据 借款申请衍生 -->") .append(
				"			<Same_Company_Apply_Cnt_1d>") .append(Same_Company_Apply_Cnt_1d) .append("</Same_Company_Apply_Cnt_1d><!-- 衍生数据 职业信息衍生 -->") .append(
				"			<If_Use_Simulator>") .append(If_Use_Simulator) .append("</If_Use_Simulator><!-- 衍生数据 借款申请衍生 -->") .append(
				"			<Work_City_Grade>") .append(Work_City_Grade) .append("</Work_City_Grade><!-- 衍生数据 职业信息衍生 -->") .append(
				"			<House_City_Grade>") .append(House_City_Grade) .append("</House_City_Grade><!-- 衍生数据 职业信息衍生 -->") .append(
				"			<Gps_City_Match_Ctiy>") .append(Gps_City_Match_Ctiy) .append("</Gps_City_Match_Ctiy><!-- 衍生数据 职业信息衍生 -->") .append(
				"			<Gps_City>") .append(Gps_City) .append("</Gps_City><!-- 衍生数据 职业信息衍生 -->") .append(
				"			<Users_Distinct>") .append(Users_Distinct) .append("</Users_Distinct><!-- 衍生数据 紧急联系人结果 -->") .append(
				"			<Rate_7d>") .append(Rate_7d) .append("</Rate_7d><!-- 衍生数据 紧急联系人结果 -->") .append(
				"			<Rate_1m>") .append(Rate_1m) .append("</Rate_1m><!-- 衍生数据 紧急联系人结果 -->") .append(
				"			<Company_If_Hit_Ban_List>") .append(Company_If_Hit_Ban_List) .append("</Company_If_Hit_Ban_List><!-- 衍生数据 职业信息衍生 -->") .append(
				"			<Lcs_12m_M4_Counts>") .append(Lcs_12m_M4_Counts) .append("</Lcs_12m_M4_Counts><!-- 衍生数据 pbc衍生结果 -->") .append(
				"			<Lcs_12m_M2_Counts>") .append(Lcs_12m_M2_Counts) .append("</Lcs_12m_M2_Counts><!-- 衍生数据 pbc衍生结果 -->") .append(
				"			<Ls_12m_M4_Counts>") .append(Ls_12m_M4_Counts) .append("</Ls_12m_M4_Counts><!-- 衍生数据 pbc衍生结果 -->") .append(
				"			<Ls_12m_M2_Counts>") .append(Ls_12m_M2_Counts) .append("</Ls_12m_M2_Counts><!-- 衍生数据 pbc衍生结果 -->") .append(
				"			<Pbc_24m_PastDue_Counts>") .append(Pbc_24m_PastDue_Counts) .append("</Pbc_24m_PastDue_Counts><!-- 衍生数据 pbc衍生结果 -->") .append(
				"			<Pbc_Querys_Num_6m>") .append(Pbc_Querys_Num_6m) .append("</Pbc_Querys_Num_6m><!-- 衍生数据 pbc衍生结果 -->") .append(
				"			<Pbc_Querys_Num_3m>") .append(Pbc_Querys_Num_3m) .append("</Pbc_Querys_Num_3m><!-- 衍生数据 pbc衍生结果 -->") .append(
				"			<Lcs_PastDueFrequency_Count>") .append(Lcs_PastDueFrequency_Count) .append("</Lcs_PastDueFrequency_Count><!-- 衍生数据 pbc衍生结果 -->") .append(
				"			<Lcs_PastDueAmount>") .append(Lcs_PastDueAmount) .append("</Lcs_PastDueAmount><!-- 衍生数据 pbc衍生结果 -->") .append(
				"			<Ls_PastDueFrequency_Count>") .append(Ls_PastDueFrequency_Count) .append("</Ls_PastDueFrequency_Count><!-- 衍生数据 pbc衍生结果 -->") .append(
				"			<Ls_PastDueAmount>") .append(Ls_PastDueAmount) .append("</Ls_PastDueAmount><!-- 衍生数据 pbc衍生结果 -->") .append(
				"			<ApplicantLendflatCnt7d>") .append(ApplicantLendflatCnt7d) .append("</ApplicantLendflatCnt7d><!-- 衍生数据 pbc衍生结果 -->") .append(
				"			<ApplicantLendflatCnt1m>") .append(ApplicantLendflatCnt1m) .append("</ApplicantLendflatCnt1m><!-- 衍生数据 pbc衍生结果 -->") .append(
				"			<ApplicantLendflatCnt3m>") .append(ApplicantLendflatCnt3m) .append("</ApplicantLendflatCnt3m><!-- 衍生数据 pbc衍生结果 -->") .append(
				"			<ApplicantLendflatCnt6m>") .append(ApplicantLendflatCnt6m) .append("</ApplicantLendflatCnt6m><!-- 衍生数据 pbc衍生结果 -->") .append(
				"			<ApplicantLendflatCnt12m>") .append(ApplicantLendflatCnt12m) .append("</ApplicantLendflatCnt12m><!-- 衍生数据 pbc衍生结果 -->") .append(
				"			<IdNoAssoManyApplyInfoCnt3m>") .append(IdNoAssoManyApplyInfoCnt3m) .append("</IdNoAssoManyApplyInfoCnt3m><!-- 衍生数据 同盾衍生结果 -->") .append(
				"			<ApplyInfoAssoManyIdNoCnt3m>") .append(ApplyInfoAssoManyIdNoCnt3m) .append("</ApplyInfoAssoManyIdNoCnt3m><!-- 衍生数据 同盾衍生结果 -->") .append(
				"			<Register_App_Cnt>") .append(Register_App_Cnt) .append("</Register_App_Cnt><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Loan_App_Cnt>") .append(Loan_App_Cnt) .append("</Loan_App_Cnt><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Finance_App_Cnt>") .append(Finance_App_Cnt) .append("</Finance_App_Cnt><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Loan_Or_Finance_App_Cnt>") .append(Loan_Or_Finance_App_Cnt) .append("</Loan_Or_Finance_App_Cnt><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<If_Own_Car>") .append(If_Own_Car) .append("</If_Own_Car><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<If_Pay_Ins>") .append(If_Pay_Ins) .append("</If_Pay_Ins><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<If_Fin_Buy_Pre6>") .append(If_Fin_Buy_Pre6) .append("</If_Fin_Buy_Pre6><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Query_Org_Cnt30d>") .append(Query_Org_Cnt30d) .append("</Query_Org_Cnt30d><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Query_Cnt30d>") .append(Query_Cnt30d) .append("</Query_Cnt30d><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Query_Cnt90d>") .append(Query_Cnt90d) .append("</Query_Cnt90d><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Query_Org_Cnt90d>") .append(Query_Org_Cnt90d) .append("</Query_Org_Cnt90d><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Query_Org_Cnt7d>") .append(Query_Org_Cnt7d) .append("</Query_Org_Cnt7d><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Query_Cnt7d>") .append(Query_Cnt7d) .append("</Query_Cnt7d><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Query_Org_Cnt180d>") .append(Query_Org_Cnt180d) .append("</Query_Org_Cnt180d><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Query_Cnt180d>") .append(Query_Cnt180d) .append("</Query_Cnt180d><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Query_Org_Cnt360d>") .append(Query_Org_Cnt360d) .append("</Query_Org_Cnt360d><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Query_Cnt360d>") .append(Query_Cnt360d) .append("</Query_Cnt360d><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Use_Mobile_Other_Name_Cnt>") .append(Use_Mobile_Other_Name_Cnt) .append("</Use_Mobile_Other_Name_Cnt><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Use_Mobile_Other_Idno_Cnt>") .append(Use_Mobile_Other_Idno_Cnt) .append("</Use_Mobile_Other_Idno_Cnt><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Use_Idno_Other_Mobile_Cnt>") .append(Use_Idno_Other_Mobile_Cnt) .append("</Use_Idno_Other_Mobile_Cnt><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Use_Idno_Other_Name_Cnt>") .append(Use_Idno_Other_Name_Cnt) .append("</Use_Idno_Other_Name_Cnt><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Blacklist_Name_With_Phone>") .append(Blacklist_Name_With_Phone) .append("</Blacklist_Name_With_Phone><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Blacklist_Update_Time_Name_Phone>") .append(Blacklist_Update_Time_Name_Phone) .append("</Blacklist_Update_Time_Name_Phone><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Blacklist_Name_With_Idcard>") .append(Blacklist_Name_With_Idcard) .append("</Blacklist_Name_With_Idcard><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Blacklist_Update_Time_name_Idcard>") .append(Blacklist_Update_Time_name_Idcard) .append("</Blacklist_Update_Time_name_Idcard><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Phone_Gray_Score>") .append(Phone_Gray_Score) .append("</Phone_Gray_Score><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Social_Liveness>") .append(Social_Liveness) .append("</Social_Liveness><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Social_Influence>") .append(Social_Influence) .append("</Social_Influence><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<User_Phone_Province>") .append(User_Phone_Province) .append("</User_Phone_Province><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<User_City>") .append(User_City) .append("</User_City><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<User_Phone_City>") .append(User_Phone_City) .append("</User_Phone_City><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<User_Region>") .append(User_Region) .append("</User_Region><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<User_Phone_Operator>") .append(User_Phone_Operator) .append("</User_Phone_Operator><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<User_Province>") .append(User_Province) .append("</User_Province><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Cnt_To_All>") .append(Cnt_To_All) .append("</Cnt_To_All><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Cnt_Be_All>") .append(Cnt_Be_All) .append("</Cnt_Be_All><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Cnt_All>") .append(Cnt_All) .append("</Cnt_All><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Cnt_Router>") .append(Cnt_Router) .append("</Cnt_Router><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Router_Ratio>") .append(Router_Ratio) .append("</Router_Ratio><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Cnt_To_Black>") .append(Cnt_To_Black) .append("</Cnt_To_Black><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Cnt_Be_Black>") .append(Cnt_Be_Black) .append("</Cnt_Be_Black><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Cnt_Black>") .append(Cnt_Black) .append("</Cnt_Black><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Black_Ratio>") .append(Black_Ratio) .append("</Black_Ratio><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Cnt_Black2>") .append(Cnt_Black2) .append("</Cnt_Black2><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Cnt_To_Applied>") .append(Cnt_To_Applied) .append("</Cnt_To_Applied><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Cnt_Be_Applied>") .append(Cnt_Be_Applied) .append("</Cnt_Be_Applied><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Cnt_Applied>") .append(Cnt_Applied) .append("</Cnt_Applied><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Recent_Time_To_All>") .append(Recent_Time_To_All) .append("</Recent_Time_To_All><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Recent_Time_Be_All>") .append(Recent_Time_Be_All) .append("</Recent_Time_Be_All><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Recent_Time_To_Black>") .append(Recent_Time_To_Black) .append("</Recent_Time_To_Black><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Recent_Time_Be_Black>") .append(Recent_Time_Be_Black) .append("</Recent_Time_Be_Black><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Recent_Time_To_Applied>") .append(Recent_Time_To_Applied) .append("</Recent_Time_To_Applied><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Recent_Time_Be_Applied>") .append(Recent_Time_Be_Applied) .append("</Recent_Time_Be_Applied><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Call_Cnt_To_All>") .append(Call_Cnt_To_All) .append("</Call_Cnt_To_All><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Call_Cnt_Be_All>") .append(Call_Cnt_Be_All) .append("</Call_Cnt_Be_All><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Call_Cnt_To_Black>") .append(Call_Cnt_To_Black) .append("</Call_Cnt_To_Black><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Call_Cnt_Be_Black>") .append(Call_Cnt_Be_Black) .append("</Call_Cnt_Be_Black><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Call_Cnt_To_Applied>") .append(Call_Cnt_To_Applied) .append("</Call_Cnt_To_Applied><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Call_Cnt_Be_Applied>") .append(Call_Cnt_Be_Applied) .append("</Call_Cnt_Be_Applied><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Time_Spent_To_All>") .append(Time_Spent_To_All) .append("</Time_Spent_To_All><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Time_Spent_Be_All>") .append(Time_Spent_Be_All) .append("</Time_Spent_Be_All><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Time_Spent_To_Black>") .append(Time_Spent_To_Black) .append("</Time_Spent_To_Black><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Time_Spent_Be_Black>") .append(Time_Spent_Be_Black) .append("</Time_Spent_Be_Black><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Time_Spent_To_Applied>") .append(Time_Spent_To_Applied) .append("</Time_Spent_To_Applied><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Time_Spent_Be_Applied>") .append(Time_Spent_Be_Applied) .append("</Time_Spent_Be_Applied><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Weight_To_All>") .append(Weight_To_All) .append("</Weight_To_All><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Weight_Be_All>") .append(Weight_Be_All) .append("</Weight_Be_All><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Weight_All>") .append(Weight_All) .append("</Weight_All><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Weight_To_Black>") .append(Weight_To_Black) .append("</Weight_To_Black><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Weight_Be_Black>") .append(Weight_Be_Black) .append("</Weight_Be_Black><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Weight_Black>") .append(Weight_Black) .append("</Weight_Black><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Weight_To_Applied>") .append(Weight_To_Applied) .append("</Weight_To_Applied><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Weight_Be_Applied>") .append(Weight_Be_Applied) .append("</Weight_Be_Applied><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Weight_Applied>") .append(Weight_Applied) .append("</Weight_Applied><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Most_Familiar_To_All>") .append(Most_Familiar_To_All) .append("</Most_Familiar_To_All><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Most_Familiar_Be_All>") .append(Most_Familiar_Be_All) .append("</Most_Familiar_Be_All><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Most_Familiar_All>") .append(Most_Familiar_All) .append("</Most_Familiar_All><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Most_Familiar_To_Applied>") .append(Most_Familiar_To_Applied) .append("</Most_Familiar_To_Applied><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Most_Familiar_Be_Applied>") .append(Most_Familiar_Be_Applied) .append("</Most_Familiar_Be_Applied><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Most_Familiar_Applied>") .append(Most_Familiar_Applied) .append("</Most_Familiar_Applied><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<To_Max>") .append(To_Max) .append("</To_Max><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<To_Mean>") .append(To_Mean) .append("</To_Mean><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<To_Min>") .append(To_Min) .append("</To_Min><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Be_Max>") .append(Be_Max) .append("</Be_Max><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Be_Mean>") .append(Be_Mean) .append("</Be_Mean><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Be_Min>") .append(Be_Min) .append("</Be_Min><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Max_Linkman>0</Max_Linkman><!-- 无 -->") .append(
				"			<Mean_Linkman>0</Mean_Linkman><!-- 无 -->") .append(
				"			<Min_Linkman>0</Min_Linkman><!-- 无 -->") .append(
				"			<To_Is_Familiar>") .append(To_Is_Familiar) .append("</To_Is_Familiar><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<To_Median_Familiar>") .append(To_Median_Familiar) .append("</To_Median_Familiar><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<To_Not_Familiar>") .append(To_Not_Familiar) .append("</To_Not_Familiar><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Be_Is_Familiar>") .append(Be_Is_Familiar) .append("</Be_Is_Familiar><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Be_Median_Familiar>") .append(Be_Median_Familiar) .append("</Be_Median_Familiar><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Be_Not_Familiar>") .append(Be_Not_Familiar) .append("</Be_Not_Familiar><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Is_Familiar>") .append(Is_Familiar) .append("</Is_Familiar><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Median_Familiar>") .append(Median_Familiar) .append("</Median_Familiar><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Not_Familiar>") .append(Not_Familiar) .append("</Not_Familiar><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->") .append(
				"			<Operator_Grade></Operator_Grade><!-- 无 -->") .append(
				"			<Is_InterWhiteList></Is_InterWhiteList><!--  无  -->") .append(
				"			<WhiteList_Type></WhiteList_Type><!--  无  -->") .append(
				"			<WhiteList_PreAmount>0</WhiteList_PreAmount><!--  无  -->") .append(
				"		</APS_Wangdai_Variable>") .append(
				"	</APS_Wangdai_Variable>") .append(
				"</ProcessManagerRequest>") .append(
				"").toString();
		
		return xml;
	}
	
	
	private int getAge(String birthday) {
		birthday = birthday.replaceAll("年", "-").replaceAll("月", "-").replaceAll("日", "");
		return AgeUtils.getAgeFromBirthTime(birthday);
	}
	
	private String getBirthFormat(String str) {
		return str.replaceAll("年", "-").replaceAll("月", "-").replaceAll("日", "");
	}
	
	
	public static void main(String[] args ) {
		System.out.println(PMXmlStringUtils2.getPMRequestXML(null,null,null,null,null,null,null, new JSONObject().parseObject(inJson).getJSONObject("data"),null));
	}
}
