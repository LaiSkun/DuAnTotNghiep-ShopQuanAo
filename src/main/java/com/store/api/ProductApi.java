package com.store.api;

import com.store.DTO.ProductColorDTO;
import com.store.DTO.ProductImgDTO;
import com.store.dao.ProductColorDAO;
import com.store.model.Product_Images;
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
	
	@PostMapping("/api/product-color/{colorId}")
	public ResponseEntity<List<ProductColorDTO>> postColor(@PathVariable Optional<Long> colorId) {
		List<Product_Images> images = dao.findByColorID(colorId.orElse(0L)).getImages();
		List<ProductColorDTO> ProductGetImg = new ArrayList<>();
		for (Product_Images image : images) {
		ProductColorDTO saveImg =  new ProductColorDTO();
			saveImg.setImgId(image.getImgID());
			saveImg.setImage(image.getImage());
			saveImg.setColorId(colorId.get());
			ProductGetImg.add(saveImg);
		}
		//vòng lặp trả về 1 images.get(index).getimage()/
//		images.get(0)
		return new ResponseEntity<List<ProductColorDTO>>(ProductGetImg, HttpStatus.OK);
	}
}
