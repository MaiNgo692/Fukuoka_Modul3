import util.MySqlConnect;

import java.sql.*;
import java.text.MessageFormat;

public class Main {
//    public static void main(String[] args) {
//        Connection conn = null;
//        PreparedStatement pst = null;
//        ResultSet rs = null;
//        try{
//            conn = MySqlConnect.open();
//            System.out.println("tạo schema ");
//            String sql = "CREATE SCHEMA 'test_fukuoka'";
//            pst = conn.prepareStatement(sql);
//            rs = pst.executeQuery();
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            MySqlConnect.close(conn);
//        }

        static final String URL = "jdbc:mysql://localhost:3306/ra_fukuoka";
        static final String USERNAME = "root1";
        static final String PASSWORD = "Root123456";

        public static void main(String[] args) {
            String createTableSQL =
                    "create table users(id int primary key, name varchar(30)) ";
            try (
                    Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                    Statement statement = connection.createStatement();
            ) {
                statement.execute(createTableSQL);
                System.out.println("Create Successfully!");
            } catch (SQLException  e) {
                e.printStackTrace();
            }
        }
//    }
}
