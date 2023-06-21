package com.store.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.dao.ProductColorDAO;
import com.store.model.Product_Images;

@RestController
public class ProductApi {
	@Autowired
	ProductColorDAO dao;
	
	@PostMapping("/api/product-color/{colorId}")
	public ResponseEntity<List<Product_Images>> postColor(@PathVariable Optional<Integer> colorId) {
		List<Product_Images> images = dao.findById(colorId.orElse(0)).get().getImages();
		return new ResponseEntity<List<Product_Images>>(images, HttpStatus.OK);
	}
}
