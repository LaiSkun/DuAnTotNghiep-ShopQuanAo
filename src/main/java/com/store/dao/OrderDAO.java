package com.store.dao;

import com.store.model.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDAO extends JpaRepository<Orders, Long> {

    @Query(value = "SELECT * from orders where userID = :userID Order BY orderID desc ",nativeQuery = true)
    Page<Orders> findByUserID(String userID, Pageable pageable);

    @Query(value = "select * from orders Order by orderID desc", nativeQuery = true)
    List<Orders> findAllDESC();
    @Query(value = "SELECT cast(datepart(month,Date) as varchar)+'-'+cast(datepart(yyyy,Date) as varchar) as MonthAndYear\n" +
            "FROM orders group by  cast(datepart(MONTH,Date) as varchar)+'-'+cast(datepart(yyyy,Date) as varchar)", nativeQuery = true)
    List<String> findDateAnDCountMonth();
    @Query(value = "select orders.orderID from orders inner join status on status.orderID = orders.orderID " +
            "where MONTH(orders.date) = :Month and status.description = '5' order by orderID",nativeQuery = true)
    List<Long> findOrderByMonthAndDone(@Param("Month")String month);
    Orders findByOrderID(Long OrderID);
}
