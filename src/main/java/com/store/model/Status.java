package com.store.model;

import java.util.Date;
import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import javax.persistence.*;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@SuppressWarnings("serial")
@Data
@Entity 
@Table(name = "status")
public class Status {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long statusID;
	String statusname;
	String reason;
	String description;
	String notes;
	@Column(name = "dateComplete")
	@DateTimeFormat(pattern = "dd-mm-yyyy")
	Date createDate ;
	Double transportFee;
	Double feeCollected;
	@ManyToOne
	@JoinColumn(name = "orderID")
	private Orders orders;
}
