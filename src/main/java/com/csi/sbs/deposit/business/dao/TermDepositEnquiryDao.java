package com.csi.sbs.deposit.business.dao;


import org.apache.ibatis.annotations.Mapper;

import com.csi.sbs.deposit.business.base.BaseDao;


@Mapper
public interface TermDepositEnquiryDao<T> extends BaseDao<T>{

	public int searchTermIDAndAccount(String ID, String AccountNumber);
}