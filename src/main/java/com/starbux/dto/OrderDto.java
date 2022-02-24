package com.starbux.dto;

import com.starbux.model.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private Date orderDate;
    private BigDecimal originalTotal;
    private BigDecimal totalWithDiscount;
    private Cart cart;
}
