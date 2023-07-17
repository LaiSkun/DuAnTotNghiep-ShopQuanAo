package com.store.api;

import com.store.constant.SessionConstant;
import com.store.DTO.CartDto;
import com.store.model.Orders;
import com.store.model.Users;
import com.store.service.CartService;
import com.store.service.OrdersService;
import com.store.service.UserService;
import com.store.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartApi {
	// /api/cart/update?productId =.....&quantity=....&isReplace=.....

	@Autowired
	private CartService cartService;


	@GetMapping("/update")
	public ResponseEntity<?> doGetUpdate(
			@RequestParam("productID") String productID,
			@RequestParam("colorID") Long colorID,
			@RequestParam("quantity") Integer quantity,
			@RequestParam("categoryID") String categoryID,
			@RequestParam("isReplace") Boolean isRePlace, HttpSession session) {
		
		
		CartDto currentCart = SessionUtil.getCurrenCart(session);
		cartService.updateCart(currentCart, productID,colorID ,quantity,categoryID, isRePlace);

		return ResponseEntity.ok(currentCart);
	}
	@GetMapping("/checkout")
	public ResponseEntity<?> doGetCheckout(@RequestParam("address") String address,
										   @RequestParam("phone") String phone,
										   @RequestParam("email") String email,
										   HttpSession session) {
		Users currentUser = (Users) session.getAttribute(SessionConstant.CURRENT_USER);
			CartDto currentCart = SessionUtil.getCurrenCart(session);
			try {
				cartService.insert(currentCart, currentUser, address, phone, email);
				session.setAttribute(SessionConstant.CURRENT_CART, new CartDto());
				return new ResponseEntity<>(HttpStatus.OK);
			} catch (Exception ex) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
	}



	

