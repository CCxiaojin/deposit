package com.csi.sbs.deposit.business.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import com.alibaba.fastjson.JSONObject;
import com.csi.sbs.common.business.json.JsonProcess;
import com.csi.sbs.common.business.util.UUIDUtil;
import com.csi.sbs.common.business.constant.CommonConstant;
import com.csi.sbs.deposit.business.clientmodel.CloseAccountModel;
import com.csi.sbs.deposit.business.clientmodel.CustomerAndAccountModel;
import com.csi.sbs.deposit.business.clientmodel.CustomerMaintenanceModel;
import com.csi.sbs.deposit.business.clientmodel.AccountNumber;
import com.csi.sbs.deposit.business.clientmodel.DepositModel;
import com.csi.sbs.deposit.business.clientmodel.WithDrawalModel;
import com.csi.sbs.deposit.business.constant.SysConstant;
import com.csi.sbs.deposit.business.entity.AccountMasterEntity;
import com.csi.sbs.deposit.business.entity.CustomerMasterEntity;
import com.csi.sbs.deposit.business.service.AccountMasterService;
import com.csi.sbs.deposit.business.service.CustomerMasterService;
import com.csi.sbs.deposit.business.util.AvailableNumberUtil;
import com.csi.sbs.deposit.business.util.LogUtil;
import com.csi.sbs.deposit.business.util.PostUtil;

@CrossOrigin // 解决跨域请求
@Controller
@RequestMapping("/deposit/account")
@Api(value = "Then controller is deposit account",produces = "application/json")
public class CustomerMasterController {

	@Resource
	private CustomerMasterService customerMasterService;

	@Resource
	private AccountMasterService accountMasterService;

	@Resource
	private RestTemplate restTemplate;

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
	@ApiOperation(value = "This api is for create savingaccount", notes = "version 0.0.1",produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 0, message = "Create Fail!"),
			@ApiResponse(code = 1, message = "Create Success!") })
	@ApiImplicitParam(paramType = "body", name = "cam", required = true, value = "CustomerAndAccountModel")
	public String openingSavingAccount(@RequestBody CustomerAndAccountModel customerAndAccountModel)
			throws JsonProcessingException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (!JsonProcess
					.returnValue(JsonProcess.changeToJSONObject(commonBusinessProcess(customerAndAccountModel)), "code")
					.equals("1")) {
				return commonBusinessProcess(customerAndAccountModel);
			}
			// 校验客户是否存在
			CustomerMasterEntity cme = new CustomerMasterEntity();
			cme.setCustomerid(customerAndAccountModel.getCustomer().getCustomerid());
			CustomerMasterEntity recustomer = customerMasterService.findCustomerByCustomerID(cme);
			boolean flag = false;
			if (recustomer != null) {
				flag = true;
			}
			customerAndAccountModel.getAccount().setCurrencycode(JsonProcess.returnValue(
					JsonProcess.changeToJSONObject(commonBusinessProcess(customerAndAccountModel)), "localCCy"));
			customerAndAccountModel.getAccount().setAccounttype(SysConstant.ACCOUNT_TYPE1);
			String[] temp = customerMasterService.createCustomer(customerAndAccountModel, flag, recustomer);

			// 写入日志
			String logstr = "create accountNumber:" + temp[0] + " success!";
			LogUtil.saveLog(
					restTemplate, 
					SysConstant.OPERATION_CREATE, 
					SysConstant.LOCAL_SERVICE_NAME, 
					SysConstant.OPERATION_SUCCESS, 
					logstr
					);
			AvailableNumberUtil.availableNumberIncrease(restTemplate, SysConstant.NEXT_AVAILABLE_CUSTOMERNUMBER);
			map.put("msg", "创建成功");
			map.put("accountNumber", temp[0]);
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
	public String openingCurrentAccount(@RequestBody CustomerAndAccountModel customerAndAccountModel)
			throws JsonProcessingException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (!JsonProcess
					.returnValue(JsonProcess.changeToJSONObject(commonBusinessProcess(customerAndAccountModel)), "code")
					.equals("1")) {
				return commonBusinessProcess(customerAndAccountModel);
			}
			// 校验客户是否存在
			CustomerMasterEntity cme = new CustomerMasterEntity();
			cme.setCustomerid(customerAndAccountModel.getCustomer().getCustomerid());
			CustomerMasterEntity recustomer = customerMasterService.findCustomerByCustomerID(cme);
			boolean flag = false;
			if (recustomer != null) {
				flag = true;
			}
			customerAndAccountModel.getAccount().setCurrencycode(JsonProcess.returnValue(
					JsonProcess.changeToJSONObject(commonBusinessProcess(customerAndAccountModel)), "localCCy"));
			customerAndAccountModel.getAccount().setAccounttype(SysConstant.ACCOUNT_TYPE2);
			String[] temp = customerMasterService.createCustomer(customerAndAccountModel, flag, recustomer);
			// 写入日志
			String logstr = "create accountNumber:" + temp[0] + " success!";
			LogUtil.saveLog(
					restTemplate, 
					SysConstant.OPERATION_CREATE, 
					SysConstant.LOCAL_SERVICE_NAME, 
					SysConstant.OPERATION_SUCCESS,
					logstr
					);
			AvailableNumberUtil.availableNumberIncrease(restTemplate, SysConstant.NEXT_AVAILABLE_CUSTOMERNUMBER);
			map.put("msg", "创建成功");
			map.put("accountNumber", temp[0]);
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
	public String openingFEAccount(@RequestBody CustomerAndAccountModel customerAndAccountModel)
			throws JsonProcessingException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (!JsonProcess
					.returnValue(JsonProcess.changeToJSONObject(commonBusinessProcess(customerAndAccountModel)), "code")
					.equals("1")) {
				return commonBusinessProcess(customerAndAccountModel);
			}
			// 校验客户是否存在
			CustomerMasterEntity cme = new CustomerMasterEntity();
			cme.setCustomerid(customerAndAccountModel.getCustomer().getCustomerid());
			CustomerMasterEntity recustomer = customerMasterService.findCustomerByCustomerID(cme);
			boolean flag = false;
			if (recustomer != null) {
				flag = true;
			}
			customerAndAccountModel.getAccount().setBalance(new BigDecimal(0));
			customerAndAccountModel.getAccount().setAccounttype(SysConstant.ACCOUNT_TYPE3);
			String[] temp = customerMasterService.createCustomer(customerAndAccountModel, flag, recustomer);
			// 写入日志
			String logstr = "create accountNumber:" + temp[0] + " success!";
			LogUtil.saveLog(
					restTemplate, 
					SysConstant.OPERATION_CREATE, 
					SysConstant.LOCAL_SERVICE_NAME, 
					SysConstant.OPERATION_SUCCESS,
					logstr
					);
			AvailableNumberUtil.availableNumberIncrease(restTemplate, SysConstant.NEXT_AVAILABLE_CUSTOMERNUMBER);
			map.put("msg", "创建成功");
			map.put("accountNumber", temp[0]);
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
	public String openingTDAccount(@RequestBody CustomerAndAccountModel customerAndAccountModel)
			throws JsonProcessingException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (!JsonProcess
					.returnValue(JsonProcess.changeToJSONObject(commonBusinessProcess(customerAndAccountModel)), "code")
					.equals("1")) {
				return commonBusinessProcess(customerAndAccountModel);
			}
			// 校验客户是否存在
			CustomerMasterEntity cme = new CustomerMasterEntity();
			cme.setCustomerid(customerAndAccountModel.getCustomer().getCustomerid());
			CustomerMasterEntity recustomer = customerMasterService.findCustomerByCustomerID(cme);
			boolean flag = false;
			if (recustomer != null) {
				flag = true;
			}
			customerAndAccountModel.getAccount().setAccounttype(SysConstant.ACCOUNT_TYPE4);
			String[] temp = customerMasterService.createCustomer(customerAndAccountModel, flag, recustomer);
			// 写入日志
			String logstr = "create accountNumber:" + temp[0] + " success!";
			LogUtil.saveLog(
					restTemplate, 
					SysConstant.OPERATION_CREATE, 
					SysConstant.LOCAL_SERVICE_NAME, 
					SysConstant.OPERATION_SUCCESS, 
					logstr
					);
			AvailableNumberUtil.availableNumberIncrease(restTemplate, SysConstant.NEXT_AVAILABLE_CUSTOMERNUMBER);
			map.put("msg", "创建成功");
			map.put("accountNumber", temp[0]);
			map.put("code", "1");
		} catch (Exception e) {
			map.put("msg", "创建失败");
			map.put("code", "0");
		}

		return objectMapper.writeValueAsString(map);
	}

	/**
	 * 账号关闭
	 * 
	 * @param cam
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/accountClosure", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This api is for close account", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 0, message = "close Fail!"),
			@ApiResponse(code = 1, message = "close Success!") })
	@ApiImplicitParam(paramType = "body", name = "closeAccountModel", required = true, value = "closeAccountModel")
	public String accountClosure(@RequestBody CloseAccountModel closeAccountModel) throws JsonProcessingException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			AccountMasterEntity ame = new AccountMasterEntity();
			ame.setAccountnumber(closeAccountModel.getAccountNumber());
			ame.setAccounttype(closeAccountModel.getAccountType());
			// 根据accountNumber 和 accountType查询账号
			List<AccountMasterEntity> accountList = accountMasterService.findAccountByParams(ame);
			if (accountList == null || accountList.size() == 0) {
				map.put("msg", "Record Not Found");
				map.put("code", "0");
				return objectMapper.writeValueAsString(map);
			}
			// 校验账户余额是否大于0
			int r = accountList.get(0).getBalance().compareTo(BigDecimal.ZERO);
			if (r != 0) {
				map.put("msg", "Account Balance Not Zero");
				map.put("code", "0");
				return objectMapper.writeValueAsString(map);
			}

			ame.setAccountstatus(SysConstant.ACCOUNT_STATE1);
			ame.setLastupdateddate(sf.parse(sf.format(new Date())));
			if (accountMasterService.closeAccount(ame) > 0) {
				map.put("msg", "Account Deleted Success");
				map.put("code", "1");
				String logstr = "close accountNumber:" + ame.getAccountnumber() + " success!";
				LogUtil.saveLog(
						restTemplate, 
						SysConstant.OPERATION_UPDATE, 
						SysConstant.LOCAL_SERVICE_NAME, 
						SysConstant.OPERATION_SUCCESS, 
						logstr
						);
			} else {
				map.put("msg", "Account Deleted Fail");
				map.put("code", "0");
			}
			return objectMapper.writeValueAsString(map);
		} catch (Exception e) {
			map.put("msg", "Account Deleted Fail");
			map.put("code", "0");
		}

		return objectMapper.writeValueAsString(map);
	}

	/**
	 * 账号维护
	 * 
	 * @param cam
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/updateCustContactInfo", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This api is for update customer contact Information", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 0, message = "update Fail!"),
			@ApiResponse(code = 1, message = "update Success!") })
	@ApiImplicitParam(paramType = "body", name = "customerMaintenanceModel", required = true, value = "customerMaintenanceModel")
	public String updateCustContactInfo(@RequestBody CustomerMaintenanceModel customerMaintenanceModel)
			throws JsonProcessingException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			AccountMasterEntity ame = new AccountMasterEntity();
			ame.setAccountnumber(customerMaintenanceModel.getAccountNumber());
			// 根据accountNumber查询账号
			List<AccountMasterEntity> accountList = accountMasterService.findAccountByParams(ame);
			if (accountList == null || accountList.size() == 0) {
				map.put("msg", "Record Not Found");
				map.put("code", "0");
				return objectMapper.writeValueAsString(map);
			}
			CustomerMasterEntity customer = new CustomerMasterEntity();
			customer.setMailingaddress(customerMaintenanceModel.getMailingAddress());
			customer.setMobilephonenumber(customerMaintenanceModel.getMobilePhoneNumber());
			customer.setCustomerid(customerMaintenanceModel.getCustomerID());
			if (customerMasterService.contactInformationUpdate(customer) > 0) {
				map.put("msg", customerMaintenanceModel.getAccountNumber()
						+ "-Customer Contact Information already Record Changed");
				map.put("code", "1");
				String logstr = "update accountNumber:" + customerMaintenanceModel.getAccountNumber() + " contact information success!";
				LogUtil.saveLog(
						restTemplate, 
						SysConstant.OPERATION_UPDATE, 
						SysConstant.LOCAL_SERVICE_NAME, 
						SysConstant.OPERATION_SUCCESS, 
						logstr
						);
				return objectMapper.writeValueAsString(map);
			} else {
				map.put("msg", "Record Change Fail");
				map.put("code", "0");
				return objectMapper.writeValueAsString(map);
			}
		} catch (Exception e) {
			map.put("msg", "Record Change Fail");
			map.put("code", "0");
		}

		return objectMapper.writeValueAsString(map);
	}

	/**
	 * 存款
	 * 
	 * @param cam
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/deposit", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This api is for deposit", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 0, message = "deposit Fail!"),
			@ApiResponse(code = 1, message = "deposit Success!") })
	@ApiImplicitParam(paramType = "body", name = "depositModel", required = true, value = "depositModel")
	public String deposit(@RequestBody DepositModel depositModel) throws JsonProcessingException {
		Map<String, Object> map = null;
		try {
			map = accountMasterService.deposit(depositModel, restTemplate);
		} catch (Exception e) {
			map.put("msg", "Transaction Fail");
			map.put("code", "0");
		}
		return objectMapper.writeValueAsString(map);
	}

	/**
	 * 取款
	 * 
	 * @param cam
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/withdrawal", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This api is for withdrawal", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 0, message = "withdrawal Fail!"),
			@ApiResponse(code = 1, message = "withdrawal Success!") })
	@ApiImplicitParam(paramType = "body", name = "withDrawalModel", required = true, value = "withDrawalModel")
	public String withdrawal(@RequestBody WithDrawalModel withDrawalModel) throws JsonProcessingException {
		Map<String, Object> map = null;
		try {
			map = accountMasterService.withdrawal(withDrawalModel, restTemplate);
		} catch (Exception e) {
			map.put("msg", "Transaction Fail");
			map.put("code", "0");
		}
		return objectMapper.writeValueAsString(map);
	}
	
	/**
	 * 查找账号 Alina 
	 * 
	 * @param accountNumModel
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value="/accountSearch", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This api is for account search", notes = "version 0.0.1")
	@ApiImplicitParam(paramType = "body", name = "accountNumModel", required = true, value = "accountNumModel")
	public String accountSearch(@RequestBody AccountNumber accountNumModel) throws JsonProcessingException{
		@SuppressWarnings("unused")
		Map<String, Object> map = new HashMap<String, Object>();
		AccountMasterEntity ame = new AccountMasterEntity();
		ame.setAccountnumber(accountNumModel.getAccountNumber());
		ame.setAccounttype(accountNumModel.getAccountType());
		ame.setCurrencycode(accountNumModel.getCcycode());
		// 根据accountNumber查询账号
		List<AccountMasterEntity> accountList = accountMasterService.findAccountByParams(ame);
		if(accountList == null || accountList.size() == 0){
			return null;
		}else{
			return objectMapper.writeValueAsString(accountList.get(0));
		}
	}
	
	/**
	 * Alina 查找账号
	 * 
	 * @param accountNumModel
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value="/updateAccountInfo", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This api is for update account master information", notes = "version 0.0.1")
	@ApiImplicitParam(paramType = "body", name = "accountNumModel", required = true, value = "accountNumModel")
	public String updateAccountInfo(@RequestBody AccountMasterEntity accountInfo) throws JsonProcessingException{
		Map<String, Object> map = new HashMap<String, Object>();
		Date lastupdateddate = null;
		try {
			lastupdateddate= sf.parse(sf.format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		accountInfo.setLastupdateddate(lastupdateddate);
		try{
			int a = accountMasterService.update(accountInfo);
			if(a>0){
				map.put("msg", "update success");
				map.put("code", "1");
				String logstr = "update accountNumber:" + accountInfo.getAccountnumber() + " account infomation success!";
				LogUtil.saveLog(
						restTemplate, 
						SysConstant.OPERATION_UPDATE, 
						SysConstant.LOCAL_SERVICE_NAME, 
						SysConstant.OPERATION_SUCCESS, 
						logstr
						);
			}else{
				map.put("msg", "Record Change Fail");
				map.put("code", "0");
			}
		}catch(Exception e){
			map.put("msg", "Record Change Fail");
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
		String param1 = "{\"apiname\":\"getSystemParameter\"}";
		ResponseEntity<String> result1 = restTemplate.postForEntity(
				"http://" + CommonConstant.getSYSADMIN() + SysConstant.SERVICE_INTERNAL_URL + "",
				PostUtil.getRequestEntity(param1), String.class);
		if (result1 == null) {
			map.put("msg", "调用服务接口地址失败");
			map.put("code", "0");
		}
		String path = JsonProcess.returnValue(JsonProcess.changeToJSONObject(result1.getBody()), "internaURL");

		// 调用系统参数服务接口
		String param2 = "{\"item\":\"ClearingCode,BranchNumber,LocalCcy,NextAvailableCustomerNumber\"}";
		ResponseEntity<String> result2 = restTemplate.postForEntity(path, PostUtil.getRequestEntity(param2),
				String.class);
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
		for (int i = 0; i < JsonProcess.changeToJSONArray(result2.getBody()).size(); i++) {
			jsonObject1 = JsonProcess
					.changeToJSONObject(JsonProcess.changeToJSONArray(result2.getBody()).get(i).toString());
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
		String result3 = restTemplate
				.getForEntity("http://SYSADMIN/sysadmin/generate/getNextAvailableNumber/NextAvailableCustomerNumber", String.class).getBody();
		String customerNumber = "";
		if (result3 == null) {
			map.put("msg", "调用系统参数失败");
			map.put("code", "0");
		}

		// 返回数据处理
		customerNumber = JsonProcess.returnValue(JsonProcess.changeToJSONObject(result3), "nextAvailableNumber");

		cam.getCustomer().setCustomernumber(clearcode + branchnumber + customerNumber + cam.getAccount().getAccounttype());
		cam.getCustomer().setId(UUIDUtil.generateUUID());

		cam.getAccount().setBalance(new BigDecimal(0));
		cam.getAccount().setId(UUIDUtil.generateUUID());
		cam.getAccount().setAccountnumber(clearcode + branchnumber + customerNumber + cam.getAccount().getAccounttype());

		map.put("msg", "处理成功");
		map.put("localCCy", localCCy);
		map.put("code", "1");

		return objectMapper.writeValueAsString(map);
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
		if (cam.getAccount().getAccounttype() == null || "".equals(cam.getAccount().getAccounttype())) {
			return false;
		}

		return true;
	}
}
