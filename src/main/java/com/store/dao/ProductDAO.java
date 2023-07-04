package com.store.dao;

import com.store.model.Categories;
import com.store.model.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ProductDAO extends JpaRepository<Products, String> {
    List<Products> findByDeprecated(Boolean deprecated);
    Page<Products> findByDeprecated(Boolean deprecated, Pageable pageable);
    void deleteProductsByProductID(String ProductID);
    @Modifying(clearAutomatically = true)
    @Query(value = "update Products set deprecated = 1 WHERE productID = ?", nativeQuery = true)
    void updateLocal(String productID);
    List<Products> findByCategoryIn(Collection<Categories> categoryIds);

    @Modifying(clearAutomatically = true)
    @Query(value = "update Products set deprecated = 0 WHERE productID = ?", nativeQuery = true)
    void updateStatusTrue(String productID);
    Products findByProductID(String name);
    @Modifying(clearAutomatically = true)
    @Query(value = "select * from Products WHERE productID = ?", nativeQuery = true)
    Products findProductId(String productID);
}
