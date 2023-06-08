package com.store.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;


@SuppressWarnings("serial")
@Data
@Entity 
@Table(name = "order_detalis")
public class Order_Details {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long orderDetailsID;
	Double price;
	Integer quantity;
	@ManyToOne
	@JoinColumn(name = "productID")
	Products product;
	@ManyToOne
	@JoinColumn(name = "orderID")
	Orders order;
}
