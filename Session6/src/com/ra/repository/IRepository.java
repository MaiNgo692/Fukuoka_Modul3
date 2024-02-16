package com.ra.repository;

import java.util.List;

public interface IRepository <T, K>{
    List<T> findAll(Class<T> entityClass);
    T findId(K id,Class<T> entityClas);
    T add(T entity,Class<T> entityClass);
    T edit(T entity);
    boolean remove(K id,Class<T> entityClass);
}
