package com.ra.entity;

import com.ra.util.*;

@Table(name = "accounts")
public class Account {
    @Id
    @Increment
    @Column(name = "Acc_Id")
    Integer accId;
    @Column(name = "User_Name")
    String userName;
    @Column(name = "Password")
    String password;
    @Column(name = "Permission")
    boolean permission=true;
    @Column(name = "Emp_id")
    String empId;
    @Column(name = "Acc_Status")
    boolean accStatus=true;

    public Account() {
    }

    public Account(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public Account(String userName, String password, boolean permission, String empId, boolean accStatus) {
        this.userName = userName;
        this.password = password;
        this.permission = permission;
        this.empId = empId;
        this.accStatus = accStatus;
    }

    public Integer getAccId() {
        return accId;
    }

    public void setAccId(Integer accId) {
        this.accId = accId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public boolean isAccStatus() {
        return accStatus;
    }

    public void setAccStatus(boolean accStatus) {
        this.accStatus = accStatus;
    }
    public void displayData(){
        System.out.printf("|%s|%s|%s|%s|%s|%s|\n", FontColor.centerString(12, String.valueOf(this.accId)), FontColor.centerString(30,this.userName),
                FontColor.centerString(30,this.password),FontColor.centerString(20,this.permission?"User":"Admin") ,FontColor.centerString(20,this.empId),
                FontColor.centerString(17,this.accStatus?"Active":"Block"));
    }
}
