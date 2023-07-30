package com.store.service.impl;

import java.time.YearMonth;
import java.util.List;

import com.store.dao.ProductDAO;
import com.store.model.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.dao.StatisticDAO;
import com.store.service.StatisticService;

@Service
public class StatisticServiceImpl implements StatisticService {
	@Autowired
	private StatisticDAO dao;
	@Autowired
	private ProductDAO productDAO;
	@Override
	public String[][] getTotalPriceMonth() {
		String[][] result = new String[2][6];
		YearMonth currentTime = YearMonth.now();
		for (int i = 0; i < 6; i++) {
			String month = currentTime.minusMonths((long) i).getMonthValue() + "";
			String year = currentTime.minusMonths((long) i).getYear() + "";
			result[0][5 - i] = month + "-" + year;
			result[1][5 - i] = dao.getTotalPriceMonth(month, year);
		}
		return result;
	}

	@Override
	public String[][] getProductTotal() {
		YearMonth currentTime = YearMonth.now();
		String month = currentTime.getMonthValue() + "";
		String year = currentTime.getYear() + "";
		List<String> productSoLuong = dao.getProductTotal(month,year);
		String[][] productToTal = new String[2][productSoLuong.size()+1];
		for (int i = productSoLuong.size(); i > 0; i--) {

			String[] output = productSoLuong.get(i-1).split("\\,");
			Products products = productDAO.findByProductID(output[0]);
			productToTal[0][i-1] = products.getName();
			productToTal[1][i-1] = output[1];
		}
			productToTal[0][productSoLuong.size()] = "0";
			productToTal[1][productSoLuong.size()] = "0";
		return productToTal;
	}


}
