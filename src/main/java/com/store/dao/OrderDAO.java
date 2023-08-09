package com.store.dao;

import com.store.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDAO extends JpaRepository<Orders, Long> {

    @Query(value = "SELECT * from orders where userID = :userID ",nativeQuery = true)
    List<Orders> findByUserID(String userID);
    @Query(value = "select * from orders Order by orderID desc", nativeQuery = true)
    List<Orders> findAllDESC();
    Orders findByOrderID(Long OrderID);
}
