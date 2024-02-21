package com.ra.service;

import com.ra.entity.Order;
import com.ra.entity.OrderDetail;
import com.ra.repository.impl.Repository;
import com.ra.util.Column;
import com.ra.util.MySqlConnect;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderDetailService extends Repository<OrderDetail,String> {
    List<OrderDetail> getAllOrderDetailByOrderId(String orderId){
        Connection conn = null;
        PreparedStatement pst;
        ResultSet rs;
        List<OrderDetail> result = new ArrayList<>();
        try{
            conn = MySqlConnect.open();
            Field[]fields = Order.class.getDeclaredFields();
            String sql = "SELECT * FROM orderdetails WHERE orderId = ?";
            pst = conn.prepareStatement(sql);
            pst.setObject(1,orderId);
            rs = pst.executeQuery();
            while (rs.next()) {
                OrderDetail entity = OrderDetail.class.getDeclaredConstructor().newInstance();
                for (Field f : fields) {
                    f.setAccessible(true);
                    f.set(entity, rs.getObject(f.getAnnotation(Column.class).name()));
                }
                result.add(entity);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            MySqlConnect.close(conn);
        }
        return result;
    }
    public float billAllOrderDetail(String orderId){
        List<OrderDetail> orderDetails = getAllOrderDetailByOrderId(orderId);
        float result = (float) orderDetails.stream().mapToDouble(o -> o.getQuantity()*o.getPrice()).sum();
        return result;
    }
}
