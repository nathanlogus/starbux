package com.starbux.starbuxbackend.controller;

import com.starbux.starbuxbackend.dto.CartDto;
import com.starbux.starbuxbackend.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Slf4j
public class OrderController {
    @PostMapping("/{userId}/carts/{cartId}/order")
    public ResponseEntity<List<OrderDto>> createOrder(@PathVariable Long userId, @PathVariable Long cartId) {
        log.info("{} - {}", userId, cartId);
        return null;
    }
}
