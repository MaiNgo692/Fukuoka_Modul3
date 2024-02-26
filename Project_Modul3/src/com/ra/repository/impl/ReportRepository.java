package com.ra.repository.impl;

import com.ra.util.FontColor;
import com.ra.util.MysqlConnect;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ReportRepository {
    public Float costStatisticByDate(Date date, boolean billType){
        Connection conn=null;
        Float costs=0F;
        try{
            conn= MysqlConnect.open();
            CallableStatement cs = conn.prepareCall("{call Cost_statistic_by_date(?,?,?)}");
            cs.registerOutParameter(1, Types.FLOAT);
            cs.setString(2, String.valueOf(date));
            cs.setBoolean(3,billType);
            ResultSet rs = cs.executeQuery();
            while (rs.next()){
                costs= rs.getFloat(1);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println(FontColor.err(ex.getMessage()));
        }finally {
            MysqlConnect.close(conn);
        }
        return costs;
    }
    public Float costStatisticOverPeriod(Date fromDate, Date toDate,boolean billType){
        Connection conn=null;
        Float costs=0F;
        try{
            conn= MysqlConnect.open();
            CallableStatement cs = conn.prepareCall("{call Cost_statistic_over_period(?,?,?,?)}");
            cs.registerOutParameter(1, Types.FLOAT);
            cs.setString(2, String.valueOf(fromDate));
            cs.setString(3, String.valueOf(toDate));
            cs.setBoolean(4,billType);
            ResultSet rs = cs.executeQuery();
            while (rs.next()){
                costs= rs.getFloat(1);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println(FontColor.err(ex.getMessage()));
        }finally {
            MysqlConnect.close(conn);
        }
        return costs;
    }

    public Integer empStatisticByStatus(int empStatus){
        Connection conn=null;
        int count =0;
        try{
            conn= MysqlConnect.open();
            CallableStatement cs = conn.prepareCall("{call Emp_statistic_by_status(?,?)}");
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setInt(2, empStatus);
            ResultSet rs = cs.executeQuery();
            while (rs.next()){
                count= rs.getInt(1);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println(FontColor.err(ex.getMessage()));
        }finally {
            MysqlConnect.close(conn);
        }
        return count;
    }
    public Map<String,Integer> mostProductQuantityOverPeriod(Date fromDate, Date toDate, boolean billType){
        Connection conn=null;
        Map<String,Integer> map = new HashMap<>();
        try{
            conn= MysqlConnect.open();
            CallableStatement cs = conn.prepareCall("{call Most_product_quantity_over_period(?,?,?,?,?)}");
            cs.registerOutParameter(1, Types.VARCHAR);
            cs.registerOutParameter(2, Types.INTEGER);
            cs.setString(3, String.valueOf(fromDate));
            cs.setString(4, String.valueOf(toDate));
            cs.setBoolean(5,billType);
            ResultSet rs = cs.executeQuery();
            while (rs.next()){
                map.put(rs.getString(1),rs.getInt(2));
            }
        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println(FontColor.err(ex.getMessage()));
        }finally {
            MysqlConnect.close(conn);
        }
        return map;
    }
    public Map<String,Integer> leastProductQuantityOverPeriod(Date fromDate, Date toDate, boolean billType){
        Connection conn=null;
        Map<String,Integer> map = new HashMap<>();
        try{
            conn= MysqlConnect.open();
            CallableStatement cs = conn.prepareCall("{call Least_product_quantity_over_period(?,?,?,?,?)}");
            cs.registerOutParameter(1, Types.VARCHAR);
            cs.registerOutParameter(2, Types.INTEGER);
            cs.setString(3, String.valueOf(fromDate));
            cs.setString(4, String.valueOf(toDate));
            cs.setBoolean(5,billType);
            ResultSet rs = cs.executeQuery();
            while (rs.next()){
                map.put(rs.getString(1),rs.getInt(2));
            }
        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println(FontColor.err(ex.getMessage()));
        }finally {
            MysqlConnect.close(conn);
        }
        return map;
    }
}
