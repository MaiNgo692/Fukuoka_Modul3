package com.ra.repository.impl;

import com.ra.entity.Bill;
import com.ra.util.*;
import java.lang.reflect.Field;
import java.sql.*;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.stream.Collectors;

public class BillRepository extends Repository<Bill,Long> {
    public Bill findByIdOrBillCode(Long id,String billCode, boolean billType){
        Connection conn = null;
        PreparedStatement pst;
        ResultSet rs;
        try{
            conn = MysqlConnect.open();
            Field[]fields =Bill.class.getDeclaredFields();
            String sql = "SELECT * FROM bills WHERE (Bill_Id = ? OR Bill_Code = ? )AND Bill_Type = ?";
            pst =conn.prepareStatement(sql);
            pst.setObject(1,id);
            pst.setObject(2,billCode);
            pst.setObject(3,billType);
            rs = pst.executeQuery();
            while (rs.next()){
                return setField(Bill.class,rs,fields);
            }
        }catch (Exception ex){
            System.out.println(FontColor.err("Tìm không thành công!"));
            ex.printStackTrace();
        }finally {
            MysqlConnect.close(conn);
        }
        return null;
    }
    @Override
    public Bill add(Bill entity) {
        Connection conn = null;
        PreparedStatement pst;
        try{
            conn = MysqlConnect.open();
            conn.setAutoCommit(false);
            Field[] fields = entity.getClass().getDeclaredFields();
            String tableName = entity.getClass().getAnnotation(Table.class).name();
            String columns = Arrays.stream(fields).map(f -> f.getAnnotation(Column.class).name()).collect(Collectors.joining(","));
            String values = Arrays.stream(fields).map(f -> "?").collect(Collectors.joining(","));
            String sql = MessageFormat.format("INSERT INTO {0}({1}) VALUES ({2})",tableName,columns,values);
//            System.out.println(sql);
            pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int index =1;
            for(Field f: fields){
                f.setAccessible(true);
                pst.setObject(index++ ,f.get(entity));
            }
            int result = pst.executeUpdate();
            if(result >0){
                conn.commit();
                ResultSet rs = pst.getGeneratedKeys();
                Long id=0L;
                if(rs.next()){
                    id = rs.getLong(1);
                }
                entity.setBillId(id);
                return entity;
            }
        }catch (Exception ex){
            try {
                conn.rollback();
            } catch (SQLException e) {
                System.out.println(FontColor.err(e.getMessage()));
            }
            System.out.println(FontColor.err("Thêm không thành công!"));
            System.out.println(FontColor.err(ex.getMessage()));
        }finally {
            MysqlConnect.close(conn);
        }
        return null;
    }
}
