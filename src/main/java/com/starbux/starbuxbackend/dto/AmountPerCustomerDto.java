package com.starbux.starbuxbackend.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AmountPerCustomerDto {
    private UserDto user;
    private BigDecimal amount;
}
