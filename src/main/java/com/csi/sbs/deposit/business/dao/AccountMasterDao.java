package com.csi.sbs.deposit.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.csi.sbs.deposit.business.base.BaseDao;
import com.csi.sbs.deposit.business.entity.AccountMasterEntity;

@Mapper
public interface AccountMasterDao<T> extends BaseDao<T> {

	public List<AccountMasterEntity> findAccountByParams(AccountMasterEntity ame);
	
	public int closeAccount(AccountMasterEntity ame);
	
}
