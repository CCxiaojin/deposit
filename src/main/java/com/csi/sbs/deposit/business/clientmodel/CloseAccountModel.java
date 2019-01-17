package com.csi.sbs.deposit.business.clientmodel;

import io.swagger.annotations.ApiModel;

@ApiModel
public class CloseAccountModel {

	private String accountNumber;

	private String accountType;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

}
