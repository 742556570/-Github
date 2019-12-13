package com.jeeplus.modules.app.id5.req;

import java.util.HashMap;
import java.util.Map;

import com.jeeplus.modules.app.id5.util.XmlToMap;
import com.jeeplus.modules.app.id5.util.httpClient.Id5Submit;

import net.sf.json.JSONObject;

/**
 * 
 * @author 阳光保险
 *
 */
public class Id5Client {
	/**
	 * 四要素参数格式，baffleFlg是挡板 如果是N则调用国政通接口否则
	 * @param param "王XX,6214830103XXXX,15232419920XXXXX,1551071XXXX";
	 * @param baffleFlg
	 * @return
	 */
	public String buildRequest(String param,String baffleFlg){
		String code ="";
		if(baffleFlg=="N") {
			try {
				String returnXML =Id5Submit.buildRequest(param);
				Map<String, Object> map = XmlToMap.xml2map(returnXML, false);
				JSONObject json = JSONObject.fromObject(map); 
		        JSONObject jsonMsssage =json.getJSONObject("message");
		        JSONObject acctnoInfos =json.getJSONObject("acctnoInfos");
		        JSONObject jsonAcctnoInfo =acctnoInfos.getJSONObject("acctnoInfo");
		        String status = jsonMsssage.getString("status");
		        String value = jsonMsssage.getString("value");
		        code= jsonAcctnoInfo.getString("code");
		        String message = jsonAcctnoInfo.getString("message");
		        System.out.println("国政通处理状态"+status+"处理结果是："+value+"  国政通返回code："+code+"返回的message"+message);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(baffleFlg=="Y") {
			code="7";
			String message="银行卡挡板程序处理成功";
			System.out.println(message);
		}
		return code;
	}
	/**
	 * 四要素参数格式，baffleFlg是挡板 如果是N则调用国政通接口否则
	 * @param param "王XX,6214830103XXXX,15232419920XXXXX,1551071XXXX";
	 * @param baffleFlg
	 * @return
	 */
	public JSONObject buildRequestBackMap(String param){
		JSONObject jsonbank = new JSONObject();
			try {
				
				String returnXML =Id5Submit.buildRequest(param);
				Map<String, Object> map = XmlToMap.xml2map(returnXML, false);
				JSONObject json = JSONObject.fromObject(map); 
		        JSONObject jsonMsssage =json.getJSONObject("message");
		        JSONObject acctnoInfos =json.getJSONObject("acctnoInfos");
		        JSONObject jsonAcctnoInfo =acctnoInfos.getJSONObject("acctnoInfo");
		        String status = jsonMsssage.getString("status");
		        String value = jsonMsssage.getString("value");
		        String code= jsonAcctnoInfo.getString("code");
		        String message = jsonAcctnoInfo.getString("message");
		        jsonbank.put("code", code);
		        jsonbank.put("message", json);
		        System.out.println("国政通处理状态"+status+"处理结果是："+value+"  国政通返回code："+code+"返回的message"+message);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return jsonbank;
	}
	
	public static void main(String[] args) {
//		String params = "徐兰飞,6217231204002712340,330881198606140716,13906535484";
//		String params = "王志祥,6226632301571480,330121196702271675,13758115121‬";
//		String params = "王周泉,622908113031476612,33032919730122353X,18805018977";
//		String params = "吴小兰,6210813520010089020,460027197012286227,13518010558";
//		String params = "陈文有,6236684470001669300,642225198104100619,15595569908";
//		String params = "王刚,6227000839370023693,220221197511090833,13944697555";
//		String params = "王昌梅,6210810900003414794,371328198109052546,13039093072";
//		String params = "陈丽兰,	6214921501204859,350624198205201042,13666015272";
//		String params = "范安长,6217222305002775020,510624196809121511,15390147022";
//		String params = "张红良,6227002003400369398,32092219850323633X,13405234658";
//		String params = "付海欣,6217004220023382223,610103197701210810,15202902221";
		
//		String params = "杨晓莉,6217003320045226985,61232119751224112X,13802411889";
		
//		String params = "何凯,6236684220010480391,339011197206043299,13819529028";
//		String params = "王君萍,6214923006502019,340122197102044084,13966782596";
//		String params = "王景录,6226620307335323,232326197608230514,15004598678";
		
//		String params = "候金利,6215593700016265858,142731198306183615,18602976168";
		
		
		
//		String params = "赵建玉,6217002220007682905,370982197308273080,18364809363";
//		String params = "于芳,6217000940014527247,220104196503070043,18143051185";
		
//		String params = "蓝庆敏,6214921206027225,452731197208190318,13411595899";
		
//		String params = "傅海涛,6222022201022097804,420802197007280313,18608900977";
//		String params = "李贤喜,6217003760104365507,500243198510081310,15213131563";
		
//		String params = "傅强,6236683230010761063,352124198108120915,18665119099";
		
//		String params = "林焕良,6226631500730574,352623196811105970,13714855657";
		
//		String params = "白永辉,6210810590002183514,21041119730829081X,13842333017";
		
//		String params = "谢双杰,6226622211737149,411325199002250786,18624795588";
		
//		String params = "路娇娇,6214922402213361,370829198608261724,15053708727";
		
//		String params = "张士顺,6215581106002179323,320322198707307619,13775968831";
		
//		String params = "陈加立,6217003860029050703,532129198206170524,13888056004";
		
//		String params = "张小礼,6212263602066055146,350624197708013567,13719360816";
		
//		String params = "许英儒,6214921500090283,310110196702233254,13906001123";
		
//		String params = "周国良,6222080511000475437,142701198005051215,15388599718";
		
//		String params = "华海,6226622303489179,330622196604050055,13957566003";
		
//		String params = "赖泰清,6222083602013635104,362132197505093815,15112066659";
		
//		String params = "杨学洪,6226631800847367,320882198203122817,18936134066";
		
		String params = "鞠武铮,6226620701919136,370302197210220517,18502435634";
		
		new Id5Client().buildRequest(params, "N");
	}

}