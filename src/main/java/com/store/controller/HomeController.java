package com.store.controller;

import com.store.constant.SessionConstant;
import com.store.model.Products;
import com.store.model.Users;
import com.store.service.ProductService;
import com.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
	@Autowired
	private ProductService productService;
	@Autowired
	private UserService userService;

	private static final int MAX_SIZE =4;

	@RequestMapping({"/", "/home"})
	public String home( @RequestParam(value = "pageM", required = false, defaultValue = "1") int pageM,
						@RequestParam(value = "pageW", required = false, defaultValue = "1") int pageW,
						Model model) {
		List<Products> productM = new ArrayList<>();
		try {
			Page<Products> pageProductM = productService.findMen(MAX_SIZE, pageM);
			productM = pageProductM.getContent();
			model.addAttribute("totalPagesM", pageProductM.getTotalPages());
			model.addAttribute("currentPageM", pageM);
		} catch (Exception e) {
			productM = productService.findAll();
		}
		model.addAttribute("productM", productM);
		List<Products> productW = new ArrayList<>();
		try {
			Page<Products> pageProduct = productService.findWomen(MAX_SIZE, pageW);
			productW = pageProduct.getContent();
			model.addAttribute("totalPagesW", pageProduct.getTotalPages());
			model.addAttribute("currentPageW", pageW);
		} catch (Exception e) {
			productW = productService.findAll();
		}

		model.addAttribute("productW", productW);
		return "/layout/home";
	}


	@RequestMapping("/login")
	public String doGetLogin(Model model) {
		model.addAttribute("userRequest", new Users());
		return "layout/login";
	}

	@PostMapping("/login")
	public String doPostLogin(@ModelAttribute("userRequest") Users userRequest, HttpSession session) {
		Users userResponse = userService.doLogin(userRequest.getUserID(), userRequest.getPassword());
		if (userResponse != null) {
			session.setAttribute(SessionConstant.CURRENT_USER, userResponse);
			return "redirect:/home";
		} else {
			return "redirect:/login";
		}
	}

	@RequestMapping("/logout")
	public String doGetLogout(HttpSession session) {
		session.removeAttribute(SessionConstant.CURRENT_USER);
		return "redirect:/home";
	}
	@RequestMapping("/contact")
	public String doGetContact() {
		return "layout/contact";
	}
}
