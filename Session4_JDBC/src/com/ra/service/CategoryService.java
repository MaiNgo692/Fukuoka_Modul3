package com.ra.service;

import com.ra.entity.Category;

import java.util.List;

public interface CategoryService {
    public List<Category> findAll();
    public List<Category> findByName(String name);
    Category findId(int id);
    Category insert(Category category);
    boolean delete(double id);
    Category update(Category category);
}
