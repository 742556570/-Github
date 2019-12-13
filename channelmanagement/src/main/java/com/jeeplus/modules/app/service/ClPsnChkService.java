package com.jeeplus.modules.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.app.pojo.ResponeJsonObj;
import com.jeeplus.modules.app.dao.ClPsnCHKDao;
import com.jeeplus.modules.app.entity.ClPsnCHk;

@Service
@Transactional(readOnly = false)
public class ClPsnChkService extends CrudService<ClPsnCHKDao, ClPsnCHk> {

	@Autowired
	private ClPsnCHKDao clPsnCHkDao;

	public String saveClPsnChkImageJsonObj(List<ClPsnCHk> clPsnCHk) {
		int i = 0;
		for (ClPsnCHk cph : clPsnCHk) {
			i += clPsnCHkDao.insert(cph);
		}

		if (i == 2) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "保存成功", null);
		} else {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "保存失败", null);
		}

	}

	public String getByUsrCde(String usrCde) {

		List<ClPsnCHk> list = clPsnCHkDao.selectByUsrCde(usrCde);

		return ResponeJsonObj.getResJsonObjFromArray(ResponeJsonObj.SUCCESS, "保存成功", list);

	}
	
	
	public List<ClPsnCHk> getListByUsrCde(String usrCde) {

		List<ClPsnCHk> list = clPsnCHkDao.selectByUsrCde(usrCde);

		return list;

	}

	public String getByUsrAndTrdNo(HashMap<String, String> param) {

		List<ClPsnCHk> list = clPsnCHkDao.selectByUsrCdeAndTrdNo(param);

		return ResponeJsonObj.getResJsonObjFromArray(ResponeJsonObj.SUCCESS, "保存成功", list);

	}

	public List<ClPsnCHk> getListByUsrAndTrdNo(HashMap<String, String> param) {

		List<ClPsnCHk> list = clPsnCHkDao.selectByUsrCdeAndTrdNo(param);

		return list;

	}
	
	public boolean updateByPrimaryKeySelective(List<ClPsnCHk> list) {
		int i = 0;
		for (ClPsnCHk cph : list) {
			i+=clPsnCHkDao.updateByPrimaryKeySelective(cph);
		}
		if (i == 2) {
			return true;
		} else {
			return false;
		}
	}
	
	public Map<String, String> dealJson(JsonObject json) {
		JsonElement errorMsg = json.get("error_message");
		
		Map<String, String> result = new HashMap<String,String>();
		String scores = "";
		result.put("result", "true");
		result.put("msg", "");
		if(errorMsg == null) {
			int id_attacked = json.getAsJsonObject("id_exceptions").get("id_attacked").getAsInt();
			scores="id_attacked="+id_attacked;
			if(id_attacked == 1) {
				result.put("result", "false");
				result.put("msg", "id_attacked");
				return result;
			}
			double mask_confidence = json.getAsJsonObject("face_genuineness").get("mask_confidence").getAsDouble();
			double mask_threshold = json.getAsJsonObject("face_genuineness").get("mask_threshold").getAsDouble();
			scores=scores+"&mask_confidence="+mask_confidence+"&mask_threshold="+mask_threshold;
			if(mask_confidence >= mask_threshold) {
				result.put("result", "false");
				result.put("msg", "mask");
				return result;
			}
			double screen_replay_confidence = json.getAsJsonObject("face_genuineness").get("screen_replay_confidence").getAsDouble();
			double screen_replay_threshold = json.getAsJsonObject("face_genuineness").get("screen_replay_threshold").getAsDouble();
			scores=scores+"&screen_replay_confidence="+screen_replay_confidence+"&screen_replay_threshold="+screen_replay_threshold;
			if(screen_replay_confidence >= screen_replay_threshold) {
				result.put("result", "false");
				result.put("msg", "screen_replay");
				return result;
			}
			double synthetic_face_confidence = json.getAsJsonObject("face_genuineness").get("synthetic_face_confidence").getAsDouble();
			double synthetic_face_threshold = json.getAsJsonObject("face_genuineness").get("synthetic_face_threshold").getAsDouble();
			scores=scores+"&synthetic_face_confidence="+synthetic_face_confidence+"&synthetic_face_threshold="+synthetic_face_threshold;
			if(synthetic_face_confidence >= synthetic_face_threshold) {
				result.put("result", "false");
				result.put("msg", "synthetic_face");
				return result;
			}
			int face_replaced = json.getAsJsonObject("face_genuineness").get("face_replaced").getAsInt();
			result.put("face_replaced", face_replaced+"");
			scores=scores+"&face_replaced="+face_replaced;
			if(face_replaced == 1) {
				result.put("result", "false");
				result.put("msg", "face_replaced");
				return result;
			}
			double confidence = json.getAsJsonObject("result_faceid").get("confidence").getAsDouble();
			double _1e3 = json.getAsJsonObject("result_faceid").getAsJsonObject("thresholds").get("1e-3").getAsDouble();
			double _1e4 = json.getAsJsonObject("result_faceid").getAsJsonObject("thresholds").get("1e-4").getAsDouble();
			double _1e5 = json.getAsJsonObject("result_faceid").getAsJsonObject("thresholds").get("1e-5").getAsDouble();
			double _1e6 = json.getAsJsonObject("result_faceid").getAsJsonObject("thresholds").get("1e-6").getAsDouble();
			scores=scores+"&confidence="+confidence;
			scores=scores+"&_1e3="+_1e3;
			scores=scores+"&_1e4="+_1e4;
			scores=scores+"&_1e5="+_1e5;
			scores=scores+"&_1e6="+_1e6;
			if(confidence < _1e4) {
				result.put("result", "false");
				result.put("msg", "not_self");
				return result;
			}
			result.put("scores", scores);
		}else {
			String errMsg = errorMsg.getAsString();
			result.put("result", "false");
			result.put("msg", errMsg);
			return result;
		}
		return result;
	}
	
	
	
	public Map<String, String> dealJsonGetResult(JSONObject json) {
		
		Map<String, String> result = new HashMap<String,String>();
		String scores = "";
		
		JSONObject liveness_result = json.getJSONObject("liveness_result");
		
		JSONObject face_genuineness = liveness_result.getJSONObject("face_genuineness");

		double mask_confidence = face_genuineness.getDouble("mask_confidence");
		double screen_replay_confidence = face_genuineness.getDouble("screen_replay_confidence");
		double mask_threshold = face_genuineness.getDouble("mask_threshold");
		double synthetic_face_confidence = face_genuineness.getDouble("synthetic_face_confidence");
		double synthetic_face_threshold = face_genuineness.getDouble("synthetic_face_threshold");
		double screen_replay_threshold = face_genuineness.getDouble("screen_replay_threshold");
		double face_replaced = face_genuineness.getDouble("face_replaced");
		scores  = "mask_confidence="+mask_confidence+"&screen_replay_confidence="+screen_replay_confidence
				+"&mask_threshold="+mask_threshold+"&synthetic_face_confidence="+synthetic_face_confidence
				+"&synthetic_face_threshold="+synthetic_face_threshold+"&screen_replay_threshold"+screen_replay_threshold
				+"&face_replaced"+face_replaced;
		if(mask_confidence >= mask_threshold) {
			result.put("result", "false");
			result.put("msg", "mask");
			return result;
		}
		if(screen_replay_confidence >= screen_replay_threshold) {
			result.put("result", "false");
			result.put("msg", "screen_replay");
			return result;
		}
		if(synthetic_face_confidence >= synthetic_face_threshold) {
			result.put("result", "false");
			result.put("msg", "synthetic_face");
			return result;
		}
		if(face_replaced == 1) {
			result.put("result", "false");
			result.put("msg", "face_replaced");
			return result;
		}
		
		
		JSONObject details = liveness_result.getJSONObject("details");
		double UPLOAD_TIMES = details.getDouble("UPLOAD_TIMES");
		double NO_AUDIO = details.getDouble("NO_AUDIO");
		double VIDEO_FACE_INCONSISTENT = details.getDouble("VIDEO_FACE_INCONSISTENT");
		double FACE_NOT_FOUND = details.getDouble("FACE_NOT_FOUND");
		double SR_ERROR = details.getDouble("SR_ERROR");
		double INVALID_VIDEO_DURATION = details.getDouble("INVALID_VIDEO_DURATION");
		double LOW_FACE_QUALITY = details.getDouble("LOW_FACE_QUALITY");
		double NOT_SYNCHRONIZED = details.getDouble("NOT_SYNCHRONIZED");
		double VIDEO_FORMAT_UNSUPPORTED = details.getDouble("VIDEO_FORMAT_UNSUPPORTED");
		scores = scores+"&UPLOAD_TIMES="+UPLOAD_TIMES
				       +"&NO_AUDIO="+NO_AUDIO
				       +"&VIDEO_FACE_INCONSISTENT="+VIDEO_FACE_INCONSISTENT
				       +"&FACE_NOT_FOUND="+FACE_NOT_FOUND
				       +"&SR_ERROR="+SR_ERROR
				       +"&INVALID_VIDEO_DURATION="+INVALID_VIDEO_DURATION
				       +"&LOW_FACE_QUALITY="+LOW_FACE_QUALITY
				       +"&NOT_SYNCHRONIZED="+NOT_SYNCHRONIZED
				       +"&VIDEO_FORMAT_UNSUPPORTED="+VIDEO_FORMAT_UNSUPPORTED;
		
		
		String fccResult = liveness_result.getString("result");
		if(!"PASS".equals(fccResult)) {
			result.put("result", "false");
			result.put("msg", "NOT PASS");
			return result;
		}
		scores = scores+"&fccResult="+fccResult;
		
		JSONObject verify_result = json.getJSONObject("verify_result");
		
		JSONObject id_exceptions = verify_result.getJSONObject("id_exceptions");
		double id_photo_monochrome = id_exceptions.getDouble("id_photo_monochrome");
		double id_attacked = id_exceptions.getDouble("id_attacked");
		if(id_attacked == 1) {
			result.put("result", "false");
			result.put("msg", "id_attacked");
			return result;
		}
		if(id_photo_monochrome == 1) {
			result.put("result", "false");
			result.put("msg", "id_photo_monochrome");
			return result;
		}
		scores = scores+"&id_attacked="+id_attacked+"&id_photo_monochrome="+id_photo_monochrome;
		
		
		JSONObject result_faceid = verify_result.getJSONObject("result_faceid");
		double confidence = result_faceid.getDouble("confidence");
		JSONObject thresholds = result_faceid.getJSONObject("thresholds");
		double _1e3 = thresholds.getDouble("1e-3");
		double _1e4 = thresholds.getDouble("1e-4");
		double _1e5 = thresholds.getDouble("1e-5");
		double _1e6 = thresholds.getDouble("1e-6");
		scores=scores+"&confidence="+confidence;
		scores=scores+"&_1e3="+_1e3;
		scores=scores+"&_1e4="+_1e4;
		scores=scores+"&_1e5="+_1e5;
		scores=scores+"&_1e6="+_1e6;
		if(confidence <= _1e5) {
			result.put("result", "false");
			result.put("msg", "not_self");
			return result;
		}
		result.put("scores", scores);
		return result;
	}
	
	public static void main(String[] args) {
//		String str = 
//				"{\"time_used\":285,\"error_message\":\"ID_NUMBER_NAME_NOT_MATCH\",\"face_genuineness\":{\"mask_confidence\":0.0,\"screen_replay_confidence\":0.0,\"mask_threshold\":0.5,\"synthetic_face_confidence\":0.0,\"synthetic_face_threshold\":0.5,\"screen_replay_threshold\":0.5},\"request_id\":\"1514530352,5f0edd86-5e99-4899-9d5a-0330080d0e0b\"}";
		
		String str = "{\"id_exceptions\":{\"id_photo_monochrome\":0,\"id_attacked\":0},\"face_genuineness\":{\"mask_confidence\":0.0,\"mask_threshold\":0.5,\"screen_replay_confidence\":0.0,\"screen_replay_threshold\":0.5,\"synthetic_face_confidence\":0.0,\"synthetic_face_threshold\":0.5,\"face_replaced\":0},\"request_id\":\"1515725688,546f2e69-4d9e-4af7-9079-473ced0a3fff\",\"time_used\":1113,\"result_faceid\":{\"confidence\":91.982,\"thresholds\":{\"1e-3\":62.169,\"1e-5\":74.399,\"1e-4\":69.315,\"1e-6\":78.038}}}";
		System.out.println(new ClPsnChkService().dealJson(new Gson().fromJson(str, JsonObject.class)));
	}

}
