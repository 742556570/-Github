package com.jeeplus.modules.app.web;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.modules.app.api.Notify.UnSysNotify;
import com.jeeplus.modules.app.api.Notify.request.UnSysNotifyVO;
import com.jeeplus.modules.app.api.withholdrecover.WithholdRecover;
import com.jeeplus.modules.app.api.withholdrecover.response.RespWithholdRecoverlVO;
import com.jeeplus.modules.app.entity.ClWithRecover;
import com.jeeplus.modules.app.pojo.ResponeJsonObj;
import com.jeeplus.modules.app.service.WithRecoverQueryService;

/**
 * 代扣追产接口
 * 
 * @author 阳光保险
 *
 */
@Controller
@RequestMapping(value = "api/sysCollection")
public class WithholdRecoverController {

	private final static Logger logger = LoggerFactory.getLogger(WithholdRecoverController.class);
	@Autowired
	private WithRecoverQueryService withRecoverQueryService;
	/**
	 * 代扣追偿接口
	 * 
	 * @param request
	 * @param response
	 * @param QUERY_DT
	 * @return
	 * @throws ParseException
	 */
	@ResponseBody
	@RequestMapping(value = "getWithholdRecover")
	public String getWithholdRecover(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "QUERY_DT") String QUERY_DT) throws ParseException {
		long startTime = System.currentTimeMillis();    //获取开始时间
		
		WithholdRecover withholdRecover = new WithholdRecover();
		String QUERY_TYPE1 ="CLAIS";//理赔
		RespWithholdRecoverlVO respWithholdRecoverlVO1 = withholdRecover.withholdRecover(QUERY_DT,QUERY_TYPE1);
		logger.info("理赔追偿接口返回结果" + respWithholdRecoverlVO1.toString());
		if (respWithholdRecoverlVO1.getErrorCode().equals("00000")) {
			for (RespWithholdRecoverlVO respWithholdRecoverlVOList : respWithholdRecoverlVO1.getPoliceList()) {
				// 将查询的数据插库
				insertClWithRecoverData(respWithholdRecoverlVOList,QUERY_TYPE1);
			
			long endTime = System.currentTimeMillis();    //获取结束时间
			logger.info("程序运行时间：" + (endTime - startTime) + "ms");    //输出程序运行时间
			}
		}
		String QUERY_TYPE ="SUBROGATION";//追偿
		RespWithholdRecoverlVO respWithholdRecoverlVO = withholdRecover.withholdRecover(QUERY_DT,QUERY_TYPE);
		logger.info("代扣追偿接口返回结果" + respWithholdRecoverlVO.toString());
		if (respWithholdRecoverlVO.getErrorCode().equals("00000")) {
			for (RespWithholdRecoverlVO respWithholdRecoverlVOList : respWithholdRecoverlVO.getPoliceList()) {
				// 将查询的数据插库
				insertClWithRecoverData(respWithholdRecoverlVOList,QUERY_TYPE);
			
				long endTime = System.currentTimeMillis();    //获取结束时间
				logger.info("程序运行时间：" + (endTime - startTime) + "ms");    //输出程序运行时间
				long runTime =endTime - startTime;
				UnSysNotifyVO unSysNotifyVO = new UnSysNotifyVO();
				unSysNotifyVO.setJobcode("SD000001");
				unSysNotifyVO.setResmsg("成功");
				unSysNotifyVO.setRunres("success");
				unSysNotifyVO.setRuntime(String.valueOf(runTime));
				this.UnSysNotify(unSysNotifyVO);
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "成功", null);
			}
		}
		return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "失败", null);
	}

	/**
	 * 解析参数 设值
	 * 
	 * @param job
	 * @return
	 * @throws ParseException
	 */
	private void insertClWithRecoverData(RespWithholdRecoverlVO respWithholdRecoverlVOList,String QUERY_TYPE) throws ParseException {
		ClWithRecover clWithRecover = new ClWithRecover();
		int applyID = withRecoverQueryService.getApplyId();
		clWithRecover.setLoanSeq(applyID);
		clWithRecover.setPolicyNo(respWithholdRecoverlVOList.getPolicyNo());
		clWithRecover.setPolicyCount(respWithholdRecoverlVOList.getPoliceCount());
		clWithRecover.setBankCode(respWithholdRecoverlVOList.getBankCode());
		clWithRecover.setCheckDate(respWithholdRecoverlVOList.getCheckDate());
		clWithRecover.setPlanFee(respWithholdRecoverlVOList.getPlanFee());
		clWithRecover.setCapital(respWithholdRecoverlVOList.getCapital());
		clWithRecover.setIntamt(respWithholdRecoverlVOList.getIntamt());
		clWithRecover.setPremium(respWithholdRecoverlVOList.getPremium());
		clWithRecover.setPenalsum(respWithholdRecoverlVOList.getPenalsum());
		clWithRecover.setSetlInd(respWithholdRecoverlVOList.getSetlInd());
		clWithRecover.setDiffType(QUERY_TYPE);
		clWithRecover.setSysstate("0");//未做处理
		withRecoverQueryService.insertWithRecover(clWithRecover);
	}
	/**
	 * 目前是假的通知
	 * @param unSysNotifyVO
	 */
	public void UnSysNotify(UnSysNotifyVO unSysNotifyVO) {
		UnSysNotify UnSysNotify = new UnSysNotify();
		unSysNotifyVO.setJobcode(unSysNotifyVO.getJobcode());
		unSysNotifyVO.setResmsg(unSysNotifyVO.getResmsg());
		unSysNotifyVO.setRunres(unSysNotifyVO.getRunres());
		unSysNotifyVO.setRuntime(unSysNotifyVO.getRuntime());
		String reulst = UnSysNotify.UnSysNotify(unSysNotifyVO);
		logger.info("程序执行结果" + reulst);   
	}
}
