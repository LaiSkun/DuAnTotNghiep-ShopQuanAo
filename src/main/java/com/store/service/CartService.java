package com.store.service;

import com.store.DTO.CartDto;
import com.store.model.Users;

public interface CartService {
    CartDto updateCart(CartDto cart, String productID, Long colorID, Integer quantity,String categoryID, boolean isReplace);

    Integer getTotalQuantity(CartDto cart);

    Double getTotalPrice(CartDto cart);

    void insert(CartDto cart, Users user, String address, String phone, String email);
}
