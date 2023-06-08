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
@Entity @Table(name = "product_image")
public class Product_Images {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long imgID;
	String img_name;
	@ManyToOne
	@JoinColumn(name = "colorID")
	Product_Colors product_color;
}
