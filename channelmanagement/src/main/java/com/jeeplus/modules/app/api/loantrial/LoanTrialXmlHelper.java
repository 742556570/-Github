package com.jeeplus.modules.app.api.loantrial;

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

import com.jeeplus.modules.app.api.loantrial.request.ReqLoanTrialVO;
import com.jeeplus.modules.app.api.loantrial.response.RespLoanTrialVO;


/**
 * 阳光保险 
 * 贷款申请试算转化成xml
 */

public class LoanTrialXmlHelper {
	/**
	 * 贷款设备区部分试算
	 * 
	 * @return xml字符串
	 * @throws TransformerConfigurationException
	 * @throws ParserConfigurationException
	 */
	public String createAddXml(ReqLoanTrialVO reqLoanTrialVO) {

		
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
		Element root = document.createElement("msgbody");
		document.appendChild(root);
		document.setXmlStandalone(true);
		// <serviceId>servP0000100012</serviceId>
		Element serviceId = document.createElement("serviceId");
		// 交易吗
		serviceId.setTextContent(reqLoanTrialVO.getServiceId());
		// <serviceId>servP0000100012</serviceId>
		Element ORIG_PRCP = document.createElement("ORIG_PRCP");
		ORIG_PRCP.setTextContent(reqLoanTrialVO.getORIG_PRCP());
		
		Element DUE_DAY = document.createElement("DUE_DAY");
		DUE_DAY.setTextContent(reqLoanTrialVO.getDUE_DAY());
		
		Element LOAN_ACTV_DT = document.createElement("LOAN_ACTV_DT");
		LOAN_ACTV_DT.setTextContent(reqLoanTrialVO.getLOAN_ACTV_DT());
		
		Element LOAN_TYP = document.createElement("LOAN_TYP");
		LOAN_TYP.setTextContent(reqLoanTrialVO.getLOAN_TYP());
		
		Element INT_START_DT = document.createElement("INT_START_DT");
		INT_START_DT.setTextContent(reqLoanTrialVO.getINT_START_DT());
		
		Element LOAN_INT_RATE = document.createElement("LOAN_INT_RATE");
		LOAN_INT_RATE.setTextContent(reqLoanTrialVO.getLOAN_INT_RATE());
		
		Element PAYM_FREQ_UNIT = document.createElement("PAYM_FREQ_UNIT");
		PAYM_FREQ_UNIT.setTextContent(reqLoanTrialVO.getPAYM_FREQ_UNIT());
		
		Element PAYM_FREQ_FREQ = document.createElement("PAYM_FREQ_FREQ");
		PAYM_FREQ_FREQ.setTextContent(reqLoanTrialVO.getPAYM_FREQ_FREQ());
		
		Element LOAN_PAYM_TYP = document.createElement("LOAN_PAYM_TYP");
		LOAN_PAYM_TYP.setTextContent(reqLoanTrialVO.getLOAN_PAYM_TYP());
		
		Element INSTM_IND = document.createElement("INSTM_IND");
		INSTM_IND.setTextContent(reqLoanTrialVO.getINSTM_IND());
		
		Element TNR = document.createElement("TNR");
		TNR.setTextContent(reqLoanTrialVO.getTNR());
		

		Element LmDnShdMtdTList = document.createElement("LmDnShdMtdTList");
		if (reqLoanTrialVO.getLmDnShdMtdTList() == null) {
			return "";
		}
		for (ReqLoanTrialVO lmDnShdMtdTList : reqLoanTrialVO.getLmDnShdMtdTList()) {
			Element MX = document.createElement("MX");

			LmDnShdMtdTList.appendChild(MX);

			Element CAL_TOT_INSTM = document.createElement("CAL_TOT_INSTM");
			CAL_TOT_INSTM.setTextContent(lmDnShdMtdTList.getCAL_TOT_INSTM());

			MX.appendChild(CAL_TOT_INSTM);

		}
		
		Element LmPmShdTList = document.createElement("LmPmShdTList");
		if (reqLoanTrialVO.getLmPmShdTList() == null) {
			return "";
		}
		for (ReqLoanTrialVO lmPmShdTList : reqLoanTrialVO.getLmPmShdTList()) {
			Element MX = document.createElement("MX");
			
			LmPmShdTList.appendChild(MX);
			
			Element PS_DUE_DT = document.createElement("PS_DUE_DT");
			PS_DUE_DT.setTextContent(lmPmShdTList.getPS_DUE_DT());
			
			MX.appendChild(PS_DUE_DT);
			
		}
		
		Element LmFeeTxTList = document.createElement("LmFeeTxTList");
		if (reqLoanTrialVO.getLmFeeTxTList() == null) {
			return "";
		}
		for (ReqLoanTrialVO lmFeeTxTList : reqLoanTrialVO.getLmFeeTxTList()) {
			Element MX = document.createElement("MX");
			
			LmFeeTxTList.appendChild(MX);
			
			Element FEE_CDE = document.createElement("FEE_CDE");
			FEE_CDE.setTextContent(lmFeeTxTList.getFEE_CDE());
			
			Element RECV_PAY_IND = document.createElement("RECV_PAY_IND");
			RECV_PAY_IND.setTextContent(lmFeeTxTList.getRECV_PAY_IND());
			
			Element FEE_TYP = document.createElement("FEE_TYP");
			FEE_TYP.setTextContent(lmFeeTxTList.getFEE_TYP());
			
			Element FEE_CALC_TYP = document.createElement("FEE_CALC_TYP");
			FEE_CALC_TYP.setTextContent(lmFeeTxTList.getFEE_CALC_TYP());
			
			Element FEE_PCT_BASE = document.createElement("FEE_PCT_BASE");
			FEE_PCT_BASE.setTextContent(lmFeeTxTList.getFEE_PCT_BASE());
			
			Element BASE_AMT = document.createElement("BASE_AMT");
			BASE_AMT.setTextContent(lmFeeTxTList.getBASE_AMT());
			
			Element CHRG_PCT = document.createElement("CHRG_PCT");
			CHRG_PCT.setTextContent(lmFeeTxTList.getCHRG_PCT());
			
			Element HOLD_FEE_IND = document.createElement("HOLD_FEE_IND");
			HOLD_FEE_IND.setTextContent(lmFeeTxTList.getHOLD_FEE_IND());
			
			Element HOLD_FEE_SETL_DT = document.createElement("HOLD_FEE_SETL_DT");
			HOLD_FEE_SETL_DT.setTextContent(lmFeeTxTList.getHOLD_FEE_SETL_DT());
			
			Element ACC_IND = document.createElement("ACC_IND");
			ACC_IND.setTextContent(lmFeeTxTList.getACC_IND());
			
			
			MX.appendChild(FEE_CDE);
			MX.appendChild(RECV_PAY_IND);
			MX.appendChild(FEE_TYP);
			MX.appendChild(FEE_CALC_TYP);
			MX.appendChild(FEE_PCT_BASE);
			MX.appendChild(BASE_AMT);
			MX.appendChild(CHRG_PCT);
			MX.appendChild(HOLD_FEE_IND);
			MX.appendChild(HOLD_FEE_SETL_DT);
			MX.appendChild(ACC_IND);
		}
		root.appendChild(serviceId);
		root.appendChild(ORIG_PRCP);
		root.appendChild(DUE_DAY);
		root.appendChild(LOAN_ACTV_DT);
		root.appendChild(LOAN_TYP);
		root.appendChild(INT_START_DT);
		root.appendChild(LOAN_INT_RATE);
		root.appendChild(PAYM_FREQ_UNIT);
		root.appendChild(PAYM_FREQ_FREQ);
		root.appendChild(LOAN_PAYM_TYP);
		root.appendChild(INSTM_IND);
		root.appendChild(TNR);
		root.appendChild(LmDnShdMtdTList);
		root.appendChild(LmPmShdTList);
		root.appendChild(LmFeeTxTList);

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
                     // respLoanTrialVO_1.setPS_FEE_AMT_FIRST(null);
                      //String PS_FEE_AMT_SER = rootElement.getElementsByTagName("PS_FEE_AMT_SER").item(j).getFirstChild().getNodeValue();
                     // respLoanTrialVO_1.setPS_FEE_AMT_SER(null);
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
		//LoanTrialXmlHelper LoanBankXmlHelper = new LoanTrialXmlHelper();
//		ReqLoanTrialVO ReqLoanTrialVO = new ReqLoanTrialVO();
//		List<ReqLoanTrialVO> list = new ArrayList<ReqLoanTrialVO>();
//		ReqLoanTrialVO.setServiceId("serv10000100027");
//		ReqLoanTrialVO.setORIG_PRCP("60000");
//		ReqLoanTrialVO.setDUE_DAY("02");
//		ReqLoanTrialVO.setLOAN_ACTV_DT("2018-02-02");
//		ReqLoanTrialVO.setLOAN_TYP("goodPropertyA");
//		ReqLoanTrialVO.setINT_START_DT("2018-02-02");
//		ReqLoanTrialVO.setLOAN_INT_RATE("0.0665");
//		ReqLoanTrialVO.setPAYM_FREQ_UNIT("M");
//		ReqLoanTrialVO.setPAYM_FREQ_FREQ("1");
//		ReqLoanTrialVO.setLOAN_PAYM_TYP("01");
//		ReqLoanTrialVO.setINSTM_IND("Y");
//		ReqLoanTrialVO.setTNR("36");
//		ReqLoanTrialVO.setCAL_TOT_INSTM("");
//		ReqLoanTrialVO.setPS_DUE_DT("");
//		ReqLoanTrialVO.setFEE_CDE("F0001");
//		ReqLoanTrialVO.setRECV_PAY_IND("R");
//		ReqLoanTrialVO.setFEE_TYP("06");
//		ReqLoanTrialVO.setFEE_CALC_TYP("PCT");
//		ReqLoanTrialVO.setFEE_PCT_BASE("01");
//		ReqLoanTrialVO.setBASE_AMT("60000");
//		ReqLoanTrialVO.setCHRG_PCT("0.0103");
//		ReqLoanTrialVO.setHOLD_FEE_IND("Y");
//		ReqLoanTrialVO.setHOLD_FEE_SETL_DT("2018-02-02");
//		ReqLoanTrialVO.setACC_IND("N");
//		list.add(ReqLoanTrialVO);
//		ReqLoanTrialVO.setLmDnShdMtdTList(list);
//		ReqLoanTrialVO.setLmFeeTxTList(list);
//		ReqLoanTrialVO.setLmPmShdTList(list);
//		String xmlstr = LoanBankXmlHelper.createAddXml(ReqLoanTrialVO);
//		System.out.println(xmlstr);
		
		
		//String xmlstr = "<?xml version=\"1.0\" encoding=\"gb2312\" ?><msgall><errorCode>00000</errorCode><errorMsg/><TOTAL_NORM_INT>6349.44</TOTAL_NORM_INT><TOTAL_AMT>66349.44</TOTAL_AMT><TOTAL_FIRST_FEE>0.0</TOTAL_FIRST_FEE><TOTAL_FEE_AMT>22248.0</TOTAL_FEE_AMT><TOTAL_SER_FEE>0.0</TOTAL_SER_FEE><PayFeeList><MX><HOLD_FEE_SETL_DT>2018-03-02</HOLD_FEE_SETL_DT><HOLD_FEE_IND>Y</HOLD_FEE_IND><FEE_TYP>06</FEE_TYP><FEE_AMT>618.0</FEE_AMT><FEE_CDE>F0001</FEE_CDE><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX><MX><HOLD_FEE_SETL_DT>2018-04-02</HOLD_FEE_SETL_DT><HOLD_FEE_IND>Y</HOLD_FEE_IND><FEE_TYP>06</FEE_TYP><FEE_AMT>618.0</FEE_AMT><FEE_CDE>F0001</FEE_CDE><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX><MX><HOLD_FEE_SETL_DT>2018-05-02</HOLD_FEE_SETL_DT><HOLD_FEE_IND>Y</HOLD_FEE_IND><FEE_TYP>06</FEE_TYP><FEE_AMT>618.0</FEE_AMT><FEE_CDE>F0001</FEE_CDE><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX><MX><HOLD_FEE_SETL_DT>2018-06-02</HOLD_FEE_SETL_DT><HOLD_FEE_IND>Y</HOLD_FEE_IND><FEE_TYP>06</FEE_TYP><FEE_AMT>618.0</FEE_AMT><FEE_CDE>F0001</FEE_CDE><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX><MX><HOLD_FEE_SETL_DT>2018-07-02</HOLD_FEE_SETL_DT><HOLD_FEE_IND>Y</HOLD_FEE_IND><FEE_TYP>06</FEE_TYP><FEE_AMT>618.0</FEE_AMT><FEE_CDE>F0001</FEE_CDE><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX><MX><HOLD_FEE_SETL_DT>2018-08-02</HOLD_FEE_SETL_DT><HOLD_FEE_IND>Y</HOLD_FEE_IND><FEE_TYP>06</FEE_TYP><FEE_AMT>618.0</FEE_AMT><FEE_CDE>F0001</FEE_CDE><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX><MX><HOLD_FEE_SETL_DT>2018-09-02</HOLD_FEE_SETL_DT><HOLD_FEE_IND>Y</HOLD_FEE_IND><FEE_TYP>06</FEE_TYP><FEE_AMT>618.0</FEE_AMT><FEE_CDE>F0001</FEE_CDE><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX><MX><HOLD_FEE_SETL_DT>2018-10-02</HOLD_FEE_SETL_DT><HOLD_FEE_IND>Y</HOLD_FEE_IND><FEE_TYP>06</FEE_TYP><FEE_AMT>618.0</FEE_AMT><FEE_CDE>F0001</FEE_CDE><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX><MX><HOLD_FEE_SETL_DT>2018-11-02</HOLD_FEE_SETL_DT><HOLD_FEE_IND>Y</HOLD_FEE_IND><FEE_TYP>06</FEE_TYP><FEE_AMT>618.0</FEE_AMT><FEE_CDE>F0001</FEE_CDE><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX><MX><HOLD_FEE_SETL_DT>2018-12-02</HOLD_FEE_SETL_DT><HOLD_FEE_IND>Y</HOLD_FEE_IND><FEE_TYP>06</FEE_TYP><FEE_AMT>618.0</FEE_AMT><FEE_CDE>F0001</FEE_CDE><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX><MX><HOLD_FEE_SETL_DT>2019-01-02</HOLD_FEE_SETL_DT><HOLD_FEE_IND>Y</HOLD_FEE_IND><FEE_TYP>06</FEE_TYP><FEE_AMT>618.0</FEE_AMT><FEE_CDE>F0001</FEE_CDE><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX><MX><HOLD_FEE_SETL_DT>2019-02-02</HOLD_FEE_SETL_DT><HOLD_FEE_IND>Y</HOLD_FEE_IND><FEE_TYP>06</FEE_TYP><FEE_AMT>618.0</FEE_AMT><FEE_CDE>F0001</FEE_CDE><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX><MX><HOLD_FEE_SETL_DT>2019-03-02</HOLD_FEE_SETL_DT><HOLD_FEE_IND>Y</HOLD_FEE_IND><FEE_TYP>06</FEE_TYP><FEE_AMT>618.0</FEE_AMT><FEE_CDE>F0001</FEE_CDE><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX><MX><HOLD_FEE_SETL_DT>2019-04-02</HOLD_FEE_SETL_DT><HOLD_FEE_IND>Y</HOLD_FEE_IND><FEE_TYP>06</FEE_TYP><FEE_AMT>618.0</FEE_AMT><FEE_CDE>F0001</FEE_CDE><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX><MX><HOLD_FEE_SETL_DT>2019-05-02</HOLD_FEE_SETL_DT><HOLD_FEE_IND>Y</HOLD_FEE_IND><FEE_TYP>06</FEE_TYP><FEE_AMT>618.0</FEE_AMT><FEE_CDE>F0001</FEE_CDE><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX><MX><HOLD_FEE_SETL_DT>2019-06-02</HOLD_FEE_SETL_DT><HOLD_FEE_IND>Y</HOLD_FEE_IND><FEE_TYP>06</FEE_TYP><FEE_AMT>618.0</FEE_AMT><FEE_CDE>F0001</FEE_CDE><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX><MX><HOLD_FEE_SETL_DT>2019-07-02</HOLD_FEE_SETL_DT><HOLD_FEE_IND>Y</HOLD_FEE_IND><FEE_TYP>06</FEE_TYP><FEE_AMT>618.0</FEE_AMT><FEE_CDE>F0001</FEE_CDE><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX><MX><HOLD_FEE_SETL_DT>2019-08-02</HOLD_FEE_SETL_DT><HOLD_FEE_IND>Y</HOLD_FEE_IND><FEE_TYP>06</FEE_TYP><FEE_AMT>618.0</FEE_AMT><FEE_CDE>F0001</FEE_CDE><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX><MX><HOLD_FEE_SETL_DT>2019-09-02</HOLD_FEE_SETL_DT><HOLD_FEE_IND>Y</HOLD_FEE_IND><FEE_TYP>06</FEE_TYP><FEE_AMT>618.0</FEE_AMT><FEE_CDE>F0001</FEE_CDE><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX><MX><HOLD_FEE_SETL_DT>2019-10-02</HOLD_FEE_SETL_DT><HOLD_FEE_IND>Y</HOLD_FEE_IND><FEE_TYP>06</FEE_TYP><FEE_AMT>618.0</FEE_AMT><FEE_CDE>F0001</FEE_CDE><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX><MX><HOLD_FEE_SETL_DT>2019-11-02</HOLD_FEE_SETL_DT><HOLD_FEE_IND>Y</HOLD_FEE_IND><FEE_TYP>06</FEE_TYP><FEE_AMT>618.0</FEE_AMT><FEE_CDE>F0001</FEE_CDE><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX><MX><HOLD_FEE_SETL_DT>2019-12-02</HOLD_FEE_SETL_DT><HOLD_FEE_IND>Y</HOLD_FEE_IND><FEE_TYP>06</FEE_TYP><FEE_AMT>618.0</FEE_AMT><FEE_CDE>F0001</FEE_CDE><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX><MX><HOLD_FEE_SETL_DT>2020-01-02</HOLD_FEE_SETL_DT><HOLD_FEE_IND>Y</HOLD_FEE_IND><FEE_TYP>06</FEE_TYP><FEE_AMT>618.0</FEE_AMT><FEE_CDE>F0001</FEE_CDE><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX><MX><HOLD_FEE_SETL_DT>2020-02-02</HOLD_FEE_SETL_DT><HOLD_FEE_IND>Y</HOLD_FEE_IND><FEE_TYP>06</FEE_TYP><FEE_AMT>618.0</FEE_AMT><FEE_CDE>F0001</FEE_CDE><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX><MX><HOLD_FEE_SETL_DT>2020-03-02</HOLD_FEE_SETL_DT><HOLD_FEE_IND>Y</HOLD_FEE_IND><FEE_TYP>06</FEE_TYP><FEE_AMT>618.0</FEE_AMT><FEE_CDE>F0001</FEE_CDE><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX><MX><HOLD_FEE_SETL_DT>2020-04-02</HOLD_FEE_SETL_DT><HOLD_FEE_IND>Y</HOLD_FEE_IND><FEE_TYP>06</FEE_TYP><FEE_AMT>618.0</FEE_AMT><FEE_CDE>F0001</FEE_CDE><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX><MX><HOLD_FEE_SETL_DT>2020-05-02</HOLD_FEE_SETL_DT><HOLD_FEE_IND>Y</HOLD_FEE_IND><FEE_TYP>06</FEE_TYP><FEE_AMT>618.0</FEE_AMT><FEE_CDE>F0001</FEE_CDE><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX><MX><HOLD_FEE_SETL_DT>2020-06-02</HOLD_FEE_SETL_DT><HOLD_FEE_IND>Y</HOLD_FEE_IND><FEE_TYP>06</FEE_TYP><FEE_AMT>618.0</FEE_AMT><FEE_CDE>F0001</FEE_CDE><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX><MX><HOLD_FEE_SETL_DT>2020-07-02</HOLD_FEE_SETL_DT><HOLD_FEE_IND>Y</HOLD_FEE_IND><FEE_TYP>06</FEE_TYP><FEE_AMT>618.0</FEE_AMT><FEE_CDE>F0001</FEE_CDE><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX><MX><HOLD_FEE_SETL_DT>2020-08-02</HOLD_FEE_SETL_DT><HOLD_FEE_IND>Y</HOLD_FEE_IND><FEE_TYP>06</FEE_TYP><FEE_AMT>618.0</FEE_AMT><FEE_CDE>F0001</FEE_CDE><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX><MX><HOLD_FEE_SETL_DT>2020-09-02</HOLD_FEE_SETL_DT><HOLD_FEE_IND>Y</HOLD_FEE_IND><FEE_TYP>06</FEE_TYP><FEE_AMT>618.0</FEE_AMT><FEE_CDE>F0001</FEE_CDE><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX><MX><HOLD_FEE_SETL_DT>2020-10-02</HOLD_FEE_SETL_DT><HOLD_FEE_IND>Y</HOLD_FEE_IND><FEE_TYP>06</FEE_TYP><FEE_AMT>618.0</FEE_AMT><FEE_CDE>F0001</FEE_CDE><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX><MX><HOLD_FEE_SETL_DT>2020-11-02</HOLD_FEE_SETL_DT><HOLD_FEE_IND>Y</HOLD_FEE_IND><FEE_TYP>06</FEE_TYP><FEE_AMT>618.0</FEE_AMT><FEE_CDE>F0001</FEE_CDE><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX><MX><HOLD_FEE_SETL_DT>2020-12-02</HOLD_FEE_SETL_DT><HOLD_FEE_IND>Y</HOLD_FEE_IND><FEE_TYP>06</FEE_TYP><FEE_AMT>618.0</FEE_AMT><FEE_CDE>F0001</FEE_CDE><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX><MX><HOLD_FEE_SETL_DT>2021-01-02</HOLD_FEE_SETL_DT><HOLD_FEE_IND>Y</HOLD_FEE_IND><FEE_TYP>06</FEE_TYP><FEE_AMT>618.0</FEE_AMT><FEE_CDE>F0001</FEE_CDE><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX><MX><HOLD_FEE_SETL_DT>2021-02-02</HOLD_FEE_SETL_DT><HOLD_FEE_IND>Y</HOLD_FEE_IND><FEE_TYP>06</FEE_TYP><FEE_AMT>618.0</FEE_AMT><FEE_CDE>F0001</FEE_CDE><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX></PayFeeList><PayShdTryListAll><MX><PERD_NO>0</PERD_NO><PRCP_AMT>0.0</PRCP_AMT><INSTM_AMT>0.0</INSTM_AMT><NORM_INT>0.0</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2018-02-02</DUE_DT><PS_REM_PRCP>60000.0</PS_REM_PRCP><PS_FEE_AMT/><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST/><PS_FEE_AMT_SER/></MX><MX><PERD_NO>1</PERD_NO><PRCP_AMT>1510.54</PRCP_AMT><INSTM_AMT>1843.04</INSTM_AMT><NORM_INT>332.5</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2018-03-02</DUE_DT><PS_REM_PRCP>58489.46</PS_REM_PRCP><PS_FEE_AMT>618.0</PS_FEE_AMT><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST>0.0</PS_FEE_AMT_FIRST><PS_FEE_AMT_SER/></MX><MX><PERD_NO>2</PERD_NO><PRCP_AMT>1518.91</PRCP_AMT><INSTM_AMT>1843.04</INSTM_AMT><NORM_INT>324.13</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2018-04-02</DUE_DT><PS_REM_PRCP>56970.55</PS_REM_PRCP><PS_FEE_AMT>618.0</PS_FEE_AMT><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST>0.0</PS_FEE_AMT_FIRST><PS_FEE_AMT_SER/></MX><MX><PERD_NO>3</PERD_NO><PRCP_AMT>1527.33</PRCP_AMT><INSTM_AMT>1843.04</INSTM_AMT><NORM_INT>315.71</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2018-05-02</DUE_DT><PS_REM_PRCP>55443.22</PS_REM_PRCP><PS_FEE_AMT>618.0</PS_FEE_AMT><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST>0.0</PS_FEE_AMT_FIRST><PS_FEE_AMT_SER/></MX><MX><PERD_NO>4</PERD_NO><PRCP_AMT>1535.79</PRCP_AMT><INSTM_AMT>1843.04</INSTM_AMT><NORM_INT>307.25</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2018-06-02</DUE_DT><PS_REM_PRCP>53907.43</PS_REM_PRCP><PS_FEE_AMT>618.0</PS_FEE_AMT><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST>0.0</PS_FEE_AMT_FIRST><PS_FEE_AMT_SER/></MX><MX><PERD_NO>5</PERD_NO><PRCP_AMT>1544.3</PRCP_AMT><INSTM_AMT>1843.04</INSTM_AMT><NORM_INT>298.74</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2018-07-02</DUE_DT><PS_REM_PRCP>52363.13</PS_REM_PRCP><PS_FEE_AMT>618.0</PS_FEE_AMT><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST>0.0</PS_FEE_AMT_FIRST><PS_FEE_AMT_SER/></MX><MX><PERD_NO>6</PERD_NO><PRCP_AMT>1552.86</PRCP_AMT><INSTM_AMT>1843.04</INSTM_AMT><NORM_INT>290.18</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2018-08-02</DUE_DT><PS_REM_PRCP>50810.27</PS_REM_PRCP><PS_FEE_AMT>618.0</PS_FEE_AMT><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST>0.0</PS_FEE_AMT_FIRST><PS_FEE_AMT_SER/></MX><MX><PERD_NO>7</PERD_NO><PRCP_AMT>1561.47</PRCP_AMT><INSTM_AMT>1843.04</INSTM_AMT><NORM_INT>281.57</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2018-09-02</DUE_DT><PS_REM_PRCP>49248.8</PS_REM_PRCP><PS_FEE_AMT>618.0</PS_FEE_AMT><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST>0.0</PS_FEE_AMT_FIRST><PS_FEE_AMT_SER/></MX><MX><PERD_NO>8</PERD_NO><PRCP_AMT>1570.12</PRCP_AMT><INSTM_AMT>1843.04</INSTM_AMT><NORM_INT>272.92</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2018-10-02</DUE_DT><PS_REM_PRCP>47678.68</PS_REM_PRCP><PS_FEE_AMT>618.0</PS_FEE_AMT><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST>0.0</PS_FEE_AMT_FIRST><PS_FEE_AMT_SER/></MX><MX><PERD_NO>9</PERD_NO><PRCP_AMT>1578.82</PRCP_AMT><INSTM_AMT>1843.04</INSTM_AMT><NORM_INT>264.22</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2018-11-02</DUE_DT><PS_REM_PRCP>46099.86</PS_REM_PRCP><PS_FEE_AMT>618.0</PS_FEE_AMT><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST>0.0</PS_FEE_AMT_FIRST><PS_FEE_AMT_SER/></MX><MX><PERD_NO>10</PERD_NO><PRCP_AMT>1587.57</PRCP_AMT><INSTM_AMT>1843.04</INSTM_AMT><NORM_INT>255.47</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2018-12-02</DUE_DT><PS_REM_PRCP>44512.29</PS_REM_PRCP><PS_FEE_AMT>618.0</PS_FEE_AMT><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST>0.0</PS_FEE_AMT_FIRST><PS_FEE_AMT_SER/></MX><MX><PERD_NO>11</PERD_NO><PRCP_AMT>1596.37</PRCP_AMT><INSTM_AMT>1843.04</INSTM_AMT><NORM_INT>246.67</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2019-01-02</DUE_DT><PS_REM_PRCP>42915.92</PS_REM_PRCP><PS_FEE_AMT>618.0</PS_FEE_AMT><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST>0.0</PS_FEE_AMT_FIRST><PS_FEE_AMT_SER/></MX><MX><PERD_NO>12</PERD_NO><PRCP_AMT>1605.21</PRCP_AMT><INSTM_AMT>1843.04</INSTM_AMT><NORM_INT>237.83</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2019-02-02</DUE_DT><PS_REM_PRCP>41310.71</PS_REM_PRCP><PS_FEE_AMT>618.0</PS_FEE_AMT><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST>0.0</PS_FEE_AMT_FIRST><PS_FEE_AMT_SER/></MX><MX><PERD_NO>13</PERD_NO><PRCP_AMT>1614.11</PRCP_AMT><INSTM_AMT>1843.04</INSTM_AMT><NORM_INT>228.93</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2019-03-02</DUE_DT><PS_REM_PRCP>39696.6</PS_REM_PRCP><PS_FEE_AMT>618.0</PS_FEE_AMT><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST>0.0</PS_FEE_AMT_FIRST><PS_FEE_AMT_SER/></MX><MX><PERD_NO>14</PERD_NO><PRCP_AMT>1623.05</PRCP_AMT><INSTM_AMT>1843.04</INSTM_AMT><NORM_INT>219.99</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2019-04-02</DUE_DT><PS_REM_PRCP>38073.55</PS_REM_PRCP><PS_FEE_AMT>618.0</PS_FEE_AMT><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST>0.0</PS_FEE_AMT_FIRST><PS_FEE_AMT_SER/></MX><MX><PERD_NO>15</PERD_NO><PRCP_AMT>1632.05</PRCP_AMT><INSTM_AMT>1843.04</INSTM_AMT><NORM_INT>210.99</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2019-05-02</DUE_DT><PS_REM_PRCP>36441.5</PS_REM_PRCP><PS_FEE_AMT>618.0</PS_FEE_AMT><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST>0.0</PS_FEE_AMT_FIRST><PS_FEE_AMT_SER/></MX><MX><PERD_NO>16</PERD_NO><PRCP_AMT>1641.09</PRCP_AMT><INSTM_AMT>1843.04</INSTM_AMT><NORM_INT>201.95</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2019-06-02</DUE_DT><PS_REM_PRCP>34800.41</PS_REM_PRCP><PS_FEE_AMT>618.0</PS_FEE_AMT><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST>0.0</PS_FEE_AMT_FIRST><PS_FEE_AMT_SER/></MX><MX><PERD_NO>17</PERD_NO><PRCP_AMT>1650.19</PRCP_AMT><INSTM_AMT>1843.04</INSTM_AMT><NORM_INT>192.85</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2019-07-02</DUE_DT><PS_REM_PRCP>33150.22</PS_REM_PRCP><PS_FEE_AMT>618.0</PS_FEE_AMT><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST>0.0</PS_FEE_AMT_FIRST><PS_FEE_AMT_SER/></MX><MX><PERD_NO>18</PERD_NO><PRCP_AMT>1659.33</PRCP_AMT><INSTM_AMT>1843.04</INSTM_AMT><NORM_INT>183.71</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2019-08-02</DUE_DT><PS_REM_PRCP>31490.89</PS_REM_PRCP><PS_FEE_AMT>618.0</PS_FEE_AMT><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST>0.0</PS_FEE_AMT_FIRST><PS_FEE_AMT_SER/></MX><MX><PERD_NO>19</PERD_NO><PRCP_AMT>1668.53</PRCP_AMT><INSTM_AMT>1843.04</INSTM_AMT><NORM_INT>174.51</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2019-09-02</DUE_DT><PS_REM_PRCP>29822.36</PS_REM_PRCP><PS_FEE_AMT>618.0</PS_FEE_AMT><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST>0.0</PS_FEE_AMT_FIRST><PS_FEE_AMT_SER/></MX><MX><PERD_NO>20</PERD_NO><PRCP_AMT>1677.77</PRCP_AMT><INSTM_AMT>1843.04</INSTM_AMT><NORM_INT>165.27</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2019-10-02</DUE_DT><PS_REM_PRCP>28144.59</PS_REM_PRCP><PS_FEE_AMT>618.0</PS_FEE_AMT><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST>0.0</PS_FEE_AMT_FIRST><PS_FEE_AMT_SER/></MX><MX><PERD_NO>21</PERD_NO><PRCP_AMT>1687.07</PRCP_AMT><INSTM_AMT>1843.04</INSTM_AMT><NORM_INT>155.97</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2019-11-02</DUE_DT><PS_REM_PRCP>26457.52</PS_REM_PRCP><PS_FEE_AMT>618.0</PS_FEE_AMT><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST>0.0</PS_FEE_AMT_FIRST><PS_FEE_AMT_SER/></MX><MX><PERD_NO>22</PERD_NO><PRCP_AMT>1696.42</PRCP_AMT><INSTM_AMT>1843.04</INSTM_AMT><NORM_INT>146.62</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2019-12-02</DUE_DT><PS_REM_PRCP>24761.1</PS_REM_PRCP><PS_FEE_AMT>618.0</PS_FEE_AMT><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST>0.0</PS_FEE_AMT_FIRST><PS_FEE_AMT_SER/></MX><MX><PERD_NO>23</PERD_NO><PRCP_AMT>1705.82</PRCP_AMT><INSTM_AMT>1843.04</INSTM_AMT><NORM_INT>137.22</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2020-01-02</DUE_DT><PS_REM_PRCP>23055.28</PS_REM_PRCP><PS_FEE_AMT>618.0</PS_FEE_AMT><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST>0.0</PS_FEE_AMT_FIRST><PS_FEE_AMT_SER/></MX><MX><PERD_NO>24</PERD_NO><PRCP_AMT>1715.28</PRCP_AMT><INSTM_AMT>1843.04</INSTM_AMT><NORM_INT>127.76</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2020-02-02</DUE_DT><PS_REM_PRCP>21340.0</PS_REM_PRCP><PS_FEE_AMT>618.0</PS_FEE_AMT><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST>0.0</PS_FEE_AMT_FIRST><PS_FEE_AMT_SER/></MX><MX><PERD_NO>25</PERD_NO><PRCP_AMT>1724.78</PRCP_AMT><INSTM_AMT>1843.04</INSTM_AMT><NORM_INT>118.26</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2020-03-02</DUE_DT><PS_REM_PRCP>19615.22</PS_REM_PRCP><PS_FEE_AMT>618.0</PS_FEE_AMT><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST>0.0</PS_FEE_AMT_FIRST><PS_FEE_AMT_SER/></MX><MX><PERD_NO>26</PERD_NO><PRCP_AMT>1734.34</PRCP_AMT><INSTM_AMT>1843.04</INSTM_AMT><NORM_INT>108.7</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2020-04-02</DUE_DT><PS_REM_PRCP>17880.88</PS_REM_PRCP><PS_FEE_AMT>618.0</PS_FEE_AMT><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST>0.0</PS_FEE_AMT_FIRST><PS_FEE_AMT_SER/></MX><MX><PERD_NO>27</PERD_NO><PRCP_AMT>1743.95</PRCP_AMT><INSTM_AMT>1843.04</INSTM_AMT><NORM_INT>99.09</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2020-05-02</DUE_DT><PS_REM_PRCP>16136.93</PS_REM_PRCP><PS_FEE_AMT>618.0</PS_FEE_AMT><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST>0.0</PS_FEE_AMT_FIRST><PS_FEE_AMT_SER/></MX><MX><PERD_NO>28</PERD_NO><PRCP_AMT>1753.61</PRCP_AMT><INSTM_AMT>1843.04</INSTM_AMT><NORM_INT>89.43</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2020-06-02</DUE_DT><PS_REM_PRCP>14383.32</PS_REM_PRCP><PS_FEE_AMT>618.0</PS_FEE_AMT><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST>0.0</PS_FEE_AMT_FIRST><PS_FEE_AMT_SER/></MX><MX><PERD_NO>29</PERD_NO><PRCP_AMT>1763.33</PRCP_AMT><INSTM_AMT>1843.04</INSTM_AMT><NORM_INT>79.71</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2020-07-02</DUE_DT><PS_REM_PRCP>12619.99</PS_REM_PRCP><PS_FEE_AMT>618.0</PS_FEE_AMT><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST>0.0</PS_FEE_AMT_FIRST><PS_FEE_AMT_SER/></MX><MX><PERD_NO>30</PERD_NO><PRCP_AMT>1773.1</PRCP_AMT><INSTM_AMT>1843.04</INSTM_AMT><NORM_INT>69.94</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2020-08-02</DUE_DT><PS_REM_PRCP>10846.89</PS_REM_PRCP><PS_FEE_AMT>618.0</PS_FEE_AMT><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST>0.0</PS_FEE_AMT_FIRST><PS_FEE_AMT_SER/></MX><MX><PERD_NO>31</PERD_NO><PRCP_AMT>1782.93</PRCP_AMT><INSTM_AMT>1843.04</INSTM_AMT><NORM_INT>60.11</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2020-09-02</DUE_DT><PS_REM_PRCP>9063.96</PS_REM_PRCP><PS_FEE_AMT>618.0</PS_FEE_AMT><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST>0.0</PS_FEE_AMT_FIRST><PS_FEE_AMT_SER/></MX><MX><PERD_NO>32</PERD_NO><PRCP_AMT>1792.81</PRCP_AMT><INSTM_AMT>1843.04</INSTM_AMT><NORM_INT>50.23</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2020-10-02</DUE_DT><PS_REM_PRCP>7271.15</PS_REM_PRCP><PS_FEE_AMT>618.0</PS_FEE_AMT><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST>0.0</PS_FEE_AMT_FIRST><PS_FEE_AMT_SER/></MX><MX><PERD_NO>33</PERD_NO><PRCP_AMT>1802.75</PRCP_AMT><INSTM_AMT>1843.04</INSTM_AMT><NORM_INT>40.29</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2020-11-02</DUE_DT><PS_REM_PRCP>5468.4</PS_REM_PRCP><PS_FEE_AMT>618.0</PS_FEE_AMT><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST>0.0</PS_FEE_AMT_FIRST><PS_FEE_AMT_SER/></MX><MX><PERD_NO>34</PERD_NO><PRCP_AMT>1812.74</PRCP_AMT><INSTM_AMT>1843.04</INSTM_AMT><NORM_INT>30.3</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2020-12-02</DUE_DT><PS_REM_PRCP>3655.66</PS_REM_PRCP><PS_FEE_AMT>618.0</PS_FEE_AMT><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST>0.0</PS_FEE_AMT_FIRST><PS_FEE_AMT_SER/></MX><MX><PERD_NO>35</PERD_NO><PRCP_AMT>1822.78</PRCP_AMT><INSTM_AMT>1843.04</INSTM_AMT><NORM_INT>20.26</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2021-01-02</DUE_DT><PS_REM_PRCP>1832.88</PS_REM_PRCP><PS_FEE_AMT>618.0</PS_FEE_AMT><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST>0.0</PS_FEE_AMT_FIRST><PS_FEE_AMT_SER/></MX><MX><PERD_NO>36</PERD_NO><PRCP_AMT>1832.88</PRCP_AMT><INSTM_AMT>1843.04</INSTM_AMT><NORM_INT>10.16</NORM_INT><INT_RATE>0.0665</INT_RATE><OD_INT_RATE>0.0</OD_INT_RATE><DUE_DT>2021-02-02</DUE_DT><PS_REM_PRCP>0.0</PS_REM_PRCP><PS_FEE_AMT>618.0</PS_FEE_AMT><FREE_INTEREST>0.0</FREE_INTEREST><PS_FEE_AMT_FIRST>0.0</PS_FEE_AMT_FIRST><PS_FEE_AMT_SER/></MX></PayShdTryListAll></msgall>\r\n"; 
		//RespLoanTrialVO RespLoanTrialVO =LoanBankXmlHelper.xml2BeanFromAdd(xmlstr);
		//System.out.println(RespLoanTrialVO.toString());
	}
}
