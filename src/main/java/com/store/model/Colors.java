package com.store.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Colors")
public class Colors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long IdColor;
    String NameColor;
    String RGBColor;
}
