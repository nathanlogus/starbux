package com.starbux.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartDto {
    private Long id;
    private BigDecimal subtotal;
    private List<CartItemDto> cartItems;
}
