package com.starbux.starbuxbackend.controller;

import com.starbux.starbuxbackend.dto.CartDto;
import com.starbux.starbuxbackend.dto.CartItemDto;
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
        return ResponseEntity.ok(cartService.createCartItem(userId, cartId));
    }

    @DeleteMapping("/{userId}/carts/{cartId}")
    public ResponseEntity deleteItemFromCart(@PathVariable Long userId, @PathVariable Long cartId, @RequestParam(required = true) Long cartItemId) {
        boolean isRemoved = cartService.deleteCartItem(userId, cartId, cartItemId);
        if (isRemoved) {
            return new ResponseEntity("Succesfully removed cart item!", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{userId}/carts/{cartId}/{cartItemId}")
    public ResponseEntity<CartItemDto> addProductToCartItem(@PathVariable Long userId, @PathVariable Long cartId,
                                                            @PathVariable Long cartItemId, @RequestParam(required = true) Long productId) {
        return ResponseEntity.ok(cartService.addProductToCartItem(userId, cartId, cartItemId, productId));
    }

    @PutMapping("/{userId}/carts/{cartId}/{cartItemId}")
    public ResponseEntity<CartItemDto> updateCartItemQuantity(@PathVariable Long userId, @PathVariable Long cartId,
                                                              @PathVariable Long cartItemId, @RequestParam(required = true) Integer quantity) {
        return ResponseEntity.ok(cartService.updateCartItemQuantity(userId, cartId, cartItemId, quantity));
    }

    @DeleteMapping("/{userId}/carts/{cartId}/{cartItemId}")
    public ResponseEntity removeProductFromCartItem(@PathVariable Long userId, @PathVariable Long cartId,
                                                    @PathVariable Long cartItemId, @RequestParam(required = true) Long productId) {
        boolean isRemoved = cartService.removeProductFromCartItem(userId, cartId, cartItemId, productId);
        if (isRemoved) {
            return new ResponseEntity("Succesfully removed product from cart item!", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
