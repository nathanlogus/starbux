package com.starbux.dto;

import com.starbux.model.ProductType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Currency;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private ProductType productType;
    private Currency currency;
}
