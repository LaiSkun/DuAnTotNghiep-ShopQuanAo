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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class Users implements Serializable{
	@Id
	@NotBlank(message = "Không để trống UserID")
	String userID;
	@NotBlank(message = "Không để trống UserName")
	String username;
	@NotBlank(message = "Không để trống Password")
	String password;
	@NotBlank(message = "Không để trống Phone")
	String phone;
	@NotBlank(message = "Không để trống Email")
	@Email(message = "Không đúng định dạng email")
	String email;
	@NotBlank(message = "Không để trống Address")
	String address;
	@Temporal(TemporalType.DATE)
	@Column(name = "createdDate")
	Date createDate = new Date();
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	List<Orders> orders;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	List<Authorities> authorities;
}
