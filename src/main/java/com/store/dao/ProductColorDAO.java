package com.store.dao;

import com.store.model.Product_Colors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductColorDAO extends JpaRepository<Product_Colors, Long> {

//    List<Product_Colors> findByProductID(String productID);
//    List<Product_Colors> findByProductID(String productID);
}
