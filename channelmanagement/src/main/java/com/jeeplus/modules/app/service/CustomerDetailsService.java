package com.jeeplus.modules.app.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jeeplus.modules.app.dao.ClcollectionDao;
import com.jeeplus.modules.app.entity.ClBnkCrd;
import com.jeeplus.modules.app.entity.ClCustomerDetails;
import com.jeeplus.modules.app.entity.ClPrdInfo;
import com.jeeplus.modules.app.entity.ClUsrApply;

@Service
@Transactional(readOnly = false)
public class CustomerDetailsService {

	@Autowired
	private ClcollectionDao clcollectionDao;

	public String customerDetail(String CUST_ID, String POLICY_NO) {
		ClCustomerDetails clCustomerDetails = clcollectionDao.getCustomerDetails(CUST_ID);
		String gender = clCustomerDetails.getGENDER();

		if ("男".equals(gender)) {
			gender = "M";
		} else {
			gender = "F";
		}

		String custormerStr = clCustomerDetails.getCUST_ID() + "|+|" + POLICY_NO + "|+|" + clCustomerDetails.getID_NO()
				+ "|+|" + clCustomerDetails.getID_TYPE() + "|+|" + "Z" + "|+|" + clCustomerDetails.getNAME() + "|+|"
				+ gender + "|+|" + CustomerDetailsService.deal(clCustomerDetails.getBIRTHDAY()) + "|+|" + "H" + "|+|"
				+ "" + "|+|" + "Z" + "|+|" + "C" + "|+|" + "Z" + "|+|" + "Z" + "|+|" + "A" + "|+|"
				+ clCustomerDetails.getMOBILE_NO() + "|+|" + "" + "|+|" + "C" + "|+|" + "0" + "|+|" + "0" + "|+|"
				+ clCustomerDetails.getCORP_NAME() + "|+|" + "Y" + "|+|" + "N" + "|+|"
				+ clCustomerDetails.getHOMEADDRESS() + "|+|" + "新业务BJ" + "|+|" + "00370100" + "|+|";
		System.out.println("客户信息" + custormerStr);
		return custormerStr;

	}

	public String mobileDetails() {
		String mobileDetailsStr = "0" + "|+|" + "" + "|+|" + "" + "|+|" + "" + "|+|" + "" + "|+|" + "" + "|+|" + ""
				+ "|+|" + "" + "|+|";
		System.out.println("电话信息" + mobileDetailsStr);
		return mobileDetailsStr;
	}

	public String addressDetails(String CUST_ID) {
		ClCustomerDetails clCustomerDetails = clcollectionDao.getCustomerDetails(CUST_ID);
		String addressDetailsStr = "0" + "|+|" + clCustomerDetails.getCUST_ID() + "|+|" + "P" + "|+|"
				+ clCustomerDetails.getNAME() + "|+|" + this.getGender(clCustomerDetails.getGENDER()) + "|+|" + ""
				+ "|+|" + "S" + "|+|" + clCustomerDetails.getHOMEADDRESS() + "|+|" + "Y" + "|+|";
		System.out.println("地址信息" + addressDetailsStr);
		return addressDetailsStr;
	}

	public String policyDetails(String ACCT_NO, String CUST_ID, String POLICY_NO, ClUsrApply apply, ClPrdInfo prdInfo,
			ClBnkCrd bankcard) {
		String addressDetailsStr = POLICY_NO + "|+|" + CUST_ID + "|+|" + POLICY_NO + "|+|" + apply.getLoanAcno() + "|+|"
				+ CUST_ID + "|+|" + "" + "|+|" + apply.getPayChannel() + "|+|" + "" + "|+|" + "" + "|+|"
				+ prdInfo.getPRD_CDE() + "|+|" + this.getApplyFor(apply.getApplyFor()) + "|+|" + "A" + "|+|"
				+ prdInfo.getINT_RAT().multiply(new BigDecimal(10000)).setScale(0, BigDecimal.ROUND_HALF_UP) + "|+|"
				+ prdInfo.getPREMIUM_RATE().multiply(new BigDecimal(10000)).setScale(0, BigDecimal.ROUND_HALF_UP)
				+ "|+|" + 8408 + "|+|" + "0100A003" + "|+|" + "S" + "|+|" + "" + "|+|" + "" + "|+|" + "" + "|+|" + ""
				+ "|+|";
		System.out.println("保单信息" + addressDetailsStr);
		return addressDetailsStr;
	}

	private String getGender(String genderStr) {
		// M|男F|女Z|未知

		if ("男".equals(genderStr)) {
			return "M";
		} else if ("女".equals(genderStr)) {
			return "F";
		} else if ("未知".equals(genderStr)) {
			return "Z";
		}
		return "";
	}

	private String getApplyFor(String applyForStr) {
		// M|男F|女Z|未知

		if ("日常消费".equals(applyForStr)) {
			return "1";
		} else if ("装修".equals(applyForStr)) {
			return "32";
		} else if ("旅游".equals(applyForStr)) {
			return "32";
		} else if ("教育".equals(applyForStr)) {
			return "8";
		}
		return "32";
	}

	public static String deal(String params) {
		int year = params.indexOf("年");
		int month = params.indexOf("月");
		int day = params.indexOf("日");

		String yearStr = params.substring(0, year);
		String monthStr = params.substring(year + 1, month);
		String dayStr = params.substring(month + 1, day);
		if (monthStr.length() == 1) {
			monthStr = "0" + month;
		}
		if (dayStr.length() == 1) {
			dayStr = "0" + dayStr;
		}

		return yearStr + monthStr + dayStr;
	}
}
