package com.starbux.starbuxbackend.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartItemDto {
    private Long id;
    private Integer quantity;
    private BigDecimal price;
    private List<ProductDto> products;
}
