package com.store.service;

import com.store.dto.CartDto;

public interface CartService  {
    CartDto updateCart(CartDto cart, String productID,  Integer quantity, boolean isReplace);
    Integer getTotalQuantity(CartDto cart);
    Double getTotalPrice(CartDto cart);
}
