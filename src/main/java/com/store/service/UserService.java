package com.store.service;

import java.util.List;

import com.store.model.Users;

public interface UserService {
	public List<Users> findAll();

	public Users findById(String id);

	public Users create(Users product);

	public Users update(Users product);

	public void delete(String id);
	
	public List<Users> getAdministrators();

	public List<Users> searchUsers(String keyword);

	public List<Users> findByAuthorities_Role_RoleID(String roleId);


	Users doLogin(String userID, String checkpassword);
}
