package com.csi.sbs.deposit.business.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.csi.sbs.deposit.business.dao.TermDepositEnquiryDao;
import com.csi.sbs.deposit.business.service.TermDepositEnquiryService;

@Service("TermDepositEnquiryService")
public  class TermDepositEnquiryServiceImpl implements TermDepositEnquiryService{
	
	
	@SuppressWarnings("rawtypes")
	@Resource
	private TermDepositEnquiryDao termDepositeEnquiryDao;
	
	@Override
	public int searchTermIDAndAccount(String ID,String AccountNumber) {
		
		return termDepositeEnquiryDao.searchTermIDAndAccount(ID,AccountNumber);
	}
	
}
