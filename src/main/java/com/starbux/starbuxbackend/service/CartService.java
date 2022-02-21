package com.starbux.starbuxbackend.service;

import com.starbux.starbuxbackend.dto.CartDto;
import com.starbux.starbuxbackend.dto.CartItemDto;
import com.starbux.starbuxbackend.model.Cart;

import java.util.List;

public interface CartService {
    public List<CartDto> getUserCarts(Long userId);
    public CartDto getCart(Long userId, Long cartId);
    public CartDto createCart(Long userId);
    public CartItemDto createCartItem(Long userId, Long cartId);
}