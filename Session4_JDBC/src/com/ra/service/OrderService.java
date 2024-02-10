package com.ra.service;

import com.ra.entity.Category;
import com.ra.entity.Order;

import java.sql.Connection;
import java.util.List;

public interface OrderService {
    public List<Order> findAll(Connection connection);
    public List<Order> findById(int id,Connection connection);
    Order insert(Order order,Connection connection)throws Exception;
    boolean delete(double id,Connection connection);
    Order update(Order order,Connection connection);
}
