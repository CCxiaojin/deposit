package com.csi.sbs.deposit.business.entity;

import java.math.BigDecimal;
import java.util.Date;

public class AccountMasterEntity {
	
    private String id;

    private Short clearingcode;

    private Short branchnumber;

    private Long customernumber;

    private Short accounttype;

    private String accountstatus;

    private String currencycode;

    private BigDecimal balance;

    private Date lastupdateddate;

    private String chequebooktype;

    private Short chequebooksize;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Short getClearingcode() {
        return clearingcode;
    }

    public void setClearingcode(Short clearingcode) {
        this.clearingcode = clearingcode;
    }

    public Short getBranchnumber() {
        return branchnumber;
    }

    public void setBranchnumber(Short branchnumber) {
        this.branchnumber = branchnumber;
    }

    public Long getCustomernumber() {
        return customernumber;
    }

    public void setCustomernumber(Long customernumber) {
        this.customernumber = customernumber;
    }

    public Short getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(Short accounttype) {
        this.accounttype = accounttype;
    }

    public String getAccountstatus() {
        return accountstatus;
    }

    public void setAccountstatus(String accountstatus) {
        this.accountstatus = accountstatus == null ? null : accountstatus.trim();
    }

    public String getCurrencycode() {
        return currencycode;
    }

    public void setCurrencycode(String currencycode) {
        this.currencycode = currencycode == null ? null : currencycode.trim();
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
        this.chequebooktype = chequebooktype == null ? null : chequebooktype.trim();
    }

    public Short getChequebooksize() {
        return chequebooksize;
    }

    public void setChequebooksize(Short chequebooksize) {
        this.chequebooksize = chequebooksize;
    }
}