package com.ra.service.impl;

import com.ra.entity.Product;
import com.ra.repository.impl.ProductRepository;
import com.ra.service.IProductService;
import java.util.List;


public class ProductServiceImpl implements IProductService<Product> {
    ProductRepository productRepository = new ProductRepository();
    @Override
    public List<Product> findAll() {
        return productRepository.findAll(Product.class);
    }

    @Override
    public Product findId(String id) {
        return productRepository.findId(id,Product.class);
    }

    @Override
    public Product add(Product product) {
        return productRepository.add(product);
    }

    @Override
    public Product edit(Product entity) {
        return productRepository.edit(entity);
    }

    @Override
    public List<Product> findByName(String name) {
        return productRepository.findByName(name);
    }


}
