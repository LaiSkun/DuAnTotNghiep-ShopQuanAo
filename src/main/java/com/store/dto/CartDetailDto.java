package com.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDetailDto implements Serializable {

    private Long orderID;
    private String productID;
    private Integer colorID;
    private String color_hex;
    private Double price;
    private Integer quantity;
    private String name;
    private String img;
}
