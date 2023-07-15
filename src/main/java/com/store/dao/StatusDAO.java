package com.store.dao;

<<<<<<< HEAD
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.store.model.Status;

public interface StatusDAO extends JpaRepository<Status, Integer>{
	Status findByStatusID(Integer statusID);
=======
import com.store.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusDAO extends JpaRepository<Status, String> {
>>>>>>> 0b447236b566bd92ac6f04a17d9603daf007ae45
}
