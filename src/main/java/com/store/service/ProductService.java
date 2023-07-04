package com.store.service;

import com.store.model.Product_Colors;
import com.store.model.Product_Images;
import com.store.model.Products;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    public void deleteLogical(String productID);

    @Scope(proxyMode = ScopedProxyMode.INTERFACES)
    @Transactional
    public void deleteProduct(String productID);

    Products save(Products product) throws SQLException;

    List<Products> findDeprecatedTrue();

    List<Products> findDeprecatedFalse();

    Optional<Products> findByID(String productID);
//    List<Products> findByCategory(String cate);
    Products findProducts(String name);
    Page<Products> findAll(int pageSize, int pageNumber) throws Exception;
    public  void updateStatusTrue(String productID);
    Page<Products> findPaginated(Pageable pageable, List sql);

}
