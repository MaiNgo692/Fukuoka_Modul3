package com.ra;

import com.ra.model.entity.ProductEntity;
import com.ra.repository.IRepository;
import com.ra.repository.impl.Repository;

public class Application {
    public static void main(String[] args) {
        IRepository<ProductEntity, String> productRepository = new Repository<>();

//        for (ProductEntity p : productRepository.findAll(ProductEntity.class)) {
//            System.out.println(p.getId() + ": " + p.getName());
//        }
//
//        System.out.println("findId()");
//        ProductEntity p = productRepository.findId("P001", ProductEntity.class);
//        System.out.println(p.getId() + " | " + p.getName());

        ProductEntity product = new ProductEntity("15", "Smartphone 11X", 1, "image_url", "list_image_url", 500F, 450F, "Description of Smartphone X", "smartphone, tech", "Product content goes here", false);
//        System.out.println("add()");
//        productRepository.add(product,ProductEntity.class);
//        productRepository.edit(product);
        System.out.println("remove()");
          productRepository.remove("1",ProductEntity.class);

    }
}
