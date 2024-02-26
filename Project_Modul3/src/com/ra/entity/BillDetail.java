package com.ra.entity;

import com.ra.util.*;

import java.text.NumberFormat;
import java.util.Locale;

@Table(name = "bill_details")
public class BillDetail {
    @Id
    @Increment
    @Column(name = "Bill_Detail_Id")
    Long billDetailId;
    @Column(name = "Bill_Id")
    Long billId;
    @Column(name = "Product_Id")
    String productId;
    @Column(name = "Quantity")
    Integer quantity;
    @Column(name = "Price")
    Float price;

    public BillDetail() {
    }

    public BillDetail(Long billId, String productId, Integer quantity, Float price) {
        this.billId = billId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getBillDetailId() {
        return billDetailId;
    }

    public void setBillDetailId(Long billDetailId) {
        this.billDetailId = billDetailId;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
    public void displayData(){
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat vn = NumberFormat.getInstance(localeVN);
        System.out.printf("|%s|%s|%s|%s|%s|\n", FontColor.centerString(15, String.valueOf(this.billDetailId)), FontColor.centerString(15, String.valueOf(this.billId)),
                FontColor.centerString(20,this.productId),FontColor.centerString(20, String.valueOf(this.quantity)) ,FontColor.centerString(20,vn.format(this.price)));
    }
}
