package com.store.dao;

import com.store.model.Product_Colors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductColorDAO extends JpaRepository<Product_Colors, Integer> {

	

	


		
}
