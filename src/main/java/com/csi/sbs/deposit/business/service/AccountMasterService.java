package com.csi.sbs.deposit.business.service;


import java.util.List;

import com.csi.sbs.deposit.business.entity.AccountMasterEntity;

public interface AccountMasterService {
	
	
	   public int createAccount(AccountMasterEntity ame);
	   
	   public List<AccountMasterEntity> findAccountByParams(AccountMasterEntity ame);
	   
	   public int closeAccount(AccountMasterEntity ame);

}
