package com.ra.util;

import com.ra.entity.Category;
import com.ra.entity.Order;
import com.ra.entity.OrderDetail;
import com.ra.service.impl.CategoryServiceImpl;
import com.ra.service.impl.OrderDetailServiceImpl;
import com.ra.service.impl.OrderServiceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Application {
    public static void main(String[] args)  {
        CategoryServiceImpl service = new CategoryServiceImpl();
        OrderServiceImpl odService = new OrderServiceImpl();
        OrderDetailServiceImpl odDetailService = new OrderDetailServiceImpl();
//        List<Category> allCategory = service.findAll();
//        allCategory.forEach(category -> System.out.println(category.getId()+": " + category.getName()));
//        System.out.println("----------------------------------");
//        List<Category> findCategory = service.findByName("n");
//        findCategory.forEach(category -> System.out.println(category.getId()+": " + category.getName()));
//        if(service.delete(3)){
//            System.out.println("delete success!");
//            List<Category> allCategory = service.findAll();
//            allCategory.forEach(category -> System.out.println(category.getId()+": " + category.getName()));
//        }
//        Category category = new Category(3,"updated","Test,update","mo ta", 0);
//        service.update(category);
        System.out.println("add order and order detail");
        Connection connection = null;
        try {
            connection = MySQLConnect.open();
            connection.setAutoCommit(false);
            Order order = odService.insert( new Order(1,"2024-01-26 10:00:00",90.9,"Delivered"),connection);
            OrderDetail OrderDetail = new OrderDetail(order.getId(), 1, 2, 29.99);
            odDetailService.insert(OrderDetail,connection);
        } catch (Exception ex) {
            try{
                connection.rollback();
            }catch (Exception exception){
                exception.printStackTrace();
            }

        }
    }
}
