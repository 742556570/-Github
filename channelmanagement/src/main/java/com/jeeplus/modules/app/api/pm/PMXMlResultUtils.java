package com.jeeplus.modules.app.api.pm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeeplus.modules.app.utils.JSONXMLUtils;

public class PMXMlResultUtils {

	private final static Logger logger = LoggerFactory.getLogger(PMXMlResultUtils.class);
	
	public JSONObject parseDecisionResultXML(String resultXML) {
		JSONXMLUtils jx = new JSONXMLUtils();
		JSONObject result = jx.xml2JSONObject(resultXML);
		
		JSONObject pmRt = result.getJSONObject("PM_Results");
		JSONObject drRt = pmRt.getJSONObject("Decision_Results");
		JSONArray drrd = drRt.getJSONArray("PM_Decision_Results_Data");
		JSONArray codesData = drRt.getJSONArray("PM_Reason_Codes_Data");
		
		
		
		
		JSONObject jo = new JSONObject();
		String default_decision = "";
		String default_reason = "";
		
		
		for (int i = 0; i < drrd.size(); i++) {
			JSONObject obj = drrd.getJSONObject(i);

			default_reason += obj.getString("Default_Reason")+",";
			default_decision += obj.getString("System_Decision")+",";
		}
		
		if(default_reason.endsWith(",")) {
			default_reason = default_reason.substring(0, default_reason.length()-1);
		}
		
		if(default_decision.endsWith(",")) {
			default_decision = default_decision.substring(0, default_decision.length()-1);
		}
		
		jo.put("default_reason", default_reason);
		jo.put("system_decision", default_decision);
		JSONArray tmpArray = new JSONArray();
		if (codesData != null) {
			for (int i = 0; i < codesData.size(); i++) {
				JSONObject temp = new JSONObject();
				JSONObject data = codesData.getJSONObject(i);
				String code = data.getString("Reason_Code");
				String desc = data.getString("Reason_Description");
				temp.put("code", code);
				temp.put("desc", desc);
				tmpArray.add(temp);
			}
		}
		jo.put("detail", tmpArray);
		logger.info("转化PM决策，参数:"+resultXML);
		logger.info("转化PM决策，结果:"+jo.toJSONString());
		return jo;
	}
	
	
	
	
	
	public JSONObject parseStrategyResultXML(String resultXML) {
		System.out.println(resultXML);
		JSONXMLUtils jx = new JSONXMLUtils();
		JSONObject result = jx.xml2JSONObject(resultXML);
		
		JSONObject pmRt = result.getJSONObject("PM_Results");
		
		JSONObject srRt = new JSONObject();
		JSONArray srrd =  new JSONArray();
		JSONArray codesData = new JSONArray();
		if(pmRt !=null) {
			srRt = pmRt.getJSONObject("Strategy_Results");
			if(srRt!=null) {
				srrd = srRt.getJSONArray("PM_Strategy_MaxLendingAmount_Data");
			}
			codesData = srRt.getJSONArray("PM_Reason_Codes_Data");
		}
		
		JSONObject jo = new JSONObject();
		String default_decision = "";
		String default_reason = "";
		
		if(srrd == null || srrd.isEmpty()) {
			jo.put("maxAmount", "0.00");
		}else {
			JSONObject PM_Strategy_MaxLendingAmount_Results = srrd.getJSONObject(0);
			String maxAmount = "0.00";
//			for (int i = 0; i < MaxLendingResults.size(); i++) {
//				JSONObject jsonObj = MaxLendingResults.getJSONObject(i);
			if(PM_Strategy_MaxLendingAmount_Results !=null && !PM_Strategy_MaxLendingAmount_Results.isEmpty()) {
				maxAmount = PM_Strategy_MaxLendingAmount_Results.getString("Max_Lending_Amount");
			}else {
				maxAmount = "0.00";
			}
//			}
			jo.put("maxAmount", maxAmount);
		}
		
		JSONArray tmpArray = new JSONArray();
		if (codesData != null) {
			for (int i = 0; i < codesData.size(); i++) {
				JSONObject temp = new JSONObject();
				JSONObject data = codesData.getJSONObject(i);
				String code = data.getString("Reason_Code");
				String desc = data.getString("Reason_Description");
				temp.put("code", code);
				temp.put("desc", desc);
				tmpArray.add(temp);
			}
		}
		jo.put("detail", tmpArray);
		logger.info("转化PM额度，参数:"+resultXML);
		logger.info("转化PM额度，结果:"+jo.toJSONString());
		return jo;
	}
	
	
	public static void main(String[] args) {
		String xml = "<ProcessManagerResponse><Application_Header><Channel>WDL</Channel><CallType>WD2</CallType><ApplicationNumber>WD1514001805141532W</ApplicationNumber><ReturnDateTime>20180514153148</ReturnDateTime><SystemErrorCode></SystemErrorCode><SystemErrorMessage></SystemErrorMessage><ReturnError RecordNumber=\"0\" /></Application_Header><Instinct_Actions><Instinct_Action><Field_Name>Action_Taken</Field_Name><Field_Value>S</Field_Value></Instinct_Action><Instinct_Action><Field_Name>Action_User</Field_Name><Field_Value /></Instinct_Action><Instinct_Action><Field_Name>Application_Number</Field_Name><Field_Value>WD1514001805141532W</Field_Value></Instinct_Action><Instinct_Action><Field_Name>Application_Type</Field_Name><Field_Value>WDL</Field_Value></Instinct_Action><Instinct_Action><Field_Name>Capture_Date</Field_Name><Field_Value /></Instinct_Action><Instinct_Action><Field_Name>Capture_Time</Field_Name><Field_Value /></Instinct_Action><Instinct_Action><Field_Name>CountNbr</Field_Name><Field_Value>1</Field_Value></Instinct_Action><Instinct_Action><Field_Name>Country_Code</Field_Name><Field_Value>CN</Field_Value></Instinct_Action><Instinct_Action><Field_Name>Decision_Reason</Field_Name><Field_Value>H201-整体风险过高</Field_Value></Instinct_Action><Instinct_Action><Field_Name>Error_Code</Field_Name><Field_Value /></Instinct_Action><Instinct_Action><Field_Name>Fraud_Alert</Field_Name><Field_Value>H</Field_Value></Instinct_Action><Instinct_Action><Field_Name>Fraud_Score</Field_Name><Field_Value>130</Field_Value></Instinct_Action><Instinct_Action><Field_Name>Group_Member_Code</Field_Name><Field_Value /></Instinct_Action><Instinct_Action><Field_Name>Organisation</Field_Name><Field_Value>SIG</Field_Value></Instinct_Action><Instinct_Action><Field_Name>Rule_Triggered_1</Field_Name><Field_Value>AW005</Field_Value></Instinct_Action><Instinct_Action><Field_Name>Rule_Triggered_10</Field_Name><Field_Value /></Instinct_Action><Instinct_Action><Field_Name>Rule_Triggered_11</Field_Name><Field_Value /></Instinct_Action><Instinct_Action><Field_Name>Rule_Triggered_12</Field_Name><Field_Value /></Instinct_Action><Instinct_Action><Field_Name>Rule_Triggered_13</Field_Name><Field_Value /></Instinct_Action><Instinct_Action><Field_Name>Rule_Triggered_14</Field_Name><Field_Value /></Instinct_Action><Instinct_Action><Field_Name>Rule_Triggered_15</Field_Name><Field_Value /></Instinct_Action><Instinct_Action><Field_Name>Rule_Triggered_16</Field_Name><Field_Value /></Instinct_Action><Instinct_Action><Field_Name>Rule_Triggered_17</Field_Name><Field_Value /></Instinct_Action><Instinct_Action><Field_Name>Rule_Triggered_18</Field_Name><Field_Value /></Instinct_Action><Instinct_Action><Field_Name>Rule_Triggered_19</Field_Name><Field_Value /></Instinct_Action><Instinct_Action><Field_Name>Rule_Triggered_2</Field_Name><Field_Value>CR1001</Field_Value></Instinct_Action><Instinct_Action><Field_Name>Rule_Triggered_20</Field_Name><Field_Value /></Instinct_Action><Instinct_Action><Field_Name>Rule_Triggered_3</Field_Name><Field_Value /></Instinct_Action><Instinct_Action><Field_Name>Rule_Triggered_4</Field_Name><Field_Value /></Instinct_Action><Instinct_Action><Field_Name>Rule_Triggered_5</Field_Name><Field_Value /></Instinct_Action><Instinct_Action><Field_Name>Rule_Triggered_6</Field_Name><Field_Value /></Instinct_Action><Instinct_Action><Field_Name>Rule_Triggered_7</Field_Name><Field_Value /></Instinct_Action><Instinct_Action><Field_Name>Rule_Triggered_8</Field_Name><Field_Value /></Instinct_Action><Instinct_Action><Field_Name>Rule_Triggered_9</Field_Name><Field_Value /></Instinct_Action><Instinct_Action_Output_XML /></Instinct_Actions><PM_Results><Random_Number>66</Random_Number><DBR>0.00</DBR><Instinct_Results name=\"Instinct\"><Instinct_Output_XML><OutputSchema><Output><Organisation>SIG</Organisation><Country_Code>CN</Country_Code><Group_Member_Code></Group_Member_Code><Application_Number>WD1514001805141532W</Application_Number><Capture_Date></Capture_Date><Capture_Time></Capture_Time><Application_Type>WDL</Application_Type><Fraud_Score>130</Fraud_Score><Fraud_Alert>H</Fraud_Alert><Action_Taken>S</Action_Taken><Error_Code></Error_Code><Action_User></Action_User><Rule_Triggered_1>AW005</Rule_Triggered_1><Rule_Triggered_2>CR1001</Rule_Triggered_2><Rule_Triggered_3></Rule_Triggered_3><Rule_Triggered_4></Rule_Triggered_4><Rule_Triggered_5></Rule_Triggered_5><Rule_Triggered_6></Rule_Triggered_6><Rule_Triggered_7></Rule_Triggered_7><Rule_Triggered_8></Rule_Triggered_8><Rule_Triggered_9></Rule_Triggered_9><Rule_Triggered_10></Rule_Triggered_10><Rule_Triggered_11></Rule_Triggered_11><Rule_Triggered_12></Rule_Triggered_12><Rule_Triggered_13></Rule_Triggered_13><Rule_Triggered_16></Rule_Triggered_16><Rule_Triggered_17></Rule_Triggered_17><Rule_Triggered_18></Rule_Triggered_18><Rule_Triggered_19></Rule_Triggered_19><Rule_Triggered_20></Rule_Triggered_20><Decision_Reason>H201-整体风险过高</Decision_Reason><CountNbr>1</CountNbr><NatureOfFraud><FraudCode></FraudCode></NatureOfFraud><Diary><Diary_Date>14/05/2018</Diary_Date><Diary_Time>153148</Diary_Time><Diary_User_Id></Diary_User_Id><Diary_Note></Diary_Note></Diary><Triggered_Rules><Record><Name>皮勇亮</Name><Phone>13693605083</Phone><Id>150429199008093212</Id><Triggered_Rule><Rule_Code>CR1001</Rule_Code><Rule_Description>申请人身份证号与历史库申请人身份证号相同</Rule_Description><Rule_Definitions><Rule_Definition><ApplicationCategory_Label>申请人信息</ApplicationCategory_Label><ApplicationField_Label>身份证号</ApplicationField_Label><Operator_Type>=</Operator_Type><Assignment_Value></Assignment_Value><DatabaseCategory_Label>申请人信息</DatabaseCategory_Label><DatabaseField_Label>身份证号</DatabaseField_Label></Rule_Definition></Rule_Definitions></Triggered_Rule></Record><Triggered_Rule><Rule_Code>AW005</Rule_Code><Rule_Description>申请人单位名称与到黑名单单位名称相同</Rule_Description><Rule_Definitions><Rule_Definition><ApplicationCategory_Label>申请人信息</ApplicationCategory_Label><ApplicationField_Label>单位名称</ApplicationField_Label><Operator_Type>=</Operator_Type><Assignment_Value></Assignment_Value><DatabaseCategory_Label>申请人信息</DatabaseCategory_Label><DatabaseField_Label>单位名称</DatabaseField_Label></Rule_Definition><Rule_Definition><ApplicationCategory_Label>申请信息</ApplicationCategory_Label><ApplicationField_Label>名单等级</ApplicationField_Label><Operator_Type>IS NULL</Operator_Type><Assignment_Value></Assignment_Value><DatabaseCategory_Label></DatabaseCategory_Label><DatabaseField_Label></DatabaseField_Label></Rule_Definition><Rule_Definition><ApplicationCategory_Label>申请信息</ApplicationCategory_Label><ApplicationField_Label>名单等级</ApplicationField_Label><Operator_Type>IN</Operator_Type><Assignment_Value>01</Assignment_Value><DatabaseCategory_Label></DatabaseCategory_Label><DatabaseField_Label></DatabaseField_Label></Rule_Definition></Rule_Definitions><Matched_AppKeys>SIGCNNEGATIVE201709144PCL</Matched_AppKeys></Triggered_Rule></Triggered_Rules></Output></OutputSchema></Instinct_Output_XML><PM_Instinct_Results_Data><PM_Instinct_Results><Instinct_Objective>10</Instinct_Objective><Last_Update_Date>2018-05-14T15:31:48.037+08:00</Last_Update_Date><Instinct_Status>Successful Instinct call.</Instinct_Status><Instinct_Node_Id>1</Instinct_Node_Id><Instinct_Id>I1</Instinct_Id><Instinct_Test_Group>0</Instinct_Test_Group></PM_Instinct_Results></PM_Instinct_Results_Data><PM_Instinct_Details_Data><PM_Instinct_Details><Sequence_Number>1</Sequence_Number><Output_Name>Organisation</Output_Name><Output_Value>SIG</Output_Value></PM_Instinct_Details><PM_Instinct_Details><Sequence_Number>2</Sequence_Number><Output_Name>Country_Code</Output_Name><Output_Value>CN</Output_Value></PM_Instinct_Details><PM_Instinct_Details><Sequence_Number>3</Sequence_Number><Output_Name>Group_Member_Code</Output_Name><Output_Value /></PM_Instinct_Details><PM_Instinct_Details><Sequence_Number>4</Sequence_Number><Output_Name>Application_Number</Output_Name><Output_Value>WD1514001805141532W</Output_Value></PM_Instinct_Details><PM_Instinct_Details><Sequence_Number>5</Sequence_Number><Output_Name>Capture_Date</Output_Name><Output_Value /></PM_Instinct_Details><PM_Instinct_Details><Sequence_Number>6</Sequence_Number><Output_Name>Capture_Time</Output_Name><Output_Value /></PM_Instinct_Details><PM_Instinct_Details><Sequence_Number>7</Sequence_Number><Output_Name>Application_Type</Output_Name><Output_Value>WDL</Output_Value></PM_Instinct_Details><PM_Instinct_Details><Sequence_Number>8</Sequence_Number><Output_Name>Fraud_Score</Output_Name><Output_Value>130</Output_Value></PM_Instinct_Details><PM_Instinct_Details><Sequence_Number>9</Sequence_Number><Output_Name>Fraud_Alert</Output_Name><Output_Value>H</Output_Value></PM_Instinct_Details><PM_Instinct_Details><Sequence_Number>10</Sequence_Number><Output_Name>Action_Taken</Output_Name><Output_Value>S</Output_Value></PM_Instinct_Details><PM_Instinct_Details><Sequence_Number>11</Sequence_Number><Output_Name>Error_Code</Output_Name><Output_Value /></PM_Instinct_Details><PM_Instinct_Details><Sequence_Number>12</Sequence_Number><Output_Name>Action_User</Output_Name><Output_Value /></PM_Instinct_Details><PM_Instinct_Details><Sequence_Number>13</Sequence_Number><Output_Name>Rule_Triggered_1</Output_Name><Output_Value>AW005</Output_Value></PM_Instinct_Details><PM_Instinct_Details><Sequence_Number>14</Sequence_Number><Output_Name>Rule_Triggered_2</Output_Name><Output_Value>CR1001</Output_Value></PM_Instinct_Details><PM_Instinct_Details><Sequence_Number>15</Sequence_Number><Output_Name>Rule_Triggered_3</Output_Name><Output_Value /></PM_Instinct_Details><PM_Instinct_Details><Sequence_Number>16</Sequence_Number><Output_Name>Rule_Triggered_4</Output_Name><Output_Value /></PM_Instinct_Details><PM_Instinct_Details><Sequence_Number>17</Sequence_Number><Output_Name>Rule_Triggered_5</Output_Name><Output_Value /></PM_Instinct_Details><PM_Instinct_Details><Sequence_Number>18</Sequence_Number><Output_Name>Rule_Triggered_6</Output_Name><Output_Value /></PM_Instinct_Details><PM_Instinct_Details><Sequence_Number>19</Sequence_Number><Output_Name>Rule_Triggered_7</Output_Name><Output_Value /></PM_Instinct_Details><PM_Instinct_Details><Sequence_Number>20</Sequence_Number><Output_Name>Rule_Triggered_8</Output_Name><Output_Value /></PM_Instinct_Details><PM_Instinct_Details><Sequence_Number>21</Sequence_Number><Output_Name>Rule_Triggered_9</Output_Name><Output_Value /></PM_Instinct_Details><PM_Instinct_Details><Sequence_Number>22</Sequence_Number><Output_Name>Rule_Triggered_10</Output_Name><Output_Value /></PM_Instinct_Details><PM_Instinct_Details><Sequence_Number>23</Sequence_Number><Output_Name>Rule_Triggered_11</Output_Name><Output_Value /></PM_Instinct_Details><PM_Instinct_Details><Sequence_Number>24</Sequence_Number><Output_Name>Rule_Triggered_12</Output_Name><Output_Value /></PM_Instinct_Details><PM_Instinct_Details><Sequence_Number>25</Sequence_Number><Output_Name>Rule_Triggered_13</Output_Name><Output_Value /></PM_Instinct_Details><PM_Instinct_Details><Sequence_Number>26</Sequence_Number><Output_Name>Rule_Triggered_14</Output_Name><Output_Value /></PM_Instinct_Details><PM_Instinct_Details><Sequence_Number>27</Sequence_Number><Output_Name>Rule_Triggered_15</Output_Name><Output_Value /></PM_Instinct_Details><PM_Instinct_Details><Sequence_Number>28</Sequence_Number><Output_Name>Rule_Triggered_16</Output_Name><Output_Value /></PM_Instinct_Details><PM_Instinct_Details><Sequence_Number>29</Sequence_Number><Output_Name>Rule_Triggered_17</Output_Name><Output_Value /></PM_Instinct_Details><PM_Instinct_Details><Sequence_Number>30</Sequence_Number></PM_Instinct_Details><PM_Instinct_Details><Sequence_Number>31</Sequence_Number><Output_Name>Rule_Triggered_19</Output_Name><Output_Value /></PM_Instinct_Details><PM_Instinct_Details><Sequence_Number>32</Sequence_Number><Output_Name>Rule_Triggered_20</Output_Name><Output_Value /></PM_Instinct_Details><PM_Instinct_Details><Sequence_Number>33</Sequence_Number><Output_Name>Decision_Reason</Output_Name><Output_Value>H201-整体风险过高</Output_Value></PM_Instinct_Details><PM_Instinct_Details><Sequence_Number>34</Sequence_Number><Output_Name>CountNbr</Output_Name><Output_Value>1</Output_Value></PM_Instinct_Details><PM_Instinct_Details><Sequence_Number>35</Sequence_Number><Output_Name>NatureOfFraud</Output_Name><Output_Value /></PM_Instinct_Details><PM_Instinct_Details><Sequence_Number>36</Sequence_Number><Output_Name>Diary</Output_Name><Output_Value>14/05/2018153148</Output_Value></PM_Instinct_Details><PM_Instinct_Details><Sequence_Number>37</Sequence_Number><Output_Name>Triggered_Rules</Output_Name></PM_Instinct_Details></PM_Instinct_Details_Data></Instinct_Results><Decision_Results name=\"系统决策\"><PM_Decision_Results_Data><PM_Decision_Results><Last_Update_Date>2018-05-14T15:31:48.163+08:00</Last_Update_Date><Default_Decision>R</Default_Decision><Default_Reason>A999 - 网贷转人工</Default_Reason><Default_Document>0</Default_Document><System_Decision>D</System_Decision><System_Document>0</System_Document><Decision_Node_Id>2</Decision_Node_Id><Decision_Test_Group>0</Decision_Test_Group></PM_Decision_Results></PM_Decision_Results_Data><PM_Reason_Codes_Data><PM_Reason_Codes><Reason_Code>B206</Reason_Code><Reason_Description>活体比对分数过低</Reason_Description><Criteria_Name>网贷活体比对不一致</Criteria_Name><Letter_Code /><Document /><Letter_Reason /></PM_Reason_Codes><PM_Reason_Codes><Reason_Code>AC020</Reason_Code><Reason_Description>INS自动拒绝：触发黑名单</Reason_Description><Criteria_Name>网贷提现命中黑名单</Criteria_Name><Letter_Code /><Document /><Letter_Reason /></PM_Reason_Codes><PM_Reason_Codes><Reason_Code>AC020</Reason_Code><Reason_Description>INS自动拒绝：触发黑名单</Reason_Description><Criteria_Name>网贷提现有欺诈嫌疑</Criteria_Name><Letter_Code /><Document /><Letter_Reason /></PM_Reason_Codes></PM_Reason_Codes_Data></Decision_Results><Strategy_Results name=\"额度策略\"><PM_Strategy_MaxLendingAmount_Data /></Strategy_Results></PM_Results><PM_Outputs></PM_Outputs></ProcessManagerResponse>";
		
		System.out.println(new PMXMlResultUtils().parseDecisionResultXML(xml));
	}
}
