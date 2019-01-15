package com.csi.sbs.deposit.business.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.csi.sbs.common.business.util.UUIDUtil;
import com.csi.sbs.deposit.business.clientmodel.CurrencyModel;
import com.csi.sbs.deposit.business.clientmodel.DepositModel;
import com.csi.sbs.deposit.business.constant.SysConstant;
import com.csi.sbs.deposit.business.dao.AccountMasterDao;
import com.csi.sbs.deposit.business.entity.AccountMasterEntity;
import com.csi.sbs.deposit.business.entity.SysTransactionLogEntity;
import com.csi.sbs.deposit.business.service.AccountMasterService;

@Service("AccountMasterService")
public class AccountMasterServiceImpl implements AccountMasterService{
	
	
	@SuppressWarnings("rawtypes")
	@Resource
	private AccountMasterDao accountMasterDao;

	@SuppressWarnings("unchecked")
	@Override
	public int createAccount(AccountMasterEntity ame) {
		return accountMasterDao.insert(ame);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AccountMasterEntity> findAccountByParams(AccountMasterEntity ame) {
		return accountMasterDao.findAccountByParams(ame);
	}

	@Override
	public int closeAccount(AccountMasterEntity ame) {
		return accountMasterDao.closeAccount(ame);
	}

	
	@Override
	@Transactional
	public Map<String,Object> deposit(DepositModel depositModel,RestTemplate restTemplate) throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String,Object> map = new HashMap<String,Object>();
		//校验必填字段
		if(!validateMandatoryField(depositModel)){
			map.put("msg", "Required field incomplete");
			map.put("code", "0");
			
			return map;
		}
		//校验是否支持输入的ccy
		CurrencyModel currency = new CurrencyModel();
		currency.setCcycode(depositModel.getDepositCCyCode());
		String flag = restTemplate.postForObject("http://localhost:8083/sysadmin/isSupportbyccy", currency,String.class);
		if(flag.equals("false")){
			map.put("msg", "Currency Not Found");
			map.put("code", "1");
			
			return map;
		}
		//根据accountNumber 和 ccy 查询
		AccountMasterEntity account = new AccountMasterEntity();
		account.setAccountnumber(depositModel.getAccountNumber());
		account.setCurrencycode(depositModel.getDepositCCyCode());
		
		@SuppressWarnings("unchecked")
		List<AccountMasterEntity> accountList = accountMasterDao.findAccountByParams(account);
		if(accountList==null || accountList.size()==0){
			map.put("msg", "Record Not Found");
			map.put("code", "0");
			
			return map;
		}
		//校验账号状态是否是Active的
		if(!accountList.get(0).getAccountstatus().equals(SysConstant.ACCOUNT_STATE2)){
			map.put("msg", "Account is not active");
			map.put("code", "0");
			
			return map;
		}
		
		//账号余额增加
		//原来余额
		BigDecimal balance1 = BigDecimal.valueOf(Double.parseDouble(accountList.get(0).getBalance().toString()));
		//存款余额
		BigDecimal balance2 = BigDecimal.valueOf(Double.parseDouble(depositModel.getDepositAmount()));
		//总余额
		BigDecimal balance3 = balance1.add(balance2);
		account.setBalance(balance3);
		account.setLastupdateddate(sf.parse(sf.format(new Date())));
		
		//存款
		accountMasterDao.deposit(account);
		//写入日志
		depositLog(depositModel.getAccountNumber(),restTemplate);
		
		map.put("msg", "Transaction Accepted:" + depositModel.getAccountNumber());
		map.put("code", "1");
			
		return map;
	}
	
	/**
	 * 存款日志
	 * @param accountNumber
	 * @return
	 * @throws ParseException 
	 */
	private String depositLog(String accountNumber,RestTemplate restTemplate) throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SysTransactionLogEntity log = new SysTransactionLogEntity();
		log.setId(UUIDUtil.generateUUID());
		log.setOperationtype(SysConstant.OPERATION_UPDATE);
		log.setSourceservices(SysConstant.LOCAL_SERVICE_NAME);
		log.setOperationstate(SysConstant.OPERATION_SUCCESS);
		log.setOperationdate(sf.parse(sf.format(new Date())));
		log.setOperationdetail("Transaction Accepted:" + accountNumber);
		@SuppressWarnings("unused")
		String result = restTemplate.postForObject("http://localhost:8083/sysadmin/isSupportbyccy", log,String.class);
		return accountNumber;
	}

	
	private boolean validateMandatoryField(DepositModel depositModel){
		if(depositModel.getAccountNumber()==null || "".equals(depositModel.getAccountNumber())){
			return false;
		}
		if(depositModel.getDepositAmount()==null || "".equals(depositModel.getDepositAmount()) || depositModel.getDepositAmount().equals("0")){
			return false;
		}
		if(depositModel.getDepositCCyCode()==null || "".equals(depositModel.getDepositCCyCode())){
			return false;
		}
		return true;
	}
}
