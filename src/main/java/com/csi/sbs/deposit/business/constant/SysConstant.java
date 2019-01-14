package com.csi.sbs.deposit.business.constant;

import java.util.HashMap;
import java.util.Map;

public class SysConstant {
	
	
	
	
	   //账号类型
	   public static final String ACCOUNT_TYPE1 = "001";
	   public static final String ACCOUNT_TYPE2 = "002";
	   public static final String ACCOUNT_TYPE3 = "003";
	   public static final String ACCOUNT_TYPE4 = "100";
	   public static final String ACCOUNT_TYPE5 = "200";
	   public static final String ACCOUNT_TYPE6 = "300";
	   
	   public static Map<String,Object> getAccountTypeMap(){
		   Map<String,Object> map = new HashMap<String,Object>();
		   
		   map.put(ACCOUNT_TYPE1, "Saving");
		   map.put(ACCOUNT_TYPE2, "Current");
		   map.put(ACCOUNT_TYPE3, "FX");
		   map.put(ACCOUNT_TYPE4, "TermDeposit");
		   map.put(ACCOUNT_TYPE5, "Loans");
		   map.put(ACCOUNT_TYPE6, "Investment");
		   
		   return map;
	   }
	   
	   //账号状态
	   public static final String ACCOUNT_STATE1 = "D";
	   
	   public static Map<String,Object> getAccountStateMap(){
		   Map<String,Object> map = new HashMap<String,Object>();
		   
		   map.put(ACCOUNT_STATE1, "账号已关闭");
		   
		   return map;
	   }
	   
	   //操作类型
	   public static final String OPERATION_CREATE = "create";
	   public static final String OPERATION_UPDATE = "update";
	   public static final String OPERATION_QUERY = "query";
	   public static final String OPERATION_DELETE = "delete";
	   
	   //操作状态
	   public static final String OPERATION_SUCCESS = "success";
	   public static final String OPERATION_FAIL = "fail";

	   
	   //写日志服务地址
	   public static final String WRITE_LOG_SERVICEPATH = "http://localhost:8083/sysadmin/log/writeTransactionLog";
	   //返回内部服务接口地址 URL
       public static final String SERVICE_INTERNAL_URL = "http://localhost:8083/sysadmin/getServiceInternalURL";
       //本服务内网地址
       public static final String LOCAL_SERVICE_URL = "http://localhost:8084/deposit";
       //本服务名称
       public static final String LOCAL_SERVICE_NAME = "deposit";
}
