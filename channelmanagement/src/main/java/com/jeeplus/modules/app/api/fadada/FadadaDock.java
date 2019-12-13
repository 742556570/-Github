package com.jeeplus.modules.app.api.fadada;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.jeeplus.modules.app.api.account.loanorderquery.LoanOrderQuery;
import com.jeeplus.modules.app.api.fadada.client.FddClientBase;
import com.jeeplus.modules.app.api.product.PropertiesUtil;

import net.sf.json.JSONObject;

public class FadadaDock {
	private static final String url = PropertiesUtil.getString("fadada.url");
	private static final String appid = PropertiesUtil.getString("fadada.appid");
	private static final String appsecret = PropertiesUtil.getString("fadada.appsecret");
	private static final String customerid = PropertiesUtil.getString("fadada.customerid");
	private static final String version = "2.0";

	private static String path = "";
	
	
	static {
		String tmpPath = FadadaDock.class.getClassLoader().getResource("fddcontracts").getPath();
		
		if(!tmpPath.endsWith(File.separator)) {
			tmpPath = tmpPath + File.separator;
		}
		
		path = tmpPath;
		
	}
	
	/**
	 * 
	  	YGSDPH80000001-1--->1_1-->平台注册及服务协议
		YGSDPH80000002-1--->2_1-->身份验证授权书
		YGSDPH80000003-1--->2_2-->数字证书确认及授权书
		YGSDPH80000004-1--->3_1-->投保须知模板
		YGSDPH80000005-1--->3_2-->投保单模板
		YGSDPH80000006-1--->3_3-->个人征信授权书
		YGSDPH80000007-1--->3_4-->个人信息查询使用授权书
		YGSDPH80000008-1--->4_1-->贷款协议-信用类
		YGSDPH80000009-1--->4_2-->贷款协议-个人信息授权书
		YGSDPH80000014-1--->4_3-->贷款协议-还款代扣授权
		YGSDPH80000011-1--->4_4-->还款事项提醒函
		YGSDPH80000010-1--->5_1-->保险单
		YGSDPH80000012-1--->5_2-->保险单信息确认页
		YGSDPH80000013-1--->5_3-->委托扣款授权书
	 */
	
	
	/**
	 * 合同模板传输
	 * 
	 * @return
	 */
	public String ContractTemplateTransmission() {
		this.baoxiandan();
		/*this.pingtaizhucexieyi();
		this.shenfenzhengshouquanshu();
		this.shuzizhengshushouquanshu();
		this.toubaoxuzhimoban();*/
		this.toubaodanmoban();
		/*this.gerenzhengxinshouquanshu();
		this.gerenxinxishiyongchaxun();
		this.daikuanxieyixinyonglei();
		this.huankuanshixiangtixing();
		this.daikuanxieyigerenxinshouquanshu();
		this.daikuanxieyihuankuandaikou();*/
		this.baoxiandanxinxiquerenye();
		/*this.weituokoukuanshouquanshu();*/
		return "success";
		
	}

	/**
	 * 保险单
	 * 
	 * @return
	 */
	public String baoxiandan() {
		String template_id = "YGSDPH80000010-1";
		String template_name = "保险单.pdf";
		FddClientBase clientbase = new FddClientBase(appid, appsecret, version, url);
		File file = new File(path + template_name);
		String response = clientbase.invokeUploadTemplate(template_id, file,
				path + template_name);
		System.out.println("合同模板id：" + template_id + "--" + "合同模本名称：" + template_name + ":法大大返回结果" + response); // 打印结果，解析
																												// JSON
																												// 报文
		JSONObject json = JSONObject.fromObject(response);
		String result = json.getString("result");
		return result;
	}

	/**
	 * 平台注册及服务协议
	 * 
	 * @return
	 */
	public String pingtaizhucexieyi() {
		String template_id = "YGSDPH80000001-1";
		String template_name = "平台注册及服务协议.pdf";
		FddClientBase clientbase = new FddClientBase(appid, appsecret, version, url);
		File file = new File(path + template_name);
		String response = clientbase.invokeUploadTemplate(template_id, file,
				path + template_name);
		System.out.println("合同模板id：" + template_id + "--" + "合同模本名称：" + template_name + ":法大大返回结果" + response); // 打印结果，解析
																												// JSON
																												// 报文
		JSONObject json = JSONObject.fromObject(response);
		String result = json.getString("result");
		return result;
	}

	/**
	 * 身份验证授权书
	 * 
	 * @return
	 */
	public String shenfenzhengshouquanshu() {
		String template_id = "YGSDPH80000002-1";
		String template_name = "身份验证授权书.pdf";
		FddClientBase clientbase = new FddClientBase(appid, appsecret, version, url);
		File file = new File(path + template_name);
		String response = clientbase.invokeUploadTemplate(template_id, file,
				path + template_name);
		System.out.println("合同模板id：" + template_id + "--" + "合同模本名称：" + template_name + ":法大大返回结果" + response); // 打印结果，解析
																												// JSON
																												// 报文
		JSONObject json = JSONObject.fromObject(response);
		String result = json.getString("result");
		return result;
	}

	/**
	 * 数字证书确认及授权书
	 * 
	 * @return
	 */
	public String shuzizhengshushouquanshu() {
		String template_id = "YGSDPH80000003-1";
		String template_name = "数字证书确认及授权书.pdf";
		FddClientBase clientbase = new FddClientBase(appid, appsecret, version, url);
		File file = new File(path + template_name);
		String response = clientbase.invokeUploadTemplate(template_id, file,
				path + template_name);
		System.out.println("合同模板id：" + template_id + "--" + "合同模本名称：" + template_name + ":法大大返回结果" + response); // 打印结果，解析
																												// JSON
																												// 报文
		JSONObject json = JSONObject.fromObject(response);
		String result = json.getString("result");
		return result;
	}

	/**
	 * 投保须知模板
	 * 
	 * @return
	 */
	public String toubaoxuzhimoban() {
		String template_id = "YGSDPH80000004-1";
		String template_name = "投保须知模板.pdf";
		FddClientBase clientbase = new FddClientBase(appid, appsecret, version, url);
		File file = new File(path + template_name);
		String response = clientbase.invokeUploadTemplate(template_id, file,
				path + template_name);
		System.out.println("合同模板id：" + template_id + "--" + "合同模本名称：" + template_name + ":法大大返回结果" + response); // 打印结果，解析
																												// JSON
																												// 报文
		JSONObject json = JSONObject.fromObject(response);
		String result = json.getString("result");
		return result;
	}

	/**
	 * 投保单模板
	 * 
	 * @return
	 */
	public String toubaodanmoban() {
		String template_id = "YGSDPH80000005-1";
		String template_name = "投保单模板.pdf";
		FddClientBase clientbase = new FddClientBase(appid, appsecret, version, url);
		File file = new File(path + template_name);
		String response = clientbase.invokeUploadTemplate(template_id, file,
				path + template_name);
		System.out.println("合同模板id：" + template_id + "--" + "合同模本名称：" + template_name + ":法大大返回结果" + response); // 打印结果，解析
																												// JSON
																												// 报文
		JSONObject json = JSONObject.fromObject(response);
		String result = json.getString("result");
		return result;
	}

	/**
	 * 个人征信授权书
	 * 
	 * @return
	 */
	public String gerenzhengxinshouquanshu() {
		String template_id = "YGSDPH80000006-1";
		String template_name = "个人征信授权书.pdf";
		FddClientBase clientbase = new FddClientBase(appid, appsecret, version, url);
		File file = new File(path + template_name);
		String response = clientbase.invokeUploadTemplate(template_id, file,
				path + template_name);
		System.out.println("合同模板id：" + template_id + "--" + "合同模本名称：" + template_name + ":法大大返回结果" + response); // 打印结果，解析
																												// JSON
																												// 报文
		JSONObject json = JSONObject.fromObject(response);
		String result = json.getString("result");
		return result;
	}

	/**
	 * 个人信息查询使用授权书
	 * 
	 * @return
	 */
	public String gerenxinxishiyongchaxun() {
		String template_id = "YGSDPH80000007-1";
		String template_name = "个人信息查询使用授权书.pdf";
		FddClientBase clientbase = new FddClientBase(appid, appsecret, version, url);
		File file = new File(path + template_name);
		String response = clientbase.invokeUploadTemplate(template_id, file,
				path + template_name);
		System.out.println("合同模板id：" + template_id + "--" + "合同模本名称：" + template_name + ":法大大返回结果" + response); // 打印结果，解析
																												// JSON
																												// 报文
		JSONObject json = JSONObject.fromObject(response);
		String result = json.getString("result");
		return result;
	}

	/**
	 * 贷款协议-信用类
	 * 
	 * @return
	 */
	public String daikuanxieyixinyonglei() {
		String template_id = "YGSDPH80000008-1";
		String template_name = "贷款协议-信用类.pdf";
		FddClientBase clientbase = new FddClientBase(appid, appsecret, version, url);
		File file = new File(path + template_name);
		String response = clientbase.invokeUploadTemplate(template_id, file,
				path + template_name);
		System.out.println("合同模板id：" + template_id + "--" + "合同模本名称：" + template_name + ":法大大返回结果" + response); // 打印结果，解析
																												// JSON
																												// 报文
		JSONObject json = JSONObject.fromObject(response);
		String result = json.getString("result");
		return result;
	}

	/**
	 * 贷款协议-个人信息授权书
	 * 
	 * @return
	 */
	public String daikuanxieyigerenxinshouquanshu() {
		String template_id = "YGSDPH80000009-1";
		String template_name = "贷款协议-个人信息授权书.pdf";
		FddClientBase clientbase = new FddClientBase(appid, appsecret, version, url);
		File file = new File(path + template_name);
		String response = clientbase.invokeUploadTemplate(template_id, file,
				path + template_name);
		System.out.println("合同模板id：" + template_id + "--" + "合同模本名称：" + template_name + ":法大大返回结果" + response); // 打印结果，解析
																												// JSON
																												// 报文
		JSONObject json = JSONObject.fromObject(response);
		String result = json.getString("result");
		return result;
	}

	/**
	 * 贷款协议-还款代扣授权
	 * 
	 * @return
	 */
	public String daikuanxieyihuankuandaikou() {
		String template_id = "YGSDPH80000014-1";
		String template_name = "贷款协议-还款代扣授权.pdf";
		FddClientBase clientbase = new FddClientBase(appid, appsecret, version, url);
		File file = new File(path + template_name);
		String response = clientbase.invokeUploadTemplate(template_id, file,
				path + template_name);
		System.out.println("合同模板id：" + template_id + "--" + "合同模本名称：" + template_name + ":法大大返回结果" + response); // 打印结果，解析
																												// JSON
																												// 报文
		JSONObject json = JSONObject.fromObject(response);
		String result = json.getString("result");
		return result;
	}
	/**
	 * 还款事项提醒函
	 * 
	 * @return
	 */
	public String huankuanshixiangtixing() {
		String template_id = "YGSDPH80000011-1";
		String template_name = "还款事项提醒函.pdf";
		FddClientBase clientbase = new FddClientBase(appid, appsecret, version, url);
		File file = new File(path + template_name);
		String response = clientbase.invokeUploadTemplate(template_id, file,
				path + template_name);
		System.out.println("合同模板id：" + template_id + "--" + "合同模本名称：" + template_name + ":法大大返回结果" + response); // 打印结果，解析
		// JSON
		// 报文
		JSONObject json = JSONObject.fromObject(response);
		String result = json.getString("result");
		return result;
	}

	/**
	 * 保险单信息确认页
	 * 
	 * @return
	 */
	public String baoxiandanxinxiquerenye() {
		String template_id = "YGSDPH80000012-1";
		String template_name = "保险单信息确认页.pdf";
		FddClientBase clientbase = new FddClientBase(appid, appsecret, version, url);
		File file = new File(path + template_name);
		String response = clientbase.invokeUploadTemplate(template_id, file,
				path + template_name);
		System.out.println("合同模板id：" + template_id + "--" + "合同模本名称：" + template_name + ":法大大返回结果" + response); // 打印结果，解析
																												// JSON
																												// 报文
		JSONObject json = JSONObject.fromObject(response);
		String result = json.getString("result");
		return result;
	}

	/**
	 * 委托扣款授权书
	 * 
	 * @return
	 */
	public String weituokoukuanshouquanshu() {
		String template_id = "YGSDPH80000013-1";
		String template_name = "委托扣款授权书.pdf";
		FddClientBase clientbase = new FddClientBase(appid, appsecret, version, url);
		File file = new File(path + template_name);
		String response = clientbase.invokeUploadTemplate(template_id, file,
				path + template_name);
		System.out.println("合同模板id：" + template_id + "--" + "合同模本名称：" + template_name + ":法大大返回结果" + response); // 打印结果，解析
																												// JSON
																												// 报文
		JSONObject json = JSONObject.fromObject(response);
		String result = json.getString("result");
		return result;
	}
	/**
	 * 获取用户的CA证书
	 * 
	 * @return
	 */
	public String PersonCA(String customer_name,String id_card,String mobile) {
		FddClientBase clientbase = new FddClientBase(appid, appsecret, version, url);
		String ident_type="0";
		String response = clientbase.invokeSyncPersonAuto(customer_name, null, id_card,
		ident_type, mobile);
		System.out.println("获取个人ca的电子签章返回结果："+response); 
		JSONObject json = JSONObject.fromObject(response);
		String customer_id = json.getString("customer_id");
		return customer_id;
	}
	/**
	 * 关键字签章
	 * 
	 * @return
	 */
	public JSONObject invokeExtSignAuto(String customer_id,String contract_id,String doc_title,String sign_keyword) {
		String keyword_strategy ="2";//最后一个关键字签章
		String client_role = "1";// 投资平台
		String notify_url = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHMMSS");
		String date = sdf.format(new Date());
//		String transaction_id = "SD" + date + LoanOrderQuery.random6();
		String transaction_id = UUID.randomUUID().toString().replaceAll("-", "");
		FddClientBase clientbase = new FddClientBase(appid, appsecret, version, url);
		String  response  =clientbase.invokeExtSignAuto(transaction_id,customer_id,client_role,contract_id,doc_title,sign_keyword,keyword_strategy,notify_url);
																												// 报文
		JSONObject json = JSONObject.fromObject(response);
		System.out.println("自动签章接口："+json.toString()); 
		return json;
	}
	
	/**
	 * 合同生成接口
	 * 
	 * @return
	 */
	public JSONObject invokeGenerateContract(String template_id ,String customer_id,String doc_title,JSONObject paramter) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHMMSS");
		String date = sdf.format(new Date());
		String contract_id = UUID.randomUUID().toString().replaceAll("-", "");
		FddClientBase clientbase = new FddClientBase(appid, appsecret, version, url);

		String response = clientbase.invokeGenerateContract(template_id, contract_id,doc_title, "7", "0", paramter.toString(), "");

		JSONObject json = JSONObject.fromObject(response);
		System.out.println("合同生成接口："+json.toString()); 
		json.put("contract_id", contract_id);
		return json;
	}
	/**
	 * 合同归档接口
	 * 
	 * @return
	 */
	public JSONObject invokeContractFilling(String contract_id) {
		
		FddClientBase clientbase = new FddClientBase(appid, appsecret, version, url);
		String response = clientbase.invokeContractFilling(contract_id);
		System.out.println(response);  // 打印结果，解析 JSON 报文
		// 报文
		JSONObject json = JSONObject.fromObject(response);
		System.out.println("合同归档接口："+json.toString()); 
		return json;
	}
	
	/**
	 * 合同模块删除接口
	 * 
	 * @return
	 */
	public String invokeTemplateDelete(List<String> list) {
		for(String template_id:list) {
		FddClientBase clientbase = new FddClientBase(appid, appsecret, version, url);
		String response = clientbase.invokeTemplateDelete(template_id);
		System.out.println("合同模板id：" + template_id +":法大大返回结果" + response); // 打印结果，解析
		// JSON
		// 报文
		JSONObject json = JSONObject.fromObject(response);
		String msg = json.getString("msg");
		System.out.println("删除合同模块返回结果"+msg);
		}
		return null;
	}
	/**
	 * 法大大 签章流程 统一入口
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
	public JSONObject fadadaEntrance(String customer_name,String id_card,String mobile,
			JSONObject json,String template_id,String doc_title,String type,String sign_keyword,
			String policy_no,String usr_cde,String step,String sign_keywordcom) {
		String customer_id ="";
		JSONObject JSONObject = new JSONObject();
		if("1".equals(type)) {
			//获取个人ca的证书
			customer_id =this.PersonCA(customer_name, id_card, mobile);
			//合同生成接口
			JSONObject jsonContract  =this.invokeGenerateContract(template_id, customer_id, doc_title, json);
			System.out.println("合同模板id"+template_id);
			//自动签署接口
			JSONObject jsonSignAuto  =this.invokeExtSignAuto(customer_id,jsonContract.getString("contract_id"), doc_title, sign_keyword);
			
			if(jsonSignAuto.getString("result").equals("success")) {
			//归档接口
				JSONObject jsonFill = this.invokeContractFilling(jsonContract.getString("contract_id"));
				if(jsonFill.getString("result").equals("success")) {
					JSONObject.put("contract_id", jsonContract.getString("contract_id"));
					JSONObject.put("usr_cde", usr_cde);
					JSONObject.put("policy_no", policy_no);
					JSONObject.put("step", step);
					JSONObject.put("template_id", template_id);
					JSONObject.put("customer_id", customer_id);
					JSONObject.put("dl_url", jsonSignAuto.getString("download_url"));
					JSONObject.put("v_url", jsonSignAuto.getString("viewpdf_url"));
					JSONObject.put("result", "success");
					JSONObject.put("msg", "生成成功");
					return JSONObject;
				}
			}
		}else {
			//获取企业ca的证书
			 customer_id =customerid;
			 //个人ca证书
			 String customer_id1 =this.PersonCA(customer_name, id_card, mobile);
			//合同生成接口
			JSONObject jsonContract  =this.invokeGenerateContract(template_id, customer_id, doc_title, json);
				
			//第一步盖企业的
			JSONObject jsonSignAuto  =this.invokeExtSignAuto(customer_id,jsonContract.getString("contract_id"), doc_title, sign_keyword);
			//第二步调个人
			JSONObject jsonSignAuto1  =this.invokeExtSignAuto(customer_id1,jsonContract.getString("contract_id"), doc_title, sign_keywordcom);
			JSONObject jsonFill = this.invokeContractFilling(jsonContract.getString("contract_id"));
			System.out.println("合同模板id"+template_id);
				if(jsonSignAuto.getString("result").equals("success")&&jsonSignAuto1.getString("result").equals("success")) {
				//归档接口
					if(jsonFill.getString("result").equals("success")) {
						JSONObject.put("contract_id", jsonContract.getString("contract_id"));
						JSONObject.put("usr_cde", usr_cde);
						JSONObject.put("policy_no", policy_no);
						JSONObject.put("step", step);
						JSONObject.put("template_id", template_id);
						JSONObject.put("customer_id", customer_id);
						JSONObject.put("dl_url", jsonSignAuto.getString("download_url"));
						JSONObject.put("v_url", jsonSignAuto.getString("viewpdf_url"));
						JSONObject.put("result", "success");
						JSONObject.put("msg", "生成成功");
						return JSONObject;
					}
				}
		}
		
		
		JSONObject.put("result", "fail");
		JSONObject.put("msg", "生成失败");
		return JSONObject;
	}
	
	
	public void delFddCtByStep(String step) {
		List<String> list = new ArrayList<String>();
		if("all".equals(step)) {
			list.add("YGSDPH80000001-1");
			list.add("YGSDPH80000002-1");
			list.add("YGSDPH80000003-1");
			list.add("YGSDPH80000004-1");
			list.add("YGSDPH80000005-1");
			list.add("YGSDPH80000006-1");
			list.add("YGSDPH80000007-1");
			list.add("YGSDPH80000008-1");
			list.add("YGSDPH80000009-1");
			list.add("YGSDPH80000010-1");
			list.add("YGSDPH80000011-1");
			list.add("YGSDPH80000012-1");
			list.add("YGSDPH80000013-1");
			list.add("YGSDPH80000014-1");
		} else if("1-1".equals(step)) {
			list.add("YGSDPH80000001-1");
		} else if("2-1".equals(step)) {
			list.add("YGSDPH80000002-1");
		} else if("2-2".equals(step)) {
			list.add("YGSDPH80000003-1");
		} else if("3-1".equals(step)) {
			list.add("YGSDPH80000004-1");
		} else if("3-2".equals(step)) {
			list.add("YGSDPH80000005-1");
		} else if("3-3".equals(step)) {
			list.add("YGSDPH80000006-1");
		} else if("3-4".equals(step)) {
			list.add("YGSDPH80000007-1");
		} else if("4-1".equals(step)) {
			list.add("YGSDPH80000008-1");
		} else if("4-2".equals(step)) {
			list.add("YGSDPH80000009-1");
		} else if("4-3".equals(step)) {
			list.add("YGSDPH80000014-1");
		} else if("4-4".equals(step)) {
			list.add("YGSDPH80000011-1");
		} else if("5-1".equals(step)) {
			list.add("YGSDPH80000010-1");
		} else if("5-2".equals(step)) {
			list.add("YGSDPH80000012-1");
		} else if("5-3".equals(step)) {
			list.add("YGSDPH80000013-1");
		}
		invokeTemplateDelete(list);
		
	}
	
	
	public String upFddCtByStep(String step) {
		if("all".equals(step)) {
			this.baoxiandan();
			this.pingtaizhucexieyi();
			this.shenfenzhengshouquanshu();
			this.shuzizhengshushouquanshu();
			this.toubaoxuzhimoban();
			this.toubaodanmoban();
			this.gerenzhengxinshouquanshu();
			this.gerenxinxishiyongchaxun();
			this.daikuanxieyixinyonglei();
			this.huankuanshixiangtixing();
			this.daikuanxieyigerenxinshouquanshu();
			this.daikuanxieyihuankuandaikou();
			this.baoxiandanxinxiquerenye();
			this.weituokoukuanshouquanshu();
		} else if("1-1".equals(step)) {
			this.pingtaizhucexieyi();
		} else if("2-1".equals(step)) {
			this.shenfenzhengshouquanshu();
		} else if("2-2".equals(step)) {
			this.shuzizhengshushouquanshu();
		} else if("3-1".equals(step)) {
			this.toubaoxuzhimoban();
		} else if("3-2".equals(step)) {
			this.toubaodanmoban();
		} else if("3-3".equals(step)) {
			this.gerenzhengxinshouquanshu();
		} else if("3-4".equals(step)) {
			this.gerenxinxishiyongchaxun();
		} else if("4-1".equals(step)) {
			this.daikuanxieyixinyonglei();
		} else if("4-2".equals(step)) {
			this.daikuanxieyigerenxinshouquanshu();
		} else if("4-3".equals(step)) {
			this.daikuanxieyihuankuandaikou();
		} else if("4-4".equals(step)) {
			this.huankuanshixiangtixing();
		} else if("5-1".equals(step)) {
			this.baoxiandan();
		} else if("5-2".equals(step)) {
			this.baoxiandanxinxiquerenye();
		} else if("5-3".equals(step)) {
			this.weituokoukuanshouquanshu();
		} else {
			return "fail";
		}
		
		return "success";
		
	}
	
	public String getPath() {
		return path;
	}
	
	public static void main(String[] args) {
		FadadaDock FadadaDock = new FadadaDock();
		List<String> list = new ArrayList<String>();
		/*list.add("YGSDPH80000001-1");
		list.add("YGSDPH80000002-1");
		list.add("YGSDPH80000003-1");
		list.add("YGSDPH80000004-1");
		list.add("YGSDPH80000005-1");
		list.add("YGSDPH80000006-1");
		list.add("YGSDPH80000007-1");
		list.add("YGSDPH80000008-1");
		list.add("YGSDPH80000009-1");
		list.add("YGSDPH80000010-1");
		list.add("YGSDPH80000011-1");
		list.add("YGSDPH80000012-1");
		/*list.add("YGSDPH80000013-1");
		list.add("YGSDPH80000014-1");
		FadadaDock.invokeTemplateDelete(list);
		
		FadadaDock.ContractTemplateTransmission();*/
		
		System.out.println(FadadaDock.getPath());
		
	}
}
