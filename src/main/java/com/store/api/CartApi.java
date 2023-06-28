package com.store.api;


import com.store.dto.CartDto;
import com.store.service.CartService;
import com.store.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/cart")
public class CartApi {
    // /api/cart/ipdate?productId =.....&quantity=....&isReplace=.....

    @Autowired
    private CartService cartService;

    @GetMapping("/update")
    public ResponseEntity<?> doGetUpdate(@RequestParam("productID") String productID,
                                         @RequestParam("quantity") Integer quantity,
                                         @RequestParam("isReplace") Boolean isRePlace,
                                         HttpSession session){
        CartDto currentCart = SessionUtil.getCurrenCart(session);
        cartService.updateCart(currentCart, productID, quantity, isRePlace);


        return ResponseEntity.ok(currentCart);
    }
}
