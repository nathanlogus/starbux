package com.starbux.starbuxbackend.dto;

import com.starbux.starbuxbackend.model.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartItemDto {
    private Long id;
    private Integer quantity;
    private BigDecimal price;
    private List<Product> products;
}
