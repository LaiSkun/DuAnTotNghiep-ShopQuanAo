package com.store.controller;

import com.store.constant.SessionConstant;
import com.store.model.Product_Colors;
import com.store.model.Product_Images;
import com.store.model.Products;
import com.store.model.Users;
import com.store.service.ProductColorService;
import com.store.service.ProductImgService;
import com.store.service.ProductService;
import com.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
	@Autowired
	private ProductService productService;
	@Autowired
	private UserService userService;
	@Autowired
	private ProductImgService imgService;
	
	@RequestMapping({"/", "/home"})
	public String home(Model model) {
		List<Products> products = productService.findAll();
		model.addAttribute("products", products);
		return "/layout/home";
	}


	@RequestMapping({"/productgird"})
	public String productGird(Model model) {
		List<Products> products = productService.findAll();
		model.addAttribute("products", products);
		return "/layout/productgird";
	}

	@RequestMapping({"/productMen"})
	public String productMen(Model model) {
		List<Products> products = productService.findMen();
		model.addAttribute("productMen", products);
		return "/layout/productMen";
	}

	@RequestMapping({"/productWomen"})
	public String productWomen(Model model) {
		List<Products> products = productService.findWomen();
		model.addAttribute("productWomen", products);
		return "/layout/productWomen";
	}

	@RequestMapping("/product/{productID}")
	public String productID(@PathVariable("productID") String productID, Model model) {
		Products product = productService.findByProductID(productID);
		if (product == null) {
			return "redirect:/home";
		} else {
			Product_Colors color = product.getColors().get(0);
			model.addAttribute("colors", color);
			model.addAttribute("product", product);
			model.addAttribute("productID", productID);
		}
		List<Products> products = productService.findAll();
		model.addAttribute("products", products);
		return "layout/productDetails";
	}
	
	@RequestMapping( "/productlist")
	public String productlist(Model model) {
		List<Products> products = productService.findAll();
		model.addAttribute("products", products);
		return "/layout/productlist";
	}

	@RequestMapping ("/login")
	public String doGetLogin(Model model) {
		model.addAttribute("userRequest", new Users());
		return "layout/login";
	}

	@PostMapping("/login")
	public String doPostLogin(@ModelAttribute("userRequest") Users userRequest, HttpSession session){
		Users userResponse = userService.doLogin(userRequest.getUserID(), userRequest.getPassword());
		if (userResponse != null){
			session.setAttribute(SessionConstant.CURRENT_USER, userResponse);
			return "redirect:/home";
		} else {
			return "redirect:/login";
		}
	}
	@RequestMapping ("/logout")
	public String doGetLogout(HttpSession session){
		session.removeAttribute(SessionConstant.CURRENT_USER);
		return "redirect:/home";
	}
}
