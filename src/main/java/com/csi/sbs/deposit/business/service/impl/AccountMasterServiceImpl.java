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

import com.csi.sbs.common.business.constant.CommonConstant;
import com.csi.sbs.deposit.business.clientmodel.CurrencyModel;
import com.csi.sbs.deposit.business.clientmodel.DepositModel;
import com.csi.sbs.deposit.business.clientmodel.WithDrawalModel;
import com.csi.sbs.deposit.business.constant.SysConstant;
import com.csi.sbs.deposit.business.dao.AccountMasterDao;
import com.csi.sbs.deposit.business.entity.AccountMasterEntity;
import com.csi.sbs.deposit.business.service.AccountMasterService;
import com.csi.sbs.deposit.business.util.LogUtil;

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
	public int update(AccountMasterEntity ame) {
		return accountMasterDao.update(ame);
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
		String flag = restTemplate.postForObject("http://"+CommonConstant.getSYSADMIN()+"/sysadmin/isSupportbyccy", currency,String.class);
		if(flag.equals("false")){
			map.put("msg", "Currency Not Supported");
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
		String logstr = "Transaction Accepted:" + depositModel.getAccountNumber();
		LogUtil.saveLog(
				restTemplate, 
				SysConstant.OPERATION_UPDATE, 
				SysConstant.LOCAL_SERVICE_NAME, 
				SysConstant.OPERATION_SUCCESS, 
				logstr);
		map.put("msg", "Transaction Accepted:" + depositModel.getAccountNumber());
		map.put("code", "1");
			
		return map;
	}

	/**
	 * 存款必填字段校验
	 * @param depositModel
	 * @return
	 */
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



	@Override
	@Transactional
	public Map<String, Object> withdrawal(WithDrawalModel withDrawalModel, RestTemplate restTemplate)
			throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String,Object> map = new HashMap<String,Object>();
		//校验必填字段
		if(!validateMandatoryField(withDrawalModel)){
			map.put("msg", "Required field incomplete");
			map.put("code", "0");
			
			return map;
		}
		//校验是否支持输入的ccy
		CurrencyModel currency = new CurrencyModel();
		currency.setCcycode(withDrawalModel.getWithDrawalCCyCode());
		String flag = restTemplate.postForObject("http://"+CommonConstant.getSYSADMIN()+"/sysadmin/isSupportbyccy", currency,String.class);
		if(flag.equals("false")){
			map.put("msg", "Currency Not Supported");
			map.put("code", "1");
			
			return map;
		}
		//根据accountNumber 和 ccy 查询
		AccountMasterEntity account = new AccountMasterEntity();
		account.setAccountnumber(withDrawalModel.getAccountNumber());
		account.setCurrencycode(withDrawalModel.getWithDrawalCCyCode());
		
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
		//check账户余额
		if(accountList.get(0).getBalance().compareTo(BigDecimal.valueOf(Double.parseDouble(withDrawalModel.getWithDrawalAmount())))==-1){
			map.put("msg", "Insufficient Fund");
			map.put("code", "0");
			
			return map;
		}
		
		//减账户余额
		//原来余额
		BigDecimal balance1 = BigDecimal.valueOf(Double.parseDouble(accountList.get(0).getBalance().toString()));
		//取款余额
		BigDecimal balance2 = BigDecimal.valueOf(Double.parseDouble(withDrawalModel.getWithDrawalAmount()));
		//总余额
		BigDecimal balance3 = balance1.subtract(balance2);
		account.setBalance(balance3);
		account.setLastupdateddate(sf.parse(sf.format(new Date())));
		
		//取款
		accountMasterDao.withdrawal(account);
		//写入日志
		String logstr = "Transaction Accepted:" + withDrawalModel.getAccountNumber();
		LogUtil.saveLog(
				restTemplate, 
				SysConstant.OPERATION_UPDATE, 
				SysConstant.LOCAL_SERVICE_NAME,
				SysConstant.OPERATION_SUCCESS, 
				logstr);
		map.put("msg", "Transaction Accepted:" + withDrawalModel.getAccountNumber());
		map.put("code", "1");
			
		return map;
	}
	
	/**
	 * 存款必填字段校验
	 * @param depositModel
	 * @return
	 */
	private boolean validateMandatoryField(WithDrawalModel withDrawalModel){
		if(withDrawalModel.getAccountNumber()==null || "".equals(withDrawalModel.getAccountNumber())){
			return false;
		}
		if(withDrawalModel.getWithDrawalAmount()==null || "".equals(withDrawalModel.getWithDrawalAmount()) || withDrawalModel.getWithDrawalAmount().equals("0")){
			return false;
		}
		if(withDrawalModel.getWithDrawalCCyCode()==null || "".equals(withDrawalModel.getWithDrawalCCyCode())){
			return false;
		}
		return true;
	}
}
