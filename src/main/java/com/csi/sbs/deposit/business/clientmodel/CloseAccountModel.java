package com.csi.sbs.deposit.business.clientmodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class CloseAccountModel {
	
	@ApiModelProperty(required=true)
	private String accountNumber;
	   
	@ApiModelProperty(required=true)
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
