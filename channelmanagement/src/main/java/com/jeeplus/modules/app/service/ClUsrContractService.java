package com.jeeplus.modules.app.service;

import java.io.File;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.app.api.fadada.FadadaDock;
import com.jeeplus.modules.app.api.upyun.PropertiesUtil;
import com.jeeplus.modules.app.api.upyun.Result;
import com.jeeplus.modules.app.api.upyun.request.ImgCloudConstant;
import com.jeeplus.modules.app.dao.ClUsrContractDao;
import com.jeeplus.modules.app.entity.ClUsrContract;
import com.jeeplus.modules.app.utils.FileTool;

import net.sf.json.JSONObject;

@Service
@Transactional(readOnly = false)
public class ClUsrContractService extends CrudService<ClUsrContractDao, ClUsrContract> {

	@Autowired
	private ClUsrContractDao clUsrContractDao;

	/**
	 * 法大大生成合同
	 * 
	 * @param customer_name
	 * @param id_card
	 * @param mobile
	 * @param json
	 * @param template_id
	 * @param doc_title
	 * @param type
	 * @param sign_keyword
	 * @param policy_no
	 * @param usr_cde
	 * @param step
	 * @return
	 */
	public String fadadaContract(String customer_name, String id_card, String mobile, JSONObject json,
			String template_id, String doc_title, String type, String sign_keyword, String policy_no, String usr_cde,
			String step,String sign_keywordcom ,String img_dic) {
		FadadaDock fadadaDock = new FadadaDock();
		JSONObject jsonEntrance = fadadaDock.fadadaEntrance(customer_name, id_card, mobile, json, template_id,
				doc_title, type, sign_keyword, policy_no, usr_cde, step,sign_keywordcom);
		
		setData(jsonEntrance,img_dic);
		return jsonEntrance.getString("result");
	}

	/**
	 * 设值
	 * 
	 * @param jsonEntrance
	 */
	public void setData(JSONObject jsonEntrance,String img_dic) {
		ClUsrContract clUsrContract = new ClUsrContract();
		clUsrContract.setContractCde(jsonEntrance.getString("contract_id"));
		clUsrContract.setUsrCde(jsonEntrance.getString("usr_cde"));
		clUsrContract.setPolicyNo(jsonEntrance.getString("policy_no"));
		clUsrContract.setStep(jsonEntrance.getString("step"));
		clUsrContract.setTemplateId(jsonEntrance.getString("template_id"));
		clUsrContract.setCustomerId(jsonEntrance.getString("customer_id"));
		clUsrContract.setDlUrl(jsonEntrance.getString("dl_url"));
		clUsrContract.setvUrl(jsonEntrance.getString("v_url"));
		clUsrContract.setStCde("0");//
		clUsrContract.setIsUpyun("0");//
		clUsrContract.setImgDic(img_dic);
		clUsrContract.setCrtDt(new Date());
		clUsrContractDao.insert(clUsrContract);
	}
	/**
	 * 法大大下载完模板上传到又拍云
	 * @return
	 * @throws SignatureException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	public String fadadaDownLoadToUpYun(String dt) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
	    Date date=new Date(); 
	    if(StringUtils.isNotEmpty(dt)) {
        	date = DateUtils.parseDate(dt);
        }
	    Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.add(Calendar.DAY_OF_MONTH, -1);  
        date = calendar.getTime();  
	    UpYunService upYunService = new UpYunService();
		List<ClUsrContract> contractList = clUsrContractDao.getUsrContractByDate(sdf.format(date));
		for(ClUsrContract clUsrContractList:contractList) {	
			String docTitle = UUID.randomUUID().toString();
			FileTool.downloadByNIO2(clUsrContractList.getDlUrl(), PropertiesUtil.getString("downloadurl")+"/"+sdf.format(date)+"/"+clUsrContractList.getUsrCde(), docTitle+".pdf");
			int type = this.setType(clUsrContractList);
			File file = new File(PropertiesUtil.getString("downloadurl")+"/"+sdf.format(date)+"/"+clUsrContractList.getUsrCde()+"/"+docTitle+".pdf");
			Result result=upYunService.UploadFileByPDF(clUsrContractList.getUsrCde(), type, file);
			Map<String,String> params = new HashMap<String,String>();
			params.put("upyunUrl", result.getMsg());
			params.put("contractCde", clUsrContractList.getContractCde());
			params.put("docTitle",docTitle);
			clUsrContractDao.updateByusrCde(params);
			}
		return "success";
	}
	
	
	/**
	 * 法大大下载完模板与身份证照片上传到又拍云
	 * @return
	 * @throws SignatureException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	public String fadadaDownLoadToUpYunWithIdCard(String dt) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
	    Date date=new Date(); 
	    if(StringUtils.isNotEmpty(dt)) {
        	date = DateUtils.parseDate(dt);
        }
	    Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.add(Calendar.DAY_OF_MONTH, -1);  
        date = calendar.getTime();  
	    UpYunService upYunService = new UpYunService();
		List<ClUsrContract> contractList = clUsrContractDao.getUsrContractByDate(sdf.format(date));
		for(ClUsrContract clUsrContractList:contractList) {	
			String docTitle = UUID.randomUUID().toString();
			FileTool.downloadByNIO2(clUsrContractList.getDlUrl(), PropertiesUtil.getString("downloadurl")+"/"+sdf.format(date)+"/"+clUsrContractList.getUsrCde(), docTitle+".pdf");
			int type = this.setType(clUsrContractList);
			File file = new File(PropertiesUtil.getString("downloadurl")+"/"+sdf.format(date)+"/"+clUsrContractList.getUsrCde()+"/"+docTitle+".pdf");
			Result result=upYunService.UploadFileByPDF(clUsrContractList.getUsrCde(), type, file);
			Map<String,String> params = new HashMap<String,String>();
			params.put("upyunUrl", result.getMsg());
			params.put("contractCde", clUsrContractList.getContractCde());
			params.put("docTitle",docTitle);
			clUsrContractDao.updateByusrCde(params);
			}
		return "success";
	}
	
	/**
	 * 生成6位随机数
	 * @return
	 */
	public static int random6() {
		int spy = 0;
		for (int i = 0; i <= 5; i++) { // 生成一个6位的序列号
			spy = (int) (Math.random() * 10);
		}
		return spy;
	}
	private int setType(ClUsrContract clUsrContractList){
		int type=0;
		if (ImgCloudConstant.ImgDic.ID_IDCARD.equals(clUsrContractList.getImgDic())) {
			type = 1;
		} else if (ImgCloudConstant.ImgDic.ID_FACE_PP.equals(clUsrContractList.getImgDic())) {
			type = 2;
		}else if (ImgCloudConstant.ImgDic.ID_OTHER_CERTIFICATES.equals(clUsrContractList.getImgDic())) {//数据证书 
			type= 3;
		}else if (ImgCloudConstant.ImgDic.ID_CREDIT_INVESTIGATION.equals(clUsrContractList.getImgDic())) {//征信查询授权书
			type = 4;
		}else if (ImgCloudConstant.ImgDic.ID_INSURANCE.equals(clUsrContractList.getImgDic())) {//投保单 
			type = 5;
		}else if (ImgCloudConstant.ImgDic.ID_POLICY.equals(clUsrContractList.getImgDic())) {//保单 
			type = 6;
		}else if (ImgCloudConstant.ImgDic.ID_CONTRACT.equals(clUsrContractList.getImgDic())) {//合同类
			type = 7;
		}else if (ImgCloudConstant.ImgDic.ID_WITHHOLD.equals(clUsrContractList.getImgDic())) {//代扣授权委托书
			type = 8;
		}else if (ImgCloudConstant.ImgDic.ID_WITNESSES.equals(clUsrContractList.getImgDic())) {//代扣授权委托书
			type = 9;
		}else if (ImgCloudConstant.ImgDic.ID_OTHER_CERTIFICATES.equals(clUsrContractList.getImgDic())) {//代扣授权委托书
			type = 10;
		}else if (ImgCloudConstant.ImgDic.ID_PERSONQUERY.equals(clUsrContractList.getImgDic())) {//个人信息查询授权书
			type = 11;
		}
		return type;
	}
	
	public boolean isExistByPolicyNo(String policyNo,String  templateId) {
		Map<String, String> params = new HashMap<String,String>();
		params.put("POLICY_NO", policyNo);
		params.put("TEMPLATE_ID", templateId);
		int i = clUsrContractDao.isExistByPolicyNo(params);
		if(i==0) {
			return false;
		}
		return true;
	}
}
