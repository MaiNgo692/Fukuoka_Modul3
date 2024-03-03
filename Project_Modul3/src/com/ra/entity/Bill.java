package com.ra.entity;

import com.ra.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Table(name = "bills")
public class Bill {
    @Id
    @Increment
    @Column(name = "Bill_Id")
    Long billId;
    @Column(name = "Bill_Code")
    String billCode;
    @Column(name = "Bill_Type")
    boolean billType;
    @Column(name = "Emp_Id_Created")
    String empIdCreated;
    @Column(name = "Created")
    Date created=new Date();
    @Column(name = "Emp_Id_Auth")
    String empIdAuth;
    @Column(name = "Auth_Date")
    Date authDate= new Date();
    @Column(name = "Bill_Status")
    Integer billStatus;

    public Bill() {
    }

    public Bill(String billCode, boolean billType, String empIdCreated, Date created, String empIdAuth, Date authDate, Integer billStatus) {
        this.billCode = billCode;
        this.billType = billType;
        this.empIdCreated = empIdCreated;
        this.created = created;
        this.empIdAuth = empIdAuth;
        this.authDate = authDate;
        this.billStatus = billStatus;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public boolean isBillType() {
        return billType;
    }

    public void setBillType(boolean billType) {
        this.billType = billType;
    }

    public String getEmpIdCreated() {
        return empIdCreated;
    }

    public void setEmpIdCreated(String empIdCreated) {
        this.empIdCreated = empIdCreated;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {

        this.created = created;
    }

    public String getEmpIdAuth() {
        return empIdAuth;
    }

    public void setEmpIdAuth(String empIdAuth) {
        this.empIdAuth = empIdAuth;
    }

    public Date getAuthDate() {
        return authDate;
    }

    public void setAuthDate(Date authDate) {
        this.authDate = authDate;
    }

    public Integer getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(Integer billStatus) {
        this.billStatus = billStatus;
    }
    public void displayData(){
        SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.printf("|%s|%s|%s|%s|%s|%s|%s|%s|\n", FontColor.centerString(15, String.valueOf(this.billId)), FontColor.centerString(15,this.billCode),
                FontColor.centerString(20,this.billType?"Phiếu nhập":"Phiếu xuất"),FontColor.centerString(20,this.empIdCreated) ,FontColor.centerString(20, dmyFormat.format(this.created)),
                FontColor.centerString(20,this.empIdAuth),FontColor.centerString(20, dmyFormat.format(this.authDate)),
                FontColor.centerString(17,this.billStatus == 0 ?"Tạo":this.billStatus == 1?"Hủy":"Duyệt"));
    }

}
