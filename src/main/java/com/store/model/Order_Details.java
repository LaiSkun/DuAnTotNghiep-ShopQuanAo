package com.store.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@SuppressWarnings("serial")
@Getter
@Setter
@Entity
@Table(name = "order_details")
public class Order_Details {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long orderDetailID;
    Double price;
    Integer quantity;
    @ToString.Exclude
    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "productID")
    Products product;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "colorID")
    Product_Colors colorId;
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderID")
    Orders order;

}
