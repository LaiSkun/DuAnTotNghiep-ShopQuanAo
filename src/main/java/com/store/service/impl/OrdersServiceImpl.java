package com.store.service.impl;

import com.store.dao.OrderDAO;
import com.store.model.Orders;
import com.store.model.Users;
import com.store.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrderDAO dao;


    @Override
    public Orders insert(Orders orders) {
        return dao.saveAndFlush(orders);
    }

    @Override
    public List<Orders> findByUserID(String userId) {
        return dao.findByUserID(userId);
    }

    @Override
    public List<Orders> findByAll() {
        return dao.findAll();
    }

    @Override
    public Orders findById(Long OrderID) {
        return dao.findByOrderID(OrderID);
    }
}
