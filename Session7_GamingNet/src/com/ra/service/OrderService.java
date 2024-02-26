package com.ra.service;

import com.ra.entity.Computer;
import com.ra.entity.Order;
import com.ra.entity.OrderDetail;
import com.ra.entity.Service;
import com.ra.repository.IRepository;
import com.ra.repository.impl.Repository;
import com.ra.util.Column;
import com.ra.util.Id;
import com.ra.util.MySqlConnect;
import com.ra.util.Table;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.*;

public class OrderService extends Repository<Order,String> {
    IRepository<Service,String> serviceRepository = new Repository<>();
    public Order findByComputerId(String computerId){
        Connection conn = null;
        PreparedStatement pst;
        ResultSet rs;
        try{
            conn = MySqlConnect.open();
            Field[]fields = Order.class.getDeclaredFields();
            String sql = MessageFormat.format("SELECT * FROM {0} WHERE {1} = ?",Order.class.getAnnotation(Table.class).name(),"computerId");
            System.out.println(sql);
            pst = conn.prepareStatement(sql);
            pst.setObject(1,computerId);
            rs = pst.executeQuery();
            while (rs.next()) {
                Order entity = new Order();
                entity.setStartTime(rs.getDate("startTime"));
                entity.setId(rs.getString("id"));
                entity.setCreated(rs.getDate("created"));
                entity.setComputerId(rs.getString("computerId"));
                entity.setPricePerHours(rs.getFloat("pricePerHours"));
                entity.setStatus(rs.getBoolean("status"));
                return entity ;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            MySqlConnect.close(conn);
        }
        return null;
    }
    public void addNewOrderAfterOnComputer(Computer computer){
        Order newOrder = new Order();
        newOrder.setId(UUID.randomUUID().toString());
        newOrder.setComputerId(computer.getId());
//        newOrder.setStartTime(new Date());
//        newOrder.setCreated(new Date());
        newOrder.setPricePerHours(computer.getPrice());
        newOrder.setStatus(false);
        add(newOrder);
    }
    public float printBillAfterOffComputer(String computerId){
        Order order = findByComputerId(computerId);
        long useTime = (new Date().getTime()- order.getStartTime().getTime())/(1000*60*60);
        float bill = useTime == 0 ? order.getPricePerHours(): useTime * order.getPricePerHours();
        return bill;
    }
    public List<Service> displayAllService(){
        System.out.println("Danh sách dịch vụ trong tiệm:");
        List<Service> services = serviceRepository.findAll(Service.class);
        services.forEach(c -> c.display());
        return services;
    }
    public OrderDetail selectService(Scanner sc, Computer computer){
        List<Service> services = displayAllService();
        Order order = findByComputerId(computer.getId());
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(UUID.randomUUID().toString());
        orderDetail.setOrderId(order.getId());
        boolean isExit;
        if(!services.isEmpty()){
            do{
                isExit = true;
                System.out.println("Nhập mã dịch vụ:");
                String id = sc.nextLine();
                for (Service sv: services) {
                    if(sv.getId().equals(id)) {
                        orderDetail.setServiceId(sv.getId());
                        orderDetail.setPrice(sv.getPrice());
                    }
                }
                System.err.println("Vui lòng nhập lại mã dịch trong danh sách!");
            }while (isExit);
            do{
                isExit = true;
                System.out.println("Nhập số lượng dịch vụ:");
                try{
                    int quantity = Integer.parseInt(sc.nextLine());
                    orderDetail.setQuantity(quantity);
                }catch (NumberFormatException ex){
                    System.err.println("Vui lòng nhập số!");
                }
            }while (isExit);
        }else {
            System.err.println("Không có dịch vụ nào!");
        }
        return orderDetail;
    }
}
