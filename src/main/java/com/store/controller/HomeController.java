package com.store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController {
	@RequestMapping({"/","/home"})
	public String home() {
		return "/layout/home";
	}
	@RequestMapping(value = "/index")
	public String index() {
		return "/Admin/index" ;
	}
	@RequestMapping(value = "/Oder")
	public String Oder() {
		return "/Admin/Oder" ;
	}
	@RequestMapping(value = "/Product")
	public String Product() {
		return "/Admin/Product" ;
	}
	@RequestMapping(value = "/Users")
	public String Users() {
		return "/Admin/Users" ;
	}
}
