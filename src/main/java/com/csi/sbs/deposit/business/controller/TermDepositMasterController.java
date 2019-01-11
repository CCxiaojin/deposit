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

import com.csi.sbs.common.business.httpclient.ConnPostClient;
import com.csi.sbs.common.business.util.UUIDUtil;
import com.csi.sbs.deposit.business.clientmodel.TermDepositMasterModel;
import com.csi.sbs.deposit.business.service.TermDepositMasterService;
import com.csi.sbs.deposit.business.util.ValidateTDMaster;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@CrossOrigin//解决跨域请求
@Controller
@RequestMapping("/deposit/create")
public class TermDepositMasterController {
	
		

	    @Resource
	    private TermDepositMasterService tdmService;
	   
	    ObjectMapper objectMapper = new ObjectMapper();
	    
	    @RequestMapping(value = "/termDepositApplication", method = RequestMethod.POST)
	    @ResponseBody
		public String termDepositApplication(@RequestBody TermDepositMasterModel tdm) throws JsonProcessingException{
	        Map<String,Object> map = new HashMap<String,Object>();
	        try{
	        	tdm.setId(UUIDUtil.generateUUID());	        	
	        	ValidateTDMaster.validate(tdm, map);
	     	   	if(map.size()>0){
	     	   		return objectMapper.writeValueAsString(map);
	     	   	}     
	     	    tdmService.termDepositApplication(tdm);
	     	       	    
	     	    //插入日志
	     	    String path = "http://localhost:8083/sysadmin/log/writeTransactionLog";
	     	    StringBuilder jsonParam = new StringBuilder();	
	     	    jsonParam.append("{\"id\":\""+UUIDUtil.generateUUID()+"\",");
	     	    jsonParam.append("\"userid\":\"1151651\",");
	     	    jsonParam.append("\"username\":\"liyi\",");
	     	    jsonParam.append("\"operationtype\":\"C\",");
		     	jsonParam.append("\"sourceservices\":\"gdfgfg\",");
		     	jsonParam.append("\"operationstate\":\"com\",");
		     	jsonParam.append("\"operationdate\":\"2015-7-4\",");
		     	jsonParam.append("\"operationdetail\":\"description\"}");
		     		     	 
	     	    ConnPostClient.postJson(path,jsonParam.toString());
	     	    
	            map.put("msg", "创建成功");
	            map.put("code", "1");
	        }catch(Exception e){
		     	map.put("msg", "创建失败");
		        map.put("code", "0");
		    }
	        			  
			return objectMapper.writeValueAsString(map);
		}
	
	

}
