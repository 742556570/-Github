package com.jeeplus.modules.app.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.app.api.rulesengine.pojo.DecisionReq;
import com.jeeplus.modules.app.constant.AppNormalConstants;
import com.jeeplus.modules.app.dao.ClCapChannelDao;
import com.jeeplus.modules.app.entity.ClBnkCrd;
import com.jeeplus.modules.app.entity.ClCapChannel;
import com.jeeplus.modules.app.entity.ClIdCardInfo;
import com.jeeplus.modules.app.utils.HttpConUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Test {

	public static void main(String[] args) throws Exception {
		
//		String test =  "huawei&p10&7.0";
//		
//		
//		String[] tmp = test.split("&",-1);
//
//		for (String string : tmp) {
//			System.out.println(string);
//		}
		
		
//		String test = "1 2 3     4	567890";
//		String tmp = test.replaceAll("\\s*", "");
//		System.out.println(tmp);
//		System.out.println(tmp.substring(0, 6));
//		System.out.println(tmp.substring(0, 9));
		
//		System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
		
//		JedisPoolConfig cft = new JedisPoolConfig();
//		
//		JedisPool jp =  new JedisPool(cft,"127.0.0.1",7397,60,"123456");
		
//		ClCrdtExt test =new ClCrdtExt();
//		test.setSTATUS("00");
//		if("00".equals(test.getSTATUS())) {
//			test.setSTATUS("01");
//			System.out.println("00");
//		}else if ("01".equals(test.getSTATUS())) {
//			test.setSTATUS("10");
//			System.out.println("10");
//		}else {
//			System.out.println("...");
//		}
//		
	
//		String a = DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
//		System.out.println(a.substring(0, a.indexOf(" ")));
		
//		JSONObject a = new JSONObject();
//		a.put("a", 1);
//		System.out.println(a.getString("a") instanceof String );
		
//		String a  = "\n" + 
//				"<ProcessManagerResponse>\n" + 
//				"	<Application_Header>\n" + 
//				"		<Channel>WDL</Channel>\n" + 
//				"		<CallType>WD1</CallType>\n" + 
//				"		<ApplicationNumber>2018030612234567</ApplicationNumber>\n" + 
//				"		<ReturnDateTime>20180306135150</ReturnDateTime>\n" + 
//				"		<SystemErrorCode/>\n" + 
//				"		<SystemErrorMessage/>\n" + 
//				"		<ReturnError RecordNumber=\"0\" />\n" + 
//				"	</Application_Header>\n" + 
//				"	<Instinct_Actions>\n" + 
//				"		<Instinct_Action>\n" + 
//				"			<Field_Name>Action_Taken</Field_Name>\n" + 
//				"			<Field_Value />\n" + 
//				"		</Instinct_Action>\n" + 
//				"		<Instinct_Action>\n" + 
//				"			<Field_Name>Action_User</Field_Name>\n" + 
//				"			<Field_Value />\n" + 
//				"		</Instinct_Action>\n" + 
//				"		<Instinct_Action>\n" + 
//				"			<Field_Name>Application_Number</Field_Name>\n" + 
//				"			<Field_Value>申请流水号</Field_Value>\n" + 
//				"		</Instinct_Action>\n" + 
//				"		<Instinct_Action>\n" + 
//				"			<Field_Name>Application_Type</Field_Name>\n" + 
//				"			<Field_Value>WDL</Field_Value>\n" + 
//				"		</Instinct_Action>\n" + 
//				"		<Instinct_Action>\n" + 
//				"			<Field_Name>Capture_Date</Field_Name>\n" + 
//				"			<Field_Value />\n" + 
//				"		</Instinct_Action>\n" + 
//				"		<Instinct_Action>\n" + 
//				"			<Field_Name>Capture_Time</Field_Name>\n" + 
//				"			<Field_Value />\n" + 
//				"		</Instinct_Action>\n" + 
//				"		<Instinct_Action>\n" + 
//				"			<Field_Name>CountNbr</Field_Name>\n" + 
//				"			<Field_Value />\n" + 
//				"		</Instinct_Action>\n" + 
//				"		<Instinct_Action>\n" + 
//				"			<Field_Name>Country_Code</Field_Name>\n" + 
//				"			<Field_Value>CN</Field_Value>\n" + 
//				"		</Instinct_Action>\n" + 
//				"		<Instinct_Action>\n" + 
//				"			<Field_Name>Decision_Reason</Field_Name>\n" + 
//				"			<Field_Value />\n" + 
//				"		</Instinct_Action>\n" + 
//				"		<Instinct_Action>\n" + 
//				"			<Field_Name>Error_Code</Field_Name>\n" + 
//				"			<Field_Value />\n" + 
//				"		</Instinct_Action>\n" + 
//				"		<Instinct_Action>\n" + 
//				"			<Field_Name>Fraud_Alert</Field_Name>\n" + 
//				"			<Field_Value />\n" + 
//				"		</Instinct_Action>\n" + 
//				"		<Instinct_Action>\n" + 
//				"			<Field_Name>Fraud_Score</Field_Name>\n" + 
//				"			<Field_Value />\n" + 
//				"		</Instinct_Action>\n" + 
//				"		<Instinct_Action>\n" + 
//				"			<Field_Name>Group_Member_Code</Field_Name>\n" + 
//				"			<Field_Value>组织成员</Field_Value>\n" + 
//				"		</Instinct_Action>\n" + 
//				"		<Instinct_Action>\n" + 
//				"			<Field_Name>Organisation</Field_Name>\n" + 
//				"			<Field_Value>SIG</Field_Value>\n" + 
//				"		</Instinct_Action>\n" + 
//				"		<Instinct_Action>\n" + 
//				"			<Field_Name>Rule_Triggered_1</Field_Name>\n" + 
//				"			<Field_Value />\n" + 
//				"		</Instinct_Action>\n" + 
//				"		<Instinct_Action>\n" + 
//				"			<Field_Name>Rule_Triggered_10</Field_Name>\n" + 
//				"			<Field_Value />\n" + 
//				"		</Instinct_Action>\n" + 
//				"		<Instinct_Action>\n" + 
//				"			<Field_Name>Rule_Triggered_11</Field_Name>\n" + 
//				"			<Field_Value />\n" + 
//				"		</Instinct_Action>\n" + 
//				"		<Instinct_Action>\n" + 
//				"			<Field_Name>Rule_Triggered_12</Field_Name>\n" + 
//				"			<Field_Value />\n" + 
//				"		</Instinct_Action>\n" + 
//				"		<Instinct_Action>\n" + 
//				"			<Field_Name>Rule_Triggered_13</Field_Name>\n" + 
//				"			<Field_Value />\n" + 
//				"		</Instinct_Action>\n" + 
//				"		<Instinct_Action>\n" + 
//				"			<Field_Name>Rule_Triggered_14</Field_Name>\n" + 
//				"			<Field_Value />\n" + 
//				"		</Instinct_Action>\n" + 
//				"		<Instinct_Action>\n" + 
//				"			<Field_Name>Rule_Triggered_15</Field_Name>\n" + 
//				"			<Field_Value />\n" + 
//				"		</Instinct_Action>\n" + 
//				"		<Instinct_Action>\n" + 
//				"			<Field_Name>Rule_Triggered_16</Field_Name>\n" + 
//				"			<Field_Value />\n" + 
//				"		</Instinct_Action>\n" + 
//				"		<Instinct_Action>\n" + 
//				"			<Field_Name>Rule_Triggered_17</Field_Name>\n" + 
//				"			<Field_Value />\n" + 
//				"		</Instinct_Action>\n" + 
//				"		<Instinct_Action>\n" + 
//				"			<Field_Name>Rule_Triggered_18</Field_Name>\n" + 
//				"			<Field_Value />\n" + 
//				"		</Instinct_Action>\n" + 
//				"		<Instinct_Action>\n" + 
//				"			<Field_Name>Rule_Triggered_19</Field_Name>\n" + 
//				"			<Field_Value />\n" + 
//				"		</Instinct_Action>\n" + 
//				"		<Instinct_Action>\n" + 
//				"			<Field_Name>Rule_Triggered_2</Field_Name>\n" + 
//				"			<Field_Value />\n" + 
//				"		</Instinct_Action>\n" + 
//				"		<Instinct_Action>\n" + 
//				"			<Field_Name>Rule_Triggered_20</Field_Name>\n" + 
//				"			<Field_Value />\n" + 
//				"		</Instinct_Action>\n" + 
//				"		<Instinct_Action>\n" + 
//				"			<Field_Name>Rule_Triggered_3</Field_Name>\n" + 
//				"			<Field_Value />\n" + 
//				"		</Instinct_Action>\n" + 
//				"		<Instinct_Action>\n" + 
//				"			<Field_Name>Rule_Triggered_4</Field_Name>\n" + 
//				"			<Field_Value />\n" + 
//				"		</Instinct_Action>\n" + 
//				"		<Instinct_Action>\n" + 
//				"			<Field_Name>Rule_Triggered_5</Field_Name>\n" + 
//				"			<Field_Value />\n" + 
//				"		</Instinct_Action>\n" + 
//				"		<Instinct_Action>\n" + 
//				"			<Field_Name>Rule_Triggered_6</Field_Name>\n" + 
//				"			<Field_Value />\n" + 
//				"		</Instinct_Action>\n" + 
//				"		<Instinct_Action>\n" + 
//				"			<Field_Name>Rule_Triggered_7</Field_Name>\n" + 
//				"			<Field_Value />\n" + 
//				"		</Instinct_Action>\n" + 
//				"		<Instinct_Action>\n" + 
//				"			<Field_Name>Rule_Triggered_8</Field_Name>\n" + 
//				"			<Field_Value />\n" + 
//				"		</Instinct_Action>\n" + 
//				"		<Instinct_Action>\n" + 
//				"			<Field_Name>Rule_Triggered_9</Field_Name>\n" + 
//				"			<Field_Value />\n" + 
//				"		</Instinct_Action>\n" + 
//				"		<Instinct_Action_Output_XML />\n" + 
//				"	</Instinct_Actions>\n" + 
//				"	<PM_Results>\n" + 
//				"		<Random_Number>415</Random_Number>\n" + 
//				"		<DBR>0.00</DBR>\n" + 
//				"		<Instinct_Results name=\"Instinct\">\n" + 
//				"			<Instinct_Output_XML>\n" + 
//				"				<OutputSchema>\n" + 
//				"					<Output>\n" + 
//				"						<Organisation>SIG</Organisation>\n" + 
//				"						<Country_Code>CN</Country_Code>\n" + 
//				"						<Group_Member_Code>组织成员</Group_Member_Code>\n" + 
//				"						<Application_Number>申请流水号</Application_Number>\n" + 
//				"						<Capture_Date/>\n" + 
//				"						<Capture_Time/>\n" + 
//				"						<Application_Type>WDL</Application_Type>\n" + 
//				"						<Fraud_Score/>\n" + 
//				"						<Fraud_Alert/>\n" + 
//				"						<Action_Taken/>\n" + 
//				"						<Error_Code/>\n" + 
//				"						<Action_User/>\n" + 
//				"						<Rule_Triggered_1/>\n" + 
//				"						<Rule_Triggered_2/>\n" + 
//				"						<Rule_Triggered_3/>\n" + 
//				"						<Rule_Triggered_4/>\n" + 
//				"						<Rule_Triggered_5/>\n" + 
//				"						<Rule_Triggered_6/>\n" + 
//				"						<Rule_Triggered_7/>\n" + 
//				"						<Rule_Triggered_8/>\n" + 
//				"						<Rule_Triggered_9/>\n" + 
//				"						<Rule_Triggered_10/>\n" + 
//				"						<Rule_Triggered_11/>\n" + 
//				"						<Rule_Triggered_12/>\n" + 
//				"						<Rule_Triggered_13/>\n" + 
//				"						<Rule_Triggered_14/>\n" + 
//				"						<Rule_Triggered_15/>\n" + 
//				"						<Rule_Triggered_16/>\n" + 
//				"						<Rule_Triggered_17/>\n" + 
//				"						<Rule_Triggered_18/>\n" + 
//				"						<Rule_Triggered_19/>\n" + 
//				"						<Rule_Triggered_20/>\n" + 
//				"						<Decision_Reason/>\n" + 
//				"						<CountNbr/>\n" + 
//				"						<Diary>\n" + 
//				"							<Diary_Date/>\n" + 
//				"							<Diary_Time/>\n" + 
//				"							<Diary_User_Id/>\n" + 
//				"							<Diary_Note/>\n" + 
//				"						</Diary>\n" + 
//				"						<Diary>\n" + 
//				"							<Diary_Date/>\n" + 
//				"							<Diary_Time/>\n" + 
//				"							<Diary_User_Id/>\n" + 
//				"							<Diary_Note/>\n" + 
//				"						</Diary>\n" + 
//				"						<Diary>\n" + 
//				"							<Diary_Date/>\n" + 
//				"							<Diary_Time/>\n" + 
//				"							<Diary_User_Id/>\n" + 
//				"							<Diary_Note/>\n" + 
//				"						</Diary>\n" + 
//				"						<Diary>\n" + 
//				"							<Diary_Date/>\n" + 
//				"							<Diary_Time/>\n" + 
//				"							<Diary_User_Id/>\n" + 
//				"							<Diary_Note/>\n" + 
//				"						</Diary>\n" + 
//				"						<Diary>\n" + 
//				"							<Diary_Date/>\n" + 
//				"							<Diary_Time/>\n" + 
//				"							<Diary_User_Id/>\n" + 
//				"							<Diary_Note/>\n" + 
//				"						</Diary>\n" + 
//				"						<Diary>\n" + 
//				"							<Diary_Date/>\n" + 
//				"							<Diary_Time/>\n" + 
//				"							<Diary_User_Id/>\n" + 
//				"							<Diary_Note/>\n" + 
//				"						</Diary>\n" + 
//				"						<Diary>\n" + 
//				"							<Diary_Date/>\n" + 
//				"							<Diary_Time/>\n" + 
//				"							<Diary_User_Id/>\n" + 
//				"							<Diary_Note/>\n" + 
//				"						</Diary>\n" + 
//				"						<Diary>\n" + 
//				"							<Diary_Date/>\n" + 
//				"							<Diary_Time/>\n" + 
//				"							<Diary_User_Id/>\n" + 
//				"							<Diary_Note/>\n" + 
//				"						</Diary>\n" + 
//				"						<Diary>\n" + 
//				"							<Diary_Date/>\n" + 
//				"							<Diary_Time/>\n" + 
//				"							<Diary_User_Id/>\n" + 
//				"							<Diary_Note/>\n" + 
//				"						</Diary>\n" + 
//				"						<Diary>\n" + 
//				"							<Diary_Date/>\n" + 
//				"							<Diary_Time/>\n" + 
//				"							<Diary_User_Id/>\n" + 
//				"							<Diary_Note/>\n" + 
//				"						</Diary>\n" + 
//				"						<Diary>\n" + 
//				"							<Diary_Date/>\n" + 
//				"							<Diary_Time/>\n" + 
//				"							<Diary_User_Id/>\n" + 
//				"							<Diary_Note/>\n" + 
//				"						</Diary>\n" + 
//				"						<Diary>\n" + 
//				"							<Diary_Date/>\n" + 
//				"							<Diary_Time/>\n" + 
//				"							<Diary_User_Id/>\n" + 
//				"							<Diary_Note/>\n" + 
//				"						</Diary>\n" + 
//				"						<Diary>\n" + 
//				"							<Diary_Date/>\n" + 
//				"							<Diary_Time/>\n" + 
//				"							<Diary_User_Id/>\n" + 
//				"							<Diary_Note/>\n" + 
//				"						</Diary>\n" + 
//				"						<Diary>\n" + 
//				"							<Diary_Date/>\n" + 
//				"							<Diary_Time/>\n" + 
//				"							<Diary_User_Id>0</Diary_User_Id>\n" + 
//				"							<Diary_Note>06/03/2018</Diary_Note>\n" + 
//				"						</Diary>\n" + 
//				"						<Diary>\n" + 
//				"							<Diary_Date>135150</Diary_Date>\n" + 
//				"							<Diary_Time/>\n" + 
//				"							<Diary_User_Id/>\n" + 
//				"							<Diary_Note/>\n" + 
//				"						</Diary>\n" + 
//				"					</Output>\n" + 
//				"				</OutputSchema>\n" + 
//				"			</Instinct_Output_XML>\n" + 
//				"			<PM_Instinct_Results_Data>\n" + 
//				"				<PM_Instinct_Results>\n" + 
//				"					<Instinct_Objective>9</Instinct_Objective>\n" + 
//				"					<Last_Update_Date>2018-03-06T13:51:50.12+08:00</Last_Update_Date>\n" + 
//				"					<Instinct_Status>Successful Instinct call.</Instinct_Status>\n" + 
//				"					<Instinct_Node_Id>1</Instinct_Node_Id>\n" + 
//				"					<Instinct_Id>I1</Instinct_Id>\n" + 
//				"					<Instinct_Test_Group>0</Instinct_Test_Group>\n" + 
//				"				</PM_Instinct_Results>\n" + 
//				"			</PM_Instinct_Results_Data>\n" + 
//				"			<PM_Instinct_Details_Data>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>1</Sequence_Number>\n" + 
//				"					<Output_Name>Organisation</Output_Name>\n" + 
//				"					<Output_Value>SIG</Output_Value>\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>2</Sequence_Number>\n" + 
//				"					<Output_Name>Country_Code</Output_Name>\n" + 
//				"					<Output_Value>CN</Output_Value>\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>3</Sequence_Number>\n" + 
//				"					<Output_Name>Group_Member_Code</Output_Name>\n" + 
//				"					<Output_Value>组织成员</Output_Value>\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>4</Sequence_Number>\n" + 
//				"					<Output_Name>Application_Number</Output_Name>\n" + 
//				"					<Output_Value>申请流水号</Output_Value>\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>5</Sequence_Number>\n" + 
//				"					<Output_Name>Capture_Date</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>6</Sequence_Number>\n" + 
//				"					<Output_Name>Capture_Time</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>7</Sequence_Number>\n" + 
//				"					<Output_Name>Application_Type</Output_Name>\n" + 
//				"					<Output_Value>WDL</Output_Value>\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>8</Sequence_Number>\n" + 
//				"					<Output_Name>Fraud_Score</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>9</Sequence_Number>\n" + 
//				"					<Output_Name>Fraud_Alert</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>10</Sequence_Number>\n" + 
//				"					<Output_Name>Action_Taken</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>11</Sequence_Number>\n" + 
//				"					<Output_Name>Error_Code</Output_Name>\n" + 
//				"					<Output_Value>04</Output_Value>\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>12</Sequence_Number>\n" + 
//				"					<Output_Name>Action_User</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>13</Sequence_Number>\n" + 
//				"					<Output_Name>Rule_Triggered_1</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>14</Sequence_Number>\n" + 
//				"					<Output_Name>Rule_Triggered_2</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>15</Sequence_Number>\n" + 
//				"					<Output_Name>Rule_Triggered_3</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>16</Sequence_Number>\n" + 
//				"					<Output_Name>Rule_Triggered_4</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>17</Sequence_Number>\n" + 
//				"					<Output_Name>Rule_Triggered_5</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>18</Sequence_Number>\n" + 
//				"					<Output_Name>Rule_Triggered_6</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>19</Sequence_Number>\n" + 
//				"					<Output_Name>Rule_Triggered_7</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>20</Sequence_Number>\n" + 
//				"					<Output_Name>Rule_Triggered_8</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>21</Sequence_Number>\n" + 
//				"					<Output_Name>Rule_Triggered_9</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>22</Sequence_Number>\n" + 
//				"					<Output_Name>Rule_Triggered_10</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>23</Sequence_Number>\n" + 
//				"					<Output_Name>Rule_Triggered_11</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>24</Sequence_Number>\n" + 
//				"					<Output_Name>Rule_Triggered_12</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>25</Sequence_Number>\n" + 
//				"					<Output_Name>Rule_Triggered_13</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>26</Sequence_Number>\n" + 
//				"					<Output_Name>Rule_Triggered_14</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>27</Sequence_Number>\n" + 
//				"					<Output_Name>Rule_Triggered_15</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>28</Sequence_Number>\n" + 
//				"					<Output_Name>Rule_Triggered_16</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>29</Sequence_Number>\n" + 
//				"					<Output_Name>Rule_Triggered_17</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>30</Sequence_Number>\n" + 
//				"					<Output_Name>Rule_Triggered_18</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>31</Sequence_Number>\n" + 
//				"					<Output_Name>Rule_Triggered_19</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>32</Sequence_Number>\n" + 
//				"					<Output_Name>Rule_Triggered_20</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>33</Sequence_Number>\n" + 
//				"					<Output_Name>Decision_Reason</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>34</Sequence_Number>\n" + 
//				"					<Output_Name>CountNbr</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>35</Sequence_Number>\n" + 
//				"					<Output_Name>Diary</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>36</Sequence_Number>\n" + 
//				"					<Output_Name>Diary</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>37</Sequence_Number>\n" + 
//				"					<Output_Name>Diary</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>38</Sequence_Number>\n" + 
//				"					<Output_Name>Diary</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>39</Sequence_Number>\n" + 
//				"					<Output_Name>Diary</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>40</Sequence_Number>\n" + 
//				"					<Output_Name>Diary</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>41</Sequence_Number>\n" + 
//				"					<Output_Name>Diary</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>42</Sequence_Number>\n" + 
//				"					<Output_Name>Diary</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>43</Sequence_Number>\n" + 
//				"					<Output_Name>Diary</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>44</Sequence_Number>\n" + 
//				"					<Output_Name>Diary</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>45</Sequence_Number>\n" + 
//				"					<Output_Name>Diary</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>46</Sequence_Number>\n" + 
//				"					<Output_Name>Diary</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>47</Sequence_Number>\n" + 
//				"					<Output_Name>Diary</Output_Name>\n" + 
//				"					<Output_Value />\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>48</Sequence_Number>\n" + 
//				"					<Output_Name>Diary</Output_Name>\n" + 
//				"					<Output_Value>006/03/2018</Output_Value>\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"				<PM_Instinct_Details>\n" + 
//				"					<Sequence_Number>49</Sequence_Number>\n" + 
//				"					<Output_Name>Diary</Output_Name>\n" + 
//				"					<Output_Value>135150</Output_Value>\n" + 
//				"				</PM_Instinct_Details>\n" + 
//				"			</PM_Instinct_Details_Data>\n" + 
//				"		</Instinct_Results>\n" + 
//				"		<Decision_Results name=\"系统决策\">\n" + 
//				"			<PM_Decision_Results_Data>\n" + 
//				"				<PM_Decision_Results>\n" + 
//				"					<AppKey>2018030612234567WDL</AppKey>\n" + 
//				"					<Decision_Objective>10</Decision_Objective>\n" + 
//				"					<Last_Update_Date>2018-03-06T13:51:50.253+08:00</Last_Update_Date>\n" + 
//				"					<Default_Decision>A</Default_Decision>\n" + 
//				"					<Default_Reason>A000 - 风控政策通过</Default_Reason>\n" + 
//				"					<Default_Document>0</Default_Document>\n" + 
//				"					<System_Decision>D</System_Decision>\n" + 
//				"					<System_Document>0</System_Document>\n" + 
//				"					<Decision_Node_Id>1</Decision_Node_Id>\n" + 
//				"					<Decision_Test_Group>0</Decision_Test_Group>\n" + 
//				"				</PM_Decision_Results>\n" + 
//				"			</PM_Decision_Results_Data>\n" + 
//				"			<PM_Reason_Codes_Data>\n" + 
//				"				<PM_Reason_Codes>\n" + 
//				"					<Reason_Code>D210</Reason_Code>\n" + 
//				"					<Reason_Description>年龄不符</Reason_Description>\n" + 
//				"					<Criteria_Name>FA1001年龄不符</Criteria_Name>\n" + 
//				"					<Letter_Code />\n" + 
//				"					<Document />\n" + 
//				"					<Letter_Reason />\n" + 
//				"				</PM_Reason_Codes>\n" + 
//				"			</PM_Reason_Codes_Data>\n" + 
//				"		</Decision_Results>\n" + 
//				"		<Strategy_Results name=\"额度策略\">\n" + 
//				"			<PM_Strategy_MaxLendingAmount_Data>\n" + 
//				"				<PM_Strategy_MaxLendingAmount_Results>\n" + 
//				"					<Policy_Number>1</Policy_Number>\n" + 
//				"					<Adjustment_Number>0</Adjustment_Number>\n" + 
//				"					<Max_Lending_Amount>67000.00</Max_Lending_Amount>\n" + 
//				"					<Last_Update_Date>2018-03-06T13:56:18.113+08:00</Last_Update_Date>\n" + 
//				"					<Entry_Name>保单贷额度</Entry_Name>\n" + 
//				"					<Use_Requested_Limit_Flag>true</Use_Requested_Limit_Flag>\n" + 
//				"					<Existing_Limit_Increase_Flag>false</Existing_Limit_Increase_Flag>\n" + 
//				"					<Limit_Increase_Percent>0.00</Limit_Increase_Percent>\n" + 
//				"					<Use_Characteristic_As_Limit_Flag>false</Use_Characteristic_As_Limit_Flag>\n" + 
//				"					<Limit_Table_Id>11</Limit_Table_Id>\n" + 
//				"					<Limit_Field_Number>12</Limit_Field_Number>\n" + 
//				"					<Use_Characteristic_As_Limit_Multiplier>0.000</Use_Characteristic_As_Limit_Multiplier>\n" + 
//				"					<Fixed_Amount_Flag>false</Fixed_Amount_Flag>\n" + 
//				"					<Fixed_Amount>0</Fixed_Amount>\n" + 
//				"					<Base_Percentage_Increase_Flag>false</Base_Percentage_Increase_Flag>\n" + 
//				"					<Base_Percentage_Increase_Percent>0.00</Base_Percentage_Increase_Percent>\n" + 
//				"					<Limit_Minimum>0</Limit_Minimum>\n" + 
//				"					<Limit_Maximum>999999999</Limit_Maximum>\n" + 
//				"					<Limit_Rounding_Factor1>1000</Limit_Rounding_Factor1>\n" + 
//				"					<Limit_Rounding_Factor2>1000</Limit_Rounding_Factor2>\n" + 
//				"					<Limit_Rounding_Factor3>1000</Limit_Rounding_Factor3>\n" + 
//				"					<Limit_Rounding_Factor4>1000</Limit_Rounding_Factor4>\n" + 
//				"					<Limit_Rounding_Cutoff1>0</Limit_Rounding_Cutoff1>\n" + 
//				"					<Limit_Rounding_Cutoff2>0</Limit_Rounding_Cutoff2>\n" + 
//				"					<Limit_Rounding_Cutoff3>999999999</Limit_Rounding_Cutoff3>\n" + 
//				"					<Limit_Reason_Code />\n" + 
//				"					<Max_Limit_Table_Id>0</Max_Limit_Table_Id>\n" + 
//				"					<Max_Limit_Field_Number>0</Max_Limit_Field_Number>\n" + 
//				"					<Max_Limit_Multiplier>0.00</Max_Limit_Multiplier>\n" + 
//				"					<Use_Round_Up_Factor1_Flag>false</Use_Round_Up_Factor1_Flag>\n" + 
//				"					<Use_Round_Up_Factor2_Flag>false</Use_Round_Up_Factor2_Flag>\n" + 
//				"					<Use_Round_Up_Factor3_Flag>false</Use_Round_Up_Factor3_Flag>\n" + 
//				"					<Use_Round_Up_Factor4_Flag>false</Use_Round_Up_Factor4_Flag>\n" + 
//				"					<Strategy_Node_Id>1.6</Strategy_Node_Id>\n" + 
//				"					<Strategy_Test_Group>0</Strategy_Test_Group>\n" + 
//				"					<Tree_Error_Field_Label />\n" + 
//				"					<Tree_Error_Field_Value />\n" + 
//				"				</PM_Strategy_MaxLendingAmount_Results>\n" + 
//				"			</PM_Strategy_MaxLendingAmount_Data>\n" + 
//				"		</Strategy_Results>\n" + 
//				"	</PM_Results>\n" + 
//				"	<PM_Outputs/>\n" + 
//				"</ProcessManagerResponse>";
//		
//		XMLSerializer xmlSerializer = new XMLSerializer();  
//        JSON jsonObj = xmlSerializer.read(a);  
//        String jsonStr = jsonObj.toString();  
//        
//        System.out.println(jsonStr);
////        jsonStr = jsonStr.replace("[]", "\"\""); 
//		System.out.println( DateUtils.formatDate(new Date(), "yyMMddHHmm"));
//		System.out.println( (AppNormalConstants.policePrefix+14900+DateUtils.formatDate(new Date(), "yyMMddHHmm")+AppNormalConstants.policeSuffix).length());
//		
		
	
		
//		String a = "1.3001000000";
		
//		String a = "";
		
		
//		String  test = "丢雷螺母";
//		
//		String nt = new String(test.getBytes("utf-8"), "ascii");
//		
//		File file = new File("d:/test.txt");
//		
//		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)
//				, "UTF-8"));
//		
//		bw.write(test);
//		bw.flush();
//		bw.close();
		
		
//		List<String> str = FileUtils.readLines(file);
//		String te = str.get(0);
		
//		String nt = new String(te.getBytes("ascii"),"utf-8");
//		System.out.println(nt);
		
//		FileUtils.write(file, "haha", "GBK");
		
//		JSONObject n = new JSONObject();
//		
//		System.out.println(n.isEmpty());
		
		
//		JSONObject json = new JSONObject();
//		json.put("isReady", true);
//		
//		System.out.println(json.getString("isReady"));
		
//		BigDecimal monthPre = new BigDecimal("3000").multiply(new BigDecimal("0.010036"));
//		
//		monthPre = monthPre.setScale(2, BigDecimal.ROUND_HALF_UP);
//		
//		BigDecimal sumP = monthPre.multiply(new BigDecimal("12"));
//		
//		System.out.println(sumP);
		
		
		
//		System.out.println(DateUtils.formatDate(new Date(), "yyyy-MM-dd'T'HH:mm:ss"));
		
//		JedisPoolConfig config = new JedisPoolConfig();
//		config.setMaxIdle(300);
//		config.setMaxTotal(60000);
//		config.setTestOnBorrow(false);
//		
//		
//		JedisPool jp = new JedisPool(config, "10.7.128.33", 6379,10000,"123456");
//		
//		Jedis jedis = jp.getResource();
//		
//		
//		
//		System.out.println(jedis.get("test"));
		
//		Calendar c = Calendar.getInstance();
//		c.add(Calendar.DAY_OF_MONTH, -3);
//		String targetDt = DateUtils.formatDate(c.getTime(), "yyyy-MM-dd");
//		System.out.println(targetDt);
		
//		DecimalFormat df = new DecimalFormat("0.00");
//		
//		BigDecimal i = new BigDecimal("1230.034000");
//		BigDecimal j = new BigDecimal("0.034000");
//		BigDecimal k = new BigDecimal("0.000000");
//		
//		System.out.println(df.format(i));
//		System.out.println(df.format(j));
//		System.out.println(df.format(k));
		
//		String tmp = "301212*******324234*****8sadasd";
//		
//		tmp = tmp.replaceAll("(\\*)\\1+", "%");
//		
//		System.out.println(tmp);
//		
	
//		System.out.println(new BigDecimal("100").add(null));
		
		
//		System.out.println(StringUtils.contains("12345", "123456,1234"));
		
//		List<String> lines = FileUtils.readLines(new File("D://fail.txt"));
//		
//		List<String> hedui = new ArrayList<String>();
//		
//		File out = new File("D://hedui.txt");
//		if(out.exists()) {
//			out.delete();
//		}
//			
//		out.createNewFile();
//		
//		for (String line : lines) {
//			String[] tmp = line.split("\t");
//			if(tmp.length != 5) {
//				continue;
//			}
//			String result = new BigDecimal(tmp[1]).multiply(new BigDecimal(tmp[2])).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
//			System.out.println(line + "\t" + result);
//			
//			hedui.add(line + "\t" + result);
//			
//		}
//		
//		FileUtils.writeLines(out, hedui);
		
		
		String tst = "asdasd^5$#2&***<>";
		
		System.out.println(StringUtils.StringFilter(tst));
		
	}
		
}
