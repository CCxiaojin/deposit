package com.csi.sbs.deposit.business.clientmodel;

public class WithDrawalModel {

	private String accountNumber;
	private String withDrawalCCyCode;
	private String withDrawalAmount;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getWithDrawalCCyCode() {
		return withDrawalCCyCode;
	}

	public void setWithDrawalCCyCode(String withDrawalCCyCode) {
		this.withDrawalCCyCode = withDrawalCCyCode;
	}

	public String getWithDrawalAmount() {
		return withDrawalAmount;
	}

	public void setWithDrawalAmount(String withDrawalAmount) {
		this.withDrawalAmount = withDrawalAmount;
	}

}
