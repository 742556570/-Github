package com.jeeplus.modules.app.service;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.modules.app.api.product.PropertiesUtil;
import com.jeeplus.modules.app.dao.ClAppIdGenDao;
import com.jeeplus.modules.app.dao.ClBnkCrdDao;
import com.jeeplus.modules.app.dao.ClCapChannelDao;
import com.jeeplus.modules.app.dao.ClCrdtExtDao;
import com.jeeplus.modules.app.dao.ClIdCardInfoDao;
import com.jeeplus.modules.app.dao.ClPrdInfoDao;
import com.jeeplus.modules.app.dao.ClPremiumDetailDao;
import com.jeeplus.modules.app.dao.ClUsrApplyDao;
import com.jeeplus.modules.app.dao.ClUsrCntsDao;
import com.jeeplus.modules.app.dao.ClUsrInfoDao;
import com.jeeplus.modules.app.entity.ClBnkCrd;
import com.jeeplus.modules.app.entity.ClCapChannel;
import com.jeeplus.modules.app.entity.ClIdCardInfo;
import com.jeeplus.modules.app.entity.ClPrdInfo;
import com.jeeplus.modules.app.entity.ClPremiumDetail;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.entity.ClUsrApply;
import com.jeeplus.modules.app.entity.ClUsrCnts;
import com.jeeplus.modules.app.entity.ClUsrInfo;
import com.jeeplus.modules.app.utils.FtpConManager;
import com.jeeplus.modules.app.utils.GenerateFile;

@Service
@Transactional(readOnly = false)
public class CollectionService {
	@Autowired
	private CustomerDetailsService customerDetailsService;

	@Autowired
	private ClPrdInfoDao prdDao;

	@Autowired
	private ClAppIdGenDao appIdDao;

	@Autowired
	private ClIdCardInfoDao idCardDao;

	@Autowired
	private ClBnkCrdDao bnkCrdDao;

	@Autowired
	private ClUsrCntsDao usrCntsDao;

	@Autowired
	private ClCapChannelDao capChannelDao;

	@Autowired
	private ClUsrApplyDao applyDao;

	@Autowired
	private ClUsrInfoDao usrInfoDao;

	public void sysCollection(ClUsr usr, String policyNo)  {
		String msg = "";
		ClUsrApply apply = applyDao.getByPolicyNo(policyNo);
		// 获取审核中状态的申请
		ClIdCardInfo idCard = idCardDao.getByUsrCode(usr.getUSR_CDE());
		String prdCde = apply.getPrdCde();

		ClCapChannel capChannel = capChannelDao.getByCapSeq("1");
		ClPrdInfo prdInfo = prdDao.getByPrdCde(prdCde);
		ClUsrInfo usrInfo = usrInfoDao.getByUsrCode(usr.getUSR_CDE());
		List<ClUsrCnts> usrCntList = usrCntsDao.getByUsrCode(usr.getUSR_CDE());
		JSONObject usrCnts = parseUsrCntsList(usrCntList);
		ClBnkCrd bankcard = bnkCrdDao.getOneByBankNoWithOutSts(apply.getBankCardNo());
		// 同步催收
		if (!itcCustomer(usr,policyNo)) {
			msg += "保单号为:" + policyNo + "的订单同步客户信息失败。";
			System.out.println(msg);
			return;
		}
		if (!itcContact(usr, usrCnts, idCard.getCUST_NAME(), idCard.getINDIV_SEX())) {
			msg += "保单号为:" + policyNo + "的订单同步客户联系人信息失败。";
			System.out.println(msg);
			return;
		}
		if (!itcAddress(usr)) {
			msg += "保单号为:" + policyNo + "的订单同步客户地址信息失败。";
			System.out.println(msg);
			return;
		}
		if (!itcPolicy(usr, apply, prdInfo, bankcard)) {
			msg += "保单号为:" + policyNo + "的订单同步客户保单信息失败。";
			System.out.println(msg);
			return;
		}

	}
	
	
	public void getFile(ClUsr usr, String policyNo)  {
		String msg = "";
		ClUsrApply apply = applyDao.getByPolicyNo(policyNo);
		// 获取审核中状态的申请
		ClIdCardInfo idCard = idCardDao.getByUsrCode(usr.getUSR_CDE());
		String prdCde = apply.getPrdCde();

		ClCapChannel capChannel = capChannelDao.getByCapSeq("1");
		ClPrdInfo prdInfo = prdDao.getByPrdCde(prdCde);
		ClUsrInfo usrInfo = usrInfoDao.getByUsrCode(usr.getUSR_CDE());
		List<ClUsrCnts> usrCntList = usrCntsDao.getByUsrCode(usr.getUSR_CDE());
		JSONObject usrCnts = parseUsrCntsList(usrCntList);
		ClBnkCrd bankcard = bnkCrdDao.getOneByBankNoWithOutSts(apply.getBankCardNo());
		// 同步催收
		if (!itcCustomerFile(usr,policyNo)) {
			msg += "保单号为:" + policyNo + "的订单同步客户信息失败。";
			System.out.println(msg);
			return;
		}
		if (!itcContactFile(usr, usrCnts, idCard.getCUST_NAME(), idCard.getINDIV_SEX(),policyNo)) {
			msg += "保单号为:" + policyNo + "的订单同步客户联系人信息失败。";
			System.out.println(msg);
			return;
		}
		if (!itcAddressFile(usr,policyNo)) {
			msg += "保单号为:" + policyNo + "的订单同步客户地址信息失败。";
			System.out.println(msg);
			return;
		}
		if (!itcPolicyFile(usr, apply, prdInfo, bankcard)) {
			msg += "保单号为:" + policyNo + "的订单同步客户保单信息失败。";
			System.out.println(msg);
			return;
		}

	}
	
	
	private JSONObject parseUsrCntsList(List<ClUsrCnts> usrCntsList) {
		JSONObject jsObj = new JSONObject();

		if (usrCntsList.size() == 1) {
			jsObj.put("otherName", "");
			jsObj.put("otherPhone", "");
			jsObj.put("otherRelation", "");
		}

		for (ClUsrCnts clUsrCnts : usrCntsList) {
			if (clUsrCnts.getLVL().equals("first")) {
				jsObj.put("relationName", clUsrCnts.getREL_NAME());
				jsObj.put("relationPhone", clUsrCnts.getREL_MOBILE());
				jsObj.put("relation", clUsrCnts.getREL_RELATION());
			} else if (clUsrCnts.getLVL().equals("second")) {
				jsObj.put("otherName", clUsrCnts.getREL_NAME());
				jsObj.put("otherPhone", clUsrCnts.getREL_MOBILE());
				jsObj.put("otherRelation", clUsrCnts.getREL_RELATION());
			}
		}

		return jsObj;
	}

	private boolean itcCustomer(ClUsr usr,String policyNo) {
		try {
			String CUST_ID = usr.getUSR_CDE();
			SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
			Date dateC=new Date();  
		    Calendar calendar = Calendar.getInstance();  
	        calendar.setTime(dateC);  
	        calendar.add(Calendar.DAY_OF_MONTH, -1);  
	        dateC = calendar.getTime();  
			String date = sdf.format(dateC);
			String fileName = date + "ICT_CUSTOMER.txt";
			String fileNameOK = date + ".ok";

			String customerDetailStr = customerDetailsService.customerDetail(CUST_ID,policyNo);
			// 生成文件
			this.SFtp(customerDetailStr, fileName);
			// 生成文件后缀为ok
			this.SFtpOK(fileNameOK);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}

	}
	
	
	private boolean itcCustomerFile(ClUsr usr,String policyNo) {
		try {
			String CUST_ID = usr.getUSR_CDE();
			String date = policyNo+"_";
			String fileName = date + "ICT_CUSTOMER.txt";

			String customerDetailStr = customerDetailsService.customerDetail(CUST_ID,policyNo);
			// 生成文件
			this.genFile(customerDetailStr, fileName);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}

	}
	

	private boolean itcContact(ClUsr usr, JSONObject usrCnts, String p_name, String gender) {
		try {
			if (gender.equals("男")) {
				gender = "M";
			} else if (gender.equals("女")) {
				gender = "F";
			} else {
				gender = "Z";
			}
			String mobileDetailsStr2 ="";
			String mobileDetailsStr3="";
			// String mobileDetailStr = customerDetailsService.mobileDetails();
			SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
			Date dateC=new Date();  
		    Calendar calendar = Calendar.getInstance();  
	        calendar.setTime(dateC);  
	        calendar.add(Calendar.DAY_OF_MONTH, -1);  
	        dateC = calendar.getTime();  
			String date = sdf.format(dateC);
			String fileName = date + "ICT_CONTACT.txt";
			String mobileDetailsStr1 = 0 + "|+|" + usr.getUSR_CDE() + "|+|" + "P" + "|+|" + p_name + "|+|" + gender
					+ "|+|" + "MOBI" + "|+|" + usr.getUSR_TEL() + "|+|" + "Y" + "|+|";
			this.SFtp(mobileDetailsStr1, fileName);
			if(!StringUtils.isEmpty(usrCnts.getString("relationPhone"))) {
				 mobileDetailsStr2 = 0 + "|+|" + usr.getUSR_CDE() + "|+|"
							+ getCSCntsRelationShip(usrCnts.getString("relation")) + "|+|" + usrCnts.getString("relationName")
							+ "|+|" + gender + "|+|" + "MOBI" + "|+|" + usrCnts.getString("relationPhone") + "|+|" + "Y" + "|+|";
				 this.SFtp(mobileDetailsStr2 , fileName);
			}
			if(!StringUtils.isEmpty(usrCnts.getString("otherPhone"))) {
				 mobileDetailsStr3 = 0 + "|+|" + usr.getUSR_CDE() + "|+|"
							+ getCSCntsRelationShip(usrCnts.getString("otherRelation")) + "|+|" + usrCnts.getString("otherName")
							+ "|+|" + gender + "|+|" + "MOBI" + "|+|" + usrCnts.getString("otherPhone") + "|+|" + "Y" + "|+|";
				 this.SFtp(mobileDetailsStr3, fileName);
			}
			String mobileDetailStr = mobileDetailsStr1 +"\n" + mobileDetailsStr2 + "\n" + mobileDetailsStr3;
			System.out.println("电话信息" + mobileDetailStr);
			
			
			
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	
	private boolean itcContactFile(ClUsr usr, JSONObject usrCnts, String p_name, String gender,String policyNo) {
		try {
			if (gender.equals("男")) {
				gender = "M";
			} else if (gender.equals("女")) {
				gender = "F";
			} else {
				gender = "Z";
			}
			String mobileDetailsStr2 ="";
			String mobileDetailsStr3="";
			// String mobileDetailStr = customerDetailsService.mobileDetails();
			String date = policyNo + "_";
			String fileName = date + "ICT_CONTACT.txt";
			String mobileDetailsStr1 = 0 + "|+|" + usr.getUSR_CDE() + "|+|" + "P" + "|+|" + p_name + "|+|" + gender
					+ "|+|" + "MOBI" + "|+|" + usr.getUSR_TEL() + "|+|" + "Y" + "|+|";
			this.genFile(mobileDetailsStr1, fileName);
			if(!StringUtils.isEmpty(usrCnts.getString("relationPhone"))) {
				 mobileDetailsStr2 = 0 + "|+|" + usr.getUSR_CDE() + "|+|"
							+ getCSCntsRelationShip(usrCnts.getString("relation")) + "|+|" + usrCnts.getString("relationName")
							+ "|+|" + gender + "|+|" + "MOBI" + "|+|" + usrCnts.getString("relationPhone") + "|+|" + "Y" + "|+|";
				 this.genFile(mobileDetailsStr2 , fileName);
			}
			if(!StringUtils.isEmpty(usrCnts.getString("otherPhone"))) {
				 mobileDetailsStr3 = 0 + "|+|" + usr.getUSR_CDE() + "|+|"
							+ getCSCntsRelationShip(usrCnts.getString("otherRelation")) + "|+|" + usrCnts.getString("otherName")
							+ "|+|" + gender + "|+|" + "MOBI" + "|+|" + usrCnts.getString("otherPhone") + "|+|" + "Y" + "|+|";
				 this.genFile(mobileDetailsStr3, fileName);
			}
			String mobileDetailStr = mobileDetailsStr1 +"\n" + mobileDetailsStr2 + "\n" + mobileDetailsStr3;
			System.out.println("电话信息" + mobileDetailStr);
			
			
			
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	private String getCSCntsRelationShip(String relationStr) {
		if ("本人".equals(relationStr)) {
			return "P";
		} else if ("配偶".equals(relationStr)) {
			return "C";
		} else if ("父亲".equals(relationStr)) {
			return "F";
		} else if ("母亲".equals(relationStr)) {
			return "M";
		} else if ("父母".equals(relationStr)) {
			return "F";
		} else if ("兄弟".equals(relationStr)) {
			return "B";
		} else if ("姐妹".equals(relationStr)) {
			return "S";
		} else if ("亲属".equals(relationStr)) {
			return "L";
		} else if ("亲戚".equals(relationStr)) {
			return "L";
		} else if ("同事".equals(relationStr)) {
			return "W";
		} else if ("朋友".equals(relationStr)) {
			return "O";
		} else {
			return "O";
		}
	}

	private boolean itcAddress(ClUsr usr) {
		try {
			String CUST_ID = usr.getUSR_CDE();
			SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
			Date dateC=new Date();  
		    Calendar calendar = Calendar.getInstance();  
	        calendar.setTime(dateC);  
	        calendar.add(Calendar.DAY_OF_MONTH, -1);  
	        dateC = calendar.getTime();  
			String date = sdf.format(dateC);
			String fileName = date + "ICT_ADDRESS.txt";

			String addressDetailStr = customerDetailsService.addressDetails(CUST_ID);
			this.SFtp(addressDetailStr, fileName);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean itcAddressFile(ClUsr usr,String policyNo) {
		try {
			String CUST_ID = usr.getUSR_CDE();
			String date = policyNo + "_";
			String fileName = date + "ICT_ADDRESS.txt";

			String addressDetailStr = customerDetailsService.addressDetails(CUST_ID);
			this.genFile(addressDetailStr, fileName);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	private boolean itcPolicy(ClUsr usr, ClUsrApply apply, ClPrdInfo prdInfo, ClBnkCrd bankcard) {
		try {
			String CUST_ID = usr.getUSR_CDE();
			String ACCT_NO = bankcard.getAPPL_CARD_NO();
			String POLICY_NO = apply.getPolicyNo();
			SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
			Date dateC=new Date();  
		    Calendar calendar = Calendar.getInstance();  
	        calendar.setTime(dateC);  
	        calendar.add(Calendar.DAY_OF_MONTH, -1);  
	        dateC = calendar.getTime();  
			String date = sdf.format(dateC);
			String fileName = date + "ICT_POLICY.txt";
			String policyDetailStr = customerDetailsService.policyDetails(ACCT_NO, CUST_ID, POLICY_NO, apply, prdInfo,
					bankcard);
			this.SFtp(policyDetailStr, fileName);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}

	}
	
	private boolean itcPolicyFile(ClUsr usr, ClUsrApply apply, ClPrdInfo prdInfo, ClBnkCrd bankcard) {
		try {
			String CUST_ID = usr.getUSR_CDE();
			String ACCT_NO = bankcard.getAPPL_CARD_NO();
			String POLICY_NO = apply.getPolicyNo();
			String date = POLICY_NO + "_";
			String fileName = date + "ICT_POLICY.txt";
			String policyDetailStr = customerDetailsService.policyDetails(ACCT_NO, CUST_ID, POLICY_NO, apply, prdInfo,
					bankcard);
			this.genFile(policyDetailStr, fileName);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}

	}
	
	/**
	 * 将文件传输到ftp
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	private boolean SFtp(String str, String fileName) {
		GenerateFile generateFile = new GenerateFile();
		String url = PropertiesUtil.getString("baseurl");
		// 生成文件放在本地
		generateFile.setJsonData(str, url + fileName);
		// 将本地的文件传输到ftp上面
		FtpConManager.getInstance().login(PropertiesUtil.getString("ipurl"), PropertiesUtil.getString("name"),
				PropertiesUtil.getString("password"));
		boolean flag =  FtpConManager.getInstance().uploadFile(url + fileName, fileName);
		System.out.println("操作结果:" + flag);
		return flag;
	}
	
	
	private void genFile(String str, String fileName) {
		GenerateFile generateFile = new GenerateFile();
		String url = PropertiesUtil.getString("baseurl");
		// 生成文件放在本地
		generateFile.setJsonData(str, url + fileName);
	}

	/**
	 * 将文件传输到ftp
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	private boolean SFtpOK(String fileName) {
		GenerateFile generateFile = new GenerateFile();
		String url = PropertiesUtil.getString("baseurl");
		// 生成文件放在本地
		generateFile.setData(url + fileName);
		// 将本地的文件传输到ftp上面
		FtpConManager.getInstance().login(PropertiesUtil.getString("ipurl"), PropertiesUtil.getString("name"),
				PropertiesUtil.getString("password"));
		boolean flag = FtpConManager.getInstance().uploadFile(url + fileName, fileName);
		System.out.println("操作结果:" + flag);
		return flag;
	}
}
