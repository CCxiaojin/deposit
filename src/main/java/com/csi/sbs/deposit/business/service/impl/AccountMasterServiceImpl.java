package com.csi.sbs.deposit.business.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


import com.csi.sbs.deposit.business.dao.AccountMasterDao;
import com.csi.sbs.deposit.business.entity.AccountMasterEntity;
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

}
