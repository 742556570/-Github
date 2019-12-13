package com.jeeplus.modules.app.test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.utils.DateUtils;

public class Tishu {

	/**
	 * 
	 *
select tmp.*,tt.CRT_DT as cont_DT from 
(select a.* from 
(select cl_usr_contract.USR_CDE,cl_usr_contract.CRT_DT from cl_usr_contract where cl_usr_contract.STEP='3-3' GROUP BY cl_usr_contract.USR_CDE,cl_usr_contract.CRT_DT  ) a
where a.CRT_DT = ( select min(b.CRT_DT) from (select cl_usr_contract.USR_CDE,cl_usr_contract.CRT_DT from cl_usr_contract where cl_usr_contract.STEP='3-3' GROUP BY cl_usr_contract.USR_CDE,cl_usr_contract.CRT_DT ) as b where b.USR_CDE = a.USR_CDE)

) as tt,
(SELECT
cl_crdtext.USR_CDE,
cl_crdtext.USR_APPSEQ,
cl_crdtext.CRDTEXT_DT,
cl_idcard_info.CUST_NAME,
cl_idcard_info.ID_NO,
cl_apiinfo.SERVICE_NAME,
cl_apiinfo.INVOKE_DT,
cl_apiinfo.TIME_SPAN,
cl_apiinfo.RESULT

from 
cl_crdtext,
cl_idcard_info,
cl_apiinfo
WHERE
cl_crdtext.CRT_DT >= '2018-07-04 00:00:00' AND
cl_crdtext.CRT_DT < '2018-07-05' AND
cl_crdtext.USR_CDE = cl_idcard_info.USR_CDE AND
cl_crdtext.USR_CDE = cl_apiinfo.USR_CDE AND
(cl_apiinfo.SERVICE_NAME = 'CHKSELF' OR cl_apiinfo.SERVICE_NAME = 'LIVEVERIFY' OR cl_apiinfo.SERVICE_NAME = 'ID5CLIENT')
ORDER BY cl_idcard_info.ID_NO) as tmp where tmp.USR_CDE = tt.USR_CDE 

	 * 
	 * @param args
	 * @throws Exception
	 */
	
	
	public static void main(String[] args) throws Exception {
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		
		List<String> all = new ArrayList<String>();
		all.add("预授信保单号	授信时间	姓名	身份证号	调用时间	调用时长	返回结果时间	分数	结论");
		String lastUsrCde = "";
		List<String> lines = FileUtils.readLines(new File("D:/tishu.txt"));
		List<String> errors = new ArrayList<String>();
		for (String line : lines) {
			
			String jielun = "";
			String serviceName = "";
			String[] cols = line.split("\t");
			
			if(cols[4].equals("CHKSELF")) {
				serviceName="身份验证";
				//{"trdNo":"2834dad1ff9147f8826a08ec22183b5e","allCnt":1,"allCntLimit":150,"todayCnt":1,"edCntLimit":5,"fppRes":{"time_used":1433,"id_exceptions":{"id_photo_monochrome":0,"id_attacked":0},"result_faceid":{"confidence":84.219,"thresholds":{"1e-3":62.169,"1e-5":74.399,"1e-4":69.315,"1e-6":78.038}},"request_id":"1528355597,58304c3a-fc57-4378-b73d-84eb4e2fbeb4"}}
				JSONObject obj = JSONObject.parseObject(cols[7]);
				JSONObject fppRes = obj.getJSONObject("fppRes");
				if(fppRes == null || fppRes.isEmpty()) {
					jielun = "null\t不通过";
				}else {
					JSONObject result_faceid = fppRes.getJSONObject("result_faceid");
					if(result_faceid == null) {
						jielun = "null\t不通过";
					}else {
						String score = result_faceid.getString("confidence");
						if(score == null) {
							jielun = "null\t不通过";
						}else {
							if(new BigDecimal(score).subtract(new BigDecimal("69.315")).doubleValue()>=0) {
								jielun = score+"\t通过";
							}else {
								jielun = score+"\t不通过";
							}
						}
					}
				}
			
			} else if (cols[4].equals("LIVEVERIFY")) {
				serviceName="活体检测";
				//{"id_exceptions": {"id_photo_monochrome": 0, "id_attacked": 0}, "face_genuineness": {"mask_confidence": 0.0, "screen_replay_confidence": 0.0, "mask_threshold": 0.5, "synthetic_face_confidence": 0.0, "synthetic_face_threshold": 0.5, "screen_replay_threshold": 0.5, "face_replaced": 0}, "request_id": "1530671326,9657d498-93c2-433d-a3c7-4d225aeb0f4d", "time_used": 1803, "result_faceid": {"confidence": 84.153, "thresholds": {"1e-3": 62.169, "1e-5": 74.399, "1e-4": 69.315, "1e-6": 78.038}}}
				JSONObject obj = JSONObject.parseObject(cols[7]);
				JSONObject result_faceid = obj.getJSONObject("result_faceid");
				String score = result_faceid.getString("confidence");
				if(new BigDecimal(score).subtract(new BigDecimal("69.315")).doubleValue()>=0) {
					jielun = score+"\t通过";
				}else {
					jielun = score+"\t不通过";
				}
				
				
			} else if(cols[4].equals("ID5CLIENT")) {
				//
				serviceName="银行卡信息提交";
				if("7".equals(cols[7])) {
					jielun = "四要素一致\t通过";
				}else {
					jielun = "四要素不一致\t不通过";
				}
			} else {
				errors.add(line);
			}
			
			Date in_dt = DateUtils.parseDate(cols[5]);
			
			long end = in_dt.getTime() +  Long.parseLong(cols[6]);
			
			Date en_dt = new Date(end);
			
			String jieshu = DateUtils.formatDate(en_dt, "yyyy-MM-dd HH:mm:ss");
			
			String  rrr = cols[0] + "\t" + cols[1] + "\t" +cols[2] + "\t"+cols[3] + "\t"+serviceName+"\t"+cols[5] + "\t" +cols[6] + "\t"+jieshu+"\t"+jielun;
			
			
			System.out.println(rrr);
			
			
			List<String> group = map.get(cols[0]);
			if(group == null) {
				group = new ArrayList<String>();
				String  rss = cols[0] + "\t" + cols[1] + "\t" +cols[2] + "\t"+cols[3] + "\t"+"生成授权书"+"\t"+"-" + "\t" + "-" + "\t"+cols[8]+"\t"+"\t通过";
				group.add(rss);
				group.add(rrr);
				map.put(cols[0], group);
			}else {
				if(jielun.contains("\t通过"))
					group.add(rrr);
			}
			
			
			
			
			
		}
		
		Set<String> keys = map.keySet();
		for (String key : keys) {
			List<String> t = map.get(key);
			String i1 = t.get(0);
			t.remove(0);
			t.add(i1);
			 for (String string : t) {
				all.add(string);
			}
		}
		
		FileUtils.writeLines(new File("d:/jieguo.txt"), all);
	}
}
