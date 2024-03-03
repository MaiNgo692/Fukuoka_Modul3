package com.ra.entity;

import com.ra.util.Column;
import com.ra.util.FontColor;
import com.ra.util.Id;
import com.ra.util.Table;

import java.text.SimpleDateFormat;
import java.util.Date;

@Table(name = "products")
public class Product {
    @Id
    @Column(name = "Product_Id")
    String productId;
    @Column(name = "Product_Name")
    String productName;
    @Column(name = "Manufacturer")
    String manufacturer;
    @Column(name = "Created")
    Date created;
    @Column(name = "Batch")
    Integer batch;
    @Column(name = "Quantity")
    Integer quantity=0;
    @Column(name = "Product_Status")
    boolean productStatus=true;

    public Product() {
    }

    public Product(String productId, String productName, String manufacturer, Date created, Integer batch, boolean productStatus) {
        this.productId = productId;
        this.productName = productName;
        this.manufacturer = manufacturer;
        this.created = created;
        this.batch = batch;
        this.productStatus = productStatus;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getBatch() {
        return batch;
    }

    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public boolean isProductStatus() {
        return productStatus;
    }

    public void setProductStatus(boolean productStatus) {
        this.productStatus = productStatus;
    }
    public void displayData(){
        SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.printf("|%s|%s|%s|%s|%s|%s|%s|\n", FontColor.centerString(12,this.productId), FontColor.centerString(30,this.productName),
                FontColor.centerString(30,this.manufacturer),FontColor.centerString(30, dmyFormat.format(this.created)) ,FontColor.centerString(10, String.valueOf(this.batch)),
                FontColor.centerString(10, String.valueOf(this.quantity)),FontColor.centerString(17,this.productStatus?"Hoạt động":"Không hoạt động"));
    }
}
