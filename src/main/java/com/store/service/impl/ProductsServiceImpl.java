package com.store.service.impl;

import com.store.dao.ProductDAO;
import com.store.model.Products;
import com.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public class ProductsServiceImpl implements ProductService {
    @Autowired
    private ProductDAO productDAO;
	//chuyển về trạng thái ngưng bán
    @Override
    @Transactional(rollbackOn = {Exception.class, Throwable.class})
    public void deleteLogical(String productID) {
        productDAO.updateLocal(productID);
    }
	//xóa sản phẩm
    @Override
    public void deleteProduct(String productID) {
        productDAO.deleteProductsByProductID(productID);
    }
	//thêm sản phẩm (lưu)
    @Override
    @Transactional(rollbackOn = {Exception.class, Throwable.class})
    public Products save(Products product) throws SQLException {
        return productDAO.saveAndFlush(product);
    }
	//sản phẩm đang basn
    @Override
    public List<Products> findDeprecatedTrue() {
        return productDAO.findByDeprecated(Boolean.FALSE);
    }
	//sản phẩm ngưng bán
    @Override
    public List<Products> findDeprecatedFalse() {
        return productDAO.findByDeprecated(Boolean.TRUE);
    }
	// sản phẩm theo id
    @Override
    public Optional<Products> findByID(String productID) {
        return productDAO.findById(productID);
    }
	// sản phẩm theo name
    @Override
    public Products findProducts(String name) {
        return productDAO.findByProductID(name);
    }
    // tìm tất cả sản phẩm
    @Override
    public Page<Products> findAll(int pageSize, int pageNumber) throws Exception {
        if (pageNumber >= 1) {
            return productDAO.findAll(PageRequest.of(pageNumber - 1, pageSize));
        } else {
            throw new Exception("page false ");
        }
    }
    // phân trang
    @Override
    public Page<Products> findPaginated(Pageable pageable, List sql) {
     List<Products> products = sql;
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Products> list;

        if (products.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, products.size());
            list = products.subList(startItem, toIndex);
        }
        Page<Products> pageProducts = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), products.size());
        return pageProducts;

    }
    @Override
    public void updateStatusTrue(String productID) {
         productDAO.updateStatusTrue(productID);
    }

    // ProductUser

    @Override
    public List<Products> findAll() {
        return productDAO.findByDeprecated(Boolean.TRUE);
    }

    @Override
    public Page<Products> findMen(int pageSize, int pageNumber) throws  Exception {
        if (pageNumber >= 1) {


            return productDAO.findMen( PageRequest.of(pageNumber - 1, pageSize));
        }else{
            throw new Exception ("Page number must be grat than 0");
        }
    }

    @Override
    public Page<Products> findWomen (int pageSize, int pageNumber) throws  Exception {
        if (pageNumber >= 1) {


            return productDAO.findWoman( PageRequest.of(pageNumber - 1, pageSize));
        }else{
            throw new Exception ("Page number must be grat than 0");
        }
    }

    @Override
    public Page<Products> findByAll(int pageSize, int pageNumber) throws  Exception {
        if (pageNumber >= 1) {


            return productDAO.findByDeprecated(Boolean.TRUE, PageRequest.of(pageNumber - 1, pageSize));
        }else{
            throw new Exception ("Page number must be grat than 0");
        }
    }




    @Override
    public Products findByProductID(String productID) {
        return productDAO.findByProductID(productID);
    }

    @Override
    public Products findById(String productID) {
        Optional<Products> optional = productDAO.findById(productID);
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public Page<Products> findAllByCategoryId(String categoryId,int pageSize, int pageNumber) throws  Exception {
        if (pageNumber >= 1) {


            return productDAO.finByCategoryId(categoryId, PageRequest.of(pageNumber - 1, pageSize));
        }else{
            throw new Exception ("Page number must be grat than 0");
        }
    }


    @Override
    public Page<Products> listAll(String keyword,int pageSize, int pageNumber) throws  Exception{
        if (pageNumber >= 1) {

            if (keyword != null) {
                return productDAO.findAll(keyword, PageRequest.of(pageNumber - 1, pageSize));
            }
            return productDAO.findByDeprecated(Boolean.TRUE, PageRequest.of(pageNumber - 1, pageSize)) ;

        }else{
            throw new Exception ("Page number must be grat than 0");
        }
    }

}
