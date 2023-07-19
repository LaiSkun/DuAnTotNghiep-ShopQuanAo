package com.store.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.store.DTO.CartDto;
import com.store.constant.SessionConstant;
import com.store.model.Orders;
import com.store.model.Users;
import com.store.payment.PaypalPaymentIntent;
import com.store.payment.PaypalPaymentMethod;
import com.store.service.*;
import com.store.util.PaymentUtils;
import com.store.util.SessionUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
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

	public static final String URL_PAYPAL_SUCCESS = "pay/success";
	public static final String URL_PAYPAL_CANCEL = "pay/cancel";

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private PaypalService paypalService;

	@GetMapping("")
	public String dogetIndex(Model model) {
		return "layout/cart";
	}

	@PostMapping("/order")
	public String doOrder(Model model) {
		return "redirect:/cart";
	}

	@GetMapping("/update")
	public String doGetUpdate(@RequestParam("productID") String productID, @RequestParam("colorID") Long colorID,
			@RequestParam("quantity") Integer quantity, @RequestParam("categoryID") String categoryID,
			@RequestParam("isReplace") Boolean isRePlace, HttpSession session) {

		CartDto currentCart = SessionUtil.getCurrenCart(session);
		cartService.updateCart(currentCart, productID, colorID, quantity, categoryID, isRePlace);

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

	@GetMapping("/checkout")
	public String doOrder(Model model, HttpSession session, RedirectAttributes ra) {
		Users currentUser = (Users) session.getAttribute(SessionConstant.CURRENT_USER);
		if (!ObjectUtils.isEmpty(currentUser)) {
			return "layout/cartcheckout";
		}
		ra.addFlashAttribute("message", "Vui lòng đăng nhập trước khi thanh toán!");
		return "redirect:/cart";
	}

	@PostMapping("/pay")
	public String pay(@RequestParam("price") String price, HttpServletRequest request) {
		String cancelUrl = PaymentUtils.getBaseURL(request) + "/cart/" + URL_PAYPAL_CANCEL;
		String successUrl = PaymentUtils.getBaseURL(request) + "/cart/"  + URL_PAYPAL_SUCCESS;
		double exchangeRate = 23000;
		try {
			Payment payment = paypalService.createPayment(Double.valueOf(price)/exchangeRate, "USD", PaypalPaymentMethod.paypal,
					PaypalPaymentIntent.sale, "payment description", cancelUrl, successUrl);
			System.out.println(successUrl);
			for (Links links : payment.getLinks())
				if (links.getRel().equals("approval_url")) {
					//chuyen den trang paypal
					return "redirect:" + links.getHref();
					//xu ly success and cancel
				}
		} catch (PayPalRESTException e) {
			log.error(e.getMessage());
		}
		return "redirect:/";
	}

	@GetMapping(URL_PAYPAL_CANCEL)
	public String cancelPay() {
		return "layout/cancel";
	}

	@GetMapping(URL_PAYPAL_SUCCESS)
	public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
		try {
			
			Payment payment = paypalService.executePayment(paymentId, payerId);
			if (payment.getState().equals("approved"))
				return "redirect:/";
		} catch (PayPalRESTException e) {
			log.error(e.getMessage());
		}
		return "redirect:/cart";
	}
}
