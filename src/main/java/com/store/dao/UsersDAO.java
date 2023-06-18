package com.store.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.store.model.Users;


public interface UsersDAO extends JpaRepository<Users, String> {
	@Query("SELECT DISTINCT ar.user FROM Authorities ar WHERE ar.role.id IN ('staff', 'admin','customer')")
	List<Users> getAdminitrators();
	
	List<Users> findByUsername(String username);
	
}
