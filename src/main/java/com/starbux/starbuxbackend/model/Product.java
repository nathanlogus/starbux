package com.starbux.starbuxbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
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

    private BigDecimal price;

    private ProductType productType;
    
    private Currency currency;
    
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "products")
    @JsonBackReference
    private List<CartItem> cartItem;
}
