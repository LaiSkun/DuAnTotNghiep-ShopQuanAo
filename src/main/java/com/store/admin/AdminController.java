package com.store.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
	@RequestMapping("/admin")
	public String admin() {
		return "/admin/admin";
	}
	@RequestMapping("/admin/user")
	public String adminUser() {
		return "/admin/user";
	}
	@RequestMapping("/admin/product")
	public String adminProduct() {
		return "/admin/product";
	}
	@RequestMapping("/admin/order")
	public String adminOrder() {
		return "/admin/order";
	}
}
