package com.starbux.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private Long id;
    private Integer quantity;
    private BigDecimal price;
    private List<ProductDto> products;
}
