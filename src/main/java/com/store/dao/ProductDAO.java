package com.store.dao;

import com.store.model.Categories;
import com.store.model.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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
    //search full
    @Query(nativeQuery = true, value = "select * from [dbo].[searchFullTextProducts](:search1)")
    List<Products> findSearch(@Param("search1") String search);
    @Query(nativeQuery = true, value = "select * from [dbo].[full_text_categoryId]( :categoryId, :search12)")
    List<Products> findSearchAndCateId1(@Param("search12") String search, @Param("categoryId") String cate);
    @Query(nativeQuery = true, value = "select * from [dbo].[full_text_statusF](:search1)")
    List<Products> findSearchAndSatusF(@Param("search1") String search);
    @Query(nativeQuery = true, value = "select * from [dbo].[full_text_statusT](:search1)")
    List<Products> findSearchAndSatusT(@Param("search1") String search);

    // Product User
    @Query(value = "SELECT * from products where categoryID like '%nam' and deprecated = 1 ", nativeQuery = true)

    Page<Products> findMen(Pageable pageable);

    @Query(value = "SELECT * from products where categoryID like '%nu' and deprecated = 1  ", nativeQuery = true)
    Page<Products> findWoman(Pageable pageable);

    @Query(value = "SELECT * from products where categoryID = :categoryId and deprecated = 1  ",nativeQuery = true)
    Page<Products> finByCategoryId(String categoryId, Pageable pageable);

    @Query(value = "SELECT * from products  where [name] LIKE %?1% and deprecated = 1  ", nativeQuery = true)
    Page<Products> findAll(String keyword, Pageable pageable);

    @Query(value = "SELECT * from products where deprecated = 1  ", nativeQuery = true)
    Page<Products> findByDeprecated(Pageable pageable);

}
