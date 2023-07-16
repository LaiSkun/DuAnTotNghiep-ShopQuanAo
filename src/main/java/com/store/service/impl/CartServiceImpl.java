package com.store.service.impl;

import com.store.DTO.CartDetailDto;
import com.store.DTO.CartDto;
import com.store.model.*;
import com.store.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Date;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductColorsService productcolorsService;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private OrderDetailsService orderDetailsService;
    @Autowired
    private StatusService statusService;

    @Override
    public CartDto updateCart(CartDto cart, String productID, Long colorID, Integer quantity,String categoryID, boolean isReplace) {
        Products product = productService.findById(productID);

        Map<String, CartDetailDto> details = cart.getDetails();

        String colorIDStr = String.valueOf(colorID);
        if (!details.containsKey(colorIDStr)) {
            // Thêm mới sản phẩm
            CartDetailDto newDetail = createNewCartDetail(cart, product, colorID, quantity,categoryID);
            details.put(colorIDStr, newDetail);
        } else if (quantity > 0){
            // Cập nhật số lượng sản phẩm
            CartDetailDto existingDetail = details.get(colorIDStr);
            if (isReplace) {
                // Thay thế số lượng sản phẩm
                existingDetail.setQuantity(quantity);
            } else {
                // Cộng dồn số lượng sản phẩm
                existingDetail.setQuantity(existingDetail.getQuantity() + quantity);
            }
        }else {
            details.remove(colorIDStr);
        }

        // Cập nhật tổng số lượng và tổng giá trị của giỏ hàng
        cart.setTotalQuantity(getTotalQuantity(cart));
        cart.setTotalPrice(getTotalPrice(cart));

        return cart;
    }

    @Override
    public Integer getTotalQuantity(CartDto cart) {
        Integer totalQuantity = 0;
        Map<String, CartDetailDto> details = cart.getDetails();
        for (CartDetailDto cartDetail : details.values()) {
            totalQuantity += cartDetail.getQuantity();
        }
        return totalQuantity;
    }

    @Override
    public Double getTotalPrice(CartDto cart) {
        Double totalPrice = 0D;
        Map<String, CartDetailDto> details = cart.getDetails();
        for (CartDetailDto cartDetail : details.values()) {
            totalPrice += cartDetail.getPrice() * cartDetail.getQuantity();
        }
        return totalPrice;
    }
    @Transactional
    @Override
    public void insert(CartDto cart, Users user, String address, String phone, String email) {
        // insert vao order
        Orders order = new Orders();
        order.setUser(user);
        order.setAddress(address);
        order.setPhone(phone);
        order.setEmail(email);
        order.setPrice(cart.getTotalPrice());


        try {
            Orders orderRespone = ordersService.insert(order);
            Status status = new Status();
            status.setStatusID(order.getOrderID());
            status.setDescription("Chưa thanh toán");
            status.setStatusname("New");
            status.setCreateDate(new Date());
            status.setTransportFee(cart.getTotalPrice() * 2 / 10);
            status.setFeeCollected(0.0);
            status.setOrders(order);
            Status status1 = statusService.insert(status);
            // Duyet hashmap de insert lan luot vao order_details
            // trong luc duyet hashmap qua tung sp -> di update quantity cho tung san pham
            for (CartDetailDto cartDetail : cart.getDetails().values()){
                cartDetail.setOrderID(orderRespone.getOrderID());
                orderDetailsService.insert(cartDetail);

                Product_Colors product_colors = productcolorsService.findById(cartDetail.getColorID());
                Integer newAvailable = product_colors.getAvailable()-cartDetail.getQuantity();
                productcolorsService.updateQuantity(newAvailable,cartDetail.getColorID());
            }
        }catch (Exception ex){

        }
    }

    private CartDetailDto createNewCartDetail(CartDto cart, Products product, Long colorID, Integer quantity, String categoryID) {
        CartDetailDto cartDetail = new CartDetailDto();
        cartDetail.setProductID(product.getProductID());
        cartDetail.setPrice(product.getPrice());
        cartDetail.setQuantity(quantity);
        cartDetail.setName(product.getName());
        cartDetail.setImg(product.getImg());
        cartDetail.setCategoryID(categoryID);

        // Kiểm tra màu sản phẩm đã tồn tại trong giỏ hàng hay chưa
        Map<String, CartDetailDto> details = cart.getDetails();
        String colorIDStr = String.valueOf(colorID);
        if (details.containsKey(colorIDStr)) {
            // Màu đã tồn tại trong giỏ hàng, cập nhật số lượng
            CartDetailDto existingDetail = details.get(colorIDStr);
            Integer existingQuantity = existingDetail.getQuantity();
            Integer newQuantity = existingQuantity + quantity;
            existingDetail.setQuantity(newQuantity);

            // Thêm cả 2 màu vào tên sản phẩm
            String existingColorHex = existingDetail.getColorhex();
            String newColorHex = product.getColors().stream()
                    .filter(c -> c.getColorID().equals(colorID))
                    .map(Product_Colors::getColorhex)
                    .findFirst()
                    .orElse(null);
            String updatedName = product.getName() + " (" + existingColorHex + ", " + newColorHex + ")";
            existingDetail.setName(updatedName);
        } else {
            // Màu chưa tồn tại trong giỏ hàng, tạo mới chi tiết giỏ hàng
            Product_Colors color = product.getColors().stream()
                    .filter(c -> c.getColorID().equals(colorID))
                    .findFirst()
                    .orElse(null);

            if (color == null) {
                throw new IllegalArgumentException("Màu sản phẩm không tồn tại");
            }

            cartDetail.setColorID(colorID);
            cartDetail.setColorhex(color.getColorhex());

            // Thêm màu vào tên sản phẩm
            String colorHex = color.getColorhex();
            String updatedName = product.getName() + " (" + colorHex + ")";
            cartDetail.setName(updatedName);

            details.put(colorIDStr, cartDetail);
        }

        return cartDetail;
    }


}



