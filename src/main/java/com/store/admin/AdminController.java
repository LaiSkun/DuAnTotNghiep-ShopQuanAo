package com.store.admin;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
	public String admin() {
		return "/admin/admin";
	}
	@RequestMapping("/admin/authorizing")
	public String author() {
		return "/admin/authorities/authorities";
	}
	@RequestMapping("/admin/product")
	public String adminProduct() {
		return "/admin/product/product";
	}

	@RequestMapping("/admin/order")
	public String adminOrder() {
		return "/admin/order/order";
	}
	@RequestMapping("/admin/status")
	public String adminStatus() {
		return "/admin/status/status";
	}
}
