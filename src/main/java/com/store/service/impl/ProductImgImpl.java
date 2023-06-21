package com.store.service.impl;

import com.store.dao.ProductImgDAO;
import com.store.model.Product_Images;
import com.store.service.ProductImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public class ProductImgImpl implements ProductImgService {
    @Autowired
    ProductImgDAO productImgDAO;
    @Override
    public List<Product_Images> findAll() {
       return productImgDAO.findAll();
    }
}
