package com.csi.sbs.deposit.business.controller;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.csi.sbs.deposit.business.service.TermDepositEnquiryService;
import com.csi.sbs.common.business.httpclient.ConnPostClient;
import com.csi.sbs.deposit.business.clientmodel.TermDepositEnquiryModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



@CrossOrigin//解决跨域请求
@Controller
@RequestMapping("/deposit")
public class TermDepositEnquiryController {

	@Resource
	private TermDepositEnquiryService termDepositEnquiryService;
	
	ObjectMapper objectMapper = new ObjectMapper();
	@RequestMapping(value = "/termDepositEnquiry", method = RequestMethod.POST)
    @ResponseBody
	public String getTermDepositIDAndAccount(@RequestBody TermDepositEnquiryModel cam) throws JsonProcessingException{
    	
		Map<String,Object> map = new HashMap<String,Object>();
		int count = termDepositEnquiryService.searchTermIDAndAccount(cam.getId(),cam.getAccountnumber());
		if(count>0){
			map.put("msg", "查找成功");
     	   	map.put("code", "1");
			String param="{\"ID\":"+cam.getId()+";"
					+ "\"UserID\":"+cam.getAccountnumber()+";"
					+"}";
			@SuppressWarnings("unused")
			String result1 = ConnPostClient.postJson("http://localhost:8083/deposit/",param);
			map.put("msg", "写入日志成功");
     	   	map.put("code", "1");
     	   return objectMapper.writeValueAsString(map);
		}else{
			map.put("msg", "查找失败");
     	   	map.put("code", "0");
     	   return objectMapper.writeValueAsString(map);
		}
		
		
    	
	}
	
}
