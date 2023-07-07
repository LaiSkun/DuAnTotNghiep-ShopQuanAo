package com.store.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "products")
public class Products {
    @Id
    String productID;
    String name;
    String img;
    Double price;
    @Column(name = "Createdate")
    @DateTimeFormat(pattern = "dd-mm-yyyy")
    Date createDate ;
    int available;
    boolean deprecated;
    String description;
    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryID")
    Categories category;
    @JsonIgnore
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    List<Order_Details> orderDetails;
    @OneToMany(mappedBy = "product")
    private List<Product_Colors> colors;
    public Products(String productID, String name, String img, Double price, Date createDate, int available, boolean deprecated, String description, Categories category, List<Order_Details> orderDetails) {
        this.productID = productID;
        this.name = name;
        this.img = img;
        this.price = price;
        this.createDate = createDate;
        this.available = available;
        this.deprecated = deprecated;
        this.description = description;
        this.category = category;
        this.orderDetails = orderDetails;
    }
    public Products() {
    }

}
