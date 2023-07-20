package com.store.controller;

import com.store.constant.SessionConstant;
import com.store.model.Authorities;
import com.store.model.Products;
import com.store.model.Users;

import com.store.service.ProductService;
import com.store.service.UserService;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
	@Autowired
	private ProductService productService;
	@Autowired
	private UserService userService;
	@Autowired
	private AuthenticationManagerBuilder authenticationManagerBuilder;
	
	private static final int MAX_SIZE = 4;



	@RequestMapping({ "/", "/home" })
	public String home(@RequestParam(value = "pageM", required = false, defaultValue = "1") int pageM,
			@RequestParam(value = "pageW", required = false, defaultValue = "1") int pageW, Model model) {
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

	@GetMapping("/login")
	public String doGetLogin(Model model) {
		model.addAttribute("userRequest", new Users());
		return "layout/login";
	}
	
	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String login(@RequestParam("userID") String username, @RequestParam("password") String password, @ModelAttribute("userRequest") Users userRequest, HttpSession session) throws Exception {
	    // Tạo đối tượng Authentication từ thông tin người dùng
	    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(
	        new UsernamePasswordAuthenticationToken(username, password)
	    );
	    // Xác thực thành công, lưu thông tin người dùng vào session
	    if (authentication.isAuthenticated()) {
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        Users userResponse = userService.doLogin(username, password);
	        session.setAttribute(SessionConstant.CURRENT_USER, userResponse);
	        
	        // Kiểm tra vai trò của người dùng và chuyển hướng đến trang tương ứng
	        List<Authorities> authoritiesList = userResponse.getAuthorities();
	        for (Authorities authorities : authoritiesList) {
	            String userRole = authorities.getRole().getRoleID();
	            if (userRole.equalsIgnoreCase("admin")||userRole.equalsIgnoreCase("staff")) {
	                return "redirect:/admin";
	            }
	        }
	        
	        return "redirect:/home";
	    } else {
	        throw new Exception("Invalid username or password");
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


	@GetMapping("/register")
	public String doGetRegister(Model model) {
		model.addAttribute("userRequest", new Users());
		return "layout/register";
	}

	@PostMapping("/register")
	public String goPostRegister(@ModelAttribute("userRequest") Users userRequest, HttpSession session, RedirectAttributes ra){
		Users userResponse = userService.save(userRequest, userRequest.getUserID());
		if(userResponse != null){
			session.setAttribute("currentUser", userResponse);
			return"redirect:/home";
		}else {
			ra.addFlashAttribute("message", "Username đã tồn tại !");
			return "redirect:/register";
		}
	}

}
