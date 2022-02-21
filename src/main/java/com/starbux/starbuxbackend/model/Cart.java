package com.starbux.starbuxbackend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name="CART")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;
    
    private BigDecimal subtotal;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cart", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference
    private List<CartItem> cartItems;

}
