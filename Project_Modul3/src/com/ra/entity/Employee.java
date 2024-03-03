package com.ra.entity;

import com.ra.util.Column;
import com.ra.util.FontColor;
import com.ra.util.Id;
import com.ra.util.Table;
import java.util.Date;

@Table(name = "employees")
public class Employee {
    @Id
    @Column(name = "Emp_Id")
    String empId;
    @Column(name = "Emp_Name")
    String empName;
    @Column(name = "Birth_Of_Date")
    Date birthOfDate;
    @Column(name = "Email")
    String email;
    @Column(name = "Phone")
    String phone;
    @Column(name = "Address")
    String address;
    @Column(name = "Emp_Status")
    Integer empStatus;

    public Employee() {
    }

    public Employee(String empId, String empName, Date birthOfDate, String email, String phone, String address, Integer empStatus) {
        this.empId = empId;
        this.empName = empName;
        this.birthOfDate = birthOfDate;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.empStatus = empStatus;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Date getBirthOfDate() {
        return birthOfDate;
    }

    public void setBirthOfDate(Date birthOfDate) {
        this.birthOfDate = birthOfDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getEmpStatus() {
        return empStatus;
    }

    public void setEmpStatus(Integer empStatus) {
        this.empStatus = empStatus;
    }
    public void displayData(){
        System.out.printf("|%s|%s|%s|%s|%s|%s|%s|\n", FontColor.centerString(15,this.empId), FontColor.centerString(30,this.empName),
                FontColor.centerString(20, String.valueOf(this.birthOfDate)),FontColor.centerString(20,this.email) ,
                FontColor.centerString(20,this.phone),FontColor.centerString(30,this.address),
                FontColor.centerString(17,this.empStatus==0?"Hoạt động":this.empStatus==1?"Nghỉ chế độ":"Nghỉ việc"));
    }
}
