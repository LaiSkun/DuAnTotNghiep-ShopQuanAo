package com.store.DTO;

import com.store.model.Products;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
public class ProductImgDTO {
    @ManyToOne
    @JoinColumn(name = "productID")
    Products product;
    long id;
    String Color;
    String ColorHex;
    MultipartFile img;
    int avaible;
    long imgid;

    public ProductImgDTO(Products product, String color, String colorHex, MultipartFile img) {
        this.product = product;
        Color = color;
        ColorHex = colorHex;
        this.img = img;
    }
    public ProductImgDTO(Products product,int avaible, String color, String colorHex){
        this.product = product;
        Color = color;
        this.avaible = avaible;
        ColorHex = colorHex;

    }
    public ProductImgDTO() {
    }
}
