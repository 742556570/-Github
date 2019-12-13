package com.jeeplus.modules.app.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.app.api.account.loanorderquery.LoanOrderQuery;
import com.jeeplus.modules.app.api.account.loanorderquery.response.LoanOrderDetail;
import com.jeeplus.modules.app.api.account.loanorderquery.response.OUT;
import com.jeeplus.modules.app.entity.ClIdCardInfo;
import com.jeeplus.modules.app.entity.ClUsrApply;
import com.jeeplus.modules.app.entity.ClUsrLoan;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional(readOnly = false)
public class ClUpdatePaymentService {

	private final static Logger logger = LoggerFactory.getLogger(ClUpdatePaymentService.class);
	
	@Autowired
	private LoanOrderQueryService loanOrderQueryService ;
	
	@Autowired
	private ClUsrApplyService applyService;
	
	@Autowired
	private ClIdCardInfoService idCardInfoService;
	
	@Autowired
	private WithRecoverQueryService withRecoverQueryService;
	
	@Autowired
	private ClUsrApplyService usrApplyService;

	
	public void getLoanOrderQuery()  {
		
		List<ClUsrApply> list = applyService.getUsrApplyLoaned();
		
		for (ClUsrApply clUsrApply : list) {
			String POLICY_NO= clUsrApply.getPolicyNo();
			String USR_CDE = clUsrApply.getUsrCde();
			if(StringUtils.isNotEmpty(POLICY_NO) && StringUtils.isNotEmpty(USR_CDE)) {
				try {
					logger.info("更新用户id:"+USR_CDE+"账单信息,开始");
					queryAndUpdate(USR_CDE);
					logger.info("更新用户id:"+USR_CDE+"账单信息,完毕");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.info("更新用户id:"+USR_CDE+"账单信息,异常");
				}
			}
		}
		
	}
	
	
	public void getLoanOrderQuery(String USR_CDE)  {
			
		try {
			logger.info("更新用户id:"+USR_CDE+"账单信息,开始");
			queryAndUpdate(USR_CDE);
			logger.info("更新用户id:"+USR_CDE+"账单信息,完毕");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("更新用户id:"+USR_CDE+"账单信息,异常");
		}
	}
	
	
	
	public void queryAndUpdate(String USR_CDE) throws ParseException {
		
		ClIdCardInfo clIdCardInfo =idCardInfoService.getByUsrCode(USR_CDE);
		LoanOrderQuery loanOrderQuery = new LoanOrderQuery();
		OUT OUT = loanOrderQuery.loanOrderQuery(clIdCardInfo.getID_NO());
		logger.info("贷款还款计划查询接口返回结果" + OUT.toString());
		
		if (OUT.getBODY().getRECORD().getHANDLECODE().equals("00006")) {
			JSONArray json = JSONArray.fromObject(OUT.getBODY().getRECORD().getCONTENT()); // 首先把字符串转成 JSONArray 对象
			if (json.size() > 0) {
				for (int i = 0; i < json.size(); i++) {
					JSONObject job = json.getJSONObject(i); // 遍历 jsonarray 数组，把每一个对象转成 json 对象
					LoanOrderDetail detail = (LoanOrderDetail)JSONObject.toBean(job, LoanOrderDetail.class);
					System.out.println(detail.toString());
					//如果追偿的表中追偿状态为Y 
					boolean setLind = withRecoverQueryService.selectByPoliyNoToSetLind(String.valueOf(job.get("guarantyid")));
					if(setLind){
						detail.setAccflag("2");
						updateApplyStatusByPolicyNo(String.valueOf(job.get("guarantyid")));
					}
					ClUsrLoan clUsrLoan = setLoanOrderData(detail,USR_CDE);
					System.out.println(detail.toString());
					ClUsrLoan inDbUsrLoan = loanOrderQueryService.getselectByPrimaryKey(String.valueOf(job.get("guarantyid")));
					if(inDbUsrLoan == null) {
						boolean resultOrder = loanOrderQueryService.insertLoanOrder(clUsrLoan);
						if (resultOrder) {
							logger.info("小额信用贷款查询插入数据库成功" + resultOrder);
						}
					}else {
						loanOrderQueryService.updateByObj(clUsrLoan);
						String deptS = applyStsMappingZWSts(clUsrLoan.getAccFlag());
						String policyNo = clUsrLoan.getPolicyNo();
						String time = DateUtils.getNowFullFmt();
						if(!setLind){
							usrApplyService.updateDebtByPolicyNo(deptS, time, policyNo);
						}
					}
					
				}
			}
		}
	}
	/**
	 * 解析参数 设值
	 * @param job
	 * @return
	 * @throws ParseException
	 */
	private ClUsrLoan setLoanOrderData(LoanOrderDetail detail,String USR_CDE) throws ParseException {
		int applyID = loanOrderQueryService.getApplyId();
		ClUsrLoan clUsrLoan = new ClUsrLoan();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date enddate = null;
		Date sdate = null;
		if(!StringUtils.isEmpty(detail.getSdate())){
			 sdate = sdf.parse(detail.getSdate());
		}
		
		if(!StringUtils.isEmpty(detail.getEnddate())){
			enddate = sdf.parse(detail.getEnddate());
		}
		Date begindate = null;
		if(!StringUtils.isEmpty(detail.getBegindate())){
			begindate = sdf.parse(detail.getBegindate());
		}
		clUsrLoan.setLoanSeq(applyID);
		clUsrLoan.setUsrCde(USR_CDE);
		clUsrLoan.setApplCde("");
		clUsrLoan.setLoanAcno("");
		clUsrLoan.setEndDt(enddate);
		clUsrLoan.setsDate(sdate);
		clUsrLoan.setMdfDt(new Date());
		clUsrLoan.setPolicyNo(detail.getGuarantyid());
		clUsrLoan.setAccFlag(detail.getAccflag());
		clUsrLoan.setBal(detail.getBal().toString());
		clUsrLoan.setBeginDt(begindate);
		clUsrLoan.setLoanAmt(detail.getLoanamt());
		clUsrLoan.setLoanTerm(detail.getLoanterm());
		clUsrLoan.setRetuKind(detail.getRetukind());
		clUsrLoan.setsTerm(detail.getSterm());
		logger.info("借款订单插入数据库数据" + clUsrLoan.toString());
		return clUsrLoan;
	}
	
	
	/**
	 *	根据保单号修改申请表中装态 
	 */
	private boolean updateApplyStatusByPolicyNo(String policyNo) {
		
		return usrApplyService.updateStatusByPolicyNo(policyNo);
	}
	
	
	
	
	private String applyStsMappingZWSts(String zWSts) {
		if("0".equals(zWSts)) {
			return "30";
		} else if("1".equals(zWSts)) {
			return "31";
		} else if("2".equals(zWSts)) {
			return "40";
		} else if("3".equals(zWSts)) {
			return "42";
		} else if("4".equals(zWSts)) {
			return "41";
		}
		return "";
	}
	
	
}
