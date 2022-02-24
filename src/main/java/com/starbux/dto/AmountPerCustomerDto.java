package com.starbux.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmountPerCustomerDto {
    private UserDto user;
    private BigDecimal amount;
}
