package com.store.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Data
@Getter
@Setter
@Entity @Table(name = "product_color")
public class Product_Colors {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer colorID;
	
	@Nationalized
	String color_hex;
	String color_name;
	
	@ManyToOne
	@JoinColumn(name = "productID")
	Products product;
	
	@OneToMany(mappedBy = "colorID")
	private List<Product_Images> images;
}
