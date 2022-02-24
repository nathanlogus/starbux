package com.starbux.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private Long id;
    private BigDecimal subtotal;
    private List<CartItemDto> cartItems;
}
