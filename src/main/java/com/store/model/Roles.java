package com.store.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "roles")
public class Roles {
	@Id
	private String roleID;
	private String rolename;
	@JsonIgnore
	@OneToMany(mappedBy = "role")
	List<Authorities> authorities;
}
