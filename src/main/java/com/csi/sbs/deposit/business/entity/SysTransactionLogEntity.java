package com.csi.sbs.deposit.business.entity;

import java.util.Date;

public class SysTransactionLogEntity {
    
	
	private String id;

    private String operationtype;

    private String sourceservices;

    private String operationstate;

    private Date operationdate;

    private String operationdetail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOperationtype() {
        return operationtype;
    }

    public void setOperationtype(String operationtype) {
        this.operationtype = operationtype == null ? null : operationtype.trim();
    }

    public String getSourceservices() {
        return sourceservices;
    }

    public void setSourceservices(String sourceservices) {
        this.sourceservices = sourceservices == null ? null : sourceservices.trim();
    }

    public String getOperationstate() {
        return operationstate;
    }

    public void setOperationstate(String operationstate) {
        this.operationstate = operationstate == null ? null : operationstate.trim();
    }

    public Date getOperationdate() {
        return operationdate;
    }

    public void setOperationdate(Date operationdate) {
        this.operationdate = operationdate;
    }

    public String getOperationdetail() {
        return operationdetail;
    }

    public void setOperationdetail(String operationdetail) {
        this.operationdetail = operationdetail == null ? null : operationdetail.trim();
    }
}