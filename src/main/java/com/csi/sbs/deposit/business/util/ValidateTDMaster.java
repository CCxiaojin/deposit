package com.csi.sbs.deposit.business.util;

import java.util.Map;

import com.csi.sbs.deposit.business.clientmodel.TermDepositMasterModel;
import com.mysql.jdbc.StringUtils;

public class ValidateTDMaster {
	
	
	 public static Map<String,Object> validate(TermDepositMasterModel tdm
			 ,Map<String,Object> map){
		 
		 if(tdm!=null){
			 if(StringUtils.isNullOrEmpty(tdm.getAccountnumber())){ 	            
				 map.put("code", "409");
				 map.put("msg", "account number is mandatory");	
				 return map;
			 }
			 
			 if(StringUtils.isNullOrEmpty(tdm.getDepositnumber())){ 	            
				 map.put("code", "409");
				 map.put("msg", "deposit number is mandatory");	
				 return map;
			 }
			 if(validateObjectIfnull(tdm.getDepositamount())){ 	            
				 map.put("code", "409");
				 map.put("msg", "deposit amount is mandatory");	
				 return map;
			 }
			 if(validateObjectIfnull(tdm.getMaturityinterest())){ 	            
				 map.put("code", "409");
				 map.put("msg", "Maturity interest is mandatory");	
				 return map;
			 }
			 if(validateObjectIfnull(tdm.getMaturityamount())){ 	            
				 map.put("code", "409");
				 map.put("msg", "maturity amount is mandatory");	
				 return map;
			 }
			 if(StringUtils.isNullOrEmpty(tdm.getTermperiod())){ 	            
				 map.put("code", "409");
				 map.put("msg", "term period is mandatory");	
				 return map;
			 }			 
		 }
		 
		 return map;
		 
	 }
	 
	 
	 public static boolean validateObjectIfnull(Object obj){
		
		 boolean empty = false;		 
		 if( null ==obj || (null!=obj && StringUtils.isNullOrEmpty(obj.toString()))){
			 empty = true;
		 }
		 
		 return empty;
		 
	 }
	 
	

}
