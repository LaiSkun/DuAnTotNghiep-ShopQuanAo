package com.store.dao;

import com.store.model.Product_Colors;
import com.store.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductColorDAO extends JpaRepository<Product_Colors, Long> {
    Optional<Product_Colors> findByProductAndColorhex(Products id, String colorHex);
    void deleteByColorID(long id);

    Product_Colors findByColorID(Integer colorID);
}
