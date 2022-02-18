package com.starbux.starbuxbackend.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Currency;

@Data
@Entity
@Table(name="PRODUCT")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private ProductType productType;

    private Double price;

    private Currency currency;
}
