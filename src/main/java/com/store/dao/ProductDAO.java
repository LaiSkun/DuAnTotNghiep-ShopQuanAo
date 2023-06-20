package com.store.dao;


import com.store.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDAO extends JpaRepository<Products,  String> {
    List<Products> findByDeprecatedAndAvailableGreaterThan(Boolean Deprecated, Integer available );

    @Query(value = "SELECT * from products where categoryID like '%nam' and deprecated = 1 and available > 0", nativeQuery = true)
    List<Products> findMen();

    @Query(value = "SELECT * from products where categoryID like '%nu' and deprecated = 1 and available > 0", nativeQuery = true)
    List<Products> findWoman();

    Products findByProductID(String productID);
}
