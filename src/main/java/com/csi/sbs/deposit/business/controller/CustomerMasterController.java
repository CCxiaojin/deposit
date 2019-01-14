package com.csi.sbs.deposit.business.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.csi.sbs.deposit.business.clientmodel.CloseAccountModel;
import com.csi.sbs.deposit.business.clientmodel.CustomerAndAccountModel;
import com.csi.sbs.deposit.business.clientmodel.CustomerMaintenanceModel;
import com.csi.sbs.deposit.business.constant.SysConstant;
import com.csi.sbs.deposit.business.entity.AccountMasterEntity;
import com.csi.sbs.deposit.business.entity.CustomerMasterEntity;
import com.csi.sbs.deposit.business.entity.SysTransactionLogEntity;
import com.csi.sbs.deposit.business.service.AccountMasterService;
import com.csi.sbs.deposit.business.service.CustomerMasterService;
import com.csi.sbs.deposit.business.util.WriteLogUtil;

@CrossOrigin // 解决跨域请求
@Controller
@RequestMapping("/deposit/account")
@Api(value = "Then controller is deposit account")
public class CustomerMasterController {
	
	@Resource
	private CustomerMasterService customerMasterService;
	
	@Resource
	private AccountMasterService accountMasterService;
	
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
	@ApiImplicitParam(required = true)
	public String openingSavingAccount(@RequestBody CustomerAndAccountModel customerAndAccountModel) throws JsonProcessingException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (!JsonProcess.returnValue(JsonProcess.changeToJSONObject(commonBusinessProcess(customerAndAccountModel)), "code")
					.equals("1")) {
				return commonBusinessProcess(customerAndAccountModel);
			}
			//校验客户是否存在
			CustomerMasterEntity cme = new CustomerMasterEntity();
			cme.setCustomerid(customerAndAccountModel.getCustomer().getCustomerid());
			CustomerMasterEntity recustomer = customerMasterService.findCustomerByCustomerID(cme);
			boolean flag = false;
			if(recustomer!=null){
				flag = true;
			}
			customerAndAccountModel.getAccount().setCurrencycode(
					JsonProcess.returnValue(JsonProcess.changeToJSONObject(commonBusinessProcess(customerAndAccountModel)), "localCCy"));
			customerAndAccountModel.getAccount().setAccounttype(SysConstant.ACCOUNT_TYPE1);
			String accountNumber = customerMasterService.createCustomer(customerAndAccountModel,flag);
			
			//写入日志
			createAccountLog(accountNumber);
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
	@ApiImplicitParam(required = true)
	public String openingCurrentAccount(@RequestBody CustomerAndAccountModel customerAndAccountModel) throws JsonProcessingException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (!JsonProcess.returnValue(JsonProcess.changeToJSONObject(commonBusinessProcess(customerAndAccountModel)), "code")
					.equals("1")) {
				return commonBusinessProcess(customerAndAccountModel);
			}
			//校验客户是否存在
			CustomerMasterEntity cme = new CustomerMasterEntity();
			cme.setCustomerid(customerAndAccountModel.getCustomer().getCustomerid());
			CustomerMasterEntity recustomer = customerMasterService.findCustomerByCustomerID(cme);
			boolean flag = false;
			if(recustomer!=null){
				flag = true;
			}
			customerAndAccountModel.getAccount().setCurrencycode(
					JsonProcess.returnValue(JsonProcess.changeToJSONObject(commonBusinessProcess(customerAndAccountModel)), "localCCy"));
			customerAndAccountModel.getAccount().setAccounttype(SysConstant.ACCOUNT_TYPE2);
			String accountNumber = customerMasterService.createCustomer(customerAndAccountModel,flag);
			//写入日志
			createAccountLog(accountNumber);
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
	@ApiImplicitParam(required = true)
	public String openingFEAccount(@RequestBody CustomerAndAccountModel customerAndAccountModel) throws JsonProcessingException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (!JsonProcess.returnValue(JsonProcess.changeToJSONObject(commonBusinessProcess(customerAndAccountModel)), "code")
					.equals("1")) {
				return commonBusinessProcess(customerAndAccountModel);
			}
			//校验客户是否存在
			CustomerMasterEntity cme = new CustomerMasterEntity();
			cme.setCustomerid(customerAndAccountModel.getCustomer().getCustomerid());
			CustomerMasterEntity recustomer = customerMasterService.findCustomerByCustomerID(cme);
			boolean flag = false;
			if(recustomer!=null){
				flag = true;
			}
			customerAndAccountModel.getAccount().setBalance(new BigDecimal(0));
			customerAndAccountModel.getAccount().setAccounttype(SysConstant.ACCOUNT_TYPE3);
			String accountNumber = customerMasterService.createCustomer(customerAndAccountModel,flag);
			//写入日志
			createAccountLog(accountNumber);
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
	@ApiImplicitParam(required = true)
	public String openingTDAccount(@RequestBody CustomerAndAccountModel customerAndAccountModel) throws JsonProcessingException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (!JsonProcess.returnValue(JsonProcess.changeToJSONObject(commonBusinessProcess(customerAndAccountModel)), "code")
					.equals("1")) {
				return commonBusinessProcess(customerAndAccountModel);
			}
			//校验客户是否存在
			CustomerMasterEntity cme = new CustomerMasterEntity();
			cme.setCustomerid(customerAndAccountModel.getCustomer().getCustomerid());
			CustomerMasterEntity recustomer = customerMasterService.findCustomerByCustomerID(cme);
			boolean flag = false;
			if(recustomer!=null){
				flag = true;
			}
			customerAndAccountModel.getAccount().setAccounttype(SysConstant.ACCOUNT_TYPE4);
			String accountNumber = customerMasterService.createCustomer(customerAndAccountModel,flag);
			//写入日志
			createAccountLog(accountNumber);
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
	 * 账号关闭
	 * @param cam
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/accountClosure", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This api is for close account", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 0, message = "close Fail!"),
		@ApiResponse(code = 1, message = "close Success!") })
    @ApiImplicitParam(required = true)
	public String accountClosure(@RequestBody CloseAccountModel closeAccountModel) throws JsonProcessingException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			AccountMasterEntity ame = new AccountMasterEntity();
			ame.setAccountnumber(closeAccountModel.getAccountNumber());
			ame.setAccounttype(closeAccountModel.getAccountType());
			//根据accountNumber 和 accountType查询账号
			List<AccountMasterEntity> accountList = accountMasterService.findAccountByParams(ame);
			if(accountList==null || accountList.size()==0){
				map.put("msg", "Record Not Found");
				map.put("code", "0");
				return objectMapper.writeValueAsString(map);
			}
			//校验账户余额是否大于0
			int r = accountList.get(0).getBalance().compareTo(BigDecimal.ZERO);
			if(r!=0){
				map.put("msg", "Account Balance Not Zero");
				map.put("code", "0");
				return objectMapper.writeValueAsString(map);
			}
			
			ame.setAccountstatus(SysConstant.ACCOUNT_STATE1);
			ame.setLastupdateddate(sf.parse(sf.format(new Date())));
			if(accountMasterService.closeAccount(ame)>0){
				map.put("msg", "Account Deleted Success");
				map.put("code", "1");
				closeAccountLog(ame.getAccountnumber());
			}else{
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
	 * @param cam
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/updateCustContactInfo", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This api is for update customer contact Information", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 0, message = "update Fail!"),
		@ApiResponse(code = 1, message = "update Success!") })
    @ApiImplicitParam(required = true)
	public String updateCustContactInfo(@RequestBody CustomerMaintenanceModel customerMaintenanceModel) throws JsonProcessingException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			AccountMasterEntity ame = new AccountMasterEntity();
			ame.setAccountnumber(customerMaintenanceModel.getAccountNumber());
			//根据accountNumber查询账号
			List<AccountMasterEntity> accountList = accountMasterService.findAccountByParams(ame);
			if(accountList==null || accountList.size()==0){
				map.put("msg", "Record Not Found");
				map.put("code", "0");
				return objectMapper.writeValueAsString(map);
			}
			CustomerMasterEntity customer = new CustomerMasterEntity();
			customer.setMailingaddress(customerMaintenanceModel.getMailingAddress());
			customer.setMobilephonenumber(customerMaintenanceModel.getMobilePhoneNumber());
			customer.setCustomerid(customerMaintenanceModel.getCustomerID());
			if(customerMasterService.contactInformationUpdate(customer)>0){
				map.put("msg", customerMaintenanceModel.getAccountNumber()+"-Customer Contact Information already Record Changed");
				map.put("code", "1");
				updateAccountLog(customerMaintenanceModel.getAccountNumber());
				return objectMapper.writeValueAsString(map);
			}else{
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

	/**
	 * 创建账号日志
	 * @param accountNumber
	 * @return
	 */
	private boolean createAccountLog(String accountNumber) {
		try {
			SysTransactionLogEntity log = new SysTransactionLogEntity();
		    log.setId(UUIDUtil.generateUUID());
		    log.setUserid("000000");
		    log.setUsername("测试账号");
		    log.setOperationtype(SysConstant.OPERATION_CREATE);
		    log.setSourceservices(SysConstant.LOCAL_SERVICE_NAME);
		    log.setOperationstate(SysConstant.OPERATION_SUCCESS);
		    log.setOperationdate(sf.parse(sf.format(new Date())));
		    log.setOperationdetail("create accountNumber:"+accountNumber+" success!");
		    WriteLogUtil.writeLog(SysConstant.WRITE_LOG_SERVICEPATH, JsonProcess.changeEntityTOJSON(log));
		} catch (Exception e) {
          return false;
		}
		return true;
	}
	
	/**
	 * 创建关闭账号日志
	 * @param accountNumber
	 * @return
	 */
	private boolean closeAccountLog(String accountNumber) {
		try {
			SysTransactionLogEntity log = new SysTransactionLogEntity();
		    log.setId(UUIDUtil.generateUUID());
		    log.setUserid("000000");
		    log.setUsername("测试账号");
		    log.setOperationtype(SysConstant.OPERATION_DELETE);
		    log.setSourceservices(SysConstant.LOCAL_SERVICE_NAME);
		    log.setOperationstate(SysConstant.OPERATION_SUCCESS);
		    log.setOperationdate(sf.parse(sf.format(new Date())));
		    log.setOperationdetail("close accountNumber:"+accountNumber+" success!");
		    WriteLogUtil.writeLog(SysConstant.WRITE_LOG_SERVICEPATH, JsonProcess.changeEntityTOJSON(log));
		} catch (Exception e) {
          return false;
		}
		return true;
	}

	/**
	 * 账号修改
	 */
	private boolean updateAccountLog(String accountNumber){
		try {
			SysTransactionLogEntity log = new SysTransactionLogEntity();
		    log.setId(UUIDUtil.generateUUID());
		    log.setUserid("000000");
		    log.setUsername("测试账号");
		    log.setOperationtype(SysConstant.OPERATION_UPDATE);
		    log.setSourceservices(SysConstant.LOCAL_SERVICE_NAME);
		    log.setOperationstate(SysConstant.OPERATION_SUCCESS);
		    log.setOperationdate(sf.parse(sf.format(new Date())));
		    log.setOperationdetail("update accountNumber:"+accountNumber+" contact information success!");
		    WriteLogUtil.writeLog(SysConstant.WRITE_LOG_SERVICEPATH, JsonProcess.changeEntityTOJSON(log));
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
