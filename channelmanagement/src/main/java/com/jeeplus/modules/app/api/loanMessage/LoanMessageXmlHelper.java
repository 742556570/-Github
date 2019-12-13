package com.jeeplus.modules.app.api.loanMessage;

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
import org.xml.sax.InputSource;

import com.jeeplus.modules.app.api.loanMessage.request.ReqLoanMessageVO;
import com.jeeplus.modules.app.api.loanMessage.response.RespLoanMessageVO;


/**
 * 阳光保险 xml 转换帮助类 将放款同步信息参数封装成xml
 */

public class LoanMessageXmlHelper {
	/**
	 * 
	 * @return xml字符串
	 * @throws TransformerConfigurationException
	 * @throws ParserConfigurationException
	 */
	public String createAddXml(ReqLoanMessageVO reqLoanMessageVO) {

		
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
		serviceId.setTextContent(reqLoanMessageVO.getServiceId());
		// <serviceId>servP0000100012</serviceId>
		if (reqLoanMessageVO.getLmFeeTxTList() == null) {
			return "";
		}
		Element LOAN_CONT_NO = document.createElement("LOAN_CONT_NO");
		LOAN_CONT_NO.setTextContent(reqLoanMessageVO.getLOAN_CONT_NO());

		Element BEL_TYPE = document.createElement("BEL_TYPE");
		BEL_TYPE.setTextContent(reqLoanMessageVO.getBEL_TYPE());

		Element BCH_CDE = document.createElement("BCH_CDE");
		BCH_CDE.setTextContent(reqLoanMessageVO.getBCH_CDE());

		Element LOAN_NO = document.createElement("LOAN_NO");
		LOAN_NO.setTextContent(reqLoanMessageVO.getLOAN_NO());

		Element CUST_NAME = document.createElement("CUST_NAME");
		CUST_NAME.setTextContent(reqLoanMessageVO.getCUST_NAME());

		Element ID_NO = document.createElement("ID_NO");
		ID_NO.setTextContent(reqLoanMessageVO.getID_NO());

		Element ORIG_PRCP = document.createElement("ORIG_PRCP");
		ORIG_PRCP.setTextContent(reqLoanMessageVO.getORIG_PRCP());
		
		Element LOAN_ACTV_DT = document.createElement("LOAN_ACTV_DT");
		LOAN_ACTV_DT.setTextContent(reqLoanMessageVO.getLOAN_ACTV_DT());
		
		Element LOAN_TYP = document.createElement("LOAN_TYP");
		LOAN_TYP.setTextContent(reqLoanMessageVO.getLOAN_TYP());

		Element LOAN_PAYM_MTD = document.createElement("LOAN_PAYM_MTD");
		LOAN_PAYM_MTD.setTextContent(reqLoanMessageVO.getLOAN_PAYM_MTD());

		Element LOAN_PAYM_TYP = document.createElement("LOAN_PAYM_TYP");
		LOAN_PAYM_TYP.setTextContent(reqLoanMessageVO.getLOAN_PAYM_TYP());

		Element TNR = document.createElement("TNR");
		TNR.setTextContent(reqLoanMessageVO.getTNR());

		Element DN_CHANNEL = document.createElement("DN_CHANNEL");
		DN_CHANNEL.setTextContent(reqLoanMessageVO.getDN_CHANNEL());
		
		Element FEE_ACT = document.createElement("FEE_ACT");
		FEE_ACT.setTextContent(reqLoanMessageVO.getFEE_ACT());
		
		Element HOLD_FEE_IND = document.createElement("HOLD_FEE_IND");
		HOLD_FEE_IND.setTextContent(reqLoanMessageVO.getHOLD_FEE_IND());
		
		Element RISK_CODE = document.createElement("RISK_CODE");
		RISK_CODE.setTextContent(reqLoanMessageVO.getRISK_CODE());
		
		Element FIRST_FEE_RATE = document.createElement("FIRST_FEE_RATE");
		FIRST_FEE_RATE.setTextContent(reqLoanMessageVO.getFIRST_FEE_RATE());
		
		Element SERV_FEE_RATE = document.createElement("SERV_FEE_RATE");
		SERV_FEE_RATE.setTextContent(reqLoanMessageVO.getSERV_FEE_RATE());
		
		Element GUAR_FEE_RATE = document.createElement("GUAR_FEE_RATE");
		GUAR_FEE_RATE.setTextContent(reqLoanMessageVO.getGUAR_FEE_RATE());
		
		Element PH_FLAG = document.createElement("PH_FLAG");
		PH_FLAG.setTextContent(reqLoanMessageVO.getPH_FLAG());

		Element LmFeeTxTList = document.createElement("LmFeeTxTList");
		for (ReqLoanMessageVO reqLoanMessageVOList : reqLoanMessageVO.getLmFeeTxTList()) {
			Element MX = document.createElement("MX");
			LmFeeTxTList.appendChild(MX);

			Element HOLD_FEE_IND1 = document.createElement("HOLD_FEE_IND");
			HOLD_FEE_IND1.setTextContent(reqLoanMessageVO.getHOLD_FEE_IND());
			
			Element ACC_IND = document.createElement("ACC_IND");
			ACC_IND.setTextContent(reqLoanMessageVOList.getACC_IND());

			MX.appendChild(HOLD_FEE_IND1);
			MX.appendChild(ACC_IND);

		}
		root.appendChild(serviceId);
		root.appendChild(LOAN_CONT_NO);
		root.appendChild(BEL_TYPE);
		root.appendChild(BCH_CDE);
		root.appendChild(LOAN_NO);
		root.appendChild(CUST_NAME);
		root.appendChild(ID_NO);
		root.appendChild(ORIG_PRCP);
		root.appendChild(LOAN_ACTV_DT);
		root.appendChild(LOAN_TYP);
		root.appendChild(LOAN_PAYM_MTD);
		root.appendChild(LOAN_PAYM_TYP);
		root.appendChild(TNR);
		root.appendChild(DN_CHANNEL);
		root.appendChild(FEE_ACT);
		root.appendChild(HOLD_FEE_IND);
		root.appendChild(RISK_CODE);
		root.appendChild(LmFeeTxTList);
		root.appendChild(FIRST_FEE_RATE);
		root.appendChild(SERV_FEE_RATE);
		root.appendChild(GUAR_FEE_RATE);
		root.appendChild(PH_FLAG);


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
	 * 同步产品信息对象 <?xml version="1.0" encoding="gb2312"
	 * ?><msgall><errorCode>00000</errorCode><errorMsg>success</errorMsg></msgall>
	 *
	 * @param xmlStr
	 *            xml 字符串
	 * @return 序列化对象
	 */
	public RespLoanMessageVO xml2BeanFromAdd(String xmlStr) {
		if (TextUtils.isEmpty(xmlStr)) {
			return null;
		}
		RespLoanMessageVO respLoanMessageVO = null;
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
			String errorMsg = rootElement.getElementsByTagName("errorMsg").item(0).getFirstChild().getNodeValue();
			respLoanMessageVO = new RespLoanMessageVO(errorCode, errorMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respLoanMessageVO;
	}

	public static void main(String[] args) {
		LoanMessageXmlHelper loanMessageXmlHelper = new LoanMessageXmlHelper();
		ReqLoanMessageVO reqLoanMessageVO = new ReqLoanMessageVO();
		List<ReqLoanMessageVO> list = new ArrayList<ReqLoanMessageVO>();
		reqLoanMessageVO.setServiceId("serv10000000122");
		reqLoanMessageVO.setLOAN_CONT_NO("C830820180115233666");
		reqLoanMessageVO.setBEL_TYPE("01");
		reqLoanMessageVO.setBCH_CDE("00030300");
		reqLoanMessageVO.setLOAN_NO("C830820180115233666");
		reqLoanMessageVO.setCUST_NAME("史小成");
		reqLoanMessageVO.setID_NO("320483198305167439");
		reqLoanMessageVO.setORIG_PRCP("112000");
		reqLoanMessageVO.setLOAN_ACTV_DT("2018-01-16");
		reqLoanMessageVO.setLOAN_TYP("property");
		reqLoanMessageVO.setLOAN_PAYM_MTD("01");
		reqLoanMessageVO.setLOAN_PAYM_TYP("01");
		reqLoanMessageVO.setTNR("36");
		reqLoanMessageVO.setDN_CHANNEL("FOTIC");
		reqLoanMessageVO.setFEE_ACT("1.7");
		reqLoanMessageVO.setHOLD_FEE_IND("Y");
		reqLoanMessageVO.setRISK_CODE("8308");
		reqLoanMessageVO.setACC_IND("N");
		reqLoanMessageVO.setFIRST_FEE_RATE("0");
		reqLoanMessageVO.setSERV_FEE_RATE("0");
		reqLoanMessageVO.setGUAR_FEE_RATE("1.7");
		reqLoanMessageVO.setPH_FLAG("N");
		list.add(reqLoanMessageVO);
		reqLoanMessageVO.setLmFeeTxTList(list);
		String xmlstr = loanMessageXmlHelper.createAddXml(reqLoanMessageVO);
		System.out.println(xmlstr);
	}
}
