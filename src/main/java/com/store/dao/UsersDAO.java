package com.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.model.Users;


public interface UsersDAO extends JpaRepository<Users, String> {

}
