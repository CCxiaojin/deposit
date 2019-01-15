package com.csi.sbs.deposit.business.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TermDepositEnquiryEntity {
    private String id;

    private String accountnumber;

    private String accounttype;

    private String depositnumber;

    private BigDecimal depositamount;

    private String termperiod;

    private BigDecimal terminterestrate;

    private Date maturitydate;

    private BigDecimal maturityinterest;

    private BigDecimal maturityamount;

    private String maturitystatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber == null ? null : accountnumber.trim();
    }

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype == null ? null : accounttype.trim();
    }

    public String getDepositnumber() {
        return depositnumber;
    }

    public void setDepositnumber(String depositnumber) {
        this.depositnumber = depositnumber == null ? null : depositnumber.trim();
    }

    public BigDecimal getDepositamount() {
        return depositamount;
    }

    public void setDepositamount(BigDecimal depositamount) {
        this.depositamount = depositamount;
    }

    public String getTermperiod() {
        return termperiod;
    }

    public void setTermperiod(String termperiod) {
        this.termperiod = termperiod == null ? null : termperiod.trim();
    }

    public BigDecimal getTerminterestrate() {
        return terminterestrate;
    }

    public void setTerminterestrate(BigDecimal terminterestrate) {
        this.terminterestrate = terminterestrate;
    }

    public Date getMaturitydate() {
        return maturitydate;
    }

    public void setMaturitydate(Date maturitydate) {
        this.maturitydate = maturitydate;
    }

    public BigDecimal getMaturityinterest() {
        return maturityinterest;
    }

    public void setMaturityinterest(BigDecimal maturityinterest) {
        this.maturityinterest = maturityinterest;
    }

    public BigDecimal getMaturityamount() {
        return maturityamount;
    }

    public void setMaturityamount(BigDecimal maturityamount) {
        this.maturityamount = maturityamount;
    }

    public String getMaturitystatus() {
        return maturitystatus;
    }

    public void setMaturitystatus(String maturitystatus) {
        this.maturitystatus = maturitystatus == null ? null : maturitystatus.trim();
    }
}