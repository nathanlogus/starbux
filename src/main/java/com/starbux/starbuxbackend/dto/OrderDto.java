package com.starbux.starbuxbackend.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderDto {
    private Long id;
    private Date orderDate;
    private BigDecimal originalTotal;
    private BigDecimal totalWithDiscount;
}
