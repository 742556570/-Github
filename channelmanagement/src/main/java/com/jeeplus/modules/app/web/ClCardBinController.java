package com.jeeplus.modules.app.web;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jeeplus.modules.app.service.ClCardBinService;

/**
 * 查询卡bin是否合法
 * 
 * @author 阳光保险
 * @date 2017年12月26
 *
 */
@Controller
@RequestMapping(value = "api/cardbin")
public class ClCardBinController {

	@Autowired
	private ClCardBinService clCardBinService;
	/**
	 * 根据卡号的的前六位到自己的卡bin库里面进行查询
	 * @param card
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "selectcardbin")
	public String selectCardBin(@RequestParam(value = "card") String card,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("服务器接受的卡号："+card);
		String cardSub = card.substring(0, 6);
		String result = clCardBinService.getByClCardBinId(cardSub);
		return result;
	}
}
