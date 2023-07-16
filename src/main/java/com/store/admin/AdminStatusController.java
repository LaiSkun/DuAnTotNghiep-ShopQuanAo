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
import org.springframework.web.bind.annotation.RequestParam;

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
	public String adminStatus(Model model, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		
		
		List<Status> status = statusService.findAll();

		int totalItems = status.size();
		int totalPages = (int) Math.ceil(totalItems / (double) size);
		int startItem = page * size + 1;
		int endItem = Math.min((page + 1) * size, totalItems);

		List<Status> statusList = status.subList(page * size, endItem);

		model.addAttribute("statuss", statusList);
		model.addAttribute("status", new Status());
		model.addAttribute("currentPage", page + 1);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("startItem", startItem);
		model.addAttribute("endItem", endItem);
		
		
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
	public String edit(@PathVariable("id") long id, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, Model model) {
		Status status = statusService.findById(id);
		model.addAttribute("status", status);
		
		List<Status> list = statusService.findAll();
		model.addAttribute("statuss", list);
		
		
	    
	    int totalItems = list.size();
	    int totalPages = (int) Math.ceil(totalItems / (double) size);
	    int startItem = page * size + 1;
	    int endItem = Math.min((page + 1) * size, totalItems);
	    
	    List<Status> statusList = list.subList(page * size, endItem);
	    
	    model.addAttribute("statuss", statusList);
	    model.addAttribute("currentPage", page + 1);
	    model.addAttribute("totalPages", totalPages);
	    model.addAttribute("startItem", startItem);
	    model.addAttribute("endItem", endItem);
	    
		return "/admin/status/status";
	}
}
