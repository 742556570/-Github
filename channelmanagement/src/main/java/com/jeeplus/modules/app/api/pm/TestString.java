package com.jeeplus.modules.app.api.pm;

public class TestString {
	
	public static String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + 
			"<ProcessManagerRequest>" + 
			"	<Application_Header>" + 
			"		<Channel>WDL</Channel>" + 
			"		<CallType>WD1</CallType>" + 
			"		<ApplicationNumber>WD1</ApplicationNumber>" + 
			"	</Application_Header>" + 
			"	<APS_Applications>" + 
			"		<APS_Application>" + 
			"			<Organisation_Code>SIG</Organisation_Code>" + 
			"			<Country_Code>CN</Country_Code>" + 
			"			<Group_Member_Code>组织成员码</Group_Member_Code>" + 
			"			<Application_Number>WD2018031215432110003</Application_Number>" + 
			"			<Apply_Channel>来源渠道</Apply_Channel>" + 
			"			<Application_Date>2018-03-12T15:43:21</Application_Date>" + 
			"			<Application_Type>WDL</Application_Type>" + 
			"			<Apply_Limit>0</Apply_Limit>" + 
			"			<Loan_Period>0</Loan_Period>" + 
			"			<Loan_Usage>消费分期</Loan_Usage>" + 
			"			<Loan_Rate>0.03</Loan_Rate>" + 
			"			<Premium>200</Premium>" + 
			"			<Branch>0100A003</Branch> <!--  虚拟门店代码  -->  " + 
			"			<Final_Decision>pass</Final_Decision> <!--  待确认 pass reject ？-->" + 
			"			<Final_Decision_Date>2018-03-12T15:48:21</Final_Decision_Date> <!--  待确认 -->" + 
			"			<Final_Limit_Reason_Code></Final_Limit_Reason_Code> <!--  待确认 CL_CP_0005_00000016 -->" + 
			"			<Final_Limit>0</Final_Limit> <!--  待确认 -->" + 
			"			<Workflow_Status></Workflow_Status>" + 
			"			<Sales_Channel>N11407</Sales_Channel>  <!--  渠道 --> " + 
			"			<Sales_Person>01000005</Sales_Person>  <!--  虚拟销售人员  --> " + 
			"			<Loan_Type>TQD</Loan_Type><!-- TQD -->" + 
			"			<Sales_Level></Sales_Level> <!--无-->" + 
			"			<Shop_Recommended></Shop_Recommended> <!--无-->" + 
			"			<Channel_Company_Name></Channel_Company_Name><!--无-->" + 
			"			<Cancel_Reapplication_Flag></Cancel_Reapplication_Flag><!--无-->" + 
			"			<Equivalent_Yearly_Insurance_Premium>0</Equivalent_Yearly_Insurance_Premium><!--无-->" + 
			"			<Required_Payment_Year></Required_Payment_Year><!--无-->" + 
			"			<Payment_frequency></Payment_frequency><!--无-->" + 
			"			<Date_Of_Policy>2018-03-12T15:57:17</Date_Of_Policy>" + 
			"			<External_Debt>0</External_Debt>" + 
			"			<City_Level></City_Level><!--无-->" + 
			"			<Is_State_Enterprises></Is_State_Enterprises><!--无-->" + 
			"			<Is_SIG_Policy></Is_SIG_Policy><!--无-->" + 
			"			<Equivalent_Yearly_Insurance_Premium2>0</Equivalent_Yearly_Insurance_Premium2><!--无-->" + 
			"			<Required_Payment_Year2></Required_Payment_Year2><!--无-->" + 
			"			<Payment_frequency2></Payment_frequency2><!--无-->" + 
			"			<Date_Of_Policy2></Date_Of_Policy2><!--无-->" + 
			"			<Is_SIG_Policy2></Is_SIG_Policy2><!--无-->" + 
			"			<Equivalent_Yearly_Insurance_Premium3>0</Equivalent_Yearly_Insurance_Premium3><!--无-->" + 
			"			<Required_Payment_Year3></Required_Payment_Year3><!--无-->" + 
			"			<Payment_frequency3></Payment_frequency3><!--无-->" + 
			"			<Date_Of_Policy3></Date_Of_Policy3><!--无-->" + 
			"			<Is_SIG_Policy3></Is_SIG_Policy3><!--无-->" + 
			"			<Customer_Type></Customer_Type><!--  无 -->" + 
			"			<Application_Branch_Code></Application_Branch_Code><!--无-->" + 
			"			<Application_Branch></Application_Branch><!--无-->" + 
			"			<Sales_Name></Sales_Name><!--无-->" + 
			"			<Four_Channel_Name></Four_Channel_Name><!--无-->" + 
			"			<Fenqi_Product_Code></Fenqi_Product_Code>" + 
			"			<Fenqi_Payment_Method></Fenqi_Payment_Method>" + 
			"			<Guarantee_Method></Guarantee_Method>" + 
			"			<Channel_Type></Channel_Type>" + 
			"			<Business_Origin></Business_Origin>" + 
			"			<Sale_Channel></Sale_Channel><!--无-->" + 
			"			<First_Audit_Guarantee_Method></First_Audit_Guarantee_Method><!--无-->" + 
			"			<Apply_Stores_Province></Apply_Stores_Province><!--无-->" + 
			"			<Apply_Stores_City></Apply_Stores_City><!--无-->" + 
			"			<Apply_Stores_Distinct></Apply_Stores_Distinct><!--无-->" + 
			"			<Report_User>01000005</Report_User><!-- 录单员代码？ -->" + 
			"			<Report_Team></Report_Team><!--无-->" + 
			"			<Report_Reason></Report_Reason><!--无-->" + 
			"			<Report_Note></Report_Note><!--无-->" + 
			"			<Apply_City></Apply_City><!--无-->" + 
			"		</APS_Application>" + 
			"	</APS_Applications>" + 
			"	<APS_Applicants>" + 
			"		<APS_Applicant>" + 
			"			<Name>佟丽娅</Name>" + 
			"			<Gender>女</Gender>" + 
			"			<Nation>锡伯族</Nation>" + 
			"			<Age>33</Age>" + 
			"			<Birthday>1984-08-08</Birthday>" + 
			"			<Id_Type>身份证</Id_Type><!--码值  或者  身份证-->" + 
			"			<Id_Number>441402200203202504</Id_Number>" + 
			"			<Marriage></Marriage><!--无-->" + 
			"			<Education></Education><!--无-->" + 
			"			<Home_Address_State></Home_Address_State>" + 
			"			<Home_Address_City></Home_Address_City>" + 
			"			<Home_Address_County></Home_Address_County>" + 
			"			<Home_Address_Street_Address></Home_Address_Street_Address>" + 
			"			<Home_Address_Zip></Home_Address_Zip><!--无-->" + 
			"			<Home_Phone></Home_Phone><!--无-->" + 
			"			<Mobile>13902933934</Mobile>" + 
			"			<Email></Email><!--无-->" + 
			"			<Is_Private_Employer>0</Is_Private_Employer>" + 
			"			<Company_Name>测试单位信息</Company_Name>" + 
			"			<Company_Address_State>北京</Company_Address_State>" + 
			"			<Company_Address_City>北京</Company_Address_City>" + 
			"			<Company_Address_County>东城区</Company_Address_County>" + 
			"			<Company_Address_Street_Address>东直门外大街200号</Company_Address_Street_Address>" + 
			"			<Company_Address_Zip></Company_Address_Zip><!--无-->" + 
			"			<Company_Phone>010-55331234</Company_Phone><!--无-->" + 
			"			<Department></Department><!--无-->" + 
			"			<Industry_Code></Industry_Code><!--无-->" + 
			"			<Position_Code></Position_Code><!--无-->" + 
			"			<Employed_Date></Employed_Date><!--无-->" + 
			"			<Base_Salary>0</Base_Salary><!--无-->" + 
			"			<Other_Income>0</Other_Income><!--无-->" + 
			"			<Total_Income>0</Total_Income><!--无-->" + 
			"			<Payment_Date>0</Payment_Date><!--无-->" + 
			"			<Foster_Count>0</Foster_Count><!--无-->" + 
			"			<Has_Social_Security>0</Has_Social_Security><!--无-->" + 
			"			<Has_Vehicle>0</Has_Vehicle><!--无-->" + 
			"			<Has_Property>0</Has_Property><!--无-->" + 
			"			<Has_PBOC>0</Has_PBOC><!--无-->" + 
			"			<Has_Bank_Statement>0</Has_Bank_Statement><!--无-->" + 
			"			<Plate_Number></Plate_Number><!--无-->" + 
			"			<Additional_Mobile></Additional_Mobile><!--无-->" + 
			"			<Additional_Company_Phone></Additional_Company_Phone><!--无-->" + 
			"			<Spouse_Name></Spouse_Name><!--无-->" + 
			"			<Spouse_Mobile></Spouse_Mobile><!--无-->" + 
			"			<Vehicle_Type></Vehicle_Type><!--无-->" + 
			"			<Enterprise_Type></Enterprise_Type><!--无-->" + 
			"			<Job_Grading></Job_Grading><!--无-->" + 
			"			<Businese_Condition></Businese_Condition><!--无-->" + 
			"			<Has_Multi_Property></Has_Multi_Property><!--无-->" + 
			"			<Verification_Income>0</Verification_Income><!--无-->" + 
			"			<Monthly_Total_Income>0</Monthly_Total_Income><!--无-->" + 
			"			<City_Class></City_Class><!--无-->" + 
			"			<Company_Nature></Company_Nature><!--无-->" + 
			"			<Property_Number></Property_Number><!--无-->" + 
			"			<Rent_Addr_Province></Rent_Addr_Province><!--无-->" + 
			"			<Rent_Addr_City></Rent_Addr_City><!--无-->" + 
			"			<Rent_Addr_District></Rent_Addr_District><!--无-->" + 
			"			<Rent_Addr_Detail></Rent_Addr_Detail><!--无-->" + 
			"			<Decorate_Addr></Decorate_Addr><!--无-->" + 
			"			<Frame_Number></Frame_Number><!--无-->" + 
			"			<ID_Start_Date>2018-03-06</ID_Start_Date><!--无-->" + 
			"			<ID_Expire_Date>2018-03-06</ID_Expire_Date><!--无-->" + 
			"			<ID_Address_Province></ID_Address_Province><!--无-->" + 
			"			<ID_Address_City></ID_Address_City><!--无-->" + 
			"			<ID_Address_District></ID_Address_District><!--无-->" + 
			"			<ID_Address_Detail></ID_Address_Detail><!--无-->" + 
			"			<Occupation_Classing></Occupation_Classing><!--无-->" + 
			"			<Employ_Date>2018-03-06</Employ_Date><!--无-->" + 
			"			<Inhabit_Start_Date>2018-03-06</Inhabit_Start_Date><!--无-->" + 
			"			<In_Insurance_City_Date>2018-03-06</In_Insurance_City_Date><!--无-->" + 
			"			<Private_Business_Owner_Premises></Private_Business_Owner_Premises><!--无-->" + 
			"			<Private_Business_Equity_Rate></Private_Business_Equity_Rate><!--无-->" + 
			"			<Pay_Off_Method></Pay_Off_Method><!--无-->" + 
			"			<registDate></registDate><!--无-->" + 
			"			<registFund>0</registFund><!--无-->" + 
			"			<fundCurrency></fundCurrency><!--无-->" + 
			"			<artificialName></artificialName><!--无-->" + 
			"			<manageBeginDate></manageBeginDate><!--无-->" + 
			"			<manageEndDate></manageEndDate><!--无-->" + 
			"			<corpTypeID></corpTypeID><!--无-->" + 
			"			<corpTypeCaption></corpTypeCaption><!--无-->" + 
			"			<staffNumber>0</staffNumber><!--无-->" + 
			"			<tradeCode></tradeCode><!--无-->" + 
			"			<Is_shareholder></Is_shareholder><!--无-->" + 
			"			<Contributive_Proportion>0</Contributive_Proportion><!--无-->" + 
			"			<Is_Same_Legal_Name></Is_Same_Legal_Name><!--无-->" + 
			"			<Is_Exist_important_Person></Is_Exist_important_Person><!--无-->" + 
			"			<logoutDate></logoutDate><!--无-->" + 
			"			<revokeDate></revokeDate><!--无-->" + 
			"			<StatusID></StatusID><!--无-->" + 
			"			<statusCaption></statusCaption><!--无-->" + 
			"		</APS_Applicant>" + 
			"	</APS_Applicants>" + 
			"	<APS_Contacts>" + 
			"		<APS_Contact>" + 
			"			<Name>测试1</Name>" + 
			"			<Gender></Gender><!--无-->" + 
			"			<Id_Number></Id_Number><!--无-->" + 
			"			<Age></Age><!--无-->" + 
			"			<Relation>同事</Relation>" + 
			"			<Contact_Home_Phone></Contact_Home_Phone><!--无-->" + 
			"			<Contact_Mobile></Contact_Mobile>" + 
			"			<Contact_Company_Phone></Contact_Company_Phone><!--无-->" + 
			"			<Additional_Mobile></Additional_Mobile><!--无-->" + 
			"			<Additional_Company_Phone></Additional_Company_Phone><!--无-->" + 
			"		</APS_Contact>" + 
			"	</APS_Contacts>" + 
			"	<PBOC_Personal_Infos>" + 
			"		<PBOC_Personal_Info>" + 
			"			<Name>佟丽娅</Name>" + 
			"			<Gender>女</Gender>" + 
			"			<ID_Type>身份证</ID_Type>" + 
			"			<ID_Number>441402200203202504</ID_Number>" + 
			"			<Birthday>1984-08-08</Birthday>" + 
			"			<Education></Education>" + 
			"			<Education_Degree></Education_Degree><!--无-->" + 
			"			<Mailing_Address></Mailing_Address>" + 
			"			<ID_Address></ID_Address><!--无-->" + 
			"			<Home_Phone_Number></Home_Phone_Number><!--无-->" + 
			"			<Company_Phone_Number></Company_Phone_Number>" + 
			"			<Cell_Phone_Number></Cell_Phone_Number>" + 
			"			<Marital_State></Marital_State><!--无-->" + 
			"			<Spouse_Name></Spouse_Name><!--无-->" + 
			"			<Spouse_ID_Type></Spouse_ID_Type><!--无-->" + 
			"			<Spouse_ID_Number></Spouse_ID_Number><!--无-->" + 
			"			<Spouse_Company></Spouse_Company><!--无-->" + 
			"			<Spouse_Contact_Number></Spouse_Contact_Number><!--无-->" + 
			"		</PBOC_Personal_Info>" + 
			"	</PBOC_Personal_Infos>" + 
			"	<APS_OtherInfo3s>" + 
			"		<APS_OtherInfo3>" + 
			"			<Merchant_ID></Merchant_ID><!--无-->" + 
			"			<Product_Category></Product_Category><!--无-->" + 
			"			<Product_Category_Detail></Product_Category_Detail><!--无-->" + 
			"			<Co_Org_City></Co_Org_City><!--无-->" + 
			"			<Agency_Rating></Agency_Rating><!--无-->" + 
			"			<Org_Status></Org_Status><!--无-->" + 
			"			<Bank_Card></Bank_Card><!--无-->" + 
			"			<Bank_Mobile></Bank_Mobile><!--无-->" + 
			"			<ID_Validation></ID_Validation><!--无-->" + 
			"			<ID_Compare>0</ID_Compare><!--无-->" + 
			"			<Liveness_Identification></Liveness_Identification><!--无-->" + 
			"			<Public_Security_Query>0</Public_Security_Query><!--无-->" + 
			"			<Register_Address_Province></Register_Address_Province><!--无-->" + 
			"			<Register_Address_City></Register_Address_City><!--无-->" + 
			"			<Register_Address_District></Register_Address_District><!--无-->" + 
			"			<Register_Address_Detail></Register_Address_Detail><!--无-->" + 
			"			<Income_Source></Income_Source><!--无-->" + 
			"		</APS_OtherInfo3>" + 
			"	</APS_OtherInfo3s>" + 
			"	<APS_Wangdai_Variable>" + 
			"		<APS_Wangdai_Variable>" + 
			"			<Fail_Times>0</Fail_Times>" + 
			"			<Is_ID_Impersonated>false</Is_ID_Impersonated><!-- 衍生数据，活体 -->" + 
			"			<Software_Times>0</Software_Times><!-- 衍生数据，活体 -->" + 
			"			<Mask_Times>0</Mask_Times><!-- 衍生数据，活体 -->" + 
			"			<Screen_Times>0</Screen_Times><!-- 衍生数据，活体 -->" + 
			"			<CurrentOverdueAccount>0</CurrentOverdueAccount><!--衍生数据 内匹-->" + 
			"			<IsOver30dOverdue6m>false</IsOver30dOverdue6m><!--衍生数据 内匹-->" + 
			"			<PremiumOverdueMonth6m>0</PremiumOverdueMonth6m><!--衍生数据 内匹-->" + 
			"			<PremiumTimeOver7dCnt>0</PremiumTimeOver7dCnt><!--衍生数据 内匹-->" + 
			"			<IsClaimClient>false</IsClaimClient><!--衍生数据 内匹-->" + 
			"			<IsForHighRiskRefuse>false</IsForHighRiskRefuse><!--衍生数据 内匹-->" + 
			"			<NoSettleLoanCnt>0</NoSettleLoanCnt><!--衍生数据 内匹  未结清贷款笔数-->" + 
			"			<NetLoanMaxOverDays3m>0</NetLoanMaxOverDays3m><!-- 衍生数据 内匹 网贷放款近3个月最大逾期天数 -->" + 
			"			<Applicant_If_AsContact_Apply>false</Applicant_If_AsContact_Apply><!--衍生数据 内匹-->" + 
			"			<Applicant_Contact_If_Apply>false</Applicant_Contact_If_Apply><!--衍生数据 内匹-->" + 
			"			<Applicant_Contact_If_AsContact_Apply>false</Applicant_Contact_If_AsContact_Apply><!--衍生数据 内匹-->" + 
			"			<Applicant_Contact_If_Mutual_Contact>false</Applicant_Contact_If_Mutual_Contact><!--衍生数据 内匹-->" + 
			"			<Applicant_Contact_AssoApplicat_If_HighRisk></Applicant_Contact_AssoApplicat_If_HighRisk><!--无-->" + 
			"			<Applicant_Contact_AssoApply_If_HighRisk>false</Applicant_Contact_AssoApply_If_HighRisk><!--衍生数据 内匹-->" + 
			"			<Mobile_Check_Result>一致</Mobile_Check_Result><!--衍生数据 鹏元-->" + 
			"			<Mobile_Online_Time_Length>0</Mobile_Online_Time_Length><!--衍生数据 鹏元-->" + 
			"			<Install_Loan_App_Cnt>0</Install_Loan_App_Cnt><!-- 衍生数据 借款申请衍生 -->" + 
			"			<Install_Shares_App_Cnt>0</Install_Shares_App_Cnt><!-- 衍生数据 借款申请衍生 -->" + 
			"			<Install_Lottery_App_Cnt>0</Install_Lottery_App_Cnt><!-- 衍生数据 借款申请衍生 -->" + 
			"			<Install_Finance_App_Cnt>0</Install_Finance_App_Cnt><!-- 衍生数据 借款申请衍生 -->" + 
			"			<Install_Virtual_App_Cnt>0</Install_Virtual_App_Cnt><!-- 衍生数据 借款申请衍生 -->" + 
			"			<Contact_Has_LoanApp_Cnt>0</Contact_Has_LoanApp_Cnt><!-- 衍生数据 内匹 联系衍生结果 -->" + 
			"			<Msg_Risk_If>false</Msg_Risk_If><!-- 衍生数据 紧急联系人结果 -->" + 
			"			<Msg_Risk_Type>false</Msg_Risk_Type><!-- 衍生数据 紧急联系人结果 -->" + 
			"			<Current_Divice_Apply_Cnt>0</Current_Divice_Apply_Cnt><!-- 衍生数据 借款申请衍生 -->" + 
			"			<Apply_Divice_Cnt>0</Apply_Divice_Cnt><!-- 衍生数据 借款申请衍生 -->" + 
			"			<Same_Gps_Apply_Cnt>0</Same_Gps_Apply_Cnt><!-- 衍生数据 借款申请衍生 -->" + 
			"			<Same_Ip_Apply_Cnt>0</Same_Ip_Apply_Cnt><!-- 衍生数据 借款申请衍生 -->" + 
			"			<Same_Wfmac_Apply_Cnt>0</Same_Wfmac_Apply_Cnt><!-- 衍生数据 借款申请衍生 -->" + 
			"			<Same_Company_Apply_Cnt>0</Same_Company_Apply_Cnt><!-- 衍生数据 职业信息衍生 -->" + 
			"			<Apply_Divice_Cnt_6m>0</Apply_Divice_Cnt_6m><!-- 衍生数据 登录衍生 -->" + 
			"			<Same_Gps_Apply_Cnt_6m>0</Same_Gps_Apply_Cnt_6m><!-- 衍生数据 借款申请衍生 -->" + 
			"			<Same_Ip_Apply_Cnt_6m>0</Same_Ip_Apply_Cnt_6m><!-- 衍生数据 借款申请衍生 -->" + 
			"			<Same_Wfmac_Apply_Cnt_6m>0</Same_Wfmac_Apply_Cnt_6m><!-- 衍生数据 借款申请衍生 -->" + 
			"			<Same_Company_Apply_Cnt_6m>0</Same_Company_Apply_Cnt_6m><!-- 衍生数据 职业信息衍生 -->" + 
			"			<Apply_Divice_Cnt_1d>0</Apply_Divice_Cnt_1d><!-- 衍生数据 登录衍生 -->" + 
			"			<Same_Gps_Apply_Cnt_1d>0</Same_Gps_Apply_Cnt_1d><!-- 衍生数据 借款申请衍生 -->" + 
			"			<Same_Ip_Apply_Cnt_1d>0</Same_Ip_Apply_Cnt_1d><!-- 衍生数据 借款申请衍生 -->" + 
			"			<Same_Wfmac_Apply_Cnt_1d>0</Same_Wfmac_Apply_Cnt_1d><!-- 衍生数据 借款申请衍生 -->" + 
			"			<Same_Company_Apply_Cnt_1d>0</Same_Company_Apply_Cnt_1d><!-- 衍生数据 职业信息衍生 -->" + 
			"			<If_Use_Simulator>false</If_Use_Simulator><!-- 衍生数据 借款申请衍生 -->" + 
			"			<Work_City_Grade>二级城市</Work_City_Grade><!-- 衍生数据 职业信息衍生 -->" + 
			"			<House_City_Grade>二级城市</House_City_Grade><!-- 衍生数据 职业信息衍生 -->" + 
			"			<Gps_City_Match_Ctiy>一致</Gps_City_Match_Ctiy><!-- 衍生数据 职业信息衍生 -->" + 
			"			<Gps_City>杭州</Gps_City><!-- 衍生数据 职业信息衍生 -->" + 
			"			<Users_Distinct>0</Users_Distinct><!-- 衍生数据 紧急联系人结果 -->" + 
			"			<Rate_7d>0</Rate_7d><!-- 衍生数据 紧急联系人结果 -->" + 
			"			<Rate_1m>0</Rate_1m><!-- 衍生数据 紧急联系人结果 -->" + 
			"			<Company_If_Hit_Ban_List>工作单位名称是否命中禁止准入名单</Company_If_Hit_Ban_List><!-- 衍生数据 职业信息衍生 -->" + 
			"			<Lcs_12m_M4_Counts>0</Lcs_12m_M4_Counts><!-- 衍生数据 pbc衍生结果 -->" + 
			"			<Lcs_12m_M2_Counts>0</Lcs_12m_M2_Counts><!-- 衍生数据 pbc衍生结果 -->" + 
			"			<Ls_12m_M4_Counts>0</Ls_12m_M4_Counts><!-- 衍生数据 pbc衍生结果 -->" + 
			"			<Ls_12m_M2_Counts>0</Ls_12m_M2_Counts><!-- 衍生数据 pbc衍生结果 -->" + 
			"			<Pbc_24m_PastDue_Counts>0</Pbc_24m_PastDue_Counts><!-- 衍生数据 pbc衍生结果 -->" + 
			"			<Pbc_Querys_Num_6m>0</Pbc_Querys_Num_6m><!-- 衍生数据 pbc衍生结果 -->" + 
			"			<Pbc_Querys_Num_3m>0</Pbc_Querys_Num_3m><!-- 衍生数据 pbc衍生结果 -->" + 
			"			<Lcs_PastDueFrequency_Count>0</Lcs_PastDueFrequency_Count><!-- 衍生数据 pbc衍生结果 -->" + 
			"			<Lcs_PastDueAmount>0</Lcs_PastDueAmount><!-- 衍生数据 pbc衍生结果 -->" + 
			"			<Ls_PastDueFrequency_Count>0</Ls_PastDueFrequency_Count><!-- 衍生数据 pbc衍生结果 -->" + 
			"			<Ls_PastDueAmount>0</Ls_PastDueAmount><!-- 衍生数据 pbc衍生结果 -->" + 
			"			<ApplicantLendflatCnt7d>0</ApplicantLendflatCnt7d><!-- 衍生数据 pbc衍生结果 -->" + 
			"			<ApplicantLendflatCnt1m>0</ApplicantLendflatCnt1m><!-- 衍生数据 pbc衍生结果 -->" + 
			"			<ApplicantLendflatCnt3m>0</ApplicantLendflatCnt3m><!-- 衍生数据 pbc衍生结果 -->" + 
			"			<ApplicantLendflatCnt6m>0</ApplicantLendflatCnt6m><!-- 衍生数据 pbc衍生结果 -->" + 
			"			<ApplicantLendflatCnt12m>0</ApplicantLendflatCnt12m><!-- 衍生数据 pbc衍生结果 -->" + 
			"			<IdNoAssoManyApplyInfoCnt3m>0</IdNoAssoManyApplyInfoCnt3m><!-- 衍生数据 同盾衍生结果 -->" + 
			"			<ApplyInfoAssoManyIdNoCnt3m>0</ApplyInfoAssoManyIdNoCnt3m><!-- 衍生数据 同盾衍生结果 -->" + 
			"			<Register_App_Cnt>0</Register_App_Cnt><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Loan_App_Cnt>0</Loan_App_Cnt><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Finance_App_Cnt>0</Finance_App_Cnt><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Loan_Or_Finance_App_Cnt>0</Loan_Or_Finance_App_Cnt><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<If_Own_Car>0</If_Own_Car><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<If_Pay_Ins>0</If_Pay_Ins><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<If_Fin_Buy_Pre6>0</If_Fin_Buy_Pre6><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Query_Org_Cnt30d>0</Query_Org_Cnt30d><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Query_Cnt30d>0</Query_Cnt30d><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Query_Cnt90d>0</Query_Cnt90d><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Query_Org_Cnt90d>0</Query_Org_Cnt90d><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Query_Org_Cnt7d>0</Query_Org_Cnt7d><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Query_Cnt7d>0</Query_Cnt7d><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Query_Org_Cnt180d>0</Query_Org_Cnt180d><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Query_Cnt180d>0</Query_Cnt180d><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Query_Org_Cnt360d>0</Query_Org_Cnt360d><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Query_Cnt360d>0</Query_Cnt360d><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Use_Mobile_Other_Name_Cnt>0</Use_Mobile_Other_Name_Cnt><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Use_Mobile_Other_Idno_Cnt>0</Use_Mobile_Other_Idno_Cnt><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Use_Idno_Other_Mobile_Cnt>0</Use_Idno_Other_Mobile_Cnt><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Use_Idno_Other_Name_Cnt>0</Use_Idno_Other_Name_Cnt><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Blacklist_Name_With_Phone>false</Blacklist_Name_With_Phone><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Blacklist_Update_Time_Name_Phone>2018-03-06T17:57:17</Blacklist_Update_Time_Name_Phone><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Blacklist_Name_With_Idcard>false</Blacklist_Name_With_Idcard><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Blacklist_Update_Time_name_Idcard>2018-03-06T17:57:17</Blacklist_Update_Time_name_Idcard><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Phone_Gray_Score>0</Phone_Gray_Score><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Social_Liveness>0</Social_Liveness><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Social_Influence>0</Social_Influence><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<User_Phone_Province>北京</User_Phone_Province><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<User_City>北京</User_City><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<User_Phone_City>北京</User_Phone_City><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<User_Region>北京</User_Region><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<User_Phone_Operator>移动</User_Phone_Operator><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<User_Province>北京</User_Province><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Cnt_To_All>0</Cnt_To_All><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Cnt_Be_All>0</Cnt_Be_All><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Cnt_All>0</Cnt_All><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Cnt_Router>0</Cnt_Router><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Router_Ratio>0</Router_Ratio><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Cnt_To_Black>0</Cnt_To_Black><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Cnt_Be_Black>0</Cnt_Be_Black><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Cnt_Black>0</Cnt_Black><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Black_Ratio>0</Black_Ratio><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Cnt_Black2>0</Cnt_Black2><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Cnt_To_Applied>0</Cnt_To_Applied><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Cnt_Be_Applied>0</Cnt_Be_Applied><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Cnt_Applied>0</Cnt_Applied><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Recent_Time_To_All>2018-03-06T17:57:17</Recent_Time_To_All><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Recent_Time_Be_All>2018-03-06T17:57:17</Recent_Time_Be_All><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Recent_Time_To_Black>2018-03-06T17:57:17</Recent_Time_To_Black><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Recent_Time_Be_Black>2018-03-06T17:57:17</Recent_Time_Be_Black><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Recent_Time_To_Applied>2018-03-06T17:57:17</Recent_Time_To_Applied><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Recent_Time_Be_Applied>2018-03-06T17:57:17</Recent_Time_Be_Applied><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Call_Cnt_To_All>0</Call_Cnt_To_All><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Call_Cnt_Be_All>0</Call_Cnt_Be_All><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Call_Cnt_To_Black>0</Call_Cnt_To_Black><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Call_Cnt_Be_Black>0</Call_Cnt_Be_Black><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Call_Cnt_To_Applied>0</Call_Cnt_To_Applied><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Call_Cnt_Be_Applied>0</Call_Cnt_Be_Applied><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Time_Spent_To_All>0</Time_Spent_To_All><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Time_Spent_Be_All>0</Time_Spent_Be_All><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Time_Spent_To_Black>0</Time_Spent_To_Black><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Time_Spent_Be_Black>0</Time_Spent_Be_Black><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Time_Spent_To_Applied>0</Time_Spent_To_Applied><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Time_Spent_Be_Applied>0</Time_Spent_Be_Applied><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Weight_To_All>0</Weight_To_All><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Weight_Be_All>0</Weight_Be_All><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Weight_All>0</Weight_All><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Weight_To_Black>0</Weight_To_Black><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Weight_Be_Black>0</Weight_Be_Black><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Weight_Black>0</Weight_Black><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Weight_To_Applied>0</Weight_To_Applied><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Weight_Be_Applied>0</Weight_Be_Applied><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Weight_Applied>0</Weight_Applied><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Most_Familiar_To_All>0</Most_Familiar_To_All><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Most_Familiar_Be_All>0</Most_Familiar_Be_All><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Most_Familiar_All>0</Most_Familiar_All><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Most_Familiar_To_Applied>0</Most_Familiar_To_Applied><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Most_Familiar_Be_Applied>0</Most_Familiar_Be_Applied><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Most_Familiar_Applied>0</Most_Familiar_Applied><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<To_Max>0</To_Max><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<To_Mean>0</To_Mean><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<To_Min>0</To_Min><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Be_Max>0</Be_Max><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Be_Mean>0</Be_Mean><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Be_Min>0</Be_Min><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Max_Linkman>0</Max_Linkman><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Mean_Linkman>0</Mean_Linkman><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Min_Linkman>0</Min_Linkman><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<To_Is_Familiar>0</To_Is_Familiar><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<To_Median_Familiar>0</To_Median_Familiar><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<To_Not_Familiar>0</To_Not_Familiar><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Be_Is_Familiar>0</Be_Is_Familiar><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Be_Median_Familiar>0</Be_Median_Familiar><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Be_Not_Familiar>0</Be_Not_Familiar><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Is_Familiar>0</Is_Familiar><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Median_Familiar>0</Median_Familiar><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Not_Familiar>0</Not_Familiar><!-- 衍生数据 聚信立 蜜罐 衍生结果 -->" + 
			"			<Operator_Grade>二星级</Operator_Grade><!-- 衍生数据 鹏元衍生结果 -->" + 
			"			<Is_InterWhiteList></Is_InterWhiteList><!--  无  -->" + 
			"			<WhiteList_Type></WhiteList_Type><!--  无  -->" + 
			"			<WhiteList_PreAmount>0</WhiteList_PreAmount><!--  无  -->" + 
			"		</APS_Wangdai_Variable>" + 
			"	</APS_Wangdai_Variable>" + 
			"</ProcessManagerRequest>" + 
			"";

}
