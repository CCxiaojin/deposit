package com.csi.sbs.deposit.business.service;

import java.util.List;

import com.csi.sbs.deposit.business.clientmodel.CustomerAndAccountModel;
import com.csi.sbs.deposit.business.entity.CustomerMasterEntity;

public interface CustomerMasterService {
	
	
	   public List<CustomerMasterEntity> queryAll();
	   
	   public String createCustomer(CustomerAndAccountModel cam,boolean flag);
	   
	   public int contactInformationUpdate(CustomerMasterEntity cme);
	   
	   public CustomerMasterEntity findCustomerByCustomerID(CustomerMasterEntity cme);

}
