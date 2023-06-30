package com.store.service.impl;

import com.store.dao.ProductImgDAO;
import com.store.model.Product_Colors;
import com.store.model.Product_Images;
import com.store.service.ProductImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Product_Images save(Product_Images productImages) throws SQLException {
         return productImgDAO.saveAndFlush(productImages);
    }

    @Override
    public Optional<Product_Images> findByProductcolor(Product_Colors productColors) {
        return productImgDAO.findByProductcolor(productColors);
    }

    @Override
    public Optional<Product_Images> findById(long id) {
        return productImgDAO.findById(id);
    }

    @Override
    public void deleteImg(long id) {
        productImgDAO.deleteByImgID(id);
    }
}
