package com.jeeplus.modules.app.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.app.api.account.paychannel.PayChannelBankCard;
import com.jeeplus.modules.app.api.account.paychannel.request.ReqPayChannelBankCardVO;
import com.jeeplus.modules.app.dao.ClBnkCrdDao;
import com.jeeplus.modules.app.entity.ClBnkCrd;

import net.sf.json.JSONObject;

@Service
@Transactional(readOnly = false)
public class PayChannelBankCardService extends CrudService<ClBnkCrdDao, ClBnkCrd> {

	@Autowired
	private ClBnkCrdDao clBnkCrdDao;

	/**
	 * 绑卡申请接口
	 * 
	 * @param clBnkCrd
	 * @return
	 */
	public JSONObject payChannelBankCard(String acName, String acNo, String bankCode, String idNo, String phoneNo,
			String channel,ClBnkCrd bnkCrd) {
		
		JSONObject result = new JSONObject();
		result.put("result", "fail");
		result.put("detail", new JSONObject());
		PayChannelBankCard PayChannelBankCard = new PayChannelBankCard();
		ReqPayChannelBankCardVO ReqPayChannelBankCardVO = new ReqPayChannelBankCardVO();
		ReqPayChannelBankCardVO.setAcName(acName);
		ReqPayChannelBankCardVO.setAcNo(acNo);
		ReqPayChannelBankCardVO.setBankCode(bankCode);
		ReqPayChannelBankCardVO.setIdNo(idNo);
		ReqPayChannelBankCardVO.setPhoneNo(phoneNo);
		ReqPayChannelBankCardVO.setCardChn(channel);
		if(bnkCrd.getBCRD_SEQ() == null) {
			if(clBnkCrdDao.getOneByBankNoWithOutSts(acNo) != null) {
				return result;
			}
			int i = clBnkCrdDao.insert(bnkCrd);
			if (i == 0) {
				result.put("result", "fail");
				result.put("detail", new JSONObject());
				return result;
			} 
		}else  {
			int i = clBnkCrdDao.updateBySEQSelective(bnkCrd);
			if (i == 0) {
				result.put("result", "fail");
				result.put("detail", new JSONObject());
				return result;
			} 
		}
		// 支付通道1 中金支付
		if (channel.equals("CL0001")) { 
			JSONObject jsonBankCard = PayChannelBankCard.payChannelBankCard(ReqPayChannelBankCardVO);
			if ("true".equals(jsonBankCard.getString("success"))) {
				JSONObject jsonBankCardData = (JSONObject) jsonBankCard.get("data");
				String signNo = jsonBankCardData.getString("signNo");
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("BIND_IP", channel);
				if(StringUtils.isEmpty(bnkCrd.getIS_BINDFOTIC())) {
					params.put("IS_BINDFOTIC", "0");
				}else {
					params.put("IS_BINDFOTIC", bnkCrd.getIS_BINDFOTIC());
				}
				params.put("BIND_CDE", signNo);
				params.put("APPL_CARD_NO", acNo);
				clBnkCrdDao.updateBankCardApply(params);
				result.put("result", "success");
				result.put("detail", jsonBankCard);
				return result;
			} else {
//				String errMsg = jsonBankCard.getString("errorMsg");
//				if(errMsg.contains("有签约记录")) {
//					result.put("result", "success");
//				}else {
					result.put("result", "fail");
					result.put("detail", jsonBankCard);
//				}
				return result;
			}
		} else {
			JSONObject jsonBankCard = PayChannelBankCard.payChannelBankCard(ReqPayChannelBankCardVO);
			if ("true".equals(jsonBankCard.getString("success"))) {
				JSONObject jsonBankCardData = (JSONObject) jsonBankCard.get("data");
				String signNo = jsonBankCardData.getString("signNo");
				ClBnkCrd clBnkCrd = clBnkCrdDao.getOneByBankNoWithOutSts(acNo);
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("BIND_IP", clBnkCrd.getBIND_IP() + "," + channel);
				if(StringUtils.isEmpty(bnkCrd.getIS_BINDFOTIC())) {
					params.put("IS_BINDFOTIC", "0");
				}else {
					params.put("IS_BINDFOTIC", bnkCrd.getIS_BINDFOTIC());
				}
				params.put("BIND_CDE", clBnkCrd.getBIND_CDE() + "," + signNo);
				params.put("APPL_CARD_NO", acNo);
				clBnkCrdDao.updateBankCardApply(params);
				result.put("result", "success");
				result.put("detail", jsonBankCard);
				return result;
			}else {
				result.put("result", "fail");
				result.put("detail", jsonBankCard);
				return result;
			}
		}

	}

	/**
	 * 签约确认接口
	 * 
	 * @param clBnkCrd
	 * @return
	 */
	public JSONObject payChannelBankCardSign(String verifyCode, String acNo, String channel) {
		PayChannelBankCard PayChannelBankCard = new PayChannelBankCard();
		
		
		JSONObject result = new JSONObject();
		result.put("result", "fail");
		result.put("detail", new JSONObject());
		ClBnkCrd clBnkCrd = clBnkCrdDao.getOneByBankNoWithOutSts(acNo);
		// 支付通道1 中金支付
		if (channel.equals("CL0001")) {
			String str = new String(clBnkCrd.getBIND_CDE());
			String[] str1 = str.split(",");
			String signNo = str1[0].toString();
			JSONObject jsonBankCard = PayChannelBankCard.payChannelBankCardSign(signNo, verifyCode);
			if ("true".equals(jsonBankCard.getString("success"))) {
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("IS_BINDFOTIC", "1");
				params.put("APPL_CARD_NO", acNo);
				clBnkCrdDao.updateBankIsBind(params);
				result.put("result", "success");
				result.put("detail", jsonBankCard);
				return result;
			}else {
				result.put("result", "fail");
				result.put("detail", jsonBankCard);
				return result;
			}
		} else {
			String str = new String(clBnkCrd.getBIND_CDE());
			String[] str1 = str.split(",");
			if(str1.length < 2) {
				result.put("result", "fail");
				result.put("detail",  new JSONObject());
				return result;
			}
			String signNo = str1[1].toString();
			JSONObject jsonBankCard = PayChannelBankCard.payChannelBankCardSign(signNo, verifyCode);
			if ("true".equals(jsonBankCard.getString("success"))) {
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("IS_BINDFOTIC", "2");
				params.put("APPL_CARD_NO", acNo);
				clBnkCrdDao.updateBankIsBind(params);
				result.put("result", "success");
				result.put("detail", jsonBankCard);
				return result;
			}else {
				result.put("result", "fail");
				result.put("detail", jsonBankCard);
				return result;
			}
		}

		
	}

	/**
	 * 短信重发
	 * 
	 * @param clBnkCrd
	 * @return
	 */
	public JSONObject payChannelBankCardSign(String acNo, String channel) {
		
		JSONObject result = new JSONObject();
		result.put("result", "fail");
		result.put("detail", new JSONObject());
		
		PayChannelBankCard PayChannelBankCard = new PayChannelBankCard();
		ClBnkCrd clBnkCrd = clBnkCrdDao.getOneByBankNoWithOutSts(acNo);
		// 支付通道1 中金支付
		if (channel.equals("CL0001")) {
			String str = new String(clBnkCrd.getBIND_CDE());
			String[] str1 = str.split(",");
			String signNo = str1[0].toString();
			JSONObject jsonBankCard = PayChannelBankCard.payChannelBankCardverifyCode(signNo);
			if ("true".equals(jsonBankCard.getString("success"))) {
				result.put("result", "success");
				result.put("detail", jsonBankCard);
				return result;
			}else {
				result.put("result", "fail");
				result.put("detail", jsonBankCard);
				return result;
			}
		} else {
			String str = new String(clBnkCrd.getBIND_CDE());
			String[] str1 = str.split(",");
			if(str1.length < 2) {
				result.put("result", "fail");
				result.put("detail",  new JSONObject());
				return result;
			}
			String signNo = str1[1].toString();
			JSONObject jsonBankCard = PayChannelBankCard.payChannelBankCardverifyCode(signNo);
			if ("true".equals(jsonBankCard.getString("success"))) {
				result.put("result", "success");
				result.put("detail", jsonBankCard);
				return result;
			}else {
				result.put("result", "fail");
				result.put("detail", jsonBankCard);
				return result;
			}
		}

	}

	/**
	 * 绑卡确认查询接口
	 * 
	 * @param clBnkCrd
	 * @return
	 */
	public JSONObject payChannelBankCardQuery(String acName, String acNo, String bankCode, String idNo, String phoneNo,
			String channel) {
		
		JSONObject result = new JSONObject();
		result.put("result", "fail");
		result.put("detail", new JSONObject());
		
		
		PayChannelBankCard PayChannelBankCard = new PayChannelBankCard();
		ReqPayChannelBankCardVO ReqPayChannelBankCardVO = new ReqPayChannelBankCardVO();
		ReqPayChannelBankCardVO.setAcName(acName);
		ReqPayChannelBankCardVO.setAcNo(acNo);
		ReqPayChannelBankCardVO.setBankCode(bankCode);
		ReqPayChannelBankCardVO.setIdNo(idNo);
		ReqPayChannelBankCardVO.setPhoneNo(phoneNo);
		// 支付通道1 中金支付
		ClBnkCrd clBnkCrd = clBnkCrdDao.getOneByBankNoWithOutSts(acNo);
		if (channel.equals("CL0001")) {
			String str = new String(clBnkCrd.getBIND_CDE());
			String signNo = "";
			if(StringUtils.isNotEmpty(str)) {
			String[] str1 = str.split(",");
				if(str.length() == 2) {
					signNo = str1[0].toString();
				}
			}
			JSONObject jsonBankCard = PayChannelBankCard.payChannelBankCardQuery(ReqPayChannelBankCardVO, signNo);
			if ("true".equals(jsonBankCard.getString("success"))) {
				net.sf.json.JSONArray queryInfos = jsonBankCard.getJSONObject("data").getJSONArray("queryInfos");
				for (int i = 0; i < queryInfos.size(); i++) {
					JSONObject tmp = queryInfos.getJSONObject(i);
					if(tmp.getString("cardChn").equals("CL0001")) {
						HashMap<String, String> params = new HashMap<String, String>();
						params.put("IS_BINDFOTIC", "1");
						params.put("APPL_CARD_NO", acNo);
						clBnkCrdDao.updateBankIsBind(params);
						result.put("result", "success");
						result.put("detail", jsonBankCard);
						return result;
					}
				}
				result.put("result", "fail");
				result.put("detail", jsonBankCard);
				return result;
			}else {
				result.put("result", "fail");
				result.put("detail", jsonBankCard);
				return result;
			}
		} else {
			String str = new String(clBnkCrd.getBIND_CDE());
			String signNo = "";
			if(StringUtils.isNotEmpty(str)) {
			String[] str1 = str.split(",");
				if(str.length() == 2) {
					signNo = str1[1].toString();
				}
			}
			JSONObject jsonBankCard = PayChannelBankCard.payChannelBankCardQuery(ReqPayChannelBankCardVO, signNo);
			if ("true".equals(jsonBankCard.getString("success"))) {
				net.sf.json.JSONArray queryInfos = jsonBankCard.getJSONObject("data").getJSONArray("queryInfos");
				for (int i = 0; i < queryInfos.size(); i++) {
					JSONObject tmp = queryInfos.getJSONObject(i);
					if(tmp.getString("cardChn").equals("CL0002")) {
						HashMap<String, String> params = new HashMap<String, String>();
						params.put("IS_BINDFOTIC", "2");
						params.put("APPL_CARD_NO", acNo);
						clBnkCrdDao.updateBankIsBind(params);
						result.put("result", "success");
						result.put("detail", jsonBankCard);
						return result;
					}
				}
				result.put("result", "fail");
				result.put("detail", jsonBankCard);
				return result;
			}else {
				result.put("result", "fail");
				result.put("detail", jsonBankCard);
				return result;
			}
		}
	}

	public static void main(String[] args) {
		/*
		 * JSONObject jsonBankCard = new JSONObject(); JSONObject jsonBankCard1 =
		 * jsonBankCard.fromObject(
		 * "{\"success\":\"true\",\"errorCode\":\"1\",\"errorMsg\":\"签约申请成功\",\"data\":{\"resultCode\":\"0000\",\"cardChn\":\"CL0001\",\"resultMsg\":\"签约申请成功\",\"signNo\":\"30000000000006220178\"}}"
		 * ); JSONObject jsonBankCard2 = (JSONObject) jsonBankCard1.get("data"); String
		 * signNo = jsonBankCard2.getString("signNo"); System.out.println(signNo);
		 */
		String str = new String("1,2");
		String[] str1 = str.split(",");
		String signNo = str1[0].toString();
		System.out.println(signNo);
	}
}
