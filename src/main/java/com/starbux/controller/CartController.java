package com.starbux.controller;

import com.starbux.dto.CartDto;
import com.starbux.dto.CartItemDto;
import com.starbux.mapper.CartMapper;
import com.starbux.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Slf4j
public class CartController {
    @Autowired
    CartService cartService;

    @Autowired
    CartMapper cartMapper;

    @GetMapping("/{userId}/carts")
    public ResponseEntity<List<CartDto>> getUserCarts(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getUserCarts(userId).stream().map(cart -> cartMapper.cartDtoFromCart(cart)).collect(Collectors.toList()));
    }

    @PostMapping("/{userId}/carts")
    public ResponseEntity<CartDto> createUserCart(@PathVariable Long userId) {
        return new ResponseEntity(cartMapper.cartDtoFromCart(cartService.createCart(userId)), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/carts/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable Long userId, @PathVariable Long cartId) {
        return ResponseEntity.ok(cartMapper.cartDtoFromCart(cartService.getCart(userId, cartId)));
    }

    @PostMapping("/{userId}/carts/{cartId}")
    public ResponseEntity<CartItemDto> addItemToCart(@PathVariable Long userId, @PathVariable Long cartId) {
        return new ResponseEntity(cartMapper.cartItemDtoFromCartItem(cartService.createCartItem(userId, cartId)), HttpStatus.CREATED);
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
        return new ResponseEntity(cartMapper.cartItemDtoFromCartItem(cartService.addProductToCartItem(userId, cartId, cartItemId, productId)), HttpStatus.CREATED);
    }

    @PutMapping("/{userId}/carts/{cartId}/{cartItemId}")
    public ResponseEntity<CartItemDto> updateCartItemQuantity(@PathVariable Long userId, @PathVariable Long cartId,
                                                              @PathVariable Long cartItemId, @RequestParam(required = true) Integer quantity) {
        return ResponseEntity.ok(cartMapper.cartItemDtoFromCartItem(cartService.updateCartItemQuantity(userId, cartId, cartItemId, quantity)));
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
