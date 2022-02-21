package com.starbux.starbuxbackend.controller;

import com.starbux.starbuxbackend.dto.CartDto;
import com.starbux.starbuxbackend.dto.CartItemDto;
import com.starbux.starbuxbackend.dto.UserDto;
import com.starbux.starbuxbackend.model.Cart;
import com.starbux.starbuxbackend.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Slf4j
public class CartController {
    @Autowired
    CartService cartService;
    
    @GetMapping("/{userId}/carts")
    public ResponseEntity<List<CartDto>> getUserCarts(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getUserCarts(userId));
    }
    
    @PostMapping("/{userId}/carts")
    public ResponseEntity<CartDto> createUserCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.createCart(userId));
    } 

    @GetMapping("/{userId}/carts/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable Long userId, @PathVariable Long cartId) {
        return ResponseEntity.ok(cartService.getCart(userId, cartId));
    }

    @PostMapping("/{userId}/carts/{cartId}")
    public ResponseEntity<CartItemDto> addItemToCart(@PathVariable Long userId, @PathVariable Long cartId) {
        return null;
    }
    
}
