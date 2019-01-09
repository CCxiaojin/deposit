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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.csi.sbs.common.business.httpclient.ConnPostClient;
import com.csi.sbs.deposit.business.clientmodel.CustomerAndAccountModel;
import com.csi.sbs.deposit.business.service.CustomerMasterService;
import com.csi.sbs.deposit.business.util.UUIDUtil;


@CrossOrigin//解决跨域请求
@Controller
@RequestMapping("/deposit")
public class CustomerMasterController {
	
	
	
	   @Resource
	   private CustomerMasterService customerMasterService;
	   
       ObjectMapper objectMapper = new ObjectMapper();
       
       @RequestMapping(value = "/{createAccount}", method = RequestMethod.POST)
       @ResponseBody
   	   public String createAccount(@RequestBody CustomerAndAccountModel cam) throws JsonProcessingException{
           Map<String,Object> map = new HashMap<String,Object>();
           try{
        	   String params = "{\"item\":\"Clearing_Code,Branch_Number\"}";
        	   String result = ConnPostClient.postJson("http://localhost:8083/sysconfig/query", params);
               cam.getCustomer().setCustomernumber("0010003");
               cam.getCustomer().setCustomerid(UUIDUtil.generateUUID());
               cam.getCustomer().setId(UUIDUtil.generateUUID());
               
               cam.getAccount().setId(UUIDUtil.generateUUID());
               
               customerMasterService.createCustomer(cam);
               map.put("msg", "创建成功");
               map.put("code", "1");
           }catch(Exception e){
        	   map.put("msg", "创建失败");
               map.put("code", "0");
           }
           			  
		   return objectMapper.writeValueAsString(map);
   	   }

}
