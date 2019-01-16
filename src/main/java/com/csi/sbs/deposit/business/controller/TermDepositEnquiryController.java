package com.csi.sbs.deposit.business.controller;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.csi.sbs.deposit.business.service.TermDepositEnquiryService;
import com.csi.sbs.deposit.business.util.PostUtil;
import com.csi.sbs.deposit.business.clientmodel.TermDepositEnquiryModel;
import com.csi.sbs.deposit.business.constant.SysConstant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



@CrossOrigin//解决跨域请求
@Controller
@RequestMapping("/deposit")
public class TermDepositEnquiryController {
	@Resource
	private RestTemplate restTemplate;


	@Resource
	private TermDepositEnquiryService termDepositEnquiryService;
	
	ObjectMapper objectMapper = new ObjectMapper();
	@RequestMapping(value = "/termDepositEnquiry", method = RequestMethod.POST)
    @ResponseBody
	public String getTermDepositIDAndAccount(@RequestBody TermDepositEnquiryModel cam) throws JsonProcessingException{
    	
		Map<String,Object> map = new HashMap<String,Object>();
		//搜索ID和AccountNumber是否存在;
		int count = termDepositEnquiryService.searchTermIDAndAccount(cam.getId(),cam.getAccountnumber());
		//count>0时,存在;count<0时,不存在;
		//若存在,调用writetransactionlog service进行写入log日志；
		if(count>0){
			map.put("msg", "查找成功");
     	   	map.put("code", "1");
    	    StringBuilder jsonParam = new StringBuilder();	
    	    jsonParam.append("{\"id\":\""+cam.getId()+"\",");
    	    jsonParam.append("\"userid\":\"1151651\",");
    	    jsonParam.append("\"username\":\"coco\",");
    	    jsonParam.append("\"operationtype\":\"C\",");
	     	jsonParam.append("\"sourceservices\":\"gdfgfg\",");
	     	jsonParam.append("\"operationstate\":\"com\",");
	     	jsonParam.append("\"operationdate\":\"2019-1-13\",");
	     	jsonParam.append("\"operationdetail\":\"description\"}");
	     	//调用写入日志的service;
	        ResponseEntity<String> result1 = restTemplate.postForEntity(SysConstant.WRITE_LOG_SERVICEPATH+"", PostUtil.getRequestEntity(jsonParam.toString()),String.class);
			if(result1==null){
				map.put("msg", "写入日志失败");
	     	   	map.put("code", "0");
			}else{
				map.put("msg", "写入日志成功");
	     	   	map.put("code", "1");
			}
     	   	return objectMapper.writeValueAsString(map);
		}else{
			map.put("msg", "查找失败");
     	   	map.put("code", "0");
     	   return objectMapper.writeValueAsString(map);
		}
		
	}
	
}
