package com.starbux.starbuxbackend.dto;

import com.starbux.starbuxbackend.model.CartItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartDto {
    private Long id;
    private BigDecimal subtotal;
    private List<CartItem> cartItems;
}
