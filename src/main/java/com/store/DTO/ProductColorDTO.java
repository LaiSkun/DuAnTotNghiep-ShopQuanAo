package com.store.DTO;

import com.store.model.Products;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
public class ProductColorDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long colorID;
    String colorhex;
    int avaible;
    String color_name;
    @ManyToOne
    @JoinColumn(name = "productID")
    Products product;
}
