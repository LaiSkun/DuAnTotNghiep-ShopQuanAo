package com.store.model;

import java.util.Date;
import java.util.List;

<<<<<<< HEAD
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
=======
import javax.persistence.*;
>>>>>>> 0b447236b566bd92ac6f04a17d9603daf007ae45

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@SuppressWarnings("serial")
@Data
@Entity 
@Table(name = "status")
public class Status {
<<<<<<< HEAD
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
=======

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long statusID;
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
>>>>>>> 0b447236b566bd92ac6f04a17d9603daf007ae45
	@JoinColumn(name = "orderID")
	private Orders orders;
}
