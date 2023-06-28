package com.store.service;

import com.store.model.Products;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    List<Products> findAll();
    Page<Products> findMen(int pageSize, int pageNumber) throws  Exception;
    Page<Products> findWomen(int pageSize, int pageNumber) throws  Exception;
    Page<Products> findAll(int pageSize, int pageNumber) throws  Exception;
    Products findByProductID(String productID);
    Products findById(String productID);
    Page<Products> findAllByCategoryId(String categoryId,int pageSize, int pageNumber) throws  Exception;
    Page<Products> listAll(String keyword,int pageSize, int pageNumber) throws  Exception;
}
