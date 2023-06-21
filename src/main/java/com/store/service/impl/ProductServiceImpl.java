package com.store.service.impl;

import com.store.dao.ProductDAO;
import com.store.model.Product_Colors;
import com.store.model.Product_Images;
import com.store.model.Products;
import com.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDAO dao;

    @Override
    public List<Products> findAll() {
        return dao.findByDeprecatedAndAvailableGreaterThan(Boolean.TRUE, 0);
    }

    @Override
    public List<Products> findMen() {
        return dao.findMen();
    }

    @Override
    public List<Products> findWomen() {
        return dao.findWoman();
    }

    @Override
    public Products findByProductID(String productID) {
        return dao.findByProductID(productID);
    }

	

}
