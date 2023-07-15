package com.store.service;

import com.store.model.Product_Colors;
import com.store.model.Product_Images;
import com.store.model.Products;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ProductColorsService {
    List<Product_Colors> findAll();
    List<Product_Colors> findByProductID(String id);
    Product_Colors save(Product_Colors productColors) throws SQLException;
    Optional<Product_Colors> findByID(long colorID);
    Optional<Product_Colors> findByproductIDAndColorHex(Products productID, String colorHex);
    @Scope(proxyMode = ScopedProxyMode.INTERFACES)
    @Transactional
    public void deleteColor(long id);
}