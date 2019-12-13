package com.jeeplus.modules.app.api.product;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;

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

import com.jeeplus.modules.app.api.product.request.ReqProductVO;
import com.jeeplus.modules.app.api.product.response.RespProductVO;


/**
 * 阳光保险
 * xml 转换帮助类 将产品同步参数封装成xml
 */

public class ProductXmlHelper {
	/**
	 * 创建 提交云影像 格式的xml
	 * xml 格式
	 * <?xml version="1.0" encoding="GBK" standalone="yes"?>
		<msgbody>
		    <serviceId>servP0000100012</serviceId>
		    <TYP_CDE>TEST11</TYP_CDE>
		    <ACCT_ROLE_DESC>测试</ACCT_ROLE_DESC>
		    <OPT_TYP>UPDATE</OPT_TYP>
		</msgbody> 
	 * @return xml字符串
	 * @throws TransformerConfigurationException 
	 * @throws ParserConfigurationException
	 */
	public  String createAddXml(ReqProductVO reqProductVO) {
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
		serviceId.setTextContent(reqProductVO.getServiceId());
		// <serviceId>servP0000100012</serviceId>
		Element TYP_CDE = document.createElement("TYP_CDE");
		// 贷款品种代码
		TYP_CDE.setTextContent(reqProductVO.getTyp_cde());
		// <serviceId>servP0000100012</serviceId>
		Element ACCT_ROLE_DESC = document.createElement("ACCT_ROLE_DESC");
		// 核算操作类型
		ACCT_ROLE_DESC.setTextContent(reqProductVO.getAcct_role_desc());
		// <serviceId>servP0000100012</serviceId>
		Element OPT_TYP = document.createElement("OPT_TYP");
		//  新增 删除 修改
		OPT_TYP.setTextContent(reqProductVO.getOpt_typ());

		root.appendChild(serviceId);
		root.appendChild(TYP_CDE);
		root.appendChild(ACCT_ROLE_DESC);
		root.appendChild(OPT_TYP);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			// 设置输出数据时换行
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
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
	 * 同步产品信息对象 <?xml version="1.0" encoding="gb2312" ?><msgall><errorCode>00000</errorCode><errorMsg>success</errorMsg></msgall>
	 *
	 * @param xmlStr
	 *            xml 字符串
	 * @return 序列化对象
	 */
	public  RespProductVO xml2BeanFromAdd(String xmlStr) {
		if (TextUtils.isEmpty(xmlStr)) {
			return null;
		}
		RespProductVO respProductVO = null;
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
			respProductVO = new RespProductVO(errorCode, errorMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respProductVO;
	}

}
