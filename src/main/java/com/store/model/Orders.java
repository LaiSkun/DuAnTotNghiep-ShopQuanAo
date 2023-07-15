package com.store.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@SuppressWarnings("serial")
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long orderID;
	String address;
	String phone;
	String email;
	Double price;
	@Temporal(TemporalType.DATE)
	@Column(name = "date")
	Date createDate = new Date();
	@ManyToOne
	@JoinColumn(name = "userID")
	Users user;
	
	@JsonIgnore
	@OneToMany(mappedBy = "order")
	List<Order_Details> orderDetails;
}
