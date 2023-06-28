package com.store.service.impl;

import com.store.dto.CartDetailDto;
import com.store.dto.CartDto;

import com.store.model.Products;
import com.store.service.CartService;
import com.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
@Service
public class CartServiceImpl  implements CartService {
    @Autowired
    private ProductService productService;

    @Override
    public CartDto updateCart(CartDto cart, String productID, Integer quantity, boolean isReplace) {
        Products product = productService.findById(productID);

        HashMap<String, CartDetailDto> details = cart.getDetails();
        // 1. Thêm mới sản phẩm
        // 2. Upate
        //      2.1 Cộng dồn
        //      2.2 Replace
        // 3. Update quantity ve 0
        if(!details.containsKey(productID)){
            // thêm mới
            CartDetailDto newDetail = createNewCartDetail(product, quantity);
            details.put(productID, newDetail);
        }else if(quantity > 0){
            // update
            if (isReplace){
                // thay thế
                details.get(productID).setQuantity(quantity);
            }else {
                // cộng dồn
                Integer oldQuantity = details.get(productID).getQuantity();
                Integer newQuantity = oldQuantity + quantity;
                details.get(productID).setQuantity(newQuantity);
            }

        }else {
            // deleted
            details.remove(productID);
        }
        //update totalQuantity
        cart.setTotalQuantity(getTotalQuantity(cart));
        //update totalprice
        cart.setTotalPrice(getTotalPrice(cart));

        return cart;
    }



    @Override
    public Integer getTotalQuantity(CartDto cart) {
        Integer totalQuantity = 0;
        HashMap<String, CartDetailDto> details = cart.getDetails();
        for (CartDetailDto cartDetail : details.values()){
            totalQuantity += cartDetail.getQuantity();

        }
        return totalQuantity;
    }

    @Override
    public Double getTotalPrice(CartDto cart) {
        Double totalPrice = 0D;
        HashMap<String, CartDetailDto> details = cart.getDetails();
        for (CartDetailDto cartDetail : details.values()){
            totalPrice += cartDetail.getPrice() * cartDetail.getQuantity();

        }
        return totalPrice;
    }

    private CartDetailDto createNewCartDetail(Products product, Integer quantity){
        CartDetailDto cartDetail = new CartDetailDto();
        cartDetail.setProductID(product.getProductID());
        cartDetail.setPrice(product.getPrice());
        cartDetail.setQuantity(quantity);
        cartDetail.setName(product.getName());
        cartDetail.setImg(product.getImg());
        return cartDetail;
    }
}


