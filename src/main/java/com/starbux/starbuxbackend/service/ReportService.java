package com.starbux.starbuxbackend.service;

import com.starbux.starbuxbackend.dto.AmountPerCustomerDto;
import com.starbux.starbuxbackend.dto.ToppingsPerDrinkDto;

import java.util.List;

public interface ReportService {
    List<AmountPerCustomerDto> amountPerCustomerReport();

    List<ToppingsPerDrinkDto> toppingsPerDrink();
}
