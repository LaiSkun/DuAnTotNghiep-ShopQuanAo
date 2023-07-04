package com.store.dao;

import com.store.model.Categories;
import com.store.model.Product_Colors;
import com.store.model.Product_Images;
import com.store.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ProductImgDAO extends JpaRepository<Product_Images, Long > {
    List<Product_Images> findAll() ;
    Optional<Product_Images> findByImgID(long id);
    Optional<Product_Images> findByProductcolor(Product_Colors productColors);
    void deleteByImgID(long id);
}
