package com.csi.sbs.deposit.business.service.impl;

import java.util.List;

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

	@SuppressWarnings("unchecked")
	@Override
	public List<AccountMasterEntity> findAccountByParams(AccountMasterEntity ame) {
		return accountMasterDao.findAccountByParams(ame);
	}

	@Override
	public int closeAccount(AccountMasterEntity ame) {
		return accountMasterDao.closeAccount(ame);
	}

}
