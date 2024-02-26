package com.ra.service;

import com.ra.entity.Product;

import java.util.List;
import java.util.Scanner;

public interface IProductService<Product> extends IService<Product,String>{
    List<Product> findByName(String name);
}
