package com.csi.sbs.deposit.business.service;


import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

import com.csi.sbs.deposit.business.clientmodel.DepositModel;
import com.csi.sbs.deposit.business.clientmodel.WithDrawalModel;
import com.csi.sbs.deposit.business.entity.AccountMasterEntity;

public interface AccountMasterService {
	
	
	   public int createAccount(AccountMasterEntity ame);
	   
	   public List<AccountMasterEntity> findAccountByParams(AccountMasterEntity ame);
	   
	   public int closeAccount(AccountMasterEntity ame);
	   
	   public Map<String,Object> deposit(DepositModel depositModel,RestTemplate restTemplate) throws ParseException;
	   
	   public Map<String,Object> withdrawal(WithDrawalModel withDrawalModel,RestTemplate restTemplate) throws ParseException;

}
