package com.store.dao;

import com.store.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusDAO extends JpaRepository<Status, String> {
}
