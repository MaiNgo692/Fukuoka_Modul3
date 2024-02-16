package com.ra.service.impl;

import com.ra.entity.Category;
import com.ra.service.CategoryService;
import com.ra.util.MySQLConnect;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    @Override
    public List<Category> findAll() {
        List<Category> result = new ArrayList<>();
        Connection conn = null;
        try{
            conn = MySQLConnect.open();
            String query ="SELECT * FROM categories";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Category category = new Category();
                category.setId(rs.getInt("CategoryID"));
                category.setName(rs.getString("CategoryName"));
                category.setDescription(rs.getString("Description"));
                category.setStatus(rs.getInt("Status"));

                result.add(category);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            MySQLConnect.close(conn);
        }
        return result;
    }

    @Override
    public List<Category> findByName(String name) {
        List<Category> result = new ArrayList<>();
        Connection conn = null;
        try{
            conn = MySQLConnect.open();
            String query ="SELECT * FROM categories c WHERE  c.CategoryName Like ?" ;
            PreparedStatement ps = conn.prepareStatement(query);
            // truyen tham so
            ps.setString(1,"%"+name+"%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Category category = new Category();
                category.setId(rs.getInt("CategoryID"));
                category.setName(rs.getString("CategoryName"));
                category.setDescription(rs.getString("Description"));
                category.setStatus(rs.getInt("Status"));
                result.add(category);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            MySQLConnect.close(conn);
        }
        return result;
    }

    @Override
    public Category findId(int id) {
        Connection conn = null;
        try {
            // B1. Tạo kết nối
            conn = MySQLConnect.open();
            // B2. Tạo đối tượng thực thi câu truy vấn
            String query = "{call sp_categories_findId(?)}";
            CallableStatement cs = conn.prepareCall(query);
            // B2.1: Truyền tham số nếu có
            cs.setInt(1, id);
            // B3. Thực thi câu truy vấn
            ResultSet rs = cs.executeQuery();
            // B4. Xử lý ResultSet
            while (rs.next()) {
                Category c = new Category();
                return c;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            MySQLConnect.close(conn);
        }
        return null;
    }

    @Override
    public Category insert(Category category) {
        Connection conn = null;
        try{
            conn = MySQLConnect.open();
//            String query ="INSERT INTO Categories (CategoryName, Description, Status)VALUES (?,?,?)";
            String query ="{call sp_categories_add(?,?,?,?)}";
//            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            CallableStatement cs = conn.prepareCall(query);
            // truyen tham so
            cs.registerOutParameter(1,Types.INTEGER);
            cs.setString(2,category.getName());
            cs.setString(3,category.getDescription());
            cs.setInt(4,category.getStatus());

            int result = cs.executeUpdate();
            if(result > 0){
//                try(ResultSet generateKeys = cs.getGeneratedKeys() ){
//                    while (generateKeys.next()){
//                        category.setId(generateKeys.getInt(1));
//                    }
//                }
                category.setId(cs.getInt(1));
            }
            return category;

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            MySQLConnect.close(conn);
        }
        return null;
    }

    @Override
    public boolean delete(double id) {
        Connection conn = null;
        try{
            conn = MySQLConnect.open();
            String query ="DELETE FROM Categories WHERE CategoryID = ? ";
            PreparedStatement ps = conn.prepareStatement(query);
            // truyen tham so
            ps.setDouble(1, id);
            int result = ps.executeUpdate();
            if(result > 0){
                return true;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            MySQLConnect.close(conn);
        }
        return false;
    }

    @Override
    public Category update(Category category) {
        Connection conn = null;
        try{
            conn = MySQLConnect.open();
            String query ="UPDATE Categories SET CategoryName = ?, Description = ?, Status = ? WHERE CategoryID = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            // truyen tham so
            ps.setString(1,category.getName());
            ps.setString(2,category.getDescription());
            ps.setInt(3,category.getStatus());
            ps.setInt(4,category.getId());
            int result = ps.executeUpdate();
            if(result > 0){
                return category;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            MySQLConnect.close(conn);
        }
        return null;
    }
}
