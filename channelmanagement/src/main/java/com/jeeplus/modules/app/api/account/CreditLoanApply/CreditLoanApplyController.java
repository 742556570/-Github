package com.jeeplus.modules.app.api.account.CreditLoanApply;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jeeplus.common.xstream.XStreamUtils;
import com.jeeplus.modules.app.api.account.CreditLoanApply.request.BODY;
import com.jeeplus.modules.app.api.account.CreditLoanApply.request.HEADER;
import com.jeeplus.modules.app.api.account.CreditLoanApply.request.IN;
import com.jeeplus.modules.app.api.account.CreditLoanApply.request.RECORD;
import com.jeeplus.modules.app.api.account.CreditLoanApply.response.CreditLoanApplyResp;
import com.jeeplus.modules.app.api.account.utils.JaxbUtil;
import com.jeeplus.modules.app.api.loanChannel.SynLoanChannelController;
import com.jeeplus.modules.app.api.product.PropertiesUtil;
import com.jeeplus.modules.app.dao.ClIdCardInfoDao;
import com.jeeplus.modules.app.dao.ClPrdInfoDao;
import com.jeeplus.modules.app.dao.ClUsrCntsDao;
import com.jeeplus.modules.app.dao.ClUsrDao;
import com.jeeplus.modules.app.dao.ClUsrInfoDao;
import com.jeeplus.modules.app.dao.ClUsrLoanDao;
import com.jeeplus.modules.app.dao.ClUsrLoanDetailDao;
import com.jeeplus.modules.app.entity.ClIdCardInfo;
import com.jeeplus.modules.app.entity.ClPrdInfo;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.entity.ClUsrCnts;
import com.jeeplus.modules.app.entity.ClUsrInfo;
import com.jeeplus.modules.app.entity.ClUsrLoan;
import com.jeeplus.modules.app.entity.ClUsrLoanDetail;
import com.jeeplus.modules.app.utils.HttpConUtils;
/**
 * 账务接口
 * @author 阳光保险
 *
 */
public class CreditLoanApplyController {
	private final static Logger logger = LoggerFactory.getLogger(SynLoanChannelController.class);
	@Autowired
	private ClIdCardInfoDao clIdCardInfoDao;
	@Autowired
	private ClUsrDao clUsrDao;
	@Autowired
	private ClUsrInfoDao clUsrInfoDao;
	@Autowired
	private ClUsrLoanDao clUsrLoanDao;
	@Autowired
	private ClUsrLoanDetailDao clUsrLoanDetailDao;
	@Autowired
	private ClPrdInfoDao clPrdInfoDao;
	@Autowired
	private ClUsrCntsDao clUsrCntsDao;
	/**
	 * 小额信贷申请
	 * @param creditLoanApplyVO
	 */
	public void creditLoanApply(String GUARANTYID,String USRCDE,String APPLYSUM,String PRD_CDE) {
		CreditLoanApplyController creditLoanApplyController = new CreditLoanApplyController();
		String xmlStr =creditLoanApplyController.setCreditLoanApplyData(GUARANTYID, USRCDE, APPLYSUM, PRD_CDE);
		logger.info("小额信用贷款请求参数"+xmlStr);
		//请求接口
		HttpConUtils httpConUtils = new HttpConUtils();
		String reulstXml = httpConUtils.doPostXML(PropertiesUtil.getString("loanapiurl"), xmlStr);
		CreditLoanApplyResp loanApplyResp = XStreamUtils.covertXml2JavaBean(reulstXml, CreditLoanApplyResp.class);

	}
	/**
	 * 小额信用贷款申请
	 * @param GUARANTYID
	 * @param USRCDE
	 * @param APPLYSUM
	 * @param PRD_CDE
	 * @return
	 */
	public String setCreditLoanApplyData(String GUARANTYID,String USRCDE,String APPLYSUM,String PRD_CDE) {
		RECORD creditLoanApplyVO = new RECORD();
		ClIdCardInfo  clIdCardInfo= clIdCardInfoDao.getByUsrCode(USRCDE);
		ClUsr clUsr =clUsrDao.getByUsrCode(USRCDE);
		ClUsrInfo clUsrInfo =clUsrInfoDao.getByUsrCode(USRCDE);
		ClUsrLoan clUsrLoan= clUsrLoanDao.selectByPrimaryKey(GUARANTYID);
		ClUsrLoanDetail clUsrLoanDetail =clUsrLoanDetailDao.selectByPrimaryKey(GUARANTYID);
		ClPrdInfo clPrdInfo = clPrdInfoDao.getByPrdCde(PRD_CDE);
		List<ClUsrCnts> clUsrCntsList = clUsrCntsDao.getByUsrCode(USRCDE);
		BODY BODY = new BODY();
		HEADER header = new HEADER();
		IN IN = new IN();
		header.setPATCH("ISP20180306105003105328");
		header.setTRANBANK("HSBKZX");
		header.setTRANCODE("YG0003");
		header.setTRANTIME("20180311105003");
		header.setDATASOURCE("ISP");
		creditLoanApplyVO.setGUARANTYID(GUARANTYID);
		creditLoanApplyVO.setAPPLYADD("网贷APP来源");
		creditLoanApplyVO.setCERTTYPE("1");
		creditLoanApplyVO.setCERTID(clIdCardInfo.getID_NO());
		creditLoanApplyVO.setCUSTOMERNAME(clIdCardInfo.getCUST_NAME());
		creditLoanApplyVO.setSEX(clIdCardInfo.getINDIV_SEX());
		creditLoanApplyVO.setBIRTHDAY(clIdCardInfo.getBIRTHDAY_DATE());
		creditLoanApplyVO.setTELNO(clUsrInfo.getINDIV_EMP_TEL());
		creditLoanApplyVO.setMOBILE(clUsr.getUSR_TEL());
		creditLoanApplyVO.setZIPCODE("000000");
		creditLoanApplyVO.setPOSTADD(clIdCardInfo.getLIVE_ADDR());
		creditLoanApplyVO.setAPPLYPURPOSE("网贷申请"); 
		creditLoanApplyVO.setAPPLYSUM(APPLYSUM);
		creditLoanApplyVO.setAPPLYCURRENCY("01");
		creditLoanApplyVO.setAPPLYTERMMONTH(String.valueOf(clUsrLoan.getLoanAmt()));
		creditLoanApplyVO.setPAYMENTTYPE("1");//未确定
		creditLoanApplyVO.setPAYMENTACCTNO("");//未确定
		creditLoanApplyVO.setPAYMETHOD(clUsrLoan.getRetuKind());
		creditLoanApplyVO.setSUBLOANTERM("");
		creditLoanApplyVO.setSUBPAYMETHOD("");
		creditLoanApplyVO.setDEBITTYPE("1");
		creditLoanApplyVO.setMONREPAYDAY("");
		creditLoanApplyVO.setMARRIAGE("");//传空值
		creditLoanApplyVO.setEDUEXPERIENCE("");//未确定 必传
		creditLoanApplyVO.setHUKOU(clUsrInfo.getLIVE_PROVINCE());
		creditLoanApplyVO.setINCOMEFLAG("1");
		creditLoanApplyVO.setMONTHLYWAGES("");//未确定 必传
		creditLoanApplyVO.setFAMILYSTATUS("");
		creditLoanApplyVO.setCITYLIVEDSTARTYEAR("");
		creditLoanApplyVO.setFAMILYADD(clUsrInfo.getLIVE_ADDR());
		creditLoanApplyVO.setFAMILYZIP("");
		creditLoanApplyVO.setFAMILYTEL("");
		creditLoanApplyVO.setUNITTYPE("");
		creditLoanApplyVO.setUNITKIND("z");
		creditLoanApplyVO.setEMPLOYMENT(clUsrInfo.getINDIV_EMP_NAME());
		if(clUsrInfo.getDUTY()=="高管人员") {
			creditLoanApplyVO.setSTAFF("0");
		}else if(clUsrInfo.getDUTY()=="中级管理人员") {
			creditLoanApplyVO.setSTAFF("1");
		}else if(clUsrInfo.getDUTY()=="基层管理人员") {
			creditLoanApplyVO.setSTAFF("2");
		}else if(clUsrInfo.getDUTY()=="一般员工") {
			creditLoanApplyVO.setSTAFF("3");
		}else if(clUsrInfo.getDUTY()=="销售/中介/业务代表") {
			creditLoanApplyVO.setSTAFF("4");
		}else {
			creditLoanApplyVO.setSTAFF("5");
		}

		creditLoanApplyVO.setCOMPANYADD(clUsrInfo.getINDIV_EMP_ADDR());
		creditLoanApplyVO.setCOMPANYZIP("");
		creditLoanApplyVO.setCOMPANYTEL(clUsrInfo.getINDIV_EMP_TEL());
		creditLoanApplyVO.setINSURERCODE("13");
		creditLoanApplyVO.setINSURERNAME("阳光保险公司");
		creditLoanApplyVO.setINSURERNAMEDETAIL("阳光保险集团股份有限公司");
		creditLoanApplyVO.setPOLICYHOLDER(clIdCardInfo.getCUST_NAME());
		creditLoanApplyVO.setINSUREDTOTALAMT(String.valueOf(clUsrLoan.getLoanAmt().longValue()*1.2));
		creditLoanApplyVO.setGUARANTYCURRENCY("01");
		creditLoanApplyVO.setINSUREBEGINDATE("");
		creditLoanApplyVO.setINSUREENDDATE("");
		creditLoanApplyVO.setPAYBEGINDATE("");
		creditLoanApplyVO.setCONFIRMVALUE(String.valueOf(clUsrLoanDetail.getsInsuamt()));
		creditLoanApplyVO.setVOUCHVALUE("");
		creditLoanApplyVO.setOPERATEORGID("");
		creditLoanApplyVO.setINSURANCECONTACTS("liuwei03");
		creditLoanApplyVO.setINSURANCETELEPHONE("95510");
		creditLoanApplyVO.setIMAGEID(clUsrLoanDetail.getApplCde());
		creditLoanApplyVO.setPAYKIND("10");
		creditLoanApplyVO.setACCTTYPE("0");
		creditLoanApplyVO.setACCTTYPE("0");
		creditLoanApplyVO.setBORROWERTYPE("1");
		creditLoanApplyVO.setENTERPRISENAME("");
		creditLoanApplyVO.setRECEIPTSPROVE("");
		creditLoanApplyVO.setENTERPRISEINCOMEMON("");
		creditLoanApplyVO.setACCOUNTINCOMEYEAR("");
		creditLoanApplyVO.setSHAREPCT("");
		creditLoanApplyVO.setLICENSENO("");
		creditLoanApplyVO.setDEPARTID("");
		creditLoanApplyVO.setINDUSTRYTYPE("");
		creditLoanApplyVO.setPURPOSETYPE("2");
		creditLoanApplyVO.setINSURERATE(String.valueOf(clPrdInfo.getPREMIUM_RATE()));
		creditLoanApplyVO.setPREMIUMPAYTYPE("01");
		creditLoanApplyVO.setPRODUCTTYPE("04");
		creditLoanApplyVO.setAPPLYNO(clUsrLoanDetail.getApplCde());
		creditLoanApplyVO.setCONTRACTNO(clUsrLoanDetail.getLoanAcno());
		creditLoanApplyVO.setPRODID(PRD_CDE);
		creditLoanApplyVO.setPRODNAME(clPrdInfo.getPRD_NAME());
		creditLoanApplyVO.setSTORIED("0100A003");
		creditLoanApplyVO.setSTORIEDNAME("销售人员对应组的核心代码");
		creditLoanApplyVO.setHOMEADDRPROV("");
		creditLoanApplyVO.setHOMEADDRCITY("");
		creditLoanApplyVO.setHOMEADDRDISTRICT("");
		creditLoanApplyVO.setEMPLOYEDTYPE("");
		creditLoanApplyVO.setWORKYEAR("");
		creditLoanApplyVO.setWORKMONTH("");
		for(ClUsrCnts clUsrCnts :clUsrCntsList){
			if(clUsrCnts.getLVL()=="first") {
				creditLoanApplyVO.setRELAPHONE1(clUsrCnts.getREL_MOBILE());
				if(clUsrCnts.getREL_RELATION()=="父母") {
					creditLoanApplyVO.setRELATION1("1");
				}else if(clUsrCnts.getREL_RELATION()=="子女"){
					creditLoanApplyVO.setRELATION1("5");
				}else if(clUsrCnts.getREL_RELATION()=="兄弟姐妹"){
					creditLoanApplyVO.setRELATION1("8");
				}else if(clUsrCnts.getREL_RELATION()=="其他"){
					creditLoanApplyVO.setRELATION1("99");
				}
			}
		}
		creditLoanApplyVO.setEMAIL("");
		creditLoanApplyVO.setCOUNTRY("中国");
		creditLoanApplyVO.setDUEDATE("");
		creditLoanApplyVO.setEDUEXPERIANCE("");//最高学历要有
		creditLoanApplyVO.setEDUDEGREE("");
		creditLoanApplyVO.setPOSITION(clUsrInfo.getDUTY());
		creditLoanApplyVO.setFAMILYCITY("");
		creditLoanApplyVO.setCOMPANYADDHEAD("");
		creditLoanApplyVO.setSUBAPPMANNO("");
		creditLoanApplyVO.setAPPLYMANNO("");
		creditLoanApplyVO.setBUSIMODE("");
		creditLoanApplyVO.setMONCOMFEERATE("");
		creditLoanApplyVO.setCONSERVFEERATE("");
		creditLoanApplyVO.setCONMANAFEERATE("");
		creditLoanApplyVO.setXTERM("");
		creditLoanApplyVO.setYTERM("");
		BODY.setRECORD(creditLoanApplyVO);

		IN.setHEADER(header);
		IN.setBODY(BODY);
		String xmlStr = JaxbUtil.convertToXml(IN);
		return xmlStr; 
	}
}
