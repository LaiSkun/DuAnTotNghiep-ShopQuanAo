package com.store.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Setter
@Getter
@Entity @Table(name = "product_color")
public class Product_Colors {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long colorID;
	String colorhex;
	int avaible;
	String color_name;
	@ManyToOne
	@JoinColumn(name = "productID")
	Products product;
	public Product_Colors(String colorhex, int avaible, String color_name, Products product) {
		this.colorhex = colorhex;
		this.color_name = color_name;
		this.product = product;
		this.avaible = avaible;
	}
	public Product_Colors() {

	}
}
