package com.ra.repository.impl;

import com.ra.entity.Category;
import com.ra.entity.Order;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

public class OrderRepository extends Repository<Order, Integer>{
    public OrderRepository() {
        this.clazz = Order.class;
        this.sp_findAll = "{call sp_order_findAll()}";
        this.sp_findId = "{call sp_order_findId(?)}";
        this.sp_insert = "{call sp_order_add(?,?,?,?,?)}";
        this.sp_update = "{call sp_order_update(?,?,?,?,?)}";
        this.sp_delete = "{call sp_order_delete(?)}";
    }

    @Override
    public void setInsertParam(CallableStatement cs, Order entity) throws SQLException {
        cs.registerOutParameter(1, Types.INTEGER);
        cs.setInt(2, entity.getCustomerId());
        cs.setDate(3, entity.getOrderDate());
        cs.setDouble(4, entity.getTotalAmount());
        cs.setString(5, entity.getStatus());
    }

    @Override
    public void setUpdateParam(CallableStatement cs, Order entity) throws SQLException {
        cs.setInt(1, entity.getId());
        cs.setInt(2, entity.getCustomerId());
        cs.setDate(3, entity.getOrderDate());
        cs.setDouble(4, entity.getTotalAmount());
        cs.setString(5, entity.getStatus());
    }

    @Override
    public void handleInsert(CallableStatement cs, Order entity) throws SQLException {
        entity.setId(cs.getInt(1));
    }

    @Override
    public void handleUpdate(CallableStatement cs, Order entity) throws SQLException {

    }
}
