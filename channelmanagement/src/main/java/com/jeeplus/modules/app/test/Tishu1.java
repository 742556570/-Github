package com.jeeplus.modules.app.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class Tishu1 {
	/**
	 * 
	 * select a.USR_CDE,a.POLICY_NO,a.CRT_DT,a.ST_CDE,idc.CUST_NAME,idc.ID_NO,ups.*
from cl_usr_apply a ,cl_idcard_info idc, (select  DISTINCT psn.USR_CDE,psn.SCORES,psn.CRT_DT from cl_psn_chk psn WHERE psn.MEDIA_DESC='3') as ups
where a.CRT_DT>='2018-07-04 00:00:00' and a.CRT_DT<'2018-07-05' and a.USR_CDE = idc.USR_CDE and a.USR_CDE = ups.USR_CDE
	 * 
	 * 
	 * @param args
	 * @throws Exception
	 */

	public static void main(String[] args) throws Exception {
		List<String> lines = FileUtils.readLines(new File("d:/tishu1.txt"));
		List<String> jieg = new ArrayList<String>();
		for (String line : lines) {
			String fenshu = "";
			String txj = "";
			
			String[] cols = line.split("\t");
			String scores= cols[5];
			
			if("11".equals(cols[2])) {
				txj = "提现成功";
			} else if("10".equals(cols[2])) {
				txj = "提现被拒";
			} else if("13".equals(cols[2])) {
				txj = "放款失败";
			}
			
			String[] tmps = scores.split("\\&");
			for (String tmp : tmps) {
				if(tmp.startsWith("confidence")) {
					fenshu = tmp.split("=")[1];
				}
				
			}
			
			
			String rrr = cols[0] + "\t" +cols[1] + "\t"+txj + "\t"+cols[3] + "\t" + cols[4] + "\t" + fenshu +"\t" + cols[6] + "\t" + cols[7];
			jieg.add(rrr);
			
		}
		
		FileUtils.writeLines(new File("d:/jieguo1.txt"), jieg);
	}
}
