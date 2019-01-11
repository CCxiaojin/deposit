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
	        	
	        	ValidateTDMaster.validate(tdm, map);
	     	   	if(map.size()>0){
	     	   		return objectMapper.writeValueAsString(map);
	     	   	}     
	     	    tdmService.termDepositApplication(tdm);
	            map.put("msg", "创建成功");
	            map.put("code", "1");
	        }catch(Exception e){
		     	map.put("msg", "创建失败");
		        map.put("code", "0");
		    }
	        			  
			return objectMapper.writeValueAsString(map);
		}
	
	

}
