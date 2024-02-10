package com.ra.entity;

import java.sql.Date;

public class Order {
    private int id;
    private int customerId;
    private String OrderDate;
    private double totalAmount;
    private String status;

    public Order() {
    }

    public Order(int id, int customerId, String orderDate, double totalAmount, String status) {
        this.id = id;
        this.customerId = customerId;
        OrderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public Order(int customerId, String orderDate, double totalAmount, String status) {
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

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
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
}
