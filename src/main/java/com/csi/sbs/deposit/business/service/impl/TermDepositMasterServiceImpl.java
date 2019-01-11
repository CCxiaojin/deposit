package com.csi.sbs.deposit.business.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csi.sbs.deposit.business.clientmodel.TermDepositMasterModel;
import com.csi.sbs.deposit.business.dao.TermDepositMasterDao;
import com.csi.sbs.deposit.business.service.TermDepositMasterService;


@Service("TermDepositMasterService")
public class TermDepositMasterServiceImpl implements TermDepositMasterService{
	
	
	
	@SuppressWarnings("rawtypes")
	@Resource
	private TermDepositMasterDao tdmDao;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void termDepositApplication(TermDepositMasterModel tdm) {
		
		tdmDao.insert(tdm);
	}	

}
