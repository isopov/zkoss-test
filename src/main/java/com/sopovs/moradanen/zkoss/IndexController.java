package com.sopovs.moradanen.zkoss;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author isopov
 * @since 18.03.2014
 */
@Controller
public class IndexController {
	@RequestMapping("/")
	public String index(){
		return "redirect:index.html";
	}
}
