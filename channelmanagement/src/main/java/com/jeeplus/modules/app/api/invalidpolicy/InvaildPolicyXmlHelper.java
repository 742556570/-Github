package com.jeeplus.modules.app.api.invalidpolicy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.http.util.TextUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.jeeplus.modules.app.api.invalidpolicy.request.ReqInvaildPolicyVO;
import com.jeeplus.modules.app.api.invalidpolicy.request.ReqItemHouseDetailVO;
import com.jeeplus.modules.app.api.invalidpolicy.request.ReqLinkPersonDetailVO;
import com.jeeplus.modules.app.api.loantrial.response.RespLoanTrialVO;


/**
 * 阳光保险 
 * 贷款申请试算转化成xml
 */

public class InvaildPolicyXmlHelper {
	/**
	 * 贷款设备区部分试算
	 * 
	 * @return xml字符串
	 * @throws TransformerConfigurationException
	 * @throws ParserConfigurationException
	 */
	public String createAddXml(ReqInvaildPolicyVO reqInvaildPolicyVO) {

		
		String xmlstr = "";
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return "";
		}
		Document document = builder.newDocument();

		document.setXmlVersion("1.0");
		// <IN>
		Element root = document.createElement("TransPolicyRequest");
		document.appendChild(root);
		document.setXmlStandalone(true);

		Element modeFlag = document.createElement("modeFlag");
		modeFlag.setTextContent(DefaultInvaildPolicyParam.modeFlag);

		Element operateType = document.createElement("operateType");
		operateType.setTextContent(DefaultInvaildPolicyParam.operateType);
		
		Element policyCount = document.createElement("policyCount");
		policyCount.setTextContent(DefaultInvaildPolicyParam.policyCount);
		
		Element sendName = document.createElement("sendName");
		sendName.setTextContent(DefaultInvaildPolicyParam.sendName);
		
		Element sendPwd = document.createElement("sendPwd");
		sendPwd.setTextContent(DefaultInvaildPolicyParam.sendPwd);
		
		Element sendSeq = document.createElement("sendSeq");
		sendSeq.setTextContent(reqInvaildPolicyVO.getSendSeq());
		
		Element sendTime = document.createElement("sendTime");
		sendTime.setTextContent(reqInvaildPolicyVO.getSendTime());
		
		Element sysFlag = document.createElement("sysFlag");
		sysFlag.setTextContent(DefaultInvaildPolicyParam.sysFlag);
		
		Element businessNature = document.createElement("businessNature");
		businessNature.setTextContent(reqInvaildPolicyVO.getBusinessNature());
		
		Element channelType = document.createElement("channelType");
		channelType.setTextContent(reqInvaildPolicyVO.getChannelType());
		
		Element shortRate = document.createElement("shortRate");
		shortRate.setTextContent(reqInvaildPolicyVO.getShortRate());
		
		Element insuredIdentifyNumber = document.createElement("insuredIdentifyNumber");
		insuredIdentifyNumber.setTextContent(reqInvaildPolicyVO.getInsuredIdentifyNumber());
		
		Element insuredPhoneNumber = document.createElement("insuredPhoneNumber");
		insuredPhoneNumber.setTextContent(DefaultInvaildPolicyParam.insuredPhoneNumber);
		
		Element endDate = document.createElement("endDate");
		endDate.setTextContent(DefaultInvaildPolicyParam.endDate);
		
		Element appliName = document.createElement("appliName");
		appliName.setTextContent(reqInvaildPolicyVO.getAppliName());
		
		Element amount = document.createElement("amount");
		amount.setTextContent(reqInvaildPolicyVO.getAmount());
		
		Element signDate = document.createElement("signDate");
		signDate.setTextContent(reqInvaildPolicyVO.getSignDate());
		
		Element appliFlag = document.createElement("appliFlag");
		appliFlag.setTextContent(DefaultInvaildPolicyParam.appliFlag);
		
		Element handler1Code = document.createElement("handler1Code");
		handler1Code.setTextContent(reqInvaildPolicyVO.getHandler1Code());
		
		Element linkPersonGroup = document.createElement("linkPersonGroup");
		for (ReqLinkPersonDetailVO reqLinkPersonDetailVO : reqInvaildPolicyVO.getLinkPersonDetail()) {
			Element linkPersonDetail = document.createElement("linkPersonDetail");
			linkPersonGroup.appendChild(linkPersonDetail);

			Element linkPhone = document.createElement("linkPhone");
			linkPhone.setTextContent(reqLinkPersonDetailVO.getLinkPhone());
			
			Element linkShip = document.createElement("linkShip");
			linkShip.setTextContent(reqLinkPersonDetailVO.getLinkShip());
			
			Element linkMobile = document.createElement("linkMobile");
			linkMobile.setTextContent(reqLinkPersonDetailVO.getLinkMobile());
			
			Element linkAdress = document.createElement("linkAdress");
			linkAdress.setTextContent(reqLinkPersonDetailVO.getLinkAdress());
			
			Element insuredFlag = document.createElement("insuredFlag");
			insuredFlag.setTextContent(reqLinkPersonDetailVO.getInsuredFlag());
			
			Element linkName = document.createElement("linkName");
			linkName.setTextContent(reqLinkPersonDetailVO.getLinkName());
			

			linkPersonDetail.appendChild(linkPhone);
			linkPersonDetail.appendChild(linkShip);
			linkPersonDetail.appendChild(linkMobile);
			linkPersonDetail.appendChild(linkAdress);
			linkPersonDetail.appendChild(insuredFlag);
			linkPersonDetail.appendChild(linkName);

		}
		Element loanEndDate = document.createElement("loanEndDate");
		loanEndDate.setTextContent(reqInvaildPolicyVO.getLoanEndDate());
		
		Element insureagrt = document.createElement("insureagrt");
		insureagrt.setTextContent(DefaultInvaildPolicyParam.insureagrt);
		
		Element loanStartDate = document.createElement("loanStartDate");
		loanStartDate.setTextContent(DefaultInvaildPolicyParam.loanStartDate);
		
		Element agreementNo = document.createElement("agreementNo");
		agreementNo.setTextContent(reqInvaildPolicyVO.getAgreementNo());
		
		Element addressName = document.createElement("addressName");
		addressName.setTextContent(reqInvaildPolicyVO.getAddressName());
		
		Element argueSolution = document.createElement("argueSolution");
		argueSolution.setTextContent(DefaultInvaildPolicyParam.argueSolution);
		
		Element appliPhoneNumber = document.createElement("appliPhoneNumber");
		appliPhoneNumber.setTextContent(reqInvaildPolicyVO.getAppliPhoneNumber());
		
		Element structure = document.createElement("structure");
		structure.setTextContent(DefaultInvaildPolicyParam.structure);
		
		Element inputDate = document.createElement("inputDate");
		inputDate.setTextContent(reqInvaildPolicyVO.getInputDate());
		
		Element pledgeName = document.createElement("pledgeName");
		pledgeName.setTextContent(reqInvaildPolicyVO.getPledgeName());
		
		Element riskCode = document.createElement("riskCode");
		riskCode.setTextContent(reqInvaildPolicyVO.getRiskCode());
		
		Element appliNature = document.createElement("appliNature");
		appliNature.setTextContent(DefaultInvaildPolicyParam.appliNature);
		
		Element loanNature = document.createElement("loanNature");
		loanNature.setTextContent(DefaultInvaildPolicyParam.loanNature);
		
		Element insuredAddress = document.createElement("insuredAddress");
		insuredAddress.setTextContent(DefaultInvaildPolicyParam.insuredAddress);
		
		Element insuredNature = document.createElement("insuredNature");
		insuredNature.setTextContent(DefaultInvaildPolicyParam.insuredNature);
		
		Element agentCode = document.createElement("agentCode");
		agentCode.setTextContent(reqInvaildPolicyVO.getAgentCode());
		
		Element appliMobile = document.createElement("appliMobile");
		appliMobile.setTextContent(reqInvaildPolicyVO.getAppliMobile());
		
		Element isDisability = document.createElement("isDisability");
		isDisability.setTextContent(DefaultInvaildPolicyParam.isDisability);
		
		Element perRepaidAmount = document.createElement("perRepaidAmount");
		perRepaidAmount.setTextContent(DefaultInvaildPolicyParam.perRepaidAmount);
		
		Element appliIdentifyType = document.createElement("appliIdentifyType");
		appliIdentifyType.setTextContent(DefaultInvaildPolicyParam.appliIdentifyType);
		
		Element houseVariety = document.createElement("houseVariety");
		houseVariety.setTextContent(DefaultInvaildPolicyParam.houseVariety);
		
		Element addressCode = document.createElement("addressCode");
		addressCode.setTextContent(DefaultInvaildPolicyParam.addressCode);
		
		Element insuredFlag = document.createElement("insuredFlag");
		insuredFlag.setTextContent(DefaultInvaildPolicyParam.insuredFlag);
		
		Element insuredType = document.createElement("insuredType");
		insuredType.setTextContent(DefaultInvaildPolicyParam.insuredType);
		
		Element disRate = document.createElement("disRate");
		disRate.setTextContent(reqInvaildPolicyVO.getDisRate());
		
		Element handlerCode = document.createElement("handlerCode");
		handlerCode.setTextContent(reqInvaildPolicyVO.getHandlerCode());
		
		Element mortgageSate = document.createElement("mortgageSate");
		mortgageSate.setTextContent(DefaultInvaildPolicyParam.mortgageSate);
		
		Element startDate = document.createElement("startDate");
		startDate.setTextContent(DefaultInvaildPolicyParam.startDate);
		
		Element loanAmount = document.createElement("loanAmount");
		loanAmount.setTextContent(reqInvaildPolicyVO.getLoanAmount());
		
		Element loanUsage = document.createElement("loanUsage");
		loanUsage.setTextContent(reqInvaildPolicyVO.getLoanUsage());
		
		Element appliAddress = document.createElement("appliAddress");
		appliAddress.setTextContent(reqInvaildPolicyVO.getAppliAddress());
		
		Element repaidType = document.createElement("repaidType");
		repaidType.setTextContent(DefaultInvaildPolicyParam.repaidType);
		
		Element deliverDate = document.createElement("deliverDate");
		deliverDate.setTextContent(DefaultInvaildPolicyParam.deliverDate);
		
		Element insuredName = document.createElement("insuredName");
		insuredName.setTextContent(reqInvaildPolicyVO.getInsuredName());
		
		Element appliType = document.createElement("appliType");
		appliType.setTextContent(DefaultInvaildPolicyParam.appliType);
		
		Element education = document.createElement("education");
		education.setTextContent(DefaultInvaildPolicyParam.education);
		
		Element payTimes = document.createElement("payTimes");
		payTimes.setTextContent(reqInvaildPolicyVO.getPayTimes());
		
		Element appliPostCode = document.createElement("appliPostCode");
		appliPostCode.setTextContent(DefaultInvaildPolicyParam.appliPostCode);
		
		Element comCode = document.createElement("comCode");
		comCode.setTextContent(reqInvaildPolicyVO.getComCode());
		
		Element kindCode = document.createElement("kindCode");
		kindCode.setTextContent(DefaultInvaildPolicyParam.kindCode);
		
		Element kindName = document.createElement("kindName");
		kindName.setTextContent(DefaultInvaildPolicyParam.kindName);
		
		Element rate = document.createElement("rate");
		rate.setTextContent(reqInvaildPolicyVO.getRate());
		
		Element repaidStartDate = document.createElement("repaidStartDate");
		repaidStartDate.setTextContent(DefaultInvaildPolicyParam.repaidStartDate);
		
		Element appliIdentifyNumber = document.createElement("appliIdentifyNumber");
		appliIdentifyNumber.setTextContent(reqInvaildPolicyVO.getAppliIdentifyNumber());
		
		Element policyNo = document.createElement("policyNo");
		policyNo.setTextContent(reqInvaildPolicyVO.getPolicyNo());
		
		Element sumPremium = document.createElement("sumPremium");
		sumPremium.setTextContent(reqInvaildPolicyVO.getSumPremium());
		
		Element sumAmount = document.createElement("sumAmount");
		sumAmount.setTextContent(reqInvaildPolicyVO.getSumAmount());
		
		Element appliLocalYear = document.createElement("appliLocalYear");
		appliLocalYear.setTextContent(DefaultInvaildPolicyParam.appliLocalYear);
		
		Element disabilityPart = document.createElement("disabilityPart");
		disabilityPart.setTextContent(reqInvaildPolicyVO.getDisabilityPart());
		
		Element operatorCode = document.createElement("operatorCode");
		operatorCode.setTextContent(reqInvaildPolicyVO.getOperatorCode());
		
		Element operatesite = document.createElement("operatesite");
		operatesite.setTextContent(reqInvaildPolicyVO.getOperatesite());
		
		Element makeCom = document.createElement("makeCom");
		makeCom.setTextContent(reqInvaildPolicyVO.getMakeCom());
		
		Element insuredMobile = document.createElement("insuredMobile");
		insuredMobile.setTextContent(DefaultInvaildPolicyParam.insuredMobile);
		
		Element marriage = document.createElement("marriage");
		marriage.setTextContent(DefaultInvaildPolicyParam.marriage);
		
		Element applyNum = document.createElement("applyNum");
		applyNum.setTextContent(DefaultInvaildPolicyParam.applyNum);
		
		Element itemHouseGroup = document.createElement("itemHouseGroup");
		for (ReqItemHouseDetailVO reqItemHouseDetailVO : reqInvaildPolicyVO.getItemHouseDetail()) {
			Element itemHouseDetail = document.createElement("itemHouseDetail");
			itemHouseGroup.appendChild(itemHouseDetail);

			Element structure1 = document.createElement("structure");
			structure1.setTextContent(reqItemHouseDetailVO.getStructure());
			
			Element houseVariety1 = document.createElement("houseVariety");
			houseVariety1.setTextContent(reqItemHouseDetailVO.getHouseVariety());
			
			Element buildArea = document.createElement("buildArea");
			buildArea.setTextContent(reqItemHouseDetailVO.getBuildArea());
			
			Element buildTime = document.createElement("buildTime");
			buildTime.setTextContent(reqItemHouseDetailVO.getBuildTime());
			
			Element buildPost = document.createElement("buildPost");
			buildPost.setTextContent(reqItemHouseDetailVO.getBuildPost());
			
			Element buildAdress = document.createElement("buildAdress");
			buildAdress.setTextContent(reqItemHouseDetailVO.getBuildAdress());
			
			Element unitValue = document.createElement("unitValue");
			buildArea.setTextContent(reqItemHouseDetailVO.getUnitValue());

			itemHouseDetail.appendChild(structure1);
			itemHouseDetail.appendChild(houseVariety1);
			itemHouseDetail.appendChild(buildArea);
			itemHouseDetail.appendChild(buildTime);
			itemHouseDetail.appendChild(buildPost);
			itemHouseDetail.appendChild(buildAdress);
			itemHouseDetail.appendChild(unitValue);

		}
		Element limitedFee = document.createElement("limitedFee");
		limitedFee.setTextContent(DefaultInvaildPolicyParam.limitedFee);
		
		Element mortgageNo = document.createElement("mortgageNo");
		mortgageNo.setTextContent(reqInvaildPolicyVO.getMortgageNo());
		
		Element insuredPostCode = document.createElement("insuredPostCode");
		insuredPostCode.setTextContent(DefaultInvaildPolicyParam.insuredPostCode);
		
		Element addressNo = document.createElement("addressNo");
		addressNo.setTextContent(DefaultInvaildPolicyParam.addressNo);
		
		Element insuredIdentifyType = document.createElement("insuredIdentifyType");
		insuredIdentifyType.setTextContent(DefaultInvaildPolicyParam.insuredIdentifyType);
		
		Element perRepaidDate = document.createElement("perRepaidDate");
		perRepaidDate.setTextContent(DefaultInvaildPolicyParam.perRepaidDate);
		
		Element policyGroup = document.createElement("policyGroup");
		Element policy = document.createElement("policy");
	
		root.appendChild(modeFlag);
		root.appendChild(operateType);
		root.appendChild(policyCount);
		root.appendChild(sendName);
		root.appendChild(sendPwd);
		root.appendChild(sendSeq);
		root.appendChild(sendTime);
		root.appendChild(sysFlag);
		policyGroup.appendChild(policy);
		policy.appendChild(businessNature);
		policy.appendChild(channelType);
		policy.appendChild(shortRate);
		policy.appendChild(insuredIdentifyNumber);
		policy.appendChild(insuredPhoneNumber);
		policy.appendChild(endDate);
		policy.appendChild(appliName);
		policy.appendChild(amount);
		policy.appendChild(signDate);
		policy.appendChild(appliFlag);
		policy.appendChild(handler1Code);
		policy.appendChild(linkPersonGroup);
		policy.appendChild(loanEndDate);
		policy.appendChild(insureagrt);
		policy.appendChild(loanStartDate);
		policy.appendChild(agreementNo);
		policy.appendChild(addressName);
		policy.appendChild(argueSolution);
		policy.appendChild(appliPhoneNumber);
		policy.appendChild(structure);
		policy.appendChild(inputDate);
		policy.appendChild(pledgeName);
		policy.appendChild(riskCode);
		policy.appendChild(appliNature);
		policy.appendChild(loanNature);
		policy.appendChild(insuredAddress);
		policy.appendChild(insuredNature);
		policy.appendChild(agentCode);
		policy.appendChild(appliMobile);
		policy.appendChild(isDisability);
		policy.appendChild(perRepaidAmount);
		policy.appendChild(appliIdentifyType);
		policy.appendChild(houseVariety);
		policy.appendChild(addressCode);
		policy.appendChild(insuredFlag);
		policy.appendChild(insuredType);
		policy.appendChild(disRate);
		policy.appendChild(handlerCode);
		policy.appendChild(mortgageSate);
		policy.appendChild(startDate);
		policy.appendChild(loanAmount);
		policy.appendChild(loanUsage);
		policy.appendChild(appliAddress);
		policy.appendChild(repaidType);
		policy.appendChild(deliverDate);
		policy.appendChild(insuredName);
		policy.appendChild(appliType);
		policy.appendChild(education);
		policy.appendChild(payTimes);
		policy.appendChild(appliPostCode);
		policy.appendChild(comCode);
		policy.appendChild(kindCode);
		policy.appendChild(kindName);
		policy.appendChild(rate);
		policy.appendChild(repaidStartDate);
		policy.appendChild(appliIdentifyNumber);
		policy.appendChild(policyNo);
		policy.appendChild(sumPremium);
		policy.appendChild(sumAmount);
		policy.appendChild(appliLocalYear);
		policy.appendChild(disabilityPart);
		policy.appendChild(operatorCode);
		policy.appendChild(operatesite);
		policy.appendChild(makeCom);
		policy.appendChild(insuredMobile);
		policy.appendChild(marriage);
		policy.appendChild(applyNum);
		policy.appendChild(itemHouseGroup);
		policy.appendChild(limitedFee);
		policy.appendChild(mortgageNo);
		policy.appendChild(insuredPostCode);
		policy.appendChild(addressNo);
		policy.appendChild(insuredIdentifyType);
		policy.appendChild(perRepaidDate);
		root.appendChild(policyGroup);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			// 设置输出数据时换行
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			// export str
			transformer.transform(domSource, new StreamResult(bos));

			xmlstr = bos.toString();
		} catch (TransformerException e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return xmlstr;
	}

	/**
	 * 贷款试算接口返回处理成bean
	 * @param xmlStr
	 *            xml 字符串
	 * @return 序列化对象
	 */
	public RespLoanTrialVO xml2BeanFromAdd(String xmlStr) {
		if (TextUtils.isEmpty(xmlStr)) {
			return null;
		}
		RespLoanTrialVO respLoanTrialVO =null;

		List<RespLoanTrialVO> payFeeList = new ArrayList<RespLoanTrialVO>();
		List<RespLoanTrialVO> PayShdTryListAll = new ArrayList<RespLoanTrialVO>();
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			StringReader sr = new StringReader(xmlStr);
			InputSource inputSource = new InputSource(sr);
			Document document = documentBuilder.parse(inputSource);
			Element rootElement = document.getDocumentElement();
			// <errorCode>00000</errorCode>
			String errorCode = rootElement.getElementsByTagName("errorCode").item(0).getFirstChild().getNodeValue();
			// <errorMsg>success</errorMsg>
			//String errorMsg = rootElement.getElementsByTagName("errorMsg").item(0).getFirstChild().getNodeValue();
			String TOTAL_NORM_INT = rootElement.getElementsByTagName("TOTAL_NORM_INT").item(0).getFirstChild().getNodeValue();
			String TOTAL_AMT = rootElement.getElementsByTagName("TOTAL_AMT").item(0).getFirstChild().getNodeValue();
			String TOTAL_FIRST_FEE = rootElement.getElementsByTagName("TOTAL_FIRST_FEE").item(0).getFirstChild().getNodeValue();
			String TOTAL_FEE_AMT = rootElement.getElementsByTagName("TOTAL_FEE_AMT").item(0).getFirstChild().getNodeValue();
			String TOTAL_SER_FEE = rootElement.getElementsByTagName("TOTAL_SER_FEE").item(0).getFirstChild().getNodeValue();
			//String PayFeeList = rootElement.getElementsByTagName("PayFeeList").item(0).getNodeValue();
			//String HOLD_FEE_SETL_DT = rootElement.getElementsByTagName("HOLD_FEE_SETL_DT").item(0).getFirstChild().getNodeValue();
			NodeList childNodes = rootElement.getChildNodes();  //根节点下所有子节点

			 for (int i = 0; i < childNodes.getLength(); i++) {  //循环第一层子节点
                Node childNode = childNodes.item(i);  
                if(childNode.getNodeName()=="PayFeeList") {
                  NodeList childNodes_2 = childNode.getChildNodes();  
                  for(int j = 0;j < childNodes_2.getLength();j++){   //循环第二层子节点
                	  RespLoanTrialVO respLoanTrialVO_1 = new RespLoanTrialVO();
                      String HOLD_FEE_SETL_DT = rootElement.getElementsByTagName("HOLD_FEE_SETL_DT").item(j).getFirstChild().getNodeValue();
                      respLoanTrialVO_1.setHOLD_FEE_SETL_DT(HOLD_FEE_SETL_DT);
                      String HOLD_FEE_IND = rootElement.getElementsByTagName("HOLD_FEE_IND").item(j).getFirstChild().getNodeValue();
                      respLoanTrialVO_1.setHOLD_FEE_IND(HOLD_FEE_IND);
                      String FEE_TYP = rootElement.getElementsByTagName("FEE_TYP").item(j).getFirstChild().getNodeValue();
                      respLoanTrialVO_1.setFEE_TYP(FEE_TYP);
                      String FEE_AMT = rootElement.getElementsByTagName("FEE_AMT").item(j).getFirstChild().getNodeValue();
                      respLoanTrialVO_1.setFEE_AMT(FEE_AMT);
                      String FEE_CDE = rootElement.getElementsByTagName("FEE_CDE").item(j).getFirstChild().getNodeValue();
                      respLoanTrialVO_1.setFEE_CDE(FEE_CDE);
                      //String PS_FEE_AMT_FIRST = rootElement.getElementsByTagName("PS_FEE_AMT_FIRST").item(j).getFirstChild().getNodeValue();
                      respLoanTrialVO_1.setPS_FEE_AMT_FIRST(null);
                      //String PS_FEE_AMT_SER = rootElement.getElementsByTagName("PS_FEE_AMT_SER").item(j).getFirstChild().getNodeValue();
                      respLoanTrialVO_1.setPS_FEE_AMT_SER(null);
                      payFeeList.add(respLoanTrialVO_1);
                  	}
                  }
                 
                if(childNode.getNodeName()=="PayShdTryListAll") {
                    NodeList childNodes_2 = childNode.getChildNodes();  
                    for(int j = 0;j < childNodes_2.getLength();j++){   //循环第二层子节点
                		RespLoanTrialVO respLoanTrialVO_2 = new RespLoanTrialVO();
                        String PERD_NO = rootElement.getElementsByTagName("PERD_NO").item(j).getFirstChild().getNodeValue();
                        respLoanTrialVO_2.setPERD_NO(PERD_NO);
                        String PRCP_AMT = rootElement.getElementsByTagName("PRCP_AMT").item(j).getFirstChild().getNodeValue();
                        respLoanTrialVO_2.setPRCP_AMT(PRCP_AMT);
                        String INSTM_AMT = rootElement.getElementsByTagName("INSTM_AMT").item(j).getFirstChild().getNodeValue();
                        respLoanTrialVO_2.setINSTM_AMT(INSTM_AMT);
                        String NORM_INT = rootElement.getElementsByTagName("NORM_INT").item(j).getFirstChild().getNodeValue();
                        respLoanTrialVO_2.setNORM_INT(NORM_INT);
                        String INT_RATE = rootElement.getElementsByTagName("INT_RATE").item(j).getFirstChild().getNodeValue();
                        respLoanTrialVO_2.setINT_RATE(INT_RATE);
                        String OD_INT_RATE = rootElement.getElementsByTagName("OD_INT_RATE").item(j).getFirstChild().getNodeValue();
                        respLoanTrialVO_2.setOD_INT_RATE(OD_INT_RATE);
                        String DUE_DT = rootElement.getElementsByTagName("DUE_DT").item(j).getFirstChild().getNodeValue();
                        respLoanTrialVO_2.setDUE_DT(DUE_DT);
                        String PS_REM_PRCP = rootElement.getElementsByTagName("PS_REM_PRCP").item(j).getFirstChild().getNodeValue();
                        respLoanTrialVO_2.setPS_REM_PRCP(PS_REM_PRCP);
                       // String PS_FEE_AMT = rootElement.getElementsByTagName("PS_FEE_AMT").item(j).getFirstChild().getNodeValue();
                       // respLoanTrialVO_2.setPS_FEE_AMT(PS_FEE_AMT);
                       // String FREE_INTEREST = rootElement.getElementsByTagName("FREE_INTEREST").item(j).getFirstChild().getNodeValue();
                       // respLoanTrialVO_2.setFREE_INTEREST(FREE_INTEREST);
                       // String PS_FEE_AMT_FIRST = rootElement.getElementsByTagName("PS_FEE_AMT_FIRST").item(j).getFirstChild().getNodeValue();
                       // respLoanTrialVO_2.setPS_FEE_AMT_FIRST(PS_FEE_AMT_FIRST);
                        //String PS_FEE_AMT_SER = rootElement.getElementsByTagName("PS_FEE_AMT_SER").item(j).getFirstChild().getNodeValue();
                        respLoanTrialVO_2.setPS_FEE_AMT_SER(null);
                        PayShdTryListAll.add(respLoanTrialVO_2);
                    }
                  }
           }
			respLoanTrialVO = new RespLoanTrialVO();
			respLoanTrialVO.setErrorCode(errorCode);
			respLoanTrialVO.setTOTAL_NORM_INT(TOTAL_NORM_INT);
			respLoanTrialVO.setTOTAL_AMT(TOTAL_AMT);
			respLoanTrialVO.setTOTAL_FIRST_FEE(TOTAL_FIRST_FEE);
			respLoanTrialVO.setTOTAL_FEE_AMT(TOTAL_FEE_AMT);
			respLoanTrialVO.setTOTAL_SER_FEE(TOTAL_SER_FEE);
			respLoanTrialVO.setPayFeeList(payFeeList);
			respLoanTrialVO.setPayShdTryListAll(PayShdTryListAll);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respLoanTrialVO;
	}

	public static void main(String[] args) {
		InvaildPolicyXmlHelper LoanBankXmlHelper = new InvaildPolicyXmlHelper();
		List<ReqItemHouseDetailVO> itemHouseDetail = new ArrayList<ReqItemHouseDetailVO>();
		List<ReqLinkPersonDetailVO> linkPersonDetail = new ArrayList<ReqLinkPersonDetailVO>();
		ReqInvaildPolicyVO ReqInvaildPolicyVO = new ReqInvaildPolicyVO();
		ReqItemHouseDetailVO ReqItemHouseDetailVO = new ReqItemHouseDetailVO();
		ReqLinkPersonDetailVO ReqLinkPersonDetailVO = new ReqLinkPersonDetailVO();
		linkPersonDetail.add(ReqLinkPersonDetailVO);
		itemHouseDetail.add(ReqItemHouseDetailVO);
		ReqInvaildPolicyVO.setLinkPersonDetail(linkPersonDetail);
		ReqInvaildPolicyVO.setItemHouseDetail(itemHouseDetail);
		String  xmlstr = LoanBankXmlHelper.createAddXml(ReqInvaildPolicyVO);
		System.out.println(xmlstr);
	}
}
