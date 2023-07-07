package com.store.dao;

import com.store.model.Categories;
import com.store.model.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
@Repository
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

    // Product User
    List<Products> findByDeprecatedAndAvailableGreaterThan(Boolean Deprecated, Integer available);

    @Query(value = "SELECT * from products where categoryID like '%nam' and deprecated = 1 and available > 0", nativeQuery = true)

    Page<Products> findMen(Pageable pageable);

    @Query(value = "SELECT * from products where categoryID like '%nu' and deprecated = 1 and available > 0", nativeQuery = true)
    Page<Products> findWoman(Pageable pageable);

    @Query(value = "SELECT * from products where categoryID = :categoryId and deprecated = 1 and available > 0",nativeQuery = true)
    Page<Products> finByCategoryId(String categoryId, Pageable pageable);

    @Query(value = "SELECT * from products  where [name] LIKE %?1% and deprecated = 1 and available > 0", nativeQuery = true)
    Page<Products> findAll(String keyword, Pageable pageable);

    Page<Products> findByDeprecatedAndAvailableGreaterThan(Boolean Deprecated, Integer available,Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE products SET quantity = ? WHERE id = ?",nativeQuery = true)
    void updateQuantity(Integer newAvailable,String productID);
}
