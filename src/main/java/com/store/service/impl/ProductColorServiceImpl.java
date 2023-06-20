package com.store.service.impl;

import com.store.dao.ProductColorDAO;
import com.store.model.Product_Colors;
import com.store.service.ProductColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductColorServiceImpl implements ProductColorService {
    @Autowired
    private ProductColorDAO dao;


}
