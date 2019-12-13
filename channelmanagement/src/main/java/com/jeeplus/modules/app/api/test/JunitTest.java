package com.jeeplus.modules.app.api.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.jeeplus.modules.app.api.account.CreditLoanApply.request.BODY;
import com.jeeplus.modules.app.api.account.CreditLoanApply.request.HEADER;
import com.jeeplus.modules.app.api.account.CreditLoanApply.request.IN;
import com.jeeplus.modules.app.api.account.CreditLoanApply.request.RECORD;
import com.jeeplus.modules.app.api.account.utils.JaxbUtil;
import com.jeeplus.modules.app.api.invalidpolicy.InvaildPolicyController;
import com.jeeplus.modules.app.api.invalidpolicy.request.ReqInvaildPolicyVO;
import com.jeeplus.modules.app.api.invalidpolicy.request.ReqItemHouseDetailVO;
import com.jeeplus.modules.app.api.invalidpolicy.request.ReqLinkPersonDetailVO;
import com.jeeplus.modules.app.api.loanChannel.SynLoanChannelController;
import com.jeeplus.modules.app.api.loanChannel.request.ReqLoanChannelVO;
import com.jeeplus.modules.app.api.loanMessage.SynLoanMessageController;
import com.jeeplus.modules.app.api.loanMessage.request.ReqLoanMessageVO;
import com.jeeplus.modules.app.api.loantrial.SynLoanTrial;
import com.jeeplus.modules.app.api.loantrial.request.ReqLoanTrialVO;
import com.jeeplus.modules.app.api.product.PropertiesUtil;
import com.jeeplus.modules.app.api.product.SynProductMSGController;
import com.jeeplus.modules.app.api.product.request.ReqProductVO;
import com.jeeplus.modules.app.api.withholdrecover.WithholdRecover;
import com.jeeplus.modules.app.api.withholdrecover.request.ReqWithholdRecoverlVO;
import com.jeeplus.modules.app.utils.HttpConUtils;


public class JunitTest {
	/**
	 * 产品信息同步test
	 */
	@Test
	public void synProductTest() {
		ReqProductVO reqProductVO = new ReqProductVO();
		SynProductMSGController SynProductMSGController = new SynProductMSGController();
		reqProductVO.setServiceId("servP0000100012");
		reqProductVO.setTyp_cde("JQD");
		reqProductVO.setAcct_role_desc("极期待");
		reqProductVO.setOpt_typ("UPDATE");
		SynProductMSGController.synProductMSG(reqProductVO);
	}
	/**
	 * 放款渠道信息同步test
	 * 
	 */
	@Test
	public void synLoanChanneltest() {
		SynLoanChannelController SynLoanBankController = new SynLoanChannelController();
		ReqLoanChannelVO ReqLoanBankVO = new ReqLoanChannelVO();
		List<ReqLoanChannelVO> list = new ArrayList<ReqLoanChannelVO>();
		ReqLoanBankVO.setServiceId("servP0000100011");
		ReqLoanBankVO.setDN_CHANNEL("FOTIC");
		ReqLoanBankVO.setTNR_OPT(24);
		ReqLoanBankVO.setLOAN_RATE_MODE("FX");
		ReqLoanBankVO.setLOAN_BASE_RATE(0.09);
		ReqLoanBankVO.setINT_ADJ_PCT("");
		ReqLoanBankVO.setRATE_TYPE("U");
		ReqLoanBankVO.setINT_RAT(0.09);
		ReqLoanBankVO.setOD_INT_RATE(0.021);
		ReqLoanBankVO.setLEND_CHA_NAM("中国对外经济贸易信托有限公司");
		ReqLoanBankVO.setOPT_TYP("UPDATE");
		ReqLoanBankVO.setLAST_CHG_USR("admin");
		ReqLoanBankVO.setLAST_CHG_DT("2018-03-27 10:37:12");
		ReqLoanBankVO.setBEL_TYPE("07");
		ReqLoanBankVO.setTOTTERM_X("Z");
		ReqLoanBankVO.setXQ_RATE("");
		list.add(ReqLoanBankVO);
		ReqLoanBankVO.setPLoanTypMtdList(list);
		SynLoanBankController.synProductMSG(ReqLoanBankVO);
	}
	
	/**
	 * 放款信息同步test
	 * 
	 */
	@Test
	public void synLoanMessagetest() {
		SynLoanMessageController SynLoanMessageController = new SynLoanMessageController();
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
		SynLoanMessageController.synLoanMessage(reqLoanMessageVO);
	}
	
	/**
	 * 放款渠道信息同步test
	 * 
	 */
	@Test
	public void synLoanTrialtest() {
		SynLoanTrial SynLoanTrialController = new SynLoanTrial();
		ReqLoanTrialVO ReqLoanTrialVO = new ReqLoanTrialVO();
		List<ReqLoanTrialVO> list = new ArrayList<ReqLoanTrialVO>();
		ReqLoanTrialVO.setServiceId("serv10000100027");
		ReqLoanTrialVO.setORIG_PRCP("60000");
		ReqLoanTrialVO.setDUE_DAY("02");
		ReqLoanTrialVO.setLOAN_ACTV_DT("2018-02-02");
		ReqLoanTrialVO.setLOAN_TYP("goodPropertyA");
		ReqLoanTrialVO.setINT_START_DT("2018-02-02");
		ReqLoanTrialVO.setLOAN_INT_RATE("0.0665");
		ReqLoanTrialVO.setPAYM_FREQ_UNIT("M");
		ReqLoanTrialVO.setPAYM_FREQ_FREQ("1");
		ReqLoanTrialVO.setLOAN_PAYM_TYP("01");
		ReqLoanTrialVO.setINSTM_IND("Y");
		ReqLoanTrialVO.setTNR("3");
		ReqLoanTrialVO.setCAL_TOT_INSTM("");
		ReqLoanTrialVO.setPS_DUE_DT("");
		ReqLoanTrialVO.setFEE_CDE("F0001");
		ReqLoanTrialVO.setRECV_PAY_IND("R");
		ReqLoanTrialVO.setFEE_TYP("06");
		ReqLoanTrialVO.setFEE_CALC_TYP("PCT");
		ReqLoanTrialVO.setFEE_PCT_BASE("01");
		ReqLoanTrialVO.setBASE_AMT("60000");
		ReqLoanTrialVO.setCHRG_PCT("0.0103");
		ReqLoanTrialVO.setHOLD_FEE_IND("Y");
		ReqLoanTrialVO.setHOLD_FEE_SETL_DT("2018-02-02");
		ReqLoanTrialVO.setACC_IND("N");
		list.add(ReqLoanTrialVO);
		ReqLoanTrialVO.setLmDnShdMtdTList(list);
		ReqLoanTrialVO.setLmFeeTxTList(list);
		ReqLoanTrialVO.setLmPmShdTList(list);
		SynLoanTrialController.synLoanTrial(ReqLoanTrialVO);
	}
	
	/**
	 * 无效保单test
	 * 
	 */
	@Test
	public void invaildPolicytest() {
		InvaildPolicyController InvaildPolicyController = new InvaildPolicyController();
		List<ReqItemHouseDetailVO> itemHouseDetail = new ArrayList<ReqItemHouseDetailVO>();
		List<ReqLinkPersonDetailVO> linkPersonDetail = new ArrayList<ReqLinkPersonDetailVO>();
		ReqInvaildPolicyVO ReqInvaildPolicyVO = new ReqInvaildPolicyVO();
		ReqItemHouseDetailVO ReqItemHouseDetailVO = new ReqItemHouseDetailVO();
		ReqLinkPersonDetailVO ReqLinkPersonDetailVO = new ReqLinkPersonDetailVO();
		linkPersonDetail.add(ReqLinkPersonDetailVO);
		itemHouseDetail.add(ReqItemHouseDetailVO);
		ReqInvaildPolicyVO.setLinkPersonDetail(linkPersonDetail);
		ReqInvaildPolicyVO.setItemHouseDetail(itemHouseDetail);
		InvaildPolicyController.InvaildPolicy(ReqInvaildPolicyVO);
	}
	
	/**
	 * 代扣追偿test
	 * 
	 */
	@Test
	public void withholdRecovertest() {
		WithholdRecover WithholdRecoverController = new WithholdRecover();

		ReqWithholdRecoverlVO reqWithholdRecoverlVO = new ReqWithholdRecoverlVO();
		reqWithholdRecoverlVO.setServiceId("serv10000000156");
		reqWithholdRecoverlVO.setBEL_TYPE("07");
		reqWithholdRecoverlVO.setQUERY_DT("2018-08-26");
		reqWithholdRecoverlVO.setQUERY_TYPE("SUBROGATION");

		WithholdRecoverController.withholdRecover(reqWithholdRecoverlVO);
		
		reqWithholdRecoverlVO.setQUERY_TYPE("CLAIS");
		WithholdRecoverController.withholdRecover(reqWithholdRecoverlVO);
	}
	/**
	 * 小额信贷贷款申请test
	 * 
	 */
	@Test
	public void creditLoanApplytest() {
	  	RECORD creditLoanApplyVO = new RECORD();
		BODY BODY = new BODY();
		HEADER header = new HEADER();
		IN IN = new IN();
		header.setPATCH("ISP20180306105003105328");
		header.setTRANBANK("HSBKZX");
		header.setTRANCODE("YG0003");
		header.setTRANTIME("20180311105003");
		header.setDATASOURCE("ISP");
		creditLoanApplyVO.setGUARANTYID("12343123213");
		creditLoanApplyVO.setAPPLYADD("网贷APP来源");
		creditLoanApplyVO.setCERTTYPE("1");
		creditLoanApplyVO.setCERTID("152324187652123");
		creditLoanApplyVO.setCUSTOMERNAME("测试");
		creditLoanApplyVO.setSEX("男");
		creditLoanApplyVO.setBIRTHDAY("20171102");
		creditLoanApplyVO.setTELNO("15123456789");
		creditLoanApplyVO.setMOBILE("15123456789");
		creditLoanApplyVO.setZIPCODE("000000");
		creditLoanApplyVO.setPOSTADD("阳光保险");
		creditLoanApplyVO.setAPPLYPURPOSE("网贷申请"); 
		creditLoanApplyVO.setAPPLYSUM("5000");
		creditLoanApplyVO.setAPPLYCURRENCY("01");
		creditLoanApplyVO.setAPPLYTERMMONTH("4000");
		creditLoanApplyVO.setPAYMENTTYPE("1");//未确定
		creditLoanApplyVO.setPAYMENTACCTNO("");//未确定
		creditLoanApplyVO.setPAYMETHOD("");
		creditLoanApplyVO.setSUBLOANTERM("");
		creditLoanApplyVO.setSUBPAYMETHOD("");
		creditLoanApplyVO.setDEBITTYPE("1");
		creditLoanApplyVO.setMONREPAYDAY("");
		creditLoanApplyVO.setMARRIAGE("");//传空值
		creditLoanApplyVO.setEDUEXPERIENCE("大学");//未确定 必传
		creditLoanApplyVO.setHUKOU("北京");
		creditLoanApplyVO.setINCOMEFLAG("1");
		creditLoanApplyVO.setMONTHLYWAGES("2000");//未确定 必传
		creditLoanApplyVO.setFAMILYSTATUS("");
		creditLoanApplyVO.setCITYLIVEDSTARTYEAR("");
		creditLoanApplyVO.setFAMILYADD("北京");
		creditLoanApplyVO.setFAMILYZIP("");
		creditLoanApplyVO.setFAMILYTEL("");
		creditLoanApplyVO.setUNITTYPE("");
		creditLoanApplyVO.setUNITKIND("z");
		creditLoanApplyVO.setEMPLOYMENT("阳光保险");
		creditLoanApplyVO.setSTAFF("0");
		creditLoanApplyVO.setCOMPANYADD("阳光保险");
		creditLoanApplyVO.setCOMPANYZIP("");
		creditLoanApplyVO.setCOMPANYTEL("15123456789");
		creditLoanApplyVO.setINSURERCODE("13");
		creditLoanApplyVO.setINSURERNAME("阳光保险公司");
		creditLoanApplyVO.setINSURERNAMEDETAIL("阳光保险集团股份有限公司");
		creditLoanApplyVO.setPOLICYHOLDER("测试");
		creditLoanApplyVO.setINSUREDTOTALAMT("23");
		creditLoanApplyVO.setGUARANTYCURRENCY("01");
		creditLoanApplyVO.setINSUREBEGINDATE("");
		creditLoanApplyVO.setINSUREENDDATE("");
		creditLoanApplyVO.setPAYBEGINDATE("");
		creditLoanApplyVO.setCONFIRMVALUE("32");
		creditLoanApplyVO.setVOUCHVALUE("");
		creditLoanApplyVO.setOPERATEORGID("");
		creditLoanApplyVO.setINSURANCECONTACTS("liuwei03");
		creditLoanApplyVO.setINSURANCETELEPHONE("95510");
		creditLoanApplyVO.setIMAGEID("987");
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
		creditLoanApplyVO.setINSURERATE("0.8");
		creditLoanApplyVO.setPREMIUMPAYTYPE("01");
		creditLoanApplyVO.setPRODUCTTYPE("04");
		creditLoanApplyVO.setAPPLYNO("987");
		creditLoanApplyVO.setCONTRACTNO("987");
		creditLoanApplyVO.setPRODID("987");
		creditLoanApplyVO.setPRODNAME("987");
		creditLoanApplyVO.setSTORIED("0100A003");
		creditLoanApplyVO.setSTORIEDNAME("销售人员对应组的核心代码");
		creditLoanApplyVO.setHOMEADDRPROV("");
		creditLoanApplyVO.setHOMEADDRCITY("");
		creditLoanApplyVO.setHOMEADDRDISTRICT("");
		creditLoanApplyVO.setEMPLOYEDTYPE("");
		creditLoanApplyVO.setWORKYEAR("");
		creditLoanApplyVO.setWORKMONTH("");
		creditLoanApplyVO.setRELATION1("8");
		creditLoanApplyVO.setEMAIL("");
		creditLoanApplyVO.setCOUNTRY("中国");
		creditLoanApplyVO.setDUEDATE("");
		creditLoanApplyVO.setEDUEXPERIANCE("");//最高学历要有
		creditLoanApplyVO.setEDUDEGREE("");
		creditLoanApplyVO.setPOSITION("一般员工");
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
		System.out.println(xmlStr);
		HttpConUtils httpConUtils = new HttpConUtils();
		String reulst = httpConUtils.doPostXML(PropertiesUtil.getString("loanapiurl"), xmlStr);
		System.out.println("小额信贷返回结果"+reulst);
	}
	/**
	 * 提前还款test
	 * 
	 */
	//@Test
/*	public void LoanPrePaymenttest() {
		RECORD RECORD = new RECORD();
		BODY BODY = new BODY();
		HEADER header = new HEADER();
		IN IN = new IN();
		header.setPATCH("ISP20180306105003105328");
		header.setTRANBANK("HSBKZX");
		header.setTRANCODE("YG0003");
		header.setTRANTIME("20180311105003");
		header.setDATASOURCE("ISP");
		RECORD.setGUARANTYID("6217991660005472234");
		RECORD.setLNACCT("70100030001281206");
		RECORD.setTYPE("1");
		RECORD.setCALDATE("20171219");
		RECORD.setPAYMENTACCTNO("C832520170314050445");
		BODY.setRECORD(RECORD);
		IN.setHEADER(header);
		IN.setBODY(BODY);
		String xmlStr = JaxbUtil.convertToXml(IN);
	}*/
}
