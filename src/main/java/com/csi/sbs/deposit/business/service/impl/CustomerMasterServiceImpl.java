package com.csi.sbs.deposit.business.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csi.sbs.common.business.httpclient.ConnPostClient;
import com.csi.sbs.common.business.util.UUIDUtil;
import com.csi.sbs.deposit.business.clientmodel.CustomerAndAccountModel;
import com.csi.sbs.deposit.business.dao.AccountMasterDao;
import com.csi.sbs.deposit.business.dao.CustomerMasterDao;
import com.csi.sbs.deposit.business.entity.CustomerMasterEntity;
import com.csi.sbs.deposit.business.service.CustomerMasterService;


@Service("CustomerMasterService")
public class CustomerMasterServiceImpl implements CustomerMasterService{
	
	
	@SuppressWarnings("rawtypes")
	@Resource
	private CustomerMasterDao customerMasterDao;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private AccountMasterDao accountMasterDao;

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerMasterEntity> queryAll() {
		return customerMasterDao.queryAll();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void createCustomer(CustomerAndAccountModel cam) {
		customerMasterDao.insert(cam.getCustomer());
		accountMasterDao.insert(cam.getAccount());
	}

}
