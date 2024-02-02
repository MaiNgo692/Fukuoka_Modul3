package com.ra.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLConnect {
    public static Connection open(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/session3_view_procedure";
            String user ="root1";
            String password ="Root123456";
            Connection connn = DriverManager.getConnection(url,user,password);
            return  connn;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    public static void close(Connection conn){
        try{
            if(conn != null)
                conn.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


}
