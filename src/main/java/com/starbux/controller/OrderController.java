package com.starbux.controller;

import com.starbux.dto.OrderDto;
import com.starbux.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Slf4j
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("/{userId}/carts/{cartId}/order")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long userId, @PathVariable Long cartId) {
        return ResponseEntity.ok(orderService.getOrder(userId, cartId));
    }

    @PostMapping("/{userId}/carts/{cartId}/order")
    public ResponseEntity<OrderDto> createOrder(@PathVariable Long userId, @PathVariable Long cartId) {
        return ResponseEntity.ok(orderService.createOrder(userId, cartId));
    }
}