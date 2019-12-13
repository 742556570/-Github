package com.jeeplus.modules.app.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.modules.app.api.product.PropertiesUtil;
import com.jeeplus.modules.app.dao.ClUsrApplyDao;
import com.jeeplus.modules.app.dao.ClUsrLoanDao;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.entity.ClUsrApply;
import com.jeeplus.modules.app.entity.ClUsrLoan;
import com.jeeplus.modules.app.utils.FtpConCreditManger;
import com.jeeplus.modules.app.utils.GenerateFile;

@Service
@Transactional(readOnly = false)
public class CreditReportService {

	@Autowired
	private ClUsrApplyDao applyDao;
	
	@Autowired
	private ClUsrLoanDao  loanDao;

	// 法人姓名
	private static final String LEGALPERSON = "中国对外经济贸易信托有限公司 ";
	// 法人身份号
	private static final String LEGALPERSONIDCARD = "91110000100006653M";
	// 人行为阳光分配的金融机构代码
	private static final String DEPTCODE = PropertiesUtil.getString("cre.deptcode");
	
	private static final String LNPURP = "";

	public void getFile(ClUsr usr, String policyNo) {
		String msg = "";
		ClUsrApply apply = applyDao.getByPolicyNo(policyNo);
		// 保险业务基础信息
		if (!isGibBaseFile(policyNo)) {
			msg += "保单号为:" + policyNo + "保险业务基础信息。";
			System.out.println(msg);
			return;
		}
		// 保险合同信息
		if (!isGidContractFile(apply)) {
			msg += "保单号为:" + policyNo + "保险合同信息。";
			System.out.println(msg);
			return;
		}
		// 债权人和主合同信息
		if (!isGifCreditorFile(apply)) {
			msg += "保单号为:" + policyNo + "债权人和主合同信息。";
			System.out.println(msg);
			return;
		}

	}

	/**
	 * 生成保险业务基础信息拼接传输
	 * 
	 * @param policyNo
	 * @return
	 */
	private boolean isGibBaseFile(String policyNo) {
		try {
			String fileName = "GIB_BASE.txt";
			String gibBaseStr = DEPTCODE + "\t" + policyNo + "\t" + policyNo + "\t" + "1" + "\t" + LEGALPERSON + "\t"
					+ "d" + LEGALPERSONIDCARD;
			this.genFile(gibBaseStr, fileName);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 生成保险合同信息
	 * @param apply
	 * @return
	 */
	private boolean isGidContractFile(ClUsrApply apply) {
		try {
			BigDecimal applyAmt = new BigDecimal(apply.getApplyAmt());
			BigDecimal totalAmt = applyAmt.multiply(new BigDecimal("1.2"));
			
			ClUsrLoan loan = loanDao.selectByPrimaryKey(apply.getPolicyNo());
			if(loan == null) {
				return false;
			}
			String startDt = DateUtils.formatDate(loan.getBeginDt(), "yyyy-MM-dd");
			String endDt = DateUtils.formatDate(loan.getEndDt(), "yyyy-MM-dd");
			String PREMIUMRATE = new BigDecimal(apply.getPremiumRate()).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP).toEngineeringString();
			
			String ANNUALRATE = new BigDecimal(PREMIUMRATE).multiply(new BigDecimal("12")).toEngineeringString();
			
			
			String fileName = "GID_CONTRACT.txt";
			String gibBaseStr = DEPTCODE + "\t" + apply.getPolicyNo() + "\t" + "01" + "\t" + "1" + "\t" + totalAmt + "\t" + startDt +"\t" + endDt +"\t"
					+ "0" + "\t" + "x"+"\t" + "0" + "\t" + PREMIUMRATE + "\t" + ANNUALRATE;
//					+ apply.getPolicyNo() + "\t" + apply.getPolicyNo() + "\t" + "x" + "\t" + "0";
			this.genFile(gibBaseStr, fileName);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 生成债权人和主合同信息
	 * @param apply
	 * @return
	 */
	private boolean isGifCreditorFile(ClUsrApply apply) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String fileName = "GIF_CREDITOR.txt";
			String gibBaseStr = DEPTCODE + "\t" + apply.getPolicyNo() + "\t" + "1" + "\t" + LEGALPERSON + "\t"
					+ "z" + "\t" + LEGALPERSONIDCARD  +"\t" + apply.getPolicyNo() + "\t"
					+ apply.getPolicyNo() + "\t" + LNPURP + "\t" + "1" ;
			this.genFile(gibBaseStr, fileName);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	
	
	/**
	 * 生成文件
	 */
	private boolean genFile(String str, String fileName) {
		GenerateFile generateFile = new GenerateFile();

		// 生成文件放在本地
		String tmp = generateFile.setJsonData(str, PropertiesUtil.getString("cre.baseurl") + fileName);
		if(tmp == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * 上传所有文件至ftp
	 */
	public boolean SFtpAll() {
		boolean a  = SFtp("GIB_BASE.txt");
		boolean b = SFtp("GID_CONTRACT.txt");
		boolean c = SFtp("GIF_CREDITOR.txt");
		return a&&b&&c;
	}
	
	
	public boolean SFtp(String fileName) {
		FtpConCreditManger sftp = null;

		// Sftp上传路径
		String sftpPath = PropertiesUtil.getString("cre.url") + "/" + this.fomatDate();
		try {
			sftp = new FtpConCreditManger(PropertiesUtil.getString("cre.ip"), PropertiesUtil.getString("cre.name"),
					PropertiesUtil.getString("cre.password"));
			sftp.connect();
			sftp.mkdirs(sftpPath);
			// 上传
			return sftp.uploadFile(sftpPath, fileName, PropertiesUtil.getString("cre.baseurl"), fileName);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sftp.disconnect();
		}
		return false;
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

		// 生成文件放在本地
		generateFile.setJsonData(str, PropertiesUtil.getString("cre.baseurl") + fileName);

		// 将本地的文件传输到ftp上面
		FtpConCreditManger sftp = null;

		// Sftp上传路径
		String sftpPath = PropertiesUtil.getString("cre.url") + "/" + this.fomatDate();
		try {
			sftp = new FtpConCreditManger(PropertiesUtil.getString("cre.ip"), PropertiesUtil.getString("cre.name"),
					PropertiesUtil.getString("cre.password"));
			sftp.connect();
			sftp.mkdirs(sftpPath);
			// 上传
			return sftp.uploadFile(sftpPath, fileName, PropertiesUtil.getString("cre.baseurl"), fileName);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sftp.disconnect();
		}
		return false;
	}

	/**
	 * 格式化时间
	 * 
	 * @return
	 */
	public String fomatDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		return sdf.format(date);
	}

	/**
	 * 根据开始时间加月份得到结束时间
	 * 
	 * @return
	 */
	public String fomatDateMonth(String dateb, String month) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = sdf.parse(dateb);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, Integer.parseInt(month));
			date = calendar.getTime();
			return sdf.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 去掉时分秒
	 * 
	 * @return
	 */
	public String fomatDateDel(String dateb) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = sdf.parse(dateb);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			date = calendar.getTime();
			return sdf.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
