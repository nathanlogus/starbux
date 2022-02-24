package com.starbux.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToppingsPerDrinkDto {
    private ProductDto drink;
    private ProductDto mostUsedTopping;
}
