package com.ra.entity;

import com.ra.util.Column;
import com.ra.util.Id;
import com.ra.util.Table;

@Table(name = "OrderDetails")
public class OrderDetail {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "orderId")
    private String orderId;
    @Column(name = "serviceId")
    private String serviceId;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "price")
    private float price;

    public OrderDetail() {
    }

    public OrderDetail(String id, String orderId, String serviceId, int quantity, float price) {
        this.id = id;
        this.orderId = orderId;
        this.serviceId = serviceId;
        this.quantity = quantity;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
