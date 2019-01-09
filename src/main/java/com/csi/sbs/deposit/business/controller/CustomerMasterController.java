package com.csi.sbs.deposit.business.controller;



import java.math.BigDecimal;
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


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
        	   //先校验字段
        	   boolean validateFlag = validateField(cam);
        	   if(!validateFlag){
        		   map.put("msg", "必填字段不全");
        		   map.put("code", "0");
        		   
        		   return objectMapper.writeValueAsString(map);
        	   }
        	   
        	   String params = "{\"item\":\"ClearingCode,BranchNumber,LocalCcy\"}";
        	   String result = ConnPostClient.postJson("http://localhost:8083/sysconfig/query", params);
               if(result==null){
            	   map.put("msg", "调用系统参数失败");
            	   map.put("code", "0");
               }
        	   
               //返回数据处理
               JSONArray jsonArray = new JSONArray();
               JSONObject jsonObject = new JSONObject();
               jsonArray= JSON.parseArray(result);
               String clearcode = "";
               String branchnumber = "";
               String localCCy = "";
               for(int i=0;i<jsonArray.size();i++){
            	  //System.out.println(jsonArray.get(i));
            	  jsonObject = (JSONObject) JSON.parse(jsonArray.get(i).toString());
            	  //System.out.println(jsonObject.get("item"));
            	  if(jsonObject.get("item").equals("BranchNumber")){
            		  branchnumber = jsonObject.get("value").toString();
            	  }
            	  if(jsonObject.get("item").equals("ClearingCode")){
            		  clearcode = jsonObject.get("value").toString();
            	  }
            	  if(jsonObject.get("item").equals("LocalCcy")){
            		  localCCy = jsonObject.get("value").toString();
            	  }
               }
               
               //判断账号类型
               if(cam.getAccount().getAccounttype().equals("001") || cam.getAccount().getAccounttype().equals("002")){
            	   cam.getAccount().setCurrencycode(localCCy);
            	   cam.getAccount().setBalance(new BigDecimal(0));
               }
               if(cam.getAccount().getAccounttype().equals("003")){
            	   cam.getAccount().setBalance(new BigDecimal(0));
               }
               
               cam.getCustomer().setCustomernumber(clearcode+branchnumber);
               cam.getCustomer().setId(UUIDUtil.generateUUID());
               
               cam.getAccount().setId(UUIDUtil.generateUUID());
               cam.getAccount().setAccountnumber(clearcode+branchnumber);
               
               customerMasterService.createCustomer(cam);
               map.put("msg", "创建成功");
               map.put("code", "1");
           }catch(Exception e){
        	   map.put("msg", "创建失败");
               map.put("code", "0");
           }
           			  
		   return objectMapper.writeValueAsString(map);
   	   }

       
       /**
        * 字段校验
        */
       private boolean validateField(CustomerAndAccountModel cam){
    	   /**
    	    * 校验Customer
    	    */
    	   if(cam.getCustomer().getCustomername()==null || "".equals(cam.getCustomer().getCustomername())){
    		   return false;
    	   }
    	   if(cam.getCustomer().getCustomerid()==null || "".equals(cam.getCustomer().getCustomerid())){
    		   return false;
    	   }
    	   if(cam.getCustomer().getIssuecountry()==null || "".equals(cam.getCustomer().getIssuecountry())){
    		   return false;
    	   }
    	   if(cam.getCustomer().getDateofbirth()==null || "".equals(cam.getCustomer().getDateofbirth())){
    		   return false;
    	   }
    	   if(cam.getCustomer().getMailingaddress()==null || "".equals(cam.getCustomer().getMailingaddress())){
    		   return false;
    	   }
    	   if(cam.getCustomer().getMobilephonenumber()==null || "".equals(cam.getCustomer().getMobilephonenumber())){
    		   return false;
    	   }
    	   
    	   /**
    	    * 校验account
    	    */
    	   if(cam.getAccount().getCurrencycode()==null || "".equals(cam.getAccount().getCurrencycode())){
    		   return false;
    	   }
    	   if(cam.getAccount().getAccounttype()==null || "".equals(cam.getAccount().getAccounttype())){
    		   return false;
    	   }
    	   
    	   return true;
       }
}
