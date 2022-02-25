package com.starbux.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "PRODUCT")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private BigDecimal price;

    @NonNull
    private ProductType productType;

    @NonNull
    private Currency currency;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "products")
    @JsonBackReference
    private List<CartItem> cartItem;
}
