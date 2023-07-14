package com.store.service.impl;

import com.store.dao.StatusDAO;
import com.store.model.Status;
import com.store.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusServiceImpl implements StatusService {
    @Autowired
    private StatusDAO statusDAO;


    @Override
    public Status insert(Status status) {
        return statusDAO.saveAndFlush(status);
    }
}
