package com.store.service.impl;

import com.store.dao.ProductColorDAO;
import com.store.model.Product_Colors;
import com.store.service.ProductColorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public class ProductColorsImpl implements ProductColorsService {
    @Autowired
    public ProductColorDAO productColorDAO;
    @Override
    public List<Product_Colors> findAll() {
        return  productColorDAO.findAll();
    }
}
