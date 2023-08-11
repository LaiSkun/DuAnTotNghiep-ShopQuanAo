package com.store.service.impl;





import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.store.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.dao.StatusDAO;
import com.store.service.StatusService;

import javax.transaction.Transactional;

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
	@Transactional(rollbackOn = {Exception.class, Throwable.class})
	public Status updateStatus(Status status)  {
		return dao.saveAndFlush(status);
	}
	@Override
	public Status updateDesAndDate(Status status) {
		return dao.save(status);
	}


	@Override
	public Status insert(Status status) {
		// TODO Auto-generated method stub
		return dao.saveAndFlush(status);
	}

	@Override
	public Status findByOrderID(Orders order) {
		return dao.findByOrders(order);
	}

	@Override
	public List<Status> findByCurrentStaff(staff st) {
		return dao.findByStaffIDDESC(st);
	}

	@Override
	public List<Status> findByStatusAndDesc(staff st, long desc) {
		return dao.findByStaffAndStatusName(st,desc);
	}

	@Override
	public List<Status> findAllDesc(long desc) {
		return dao.findAllDesc(desc);
	}

	@Override
	public List<Status> findAllDesc2(long desc, long desc2) {
		return dao.findAlldesc2(desc, desc2);
	}

	@Override
	public List<Status> findByStatusAndDesc1(staff st, long desc, long desc1) {
		return dao.findByStaffAndStatusName2(st,desc,desc1);
	}


}
