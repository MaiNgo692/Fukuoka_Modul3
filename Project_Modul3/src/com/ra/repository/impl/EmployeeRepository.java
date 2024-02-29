package com.ra.repository.impl;

import com.ra.entity.Account;
import com.ra.entity.Employee;
import com.ra.util.FontColor;
import com.ra.util.MysqlConnect;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository extends Repository<Employee,String>{
   public List<Employee> findByIdOrName(String key){
       List<Employee> result = new ArrayList<>();
       Connection conn = null;
        PreparedStatement pst;
        ResultSet rs;
        try{
            conn = MysqlConnect.open();
            Field[]fields = Employee.class.getDeclaredFields();
            String sql = "SELECT * FROM employees WHERE Emp_id LIKE ? OR Emp_Name LIKE ?";
//            System.out.println(sql);
            pst =conn.prepareStatement(sql);
            pst.setObject(1,"%"+key+"%");
            pst.setObject(2,"%"+key+"%");
            rs = pst.executeQuery();
            while (rs.next()){
                result.add(setField(Employee.class,rs,fields)) ;
            }
        }catch (Exception ex){
            System.out.println(FontColor.err("Tìm không thành công!"));
            ex.printStackTrace();
        }finally {
            MysqlConnect.close(conn);
        }
        return result;
    }
}
