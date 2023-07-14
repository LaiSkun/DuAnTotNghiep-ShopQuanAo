package com.store.model;

import java.util.Date;
import java.util.List;

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
	@JoinColumn(name = "orderID")
	Orders order;
}
