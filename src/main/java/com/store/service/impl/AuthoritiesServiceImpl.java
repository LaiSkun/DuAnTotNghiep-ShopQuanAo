package com.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.store.dao.AuthoritiesDAO;
import com.store.dao.UsersDAO;
import com.store.model.Authorities;
import com.store.model.Users;
import com.store.service.AuthoritiesService;

@Service
public class AuthoritiesServiceImpl implements AuthoritiesService  {
	@Autowired
	AuthoritiesDAO dao;
	@Autowired
	UsersDAO usdao;
	
	public List<Authorities> findAuthoritiesOfAdministrators() {
		List<Users> accounts = usdao.getAdminitrators();
		return dao.authoritiesOf(accounts);
	}

	@Override
	public Authorities create(Authorities auth) {
		return dao.save(auth);
	}

	@Override
	public List<Authorities> findByUserID(String userID) {
		return dao.findByUserUserID(userID);
	}

	@Override
	public void delete(Integer id) {
		dao.deleteById(id);
	}

	@Override
	public Authorities createFromUser(Users user) {
		// TODO Auto-generated method stub
		return null;
	}


}
