package com.ra.repository.impl;

import com.ra.entity.IEntity;
import com.ra.repository.IRepository;
import com.ra.util.MySQLConnect;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class Repository <E extends IEntity,K> implements IRepository<E,K> {

    protected Class<E> clazz;
    protected String sp_findAll;
    protected String sp_findId;
    protected String sp_insert;
    protected String sp_update;
    protected String sp_delete;

    @Override
    public List<E> findAll() {
        List<E> result = new ArrayList<>();
        Connection conn = null;
        try {
            conn = MySQLConnect.open();
            CallableStatement cs = conn.prepareCall(sp_findAll);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                E entity = clazz.newInstance();
                entity.init(rs);
                result.add(entity);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public E findId(K id) {
        Connection conn = null;
        try {
            conn = MySQLConnect.open();
            CallableStatement cs = conn.prepareCall(sp_findId);
            cs.setObject(1, id);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                E entity = clazz.newInstance();
                entity.init(rs);
                return entity;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public E add(E entity) throws SQLException {
        Connection conn = null;
        try {
            conn = MySQLConnect.open();
            conn.setAutoCommit(false);
            CallableStatement cs = conn.prepareCall(sp_insert);
            setInsertParam(cs, entity);
            int result = cs.executeUpdate();
            if (result > 0) {
                handleInsert(cs, entity);
                conn.commit();
                return entity;
            } else
                return null;
        } catch (Exception ex) {
            conn.rollback();
            throw ex;
        }
    }

    @Override
    public E edit(E entity) throws SQLException {
        Connection conn = null;
        try {
            conn = MySQLConnect.open();
            CallableStatement cs = conn.prepareCall(sp_update);
            setUpdateParam(cs, entity);
            int result = cs.executeUpdate();
            if (result > 0) {
                handleUpdate(cs, entity);
                return entity;
            } else
                return null;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public boolean remove(K id) throws SQLException {
        Connection conn = null;
        try {
            conn = MySQLConnect.open();
            CallableStatement cs = conn.prepareCall(sp_delete);
            cs.setObject(1, id);
            int result = cs.executeUpdate();
            return result > 0;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public abstract void setInsertParam(CallableStatement cs, E entity) throws SQLException;
    public abstract void setUpdateParam(CallableStatement cs, E entity) throws SQLException;
    public abstract void handleInsert(CallableStatement cs, E entity) throws SQLException;
    public abstract void handleUpdate(CallableStatement cs, E entity) throws SQLException;
}
