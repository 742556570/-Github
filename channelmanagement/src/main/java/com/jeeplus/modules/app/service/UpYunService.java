package com.jeeplus.modules.app.service;

import java.io.File;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.modules.app.api.upyun.FormUploader;
import com.jeeplus.modules.app.api.upyun.Params;
import com.jeeplus.modules.app.api.upyun.PropertiesUtil;
import com.jeeplus.modules.app.api.upyun.Result;
import com.jeeplus.modules.app.api.upyun.UpyunResultBean;
import com.jeeplus.modules.app.api.upyun.XmlHelper;
import com.jeeplus.modules.app.api.upyun.request.ImgCloudConstant;
import com.jeeplus.modules.app.api.upyun.response.ImgCloudResultBean;
import com.jeeplus.modules.app.utils.HttpConUtils;

import net.sf.json.JSONObject;

@Service
@Transactional(readOnly = false)
public class UpYunService {
	private static final String BUCKET_NAME = PropertiesUtil.getString("BUCKET_NAME");
	private static final String OPERATOR_NAME = PropertiesUtil.getString("OPERATOR_NAME");
	private static final String OPERATOR_PWD = PropertiesUtil.getString("OPERATOR_PWD");

	/**
	 * 上传又拍云服务器
	 * 
	 * @param 
	 * caseNo 流水号 ，
	 * type 1.身份证 2.face++ 
	 * file 图片文件
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws SignatureException
	 * @throws InvalidKeyException
	 */
	public Result UploadFile(String caseNo, int type, File file)
			throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
		XmlHelper xmlHelper = new XmlHelper();
		UpyunResultBean upyunResultBean = new UpyunResultBean();
		// 保存路径 必须设置该参数
		String savePath = "/wangdai/{year}/{mon}/{day}/"+caseNo+"/{random32}{.suffix}";
		final Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put(Params.SAVE_KEY, savePath);

		FormUploader uploader = new FormUploader(BUCKET_NAME, OPERATOR_NAME, OPERATOR_PWD);
		Result result = uploader.upload(paramsMap, file);
		if (result.getCode() == 200) {
			String msg = result.getMsg();
			System.out.println("又拍云返回参数：" + result.toString());
			JSONObject json = JSONObject.fromObject(msg);
			upyunResultBean.setImagetype(json.get("image-type").toString());
			upyunResultBean.setImageframes(Integer.parseInt(json.get("image-frames").toString()));
			upyunResultBean.setImageheight(Integer.parseInt(json.get("image-height").toString()));
			upyunResultBean.setCode(Integer.parseInt(json.get("code").toString()));
			upyunResultBean.setFile_size(Integer.parseInt(json.get("file_size").toString()));
			upyunResultBean.setImagewidth(Integer.parseInt(json.get("image-width").toString()));
			upyunResultBean.setUrl(json.get("url").toString());
			upyunResultBean.setTime(Long.parseLong(json.get("time").toString()));
			upyunResultBean.setMessage(json.get("message").toString());
			upyunResultBean.setMimetype(json.get("mimetype").toString());
			String imgTypeName = "";
			String imgTypeId = "";
			String xmlFile = "";
			if (type == 1) {
				 imgTypeId= ImgCloudConstant.ImgDic.ID_IDCARD;
				 imgTypeName = ImgCloudConstant.ImgDic.NAME_IDCARD;
				xmlFile = xmlHelper.createAddToXml(caseNo, imgTypeName, imgTypeId, upyunResultBean);
			} else if (type == 2) {
				 imgTypeId= ImgCloudConstant.ImgDic.ID_FACE_PP;
				 imgTypeName = ImgCloudConstant.ImgDic.NAME_FACE_PP;
				xmlFile = xmlHelper.createAddToXml(caseNo, imgTypeName, imgTypeId, upyunResultBean);
			}
			ImgCloudResultBean imgCloudResultBean = this.ImageHttp(xmlFile);
			if (Integer.parseInt(imgCloudResultBean.getHANDLECODE()) == 1) {
				result.setCode(Integer.parseInt(imgCloudResultBean.getHANDLECODE()));
				result.setMsg(json.get("url").toString());
				return result;
			}
			result.setCode(402);
			result.setMsg("上传云影像服务器失败！请重新上传");
			return result;
		}
		result.setCode(400);
		result.setMsg("上传又拍云服务器失败！请重新上传");
		return result;
	}
	
	
	public Result UploadFile(String caseNo, int type, File file,String path)
			throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
		XmlHelper xmlHelper = new XmlHelper();
		UpyunResultBean upyunResultBean = new UpyunResultBean();
		// 保存路径 必须设置该参数
		String savePath = "/wangdai/{year}/{mon}/{day}/"+caseNo+"/{random32}{.suffix}";
		final Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put(Params.SAVE_KEY, savePath);

		FormUploader uploader = new FormUploader(BUCKET_NAME, OPERATOR_NAME, OPERATOR_PWD);
		Result result = uploader.upload(paramsMap, file);
		if (result.getCode() == 200) {
			String msg = result.getMsg();
			System.out.println("又拍云返回参数：" + result.toString());
			JSONObject json = JSONObject.fromObject(msg);
			upyunResultBean.setImagetype(json.get("image-type").toString());
			upyunResultBean.setImageframes(Integer.parseInt(json.get("image-frames").toString()));
			upyunResultBean.setImageheight(Integer.parseInt(json.get("image-height").toString()));
			upyunResultBean.setCode(Integer.parseInt(json.get("code").toString()));
			upyunResultBean.setFile_size(Integer.parseInt(json.get("file_size").toString()));
			upyunResultBean.setImagewidth(Integer.parseInt(json.get("image-width").toString()));
			upyunResultBean.setUrl(json.get("url").toString());
			upyunResultBean.setTime(Long.parseLong(json.get("time").toString()));
			upyunResultBean.setMessage(json.get("message").toString());
			upyunResultBean.setMimetype(json.get("mimetype").toString());
			String imgTypeName = "";
			String imgTypeId = "";
			String xmlFile = "";
			if (type == 1) {
				 imgTypeId= ImgCloudConstant.ImgDic.ID_IDCARD;
				 imgTypeName = ImgCloudConstant.ImgDic.NAME_IDCARD;
				xmlFile = xmlHelper.createAddToXml(caseNo, imgTypeName, imgTypeId, upyunResultBean);
			} else if (type == 2) {
				 imgTypeId= ImgCloudConstant.ImgDic.ID_FACE_PP;
				 imgTypeName = ImgCloudConstant.ImgDic.NAME_FACE_PP;
				xmlFile = xmlHelper.createAddToXml(caseNo, imgTypeName, imgTypeId, upyunResultBean);
			}
			ImgCloudResultBean imgCloudResultBean = this.ImageHttp(xmlFile);
			if (Integer.parseInt(imgCloudResultBean.getHANDLECODE()) == 1) {
				result.setCode(Integer.parseInt(imgCloudResultBean.getHANDLECODE()));
				result.setMsg(json.get("url").toString());
				return result;
			}
			result.setCode(402);
			result.setMsg("上传云影像服务器失败！请重新上传");
			return result;
		}
		result.setCode(400);
		result.setMsg("上传又拍云服务器失败！请重新上传");
		return result;
	}
	
	
	/**
	 * 上传又拍云服务器将pdf
	 * 
	 * @param 
	 * caseNo 流水号 ，
	 * type 1.身份证 2.face++ 
	 * file 图片文件
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws SignatureException
	 * @throws InvalidKeyException
	 */
	public Result UploadFileByPDF(String caseNo, int type, File file)
			throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
		XmlHelper xmlHelper = new XmlHelper();
		UpyunResultBean upyunResultBean = new UpyunResultBean();
		// 保存路径 必须设置该参数
		String savePath = "/wangdai/{year}/{mon}/{day}/"+caseNo+"/{random32}{.suffix}";
		final Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put(Params.SAVE_KEY, savePath);

		FormUploader uploader = new FormUploader(BUCKET_NAME, OPERATOR_NAME, OPERATOR_PWD);
		Result result = uploader.upload(paramsMap, file);
		if (result.getCode() == 200) {
			String msg = result.getMsg();
			System.out.println("又拍云返回参数：" + result.toString());
			JSONObject json = JSONObject.fromObject(msg);
			upyunResultBean.setCode(Integer.parseInt(json.get("code").toString()));
			upyunResultBean.setFile_size(Integer.parseInt(json.get("file_size").toString()));
			upyunResultBean.setUrl(json.get("url").toString());
			upyunResultBean.setTime(Long.parseLong(json.get("time").toString()));
			upyunResultBean.setMessage(json.get("message").toString());
			upyunResultBean.setMimetype(json.get("mimetype").toString());
			String imgTypeName = "";
			String imgTypeId = "";
			String xmlFile = "";
			if (type == 1) {
				 imgTypeId= ImgCloudConstant.ImgDic.ID_IDCARD;
				 imgTypeName = ImgCloudConstant.ImgDic.NAME_IDCARD;
				xmlFile = xmlHelper.createAddToXmlForPDF(caseNo, imgTypeName, imgTypeId, upyunResultBean);
			} else if (type == 2) {
				 imgTypeId= ImgCloudConstant.ImgDic.ID_FACE_PP;
				 imgTypeName = ImgCloudConstant.ImgDic.NAME_FACE_PP;
				xmlFile = xmlHelper.createAddToXmlForPDF(caseNo, imgTypeName, imgTypeId, upyunResultBean);
			}if (type == 3) {//数据证书 
				 imgTypeId= ImgCloudConstant.ImgDic.ID_OTHER_CERTIFICATES;
				 imgTypeName= ImgCloudConstant.ImgDic.NAME_OTHER_CERTIFICATES;
				xmlFile = xmlHelper.createAddToXmlForPDF(caseNo, imgTypeName, imgTypeId, upyunResultBean);
			}else if (type == 4) {//征信查询授权书
				 imgTypeId= ImgCloudConstant.ImgDic.ID_CREDIT_INVESTIGATION;
				 imgTypeName = ImgCloudConstant.ImgDic.ID_CREDIT_INVESTIGATION;
				xmlFile = xmlHelper.createAddToXmlForPDF(caseNo, imgTypeName, imgTypeId, upyunResultBean);
			}else if (type == 5) {//投保单 
				 imgTypeId= ImgCloudConstant.ImgDic.ID_INSURANCE;
				 imgTypeName = ImgCloudConstant.ImgDic.ID_INSURANCE;
				xmlFile = xmlHelper.createAddToXmlForPDF(caseNo, imgTypeName, imgTypeId, upyunResultBean);
			}else if (type == 6) {//保单 
				imgTypeId = ImgCloudConstant.ImgDic.ID_POLICY;
				imgTypeName = ImgCloudConstant.ImgDic.ID_POLICY;
				xmlFile = xmlHelper.createAddToXmlForPDF(caseNo, imgTypeName, imgTypeId, upyunResultBean);
			}else if (type == 7) {//合同类
				 imgTypeId= ImgCloudConstant.ImgDic.ID_CONTRACT;
				 imgTypeName = ImgCloudConstant.ImgDic.ID_CONTRACT;
				xmlFile = xmlHelper.createAddToXmlForPDF(caseNo, imgTypeName, imgTypeId, upyunResultBean);
			}else if (type == 8) {//代扣授权委托书
				 imgTypeId= ImgCloudConstant.ImgDic.ID_WITHHOLD;
				 imgTypeName = ImgCloudConstant.ImgDic.ID_WITHHOLD;
				xmlFile = xmlHelper.createAddToXmlForPDF(caseNo, imgTypeName, imgTypeId, upyunResultBean);
			}else if (type == 9) {//代扣授权委托书
				 imgTypeId= ImgCloudConstant.ImgDic.ID_WITNESSES;
				 imgTypeName = ImgCloudConstant.ImgDic.NAME_WITNESSES;
				xmlFile = xmlHelper.createAddToXmlForPDF(caseNo, imgTypeName, imgTypeId, upyunResultBean);
			}else if (type == 10) {//代扣授权委托书
				 imgTypeId= ImgCloudConstant.ImgDic.ID_OTHER_CERTIFICATES;
				 imgTypeName = ImgCloudConstant.ImgDic.NAME_OTHER_CERTIFICATES;
				xmlFile = xmlHelper.createAddToXmlForPDF(caseNo, imgTypeName, imgTypeId, upyunResultBean);
			}else if (type == 11) {//个人信息查询
				 imgTypeId= ImgCloudConstant.ImgDic.ID_PERSONQUERY;
				 imgTypeName = ImgCloudConstant.ImgDic.NAME_PERSONQUERY;
				xmlFile = xmlHelper.createAddToXmlForPDF(caseNo, imgTypeName, imgTypeId, upyunResultBean);
			}
			if(xmlFile!="") {
				ImgCloudResultBean imgCloudResultBean = this.ImageHttp(xmlFile);
				if (Integer.parseInt(imgCloudResultBean.getHANDLECODE()) == 1) {
					result.setCode(Integer.parseInt(imgCloudResultBean.getHANDLECODE()));
					result.setMsg(json.get("url").toString());
					return result;
				}
			}
			result.setCode(402);
			result.setMsg("上传云影像服务器失败！请重新上传");
			return result;
		}
		result.setCode(400);
		result.setMsg("上传又拍云服务器失败！请重新上传");
		return result;
	}
	
	
	
	public Result UploadFileByPDF(String caseNo, int type, File file,String path)
			throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
		XmlHelper xmlHelper = new XmlHelper();
		UpyunResultBean upyunResultBean = new UpyunResultBean();
		// 保存路径 必须设置该参数
		String savePath = "/wangdai/{year}/{mon}/{day}/"+caseNo+"/{random32}{.suffix}";
		final Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put(Params.SAVE_KEY, savePath);

		FormUploader uploader = new FormUploader(BUCKET_NAME, OPERATOR_NAME, OPERATOR_PWD);
		Result result = uploader.upload(paramsMap, file);
		if (result.getCode() == 200) {
			String msg = result.getMsg();
			System.out.println("又拍云返回参数：" + result.toString());
			JSONObject json = JSONObject.fromObject(msg);
			upyunResultBean.setCode(Integer.parseInt(json.get("code").toString()));
			upyunResultBean.setFile_size(Integer.parseInt(json.get("file_size").toString()));
			upyunResultBean.setUrl(json.get("url").toString());
			upyunResultBean.setTime(Long.parseLong(json.get("time").toString()));
			upyunResultBean.setMessage(json.get("message").toString());
			upyunResultBean.setMimetype(json.get("mimetype").toString());
			String imgTypeName = "";
			String imgTypeId = "";
			String xmlFile = "";
			if (type == 1) {
				 imgTypeId= ImgCloudConstant.ImgDic.ID_IDCARD;
				 imgTypeName = ImgCloudConstant.ImgDic.NAME_IDCARD;
				xmlFile = xmlHelper.createAddToXmlForPDF(caseNo, imgTypeName, imgTypeId, upyunResultBean);
			} else if (type == 2) {
				 imgTypeId= ImgCloudConstant.ImgDic.ID_FACE_PP;
				 imgTypeName = ImgCloudConstant.ImgDic.NAME_FACE_PP;
				xmlFile = xmlHelper.createAddToXmlForPDF(caseNo, imgTypeName, imgTypeId, upyunResultBean);
			}if (type == 3) {//数据证书 
				 imgTypeId= ImgCloudConstant.ImgDic.ID_OTHER_CERTIFICATES;
				 imgTypeName= ImgCloudConstant.ImgDic.NAME_OTHER_CERTIFICATES;
				xmlFile = xmlHelper.createAddToXmlForPDF(caseNo, imgTypeName, imgTypeId, upyunResultBean);
			}else if (type == 4) {//征信查询授权书
				 imgTypeId= ImgCloudConstant.ImgDic.ID_CREDIT_INVESTIGATION;
				 imgTypeName = ImgCloudConstant.ImgDic.ID_CREDIT_INVESTIGATION;
				xmlFile = xmlHelper.createAddToXmlForPDF(caseNo, imgTypeName, imgTypeId, upyunResultBean);
			}else if (type == 5) {//投保单 
				 imgTypeId= ImgCloudConstant.ImgDic.ID_INSURANCE;
				 imgTypeName = ImgCloudConstant.ImgDic.ID_INSURANCE;
				xmlFile = xmlHelper.createAddToXmlForPDF(caseNo, imgTypeName, imgTypeId, upyunResultBean);
			}else if (type == 6) {//保单 
				imgTypeId = ImgCloudConstant.ImgDic.ID_POLICY;
				imgTypeName = ImgCloudConstant.ImgDic.ID_POLICY;
				xmlFile = xmlHelper.createAddToXmlForPDF(caseNo, imgTypeName, imgTypeId, upyunResultBean);
			}else if (type == 7) {//合同类
				 imgTypeId= ImgCloudConstant.ImgDic.ID_CONTRACT;
				 imgTypeName = ImgCloudConstant.ImgDic.ID_CONTRACT;
				xmlFile = xmlHelper.createAddToXmlForPDF(caseNo, imgTypeName, imgTypeId, upyunResultBean);
			}else if (type == 8) {//代扣授权委托书
				 imgTypeId= ImgCloudConstant.ImgDic.ID_WITHHOLD;
				 imgTypeName = ImgCloudConstant.ImgDic.ID_WITHHOLD;
				xmlFile = xmlHelper.createAddToXmlForPDF(caseNo, imgTypeName, imgTypeId, upyunResultBean);
			}else if (type == 9) {//代扣授权委托书
				 imgTypeId= ImgCloudConstant.ImgDic.ID_WITNESSES;
				 imgTypeName = ImgCloudConstant.ImgDic.NAME_WITNESSES;
				xmlFile = xmlHelper.createAddToXmlForPDF(caseNo, imgTypeName, imgTypeId, upyunResultBean);
			}else if (type == 10) {//代扣授权委托书
				 imgTypeId= ImgCloudConstant.ImgDic.ID_OTHER_CERTIFICATES;
				 imgTypeName = ImgCloudConstant.ImgDic.NAME_OTHER_CERTIFICATES;
				xmlFile = xmlHelper.createAddToXmlForPDF(caseNo, imgTypeName, imgTypeId, upyunResultBean);
			}else if (type == 11) {//个人信息查询
				 imgTypeId= ImgCloudConstant.ImgDic.ID_PERSONQUERY;
				 imgTypeName = ImgCloudConstant.ImgDic.NAME_PERSONQUERY;
				xmlFile = xmlHelper.createAddToXmlForPDF(caseNo, imgTypeName, imgTypeId, upyunResultBean);
			}
			if(xmlFile!="") {
				ImgCloudResultBean imgCloudResultBean = this.ImageHttp(xmlFile);
				if (Integer.parseInt(imgCloudResultBean.getHANDLECODE()) == 1) {
					result.setCode(Integer.parseInt(imgCloudResultBean.getHANDLECODE()));
					result.setMsg(json.get("url").toString());
					return result;
				}
			}
			result.setCode(402);
			result.setMsg("上传云影像服务器失败！请重新上传");
			return result;
		}
		result.setCode(400);
		result.setMsg("上传又拍云服务器失败！请重新上传");
		return result;
	}
	
	
	
	/**
	 * 云影像传递本地地址
	 * 
	 * @param xmlFile
	 * @return
	 */
	public static ImgCloudResultBean ImageHttp(String xmlFile) {
		HttpConUtils httpConUtils = new HttpConUtils();
		XmlHelper xmlHelper = new XmlHelper();
		String apiUrl =  ImgCloudConstant.ADD_API;
		String resultXml = httpConUtils.doPostSSLXML(apiUrl, xmlFile);
		ImgCloudResultBean imgCloudResultBean = xmlHelper.xml2BeanFromAdd(resultXml);
		return imgCloudResultBean;
	}
	
}
