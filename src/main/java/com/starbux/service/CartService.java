package com.starbux.service;

import com.starbux.dto.CartDto;
import com.starbux.dto.CartItemDto;

import java.util.List;

public interface CartService {
    public List<CartDto> getUserCarts(Long userId);

    public CartDto getCart(Long userId, Long cartId);

    public CartDto createCart(Long userId);

    public CartItemDto createCartItem(Long userId, Long cartId);

    public boolean deleteCartItem(Long userId, Long cartId, Long cartItemId);

    public CartItemDto addProductToCartItem(Long userId, Long cartId, Long cartItemId, Long productId);

    public CartItemDto updateCartItemQuantity(Long userId, Long cartId, Long cartItemId, Integer quantity);

    public boolean removeProductFromCartItem(Long userId, Long cartId, Long cartItemId, Long productId);
}
