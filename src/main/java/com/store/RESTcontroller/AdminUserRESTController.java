package com.store.RESTcontroller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.store.model.Users;
import com.store.service.UserService;

@CrossOrigin("*")
@RestController

public class AdminUserRESTController {
	@Autowired
	UserService userService;

	@GetMapping
	public String list(Model model) {
		List<Users> list = userService.findAll();
		model.addAttribute("items", list);
		return "/admin/user";
	}
}
