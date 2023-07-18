package com.store.admin;

import java.awt.print.Pageable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.store.model.Users;
import com.store.service.UserService;

@CrossOrigin("*")
@Controller
public class AdminController {
	@Autowired
	UserService userService;
	
	@RequestMapping("/admin")
	public String admin(Model model) {
	    // Lấy thông tin xác thực của người dùng hiện tại
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    
	    // Kiểm tra xem người dùng có vai trò "admin" hay không
	    String isAdmin = authentication.getAuthorities().toString().toUpperCase();
	    System.out.println(isAdmin);
	    model.addAttribute("adminRole", isAdmin);

	    return "/admin/admin";
	}

	@RequestMapping("/admin/authorizing")
	public String author() {
		return "/admin/authorities/authorities";
	}

	@RequestMapping("/admin/order")
	public String adminOrder() {
		return "/admin/order/order";
	}

}
