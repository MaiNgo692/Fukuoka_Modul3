package com.ra.repository.impl;

import com.ra.repository.IRepository;
import com.ra.util.Column;
import com.ra.util.Id;
import com.ra.util.MySqlConnect;
import com.ra.util.Table;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public  class Repository <T,K> implements IRepository<T,K> {
    @Override
    public List<T> findAll(Class<T> entityClass) {
        List<T> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst ;
        ResultSet rs ;
        try{
            conn = MySqlConnect.open();
            pst = conn.prepareStatement("SELECT * FROM " + entityClass.getAnnotation(Table.class).name());
            rs = pst.executeQuery();
            while (rs.next()){
                T entity = entityClass.getDeclaredConstructor().newInstance();
                Field[] fields = entityClass.getDeclaredFields();
                for(Field f : fields){
                    f.setAccessible(true);
                    f.set(entity,rs.getObject(f.getAnnotation(Column.class).name()));
                }
                result.add(entity);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            MySqlConnect.close(conn);
        }
        return result;
    }

    @Override
    public T findId(K id, Class<T> entityClass) {
        Connection conn = null;
        PreparedStatement pst;
        ResultSet rs;

        try{
            conn = MySqlConnect.open();
            Field[]fields = entityClass.getDeclaredFields();
            String fieldId = Arrays.stream(fields).filter(f -> f.getAnnotation(Id.class) != null).map(f-> f.getAnnotation(Column.class).name()).findFirst().get();
            String sql = MessageFormat.format("SELECT * FROM {0} WHERE {1} = ?", entityClass.getAnnotation(Table.class).name(),fieldId);
            pst = conn.prepareStatement(sql);
            pst.setObject(1,id);
            rs = pst.executeQuery();
            while (rs.next()) {
                T entity = entityClass.getDeclaredConstructor().newInstance();
                for (Field f : fields) {
                    f.setAccessible(true);
                    f.set(entity, rs.getObject(f.getAnnotation(Column.class).name()));
                }
                return entity;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            MySqlConnect.close(conn);
        }
        return null;
    }

    @Override
    public T add(T entity) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            Field[] fields = entity.getClass().getDeclaredFields();
            conn = MySqlConnect.open();
            String columns = Arrays.stream(fields).map(f -> f.getAnnotation(Column.class).name()).collect(Collectors.joining(","));
            String values = Arrays.stream(fields).map(f -> "?").collect(Collectors.joining(","));
            String sql = MessageFormat.format("INSERT INTO {0}({1}) VALUES ({2})", entity.getClass().getAnnotation(Table.class).name(), columns,values);
            int index = 1;
            pst = conn.prepareStatement(sql);
            for (Field f : fields) {
                f.setAccessible(true);
                pst.setObject(index++,f.get(entity));
            }
            int result= pst.executeUpdate();
            return entity;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            MySqlConnect.close(conn);
        }
        return null;
    }

    @Override
    public T edit(T entity) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = MySqlConnect.open();
            Field[] fields = entity.getClass().getDeclaredFields();
            String tblName = entity.getClass().getAnnotation(Table.class).name();
            List<Field> updateFields = Arrays.stream(fields)
                    .filter(f -> f.getAnnotation(Id.class) == null)
                    .collect(Collectors.toList());
            List<Field> keyFields = Arrays.stream(fields)
                    .filter(f -> f.getAnnotation(Id.class) != null)
                    .collect(Collectors.toList());
            String columns = updateFields.stream().map(f -> f.getAnnotation(Column.class).name() + " = ?").collect(Collectors.joining(","));
            String key = keyFields.stream().map(f -> f.getAnnotation(Column.class).name() + " = ?").collect(Collectors.joining(","));
            String sql = MessageFormat.format("UPDATE {0} SET {1} WHERE {2}", tblName, columns,key);
//            System.out.println(sql);
            int index = 1;
            pst = conn.prepareStatement(sql);
            for (Field f : updateFields) {
                f.setAccessible(true);
                pst.setObject(index++,f.get(entity));
            }
            for (Field f : keyFields) {
                f.setAccessible(true);
                pst.setObject(index++,f.get(entity));
            }
            int result= pst.executeUpdate();
            return entity;
        }catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            MySqlConnect.close(conn);
        }
        return null;

    }

    @Override
    public boolean remove(K id, Class<T> entityClass) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            Field[] fields = entityClass.getDeclaredFields();
            conn = MySqlConnect.open();
            String fieldId = Arrays.stream(fields)
                    .filter(f -> f.getAnnotation(Id.class) != null)
                    .map(f -> f.getAnnotation(Column.class).name()).findFirst().get();
            String sql = MessageFormat.format("DELETE  FROM {0} WHERE {1} = ?", entityClass.getAnnotation(Table.class).name(), fieldId);
            System.out.println(sql);
            pst = conn.prepareStatement(sql);
            pst.setObject(1, id);
            int result = pst.executeUpdate();
            return result>0;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            MySqlConnect.close(conn);
        }
        return false;
    }
}
