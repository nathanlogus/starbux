package com.starbux.service;

import com.starbux.dto.AmountPerCustomerDto;
import com.starbux.dto.ToppingsPerDrinkDto;

import java.util.List;

public interface ReportService {
    List<AmountPerCustomerDto> amountPerCustomerReport();

    List<ToppingsPerDrinkDto> toppingsPerDrink();
}
