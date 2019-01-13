package com.csi.sbs.deposit.business.util;

import com.csi.sbs.common.business.httpclient.ConnPostClient;

public class WriteLogUtil {
	
	
	   public static boolean writeLog(String path,String json){
		   try {
				// 插入日志
				ConnPostClient.postJson(path, json);
			} catch (Exception e) {
	          return false;
			}
			return true;
	   }

}
