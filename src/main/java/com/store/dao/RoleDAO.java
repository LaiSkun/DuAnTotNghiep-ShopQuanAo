package com.store.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.model.Roles;

public interface RoleDAO extends JpaRepository<Roles, String> {
	Roles findByRoleID(String name);
}
