package com.store.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.store.dao.UsersDAO;
import com.store.model.Authorities;
import com.store.model.Users;
import com.store.service.UserService;



@Service
public class UserServiceImpl implements UserService{
	@Autowired
	UsersDAO dao;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Users findById(String id) {
		// TODO Auto-generated method stub
		return dao.findByUserID(id);
	}

	@Override
	public Users create(Users user) {
		// TODO Auto-generated method stub
		return dao.save(user);
	}

	@Override
	public Users update(Users user) {
		// TODO Auto-generated method stub
		return dao.save(user);
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		dao.deleteById(id);
	}

	@Override
	public List<Users> getAdministrators() {
		// TODO Auto-generated method stub
		return dao.getAdminitrators();
	}

	@Override
	public List<Users> searchUsers(String keyword) {
		// TODO Auto-generated method stub
		return dao.findByUsernameContaining(keyword);
	}

	@Override
	public List<Users> findByAuthorities_Role_RoleID(String roleId) {
		// TODO Auto-generated method stub
		return dao.findByAuthorities_Role_RoleID(roleId);
	}
	@Override
	public Users doLogin(String userID, String checkpassword) {
	    Users user = dao.findByUserID(userID);
	    if (null != user) {
	        String password = user.getPassword();        
	        boolean check = passwordEncoder.matches(checkpassword, password);
	        if (check) {
	            List<Authorities> authoritiesList = user.getAuthorities();
	            for (Authorities authorities : authoritiesList) {
	                String userRole = authorities.getRole().getRoleID();
	                if (userRole.equalsIgnoreCase("admin")) {
	                    return user; // Trả về người dùng nếu có vai trò admin
	                }
	            }
	        }
	    }
	    return user; // Trả về null nếu không xác thực thành công hoặc không có vai trò admin
	}


	@Override
	public List<Users> findAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}


}
