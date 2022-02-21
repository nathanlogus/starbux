package com.starbux.starbuxbackend.dto;

import com.starbux.starbuxbackend.model.User;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderDto {
    private Long id;
    private User user;
    private Date orderDate;
    private BigDecimal originalTotal;
    private BigDecimal totalWithDiscount;
}
