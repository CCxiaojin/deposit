package com.csi.sbs.deposit.business.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;



public class PostUtil {
	

	public static HttpEntity<String> getRequestEntity(String json) {
		// headers
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		// httpEntity
		HttpEntity<String> requestEntity = new HttpEntity<String>(json, requestHeaders);
		
		return requestEntity;
	}

}
