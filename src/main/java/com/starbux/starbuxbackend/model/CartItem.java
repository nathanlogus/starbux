package com.starbux.starbuxbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name="CART_ITEM")
public class CartItem {
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id", insertable = true, updatable = false)
    @JsonManagedReference
    private Cart cart;
    
    private Integer quantity;
    
    private BigDecimal price;

    @ManyToMany
    @JoinTable(
            name = "item_products",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    @JsonBackReference
    @JsonManagedReference
    private List<Product> products;
}
