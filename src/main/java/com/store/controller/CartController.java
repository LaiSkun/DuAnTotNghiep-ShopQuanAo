package com.store.controller;

import com.store.DTO.CartDto;
import com.store.dao.ProductDAO;
import com.store.model.Order_Details;
import com.store.model.Orders;
import com.store.model.Products;
import com.store.model.Users;
import com.store.service.*;
import com.store.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private OrderDetailsService orderDetailsService;
    @GetMapping("")
    public String dogetIndex(Model model){
        return "layout/cart";
    }
    @GetMapping("/update")
    public String doGetUpdate(
            @RequestParam("productID") String productID,
            @RequestParam("colorID") Long colorID,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("categoryID") String categoryID,
            @RequestParam("isReplace") Boolean isRePlace, HttpSession session) {


        CartDto currentCart = SessionUtil.getCurrenCart(session);
        cartService.updateCart(currentCart, productID,colorID ,quantity,categoryID, isRePlace);

        return "layout/cart :: #row";
    }
    @RequestMapping("/check/{userId}")
    public String doGetCheck(@PathVariable("userId") String userId, Model model) {
        Users users = userService.findById(userId);
        if (users == null) {
            return "redirect:/home";
        } else {
            List<Orders> orders = ordersService.findByUserID(userId);
            model.addAttribute("orders", orders);
        }
        return "layout/checkout";
    }
}
