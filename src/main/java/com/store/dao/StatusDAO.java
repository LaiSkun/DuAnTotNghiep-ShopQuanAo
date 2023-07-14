package com.store.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.store.model.Status;

public interface StatusDAO extends JpaRepository<Status, Integer>{
	Status findByStatusID(Integer statusID);
}
