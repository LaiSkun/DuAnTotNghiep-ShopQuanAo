package com.store.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.store.model.Status;

@Service
public interface StatusService {
	public List<Status> findAll();

	public Status findById(Long id);

	public Status update(Status status);

    Status insert(Status status);

}
