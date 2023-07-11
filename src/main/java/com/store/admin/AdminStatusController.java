package com.store.admin;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.store.model.Authorities;
import com.store.model.Roles;
import com.store.model.Status;
import com.store.model.Users;
import com.store.service.StatusService;

@CrossOrigin("*")
@Controller
public class AdminStatusController {
	@Autowired
	StatusService statusService;

	@GetMapping("/admin/status")
	public String adminStatus(Model model) {
		List<Status> list = statusService.findAll();
		model.addAttribute("statuss", list);
		model.addAttribute("status", new Status());
		return "/admin/status/status";
	}
	@GetMapping("/admin/status/new")
	public String getForm(Model model) {
		model.addAttribute("status", new Status());
		return "/admin/status/status";
	}

	@PostMapping("/admin/status/new")
	public String updateStatus(@ModelAttribute("status") Status status) {	
		
		statusService.update(status);
		
		return "redirect:/admin/status"; 
	}
	
	@RequestMapping("/admin/status/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		Status status = statusService.findById(id);
		model.addAttribute("status", status);
		
		
		
		
		List<Status> list = statusService.findAll();
		model.addAttribute("statuss", list);
		return "/admin/status/status";
	}
}
