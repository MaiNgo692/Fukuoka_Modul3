package com.ra.service;

import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

public interface IService<T,K> {
    List<T> findAll();
    T findId(K id);
    T add (T entity) ;
    T edit(T entity);
}
