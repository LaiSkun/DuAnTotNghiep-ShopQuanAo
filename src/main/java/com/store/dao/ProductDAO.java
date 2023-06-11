package com.store.dao;

import com.store.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDAO extends JpaRepository<Products, String> {
    List<Products> findByAvailableAndDeprecatedFalse(Integer available);
}
