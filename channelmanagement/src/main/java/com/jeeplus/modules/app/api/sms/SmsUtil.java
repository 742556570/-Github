package com.jeeplus.modules.app.api.sms;

import java.net.URL;

import javax.xml.rpc.holders.StringHolder;
import javax.xml.rpc.encoding.Serializer;

import SmsNewOperator_pkg.MtNewMessage;
import SmsNewOperator_pkg.SmsNewOperator;
import SmsNewOperator_pkg.SmsNewOperatorServiceLocator;

public class SmsUtil {

	public static void main(String[] args) {
		new SmsUtil().sendRgsCde("18210153006", "123456");
	}
	
	
	public boolean sendRgsCde(String tel, String content) {

//		String idNumber = "A50";
//		SmsMessage smsMessage = new SmsMessage("", "", 0, "", 0.00d, null, 0.00d, "S10000001", "123", null, null);
//		smsMessage.setMessageType("1");
//		smsMessage.setContent(content);
//		smsMessage.setPhoneNumber(tel);
//		MtNewMessage msg = new MtNewMessage();
//
//		smsMessage.setOrganizationCode("00040000");// 默认的机构
//		msg.setContent(smsMessage.getContent());
//		msg.setPhoneNumber(smsMessage.getPhoneNumber());
//		StringHolder sendResMsg = new StringHolder();
//		StringHolder errMsg = new StringHolder();
//		SmsNewOperatorServiceLocator locator = new SmsNewOperatorServiceLocator();
//		try {
//			SmsNewOperator smsOerator = locator
//					.getSmsNewOperator(new URL("http://sms.sinosig.com:7001/flexsms/services/SmsNewOperator"));
//
//			smsOerator.sendSms(smsMessage.getOrganizationCode(), idNumber, msg, sendResMsg, errMsg);
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
		
		Sms253Util util = new Sms253Util();
		boolean result = util.sendRgsCde(tel, content);
		return result;
	}

}

class SmsMessage {
	private String content; // 短信内容
	private String phoneNumber;// 要发送短信的手机号码 如果多个用英文逗号（,）隔开
	private String organizationCode;// 机构
	private String emailAddr;// 邮件地址，如果多个用英文逗号（,）隔开
	private String emailContent;// 邮件内容
	private String messageType;// 短信类型1）申请提交时 2）审批通过时
	private String custName;// 客户姓名
	private Integer emailTYpe;// 邮件类别
	private String certCode;// 身份证号码
	private Double applAmt;// 申请金额
	private String applTerm;// 申请期限
	private Double applRate;// 申请费率
	private String applSeq;//
	private String inputId;// 销售员
	private String backReasion;// 回退信息
	private String memo;// 备注
	private String sdate;// 放款时间

	public String getCertCode() {
		return certCode;
	}

	public void setCertCode(String certCode) {
		this.certCode = certCode;
	}

	public Double getApplAmt() {
		return applAmt;
	}

	public void setApplAmt(Double applAmt) {
		this.applAmt = applAmt;
	}

	public String getApplTerm() {
		return applTerm;
	}

	public void setApplTerm(String applTerm) {
		this.applTerm = applTerm;
	}

	public Double getApplRate() {
		return applRate;
	}

	public void setApplRate(Double applRate) {
		this.applRate = applRate;
	}

	public String getApplSeq() {
		return applSeq;
	}

	public void setApplSeq(String applSeq) {
		this.applSeq = applSeq;
	}

	public String getInputId() {
		return inputId;
	}

	public void setInputId(String inputId) {
		this.inputId = inputId;
	}

	public String getBackReasion() {
		return backReasion;
	}

	public void setBackReasion(String backReasion) {
		this.backReasion = backReasion;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getEmailTYpe() {
		return emailTYpe;
	}

	public void setEmailTYpe(Integer emailTYpe) {
		this.emailTYpe = emailTYpe;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public String getEmailAddr() {
		return emailAddr;
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	public String getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	/**
	 * 为了方便规定邮件中的必输或不笔数，直接在构造函数中对必输参数赋值，不用在调用set方法 短信构造函数
	 * 
	 * @param content
	 *            短信内容（必输）
	 * @param phoneNumber
	 *            要发送的手机号码（必输） 如果多个用英文逗号（,）隔开
	 * @param organizationCode
	 *            发送机构（必输）
	 * @param custName
	 *            客户名称（必输）
	 * @param messageType
	 *            短信类别1）申请提交时 2）审批通过时（必输）
	 */
	public SmsMessage(String content, String phoneNumber, String organizationCode, String custName,
			String messageType) {
		this.content = content;
		this.phoneNumber = phoneNumber;
		this.organizationCode = organizationCode;
		this.custName = custName;
		this.messageType = messageType;
	}

	/**
	 * 为了方便规定邮件中的必输或不笔数，直接在构造函数中对必输参数赋值，不用在调用set方法 邮件接口构造函数
	 * 
	 * @param emailAddr
	 *            邮件地址（必输） 如果多个用英文逗号（,）隔开
	 * @param emailContent
	 *            邮件内容（暂时不用，可以赋值为null）
	 * @param custName
	 *            客户名称（必输）
	 * @param emailTYpe
	 *            邮件类别（必输） 411 放款通知邮件 如果是411则 模板411去掉不用，现放款通知邮件模板为418 410
	 *            审批结果邮件--审批通过 409 审批结果邮件--审批拒绝 406 审批结果邮件--审批回退
	 * @param certCode
	 *            身份证号（必输）
	 * @param applAmt
	 *            申请金额（申请类型为411或者410时必输，其他类型可以为null）
	 * @param applTerm
	 *            申请期限（申请类型为411或者410时必输，其他类型可以为null）
	 * @param applRate
	 *            申请费率（申请类型为410时必输，其他类型可以为0）
	 * @param applSeq
	 *            申请流水号（申请类型为409或者410时必输，其他类型可以为null）
	 * @param inputId
	 *            销售员（申请类型为409或者410时必输，其他类型可以为null）
	 * @param backReasion
	 *            回退/拒绝原因（申请类型为406或者409时必输，其他类型可以为null）
	 * @param memo
	 *            回退备注（申请类型为406时必输，其他类型可以为null）
	 */
	public SmsMessage(String emailAddr, String custName, Integer emailTYpe, String certCode, Double applAmt,
			String applTerm, Double applRate, String applSeq, String inputId, String backReasion, String memo) {
		try {
			this.emailAddr = emailAddr;
		} catch (Exception e) {
			// TODO: handle exception
			this.emailAddr = "";
		}

		/*
		 * try { this.emailContent=emailContent; } catch (Exception e) { // TODO: handle
		 * exception this.emailContent=""; }
		 */

		try {
			this.custName = custName;
		} catch (Exception e) {
			// TODO: handle exception
			this.custName = "";
		}

		try {
			this.emailTYpe = emailTYpe;
		} catch (Exception e) {
			// TODO: handle exception
			this.emailTYpe = 411;
		}

		try {
			this.certCode = certCode;
		} catch (Exception e) {
			// TODO: handle exception
			this.certCode = "";
		}

		try {
			this.applAmt = applAmt;
		} catch (Exception e) {
			// TODO: handle exception
			this.applAmt = 0.00;
		}

		try {
			this.applTerm = applTerm;
		} catch (Exception e) {
			// TODO: handle exception
			this.applTerm = "0";
		}

		try {
			this.applRate = applRate;
		} catch (Exception e) {
			// TODO: handle exception
			this.applRate = 0.00;
		}

		try {
			this.applSeq = applSeq;
		} catch (Exception e) {
			// TODO: handle exception
			this.applSeq = "";
		}

		try {
			this.inputId = inputId;
		} catch (Exception e) {
			// TODO: handle exception
			this.inputId = "";
		}

		try {
			this.backReasion = backReasion;
		} catch (Exception e) {
			// TODO: handle exception
			this.backReasion = "";
		}

		try {
			this.memo = memo;
		} catch (Exception e) {
			// TODO: handle exception
			this.memo = "";
		}

	}

	/**
	 * 为了方便规定邮件中的必输或不笔数，直接在构造函数中对必输参数赋值，不用在调用set方法,放款通知邮件。 邮件接口构造函数
	 * 
	 * @param emailAddr
	 *            邮件地址（必输） 如果多个用英文逗号（,）隔开
	 * @param emailContent
	 *            邮件内容（暂时不用，可以赋值为null）
	 * @param custName
	 *            客户名称（必输）
	 * @param emailTYpe
	 *            邮件类别（必输） 418 放款通知邮件 411模板去掉不用，现放款邮件模板为418
	 * @param certCode
	 *            身份证号（必输）
	 * @param applAmt
	 *            申请金额（申请类型为411或者410时必输，其他类型可以为null）
	 * @param applTerm
	 *            申请期限（申请类型为411或者410时必输，其他类型可以为null）
	 * @param applRate
	 *            申请费率（申请类型为410时必输，其他类型可以为0）
	 * @param applSeq
	 *            申请流水号（申请类型为409或者410时必输，其他类型可以为null）
	 * @param inputId
	 *            销售员（申请类型为409或者410时必输，其他类型可以为null）
	 * @param backReasion
	 *            回退/拒绝原因（申请类型为406或者409时必输，其他类型可以为null）
	 * @param memo
	 *            回退备注（申请类型为406时必输，其他类型可以为null）
	 * @param sdate
	 *            放款时间（申请类型为411时必输，其他类型可以为null）
	 */
	public SmsMessage(String emailAddr, String custName, Integer emailTYpe, String certCode, Double applAmt,
			String applTerm, Double applRate, String applSeq, String inputId, String backReasion, String memo,
			String sdate) {
		try {
			this.emailAddr = emailAddr;
		} catch (Exception e) {
			this.emailAddr = "";
		}

		try {
			this.custName = custName;
		} catch (Exception e) {
			this.custName = "";
		}

		try {
			this.emailTYpe = emailTYpe;
		} catch (Exception e) {
			this.emailTYpe = 418;
		}

		try {
			this.certCode = certCode;
		} catch (Exception e) {
			this.certCode = "";
		}

		try {
			this.applAmt = applAmt;
		} catch (Exception e) {
			this.applAmt = 0.00;
		}

		try {
			this.applTerm = applTerm;
		} catch (Exception e) {
			this.applTerm = "0";
		}

		try {
			this.applRate = applRate;
		} catch (Exception e) {
			this.applRate = 0.00;
		}

		try {
			this.applSeq = applSeq;
		} catch (Exception e) {
			this.applSeq = "";
		}

		try {
			this.inputId = inputId;
		} catch (Exception e) {
			this.inputId = "";
		}

		try {
			this.backReasion = backReasion;
		} catch (Exception e) {
			this.backReasion = "";
		}

		try {
			this.memo = memo;
		} catch (Exception e) {
			this.memo = "";
		}

		try {
			this.sdate = sdate;
		} catch (Exception e) {
			this.sdate = "";
		}

	}
}