package com.ra.service.impl;

import com.ra.entity.Category;
import com.ra.service.CategoryService;
import com.ra.util.MySQLConnect;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
    public Category insert(Category category) {
        Connection conn = null;
        try{
            conn = MySQLConnect.open();
            String query ="INSERT INTO Categories (CategoryName, Description, Status)VALUES (?,?,?)";
            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            // truyen tham so
            ps.setString(1,category.getName());
            ps.setString(2,category.getDescription());
            ps.setInt(3,category.getStatus());

            int result = ps.executeUpdate();
            if(result > 0){
                try(ResultSet generateKeys = ps.getGeneratedKeys() ){
                    while (generateKeys.next()){
                        category.setId(generateKeys.getInt(1));
                    }
                }
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
