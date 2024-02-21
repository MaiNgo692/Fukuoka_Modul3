package com.ra.entity;

import com.ra.util.Column;
import com.ra.util.Id;
import com.ra.util.Table;

@Table(name = "computer")
public class Computer {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private float price;
    @Column(name = "status")
    private boolean status;

    public Computer() {
    }

    public Computer(String id, String name, float price, boolean status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void display(){
        System.out.printf("Mã máy: %s | Máy tính: %s | Giá: %f |  Trạng thái: %s\n",this.id,this.name,this.price,this.status? "ON":"OFF");
    }
}
