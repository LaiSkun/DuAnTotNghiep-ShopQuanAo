package com.store.service;

import com.store.model.Products;

import java.util.List;

public interface ProductService {
    List<Products> findAll();
    List<Products> findMen();
    List<Products> findWomen();
    Products findByProductID(String productID);
}
