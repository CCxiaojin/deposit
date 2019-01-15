package com.csi.sbs.deposit.business.clientmodel;

public class DepositModel {

	private String accountNumber;
	private String depositCCyCode;
	private String depositAmount;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getDepositCCyCode() {
		return depositCCyCode;
	}

	public void setDepositCCyCode(String depositCCyCode) {
		this.depositCCyCode = depositCCyCode;
	}

	public String getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(String depositAmount) {
		this.depositAmount = depositAmount;
	}

}
