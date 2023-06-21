package com.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.dao.ProductImgDAO;
import com.store.model.Product_Images;
import com.store.service.ProductImgService;
@Service
public class ProductImgServiceImpl implements ProductImgService{
	@Autowired
	ProductImgDAO dao;
	

}
