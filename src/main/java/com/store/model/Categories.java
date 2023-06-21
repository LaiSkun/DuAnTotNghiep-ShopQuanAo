package com.store.model;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "categories")
public class Categories {
    @Id
    String categoryID;
    String name;
    @JsonIgnore
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    List<Products> products;
}
