package com.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.model.Users;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersDAO extends JpaRepository<Users, String> {
    Users findByUserID(String userID);
}
