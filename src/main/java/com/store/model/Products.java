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
@Data
@Getter
@Setter
@Entity @Table(name = "Products")
public class Products {
	@Id	
	String productID;
	String name;
	String img;
	Double price;
	@Temporal(TemporalType.DATE)
	@Column(name = "Createdate")
	Date createDate = new Date();
	String description;
	Integer available;
	Boolean deprecated;
	@ManyToOne
	@JoinColumn(name = "categoryID")
	Categories category;
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	List<Order_Details> orderDetails;	
	
	@OneToMany(mappedBy = "product")
	private List<Product_Colors> colors;
}
