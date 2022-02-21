package com.starbux.starbuxbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Currency;
import java.util.List;

@Data
@Entity
@Table(name="PRODUCT")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double price;

    private ProductType productType;
    
    private Currency currency;
    
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "products")
    @JsonBackReference
    @JsonManagedReference
    private List<CartItem> cartItem;
}
