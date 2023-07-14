package com.store.dao;

import com.store.model.Orders;
import com.store.model.Products;
import com.store.model.Users;
import org.hibernate.criterion.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDAO extends JpaRepository<Orders, Long> {

    @Query(value = "SELECT * from orders where userID = :userID ",nativeQuery = true)
    List<Orders> findByUserID(String userID);

    Orders findByOrderID(Long OrderID);
}
