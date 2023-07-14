package com.store.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.store.model.Authorities;
import com.store.model.Roles;
import com.store.model.Users;
import com.store.service.AuthoritiesService;
import com.store.service.RoleService;
import com.store.service.UserService;

@CrossOrigin("*")
@Controller
public class AdminUserController {
	@Autowired
	UserService userService;
	@Autowired 
	AuthoritiesService authoritiesService;
	@Autowired
	RoleService roleService;
	
	
	@GetMapping("/admin/user")
	public String adminUser(Model model) {
		List<Users> list = userService.findAll();
		model.addAttribute("users", list);
		model.addAttribute("user", new Users());
		return "/admin/user/user";
	}

	@GetMapping("/admin/user/new")
	public String createUserForm(Model model) {
		
		model.addAttribute("user", new Users());
		return "/admin/user/user";
	}

	@PostMapping("/admin/user/new")
	public String createUser(Model model ,@Validated @ModelAttribute("user") Users user,Errors errors) {	
		if(errors.hasErrors()) {
			model.addAttribute("message", "Vui lòng sửa các lỗi sau: ");
			List<Users> list = userService.findAll();
			model.addAttribute("users", list);
			return "/admin/user/user";
		}
		Roles customerRole = roleService.findByRoleID("customer");
		Authorities authorities = new Authorities();
		authorities.setUser(user);
		authorities.setRole(customerRole);
		userService.create(user);
		authoritiesService.create(authorities);
		//Cập nhật quyền customer khi tạo user mới
		return "redirect:/admin/user"; // Chuyển hướng đến trang đã cập nhật user mới
	}

	@GetMapping("/admin/delete/{id}")
	public String delete(@PathVariable("id") String id) {
		userService .delete(id);
		return "redirect:/admin/user";
	}
	@GetMapping("/admin/search")
    public String searchUsers(@RequestParam("keyword") String keyword, Model model) {
        List<Users> users = userService.searchUsers(keyword);
        model.addAttribute("users", users);
        model.addAttribute("user", new Users());
        return "/admin/user/user";
    }
	@RequestMapping("/admin/edit/{id}")
	public String edit(@PathVariable("id") String id, Model model) {
		Users user = userService.findById(id);
		model.addAttribute("user", user);
		List<Users> list = userService.findAll();
		model.addAttribute("users", list);
		return "admin/user/user";
	}
	@PostMapping("/admin/update")
	public String update(Users user) {
		userService.create(user);
		return "redirect:/admin/edit/" + user.getUserID();
	}
	
}
