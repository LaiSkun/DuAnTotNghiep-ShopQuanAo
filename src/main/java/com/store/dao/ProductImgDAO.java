package com.store.dao;

import com.store.model.Product_Images;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImgDAO extends JpaRepository<Product_Images, Number > {
    List<Product_Images> findAll() ;
}
