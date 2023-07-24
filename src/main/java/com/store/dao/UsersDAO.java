package com.store.dao;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.store.model.Users;


public interface UsersDAO extends JpaRepository<Users, String> {
	@Query("SELECT DISTINCT ar.user FROM Authorities ar WHERE ar.role.id IN ('staff', 'admin','customer')")
	List<Users> getAdminitrators();
	
	@Query("Select user from Users "
			+ "where userID like 'keyword' or username like 'keyword' or email like 'keyword' ")
	List<Users> findByKeyword(String keyword);
	
	List<Users> findByUsernameContaining(String keyword);
	
	
	List<Users> findByAuthorities_Role_RoleID(String roleID);
	
	Users findByUserID(String userID);
	


	boolean existsByEmail(String email);
	

	Users findByEmail(String email);
     
    Users findByResetPasswordToken(String token);



}
