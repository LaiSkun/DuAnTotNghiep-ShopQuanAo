package com.store.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.dao.UsersDAO;
import com.store.model.Users;
import com.store.service.UserService;



@Service
public class UserServiceImpl implements UserService{
	@Autowired
	UsersDAO dao;

	@Override
	public List<Users> findAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Override
	public Users findById(String id) {
		// TODO Auto-generated method stub
		return dao.findById(id).get();
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
	
	
}
