package com.jeeplus.modules.app.api.pm;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.app.entity.ClCapChannel;
import com.jeeplus.modules.app.entity.ClCrdtExt;
import com.jeeplus.modules.app.entity.ClIdCardInfo;
import com.jeeplus.modules.app.entity.ClPrdInfo;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.entity.ClUsrApply;
import com.jeeplus.modules.app.entity.ClUsrInfo;
import com.jeeplus.modules.app.utils.AgeUtils;

public class PMXmlStringUtils {

	public String getPMRequestXML(String CallType ,ClCapChannel capChannel, ClPrdInfo prdInfo,ClUsrApply usrApply,ClUsr usr, ClUsrInfo usrInfo,
			ClIdCardInfo idCardInfo, JSONObject usrCnts, JSONObject wdData,BigDecimal premium,ClCrdtExt crdtExt) {
		
		String CallTypeParam = "WD1";
		String Final_Limit_Reason_Code="CL_CP_0004_00000025";
		if(CallType.equals("2")) {
			CallTypeParam = "WD2";
			Final_Limit_Reason_Code="CL_CP_0005_00000016";
		}
		
		
		JSONObject wd_anti_spoofing = wdData.getJSONObject("wd_anti_spoofing");
		JSONObject wd_apply_submit = wdData.getJSONObject("wd_apply_submit");
		JSONObject wd_bank_match_blacklist = wdData.getJSONObject("wd_bank_match_blacklist");
		JSONObject wd_career = wdData.getJSONObject("wd_career");
		JSONObject wd_career_match_blacklist = wdData.getJSONObject("wd_career_match_blacklist");
		JSONObject wd_contact_match_blacklist = wdData.getJSONObject("wd_contact_match_blacklist");
		JSONObject wd_inner_bill = wdData.getJSONObject("wd_inner_bill");
		JSONObject wd_inner_refuse = wdData.getJSONObject("wd_inner_refuse");
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
		JSONObject police_blacklist = wdData.getJSONObject("police_blacklist");
		
		
		String resident_address = "";
		if(wd_ocr_match_blacklist!=null && !wd_ocr_match_blacklist.isEmpty()) {
			resident_address = wd_ocr_match_blacklist.getString("resident_address") == null ? "" : wd_ocr_match_blacklist.getString("resident_address");
		}
		String card_level ="";
		if(wd_yl!=null && !wd_yl.isEmpty()) {
			card_level = wd_yl.getString("card_level") == null ? "" : wd_yl.getString("card_level");
		}
		
		String mask_times = "";
		String screen_times = "";
		String Public_Security_Query = "";
		if(wd_anti_spoofing != null && !wd_anti_spoofing.isEmpty()) {
			mask_times = wd_anti_spoofing.getString("mask_times")== null ? "" : wd_anti_spoofing.getString("mask_times");
			screen_times = wd_anti_spoofing.getString("screen_times")== null ? "" : wd_anti_spoofing.getString("screen_times");
			Public_Security_Query= wd_anti_spoofing.getString("face_score")== null ? "" : wd_anti_spoofing.getString("face_score");
		}
		
		String currentOverdueAccount = "0";
		String IsOver30dOverdue6m = "0";
		String PremiumOverdueMonth6m = "0";
		String PremiumTimeOver7dCnt = "0";
		String IsClaimClient = "false";
		String IsForHighRiskRefuse = "false";
		String NoSettleLoanCnt = "0";
		String NetLoanMaxOverDays3m = "0";
		String Applicant_If_AsContact_Apply = "false";
		String Applicant_Contact_If_Apply = "false";
		String Applicant_Contact_If_AsContact_Apply = "false";
		String Applicant_Contact_If_Mutual_Contact = "false";
		String Applicant_Contact_AssoApply_If_HighRisk = "false";
		if(wd_inner_bill!=null && !wd_inner_bill.isEmpty()) {
			currentOverdueAccount = wd_inner_bill.getString("overdueamt") == null ? "0" : wd_inner_bill.getString("overdueamt");
			IsOver30dOverdue6m = wd_inner_bill.getString("overdue_6_30") == null ? "false" : wd_inner_bill.getString("overdue_6_30");
			PremiumOverdueMonth6m = wd_inner_bill.getString("no_prm") == null ? "0" : wd_inner_bill.getString("no_prm");
			PremiumTimeOver7dCnt = wd_inner_bill.getString("time_gap") == null ? "0" : wd_inner_bill.getString("time_gap");
			IsClaimClient = wd_inner_bill.getString("is_claim") == null ? "false" : wd_inner_bill.getString("is_claim");
			
			NoSettleLoanCnt = wd_inner_bill.getString("no_finishi_num") == null ? "0" : wd_inner_bill.getString("no_finishi_num");
			NetLoanMaxOverDays3m = "";
			Applicant_If_AsContact_Apply = "false";
		}
		
		if(wd_inner_refuse!=null && !wd_inner_refuse.isEmpty()) {
			IsForHighRiskRefuse = wd_inner_refuse.getString("IS_REFUSE") == null ? "false" : wd_inner_refuse.getString("IS_REFUSE");
		}
		
		if(wd_inner_contact!=null && !wd_inner_contact.isEmpty()) {
			Applicant_Contact_If_Apply = wd_inner_contact.getString("applicant_contact_if_apply") == null ? "false" : wd_inner_contact.getString("applicant_contact_if_apply");
			Applicant_Contact_If_AsContact_Apply = wd_inner_contact.getString("applicant_contact_if_asContact_apply") == null ? "false" : wd_inner_contact.getString("applicant_contact_if_asContact_apply");
			Applicant_Contact_If_Mutual_Contact = wd_inner_contact.getString("applicant_contact_if_mutual_contact") == null ? "false" : wd_inner_contact.getString("applicant_contact_if_mutual_contact");
			Applicant_Contact_AssoApply_If_HighRisk = wd_inner_contact.getString("applicant_contact_assoApply_if_highRisk") == null ? "false" : wd_inner_contact.getString("applicant_contact_assoApply_if_highRisk");
		}
		
		
		String Mobile_Check_Result = "";
		String Mobile_Online_Time_Length = "0";
		String mobile_fee_level = "";
		if(wd_py_mobile_info!=null && !wd_py_mobile_info.isEmpty()) {
			Mobile_Check_Result = wd_py_mobile_info.getString("mobile_check_result") == null ? "" : wd_py_mobile_info.getString("mobile_check_result");
			Mobile_Online_Time_Length = wd_py_mobile_info.getString("mobile_online_time_length") == null ? "0" : wd_py_mobile_info.getString("mobile_online_time_length");
			mobile_fee_level = wd_py_mobile_info.getString("mobile_fee_level") == null ? "0" : wd_py_mobile_info.getString("mobile_fee_level");
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
		String Contact_Has_LoanApp_Cnt = "0";
		if(wd_relationship != null && !wd_relationship.isEmpty()) {
			Msg_Risk_If = wd_relationship.getString("msg_risk_if") == null ? "":wd_relationship.getString("msg_risk_if");
			Msg_Risk_Type = wd_relationship.getString("msg_risk_type") == null ? "":wd_relationship.getString("msg_risk_type");
			Users_Distinct = wd_relationship.getString("users_distinct") == null ? "0":wd_relationship.getString("users_distinct"); 
			Rate_7d = wd_relationship.getString("rate_7d") == null ? "0":wd_relationship.getString("rate_7d");
			Rate_1m = wd_relationship.getString("rate_1m") == null ? "0":wd_relationship.getString("rate_1m");
			Contact_Has_LoanApp_Cnt = wd_relationship.getString("contact_loan_app") == null ? "0":wd_relationship.getString("contact_loan_app");
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
		String pbc_validCard_count = "0";
		String lcs_3m_pastDue_state_maxs = "0";
		String ls_3m_pastDue_state_maxs = "0";
		String lcs_6m_m3_counts = "0";
		String lcs_6m_m2_counts = "0";
		String ls_6m_m3_counts = "0";
		String lcs_24m_pastDue_state_maxs = "0";
		String ls_24m_pastDue_state_maxs = "0";
		String pbc_querys_num_12m = "0";
		String fiveLevel = "0";
		String mobile = "";
		String officeTelephoneNo = "";
		String telephoneNo = "";
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
			pbc_validCard_count = wd_pbc.getString("pbc_validCard_count") == null ? "0":wd_pbc.getString("pbc_validCard_count");
			lcs_3m_pastDue_state_maxs = wd_pbc.getString("lcs_3m_pastDue_state_maxs") == null ? "0":wd_pbc.getString("lcs_3m_pastDue_state_maxs");
			ls_3m_pastDue_state_maxs = wd_pbc.getString("ls_3m_pastDue_state_maxs") == null ? "0":wd_pbc.getString("ls_3m_pastDue_state_maxs");
			lcs_6m_m3_counts = wd_pbc.getString("lcs_6m_m3_counts") == null ? "0":wd_pbc.getString("lcs_6m_m3_counts");
			lcs_6m_m2_counts = wd_pbc.getString("lcs_6m_m2_counts") == null ? "0":wd_pbc.getString("lcs_6m_m2_counts");
			ls_6m_m3_counts = wd_pbc.getString("ls_6m_m3_counts") == null ? "0":wd_pbc.getString("ls_6m_m3_counts");
			lcs_24m_pastDue_state_maxs = wd_pbc.getString("lcs_24m_pastDue_state_maxs") == null ? "0":wd_pbc.getString("lcs_24m_pastDue_state_maxs");
			ls_24m_pastDue_state_maxs = wd_pbc.getString("ls_24m_pastDue_state_maxs") == null ? "0":wd_pbc.getString("ls_24m_pastDue_state_maxs");
			pbc_querys_num_12m = wd_pbc.getString("pbc_querys_num_12m") == null ? "0":wd_pbc.getString("pbc_querys_num_12m");
			fiveLevel = wd_pbc.getString("fiveLevel") == null ? "0":wd_pbc.getString("fiveLevel");
			mobile = wd_pbc.getString("mobile") == null ? "0":wd_pbc.getString("mobile");
			officeTelephoneNo = wd_pbc.getString("officeTelephoneNo") == null ? "0":wd_pbc.getString("officeTelephoneNo");
			telephoneNo = wd_pbc.getString("telephoneNo") == null ? "0":wd_pbc.getString("telephoneNo");
		}
		
		
		String ApplicantLendflatCnt7d = "0";
		String ApplicantLendflatCnt1m = "0";
		String ApplicantLendflatCnt3m = "0";
		String ApplicantLendflatCnt6m = "0";
		String ApplicantLendflatCnt12m = "0";
		String IdNoAssoManyApplyInfoCnt3m = "0";
		String ApplyInfoAssoManyIdNoCnt3m = "0";
		String idNoZhuxueLoanArrearage = "";
		String idNoHitCourtShixinNamelist = "";
		String idNoHitCrimeNamelist = "";
		String idNoHitDebitfirmNamelist = "";
		String idNoHitXinLoanYuQiList = "";
		String idNoNameHittLoanYuQiBlurlist = "";
		String idNoNameHitCourtShixinBlurlist = "";
		String idNoHitOweTaxNamelist = "";
		String mobileHitShamLib = "";
		String mobileHitCommuLib = "";
		String mobileHitXinLoanYuQiList = "";
		String addressHitXinLoanYuQiPayList = "";
		String mobileRegexError = "";
		String loanDeviceProxyRecog = "";
		String idNoNameHitCourtActionBlurlist = "";
		String idNoNameHitCourtEndCaseBlurlist = "";
		String idNoHitCourtActionNamelist = "";
		String idNoHitCourtEndCaseNamelist = "";
		if(wd_td_rule_detail != null && !wd_td_rule_detail.isEmpty()) {
			ApplicantLendflatCnt7d = wd_td_rule_detail.getString("applicantLendflatCnt7d") == null ? "0":wd_td_rule_detail.getString("applicantLendflatCnt7d"); 
			ApplicantLendflatCnt1m = wd_td_rule_detail.getString("applicantLendflatCnt1m") == null ? "0":wd_td_rule_detail.getString("applicantLendflatCnt1m");
			ApplicantLendflatCnt3m = wd_td_rule_detail.getString("applicantLendflatCnt3m") == null ? "0":wd_td_rule_detail.getString("applicantLendflatCnt3m");
			ApplicantLendflatCnt6m = wd_td_rule_detail.getString("applicantLendflatCnt6m") == null ? "0":wd_td_rule_detail.getString("applicantLendflatCnt6m");
			ApplicantLendflatCnt12m = wd_td_rule_detail.getString("applicantLendflatCnt12m") == null ? "0":wd_td_rule_detail.getString("applicantLendflatCnt12m");
			IdNoAssoManyApplyInfoCnt3m = wd_td_rule_detail.getString("idNoAssoManyApplyInfoCnt3m") == null ? "0":wd_td_rule_detail.getString("idNoAssoManyApplyInfoCnt3m");
			ApplyInfoAssoManyIdNoCnt3m = wd_td_rule_detail.getString("applyInfoAssoManyIdNoCnt3m") == null ? "0":wd_td_rule_detail.getString("applyInfoAssoManyIdNoCnt3m");
			idNoZhuxueLoanArrearage = wd_td_rule_detail.getString("idNoZhuxueLoanArrearage") == null ? "0":wd_td_rule_detail.getString("idNoZhuxueLoanArrearage");
			idNoHitCourtShixinNamelist = wd_td_rule_detail.getString("idNoHitCourtShixinNamelist") == null ? "0":wd_td_rule_detail.getString("idNoHitCourtShixinNamelist");
			idNoHitCrimeNamelist = wd_td_rule_detail.getString("idNoHitCrimeNamelist") == null ? "0":wd_td_rule_detail.getString("idNoHitCrimeNamelist");
			idNoHitDebitfirmNamelist = wd_td_rule_detail.getString("idNoHitDebitfirmNamelist") == null ? "0":wd_td_rule_detail.getString("idNoHitDebitfirmNamelist");
			idNoHitXinLoanYuQiList = wd_td_rule_detail.getString("idNoHitXinLoanYuQiList") == null ? "0":wd_td_rule_detail.getString("idNoHitXinLoanYuQiList");
			idNoNameHittLoanYuQiBlurlist = wd_td_rule_detail.getString("idNoNameHittLoanYuQiBlurlist") == null ? "0":wd_td_rule_detail.getString("idNoNameHittLoanYuQiBlurlist");
			idNoNameHitCourtShixinBlurlist = wd_td_rule_detail.getString("idNoNameHitCourtShixinBlurlist") == null ? "0":wd_td_rule_detail.getString("idNoNameHitCourtShixinBlurlist");
			idNoHitOweTaxNamelist = wd_td_rule_detail.getString("idNoHitOweTaxNamelist") == null ? "0":wd_td_rule_detail.getString("idNoHitOweTaxNamelist");
			mobileHitShamLib = wd_td_rule_detail.getString("mobileHitShamLib") == null ? "0":wd_td_rule_detail.getString("mobileHitShamLib");
			mobileHitCommuLib = wd_td_rule_detail.getString("mobileHitCommuLib") == null ? "0":wd_td_rule_detail.getString("mobileHitCommuLib");
			mobileHitXinLoanYuQiList = wd_td_rule_detail.getString("mobileHitXinLoanYuQiList") == null ? "0":wd_td_rule_detail.getString("mobileHitXinLoanYuQiList");
			addressHitXinLoanYuQiPayList = wd_td_rule_detail.getString("addressHitXinLoanYuQiPayList") == null ? "0":wd_td_rule_detail.getString("addressHitXinLoanYuQiPayList");
			mobileRegexError = wd_td_rule_detail.getString("mobileRegexError") == null ? "0":wd_td_rule_detail.getString("mobileRegexError");
			loanDeviceProxyRecog = wd_td_rule_detail.getString("loanDeviceProxyRecog") == null ? "0":wd_td_rule_detail.getString("loanDeviceProxyRecog");
			idNoNameHitCourtActionBlurlist = wd_td_rule_detail.getString("idNoNameHitCourtActionBlurlist") == null ? "0":wd_td_rule_detail.getString("idNoNameHitCourtActionBlurlist");
			idNoNameHitCourtEndCaseBlurlist = wd_td_rule_detail.getString("idNoNameHitCourtEndCaseBlurlist") == null ? "0":wd_td_rule_detail.getString("idNoNameHitCourtEndCaseBlurlist");
			idNoHitCourtActionNamelist = wd_td_rule_detail.getString("idNoHitCourtActionNamelist") == null ? "0":wd_td_rule_detail.getString("idNoHitCourtActionNamelist");
			idNoHitCourtEndCaseNamelist = wd_td_rule_detail.getString("idNoHitCourtEndCaseNamelist") == null ? "0":wd_td_rule_detail.getString("idNoHitCourtEndCaseNamelist");
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
			if(Blacklist_Update_Time_Name_Phone.equals("-99")) {
				Blacklist_Update_Time_Name_Phone = "";
			}else if(!"".equals(Blacklist_Update_Time_Name_Phone)){
				try {
					Blacklist_Update_Time_Name_Phone = DateUtils.formatDate(DateUtils.parseDate(Blacklist_Update_Time_Name_Phone),"yyyy-MM-dd'T'HH:mm:ss.SSS");
				} catch (Exception e) {
					// TODO: handle exception
					Blacklist_Update_Time_Name_Phone = "";
				}
			}
			
			
			Blacklist_Name_With_Idcard = wd_jxl_honey_pot_report.getString("blacklist_name_with_idcard") == null ? "false":wd_jxl_honey_pot_report.getString("blacklist_name_with_idcard");
			Blacklist_Update_Time_name_Idcard =  wd_jxl_honey_pot_report.getString("blacklist_update_time_name_idcard") == null ? "":wd_jxl_honey_pot_report.getString("blacklist_update_time_name_idcard");
			if(Blacklist_Update_Time_name_Idcard.equals("-99")) {
				Blacklist_Update_Time_name_Idcard = "";
			}else if(!"".equals(Blacklist_Update_Time_name_Idcard)){
				try {
					Blacklist_Update_Time_name_Idcard = DateUtils.formatDate(DateUtils.parseDate(Blacklist_Update_Time_name_Idcard),"yyyy-MM-dd'T'HH:mm:ss.SSS");
				} catch (Exception e) {
					// TODO: handle exception
					Blacklist_Update_Time_name_Idcard = "";
				}
			}
			
			
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
			if(Recent_Time_To_All.contains("{")) {
				JSONObject tmp = JSONObject.parseObject(Recent_Time_To_All);
				Recent_Time_To_All = tmp.getString("$numberLong");
			}
			if("-99".equals(Recent_Time_To_All)) {
				Recent_Time_To_All = "";
			}
			Recent_Time_Be_All = wd_jxl_honey_pot_report.getString("recent_time_be_all") == null ? "":wd_jxl_honey_pot_report.getString("recent_time_be_all");
			if(Recent_Time_Be_All.contains("{")) {
				JSONObject tmp = JSONObject.parseObject(Recent_Time_Be_All);
				Recent_Time_Be_All = tmp.getString("$numberLong");
			}
			if("-99".equals(Recent_Time_Be_All)) {
				Recent_Time_Be_All = "";
			}
			Recent_Time_To_Black = wd_jxl_honey_pot_report.getString("recent_time_to_black") == null ? "":wd_jxl_honey_pot_report.getString("recent_time_to_black");
			if("-99".equals(Recent_Time_To_Black)) {
				Recent_Time_To_Black = "";
			}
			Recent_Time_Be_Black = wd_jxl_honey_pot_report.getString("recent_time_be_black") == null ? "":wd_jxl_honey_pot_report.getString("recent_time_be_black");
			if("-99".equals(Recent_Time_Be_Black)) {
				Recent_Time_Be_Black = "";
			}
			Recent_Time_To_Applied = wd_jxl_honey_pot_report.getString("recent_time_to_applied") == null ? "":wd_jxl_honey_pot_report.getString("recent_time_to_applied");
			if("-99".equals(Recent_Time_To_Applied)) {
				Recent_Time_To_Applied = "";
			}
			Recent_Time_Be_Applied = wd_jxl_honey_pot_report.getString("recent_time_be_applied") == null ? "":wd_jxl_honey_pot_report.getString("recent_time_be_applied");
			if("-99".equals(Recent_Time_Be_Applied)) {
				Recent_Time_Be_Applied = "";
			}
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
		
		
		String   huji_city_grade = "";
		if(wd_ocr!= null && !wd_ocr.isEmpty()) {
			huji_city_grade = wd_ocr.getString("huji_city_grade") == null ? "":wd_ocr.getString("huji_city_grade");
		}
		
		
		String Apply_Channel = "";
		if(usr != null) {
			Apply_Channel = StringUtils.isEmpty(usr.getUSR_SOURCE()) ? "" : usr.getUSR_SOURCE(); 
		}
		
		String Apply_Limit = "0";
		String Loan_Period = "";
		String Loan_Usage = "";
		String Application_Number = "";
		String Application_Date = "";
		String Fenqi_Product_Code = "";
		
		if (usrApply != null) {
			Apply_Limit = StringUtils.isEmpty(usrApply.getApplyAmt()) ? "0" : usrApply.getApplyAmt();
			Loan_Period = StringUtils.isEmpty(usrApply.getApplyTnr()) ? "0" : usrApply.getApplyTnr();
			Loan_Usage =  StringUtils.isEmpty(usrApply.getApplyFor()) ? "" : usrApply.getApplyFor();
			Application_Number =  StringUtils.isEmpty(usrApply.getPolicyNo()) ? "" : usrApply.getPolicyNo();
			try {
				Date t = DateUtils.parseDate(usrApply.getCrtDt());
				if(t != null) {
					Application_Date = DateUtils.formatDate(t, "yyyy-MM-dd'T'HH:mm:ss.SSS");
				}else {
					Application_Date = DateUtils.formatDate(new Date(), "yyyy-MM-dd'T'HH:mm:ss.SSS");
				}
			} catch (Exception e) {
				// TODO: handle exception
				Application_Date = DateUtils.formatDate(new Date(), "yyyy-MM-dd'T'HH:mm:ss.SSS");
			}
			
			Fenqi_Product_Code = StringUtils.isEmpty(usrApply.getPrdCde()) ? "" : usrApply.getPrdCde();
			Fenqi_Product_Code = Fenqi_Product_Code.toLowerCase();
		}
		
		String Loan_Rate = "";
		String Loan_Type = "";
		if(prdInfo != null) {
			Loan_Rate = usrApply.getIntRate();
			Loan_Type = prdInfo.getPRD_CDE();
		}
		
		
		String Premium = "";
		String Branch = "";
		String Sales_Channel = "";
		String Sales_Person = "";
		String Report_User = "";
		if(capChannel != null) {
			Premium = new BigDecimal(Apply_Limit).multiply(new BigDecimal(capChannel.getSUM_AMOUNT())).subtract(new BigDecimal(Apply_Limit)).toString();
			Branch = capChannel.getCHANNEL_CDE();
			Sales_Channel = capChannel.getMAKE_COM();
			Sales_Person = capChannel.getMAKE_COM();
			Report_User = capChannel.getOPERATOR_CDE();
		}
		String Name = "";
		String Gender = "";
		String Nation = "";
		String Age = "";
		String Birthday = "";
		String Id_Type = "身份证";
		String Id_Number = "";
		String ID_Start_Date = "";
		String ID_Expire_Date = "";
		if(idCardInfo != null) {
		 Name = idCardInfo.getCUST_NAME();
			 Gender = idCardInfo.getINDIV_SEX();
			 Nation = idCardInfo.getINDIV_NATION();
			 Age = getAge(idCardInfo.getBIRTHDAY_DATE())+"";
			 Birthday = getBirthFormat(idCardInfo.getBIRTHDAY_DATE());
			 Id_Type = "身份证";
			 Id_Number = idCardInfo.getID_NO();
			 ID_Start_Date = parseValiDate(idCardInfo.getVALID_DATE1());
			 ID_Expire_Date = parseValiDate(idCardInfo.getVALID_DATE2());
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
		
		
		String Is_InterWhiteList = "N";
		String WhiteList_PreAmount = "0.00";
		String WhiteList_Type = "";
		
		String Final_Decision_Date = "";
		
		if(crdtExt != null) {
			if(crdtExt.getCRDTEXT_TYPE().contains("WhiteList")) {
				Is_InterWhiteList = "Y";
				WhiteList_PreAmount = crdtExt.getADJ_AMT().toString();
			}
			
			try {
				Date t = DateUtils.parseDate(crdtExt.getMDF_DT());
				if(t != null) {
					Final_Decision_Date = DateUtils.formatDate(t, "yyyy-MM-dd'T'HH:mm:ss.SSS");
				}else {
					Final_Decision_Date = DateUtils.formatDate(new Date(), "yyyy-MM-dd'T'HH:mm:ss.SSS");
				}
			} catch (Exception e) {
				// TODO: handle exception
				Final_Decision_Date = DateUtils.formatDate(new Date(), "yyyy-MM-dd'T'HH:mm:ss.SSS");
			}
		}
		
		
		String IsGonganPoor = "";
		String GonganPoor_Type = "";
		
		if(police_blacklist != null && !police_blacklist.isEmpty()) {
			IsGonganPoor = StringUtils.isEmpty(police_blacklist.getString("IsGonganPoor"))? "":police_blacklist.getString("IsGonganPoor");
			GonganPoor_Type = StringUtils.isEmpty(police_blacklist.getString("GonganPoor_Type"))? "":police_blacklist.getString("GonganPoor_Type");
			GonganPoor_Type=GonganPoor_Type.replace(";", "");
		}
		
		
		String xml = new StringBuilder(
//				"<?xml version=\"1.0\" encoding=\"utf-8\"?>").append( 
				"<ProcessManagerRequest>") .append(  
				"<Application_Header>") .append( 
				"<Channel>WDL</Channel>") .append( 
				"<CallType>"+CallTypeParam+"</CallType>") .append(
				"<ApplicationNumber>"+Application_Number+"</ApplicationNumber>") .append(
				"</Application_Header>") .append(
				"<APS_Applications>") .append(
				"<APS_Application>") .append(
				"<Organisation_Code>SIG</Organisation_Code>") .append(
				"<Country_Code>CN</Country_Code>") .append(
				"<Group_Member_Code>SIG</Group_Member_Code>") .append(
				"<Application_Number>").append(Application_Number).append("</Application_Number>") .append(
				"<Apply_Channel>").append(Apply_Channel).append("</Apply_Channel>") .append(
				"<Application_Date>").append(Application_Date).append("</Application_Date>") .append(
				"<Application_Type>WDL</Application_Type>") .append(
				"<Apply_Limit>") .append(Apply_Limit) .append("</Apply_Limit>") .append(
				"<Loan_Period>") .append(Loan_Period) .append("</Loan_Period>") .append(
				"<Loan_Usage>") .append(Loan_Usage) .append("</Loan_Usage>") .append(
				"<Loan_Rate>") .append(Loan_Rate) .append("</Loan_Rate>") .append(
				"<Premium>") .append(Premium) .append("</Premium>") .append(
				"<Branch>") .append(Branch) .append("</Branch>") .append(
				"<Final_Decision>pass</Final_Decision>") .append(
				"<Final_Decision_Date>"+Final_Decision_Date+"</Final_Decision_Date>") .append(
				"<Final_Limit_Reason_Code>"+Final_Limit_Reason_Code+"</Final_Limit_Reason_Code>") .append(
				"<Final_Limit>").append(Apply_Limit).append("</Final_Limit>") .append(
				"<Workflow_Status></Workflow_Status>") .append(
				"<Sales_Channel>") .append(Sales_Channel) .append("</Sales_Channel>") .append(
				"<Sales_Person>") .append(Sales_Person) .append("</Sales_Person>") .append(
				"<Loan_Type>") .append(Loan_Type) .append("</Loan_Type>") .append(
				"<Sales_Level></Sales_Level>") .append(
				"<Shop_Recommended></Shop_Recommended>") .append(
				"<Channel_Company_Name></Channel_Company_Name>") .append(
				"<Cancel_Reapplication_Flag>false</Cancel_Reapplication_Flag>") .append(
				"<Equivalent_Yearly_Insurance_Premium>0</Equivalent_Yearly_Insurance_Premium>") .append(
				"<Required_Payment_Year></Required_Payment_Year>") .append(
				"<Payment_frequency></Payment_frequency>") .append(
				"<Date_Of_Policy>").append(DateUtils.formatDate(new Date(), "yyyy-MM-dd'T'HH:mm:ss.SSS")).append("</Date_Of_Policy>") .append(
				"<External_Debt>0</External_Debt>") .append(
				"<City_Level>"+huji_city_grade+"</City_Level>") .append(
				"<Is_State_Enterprises>false</Is_State_Enterprises>") .append(
				"<Is_SIG_Policy>true</Is_SIG_Policy>") .append(
				"<Equivalent_Yearly_Insurance_Premium2>0</Equivalent_Yearly_Insurance_Premium2>") .append(
				"<Required_Payment_Year2></Required_Payment_Year2>") .append(
				"<Payment_frequency2></Payment_frequency2>") .append(
				"<Date_Of_Policy2></Date_Of_Policy2>") .append(
				"<Is_SIG_Policy2></Is_SIG_Policy2>") .append(
				"<Equivalent_Yearly_Insurance_Premium3>0</Equivalent_Yearly_Insurance_Premium3>") .append(
				"<Required_Payment_Year3></Required_Payment_Year3>") .append(
				"<Payment_frequency3></Payment_frequency3>") .append(
				"<Date_Of_Policy3></Date_Of_Policy3>") .append(
				"<Is_SIG_Policy3></Is_SIG_Policy3>") .append(
				"<Customer_Type></Customer_Type>") .append(
				"<Application_Branch_Code>00370100</Application_Branch_Code>") .append(
				"<Application_Branch>新业务BJ</Application_Branch>") .append(
				"<Sales_Name></Sales_Name>") .append(
				"<Four_Channel_Name>N11407</Four_Channel_Name>") .append(
				"<Fenqi_Product_Code>"+"xjd"+"</Fenqi_Product_Code>") .append(
				"<Fenqi_Payment_Method>1</Fenqi_Payment_Method>") .append(
				"<Guarantee_Method></Guarantee_Method>") .append(
				"<Channel_Type></Channel_Type>") .append(
				"<Business_Origin></Business_Origin>") .append(
				"<Sale_Channel></Sale_Channel>") .append(
				"<First_Audit_Guarantee_Method></First_Audit_Guarantee_Method>") .append(
				"<Apply_Stores_Province>北京</Apply_Stores_Province>") .append(
				"<Apply_Stores_City>北京</Apply_Stores_City>") .append(
				"<Apply_Stores_Distinct>通州</Apply_Stores_Distinct>") .append(
				"<Report_User>") .append(Report_User) .append("</Report_User>") .append(
				"<Report_Team></Report_Team>") .append(
				"<Report_Reason></Report_Reason>") .append(
				"<Report_Note></Report_Note>") .append(
				"<Apply_City></Apply_City>") .append(
				"<Fenqi_Sub_Product_Code>s"+Fenqi_Product_Code+"</Fenqi_Sub_Product_Code>") .append(
				"<Channel_Grade></Channel_Grade>") .append(
				"<Wangdai_Product_Code>xjd</Wangdai_Product_Code>") .append(
				"</APS_Application>") .append(
				"</APS_Applications>") .append(
				"<APS_Applicants>") .append(
				"<APS_Applicant>") .append(
				"<Name>") .append(Name) .append("</Name>") .append(
				"<Gender>") .append(Gender) .append("</Gender>") .append(
				"<Nation>") .append(Nation) .append("</Nation>") .append(
				"<Age>") .append(Age) .append("</Age>") .append(
				"<Birthday>") .append(Birthday) .append("</Birthday>") .append(
				"<Id_Type>") .append(Id_Type) .append("</Id_Type>") .append(
				"<Id_Number>") .append(Id_Number) .append("</Id_Number>") .append(
				"<Marriage></Marriage>") .append(
				"<Education></Education>") .append(
				"<Home_Address_State>") .append(Home_Address_State) .append("</Home_Address_State>") .append(
				"<Home_Address_City>") .append(Home_Address_City) .append("</Home_Address_City>") .append(
				"<Home_Address_County>") .append(Home_Address_County) .append("</Home_Address_County>") .append(
				"<Home_Address_Street_Address>") .append(Home_Address_Street_Address) .append("</Home_Address_Street_Address>") .append(
				"<Home_Address_Zip></Home_Address_Zip>") .append(
				"<Home_Phone></Home_Phone>") .append(
				"<Mobile>") .append(Mobile) .append("</Mobile>") .append(
				"<Email></Email>") .append(
				"<Is_Private_Employer>0</Is_Private_Employer>") .append(
				"<Company_Name>") .append(Company_Name) .append("</Company_Name>") .append(
				"<Company_Address_State>") .append(Company_Address_State) .append("</Company_Address_State>") .append(
				"<Company_Address_City>") .append(Company_Address_City) .append("</Company_Address_City>") .append(
				"<Company_Address_County>") .append(Company_Address_County) .append("</Company_Address_County>") .append(
				"<Company_Address_Street_Address>") .append(Company_Address_Street_Address) .append("</Company_Address_Street_Address>") .append(
				"<Company_Address_Zip></Company_Address_Zip>") .append(
				"<Company_Phone>") .append(Company_Phone) .append("</Company_Phone>") .append(
				"<Department></Department>") .append(
				"<Industry_Code></Industry_Code>") .append(
				"<Position_Code></Position_Code>") .append(
				"<Employed_Date></Employed_Date>") .append(
				"<Base_Salary>0</Base_Salary>") .append(
				"<Other_Income>0</Other_Income>") .append(
				"<Total_Income>0</Total_Income>") .append(
				"<Payment_Date>0</Payment_Date>") .append(
				"<Foster_Count>0</Foster_Count>") .append(
				"<Has_Social_Security>0</Has_Social_Security>") .append(
				"<Has_Vehicle>0</Has_Vehicle>") .append(
				"<Has_Property>0</Has_Property>") .append(
				"<Has_PBOC>0</Has_PBOC>") .append(
				"<Has_Bank_Statement>0</Has_Bank_Statement>") .append(
				"<Plate_Number></Plate_Number>") .append(
				"<Additional_Mobile></Additional_Mobile>") .append(
				"<Additional_Company_Phone></Additional_Company_Phone>") .append(
				"<Spouse_Name></Spouse_Name>") .append(
				"<Spouse_Mobile>") .append(telephoneNo) .append("</Spouse_Mobile>") .append(
				"<Vehicle_Type></Vehicle_Type>") .append(
				"<Enterprise_Type></Enterprise_Type>") .append(
				"<Job_Grading></Job_Grading>") .append(
				"<Businese_Condition></Businese_Condition>") .append(
				"<Has_Multi_Property></Has_Multi_Property>") .append(
				"<Verification_Income>0</Verification_Income>") .append(
				"<Monthly_Total_Income>0</Monthly_Total_Income>") .append(
				"<City_Class></City_Class>") .append(
				"<Company_Nature></Company_Nature>") .append(
				"<Property_Number></Property_Number>") .append(
				"<Rent_Addr_Province></Rent_Addr_Province>") .append(
				"<Rent_Addr_City></Rent_Addr_City>") .append(
				"<Rent_Addr_District></Rent_Addr_District>") .append(
				"<Rent_Addr_Detail></Rent_Addr_Detail>") .append(
				"<Decorate_Addr></Decorate_Addr>") .append(
				"<Frame_Number></Frame_Number>") .append(
				"<ID_Start_Date>").append(ID_Start_Date).append("</ID_Start_Date>") .append(
				"<ID_Expire_Date>").append(ID_Expire_Date).append("</ID_Expire_Date>") .append(
				"<ID_Address_Province></ID_Address_Province>") .append(
				"<ID_Address_City></ID_Address_City>") .append(
				"<ID_Address_District></ID_Address_District>") .append(
				"<ID_Address_Detail></ID_Address_Detail>") .append(
				"<Occupation_Classing></Occupation_Classing>") .append(
				"<Employ_Date></Employ_Date>") .append(
				"<Inhabit_Start_Date></Inhabit_Start_Date>") .append(
				"<In_Insurance_City_Date></In_Insurance_City_Date>") .append(
				"<Private_Business_Owner_Premises></Private_Business_Owner_Premises>") .append(
				"<Private_Business_Equity_Rate></Private_Business_Equity_Rate>") .append(
				"<Pay_Off_Method></Pay_Off_Method>") .append(
				"<registDate>") .append(DateUtils.formatDate(DateUtils.parseDate(usr.getCRT_DT()), "yyyy-MM-dd'T'HH:mm:ss.SSS")) .append("</registDate>") .append(
				"<registFund>0</registFund>") .append(
				"<fundCurrency></fundCurrency>") .append(
				"<artificialName></artificialName>") .append(
				"<manageBeginDate></manageBeginDate>") .append(
				"<manageEndDate></manageEndDate>") .append(
				"<corpTypeID></corpTypeID>") .append(
				"<corpTypeCaption></corpTypeCaption>") .append(
				"<staffNumber>0</staffNumber>") .append(
				"<tradeCode></tradeCode>") .append(
				"<Is_shareholder></Is_shareholder>") .append(
				"<Contributive_Proportion>0</Contributive_Proportion>") .append(
				"<Is_Same_Legal_Name></Is_Same_Legal_Name>") .append(
				"<Is_Exist_important_Person></Is_Exist_important_Person>") .append(
				"<logoutDate>") .append(DateUtils.formatDate(DateUtils.parseDate(usr.getUSR_LAST_DT()), "yyyy-MM-dd'T'HH:mm:ss.SSS")) .append("</logoutDate>") .append(
				"<revokeDate></revokeDate>") .append(
				"<StatusID></StatusID>") .append(
				"<statusCaption></statusCaption>") .append(
				"<PengY_Score>0</PengY_Score>") .append(
				"</APS_Applicant>") .append(
				"</APS_Applicants>") .append(
				"<APS_Contacts>") .append(
				"<APS_Contact>") .append(
				"<Name>") .append(Cnts_Name1) .append("</Name>") .append(
				"<Gender></Gender>") .append(
				"<Id_Number></Id_Number>") .append(
				"<Age>0</Age>") .append(
				"<Relation>") .append(Cnts_Relation1) .append("</Relation>") .append(
				"<Contact_Home_Phone></Contact_Home_Phone>") .append(
				"<Contact_Mobile>") .append(Cnts_Mobile1) .append("</Contact_Mobile>") .append(
				"<Contact_Company_Phone></Contact_Company_Phone>") .append(
				"<Additional_Mobile></Additional_Mobile>") .append(
				"<Additional_Company_Phone></Additional_Company_Phone>") .append(
				"</APS_Contact>") .append(
				"</APS_Contacts>") .append(
				"<PBOC_Personal_Infos>") .append(
				"<PBOC_Personal_Info>") .append(
				"<Name>") .append(PBOC_NAME) .append("</Name>") .append(
				"<Gender>") .append(PBOC_Gender) .append("</Gender>") .append(
				"<ID_Type>") .append(PBOC_IDType) .append("</ID_Type>") .append(
				"<ID_Number>") .append(PBOC_IDNO) .append("</ID_Number>") .append(
				"<Birthday>") .append(PBOC_Birthday) .append("</Birthday>") .append(
				"<Education></Education>") .append(
				"<Education_Degree></Education_Degree>") .append(
				"<Mailing_Address></Mailing_Address>") .append(
				"<ID_Address>") .append(resident_address) .append("</ID_Address>") .append(
				"<Home_Phone_Number></Home_Phone_Number>") .append(
				"<Company_Phone_Number>") .append(officeTelephoneNo) .append("</Company_Phone_Number>") .append(
				"<Cell_Phone_Number>") .append(mobile) .append("</Cell_Phone_Number>") .append(
				"<Marital_State></Marital_State>") .append(
				"<Spouse_Name></Spouse_Name>") .append(
				"<Spouse_ID_Type></Spouse_ID_Type>") .append(
				"<Spouse_ID_Number></Spouse_ID_Number>") .append(
				"<Spouse_Company></Spouse_Company>") .append(
				"<Spouse_Contact_Number></Spouse_Contact_Number>") .append(
				"</PBOC_Personal_Info>") .append(
				"</PBOC_Personal_Infos>") .append(
				"<APS_OtherInfo3s>") .append(
				"<APS_OtherInfo3>") .append(
				"<Merchant_ID></Merchant_ID>") .append(
				"<Product_Category></Product_Category>") .append(
				"<Product_Category_Detail></Product_Category_Detail>") .append(
				"<Co_Org_City></Co_Org_City>") .append(
				"<Agency_Rating></Agency_Rating>") .append(
				"<Org_Status></Org_Status>") .append(
				"<Bank_Card></Bank_Card>") .append(
				"<Bank_Mobile></Bank_Mobile>") .append(
				"<ID_Validation></ID_Validation>") .append(
				"<ID_Compare>0</ID_Compare>") .append(
				"<Liveness_Identification></Liveness_Identification>") .append(
				"<Public_Security_Query>").append(Public_Security_Query).append("</Public_Security_Query>") .append(
				"<Register_Address_Province></Register_Address_Province>") .append(
				"<Register_Address_City></Register_Address_City>") .append(
				"<Register_Address_District></Register_Address_District>") .append(
				"<Register_Address_Detail></Register_Address_Detail>") .append(
				"<Income_Source></Income_Source>") .append(
				"</APS_OtherInfo3>") .append(
				"</APS_OtherInfo3s>") .append(
				"<APS_Wangdai_Variables>") .append(
				"<APS_Wangdai_Variable>") .append(
				"<Fail_Times>0</Fail_Times>") .append(
				"<Is_ID_Impersonated>false</Is_ID_Impersonated>") .append(
				"<Software_Times>0</Software_Times>") .append(
				"<Mask_Times>") .append(mask_times) .append("</Mask_Times>") .append(
				"<Screen_Times>") .append(screen_times) .append("</Screen_Times>") .append(
				"<CurrentOverdueAccount>") .append(currentOverdueAccount) .append("</CurrentOverdueAccount>") .append(
				"<IsOver30dOverdue6m>") .append(IsOver30dOverdue6m) .append("</IsOver30dOverdue6m>") .append(
				"<PremiumOverdueMonth6m>") .append(PremiumOverdueMonth6m) .append("</PremiumOverdueMonth6m>") .append(
				"<PremiumTimeOver7dCnt>") .append(PremiumTimeOver7dCnt) .append("</PremiumTimeOver7dCnt>") .append(
				"<IsClaimClient>") .append(IsClaimClient) .append("</IsClaimClient>") .append(
				"<IsForHighRiskRefuse>") .append(IsForHighRiskRefuse) .append("</IsForHighRiskRefuse>") .append(
				"<NoSettleLoanCnt>") .append(NoSettleLoanCnt) .append("</NoSettleLoanCnt>") .append(
				"<NetLoanMaxOverDays3m>") .append(NetLoanMaxOverDays3m) .append("</NetLoanMaxOverDays3m>") .append(
				"<Applicant_If_AsContact_Apply>") .append(Applicant_If_AsContact_Apply) .append("</Applicant_If_AsContact_Apply>") .append(
				"<Applicant_Contact_If_Apply>") .append(Applicant_Contact_If_Apply) .append("</Applicant_Contact_If_Apply>") .append(
				"<Applicant_Contact_If_AsContact_Apply>") .append(Applicant_Contact_If_AsContact_Apply) .append("</Applicant_Contact_If_AsContact_Apply>") .append(
				"<Applicant_Contact_If_Mutual_Contact>") .append(Applicant_Contact_If_Mutual_Contact) .append("</Applicant_Contact_If_Mutual_Contact>") .append(
				"<Applicant_Contact_AssoApplicat_If_HighRisk></Applicant_Contact_AssoApplicat_If_HighRisk>") .append(
				"<Applicant_Contact_AssoApply_If_HighRisk>") .append(Applicant_Contact_AssoApply_If_HighRisk) .append("</Applicant_Contact_AssoApply_If_HighRisk>") .append(
				"<Mobile_Check_Result>") .append(Mobile_Check_Result) .append("</Mobile_Check_Result>") .append(
				"<Mobile_Online_Time_Length>") .append(Mobile_Online_Time_Length) .append("</Mobile_Online_Time_Length>") .append(
				"<Install_Loan_App_Cnt>") .append(Install_Loan_App_Cnt) .append("</Install_Loan_App_Cnt>") .append(
				"<Install_Shares_App_Cnt>") .append(Install_Shares_App_Cnt) .append("</Install_Shares_App_Cnt>") .append(
				"<Install_Lottery_App_Cnt>") .append(Install_Lottery_App_Cnt) .append("</Install_Lottery_App_Cnt>") .append(
				"<Install_Finance_App_Cnt>") .append(Install_Finance_App_Cnt) .append("</Install_Finance_App_Cnt>") .append(
				"<Install_Virtual_App_Cnt>") .append(Install_Virtual_App_Cnt) .append("</Install_Virtual_App_Cnt>") .append(
				"<Contact_Has_LoanApp_Cnt>") .append(Contact_Has_LoanApp_Cnt) .append("</Contact_Has_LoanApp_Cnt>") .append(
				"<Msg_Risk_If>") .append(Msg_Risk_If) .append("</Msg_Risk_If>") .append(
				"<Msg_Risk_Type>") .append(Msg_Risk_Type) .append("</Msg_Risk_Type>") .append(
				"<Current_Divice_Apply_Cnt>") .append(Current_Divice_Apply_Cnt) .append("</Current_Divice_Apply_Cnt>") .append(
				"<Apply_Divice_Cnt>") .append(Apply_Divice_Cnt) .append("</Apply_Divice_Cnt>") .append(
				"<Same_Gps_Apply_Cnt>") .append(Same_Gps_Apply_Cnt) .append("</Same_Gps_Apply_Cnt>") .append(
				"<Same_Ip_Apply_Cnt>") .append(Same_Ip_Apply_Cnt) .append("</Same_Ip_Apply_Cnt>") .append(
				"<Same_Wfmac_Apply_Cnt>") .append(Same_Wfmac_Apply_Cnt) .append("</Same_Wfmac_Apply_Cnt>") .append(
				"<Same_Company_Apply_Cnt>") .append(Same_Company_Apply_Cnt) .append("</Same_Company_Apply_Cnt>") .append(
				"<Apply_Divice_Cnt_6m>") .append(Apply_Divice_Cnt_6m) .append("</Apply_Divice_Cnt_6m>") .append(
				"<Same_Gps_Apply_Cnt_6m>") .append(Same_Gps_Apply_Cnt_6m) .append("</Same_Gps_Apply_Cnt_6m>") .append(
				"<Same_Ip_Apply_Cnt_6m>") .append(Same_Ip_Apply_Cnt_6m) .append("</Same_Ip_Apply_Cnt_6m>") .append(
				"<Same_Wfmac_Apply_Cnt_6m>") .append(Same_Wfmac_Apply_Cnt_6m) .append("</Same_Wfmac_Apply_Cnt_6m>") .append(
				"<Same_Company_Apply_Cnt_6m>") .append(Same_Company_Apply_Cnt_6m) .append("</Same_Company_Apply_Cnt_6m>") .append(
				"<Apply_Divice_Cnt_1d>") .append(Apply_Divice_Cnt_1d) .append("</Apply_Divice_Cnt_1d>") .append(
				"<Same_Gps_Apply_Cnt_1d>") .append(Same_Gps_Apply_Cnt_1d) .append("</Same_Gps_Apply_Cnt_1d>") .append(
				"<Same_Ip_Apply_Cnt_1d>") .append(Same_Ip_Apply_Cnt_1d) .append("</Same_Ip_Apply_Cnt_1d>") .append(
				"<Same_Wfmac_Apply_Cnt_1d>") .append(Same_Wfmac_Apply_Cnt_1d) .append("</Same_Wfmac_Apply_Cnt_1d>") .append(
				"<Same_Company_Apply_Cnt_1d>") .append(Same_Company_Apply_Cnt_1d) .append("</Same_Company_Apply_Cnt_1d>") .append(
				"<If_Use_Simulator>") .append(If_Use_Simulator) .append("</If_Use_Simulator>") .append(
				"<Work_City_Grade>") .append(Work_City_Grade) .append("</Work_City_Grade>") .append(
				"<House_City_Grade>") .append(House_City_Grade) .append("</House_City_Grade>") .append(
				"<Gps_City_Match_Ctiy>") .append(Gps_City_Match_Ctiy) .append("</Gps_City_Match_Ctiy>") .append(
				"<Gps_City>") .append(Gps_City) .append("</Gps_City>") .append(
				"<Users_Distinct>") .append(Users_Distinct) .append("</Users_Distinct>") .append(
				"<Rate_7d>") .append(Rate_7d) .append("</Rate_7d>") .append(
				"<Rate_1m>") .append(Rate_1m) .append("</Rate_1m>") .append(
				"<Company_If_Hit_Ban_List>") .append(Company_If_Hit_Ban_List) .append("</Company_If_Hit_Ban_List>") .append(
				"<Lcs_12m_M4_Counts>") .append(Lcs_12m_M4_Counts) .append("</Lcs_12m_M4_Counts>") .append(
				"<Lcs_12m_M2_Counts>") .append(Lcs_12m_M2_Counts) .append("</Lcs_12m_M2_Counts>") .append(
				"<Ls_12m_M4_Counts>") .append(Ls_12m_M4_Counts) .append("</Ls_12m_M4_Counts>") .append(
				"<Ls_12m_M2_Counts>") .append(Ls_12m_M2_Counts) .append("</Ls_12m_M2_Counts>") .append(
				"<Pbc_24m_PastDue_Counts>") .append(Pbc_24m_PastDue_Counts) .append("</Pbc_24m_PastDue_Counts>") .append(
				"<Pbc_Querys_Num_6m>") .append(Pbc_Querys_Num_6m) .append("</Pbc_Querys_Num_6m>") .append(
				"<Pbc_Querys_Num_3m>") .append(Pbc_Querys_Num_3m) .append("</Pbc_Querys_Num_3m>") .append(
				"<Lcs_PastDueFrequency_Count>") .append(Lcs_PastDueFrequency_Count) .append("</Lcs_PastDueFrequency_Count>") .append(
				"<Lcs_PastDueAmount>") .append(Lcs_PastDueAmount) .append("</Lcs_PastDueAmount>") .append(
				"<Ls_PastDueFrequency_Count>") .append(Ls_PastDueFrequency_Count) .append("</Ls_PastDueFrequency_Count>") .append(
				"<Ls_PastDueAmount>") .append(Ls_PastDueAmount) .append("</Ls_PastDueAmount>") .append(
				"<ApplicantLendflatCnt7d>") .append(ApplicantLendflatCnt7d) .append("</ApplicantLendflatCnt7d>") .append(
				"<ApplicantLendflatCnt1m>") .append(ApplicantLendflatCnt1m) .append("</ApplicantLendflatCnt1m>") .append(
				"<ApplicantLendflatCnt3m>") .append(ApplicantLendflatCnt3m) .append("</ApplicantLendflatCnt3m>") .append(
				"<ApplicantLendflatCnt6m>") .append(ApplicantLendflatCnt6m) .append("</ApplicantLendflatCnt6m>") .append(
				"<ApplicantLendflatCnt12m>") .append(ApplicantLendflatCnt12m) .append("</ApplicantLendflatCnt12m>") .append(
				"<IdNoAssoManyApplyInfoCnt3m>") .append(IdNoAssoManyApplyInfoCnt3m) .append("</IdNoAssoManyApplyInfoCnt3m>") .append(
				"<ApplyInfoAssoManyIdNoCnt3m>") .append(ApplyInfoAssoManyIdNoCnt3m) .append("</ApplyInfoAssoManyIdNoCnt3m>") .append(
				"<Register_App_Cnt>") .append(Register_App_Cnt) .append("</Register_App_Cnt>") .append(
				"<Loan_App_Cnt>") .append(Loan_App_Cnt) .append("</Loan_App_Cnt>") .append(
				"<Finance_App_Cnt>") .append(Finance_App_Cnt) .append("</Finance_App_Cnt>") .append(
				"<Loan_Or_Finance_App_Cnt>") .append(Loan_Or_Finance_App_Cnt) .append("</Loan_Or_Finance_App_Cnt>") .append(
				"<If_Own_Car>") .append(If_Own_Car) .append("</If_Own_Car>") .append(
				"<If_Pay_Ins>") .append(If_Pay_Ins) .append("</If_Pay_Ins>") .append(
				"<If_Fin_Buy_Pre6>") .append(If_Fin_Buy_Pre6) .append("</If_Fin_Buy_Pre6>") .append(
				"<Query_Org_Cnt30d>") .append(Query_Org_Cnt30d) .append("</Query_Org_Cnt30d>") .append(
				"<Query_Cnt30d>") .append(Query_Cnt30d) .append("</Query_Cnt30d>") .append(
				"<Query_Cnt90d>") .append(Query_Cnt90d) .append("</Query_Cnt90d>") .append(
				"<Query_Org_Cnt90d>") .append(Query_Org_Cnt90d) .append("</Query_Org_Cnt90d>") .append(
				"<Query_Org_Cnt7d>") .append(Query_Org_Cnt7d) .append("</Query_Org_Cnt7d>") .append(
				"<Query_Cnt7d>") .append(Query_Cnt7d) .append("</Query_Cnt7d>") .append(
				"<Query_Org_Cnt180d>") .append(Query_Org_Cnt180d) .append("</Query_Org_Cnt180d>") .append(
				"<Query_Cnt180d>") .append(Query_Cnt180d) .append("</Query_Cnt180d>") .append(
				"<Query_Org_Cnt360d>") .append(Query_Org_Cnt360d) .append("</Query_Org_Cnt360d>") .append(
				"<Query_Cnt360d>") .append(Query_Cnt360d) .append("</Query_Cnt360d>") .append(
				"<Use_Mobile_Other_Name_Cnt>") .append(Use_Mobile_Other_Name_Cnt) .append("</Use_Mobile_Other_Name_Cnt>") .append(
				"<Use_Mobile_Other_Idno_Cnt>") .append(Use_Mobile_Other_Idno_Cnt) .append("</Use_Mobile_Other_Idno_Cnt>") .append(
				"<Use_Idno_Other_Mobile_Cnt>") .append(Use_Idno_Other_Mobile_Cnt) .append("</Use_Idno_Other_Mobile_Cnt>") .append(
				"<Use_Idno_Other_Name_Cnt>") .append(Use_Idno_Other_Name_Cnt) .append("</Use_Idno_Other_Name_Cnt>") .append(
				"<Blacklist_Name_With_Phone>") .append(Blacklist_Name_With_Phone) .append("</Blacklist_Name_With_Phone>") .append(
				"<Blacklist_Update_Time_Name_Phone>") .append(Blacklist_Update_Time_Name_Phone) .append("</Blacklist_Update_Time_Name_Phone>") .append(
				"<Blacklist_Name_With_Idcard>") .append(Blacklist_Name_With_Idcard) .append("</Blacklist_Name_With_Idcard>") .append(
				"<Blacklist_Update_Time_name_Idcard>") .append(Blacklist_Update_Time_name_Idcard) .append("</Blacklist_Update_Time_name_Idcard>") .append(
				"<Phone_Gray_Score>") .append(Phone_Gray_Score) .append("</Phone_Gray_Score>") .append(
				"<Social_Liveness>") .append(Social_Liveness) .append("</Social_Liveness>") .append(
				"<Social_Influence>") .append(Social_Influence) .append("</Social_Influence>") .append(
				"<User_Phone_Province>") .append(User_Phone_Province) .append("</User_Phone_Province>") .append(
				"<User_City>") .append(User_City) .append("</User_City>") .append(
				"<User_Phone_City>") .append(User_Phone_City) .append("</User_Phone_City>") .append(
				"<User_Region>") .append(User_Region) .append("</User_Region>") .append(
				"<User_Phone_Operator>") .append(User_Phone_Operator) .append("</User_Phone_Operator>") .append(
				"<User_Province>") .append(User_Province) .append("</User_Province>") .append(
				"<Cnt_To_All>") .append(Cnt_To_All) .append("</Cnt_To_All>") .append(
				"<Cnt_Be_All>") .append(Cnt_Be_All) .append("</Cnt_Be_All>") .append(
				"<Cnt_All>") .append(Cnt_All) .append("</Cnt_All>") .append(
				"<Cnt_Router>") .append(Cnt_Router) .append("</Cnt_Router>") .append(
				"<Router_Ratio>") .append(Router_Ratio) .append("</Router_Ratio>") .append(
				"<Cnt_To_Black>") .append(Cnt_To_Black) .append("</Cnt_To_Black>") .append(
				"<Cnt_Be_Black>") .append(Cnt_Be_Black) .append("</Cnt_Be_Black>") .append(
				"<Cnt_Black>") .append(Cnt_Black) .append("</Cnt_Black>") .append(
				"<Black_Ratio>") .append(Black_Ratio) .append("</Black_Ratio>") .append(
				"<Cnt_Black2>") .append(Cnt_Black2) .append("</Cnt_Black2>") .append(
				"<Cnt_To_Applied>") .append(Cnt_To_Applied) .append("</Cnt_To_Applied>") .append(
				"<Cnt_Be_Applied>") .append(Cnt_Be_Applied) .append("</Cnt_Be_Applied>") .append(
				"<Cnt_Applied>") .append(Cnt_Applied) .append("</Cnt_Applied>") .append(
				"<Recent_Time_To_All>") .append(Recent_Time_To_All) .append("</Recent_Time_To_All>") .append(
				"<Recent_Time_Be_All>") .append(Recent_Time_Be_All) .append("</Recent_Time_Be_All>") .append(
				"<Recent_Time_To_Black>") .append(Recent_Time_To_Black) .append("</Recent_Time_To_Black>") .append(
				"<Recent_Time_Be_Black>") .append(Recent_Time_Be_Black) .append("</Recent_Time_Be_Black>") .append(
				"<Recent_Time_To_Applied>") .append(Recent_Time_To_Applied) .append("</Recent_Time_To_Applied>") .append(
				"<Recent_Time_Be_Applied>") .append(Recent_Time_Be_Applied) .append("</Recent_Time_Be_Applied>") .append(
				"<Call_Cnt_To_All>") .append(Call_Cnt_To_All) .append("</Call_Cnt_To_All>") .append(
				"<Call_Cnt_Be_All>") .append(Call_Cnt_Be_All) .append("</Call_Cnt_Be_All>") .append(
				"<Call_Cnt_To_Black>") .append(Call_Cnt_To_Black) .append("</Call_Cnt_To_Black>") .append(
				"<Call_Cnt_Be_Black>") .append(Call_Cnt_Be_Black) .append("</Call_Cnt_Be_Black>") .append(
				"<Call_Cnt_To_Applied>") .append(Call_Cnt_To_Applied) .append("</Call_Cnt_To_Applied>") .append(
				"<Call_Cnt_Be_Applied>") .append(Call_Cnt_Be_Applied) .append("</Call_Cnt_Be_Applied>") .append(
				"<Time_Spent_To_All>") .append(Time_Spent_To_All) .append("</Time_Spent_To_All>") .append(
				"<Time_Spent_Be_All>") .append(Time_Spent_Be_All) .append("</Time_Spent_Be_All>") .append(
				"<Time_Spent_To_Black>") .append(Time_Spent_To_Black) .append("</Time_Spent_To_Black>") .append(
				"<Time_Spent_Be_Black>") .append(Time_Spent_Be_Black) .append("</Time_Spent_Be_Black>") .append(
				"<Time_Spent_To_Applied>") .append(Time_Spent_To_Applied) .append("</Time_Spent_To_Applied>") .append(
				"<Time_Spent_Be_Applied>") .append(Time_Spent_Be_Applied) .append("</Time_Spent_Be_Applied>") .append(
				"<Weight_To_All>") .append(Weight_To_All) .append("</Weight_To_All>") .append(
				"<Weight_Be_All>") .append(Weight_Be_All) .append("</Weight_Be_All>") .append(
				"<Weight_All>") .append(Weight_All) .append("</Weight_All>") .append(
				"<Weight_To_Black>") .append(Weight_To_Black) .append("</Weight_To_Black>") .append(
				"<Weight_Be_Black>") .append(Weight_Be_Black) .append("</Weight_Be_Black>") .append(
				"<Weight_Black>") .append(Weight_Black) .append("</Weight_Black>") .append(
				"<Weight_To_Applied>") .append(Weight_To_Applied) .append("</Weight_To_Applied>") .append(
				"<Weight_Be_Applied>") .append(Weight_Be_Applied) .append("</Weight_Be_Applied>") .append(
				"<Weight_Applied>") .append(Weight_Applied) .append("</Weight_Applied>") .append(
				"<Most_Familiar_To_All>") .append(Most_Familiar_To_All) .append("</Most_Familiar_To_All>") .append(
				"<Most_Familiar_Be_All>") .append(Most_Familiar_Be_All) .append("</Most_Familiar_Be_All>") .append(
				"<Most_Familiar_All>") .append(Most_Familiar_All) .append("</Most_Familiar_All>") .append(
				"<Most_Familiar_To_Applied>") .append(Most_Familiar_To_Applied) .append("</Most_Familiar_To_Applied>") .append(
				"<Most_Familiar_Be_Applied>") .append(Most_Familiar_Be_Applied) .append("</Most_Familiar_Be_Applied>") .append(
				"<Most_Familiar_Applied>") .append(Most_Familiar_Applied) .append("</Most_Familiar_Applied>") .append(
				"<To_Max>") .append(To_Max) .append("</To_Max>") .append(
				"<To_Mean>") .append(To_Mean) .append("</To_Mean>") .append(
				"<To_Min>") .append(To_Min) .append("</To_Min>") .append(
				"<Be_Max>") .append(Be_Max) .append("</Be_Max>") .append(
				"<Be_Mean>") .append(Be_Mean) .append("</Be_Mean>") .append(
				"<Be_Min>") .append(Be_Min) .append("</Be_Min>") .append(
				"<Max_Linkman>0</Max_Linkman>") .append(
				"<Mean_Linkman>0</Mean_Linkman>") .append(
				"<Min_Linkman>0</Min_Linkman>") .append(
				"<To_Is_Familiar>") .append(To_Is_Familiar) .append("</To_Is_Familiar>") .append(
				"<To_Median_Familiar>") .append(To_Median_Familiar) .append("</To_Median_Familiar>") .append(
				"<To_Not_Familiar>") .append(To_Not_Familiar) .append("</To_Not_Familiar>") .append(
				"<Be_Is_Familiar>") .append(Be_Is_Familiar) .append("</Be_Is_Familiar>") .append(
				"<Be_Median_Familiar>") .append(Be_Median_Familiar) .append("</Be_Median_Familiar>") .append(
				"<Be_Not_Familiar>") .append(Be_Not_Familiar) .append("</Be_Not_Familiar>") .append(
				"<Is_Familiar>") .append(Is_Familiar) .append("</Is_Familiar>") .append(
				"<Median_Familiar>") .append(Median_Familiar) .append("</Median_Familiar>") .append(
				"<Not_Familiar>") .append(Not_Familiar) .append("</Not_Familiar>") .append(
				"<Operator_Grade></Operator_Grade>") .append(
				"<TelCost_Range>") .append(mobile_fee_level) .append("</TelCost_Range>") .append(
				"<Is_InterWhiteList>"+Is_InterWhiteList+"</Is_InterWhiteList>") .append(
				"<WhiteList_Type></WhiteList_Type>") .append(
				"<WhiteList_PreAmount>"+WhiteList_PreAmount+"</WhiteList_PreAmount>") .append(					
				"<CDTB122>0</CDTB122>") .append(  
				"<CDTB129>0</CDTB129>") .append(  
				"<CDTP110>0</CDTP110>") .append(  
				"<CDMC108>0</CDMC108>") .append(  
				"<CSSS002>0</CSSS002>") .append(  
				"<CDCA002>").append(card_level) .append("</CDCA002>") .append(  
				"<CDTB124>0</CDTB124>") .append(  
				"<CDMC249>0</CDMC249>") .append(  
				"<CDMC065>0</CDMC065>") .append(  
				"<CSRL003>0</CSRL003>") .append(  
				"<CSRL001>0</CSRL001>") .append(  
				"<CDTB151></CDTB151>") .append(  
				"<CDMC062>0</CDMC062>") .append(  
				"<Pbc_ValidCard_Count>").append(pbc_validCard_count) .append("</Pbc_ValidCard_Count>") .append(  
				"<Lcs_3m_PastDue_State_Maxs>").append(lcs_3m_pastDue_state_maxs) .append("</Lcs_3m_PastDue_State_Maxs>") .append(  
				"<Ls_3m_PastDue_State_Maxs>").append(ls_3m_pastDue_state_maxs) .append("</Ls_3m_PastDue_State_Maxs>") .append(  
				"<Lcs_6m_m3_Counts>").append(lcs_6m_m3_counts) .append("</Lcs_6m_m3_Counts>") .append(  
				"<Lcs_6m_m2_Counts>").append(lcs_6m_m2_counts) .append("</Lcs_6m_m2_Counts>") .append(  
				"<Ls_6m_m3_Counts>").append(ls_6m_m3_counts) .append("</Ls_6m_m3_Counts>") .append(  
				"<Once_Loanac_6m_M2cnt></Once_Loanac_6m_M2cnt>") .append(  
				"<Lcs_24m_PastDue_State_Maxs>").append(lcs_24m_pastDue_state_maxs) .append("</Lcs_24m_PastDue_State_Maxs>") .append(  
				"<Ls_24m_PastDue_State_Maxs>").append(ls_24m_pastDue_state_maxs) .append("</Ls_24m_PastDue_State_Maxs>") .append(  
				"<Pbc_querys_Num_12m>").append(pbc_querys_num_12m) .append("</Pbc_querys_Num_12m>") .append(  
				"<FiveLevel>").append(fiveLevel) .append("</FiveLevel>") .append(  
				"<IdNoZhuxueLoanArrearage>").append(idNoZhuxueLoanArrearage) .append("</IdNoZhuxueLoanArrearage>") .append(  
				"<IdNoHitCourtShixinNamelist>").append(idNoHitCourtShixinNamelist) .append("</IdNoHitCourtShixinNamelist>") .append(  
				"<IdNoHitCrimeNamelist>").append(idNoHitCrimeNamelist) .append("</IdNoHitCrimeNamelist>") .append(  
				"<IdNoHitDebitfirmNamelist>").append(idNoHitDebitfirmNamelist) .append("</IdNoHitDebitfirmNamelist>") .append(  
				"<IdNoHitXinLoanYuQiList>").append(idNoHitXinLoanYuQiList) .append("</IdNoHitXinLoanYuQiList>") .append(  
				"<IdNoNameHittLoanYuQiBlurlist>").append(idNoNameHittLoanYuQiBlurlist) .append("</IdNoNameHittLoanYuQiBlurlist>") .append(  
				"<IdNoNameHitCourtShixinBlurlist>").append(idNoNameHitCourtShixinBlurlist) .append("</IdNoNameHitCourtShixinBlurlist>") .append(  
				"<IdNoHitOweTaxNamelist>").append(idNoHitOweTaxNamelist) .append("</IdNoHitOweTaxNamelist>") .append(  
				"<MobileHitShamLib>").append(mobileHitShamLib) .append("</MobileHitShamLib>") .append(  
				"<MobileHitCommuLib>").append(mobileHitCommuLib) .append("</MobileHitCommuLib>") .append(  
				"<MobileHitXinLoanYuQiList>").append(mobileHitXinLoanYuQiList) .append("</MobileHitXinLoanYuQiList>") .append(  
				"<AddressHitXinLoanYuQiPayList>").append(addressHitXinLoanYuQiPayList) .append("</AddressHitXinLoanYuQiPayList>") .append(  
				"<MobileRegexError>").append(mobileRegexError) .append("</MobileRegexError>") .append(  
				"<LoanDeviceProxyRecog>").append(loanDeviceProxyRecog) .append("</LoanDeviceProxyRecog>") .append(  
				"<IdNoNameHitCourtActionBlurlist>").append(idNoNameHitCourtActionBlurlist) .append("</IdNoNameHitCourtActionBlurlist>") .append(  
				"<IdNoNameHitCourtEndCaseBlurlist>").append(idNoNameHitCourtEndCaseBlurlist) .append("</IdNoNameHitCourtEndCaseBlurlist>") .append(  
				"<IdNoHitCourtActionNamelist>").append(idNoHitCourtActionNamelist) .append("</IdNoHitCourtActionNamelist>") .append(  
				"<IdNoHitCourtEndCaseNamelist>").append(idNoHitCourtEndCaseNamelist) .append("</IdNoHitCourtEndCaseNamelist>") .append(  
				"</APS_Wangdai_Variable>") .append(
				"</APS_Wangdai_Variables>") .append(
						"<APS_Risks>") .append(
								"<APS_Risk>") .append(
										"<IsGonganPoor>").append(IsGonganPoor) .append("</IsGonganPoor>") .append(
										"<GonganPoor_Type>").append(GonganPoor_Type) .append("</GonganPoor_Type>") .append(
								"</APS_Risk>") .append(
						"</APS_Risks>") .append(
				"</ProcessManagerRequest>") .append(
				"").toString();
		
		 
		 return xml;
	}
	
	
	private String formatXmlString(String xmlStr) {
		try {
			String tmp = "";
			Document doc = DocumentHelper.parseText(xmlStr);
			OutputFormat format = OutputFormat.createCompactFormat();
			StringWriter sw = new StringWriter();
			XMLWriter xmlWriter = new XMLWriter(sw, format); 
			xmlWriter.write(doc);
			xmlWriter.flush();
			sw.flush();
			tmp = sw.toString();
			sw.close();
			xmlWriter.close();
			tmp = tmp.replaceAll("\r\n", "").replaceAll("\n", "");
			return tmp;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return xmlStr;
		
		
	}
	
	
	private int getAge(String birthday) {
		birthday = birthday.replaceAll("年", "-").replaceAll("月", "-").replaceAll("日", "");
		return AgeUtils.getAgeFromBirthTime(birthday);
	}
	
	private String getBirthFormat(String str) {
		return str.replaceAll("年", "-").replaceAll("月", "-").replaceAll("日", "");
	}
	
	private String parseValiDate(String str) {
		return str.replaceAll("\\.", "-");
	}
	
}
