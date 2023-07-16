package com.store.service.impl;





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
	public Status findById(Long id) {
		// TODO Auto-generated method stub
		return dao.findByStatusID(id);
	}

	@Override
	public Status update(Status status) {
		return dao.save(status);	
	}

	@Override
	public Status insert(Status status) {
		// TODO Auto-generated method stub
		return dao.saveAndFlush(status);
	}

	
}
