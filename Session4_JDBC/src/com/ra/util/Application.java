package com.ra.util;

import com.ra.entity.Category;
import com.ra.service.impl.CategoryServiceImpl;

import java.sql.Connection;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        CategoryServiceImpl service = new CategoryServiceImpl();
        List<Category> allCategory = service.findAll();
        allCategory.forEach(category -> System.out.println(category.getId()+": " + category.getName()));
//        System.out.println("----------------------------------");
//        List<Category> findCategory = service.findByName("n");
//        findCategory.forEach(category -> System.out.println(category.getId()+": " + category.getName()));
//        if(service.delete(3)){
//            System.out.println("delete success!");
//            List<Category> allCategory = service.findAll();
//            allCategory.forEach(category -> System.out.println(category.getId()+": " + category.getName()));
//        }
//        Category category = new Category(3,"updated","Test,update","mo ta", 0);
//        service.update(category);
    }
}
