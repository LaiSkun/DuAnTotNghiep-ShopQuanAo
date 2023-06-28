package com.store.service.impl;

import com.store.dao.ProductColorDAO;
import com.store.model.Product_Colors;
import com.store.model.Product_Images;
import com.store.model.Products;
import com.store.service.ProductColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ProductColorServiceImpl implements ProductColorService {
    @Autowired
    private ProductColorDAO dao;


    @Override
    public Product_Colors findById(Integer colorID) {
        Optional<Product_Colors> optional = dao.findById(colorID);
        return optional.isPresent() ? optional.get() : null;
    }
}
