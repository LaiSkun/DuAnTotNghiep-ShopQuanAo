package com.store.service.impl;

import com.store.dao.ProductDAO;
import com.store.model.Products;
import com.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDAO dao;

    @Override
    public List<Products> findAll() {
        return dao.findByDeprecatedAndAvailableGreaterThan(Boolean.TRUE, 0);
    }

    @Override
    public Page<Products> findMen(int pageSize, int pageNumber) throws  Exception {
        if (pageNumber >= 1) {


            return dao.findMen( PageRequest.of(pageNumber - 1, pageSize));
        }else{
            throw new Exception ("Page number must be grat than 0");
        }
    }

    @Override
    public Page<Products> findWomen (int pageSize, int pageNumber) throws  Exception {
        if (pageNumber >= 1) {


            return dao.findWoman( PageRequest.of(pageNumber - 1, pageSize));
        }else{
            throw new Exception ("Page number must be grat than 0");
        }
    }

    @Override
    public Page<Products> findAll(int pageSize, int pageNumber) throws  Exception {
        if (pageNumber >= 1) {


            return dao.findByDeprecatedAndAvailableGreaterThan(Boolean.TRUE, 0, PageRequest.of(pageNumber - 1, pageSize));
        }else{
            throw new Exception ("Page number must be grat than 0");
        }
    }




    @Override
    public Products findByProductID(String productID) {
        return dao.findByProductID(productID);
    }

    @Override
    public Products findById(String productID) {
        Optional<Products> optional = dao.findById(productID);
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public Page<Products> findAllByCategoryId(String categoryId,int pageSize, int pageNumber) throws  Exception {
        if (pageNumber >= 1) {


            return dao.finByCategoryId(categoryId, PageRequest.of(pageNumber - 1, pageSize));
        }else{
            throw new Exception ("Page number must be grat than 0");
        }
    }


    @Override
    public Page<Products> listAll(String keyword,int pageSize, int pageNumber) throws  Exception{
        if (pageNumber >= 1) {

            if (keyword != null) {
                return dao.findAll(keyword, PageRequest.of(pageNumber - 1, pageSize));
            }
            return dao.findByDeprecatedAndAvailableGreaterThan(Boolean.TRUE, 0, PageRequest.of(pageNumber - 1, pageSize)) ;

        }else{
            throw new Exception ("Page number must be grat than 0");
        }
    }


}
