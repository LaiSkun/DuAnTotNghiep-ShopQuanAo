package com.store.dao;

import com.store.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDAO extends JpaRepository<Categories, String> {
}
