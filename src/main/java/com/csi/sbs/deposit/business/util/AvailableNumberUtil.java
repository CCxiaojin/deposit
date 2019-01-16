package com.csi.sbs.deposit.business.util;

import java.util.HashMap;
import java.util.Map;


import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.csi.sbs.common.business.json.JsonProcess;
import com.csi.sbs.deposit.business.constant.SysConstant;

public class AvailableNumberUtil {
	
	
	/**
	 * 可用Number加一
	 */
	public static void availableNumberIncrease(RestTemplate restTemplate,String item) {
		Map<String,Object> map = new HashMap<String,Object>();
		String currentNumber = restTemplate.getForEntity("http://SYSADMIN/sysadmin/generate/getNextAvailableNumber/"+SysConstant.NEXT_AVAILABLE_CUSTOMERNUMBER, String.class).getBody();
		if (currentNumber == null) {
			map.put("msg", "调用系统参数失败");
			map.put("code", "0");
		}
		int nextAvailableNumber = 0;
		nextAvailableNumber = Integer.parseInt(JsonProcess.returnValue(JsonProcess.changeToJSONObject(currentNumber), "nextAvailableNumber"));
		// 可用number加1
		nextAvailableNumber = nextAvailableNumber + 1;
		String availableNumber = String.valueOf(nextAvailableNumber);
		int availableNumberLength = availableNumber.length();
		String appendSave = "";
		// 可用number长度判断
		switch (availableNumberLength) {
		case 1:
			appendSave = "0000" + nextAvailableNumber;
			break;
		case 2:
			appendSave = "000" + nextAvailableNumber;
			break;
		case 3:
			appendSave = "00" + nextAvailableNumber;
			break;
		case 4:
			appendSave = "0" + nextAvailableNumber;
			break;
		case 5:
			appendSave = nextAvailableNumber + "";
			break;
		}
		
		
        String param = "{\"value\":\""+appendSave+"\",\"item\":\""+item+"\"}";
		ResponseEntity<String> result = restTemplate.postForEntity("http://SYSADMIN/sysadmin/generate/saveNextAvailableNumber", PostUtil.getRequestEntity(param),String.class);
		if (!result.getStatusCode().equals("200")) {
			map.put("msg", "生成下一个可用number失败");
			map.put("code", "0");
		}
	}

}
