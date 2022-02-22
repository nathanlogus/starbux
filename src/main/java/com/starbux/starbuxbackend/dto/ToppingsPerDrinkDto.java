package com.starbux.starbuxbackend.dto;

import lombok.Data;

@Data
public class ToppingsPerDrinkDto {
    private ProductDto drink;
    private ProductDto mostUsedTopping;
}
