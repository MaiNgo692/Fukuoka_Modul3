package com.ra.repository.impl;

import com.ra.entity.Account;
import com.ra.util.FontColor;
import com.ra.util.MysqlConnect;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository extends Repository<Account,Integer> {
        public List<Account> findByUserNameOrName(String key){
            List<Account> result = new ArrayList<>();
            Connection conn = null;
            PreparedStatement pst;
            ResultSet rs;
            try{
                conn = MysqlConnect.open();
                String sql = "SELECT c.* FROM accounts c INNER JOIN employees e ON c.Emp_id = e.Emp_Id WHERE c.User_Name like ? OR e.Emp_Name like ?";
                pst = conn.prepareStatement(sql);
                pst.setObject(1,"%"+key+"%");
                pst.setObject(2,"%"+key+"%");
                rs = pst.executeQuery();
                Field[] fields = Account.class.getDeclaredFields();
//            Arrays.stream(fields).filter(f -> f.getAnnotation(Increment.class)==null);
                while (rs.next()){
                    result.add(setField(Account.class,rs,fields));
                }
            }catch (Exception ex){
                System.out.println(FontColor.err("Tìm không thành công!"));
                ex.printStackTrace();
            }finally {
                MysqlConnect.close(conn);
            }
            return  result;
        }
        public Account findByEmpId(String empId){
            Connection conn = null;
            PreparedStatement pst;
            ResultSet rs;
            try{
                conn = MysqlConnect.open();
                Field[]fields = Account.class.getDeclaredFields();
                String sql = "SELECT * FROM accounts WHERE Emp_id = ? ";
                pst =conn.prepareStatement(sql);
                pst.setObject(1,empId);
                rs = pst.executeQuery();
                while (rs.next()){
                    return setField(Account.class,rs,fields);
                }
            }catch (Exception ex){
                System.out.println(FontColor.err("Tìm không thành công!"));
                ex.printStackTrace();
            }finally {
                MysqlConnect.close(conn);
            }
            return null;
        }
}
