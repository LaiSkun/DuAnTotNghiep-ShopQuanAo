package com.store.DTO;

import com.store.model.Product_Colors;
import lombok.Data;

import java.util.List;

@Data
public class sellingProductsDTO {
    String productID;
    String name;
    String img;
    Double price;
    String quantitysold;
    String categoryID;
    List<String> nameImg;
    List<Product_Colors> color;
    public sellingProductsDTO() {
    }

    public sellingProductsDTO(List<String> nameImg,String productID, String name, String img, Double price,List<Product_Colors> color, String quantitysold,String categoryID) {
        this.productID = productID;
        this.name = name;
        this.img = img;
        this.price = price;
        this.quantitysold = quantitysold;
        this.color = color;
        this.nameImg = nameImg;
        this.categoryID = categoryID;
    }
}
