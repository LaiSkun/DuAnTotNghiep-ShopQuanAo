package com.store.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.model.Product_Colors;
import com.store.model.Product_Images;

public interface ProductImgDAO extends JpaRepository<Product_Colors, Long> {

	

}
