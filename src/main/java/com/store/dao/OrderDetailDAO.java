package com.store.dao;

import com.store.DTO.CartDetailDto;
import com.store.model.Order_Details;
import com.store.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailDAO extends JpaRepository<Order_Details, Long> {

    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO order_details(orderID,productID,colorID,price,quantity)"
            + "VALUES (:#{#dto.orderID}, :#{#dto.productID}, :#{#dto.colorID}, :#{#dto.price}, :#{#dto.quantity})",
            nativeQuery = true)
    void insert(@Param("dto") CartDetailDto cartDetailDto);

}
