package com.store.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;


@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "order_details")
public class Order_Details {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long orderDetailID;
    Double price;
    Integer quantity;
    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productID")
    Products product;
    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderID")
    Orders order;
}
