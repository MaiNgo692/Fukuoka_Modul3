package com.ra.entity;

import com.ra.util.Column;
import com.ra.util.Id;
import com.ra.util.Table;

import java.time.format.DateTimeFormatter;
import java.util.Date;

@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "computerId")
    private String computerId;
    @Column(name = "startTime")
    private Date startTime;
    @Column(name = "pricePerHours")
    private float pricePerHours;
    @Column(name = "created")
    private Date created;
    @Column(name = "status")
    private boolean status;

    public Order() {
    }

    public Order(String id, String computerId, Date startTime, float pricePerHours, Date created, boolean status) {
        this.id = id;
        this.computerId = computerId;
        this.startTime = startTime;
        this.pricePerHours = pricePerHours;
        this.created = created;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComputerId() {
        return computerId;
    }

    public void setComputerId(String computerId) {
        this.computerId = computerId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public float getPricePerHours() {
        return pricePerHours;
    }

    public void setPricePerHours(float pricePerHours) {
        this.pricePerHours = pricePerHours;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
