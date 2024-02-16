package com.ra.entity;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Order implements IEntity {
    private int id;
    private int customerId;
    private Date OrderDate;
    private double totalAmount;
    private String status;

    public Order() {
    }

    public Order(int id, int customerId, Date orderDate, double totalAmount, String status) {
        this.id = id;
        this.customerId = customerId;
        OrderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public Order(int customerId, Date orderDate, double totalAmount, String status) {
        this.customerId = customerId;
        OrderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Date getOrderDate() {
        return OrderDate;
    }


    public void setOrderDate(Date orderDate) {
        OrderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void init(ResultSet rs) throws SQLException {

    }
}
