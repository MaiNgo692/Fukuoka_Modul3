package com.ra.service;

import com.ra.entity.Order;
import com.ra.entity.OrderDetail;

import java.sql.Connection;
import java.util.List;

public interface OrderDetailService {
    public List<OrderDetail> findAll(Connection connection);
    public List<OrderDetail> findById(int id,Connection connection);
    OrderDetail insert(OrderDetail orderDetail,Connection connection)throws Exception;
    boolean delete(double id,Connection connection);
    OrderDetail update(OrderDetail orderDetail,Connection connection);

}
