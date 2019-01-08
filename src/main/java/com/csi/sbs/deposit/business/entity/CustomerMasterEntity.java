package com.csi.sbs.deposit.business.entity;

import java.util.Date;

public class CustomerMasterEntity {
	
	
    private String id;

    private Short clearingcode;

    private Short branchnumber;

    private Integer customernumber;

    private String customername;

    private Long mobilephonenumber;

    private Long customerid;

    private String issuecountry;

    private Date dateofbirth;

    private String chinesename;

    private String gender;

    private String nationality;

    private String permanentresidencestatus;

    private String maritalstatus;

    private String education;

    private String residentialaddress;

    private String mailingaddress;

    private Long residencephonenumber;

    private String wechatid;

    private String accommodation;

    private Short yearsofresidence;

    private String occupation;

    private String employercompanyname;

    private String position;

    private String companyaddress;

    private Long companyphonenumber;

    private Short yearsofservices;

    private Integer monthlysalary;

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

    public Integer getCustomernumber() {
        return customernumber;
    }

    public void setCustomernumber(Integer customernumber) {
        this.customernumber = customernumber;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername == null ? null : customername.trim();
    }

    public Long getMobilephonenumber() {
        return mobilephonenumber;
    }

    public void setMobilephonenumber(Long mobilephonenumber) {
        this.mobilephonenumber = mobilephonenumber;
    }

    public Long getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Long customerid) {
        this.customerid = customerid;
    }

    public String getIssuecountry() {
        return issuecountry;
    }

    public void setIssuecountry(String issuecountry) {
        this.issuecountry = issuecountry == null ? null : issuecountry.trim();
    }

    public Date getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getChinesename() {
        return chinesename;
    }

    public void setChinesename(String chinesename) {
        this.chinesename = chinesename == null ? null : chinesename.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality == null ? null : nationality.trim();
    }

    public String getPermanentresidencestatus() {
        return permanentresidencestatus;
    }

    public void setPermanentresidencestatus(String permanentresidencestatus) {
        this.permanentresidencestatus = permanentresidencestatus == null ? null : permanentresidencestatus.trim();
    }

    public String getMaritalstatus() {
        return maritalstatus;
    }

    public void setMaritalstatus(String maritalstatus) {
        this.maritalstatus = maritalstatus == null ? null : maritalstatus.trim();
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education == null ? null : education.trim();
    }

    public String getResidentialaddress() {
        return residentialaddress;
    }

    public void setResidentialaddress(String residentialaddress) {
        this.residentialaddress = residentialaddress == null ? null : residentialaddress.trim();
    }

    public String getMailingaddress() {
        return mailingaddress;
    }

    public void setMailingaddress(String mailingaddress) {
        this.mailingaddress = mailingaddress == null ? null : mailingaddress.trim();
    }

    public Long getResidencephonenumber() {
        return residencephonenumber;
    }

    public void setResidencephonenumber(Long residencephonenumber) {
        this.residencephonenumber = residencephonenumber;
    }

    public String getWechatid() {
        return wechatid;
    }

    public void setWechatid(String wechatid) {
        this.wechatid = wechatid == null ? null : wechatid.trim();
    }

    public String getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(String accommodation) {
        this.accommodation = accommodation == null ? null : accommodation.trim();
    }

    public Short getYearsofresidence() {
        return yearsofresidence;
    }

    public void setYearsofresidence(Short yearsofresidence) {
        this.yearsofresidence = yearsofresidence;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation == null ? null : occupation.trim();
    }

    public String getEmployercompanyname() {
        return employercompanyname;
    }

    public void setEmployercompanyname(String employercompanyname) {
        this.employercompanyname = employercompanyname == null ? null : employercompanyname.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getCompanyaddress() {
        return companyaddress;
    }

    public void setCompanyaddress(String companyaddress) {
        this.companyaddress = companyaddress == null ? null : companyaddress.trim();
    }

    public Long getCompanyphonenumber() {
        return companyphonenumber;
    }

    public void setCompanyphonenumber(Long companyphonenumber) {
        this.companyphonenumber = companyphonenumber;
    }

    public Short getYearsofservices() {
        return yearsofservices;
    }

    public void setYearsofservices(Short yearsofservices) {
        this.yearsofservices = yearsofservices;
    }

    public Integer getMonthlysalary() {
        return monthlysalary;
    }

    public void setMonthlysalary(Integer monthlysalary) {
        this.monthlysalary = monthlysalary;
    }
}