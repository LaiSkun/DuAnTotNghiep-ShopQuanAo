package com.store.api;

import com.store.DTO.ProductColorUserDTO;
import com.store.DTO.ProductImgDTO;
import com.store.dao.ProductColorDAO;
import com.store.model.Product_Colors;
import com.store.model.Product_Images;
import com.store.service.ProductImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductApi {
	
	@Autowired
	ProductColorDAO dao;
	@Autowired
	ProductImgService productImgService;
	@PostMapping("/api/product-color/{colorId}")
	public ResponseEntity<List<ProductColorUserDTO>> postColor(@PathVariable Optional<Long> colorId) {
		Optional<Product_Colors> productColors = dao.findById(colorId.get());
		List<Product_Images> images = productImgService.findByProductcolorId(productColors.get());
		List<ProductColorUserDTO> ProductGetImg = new ArrayList<>();
		for (Product_Images image : images) {
		ProductColorUserDTO saveImg =  new ProductColorUserDTO();
			saveImg.setImgId(image.getImgID());
			saveImg.setImage(image.getImage());
			saveImg.setColorId(colorId.get());
			ProductGetImg.add(saveImg);
		}
		//vòng lặp trả về 1 images.get(index).getimage()/
//		images.get(0)
		return new ResponseEntity<List<ProductColorUserDTO>>(ProductGetImg, HttpStatus.OK);
	}
}
