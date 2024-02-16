package com.ra.repository;

import java.sql.SQLException;
import java.util.List;

public interface IRepository<E,K> {
    List<E> findAll();
    E findId(K id);
    E add(E entity) throws SQLException;
    E edit(E entity) throws SQLException;
    boolean remove(K id) throws SQLException;
}
