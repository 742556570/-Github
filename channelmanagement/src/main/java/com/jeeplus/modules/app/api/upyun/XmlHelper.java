package com.jeeplus.modules.app.api.upyun;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.http.util.TextUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import com.jeeplus.modules.app.api.upyun.request.ImgCloudConstant;
import com.jeeplus.modules.app.api.upyun.response.ImgCloudResultBean;

/**
 * 阳光保险
 *	xml 转换帮助类 将又拍云返回的路径及其他信息提交至云影像
 * 阳光保险集团外部系统与云影像系统对接-接口规范2.1.docx
 */

public class XmlHelper {
	/**
	 * 创建 提交云影像 格式的xml <?xml version="1.0" encoding="UTF-8"?> <IN> <META_DATA>
	 * <APP_CODE>CREDIT</APP_CODE> <CASE_NO>Y017121514192646</CASE_NO>
	 * <CREATE_USER/> <TREE_NODE> <TREE NAME="身份证明" ID="01"> <PAGE IMAGEURL=
	 * "/credit/1712/15/12/Y017121512000184/IDnumber1513310481401.jpg" TYPE="01"
	 * IMAGESIZE="44599" WIDTH="622" HEIGHT="406" FILENAME=
	 * "IDnumber1513310481401.jpg" CREATE_TIME="2017-12-15 14:21:03" ACTION="ADD"/>
	 * <PAGE IMAGEURL=
	 * "/credit/1712/15/12/Y017121512000184/IDnumber1513310481394.jpg" TYPE="01"
	 * IMAGESIZE="32338" WIDTH="602" HEIGHT="388" FILENAME=
	 * "IDnumber1513310481394.jpg" CREATE_TIME="2017-12-15 14:21:03" ACTION="ADD"/>
	 * </TREE> </TREE_NODE> </META_DATA> </IN>
	 *
	 * @param CASE_NO
	 *            申请流水号 CASE_NO VARCHAR(50) 申请流水号
	 * @param imgTypeName
	 *            影像类型名称 {@link ImgCloudConstant.ImgDic}
	 * @param imgTypeId
	 *            影像类型 {@link ImgCloudConstant.ImgDic}
	 * @param upyunResultBeanList
	 *            又拍云返回的数据集合
	 * @return xml字符串
	 * @throws ParserConfigurationException
	 */
	public  String createAddXml(String CASE_NO, String imgTypeName, String imgTypeId,
			List<UpyunResultBean> upyunResultBeanList) {
		if (upyunResultBeanList == null || upyunResultBeanList.size() <= 0) {
			return "";
		}
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
		Element root = document.createElement("IN");
		document.appendChild(root);
		// <META_DATA>
		Element mateData = document.createElement("META_DATA");
		// <APP_CODE>CREDIT</APP_CODE>
		Element appCode = document.createElement("APP_CODE");
		// 传入标示 APP_CODE VARCHAR(10) 传入标示（默认传CREDIT）
		appCode.setTextContent(ImgCloudConstant.DEFAULT_APP_CODE);
		mateData.appendChild(appCode);

		// <CASE_NO>Y017121514192646</CASE_NO>
		Element caseNo = document.createElement("CASE_NO");
		// 申请流水号 CASE_NO VARCHAR(50) 申请流水号
		caseNo.setTextContent(CASE_NO);
		mateData.appendChild(caseNo);

		// <CREATE_USER/>
		Element createUser = document.createElement("CREATE_USER");
		// 影像操作人 CREATE_USER VARCHAR(50) 影像上传操作人/影像删除操作人。
		// ACTION=’DELETE’ 时，传影像删除操作人。
		// ACTION=’ADD’ 时，传影像上传操作人。
		createUser.setTextContent(ImgCloudConstant.DEFAULT_CREATE_USER);
		mateData.appendChild(createUser);
		// <TREE_NODE>
		Element treeNode = document.createElement("TREE_NODE");
		// <TREE NAME="身份证明" ID="01">
		Element tree = document.createElement("TREE");
		// 影像类型名称 NAME VARCHAR(50) 影像类型名称
		tree.setAttribute("NAME", imgTypeName);
		// 影像类型id ID VARCHAR(256)
		tree.setAttribute("ID", imgTypeId);
		treeNode.appendChild(tree);
		for (UpyunResultBean upyunResultBean : upyunResultBeanList) {
			// <PAGE
			// IMAGEURL="/credit/1712/15/12/Y017121512000184/IDnumber1513310481401.jpg"
			// TYPE="01"
			// IMAGESIZE="44599" WIDTH="622" HEIGHT="406"
			// FILENAME="IDnumber1513310481401.jpg"
			// CREATE_TIME="2017-12-15 14:21:03" ACTION="ADD"/>
			Element page = document.createElement("PAGE");
			// 又拍云返回路径 IMAGEURL
			page.setAttribute("IMAGEURL", upyunResultBean.getUrl());
			// 文件类型 TYPE VARCHAR(50) 01 图片 02 文件
			page.setAttribute("TYPE", ImgCloudConstant.PAGE_TYPE_IMG);
			// 图片大小 IMAGESIZE
			page.setAttribute("IMAGESIZE", upyunResultBean.getFile_size() + "");
			// 图片宽度 WIDTH
			page.setAttribute("WIDTH", upyunResultBean.getImagewidth() + "");
			// 图片高度 HEIGHT
			page.setAttribute("HEIGHT", upyunResultBean.getImageheight() + "");
			// 影像名称 FILENAME
			String url = upyunResultBean.getUrl();
			String[] array2 = url.split("\\/");
			String fileName = array2[array2.length - 1];
			page.setAttribute("FILENAME", fileName);

			// 影像创建时间 CREATE_TIME
			String dateStr = "";
			// 2017-12-15 14:21:03
			SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date(upyunResultBean.getTime() * 1000);

			dateStr = myFmt.format(date);
			page.setAttribute("CREATE_TIME", dateStr);
			// 是否删除 ACTION ADD 新增 DELETE 删除
			page.setAttribute("ACTION", ImgCloudConstant.PAGE_ACTION_ADD);

			treeNode.appendChild(page);
		}
		mateData.appendChild(treeNode);

		root.appendChild(mateData);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
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
	 * 创建 提交云影像 格式的xml <?xml version="1.0" encoding="UTF-8"?> <IN> <META_DATA>
	 * <APP_CODE>CREDIT</APP_CODE> <CASE_NO>Y017121514192646</CASE_NO>
	 * <CREATE_USER/> <TREE_NODE> <TREE NAME="身份证明" ID="01"> <PAGE IMAGEURL=
	 * "/credit/1712/15/12/Y017121512000184/IDnumber1513310481401.jpg" TYPE="01"
	 * IMAGESIZE="44599" WIDTH="622" HEIGHT="406" FILENAME=
	 * "IDnumber1513310481401.jpg" CREATE_TIME="2017-12-15 14:21:03" ACTION="ADD"/>
	 * <PAGE IMAGEURL=
	 * "/credit/1712/15/12/Y017121512000184/IDnumber1513310481394.jpg" TYPE="01"
	 * IMAGESIZE="32338" WIDTH="602" HEIGHT="388" FILENAME=
	 * "IDnumber1513310481394.jpg" CREATE_TIME="2017-12-15 14:21:03" ACTION="ADD"/>
	 * </TREE> </TREE_NODE> </META_DATA> </IN>
	 *
	 * @param CASE_NO
	 *            申请流水号 CASE_NO VARCHAR(50) 申请流水号
	 * @param imgTypeName
	 *            影像类型名称 {@link ImgCloudConstant.ImgDic}
	 * @param imgTypeId
	 *            影像类型 {@link ImgCloudConstant.ImgDic}
	 * @param upyunResultBeanList
	 *            又拍云返回的数据集合
	 * @return xml字符串
	 * @throws ParserConfigurationException
	 */
	public String createAddToXmlForPDF(String CASE_NO, String imgTypeName, String imgTypeId,
			UpyunResultBean upyunResultBean) {
		if (upyunResultBean == null) {
			return "";
		}
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
		Element root = document.createElement("IN");
		document.appendChild(root);
		// <META_DATA>
		Element mateData = document.createElement("META_DATA");
		// <APP_CODE>CREDIT</APP_CODE>
		Element appCode = document.createElement("APP_CODE");
		// 传入标示 APP_CODE VARCHAR(10) 传入标示（默认传CREDIT）
		appCode.setTextContent(ImgCloudConstant.DEFAULT_APP_CODE);
		mateData.appendChild(appCode);

		// <CASE_NO>Y017121514192646</CASE_NO>
		Element caseNo = document.createElement("CASE_NO");
		// 申请流水号 CASE_NO VARCHAR(50) 申请流水号
		caseNo.setTextContent(CASE_NO);
		mateData.appendChild(caseNo);

		// <CREATE_USER/>
		Element createUser = document.createElement("CREATE_USER");
		// 影像操作人 CREATE_USER VARCHAR(50) 影像上传操作人/影像删除操作人。
		// ACTION=’DELETE’ 时，传影像删除操作人。
		// ACTION=’ADD’ 时，传影像上传操作人。
		createUser.setTextContent(ImgCloudConstant.DEFAULT_CREATE_USER);
		mateData.appendChild(createUser);
		// <TREE_NODE>
		Element treeNode = document.createElement("TREE_NODE");
		// <TREE NAME="身份证明" ID="01">
		Element tree = document.createElement("TREE");
		// 影像类型名称 NAME VARCHAR(50) 影像类型名称
		tree.setAttribute("NAME", imgTypeName);
		// 影像类型id ID VARCHAR(256)
		tree.setAttribute("ID", imgTypeId);
		treeNode.appendChild(tree);
		// <PAGE
		// IMAGEURL="/credit/1712/15/12/Y017121512000184/IDnumber1513310481401.jpg"
		// TYPE="01"
		// IMAGESIZE="44599" WIDTH="622" HEIGHT="406"
		// FILENAME="IDnumber1513310481401.jpg"
		// CREATE_TIME="2017-12-15 14:21:03" ACTION="ADD"/>
		Element page = document.createElement("PAGE");
		// 又拍云返回路径 IMAGEURL
		page.setAttribute("IMAGEURL", upyunResultBean.getUrl());
		// 文件类型 TYPE VARCHAR(50) 01 图片 02 文件
		page.setAttribute("TYPE", ImgCloudConstant.PAGE_TYPE_FILE);
		// 图片大小 IMAGESIZE
		page.setAttribute("IMAGESIZE", upyunResultBean.getFile_size() + "");
		// 图片宽度 WIDTH
		page.setAttribute("WIDTH", upyunResultBean.getImagewidth() + "");
		// 图片高度 HEIGHT
		page.setAttribute("HEIGHT", upyunResultBean.getImageheight() + "");
		// 影像名称 FILENAME
		String url = upyunResultBean.getUrl();
		String[] array2 = url.split("\\/");
		String fileName = array2[array2.length - 1];
		page.setAttribute("FILENAME", fileName);

		// 影像创建时间 CREATE_TIME
		String dateStr = "";
		// 2017-12-15 14:21:03
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(upyunResultBean.getTime() * 1000);

		dateStr = myFmt.format(date);
		page.setAttribute("CREATE_TIME", dateStr);
		// 是否删除 ACTION ADD 新增 DELETE 删除
		page.setAttribute("ACTION", ImgCloudConstant.PAGE_ACTION_ADD);

		tree.appendChild(page);
		mateData.appendChild(treeNode);

		root.appendChild(mateData);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
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
	 * 创建 提交云影像 格式的xml <?xml version="1.0" encoding="UTF-8"?> <IN> <META_DATA>
	 * <APP_CODE>CREDIT</APP_CODE> <CASE_NO>Y017121514192646</CASE_NO>
	 * <CREATE_USER/> <TREE_NODE> <TREE NAME="身份证明" ID="01"> <PAGE IMAGEURL=
	 * "/credit/1712/15/12/Y017121512000184/IDnumber1513310481401.jpg" TYPE="01"
	 * IMAGESIZE="44599" WIDTH="622" HEIGHT="406" FILENAME=
	 * "IDnumber1513310481401.jpg" CREATE_TIME="2017-12-15 14:21:03" ACTION="ADD"/>
	 * <PAGE IMAGEURL=
	 * "/credit/1712/15/12/Y017121512000184/IDnumber1513310481394.jpg" TYPE="01"
	 * IMAGESIZE="32338" WIDTH="602" HEIGHT="388" FILENAME=
	 * "IDnumber1513310481394.jpg" CREATE_TIME="2017-12-15 14:21:03" ACTION="ADD"/>
	 * </TREE> </TREE_NODE> </META_DATA> </IN>
	 *
	 * @param CASE_NO
	 *            申请流水号 CASE_NO VARCHAR(50) 申请流水号
	 * @param imgTypeName
	 *            影像类型名称 {@link ImgCloudConstant.ImgDic}
	 * @param imgTypeId
	 *            影像类型 {@link ImgCloudConstant.ImgDic}
	 * @param upyunResultBeanList
	 *            又拍云返回的数据集合
	 * @return xml字符串
	 * @throws ParserConfigurationException
	 */
	public String createAddToXml(String CASE_NO, String imgTypeName, String imgTypeId,
			UpyunResultBean upyunResultBean) {
		if (upyunResultBean == null) {
			return "";
		}
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
		Element root = document.createElement("IN");
		document.appendChild(root);
		// <META_DATA>
		Element mateData = document.createElement("META_DATA");
		// <APP_CODE>CREDIT</APP_CODE>
		Element appCode = document.createElement("APP_CODE");
		// 传入标示 APP_CODE VARCHAR(10) 传入标示（默认传CREDIT）
		appCode.setTextContent(ImgCloudConstant.DEFAULT_APP_CODE);
		mateData.appendChild(appCode);

		// <CASE_NO>Y017121514192646</CASE_NO>
		Element caseNo = document.createElement("CASE_NO");
		// 申请流水号 CASE_NO VARCHAR(50) 申请流水号
		caseNo.setTextContent(CASE_NO);
		mateData.appendChild(caseNo);

		// <CREATE_USER/>
		Element createUser = document.createElement("CREATE_USER");
		// 影像操作人 CREATE_USER VARCHAR(50) 影像上传操作人/影像删除操作人。
		// ACTION=’DELETE’ 时，传影像删除操作人。
		// ACTION=’ADD’ 时，传影像上传操作人。
		createUser.setTextContent(ImgCloudConstant.DEFAULT_CREATE_USER);
		mateData.appendChild(createUser);
		// <TREE_NODE>
		Element treeNode = document.createElement("TREE_NODE");
		// <TREE NAME="身份证明" ID="01">
		Element tree = document.createElement("TREE");
		// 影像类型名称 NAME VARCHAR(50) 影像类型名称
		tree.setAttribute("NAME", imgTypeName);
		// 影像类型id ID VARCHAR(256)
		tree.setAttribute("ID", imgTypeId);
		treeNode.appendChild(tree);
		// <PAGE
		// IMAGEURL="/credit/1712/15/12/Y017121512000184/IDnumber1513310481401.jpg"
		// TYPE="01"
		// IMAGESIZE="44599" WIDTH="622" HEIGHT="406"
		// FILENAME="IDnumber1513310481401.jpg"
		// CREATE_TIME="2017-12-15 14:21:03" ACTION="ADD"/>
		Element page = document.createElement("PAGE");
		// 又拍云返回路径 IMAGEURL
		page.setAttribute("IMAGEURL", upyunResultBean.getUrl());
		// 文件类型 TYPE VARCHAR(50) 01 图片 02 文件
		page.setAttribute("TYPE", ImgCloudConstant.PAGE_TYPE_IMG);
		// 图片大小 IMAGESIZE
		page.setAttribute("IMAGESIZE", upyunResultBean.getFile_size() + "");
		// 图片宽度 WIDTH
		page.setAttribute("WIDTH", upyunResultBean.getImagewidth() + "");
		// 图片高度 HEIGHT
		page.setAttribute("HEIGHT", upyunResultBean.getImageheight() + "");
		// 影像名称 FILENAME
		String url = upyunResultBean.getUrl();
		String[] array2 = url.split("\\/");
		String fileName = array2[array2.length - 1];
		page.setAttribute("FILENAME", fileName);

		// 影像创建时间 CREATE_TIME
		String dateStr = "";
		// 2017-12-15 14:21:03
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(upyunResultBean.getTime() * 1000);

		dateStr = myFmt.format(date);
		page.setAttribute("CREATE_TIME", dateStr);
		// 是否删除 ACTION ADD 新增 DELETE 删除
		page.setAttribute("ACTION", ImgCloudConstant.PAGE_ACTION_ADD);

		tree.appendChild(page);
		mateData.appendChild(treeNode);

		root.appendChild(mateData);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
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
	 * 将云影像添加接口的返回结果转化为序列号对象 <?xml version="1.0"?> -<OUT>
	 * <APPLYCODE>M20160416057904</APPLYCODE> <HANDLECODE>1/0</HANDLECODE>
	 * <ERRORMESSAGE>成功/失败</ERRORMESSAGE> </OUT>
	 *
	 * @param xmlStr
	 *            xml 字符串
	 * @return 序列化对象
	 */
	public  ImgCloudResultBean xml2BeanFromAdd(String xmlStr) {
		if (TextUtils.isEmpty(xmlStr)) {
			return null;
		}
		ImgCloudResultBean cloudResultBean = null;
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			StringReader sr = new StringReader(xmlStr);
			InputSource inputSource = new InputSource(sr);
			Document document = documentBuilder.parse(inputSource);
			Element rootElement = document.getDocumentElement();
			// <APPLYCODE>M20160416057904</APPLYCODE>
			String APPLYCODE = rootElement.getElementsByTagName("APPLYCODE").item(0).getFirstChild().getNodeValue();
			// <HANDLECODE>1/0</HANDLECODE>
			String HANDLECODE = rootElement.getElementsByTagName("HANDLECODE").item(0).getFirstChild().getNodeValue();
			// <ERRORMESSAGE>成功/失败</ERRORMESSAGE>
			String ERRORMESSAGE = rootElement.getElementsByTagName("ERRORMESSAGE").item(0).getFirstChild()
					.getNodeValue();
			cloudResultBean = new ImgCloudResultBean(APPLYCODE, HANDLECODE, ERRORMESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cloudResultBean;
	}

	;
}
