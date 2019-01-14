package com.csi.sbs.deposit.business.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public String[] createCustomer(CustomerAndAccountModel cam,boolean flag,CustomerMasterEntity recustomer) {
		if(flag){
			cam.getAccount().setCustomerprimarykeyid(recustomer.getId());
			accountMasterDao.insert(cam.getAccount());
		}else{
			customerMasterDao.insert(cam.getCustomer());
			cam.getAccount().setCustomerprimarykeyid(cam.getCustomer().getId());
			accountMasterDao.insert(cam.getAccount());
		}
		String[] temp = new String[2];
		temp[0] = cam.getAccount().getAccountnumber();
		temp[1] = cam.getCustomer().getId();
		return temp;
	}

	@Override
	public int contactInformationUpdate(CustomerMasterEntity cme) {
		return customerMasterDao.contactInformationUpdate(cme);
	}

	@Override
	public CustomerMasterEntity findCustomerByCustomerID(CustomerMasterEntity cme) {
		return customerMasterDao.findCustomerByCustomerID(cme);
	}

}
