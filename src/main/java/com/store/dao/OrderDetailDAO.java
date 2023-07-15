package com.store.dao;

import com.store.model.Order_Details;
import com.store.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderDetailDAO extends JpaRepository<Order_Details, Long> {
    List<Order_Details> findByOrder_OrderID(long id);
}
