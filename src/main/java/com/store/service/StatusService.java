package com.store.service;

<<<<<<< HEAD
import java.util.List;

import org.springframework.stereotype.Service;

import com.store.model.Status;

@Service
public interface StatusService {
	public List<Status> findAll();

	public Status findById(Integer id);

	public Status update(Status status);
=======
import com.store.model.Orders;
import com.store.model.Status;

public interface StatusService {
    Status insert(Status status);


>>>>>>> 0b447236b566bd92ac6f04a17d9603daf007ae45
}
