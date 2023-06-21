package com.store.dao;

import com.store.model.Product_Colors;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductColorDAO extends JpaRepository<Product_Colors, Number> {

     List<Product_Colors> findAll() ;
}
