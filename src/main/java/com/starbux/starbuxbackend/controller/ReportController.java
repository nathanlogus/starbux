package com.starbux.starbuxbackend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/report")
@Slf4j
public class ReportController {
    @GetMapping("/amount-per-customer")
    public void amountPerCustomerReport(@RequestParam(required = false) Long userId) {
    }

    @GetMapping("/toppings")
    public void mostUseDToppings() {
    }
}
