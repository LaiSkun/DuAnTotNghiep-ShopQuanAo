package com.store.service.impl;

<<<<<<< HEAD
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.dao.StatusDAO;
import com.store.model.Status;
import com.store.service.StatusService;

@Service
public class StatusServiceImpl implements StatusService {
	@Autowired
	StatusDAO dao;
	
	@Override
	public List<Status> findAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Override
	public Status findById(Integer id) {
		// TODO Auto-generated method stub
		return dao.findByStatusID(id);
	}

	@Override
	public Status update(Status status) {
		return dao.save(status);	
	}

=======
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
>>>>>>> 0b447236b566bd92ac6f04a17d9603daf007ae45
}
