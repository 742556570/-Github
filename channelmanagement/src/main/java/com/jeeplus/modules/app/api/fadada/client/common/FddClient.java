package com.jeeplus.modules.app.api.fadada.client.common;

/**
 * <h3>概要:</h3> 接口调用基础类 <br>
 * <h3>功能:</h3>
 * <ol>
 * <li>实例客户端</li>
 * </ol>
 * <h3>履历:</h3>
 * <ol>
 * <li>2017年3月16日[zhouxw] 新建</li>
 * </ol>
 */
public class FddClient {

	public FddClient(String appId, String secret, String version, String url) {
		this.appId = appId;
		this.secret = secret;
		this.version = version;
		this.url = url;
	}

	public static final String API_SUFFIX = ".api";

	/** appid */
	private String appId;
	/** app_secret */
	private String secret;
	/** 版本号，建议填写，默认2.0 */
	private String version;
	/** 接口地址，见对接邮件 */
	private String url;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	// ========================TODO 基础接口 start======================
	/**
	 * 获取接口地址：个人CA证书申请接口
	 * @author: zhouxw
	 * @date: 2017年3月16日
	 * @return
	 */
	public String getURLOfSyncPersonAuto() {
		return this.url + "syncPerson_auto" + API_SUFFIX;
	}

	/**
	 * 获取接口地址：文档传输接口
	 * @author: zhouxw
	 * @date: 2017年3月16日
	 * @return
	 */
	public String getURLOfUploadDocs() {
		return this.url + "uploaddocs" + API_SUFFIX;
	}

	/**
	 * 获取接口地址：合同模板传输接口
	 * @author: zhouxw
	 * @date: 2017年3月16日
	 * @return
	 */
	public String getURLOfUploadTemplate() {
		return this.url + "uploadtemplate" + API_SUFFIX;
	}

	/**
	 * 获取接口地址：合同生成接口
	 * @author: zhouxw
	 * @date: 2017年3月16日
	 * @return
	 */
	public String getURLOfGenerateContract() {
		return this.url + "generate_contract" + API_SUFFIX;
	}

	/**
	 * 合同模板删除接口
	 * @author: zhouxw
	 * @date: 2017年3月16日
	 * @return
	 */
	public String getURLinvokeTemplateDelete() {
		return this.url + "template_delete" + API_SUFFIX;
	}

	/**
	 * 获取接口地址：文档签署接口（手动签署模式）
	 * @author: zhouxw
	 * @date: 2017年3月16日
	 * @return
	 */
	public String getURLOfExtSign() {
		return this.url + "extsign" + API_SUFFIX;
	}

	/**
	 * 获取接口地址：文档签署接口（自动签署模式）
	 * @author: zhouxw
	 * @date: 2017年3月16日
	 * @return
	 */
	public String getURLOfExtSignAuto() {
		return this.url + "extsign_auto" + API_SUFFIX;
	}

	/**
	 * 获取接口地址：客户签署状态查询接口
	 * @author: zhouxw
	 * @date: 2017年3月16日
	 * @return
	 */
	public String getURLOfQuerySignStatus() {
		return this.url + "query_signstatus" + API_SUFFIX;
	}

	/**
	 * 获取接口地址：归档接口
	 * @author: zhouxw
	 * @date: 2017年3月16日
	 * @return
	 */
	public String getURLOfContractFilling() {
		return this.url + "contractFiling" + API_SUFFIX;
	}

	// ========================基础接口 end========================

	// ========================TODO 扩展接口 start======================

	/**
	 * 获取接口地址：修改用户信息
	 * @author: zhouxw
	 * @date: 2017年3月16日
	 * @return
	 */
	public String getURLOfInfoChange() {
		return this.url + "infochange" + API_SUFFIX;
	}

	/**
	 * 获取接口地址：合同下载接口
	 * @author: zhouxw
	 * @date: 2017年3月16日
	 * @return
	 */
	public String getURLOfDownloadPdf() {
		return this.url + "downLoadContract" + API_SUFFIX;
	}

	/**
	 * 获取接口地址：合同查看接口
	 * @author: zhouxw
	 * @date: 2017年3月16日
	 * @return
	 */
	public String getURLOfViewContract() {
		return this.url + "viewContract" + API_SUFFIX;
	}

	/**
	 * 获取接口地址：合同验签接口
	 * @author: zhouxw
	 * @date: 2017年3月16日
	 * @return
	 */
	public String getURLOfContractVerify() {
		return this.url + "contract_verify" + API_SUFFIX;
	}

	/**
	 * 获取接口地址：查询合同HASH值接口
	 * @author: zhouxw
	 * @date: 2017年3月16日
	 * @return
	 */
	public String getURLOfContractHash() {
		return this.url + "getContractHash" + API_SUFFIX;
	}

	/**
	 * 获取接口地址：文档签署接口（含有效期和次数）
	 * @author: zhouxw
	 * @date: 2017年3月16日
	 * @return
	 */
	public String getURLOfExtSignValidation() {
		return this.url + "extsign_validation" + API_SUFFIX;
	}

	/**
	 * 获取接口地址：文档临时查看下载地址接口（带有效期和次数）
	 * @author: zhouxw
	 * @date: 2017年3月16日
	 * @return
	 */
	public String getURLOfViewUrl() {
		return this.url + "geturl" + API_SUFFIX;
	}
	// ========================扩展接口 end========================
}
