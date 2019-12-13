package com.jeeplus.modules.app.api.invalidpolicy.util;

import org.apache.commons.collections.map.LinkedMap;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


/**
 * <p>客户端基类:Xml转换工具类</p>
 * <p>XmlToMap  MapToXml  </p>
 * @author XuQZ
 * @createDate 2016-7-27 15:13:41
 */
public class XmlUtil  {
	
	
	public static void main(String[] args) throws Exception {
		String data="<?xml version=\"1.0\" encoding=\"UTF-8\"?><creditreport><summary><reportSN>2014061300001709216895</reportSN><queryTime>2014.06.13 13:55:27</queryTime><reportCreateTime>2014.06.13 13:55:28</reportCreateTime><querierName>张XXXX</querierName><querierCertType>身份证</querierCertType><querierCertNo>320100000000001836</querierCertNo><userCode>南京银行XX支行/rb01</userCode><queryReason>贷款审批</queryReason></summary><personalIdentity><gender>男性</gender><birthday>XXXX.XX.XX</birthday><maritalState>已婚</maritalState><mobileTelephoneNo>13913913999</mobileTelephoneNo><officeTelephoneNo>02588888888</officeTelephoneNo><homeTelephoneNo>57711077</homeTelephoneNo><eduLevel>研究生</eduLevel><eduDegree>其他</eduDegree><postAddress>江苏省南京市建邺区江东中路259号</postAddress><registeredAddress>南京</registeredAddress></personalIdentity><spouse><name>--</name><certificateType>--</certificateType><certificateNo>--</certificateNo><employer>--</employer><telephoneNo>--</telephoneNo></spouse><resides><residence><occureDate>2013.08.02</occureDate><address>江苏省南京市玄武区龙蟠中路</address><type>自置</type></residence><residence><occureDate>2009.10.10</occureDate><address>江苏南京玄武区龙蟠中路</address><type>自置</type></residence><residence><occureDate>2009.07.24</occureDate><address>南京</address><type>自置</type></residence><residence><occureDate>2009.04.08</occureDate><address>玄武区龙蟠中路</address><type>按揭</type></residence><residence><occureDate>2007.10.24</occureDate><address>江苏省南京市玄武区龙蟠中路</address><type>自置</type></residence></resides><professions><professional><title>无</title><employerProfession>公共管理</employerProfession><occupation>机关</occupation><employerName>南京市xxxxx局</employerName><occureDate>2009.07.24</occureDate><startYear>--</startYear><duty>--</duty><employerAddress>成贤街</employerAddress></professional><professional><title>--</title><employerProfession>--</employerProfession><occupation>--</occupation><employerName>市计财处</employerName><occureDate>2009.10.10</occureDate><startYear>--</startYear><duty>中级领导（行政级别局级以下领导或大公司中级管理人员）</duty><employerAddress>--</employerAddress></professional><professional><title>--</title><employerProfession>--</employerProfession><occupation>--</occupation><employerName>南京市监督局</employerName><occureDate>2013.08.02</occureDate><startYear>--</startYear><duty>--</duty><employerAddress>江苏省南京市建邺区江东中路259号</employerAddress></professional><professional><title>--</title><employerProfession>--</employerProfession><occupation>--</occupation><employerName>南京市办公室</employerName><occureDate>1997.12.22</occureDate><startYear>--</startYear><duty>--</duty><employerAddress>--</employerAddress></professional></professions><creditTip><houseLoanCnt>1</houseLoanCnt><otherLoanCnt>8</otherLoanCnt><firstLoanPaymentMonth>2000.04</firstLoanPaymentMonth><loanCardAccountNumber>6</loanCardAccountNumber><firstLoanCardIssuedMonth>2003.09</firstLoanCardIssuedMonth><standardLoancardAccountNumber>0</standardLoancardAccountNumber><firstStandardLoancardIssueMonth>--</firstStandardLoancardIssueMonth><announcementCnt>0</announcementCnt><dissentCnt>0</dissentCnt></creditTip><unpaymentAndInvalidSummary><badAccountTotalCnt></badAccountTotalCnt><badAccountTotalBalance></badAccountTotalBalance><assetDisposalTotalCnt></assetDisposalTotalCnt><assetDisposalTotalBalance></assetDisposalTotalBalance><guarateePayTotalCnt></guarateePayTotalCnt><guarateePayTotalBalance></guarateePayTotalBalance></unpaymentAndInvalidSummary><unpaymentOverdraftSummary><loanUnpaymentTotalCnt>1</loanUnpaymentTotalCnt><loanUnpaymentTotalMonths>3</loanUnpaymentTotalMonths><loanUnpaymentMaxUnpayAmount>3,590</loanUnpaymentMaxUnpayAmount><loanUnpaymentLongestUnpayMonths>1</loanUnpaymentLongestUnpayMonths><loanCardUnpaymentAccountCnt>2</loanCardUnpaymentAccountCnt><loanCardUnpaymentTotalMonths>6</loanCardUnpaymentTotalMonths><loanCardUnpaymentMaxUnpayAmount>1,125</loanCardUnpaymentMaxUnpayAmount><loanCardUnpaymentLongestUnpayMonths>3</loanCardUnpaymentLongestUnpayMonths><standardLoanCardUnpaymentAccountCnt>0</standardLoanCardUnpaymentAccountCnt><standardLoanCardUnpaymentTotalMonths>0</standardLoanCardUnpaymentTotalMonths><standardLoanCardUnpaymentMaxtUnpayMonths>0</standardLoanCardUnpaymentMaxtUnpayMonths><standardLoanCardUnpaymentLongestUnpayMonths>0</standardLoanCardUnpaymentLongestUnpayMonths></unpaymentOverdraftSummary><unsettledLoanSummary><loanFinanceCorpCount></loanFinanceCorpCount><loanFinanceOrgCount></loanFinanceOrgCount><loanCnt></loanCnt><conactTotalAmount></conactTotalAmount><balance></balance><recently6MonthAvgPay></recently6MonthAvgPay></unsettledLoanSummary><unCancelAccountLoancardSummary><financeCorpCount>4</financeCorpCount><financeOrgCount>4</financeOrgCount><acountCnt>5</acountCnt><creditLimitTotal>595,000</creditLimitTotal><singleBankMaxCreditLimitTotal>500,000</singleBankMaxCreditLimitTotal><singleBankMinCreditLimitTotal>0</singleBankMinCreditLimitTotal><usedTotal>908</usedTotal><recent6MonthAvgUsedTotal>1,502</recent6MonthAvgUsedTotal></unCancelAccountLoancardSummary><unCancelAccountStandardLoancardSummary><financeCorpCount></financeCorpCount><financeOrgCount></financeOrgCount><acountCnt></acountCnt><creditLimitTotal></creditLimitTotal><singleBankMaxCreditLimitTotal></singleBankMaxCreditLimitTotal><singleBankMinCreditLimitTotal></singleBankMinCreditLimitTotal><overdraft></overdraft><recent6MonthAvgUsedTotal></recent6MonthAvgUsedTotal></unCancelAccountStandardLoancardSummary><guaranteeSummary><guaranteeCnt></guaranteeCnt><guaranteeAmount></guaranteeAmount><guaranteeActualAmountt></guaranteeActualAmountt></guaranteeSummary><assetDisposes><assetDisposal></assetDisposal></assetDisposes><guarateesInsteadPays><guarateeInsteadPay></guarateeInsteadPay></guarateesInsteadPays><loans><loanRecord><description>3.2003年11月07日机构“FJ”发放的50,000元（人民币）其他贷款，业务号X，保证，一次性归还,2004年11月06日到期。已于2004年11月结清。</description></loanRecord><loanRecord><description>2.2003年04月01日机构“OM”发放的150,000元（人民币）其他贷款，业务号X，抵押担保，一次性归还,2004年04月01日到期。已于2004年04月结清。</description></loanRecord><loanRecord><description>1.2000年04月27日机构“GA”发放的330,000元（人民币）个人住房贷款，业务号X，抵押担保，180期，按月归还,2015年04月26日到期。已于2014年02月结清。</description></loanRecord><loanRecord><description>7.2008年05月28日机构“南京银行支行”发放的50,000元（人民币）其他贷款，业务号0136200810033701，信用/免担保，8期，按季归还,2010年05月28日到期。已于2009年07月结清。</description></loanRecord><loanRecord><description>6.2007年06月01日机构“南京银行支行”发放的250,000元（人民币）其他贷款，业务号0136200710065401，信用/免担保，4期，按季归还,2008年06月01日到期。已于2008年05月结清。</description></loanRecord><loanRecord><description>5.2007年05月25日机构“南京银行支行”发放的50,000元（人民币）其他贷款，业务号0136200710061101，信用/免担保，4期，按季归还,2008年05月25日到期。已于2008年05月结清。</description></loanRecord><loanRecord><description>4.2004年11月04日机构“FJ”发放的50,000元（人民币）其他贷款，业务号X，保证，一次性归还,2005年11月04日到期。已于2005年11月结清。</description></loanRecord><loanRecord><description>9.2009年07月24日机构“南京银行支行”发放的300,000元（人民币）其他贷款，业务号0136200910083501，信用/免担保，8期，按季归还,2011年07月23日到期。已于2011年07月结清。</description><dueRecords><dueRecord><unpaymentAmountTotal>3,590</unpaymentAmountTotal><mostPastDueFrequency>1</mostPastDueFrequency><pastDueMonth>2011.03</pastDueMonth></dueRecord><dueRecord><unpaymentAmountTotal>3,464</unpaymentAmountTotal><mostPastDueFrequency>1</mostPastDueFrequency><pastDueMonth>2010.09</pastDueMonth></dueRecord><dueRecord><unpaymentAmountTotal>0</unpaymentAmountTotal><mostPastDueFrequency>1</mostPastDueFrequency><pastDueMonth>2010.06</pastDueMonth></dueRecord></dueRecords><specialTrades><specialTrade><specialTradeType>其他</specialTradeType><occureddate>2013.02.21</occureddate><changingFrequency>0</changingFrequency><changingAmount>46,607</changingAmount><detailRecord>提前还款</detailRecord></specialTrade></specialTrades><financeOrg>发卡机构说明</financeOrg><getFinanceOrgTime>2011.06.01</getFinanceOrgTime><announceInfo>本人声明添加日期</announceInfo><getAnnounceInfoTime>2011.06.02</getAnnounceInfoTime><dissentInfo>异议标注</dissentInfo><getDissentInfoTime>2011.06.02</getDissentInfoTime></loanRecord><loanRecord><description>8.2008年05月30日机构“南京银行支行”发放的250,000元（人民币）其他贷款，业务号0136200810034201，信用/免担保，4期，按季归还,2009年05月30日到期。已于2009年05月结清。</description></loanRecord></loans><loancards><loancardRecord><lateActualPaymentDate>2014.05.07</lateActualPaymentDate><shareAmount>0</shareAmount><actualPaymentAmount>735</actualPaymentAmount><description>3.2009年04月08日机构“AY”发放的贷记卡(美元账户），业务号X，授信额度折合人民币44,683元，信用/免担保。截至2014年05月07日，</description><billDate>2014.05.07</billDate><pastDueAmount>0</pastDueAmount><usedTotal>0</usedTotal><maxUsedTotal>33,055</maxUsedTotal><recent6MonthAvgUsedTotal>0</recent6MonthAvgUsedTotal><paymentRecord>************************</paymentRecord><pastDueFrequency>0</pastDueFrequency><schedulePaymentAmount>0</schedulePaymentAmount></loancardRecord><loancardRecord><lateActualPaymentDate>2014.05.03</lateActualPaymentDate><shareAmount>45,000</shareAmount><billDate>2014.05.07</billDate><usedTotal>908</usedTotal><mostPastDueFrequency>1</mostPastDueFrequency><mostPastDueFrequency>--</mostPastDueFrequency><recent6MonthAvgUsedTotal>1,062</recent6MonthAvgUsedTotal><schedulePaymentAmount>91</schedulePaymentAmount><pastDueFrequency>0</pastDueFrequency><unpaymentAmountTotal>1,125</unpaymentAmountTotal><unpaymentAmountTotal>--</unpaymentAmountTotal><description>2.2009年04月08日机构“AY”发放的贷记卡(人民币账户），业务号X，授信额度45,000元，信用/免担保。截至2014年05月07日，</description><actualPaymentAmount>914</actualPaymentAmount><pastDueAmount>0</pastDueAmount><maxUsedTotal>17,920</maxUsedTotal><pastDueMonth>2012.04</pastDueMonth><pastDueMonth>--</pastDueMonth><paymentRecord>NN1N*N***NNN*NNNNN*NNNNN</paymentRecord></loancardRecord><loancardRecord><lateActualPaymentDate>2014.01.30</lateActualPaymentDate><shareAmount>50,000</shareAmount><billDate>2014.05.10</billDate><usedTotal>0</usedTotal><recent6MonthAvgUsedTotal>441</recent6MonthAvgUsedTotal><schedulePaymentAmount>0</schedulePaymentAmount><pastDueFrequency>0</pastDueFrequency><description>1.2007年10月24日机构“FJ”发放的贷记卡(人民币账户），业务号X，授信额度50,000元，信用/免担保。截至2014年05月10日，</description><actualPaymentAmount>0</actualPaymentAmount><pastDueAmount>0</pastDueAmount><maxUsedTotal>45,035</maxUsedTotal><dueRecords><dueRecord><unpaymentAmountTotal>191</unpaymentAmountTotal><mostPastDueFrequency>3</mostPastDueFrequency><pastDueMonth>2011.01</pastDueMonth></dueRecord><dueRecord><unpaymentAmountTotal>122</unpaymentAmountTotal><mostPastDueFrequency>2</mostPastDueFrequency><pastDueMonth>2010.12</pastDueMonth></dueRecord><dueRecord><unpaymentAmountTotal>54</unpaymentAmountTotal><mostPastDueFrequency>1</mostPastDueFrequency><pastDueMonth>2010.11</pastDueMonth></dueRecord></dueRecords><paymentRecord>NN*NNNNNNNNNNNNNNNN1N***</paymentRecord></loancardRecord><loancardRecord><description>6.2013年08月02日机构“KC”发放的贷记卡(人民币账户），业务号X，授信额度500,000元，信用/免担保，截止2014年05月12日尚未激活。</description></loancardRecord><loancardRecord><description>5.2009年10月10日机构“南京市商业银行”发放的贷记卡(人民币账户），业务号15662230304243009543，授信额度0元，信用/免担保，截止2014年04月20日尚未激活。</description></loancardRecord><loancardRecord><description>4.2003年09月28日机构“AQ”发放的贷记卡(人民币账户），业务号X，授信额度30,000元，信用/免担保，已于2009年11月销户。</description></loancardRecord></loancards><standardLoancards><standardLoancardRecord></standardLoancardRecord></standardLoancards><guarantees><guaranteeDetail></guaranteeDetail></guarantees><oweTaxs><oweTaxRecord></oweTaxRecord></oweTaxs><civilCourts><civilCourtRecord></civilCourtRecord></civilCourts><courtExecutes><courtExecution></courtExecution></courtExecutes><administrationPunishments><administrationPunishmentRecords></administrationPunishmentRecords></administrationPunishments><accFundRecord><areaName>江苏省南京市</areaName><openDate>1992.04.01</openDate><firstMonth>1992.04</firstMonth><toMonth>2013.01</toMonth><status>缴交</status><perMonthAmount>2,056</perMonthAmount><ownPercent>12%</ownPercent><coPercent>12%</coPercent><company>南京市监督局</company><occureDate>2013.01.01</occureDate></accFundRecord><socialInsuranceDeposit><insuranceOrgAreaName></insuranceOrgAreaName><openDate></openDate><monthDuration></monthDuration><workDate></workDate><accountStatus></accountStatus><basicMoney></basicMoney><actualMoney></actualMoney><occureDate></occureDate><enterpriseName></enterpriseName><pauseReason></pauseReason></socialInsuranceDeposit><socialInsuranceDeliver><insuranceOrgAreaName></insuranceOrgAreaName><retireType></retireType><retiredDate></retiredDate><workDate></workDate><actualMoney></actualMoney><pauseReason></pauseReason><enterpriseName></enterpriseName><occureDate></occureDate></socialInsuranceDeliver><salvates><salvation></salvation></salvates><competents><competence></competence></competents><awardAndPunishs><awardAndPunishment></awardAndPunishment></awardAndPunishs><vehicles><vehicleInfo></vehicleInfo></vehicles><telPays><telPayment></telPayment></telPays><announces><announceInfo></announceInfo></announces><dissents><dissentInfo></dissentInfo></dissents><queryRecordSummary><recentlyOneMonthFor02OrganCnt>1</recentlyOneMonthFor02OrganCnt><recentlyOneMonthFor03OrganCnt>0</recentlyOneMonthFor03OrganCnt><recentlyOneMonthFor02QueryNum>1</recentlyOneMonthFor02QueryNum><recentlyOneMonthFor03QueryNum>0</recentlyOneMonthFor03QueryNum><recentlyTwoYearFor01QueryNum>4</recentlyTwoYearFor01QueryNum><recentlyTwoYearFor08QueryNum>0</recentlyTwoYearFor08QueryNum><recentlyTwoYearFor17QueryNum>0</recentlyTwoYearFor17QueryNum></queryRecordSummary><creditAndLoanQueryRecords><creditAndLoanQueryRecordDetails><queryDate>2013.07.22</queryDate><operator>KC</operator><queryReason>信用卡审批</queryReason></creditAndLoanQueryRecordDetails><creditAndLoanQueryRecordDetails><queryDate>2013.07.26</queryDate><operator>FJ</operator><queryReason>信用卡审批</queryReason></creditAndLoanQueryRecordDetails><creditAndLoanQueryRecordDetails><queryDate>2014.06.09</queryDate><operator>KC</operator><queryReason>贷款审批</queryReason></creditAndLoanQueryRecordDetails></creditAndLoanQueryRecords></creditreport>";
		Map<String, Object> map=xmlToMap(data, null);
		System.out.println(map);
		System.out.println(mapToXML(map));
	}
	
	/**
	 * Xml 解析成  Map对象（当Xml存在List节点时，会将该节点转换成List保存）
	 * @author XuQZ
	 * @param xmlStr 需要解析的Xml字符串
	 * @param obj 无需传入的参数，一般为null
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> xmlToMap(String xmlStr,Object obj) throws Exception {
		 Document doc = DocumentHelper.parseText(xmlStr);   
		 Map<String, Object> oldMap = new HashMap<String, Object>();
		 if(doc == null){
			 return oldMap;
		 }
		 Element root = doc.getRootElement();
		 if(root.elements().size()==0){
			 oldMap.put(root.getName(),root.getText());
		 }else{
			 Map<String, Object> newMap=new HashMap<String, Object>();
			 recursiveElement(root,newMap);
			 //判断是否存在根节点，有就不做处理，没有则需要将更节点加上
			 if(!newMap.containsKey(root.getName())){
				 oldMap.put(root.getName(),newMap);
			 }else{
				 oldMap=newMap;
			 }
		 }
		return oldMap;
	}
	
	
	
	/**
	 * 递归解析xlm字符串
	 * @author XuQZ
	 * @param root 根节点
	 * @param oldMap 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static Map<String,Object>  recursiveElement(Element root,Map<String,Object> oldMap) {
		List<Element> rootEs=root.elements();
		
		if(rootEs.size()==0){//判断是否存在子节点
			oldMap.put(root.getName(), root.getText());
		}else if(rootEs.size()==1){//判断是否只存在一个子节点，需要注意的是将
			Map<String, Object> newMap=new HashMap<String, Object>();
			recursiveElement(rootEs.get(0),newMap);
			oldMap.put(root.getName(),newMap);
		}else{//存在多个子节点
			//得到所有不重复的子节点
			Map<String, Object> mapKey=new LinkedMap();
			for (Element childE : rootEs) {
				mapKey.put(childE.getName(), null);
			}
			Set<String> keySet = mapKey.keySet();
			//当前子节点对应的根Map
			Map<String, Object> newMap = new HashMap<String, Object>(); 
			//当前节点对应的Map
			Map<String, Object> reMap = new HashMap<String, Object>(); 
			
			for (String childEName : keySet) {
				Namespace namespace = rootEs.get(0).getNamespace();   
				List<Element> childEs = root.elements(new org.dom4j.QName(childEName,namespace)); 
				//当前子节点是否存在多个如果存在多个则需要循环递归该节点，并存放在List中
				if(childEs.size()>1){
				     List<Map> list = new ArrayList<Map>();   
				     for (Element element : childEs) {  
					  Map<String, Object> new2Map = new HashMap<String, Object>(); 

					  new2Map=recursiveElement(element,new2Map);   
					     list.add(new2Map);
				     } 
				     reMap.put(root.getName(), list); 
				}else{
					//如果不存在多个则递归当前当前子节点
					recursiveElement(childEs.get(0),newMap);
					reMap.put(childEs.get(0).getName(),newMap.get(childEs.get(0).getName())); 
				}
			}
			//判断当前节点是否在Map中存在，需做处理
			if(!oldMap.containsKey(root.getName())){
				if(reMap.containsKey(root.getName())){
					oldMap.put(root.getName(), reMap.get(root.getName()));
				}else{
					oldMap.put(root.getName(), reMap);
				}
			}
		}
		return oldMap;
	}
	
	
	/**
	 * 将Map 解析成Xml字符串
	 * @author XuQZ
	 * @param map 需要转换的Map对象
	 * @return 返回Xml字符串 不含 <?xml version=\"1.0\" encoding=\"UTF-8\"?>
	 */
	public static String mapToXml(Map map) {
		//System.out.println("将Map转成Xml, Map：" + map.toString());
		StringBuffer sb = new StringBuffer();
		//<bizdata>");
		mapToXML(map, sb);
		//sb.append("</bizdata>");
//		System.out.println("将Map转成Xml, Xml：" + sb.toString());
		return sb.toString();
	}
	
	
	
	/**
	 * 将Map 解析成Xml字符串
	 * @author XuQZ
	 * @param map 需要转换的Map对象
	 * @return 返回Xml字符串 含 <?xml version=\"1.0\" encoding=\"UTF-8\"?>
	 */
	public static String mapToXML(Map map) {
		//System.out.println("将Map转成Xml, Map：" + map.toString());
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		//<bizdata>");
		mapToXML(map, sb);
		//sb.append("</bizdata>");
//		System.out.println("将Map转成Xml, Xml：" + sb.toString());
		return sb.toString();
	}
	
	
	/**
	 * 递归解析 Map 对象
	 * @author XuQZ
	 * @param map 待解析的 Map 对象
	 * @param sb 字符对象
	 */
	private static void mapToXML(Map map, StringBuffer sb) {
		Set set = map.keySet();
		for (Iterator it = set.iterator(); it.hasNext();) {
			String key = (String) it.next();
			Object value = map.get(key);
			if (null == value)
				value = "";
			if (value.getClass().getName().equals("java.util.ArrayList")) {
				ArrayList list = (ArrayList) map.get(key);
				sb.append("<" + key + ">");
				for (int i = 0; i < list.size(); i++) {
					HashMap hm = (HashMap) list.get(i);
					mapToXML(hm, sb);
				}
				sb.append("</" + key + ">");

			} else {
				if (value instanceof HashMap) {
					sb.append("<" + key + ">");
					mapToXML((HashMap) value, sb);
					sb.append("</" + key + ">");
				} else {
					sb.append("<" + key + ">" + value + "</" + key + ">");
				}
			}
		}
	}
	
	
	/**
	 * 得到字符串中特定节点相应属性下的值
	 * @author liqs
	 * @param responseXML 待处理的字符串
     * @param encoString 处理字符串的编码,可为null 
	 * @param nodePath 节点路径
	 * @param nodeName 属性名称
	 * @param nodeValue 属性名称的值
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> testGetRoot(String responseXML,String encoString,String nodePath,String nodeName,String nodeValue) throws Exception{  
//		int home=nodePath.lastIndexOf("/");
//		String element=nodePath.substring(home+1, nodePath.length());
		Map<String, Object> oldMap=new HashMap<String, Object>();
		InputStream in = null; 
		
		if(StringUtil.isNotNull(encoString)){
			in = new  ByteArrayInputStream(responseXML.getBytes(encoString));
		}else {
			in = new  ByteArrayInputStream(responseXML.getBytes());
		}
		
	    SAXReader sax=new SAXReader();//创建一个SAXReader对象  
	    Document document=sax.read(in);//获取document对象,如果文档无节点，则会抛出Exception提前结束  
	 //   Element root=document.getRootElement();//获取根节点  

		 if(document == null){
			 return oldMap;
		 }
		 
	    List aNode = document.selectNodes(nodePath);
	    
		for(int x = 0;x<aNode.size();x++){
		Element t = (Element) aNode.get(x);
		if(nodeValue.equals(t.attributeValue(nodeName))){
			 
				 Map<String, Object> newMap=new HashMap<String, Object>();
				 oldMap= recursiveElement(t,newMap);
			 }
		}
		
		try {
			if(in!=null){
				in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return oldMap;
	  
	} 
	
	/**
	 * 解析xml字符串,得到相应节点的数据
	 * @author liqs
	 * @param responseXML 待处理的字符串
	 * @param nodePath xml路径节点
	 * @param encoString 处理字符串的编码,可为null 
	 * @return Map 结果
	 * @throws Exception
	 */
	public static Map<String, Object> testGetRoot(String responseXML,String encoString,String nodePath) throws Exception{  
		int home=nodePath.lastIndexOf("/");
		String element=nodePath.substring(home+1, nodePath.length());
		Map<String, Object> oldMap=new HashMap<String, Object>();
		InputStream in = null; 
		
		if(StringUtil.isNotNull(encoString)){
			in = new  ByteArrayInputStream(responseXML.getBytes(encoString));
		}else {
			in = new  ByteArrayInputStream(responseXML.getBytes());
		}
		
		
	    SAXReader sax=new SAXReader();//创建一个SAXReader对象  
	    Document document=sax.read(in);//获取document对象,如果文档无节点，则会抛出Exception提前结束  
	  //  Element root=document.getRootElement();//获取根节点  

		 if(document == null){
			 return oldMap;
		 }
		 
	    List aNode = document.selectNodes(nodePath);
	    
		for(int x = 0;x<aNode.size();x++){
		Element t = (Element) aNode.get(x);
		
		
		 if(t.elements().size()==0){
			 oldMap.put(t.getName(),t.getText());
		 }else{
			 Map<String, Object> newMap=new HashMap<String, Object>();
			// newMap=  recursiveElement(t,newMap);
			 newMap=(Map<String, Object>) xmlToMap(t);
			 //判断是否存在根节点，有就不做处理，没有则需要将根节点加上
			 if(!newMap.containsKey(t.getName())){
				 oldMap.put(t.getName(),newMap);
			 }else{
				 Object object =newMap.get(element);
				 if (object instanceof List	 ){
//					 List list = new ArrayList();
//					 list=(List) newMap.get(element);
//					 oldMap=(Map<String, Object>) list.get(0);
					 oldMap=newMap;
				 }else {
					 oldMap=(Map<String, Object>) newMap.get(element);

				}
				 
			 }
		 }
//		
//		 if(t.elements().size()==0){
//			 oldMap.put(t.getName(),t.getText());
//		 }else{
//			 Map<String, Object> newMap=new HashMap<String, Object>();
//			 newMap  = (Map<String, Object>) xmlToMap(t,newMap);
//		     oldMap.put(t.getName(),newMap);
//			 
//		 }
//		 
		 
	}
		
		try {
			if(in!=null){
				in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return oldMap;
	  
	} 
	
	

	
	/**
	 * xml转map 不带属性
	 * @param xmlStr
	 * @param needRootKey 是否需要在返回的map里加根节点键
	 * @return
	 * @throws DocumentException
	 */
	public static Map xmlToMap(String xmlStr, boolean needRootKey) throws DocumentException {
		Document doc = DocumentHelper.parseText(xmlStr);
		Element root = doc.getRootElement();
		Map<String, Object> map = (Map<String, Object>) xmlToMap(root);
		if(root.elements().size()==0 && root.attributes().size()==0){
			return map;
		}
		if(needRootKey){
			//在返回的map里加根节点键（如果需要）
			Map<String, Object> rootMap = new HashMap<String, Object>();
			rootMap.put(root.getName(), map);
			return rootMap;
		}
		return map;
	}

	/**
	 * xml转map 带属性
	 * @param xmlStr
	 * @param needRootKey 是否需要在返回的map里加根节点键
	 * @return
	 * @throws DocumentException
	 */
	public static Map xmlToMapWithAttr(String xmlStr, boolean needRootKey) throws DocumentException {
		Document doc = DocumentHelper.parseText(xmlStr);
		Element root = doc.getRootElement();
		Map<String, Object> map = (Map<String, Object>) xmlToMapWithAttr(root);
		if(root.elements().size()==0 && root.attributes().size()==0){
			return map; //根节点只有一个文本内容
		}
		if(needRootKey){
			//在返回的map里加根节点键（如果需要）
			Map<String, Object> rootMap = new HashMap<String, Object>();
			rootMap.put(root.getName(), map);
			return rootMap;
		}
		return map;
	}

	/**
	 * xml转map 不带属性
	 * @param element
	 * @return
	 */
	public static Object xmlToMap(Element element) {
		// System.out.println(element.getName());
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<Element> elements = element.elements();
		if (elements.size() == 0) {
			map.put(element.getName(), element.getText());
			if (!element.isRootElement()) {
				return element.getText();
			}
		} else if (elements.size() == 1) {
			map.put(elements.get(0).getName(), xmlToMap(elements.get(0)));
		} else if (elements.size() > 1) {
			// 多个子节点的话就得考虑list的情况了，比如多个子节点有节点名称相同的
			// 构造一个map用来去重
			Map<String, Element> tempMap = new LinkedHashMap<String, Element>();
			for (Element ele : elements) {
				tempMap.put(ele.getName(), ele);
			}
			Set<String> keySet = tempMap.keySet();
			for (String string : keySet) {
				Namespace namespace = tempMap.get(string).getNamespace();
				List<Element> elements2 = element.elements(new QName(string,
						namespace));
				// 如果同名的数目大于1则表示要构建list
				if (elements2.size() > 1) {
					List<Object> list = new ArrayList<Object>();
					for (Element ele : elements2) {
						list.add(xmlToMap(ele));
					}
					map.put(string, list);
				} else {
					// 同名的数量不大于1则直接递归去
					map.put(string, xmlToMap(elements2.get(0)));
				}
				
				
			}
		}

		return map;
	}

	/**
	 * xml转map 带属性
	 * @param element
	 * @return
	 */
	private static Object xmlToMapWithAttr(Element element) {
		// System.out.println(element.getName());
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<Element> elements = element.elements();

		List<Attribute> listAttr = element.attributes(); // 当前节点的所有属性的list
		boolean hasAttributes = false;
		for (Attribute attr : listAttr) {
			hasAttributes = true;
			map.put("@" + attr.getName(), attr.getValue());
		}

		if (elements.size() == 0) {
			// map.put(element.getName(), element.getText());
			if (hasAttributes) {
				map.put("#text", element.getText());
			} else {
				map.put(element.getName(), element.getText());
			}

			if (!element.isRootElement()) {
				// return element.getText();
				if (!hasAttributes) {
					return element.getText();
				}
			}
		} else if (elements.size() == 1) {
			map.put(elements.get(0).getName(),
					xmlToMapWithAttr(elements.get(0)));
		} else if (elements.size() > 1) {
			// 多个子节点的话就得考虑list的情况了，比如多个子节点有节点名称相同的
			// 构造一个map用来去重
			Map<String, Element> tempMap = new LinkedHashMap<String, Element>();
			for (Element ele : elements) {
				tempMap.put(ele.getName(), ele);
			}
			Set<String> keySet = tempMap.keySet();
			for (String string : keySet) {
				Namespace namespace = tempMap.get(string).getNamespace();
				List<Element> elements2 = element.elements(new QName(string,
						namespace));
				// 如果同名的数目大于1则表示要构建list
				if (elements2.size() > 1) {
					List<Object> list = new ArrayList<Object>();
					for (Element ele : elements2) {
						list.add(xmlToMapWithAttr(ele));
					}
					map.put(string, list);
				} else {
					// 同名的数量不大于1则直接递归去
					map.put(string, xmlToMapWithAttr(elements2.get(0)));
				}
			}
		}

		return map;
	}

	
	/**
	 * xml转map 不带属性
	 * @param element
	 * @return
	 */
	public static Object xmlToMap(Element element,Map<String , Object> oldMap) {
		// System.out.println(element.getName());
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<Element> elements = element.elements();
		if (elements.size() == 0) {
			map.put(element.getName(), element.getText());
			if (!element.isRootElement()) {
				return element.getText();
			}
		} else if (elements.size() == 1) {
			map.put(elements.get(0).getName(), xmlToMap(elements.get(0)));
		} else if (elements.size() > 1) {
			// 多个子节点的话就得考虑list的情况了，比如多个子节点有节点名称相同的
			// 构造一个map用来去重
			Map<String, Element> tempMap = new LinkedHashMap<String, Element>();
			for (Element ele : elements) {
				tempMap.put(ele.getName(), ele);
			}
			Set<String> keySet = tempMap.keySet();
			for (String string : keySet) {
				Namespace namespace = tempMap.get(string).getNamespace();
				List<Element> elements2 = element.elements(new QName(string,
						namespace));
				// 如果同名的数目大于1则表示要构建list
				if (elements2.size() > 1) {
					List<Object> list = new ArrayList<Object>();
					for (Element ele : elements2) {
						list.add(xmlToMap(ele,oldMap));
					}
					map.put(string, list);
				} else {
					// 同名的数量不大于1则直接递归去
					map.put(string, xmlToMap(elements2.get(0)));
				}
				
				
			}
		}

		return map;
	}
	
}
