package com.store.admin;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import com.store.dao.StatisticDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.store.service.StatisticService;

@Controller
public class AdminStatisticController {
	
	@Autowired
	StatisticService service;
	@Autowired
	StatisticDAO statisticDAO;
	@GetMapping("/admin/statistic")
	public String doGetSalesGragh(Model model) {
		String [][] chartData = service.getTotalPriceMonth();
		model.addAttribute("chartData", chartData);
		String [][] productTotal = service.getProductTotal();
		model.addAttribute("productTotal", productTotal);
		return "admin/statistic/statistic";
	}
}
