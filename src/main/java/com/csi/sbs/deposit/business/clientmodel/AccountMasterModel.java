package com.csi.sbs.deposit.business.clientmodel;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel
public class AccountMasterModel {
	
    
	private String id;

    private String accountnumber;

    private String accounttype;

    private String accountstatus;

    @ApiModelProperty(required=true)
    private String currencycode;

    private BigDecimal balance;

    private Date lastupdateddate;

    private String chequebooktype;

    private Short chequebooksize;
    
    

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

	public String getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}

	public String getAccountstatus() {
		return accountstatus;
	}

	public void setAccountstatus(String accountstatus) {
		this.accountstatus = accountstatus;
	}

	public String getCurrencycode() {
		return currencycode;
	}

	public void setCurrencycode(String currencycode) {
		this.currencycode = currencycode;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Date getLastupdateddate() {
		return lastupdateddate;
	}

	public void setLastupdateddate(Date lastupdateddate) {
		this.lastupdateddate = lastupdateddate;
	}

	public String getChequebooktype() {
		return chequebooktype;
	}

	public void setChequebooktype(String chequebooktype) {
		this.chequebooktype = chequebooktype;
	}

	public Short getChequebooksize() {
		return chequebooksize;
	}

	public void setChequebooksize(Short chequebooksize) {
		this.chequebooksize = chequebooksize;
	}

	
    
}