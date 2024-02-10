package com.ra.service.impl;

import com.ra.entity.OrderDetail;
import com.ra.service.OrderDetailService;
import com.ra.util.MySQLConnect;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.List;

public class OrderDetailServiceImpl implements OrderDetailService {

    @Override
    public List<OrderDetail> findAll(Connection connection) {
        return null;
    }

    @Override
    public List<OrderDetail> findById(int id, Connection connection) {
        return null;
    }

    @Override
    public OrderDetail insert(OrderDetail orderDetail, Connection connection)throws Exception {
//            connection = MySQLConnect.open();
//            connection.setAutoCommit(false);
            String query ="{call insert_order_detail(?, ? ,? ,?, ?)}";
            CallableStatement cs = connection.prepareCall(query);
            // truyen tham so
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setInt(2,orderDetail.getOrderId());
            cs.setInt(3,orderDetail.getProductId());
            cs.setInt(4,orderDetail.getQuantity());
            cs.setDouble(5,orderDetail.getPrice());

            int result = cs.executeUpdate();
            if(result > 0){
                orderDetail.setId(cs.getInt(1));
            }
            return orderDetail;
    }

    @Override
    public boolean delete(double id, Connection connection) {
        return false;
    }

    @Override
    public OrderDetail update(OrderDetail orderDetail, Connection connection) {
        return null;
    }
}
