package com.jeeplus.modules.app.api.withholdrecover;

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
import com.jeeplus.modules.app.api.withholdrecover.request.ReqWithholdRecoverlVO;
import com.jeeplus.modules.app.api.withholdrecover.response.RespWithholdRecoverlVO;


/**
 * 阳光保险 
 * 代扣追偿
 * 
 **/

public class WithholdRecoverXmlHelper {
	/**
	 * 贷款设备区部分试算
	 * 
	 * @return xml字符串
	 * @throws TransformerConfigurationException
	 * @throws ParserConfigurationException
	 */
	public String createAddXml(ReqWithholdRecoverlVO reqWithholdRecoverlVO) {

		
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

		Element serviceId = document.createElement("serviceId");
		serviceId.setTextContent(reqWithholdRecoverlVO.getServiceId());

		Element BEL_TYPE = document.createElement("BEL_TYPE");
		BEL_TYPE.setTextContent(reqWithholdRecoverlVO.getBEL_TYPE());
		
		Element QUERY_DT = document.createElement("QUERY_DT");
		QUERY_DT.setTextContent(reqWithholdRecoverlVO.getQUERY_DT());
	
		Element QUERY_TYPE = document.createElement("QUERY_TYPE");
		QUERY_TYPE.setTextContent(reqWithholdRecoverlVO.getQUERY_TYPE());
		
		root.appendChild(serviceId);
		root.appendChild(BEL_TYPE);
		root.appendChild(QUERY_DT);
		root.appendChild(QUERY_TYPE);

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
	 * 核算代扣追偿返回处理成bean
	 * @param xmlStr
	 *            xml 字符串
	 * @return 序列化对象
	 */
	public RespWithholdRecoverlVO xml2BeanFromAdd(String xmlStr) {
		if (TextUtils.isEmpty(xmlStr)) {
			return null;
		}
		RespWithholdRecoverlVO respWithholdRecoverlVO = new RespWithholdRecoverlVO();

		List<RespWithholdRecoverlVO> policeList = new ArrayList<RespWithholdRecoverlVO>();
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			StringReader sr = new StringReader(xmlStr);
			InputSource inputSource = new InputSource(sr);
			Document document = documentBuilder.parse(inputSource);
			Element rootElement = document.getDocumentElement();
			String errorCode = rootElement.getElementsByTagName("errorCode").item(0).getFirstChild().getNodeValue();
			String errorMsg = rootElement.getElementsByTagName("errorMsg").item(0).getFirstChild().getNodeValue();
			String policeCount = rootElement.getElementsByTagName("policeCount").item(0).getFirstChild().getNodeValue();
			String sendTime = rootElement.getElementsByTagName("sendTime").item(0).getFirstChild().getNodeValue();
			NodeList childNodes = rootElement.getChildNodes();  //根节点下所有子节点
			 for (int i = 0; i < childNodes.getLength(); i++) {  //循环第一层子节点
                Node childNode = childNodes.item(i);  
                if(childNode.getNodeName().equals("policeList")) {
                  NodeList childNodes_2 = childNode.getChildNodes();  
                  for(int j = 0;j < childNodes_2.getLength();j++){   //循环第二层子节点
                	  String name=childNode.getFirstChild().getNodeValue();
                	  RespWithholdRecoverlVO RespWithholdRecoverlVO = new RespWithholdRecoverlVO();
    
                      String policyNo = rootElement.getElementsByTagName("policyNo").item(j).getFirstChild().getNodeValue();
                      RespWithholdRecoverlVO.setPolicyNo(policyNo);
                    
                      String bankCode = rootElement.getElementsByTagName("bankCode").item(j).getFirstChild().getNodeValue();
                      RespWithholdRecoverlVO.setBankCode(bankCode);;
                      
                      String checkDate = rootElement.getElementsByTagName("checkDate").item(j).getFirstChild().getNodeValue();
                      RespWithholdRecoverlVO.setCheckDate(checkDate);;
                      
                      String planFee = rootElement.getElementsByTagName("planFee").item(j).getFirstChild().getNodeValue();
                      RespWithholdRecoverlVO.setPlanFee(planFee);
                      
                      String capital = rootElement.getElementsByTagName("capital").item(j).getFirstChild().getNodeValue();
                      RespWithholdRecoverlVO.setCapital(capital);
                      
                      String intamt = rootElement.getElementsByTagName("intamt").item(j).getFirstChild().getNodeValue();
                      RespWithholdRecoverlVO.setIntamt(intamt);
                      
                      String premium = rootElement.getElementsByTagName("premium").item(j).getFirstChild().getNodeValue();
                      RespWithholdRecoverlVO.setPremium(premium);
                      
                      String penalsum = rootElement.getElementsByTagName("penalsum").item(j).getFirstChild().getNodeValue();
                      RespWithholdRecoverlVO.setPenalsum(penalsum);
                      
                      String setlInd = rootElement.getElementsByTagName("setlInd").item(j).getFirstChild().getNodeValue();
                      RespWithholdRecoverlVO.setSetlInd(setlInd);

                      policeList.add(RespWithholdRecoverlVO);
                  	}
                  }
                
           }
			respWithholdRecoverlVO.setErrorCode(errorCode);
			respWithholdRecoverlVO.setErrorMessage(errorMsg);
			respWithholdRecoverlVO.setPoliceCount(policeCount);
			respWithholdRecoverlVO.setSendTime(sendTime);
			respWithholdRecoverlVO.setPoliceList(policeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respWithholdRecoverlVO;
	}

/*	public static void main(String[] args) {
		WithholdRecoverXmlHelper WithholdRecoverXmlHelper = new WithholdRecoverXmlHelper();
		//ReqWithholdRecoverlVO ReqWithholdRecoverlVO = new ReqWithholdRecoverlVO();
		//String  xmlstr = WithholdRecoverXmlHelper.createAddXml(ReqWithholdRecoverlVO);
		String xmlStr ="<?xml version=\"1.0\" encoding=\"gb2312\" ?><msgall><errorCode>00000</errorCode><errorMsg>success</errorMsg><sendTime>2018-03-09 09:19:03</sendTime><policeCount>1</policeCount><policeList><MX><policyNo>C70100030000534962</policyNo><bankCode>ZYXF</bankCode><checkDate>2023-12-12</checkDate><planFee>7167.66</planFee><capital>6716.17</capital><intamt>151.49</intamt><premium>0.0</premium><penalsum>0.0</penalsum></MX></policeList></msgall>";
		RespWithholdRecoverlVO RespWithholdRecoverlVO = WithholdRecoverXmlHelper.xml2BeanFromAdd(xmlStr);
		System.out.println(RespWithholdRecoverlVO.toString());
	}*/
}
