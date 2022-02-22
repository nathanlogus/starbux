package com.starbux.controller;

import com.starbux.dto.AmountPerCustomerDto;
import com.starbux.dto.ToppingsPerDrinkDto;
import com.starbux.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/report")
@Slf4j
public class ReportController {
    @Autowired
    ReportService reportService;

    @GetMapping("/amount-per-customer")
    public ResponseEntity<List<AmountPerCustomerDto>> amountPerCustomerReport() {
        return ResponseEntity.ok(reportService.amountPerCustomerReport());
    }

    @GetMapping("/toppings")
    public ResponseEntity<List<ToppingsPerDrinkDto>> toppingsPerDrink() {
        return ResponseEntity.ok(reportService.toppingsPerDrink());
    }
}
