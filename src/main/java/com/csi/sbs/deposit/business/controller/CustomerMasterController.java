package com.csi.sbs.deposit.business.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import com.alibaba.fastjson.JSONObject;
import com.csi.sbs.common.business.httpclient.ConnGetClient;
import com.csi.sbs.common.business.httpclient.ConnPostClient;
import com.csi.sbs.common.business.json.JsonProcess;
import com.csi.sbs.common.business.util.UUIDUtil;
import com.csi.sbs.deposit.business.clientmodel.CustomerAndAccountModel;
import com.csi.sbs.deposit.business.constant.SysConstant;
import com.csi.sbs.deposit.business.entity.SysTransactionLogEntity;
import com.csi.sbs.deposit.business.service.CustomerMasterService;

@CrossOrigin // 解决跨域请求
@Controller
@RequestMapping("/deposit/account")
@Api(value = "Then controller is deposit account")
public class CustomerMasterController {
	
	@Resource
	private CustomerMasterService customerMasterService;
	
	private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 开普通账号
	 * 
	 * @param cam
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/openingSavingAccount", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This api is for create savingaccount", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 0, message = "Create Fail!"),
			@ApiResponse(code = 1, message = "Create Success!") })
	@ApiImplicitParam(paramType = "body", name = "cam", required = true, value = "CustomerAndAccountModel")
	public String openingSavingAccount(@RequestBody CustomerAndAccountModel cam) throws JsonProcessingException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (!JsonProcess.returnValue(JsonProcess.changeToJSONObject(commonBusinessProcess(cam)), "code")
					.equals("1")) {
				return commonBusinessProcess(cam);
			}
			cam.getAccount().setCurrencycode(
					JsonProcess.returnValue(JsonProcess.changeToJSONObject(commonBusinessProcess(cam)), "localCCy"));
			cam.getAccount().setAccounttype(SysConstant.ACCOUNT_TYPE1);
			String accountNumber = customerMasterService.createCustomer(cam);
			writeLog(accountNumber);
			map.put("msg", "创建成功");
			map.put("accountNumber", accountNumber);
			map.put("code", "1");
		} catch (Exception e) {
			map.put("msg", "创建失败");
			map.put("code", "0");
		}

		return objectMapper.writeValueAsString(map);
	}

	/**
	 * 开支票账号
	 * 
	 * @param cam
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/openingCurrentAccount", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This api is for create currentaccount", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 0, message = "Create Fail!"),
			@ApiResponse(code = 1, message = "Create Success!") })
	@ApiImplicitParam(paramType = "body", name = "cam", required = true, value = "CustomerAndAccountModel")
	public String openingCurrentAccount(@RequestBody CustomerAndAccountModel cam) throws JsonProcessingException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (!JsonProcess.returnValue(JsonProcess.changeToJSONObject(commonBusinessProcess(cam)), "code")
					.equals("1")) {
				return commonBusinessProcess(cam);
			}
			cam.getAccount().setCurrencycode(
					JsonProcess.returnValue(JsonProcess.changeToJSONObject(commonBusinessProcess(cam)), "localCCy"));
			cam.getAccount().setAccounttype(SysConstant.ACCOUNT_TYPE2);
			String accountNumber = customerMasterService.createCustomer(cam);
			writeLog(accountNumber);
			map.put("msg", "创建成功");
			map.put("accountNumber", accountNumber);
			map.put("code", "1");
		} catch (Exception e) {
			map.put("msg", "创建失败");
			map.put("code", "0");
		}

		return objectMapper.writeValueAsString(map);
	}

	/**
	 * 开外汇账号
	 * 
	 * @param cam
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/openingFEAccount", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This api is for create feaccount", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 0, message = "Create Fail!"),
			@ApiResponse(code = 1, message = "Create Success!") })
	@ApiImplicitParam(paramType = "body", name = "cam", required = true, value = "CustomerAndAccountModel")
	public String openingFEAccount(@RequestBody CustomerAndAccountModel cam) throws JsonProcessingException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (!JsonProcess.returnValue(JsonProcess.changeToJSONObject(commonBusinessProcess(cam)), "code")
					.equals("1")) {
				return commonBusinessProcess(cam);
			}
			cam.getAccount().setBalance(new BigDecimal(0));
			cam.getAccount().setAccounttype(SysConstant.ACCOUNT_TYPE3);
			String accountNumber = customerMasterService.createCustomer(cam);
			writeLog(accountNumber);
			map.put("msg", "创建成功");
			map.put("accountNumber", accountNumber);
			map.put("code", "1");
		} catch (Exception e) {
			map.put("msg", "创建失败");
			map.put("code", "0");
		}

		return objectMapper.writeValueAsString(map);
	}

	/**
	 * 开定存账号
	 * 
	 * @param cam
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/openingTDAccount", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This api is for create tdaccount", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 0, message = "Create Fail!"),
			@ApiResponse(code = 1, message = "Create Success!") })
	@ApiImplicitParam(paramType = "body", name = "cam", required = true, value = "CustomerAndAccountModel")
	public String openingTDAccount(@RequestBody CustomerAndAccountModel cam) throws JsonProcessingException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (!JsonProcess.returnValue(JsonProcess.changeToJSONObject(commonBusinessProcess(cam)), "code")
					.equals("1")) {
				return commonBusinessProcess(cam);
			}
			cam.getAccount().setAccounttype(SysConstant.ACCOUNT_TYPE4);
			String accountNumber = customerMasterService.createCustomer(cam);
			writeLog(accountNumber);
			map.put("msg", "创建成功");
			map.put("accountNumber", accountNumber);
			map.put("code", "1");
		} catch (Exception e) {
			map.put("msg", "创建失败");
			map.put("code", "0");
		}

		return objectMapper.writeValueAsString(map);
	}

	/**
	 * 创建账号公共业务处理
	 * 
	 * @param cam
	 * @return
	 * @throws JsonProcessingException
	 */
	private String commonBusinessProcess(CustomerAndAccountModel cam) throws JsonProcessingException {
		Map<String, Object> map = new HashMap<String, Object>();
		// 先校验字段
		boolean validateFlag = validateField(cam);
		if (!validateFlag) {
			map.put("msg", "必填字段不全");
			map.put("code", "0");

			return objectMapper.writeValueAsString(map);
		}

		// 调用服务接口地址
		String params1 = "{\"apiname\":\"getSystemParameter\"}";
		String result1 = ConnPostClient.postJson(SysConstant.SERVICE_INTERNAL_URL, params1);
		if (result1 == null) {
			map.put("msg", "调用服务接口地址失败");
			map.put("code", "0");
		}
		String path = JsonProcess.returnValue(JsonProcess.changeToJSONObject(result1), "internaURL");

		// 调用系统参数服务接口
		String params2 = "{\"item\":\"ClearingCode,BranchNumber,LocalCcy,NextAvailableCustomerNumber\"}";
		String result2 = ConnPostClient.postJson(path, params2);
		if (result2 == null) {
			map.put("msg", "调用系统参数失败");
			map.put("code", "0");
		}

		// 返回数据处理
		String clearcode = "";
		String branchnumber = "";
		String localCCy = "";
		JSONObject jsonObject1 = null;
		String revalue = null;
		String temp = null;
		for (int i = 0; i < JsonProcess.changeToJSONArray(result2).size(); i++) {
			jsonObject1 = JsonProcess.changeToJSONObject(JsonProcess.changeToJSONArray(result2).get(i).toString());
			revalue = JsonProcess.returnValue(jsonObject1, "item");
			temp = JsonProcess.returnValue(jsonObject1, "value");
			if (revalue.equals("BranchNumber")) {
				branchnumber = temp;
			}
			if (revalue.equals("ClearingCode")) {
				clearcode = temp;
			}
			if (revalue.equals("LocalCcy")) {
				localCCy = temp;
			}
		}

		// 调用可用customerNumber服务接口
		String customerNumber = "";
		String result3 = ConnGetClient.get("http://localhost:8083/sysadmin/generate/getNextAvailableNumber");
		if (result3 == null) {
			map.put("msg", "调用系统参数失败");
			map.put("code", "0");
		}

		// 返回数据处理
		customerNumber = JsonProcess.returnValue(JsonProcess.changeToJSONObject(result3), "nextAvailableNumber");

		cam.getCustomer().setCustomernumber(clearcode + branchnumber + customerNumber);
		cam.getCustomer().setId(UUIDUtil.generateUUID());

		cam.getAccount().setBalance(new BigDecimal(0));
		cam.getAccount().setId(UUIDUtil.generateUUID());
		cam.getAccount().setAccountnumber(clearcode + branchnumber + customerNumber);

		map.put("msg", "处理成功");
		map.put("localCCy", localCCy);
		map.put("code", "1");

		return objectMapper.writeValueAsString(map);
	}

	private boolean writeLog(String accountNumber) {
		try {
			// 插入日志
			String path = "http://localhost:8083/sysadmin/log/writeTransactionLog";
			SysTransactionLogEntity log = new SysTransactionLogEntity();
		    log.setId(UUIDUtil.generateUUID());
		    log.setUserid("000000");
		    log.setUsername("测试账号");
		    log.setOperationtype(SysConstant.OPERATION_CREATE);
		    log.setSourceservices(SysConstant.LOCAL_SERVICE_NAME);
		    log.setOperationstate(SysConstant.OPERATION_SUCCESS);
		    log.setOperationdate(sf.parse(sf.format(new Date())));
		    log.setOperationdetail("create accountNumber:"+accountNumber+" success!");
			ConnPostClient.postJson(path, JsonProcess.changeEntityTOJSON(log));
		} catch (Exception e) {
          return false;
		}
		return true;
	}

	/**
	 * 字段校验
	 */
	private boolean validateField(CustomerAndAccountModel cam) {
		/**
		 * 校验Customer
		 */
		if (cam.getCustomer().getCustomername() == null || "".equals(cam.getCustomer().getCustomername())) {
			return false;
		}
		if (cam.getCustomer().getCustomerid() == null || "".equals(cam.getCustomer().getCustomerid())) {
			return false;
		}
		if (cam.getCustomer().getIssuecountry() == null || "".equals(cam.getCustomer().getIssuecountry())) {
			return false;
		}
		if (cam.getCustomer().getDateofbirth() == null || "".equals(cam.getCustomer().getDateofbirth())) {
			return false;
		}
		if (cam.getCustomer().getMailingaddress() == null || "".equals(cam.getCustomer().getMailingaddress())) {
			return false;
		}
		if (cam.getCustomer().getMobilephonenumber() == null || "".equals(cam.getCustomer().getMobilephonenumber())) {
			return false;
		}

		/**
		 * 校验account
		 */
		if (cam.getAccount().getCurrencycode() == null || "".equals(cam.getAccount().getCurrencycode())) {
			return false;
		}
		// if(cam.getAccount().getAccounttype()==null ||
		// "".equals(cam.getAccount().getAccounttype())){
		// return false;
		// }

		return true;
	}
}
