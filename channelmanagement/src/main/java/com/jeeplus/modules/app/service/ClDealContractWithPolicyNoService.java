package com.jeeplus.modules.app.service;

import java.io.File;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.app.api.upyun.PropertiesUtil;
import com.jeeplus.modules.app.api.upyun.Result;
import com.jeeplus.modules.app.api.upyun.request.ImgCloudConstant;
import com.jeeplus.modules.app.dao.ClCrdtExtDao;
import com.jeeplus.modules.app.dao.ClIdCardInfoDao;
import com.jeeplus.modules.app.dao.ClUsrApplyDao;
import com.jeeplus.modules.app.dao.ClUsrContractDao;
import com.jeeplus.modules.app.entity.ClCrdtExt;
import com.jeeplus.modules.app.entity.ClIdCardInfo;
import com.jeeplus.modules.app.entity.ClUsrApply;
import com.jeeplus.modules.app.entity.ClUsrContract;
import com.jeeplus.modules.app.utils.FileTool;

@Service
@Transactional(readOnly = false)
public class ClDealContractWithPolicyNoService {
	
	private final static Logger logger = LoggerFactory.getLogger(ClDealContractWithPolicyNoService.class);
	
	@Autowired
	private ClUsrContractDao contractDao;
	
	@Autowired
	private ClIdCardInfoDao idCardInfoDao;
	
	@Autowired
	private ClUsrApplyDao applyDao;
	
	@Autowired
	private ClCrdtExtDao crdtDao;
	
	
	public boolean dealContractWithPolicyNo(String policyNo,String usrCde) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException  {
		return dealContractWithPolicyNo(policyNo);
	}
	
	
	public boolean dealContractWithPolicyNo(String policyNo) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException  {
		
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
	    Date date=new Date(); 
	    UpYunService upYunService = new UpYunService();
		
	    
	    String time = "";
	    String usrCde = "";
	    if(policyNo.endsWith("W")) {
	    	ClUsrApply apply = applyDao.getByPolicyNo(policyNo);
	    	if(apply == null) {
	    		logger.info(policyNo + ",未找到相应的保单记录");
	    		return false;
	    	}
	    	time = apply.getCrtDt().substring(0, 10);
	    	usrCde =  apply.getUsrCde();
	    	
	    }else {
	    	ClCrdtExt crdtExt = crdtDao.getClCrdtExtByPolicyNo(policyNo);
	    	if(crdtExt == null) {
	    		logger.info(policyNo + ",未找到相应的保单记录");
	    		return false;
	    	}
	    	time = crdtExt.getCRT_DT().substring(0, 10);
	    	usrCde =  crdtExt.getUSR_CDE();
	    }
		
		
		Map<String, String> params = new HashMap<String,String>();
		params.put("USR_CDE", usrCde);
		params.put("STEP", "3-3");
		params.put("POLICY_NO", null);
		params.put("CRT_DT", "%"+time+"%");
		
		logger.info(policyNo + ",获取合同参数:"+params);
		ClUsrContract contract = contractDao.getLatestContractByUsrAndStep(params);
		if(contract == null) {
			params.put("USR_CDE", usrCde);
			params.put("STEP", "3-3");
			params.put("POLICY_NO", null);
			params.put("CRT_DT", null);
			contract = contractDao.getLatestContractByUsrAndStep(params);
			if(contract == null) {
				logger.info(policyNo + ",获取合同结果:未找到相应合同");
				return false;
			}
			time = DateUtils.formatDate(date, "yyyy-MM-dd");

		}
		logger.info(policyNo + ",获取合同结果:"+contract);
		ClIdCardInfo idcardInfo = idCardInfoDao.getByUsrCode(usrCde);
		if(idcardInfo == null) {
			return false;
		}
		
		
		String path = getPath(time, policyNo);
		
		String docTitle = UUID.randomUUID().toString();
		
		FileTool.downloadByNIO2(contract.getDlUrl(), PropertiesUtil.getString("downloadurl")+"/"+sdf.format(date)+"/"+policyNo, docTitle+".pdf");
		int type = this.setType(contract);
		File file = new File(PropertiesUtil.getString("downloadurl")+"/"+sdf.format(date)+"/"+policyNo+"/"+docTitle+".pdf");
		Result resultpdf=upYunService.UploadFileByPDF(policyNo, type, file,path);
		
		
		File frontFile = new File(idcardInfo.getFORNT_IMGPTH_LC());
		File backFile = new File(idcardInfo.getBACK_IMGPTH_LC());
		
		Result rfront = upYunService.UploadFile(policyNo, 1, frontFile,path);
		Result rback = upYunService.UploadFile(policyNo, 1, backFile,path);
		
		if(rfront.isSucceed() && rback.isSucceed() && resultpdf.isSucceed()) {
			logger.info(policyNo + "上传成功");
			return true;
		}
		logger.info(policyNo + "上传失败");
		return false;
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
	
	private String getPath(String time,String policyNo) {
		String[] tmp = time.split("-");
		if(tmp.length == 3) {
			String path = "wangdai/"+tmp[0]+"/"+tmp[1]+"/"+tmp[2]+"/"+policyNo;
			return path;
		}else {
			String[] tt = DateUtils.formatDate(new Date(), "yyyy-MM-dd").split("-");
			String path = "wangdai/"+tt[0]+"/"+tt[1]+"/"+tt[2]+"/"+policyNo;
			return path;
		}
	}

	
	public static void main(String[] args) {
		ClDealContractWithPolicyNoService a = new ClDealContractWithPolicyNoService();
		System.out.println(a.getPath("2018-07-06", "WD102851807111623Y"));
	}
}
