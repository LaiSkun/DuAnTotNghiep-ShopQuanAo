package com.store.dao;
import com.store.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDAO extends JpaRepository<Orders, Long> {

}
