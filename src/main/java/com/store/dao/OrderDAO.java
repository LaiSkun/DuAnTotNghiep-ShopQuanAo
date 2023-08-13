package com.store.dao;

import com.store.model.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDAO extends JpaRepository<Orders, Long> {

    @Query(value = "SELECT * from orders where userID = :userID Order BY orderID desc ",nativeQuery = true)
    Page<Orders> findByUserID(String userID, Pageable pageable);

    @Query(value = "select * from orders Order by orderID desc", nativeQuery = true)
    List<Orders> findAllDESC();
    Orders findByOrderID(Long OrderID);
}
