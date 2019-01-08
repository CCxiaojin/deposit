package com.csi.sbs.deposit.business.util;

import java.util.UUID;

public class UUIDUtil {

	
	public static String generateUUID(){
		String uuid = UUID.randomUUID().toString();
		return uuid.replaceAll("-", "");
	}
}
