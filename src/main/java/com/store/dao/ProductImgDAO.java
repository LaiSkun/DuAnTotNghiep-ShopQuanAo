package com.store.dao;

import com.store.model.Product_Colors;
import com.store.model.Product_Images;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductImgDAO extends JpaRepository<Product_Images, Long > {
    List<Product_Images> findAll() ;
    Optional<Product_Images> findByImgID(long id);
//    Optional<Product_Images> findByProductcolor(Product_Colors productColors);
    List<Product_Images> findByProductcolor(Product_Colors productColors);

    void deleteByImgID(long id);
}
