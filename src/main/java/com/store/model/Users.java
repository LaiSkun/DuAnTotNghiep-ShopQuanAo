package com.store.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "users")
public class Users implements Serializable{
	@Id
	String userID;
	String username;
	String password;
	String phone;
	String email;
	String address;
//	@Temporal(TemporalType.DATE)
//	@Column(name = "createdDate")
//	Date createdDate = new Date();
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	List<Orders> orders;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	List<Authorities> authorities;
}
