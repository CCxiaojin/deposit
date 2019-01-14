package com.csi.sbs.deposit.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.csi.sbs.deposit.business.base.BaseDao;
import com.csi.sbs.deposit.business.entity.CustomerMasterEntity;


@Mapper
public interface CustomerMasterDao<T> extends BaseDao<T> {
	
	
	public List<CustomerMasterEntity> queryAll();
	
	public int contactInformationUpdate(CustomerMasterEntity cme);
	
	public CustomerMasterEntity findCustomerByCustomerID(CustomerMasterEntity cme);

}
