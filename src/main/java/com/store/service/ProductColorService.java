package com.store.service;

import com.store.model.Product_Colors;
import com.store.model.Products;


import java.util.List;

public interface ProductColorService {


//    List<Product_Colors> findByProductID(String productID);
//    List<Product_Colors> findByAll();
    Product_Colors findById(Integer colorID);
}
