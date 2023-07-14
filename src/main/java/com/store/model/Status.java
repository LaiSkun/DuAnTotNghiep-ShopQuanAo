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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity 
@Table(name = "status")
public class Status {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer statusID;
	String statusname;
	String reason;
	String notes;
	Date dateComplete = new Date();
	Double transportFee;
	Double feeCollected;
	
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderID")
	private Orders orders;
}
