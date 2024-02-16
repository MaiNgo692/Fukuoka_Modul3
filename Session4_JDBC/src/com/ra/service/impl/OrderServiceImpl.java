package com.ra.service.impl;

import com.ra.entity.Order;
import com.ra.service.OrderService;
import com.ra.util.MySQLConnect;

import java.sql.*;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    @Override
    public List<Order> findAll(Connection connection) {
        return null;
    }

    @Override
    public List<Order> findById(int id, Connection connection) {
        return null;
    }

    @Override
    public Order insert(Order order, Connection connection)throws Exception {
//            connection = MySQLConnect.open();
//            connection.setAutoCommit(false);
            String query ="{call insert_order(?, ? ,? ,?, ?)}";
            CallableStatement cs = connection.prepareCall(query);
            // truyen tham so
            cs.registerOutParameter(1,Types.INTEGER);
            cs.setInt(2,order.getCustomerId());
            cs.setDate(3,order.getOrderDate());
            cs.setDouble(4,order.getTotalAmount());
            cs.setString(5,order.getStatus());
            int result = cs.executeUpdate();
            if(result > 0){
                order.setId(cs.getInt(1));
                return order;
            }
            return null;
    }

    @Override
    public boolean delete(double id, Connection connection) {
        return false;
    }

    @Override
    public Order update(Order order, Connection connection) {
        return null;
    }
}
