package com.store.service.impl;

import com.store.dao.ProductDAO;
import com.store.model.Products;
import com.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public class ProductsServiceImpl implements ProductService {
	@Autowired
	private ProductDAO productDAO;
	@Override
	@Transactional(rollbackOn = {Exception.class, Throwable.class})
	public void deleteLogical(String productID) {
		productDAO.updateLocal(productID);
	}

	@Override
	public void deleteProduct(String productID) {
		productDAO.deleteProductsByProductID(productID);
	}

	@Override
	@Transactional(rollbackOn = {Exception.class, Throwable.class})
	public Products save(Products product)throws SQLException{
		System.out.println(product.getCreateDate());
	return productDAO.saveAndFlush(product);
	}

	@Override
	public List<Products> findDeprecatedTrue() {
		return productDAO.findByDeprecated(Boolean.FALSE);
	}
	@Override
	public List<Products> findDeprecatedFalse() {
		return productDAO.findByDeprecated(Boolean.TRUE);
	}

	@Override
	public Optional<Products> findByID(String productID) {
		return productDAO.findById(productID);
	}

	@Override
	public Products findProducts(String name) {
	return productDAO.findByProductID(name);
	}



	@Override
	public Page<Products> findAll(int pageSize, int pageNumber) throws Exception {
		if(pageNumber >= 1) {
			return productDAO.findAll(PageRequest.of(pageNumber - 1, pageSize));
		}else{
			throw  new Exception("page false ");
		}
	}
	public Page<Products> findPaginated(Pageable pageable) {
		List<Products> products = productDAO.findAll();
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<Products> list;

		if (products.size() < startItem) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, products.size());
			list = products.subList(startItem, toIndex);
		}
		return new PageImpl<Products>(list, PageRequest.of(currentPage, pageSize), products.size());
	}
}
