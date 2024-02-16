package com.ra.repository.impl;

import com.ra.entity.Order;
import com.ra.entity.OrderDetail;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

public class OrderDetailRepository extends Repository<OrderDetail, Integer>{
    public OrderDetailRepository() {
        this.clazz = OrderDetail.class;
        this.sp_findAll = "{call sp_orderDetail_findAll()}";
        this.sp_findId = "{call sp_orderDetail_findId(?)}";
        this.sp_insert = "{call sp_orderDetail_add(?,?,?,?,?)}";
        this.sp_update = "{call sp_orderDetail_update(?,?,?,?,?)}";
        this.sp_delete = "{call sp_orderDetail_delete(?)}";
    }
    @Override
    public void setInsertParam(CallableStatement cs, OrderDetail entity) throws SQLException {
        cs.registerOutParameter(1, Types.INTEGER);
        cs.setInt(2, entity.getOrderId());
        cs.setInt(3, entity.getProductId());
        cs.setInt(4, entity.getQuantity());
        cs.setDouble(5, entity.getPrice());
    }

    @Override
    public void setUpdateParam(CallableStatement cs, OrderDetail entity) throws SQLException {
        cs.setInt(1, entity.getId());
        cs.setInt(2, entity.getOrderId());
        cs.setInt(3, entity.getProductId());
        cs.setInt(4, entity.getQuantity());
        cs.setDouble(5, entity.getPrice());
    }

    @Override
    public void handleInsert(CallableStatement cs, OrderDetail entity) throws SQLException {
        entity.setId(cs.getInt(1));
    }

    @Override
    public void handleUpdate(CallableStatement cs, OrderDetail entity) throws SQLException {

    }
}
