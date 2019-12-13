package com.jeeplus.modules.app.api.loanChannel;

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

import com.jeeplus.modules.app.api.loanChannel.request.ReqLoanChannelVO;
import com.jeeplus.modules.app.api.loanChannel.response.RespLoanChannelVO;


/**
 * 阳光保险 xml 转换帮助类 将放款渠道同步信息参数封装成xml
 */

public class LoanChannelXmlHelper {
	/**
	 * 放款渠道同步信息参数的xml xml 格式 <?xml version="1.0" encoding="UTF-8"
	 * standalone="yes"?> <msgbody> <serviceId>servP0000100011</serviceId>
	 * <PLoanTypMtdList> <MX> <DN_CHANNEL>FOTIC</DN_CHANNEL> <TNR_OPT>22</TNR_OPT>
	 * <LOAN_RATE_MODE>FX</LOAN_RATE_MODE> <LOAN_BASE_RATE>FOTIC</LOAN_BASE_RATE>
	 * <INT_ADJ_PCT/> <RATE_TYPE>U</RATE_TYPE> <INT_RAT>0.058725</INT_RAT>
	 * <OD_INT_RATE>0.24</OD_INT_RATE> <LEND_CHA_NAM>银行测试2</LEND_CHA_NAM>
	 * <OPT_TYP>INSEAT</OPT_TYP> <LAST_CHG_USR>admin</LAST_CHG_USR>
	 * <LAST_CHG_DT>2017-06-28 17:28:12</LAST_CHG_DT> <BEL_TYPE>07</BEL_TYPE>
	 * <TOTTERM_X>X</TOTTERM_X> <XQ_RATE/> </MX> </PLoanTypMtdList> </msgbody>
	 * 
	 * @return xml字符串
	 * @throws TransformerConfigurationException
	 * @throws ParserConfigurationException
	 */
	public String createAddXml(ReqLoanChannelVO reqLoanBankVO) {

		
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
		serviceId.setTextContent(reqLoanBankVO.getServiceId());
		// <serviceId>servP0000100012</serviceId>
		Element PLoanTypMtdToList = document.createElement("PLoanTypMtdList");
		if (reqLoanBankVO.getPLoanTypMtdList() == null) {
			return "";
		}
		for (ReqLoanChannelVO pLoanTypMtdList : reqLoanBankVO.getPLoanTypMtdList()) {
			Element MX = document.createElement("MX");

			PLoanTypMtdToList.appendChild(MX);

			Element DN_CHANNEL = document.createElement("DN_CHANNEL");
			DN_CHANNEL.setTextContent(pLoanTypMtdList.getDN_CHANNEL());

			Element TNR_OPT = document.createElement("TNR_OPT");
			TNR_OPT.setTextContent(String.valueOf(pLoanTypMtdList.getTNR_OPT()));

			Element LOAN_RATE_MODE = document.createElement("LOAN_RATE_MODE");
			LOAN_RATE_MODE.setTextContent(pLoanTypMtdList.getLOAN_RATE_MODE());

			Element LOAN_BASE_RATE = document.createElement("LOAN_BASE_RATE");
			LOAN_BASE_RATE.setTextContent(String.valueOf(pLoanTypMtdList.getLOAN_BASE_RATE()));

			Element INT_ADJ_PCT = document.createElement("INT_ADJ_PCT");
			INT_ADJ_PCT.setTextContent(pLoanTypMtdList.getINT_ADJ_PCT());

			Element RATE_TYPE = document.createElement("RATE_TYPE");
			RATE_TYPE.setTextContent(pLoanTypMtdList.getRATE_TYPE());

			Element INT_RAT = document.createElement("INT_RAT");
			INT_RAT.setTextContent(String.valueOf(pLoanTypMtdList.getINT_RAT()));

			Element OD_INT_RATE = document.createElement("OD_INT_RATE");
			OD_INT_RATE.setTextContent(String.valueOf(pLoanTypMtdList.getOD_INT_RATE()));

			Element LEND_CHA_NAM = document.createElement("LEND_CHA_NAM");
			LEND_CHA_NAM.setTextContent(pLoanTypMtdList.getLEND_CHA_NAM());

			Element OPT_TYP = document.createElement("OPT_TYP");
			OPT_TYP.setTextContent(pLoanTypMtdList.getOPT_TYP());

			Element LAST_CHG_USR = document.createElement("LAST_CHG_USR");
			LAST_CHG_USR.setTextContent(pLoanTypMtdList.getLAST_CHG_USR());

			Element LAST_CHG_DT = document.createElement("LAST_CHG_DT");
			LAST_CHG_DT.setTextContent(pLoanTypMtdList.getLAST_CHG_DT());

			Element BEL_TYPE = document.createElement("BEL_TYPE");
			BEL_TYPE.setTextContent(pLoanTypMtdList.getBEL_TYPE());

			Element TOTTERM_X = document.createElement("TOTTERM_X");
			TOTTERM_X.setTextContent(pLoanTypMtdList.getTOTTERM_X());

			Element XQ_RATE = document.createElement("XQ_RATE");
			XQ_RATE.setTextContent(pLoanTypMtdList.getXQ_RATE());
			MX.appendChild(DN_CHANNEL);
			MX.appendChild(TNR_OPT);
			MX.appendChild(LOAN_RATE_MODE);
			MX.appendChild(LOAN_BASE_RATE);
			MX.appendChild(INT_ADJ_PCT);
			MX.appendChild(RATE_TYPE);
			MX.appendChild(INT_RAT);
			MX.appendChild(OD_INT_RATE);
			MX.appendChild(LEND_CHA_NAM);
			MX.appendChild(OPT_TYP);
			MX.appendChild(LAST_CHG_USR);
			MX.appendChild(LAST_CHG_DT);
			MX.appendChild(BEL_TYPE);
			MX.appendChild(TOTTERM_X);
			MX.appendChild(XQ_RATE);

		}
		root.appendChild(serviceId);
		root.appendChild(PLoanTypMtdToList);

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
	 * 放款渠道<?xml version="1.0" encoding="gb2312"
	 * ?><msgall><errorCode>00000</errorCode><errorMsg>success</errorMsg></msgall>
	 *
	 * @param xmlStr
	 *            xml 字符串
	 * @return 序列化对象
	 */
	public RespLoanChannelVO xml2BeanFromAdd(String xmlStr) {
		if (TextUtils.isEmpty(xmlStr)) {
			return null;
		}
		RespLoanChannelVO respLoanBankVO = null;
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
			respLoanBankVO = new RespLoanChannelVO(errorCode, errorMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respLoanBankVO;
	}

	public static void main(String[] args) {
		LoanChannelXmlHelper LoanBankXmlHelper = new LoanChannelXmlHelper();
		ReqLoanChannelVO ReqLoanBankVO = new ReqLoanChannelVO();
		List<ReqLoanChannelVO> list = new ArrayList<ReqLoanChannelVO>();
		ReqLoanBankVO.setServiceId("servP0000100011");
		ReqLoanBankVO.setDN_CHANNEL("FOTIC");
		ReqLoanBankVO.setTNR_OPT(22);
		ReqLoanBankVO.setLOAN_RATE_MODE("FX");
		ReqLoanBankVO.setLOAN_BASE_RATE(0.0650000);
		ReqLoanBankVO.setINT_ADJ_PCT("");
		ReqLoanBankVO.setRATE_TYPE("U");
		ReqLoanBankVO.setINT_RAT(0.0587250);
		ReqLoanBankVO.setOD_INT_RATE(0.2400000);
		ReqLoanBankVO.setLEND_CHA_NAM("银行测试2");
		ReqLoanBankVO.setOPT_TYP("INSEAT");
		ReqLoanBankVO.setLAST_CHG_USR("admin");
		ReqLoanBankVO.setLAST_CHG_DT("2017-06-28 17:28:12");
		ReqLoanBankVO.setBEL_TYPE("07");
		ReqLoanBankVO.setTOTTERM_X("X");
		ReqLoanBankVO.setXQ_RATE("");
		list.add(ReqLoanBankVO);
		ReqLoanBankVO.setPLoanTypMtdList(list);
		String xmlstr = LoanBankXmlHelper.createAddXml(ReqLoanBankVO);
		System.out.println(xmlstr);
	}
}
