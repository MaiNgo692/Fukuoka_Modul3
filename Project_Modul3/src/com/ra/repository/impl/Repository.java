package com.ra.repository.impl;

import com.ra.repository.IRepository;
import com.ra.util.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Repository<T,K> implements IRepository<T,K> {
    @Override
    public List<T> findAll(Class<T> entityClass) {
        List<T> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst;
        ResultSet rs;
        try{
            conn = MysqlConnect.open();
            pst = conn.prepareStatement("SELECT * FROM "+ entityClass.getAnnotation(Table.class).name());
            rs = pst.executeQuery();
            Field[] fields = entityClass.getDeclaredFields();
            while (rs.next()){
                result.add(setField(entityClass,rs,fields));
            }
        }catch (Exception ex){
            System.out.println(FontColor.err("Tìm không thành công!"));
            System.out.println(FontColor.err(ex.getMessage()));
        }finally {
            MysqlConnect.close(conn);
        }
        return result;
    }
    @Override
    public T findId(K id, Class<T> entityClass) {
        Connection conn = null;
        PreparedStatement pst;
        ResultSet rs;
        try{
            conn = MysqlConnect.open();
            Field[]fields =entityClass.getDeclaredFields();
            String fieldId = Arrays.stream(fields).filter(f-> f.getAnnotation(Id.class) != null).map(f-> f.getAnnotation(Column.class).name()).findFirst().get();
            String sql = MessageFormat.format("SELECT * FROM {0} WHERE {1} = ?", entityClass.getAnnotation(Table.class).name(), fieldId);
            pst =conn.prepareStatement(sql);
            pst.setObject(1,id);
            rs = pst.executeQuery();
            while (rs.next()){
                return setField(entityClass,rs,fields);
            }
        }catch (Exception ex){
            System.out.println(FontColor.err("Tìm không thành công!"));
            System.out.println(FontColor.err(ex.getMessage()));
        }finally {
            MysqlConnect.close(conn);
        }
        return null;
    }
    @Override
    public T add(T entity){
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
            pst = conn.prepareStatement(sql);
            int index =1;
            for(Field f: fields){
                f.setAccessible(true);
                pst.setObject(index++ ,f.get(entity));
            }
            int result = pst.executeUpdate();
            if(result >0){
                conn.commit();
                return entity;
            }
        }catch (Exception ex){
            try {
                conn.rollback();
            } catch (SQLException e) {
                System.out.println(FontColor.err(e.getMessage()));
            }
            System.out.println(FontColor.err(ex.getMessage()));
        }finally {
            MysqlConnect.close(conn);
        }
        return null;
    }

    @Override
    public T edit(T entity) {
        Connection conn = null;
        PreparedStatement pst;
        try{
            conn = MysqlConnect.open();
            conn.setAutoCommit(false);
            Field[]fields = entity.getClass().getDeclaredFields();
            String tableName = entity.getClass().getAnnotation(Table.class).name();
            List<Field> updateFields = Arrays.stream(fields).filter(f ->f.getAnnotation(Id.class) == null).collect(Collectors.toList());
            List<Field> keyFields = Arrays.stream(fields).filter(f -> f.getAnnotation(Id.class) != null).collect(Collectors.toList());
            String columns = updateFields.stream().map(f -> f.getAnnotation(Column.class).name() + " = ? ").collect(Collectors.joining(","));
            String key = keyFields.stream().map(f -> f.getAnnotation(Column.class).name()+" = ?").collect(Collectors.joining(","));
            String sql = MessageFormat.format("UPDATE {0} SET {1} WHERE {2}",tableName,columns,key);
//            System.out.println(sql);
            pst = conn.prepareStatement(sql);
            int index =1;
            for (Field f:updateFields){
                f.setAccessible(true);
                pst.setObject(index++,f.get(entity));
            }
            for (Field f:keyFields){
                f.setAccessible(true);
                pst.setObject(index++,f.get(entity));
            }
            int result =pst.executeUpdate();
            if(result>0){
                conn.commit();
                return entity;
            }
        }catch (Exception ex){
            try {
                conn.rollback();
            } catch (SQLException e) {
                System.out.println(FontColor.err(e.getMessage()));
            }
            System.out.println(FontColor.err("Sửa không thành công!"));
            System.out.println(FontColor.err(ex.getMessage()));
        }finally {
            MysqlConnect.close(conn);
        }
        return null;
    }

//    @Override
//    public boolean remove(K id, Class<T> entityClass) {
//        Connection conn = null;
//        PreparedStatement pst;
//        try{
//            conn = MysqlConnect.open();
//            Field[] fields = entityClass.getDeclaredFields();
//            String  fieldId = Arrays.stream(fields).filter(f -> f.getAnnotation(Id.class)!=null).map(f -> f.getAnnotation(Column.class).name()).findFirst().get();
//            String sql = MessageFormat.format("DELETE FROM {0} WHERE {1} =?",entityClass.getAnnotation(Table.class).name(),fieldId);
//            pst = conn.prepareStatement(sql);
//            pst.setObject(1,id);
//            int result = pst.executeUpdate();
//            if(result>0)
//                System.out.println(FontColor.err("Xóa thành công!"));
//            return result>0;
//        }catch (Exception ex){
//            System.out.println(FontColor.err("Xóa không thành công!"));
//            System.out.println(FontColor.err(ex.getMessage()));
//        }finally {
//            MysqlConnect.close(conn);
//        }
//        return false;
//    }
    T setField(Class<T> entityClass, ResultSet rs, Field[] fields) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        T entity = entityClass.getDeclaredConstructor().newInstance();
        for(Field f: fields){
            f.setAccessible(true);
            if(f.getType().equals(Date.class)){
                f.set(entity,rs.getDate(f.getAnnotation(Column.class).name()));
            }else {
                f.set(entity,rs.getObject(f.getAnnotation(Column.class).name()));
            }
        }
        return entity;
    }
}
