package com.starbux.service;

import com.starbux.model.Cart;
import com.starbux.model.CartItem;

import java.util.List;

public interface CartService {
    public List<Cart> getUserCarts(Long userId);

    public Cart getCart(Long userId, Long cartId);

    public Cart createCart(Long userId);

    public CartItem createCartItem(Long userId, Long cartId);

    public boolean deleteCartItem(Long userId, Long cartId, Long cartItemId);

    public CartItem addProductToCartItem(Long userId, Long cartId, Long cartItemId, Long productId);

    public CartItem updateCartItemQuantity(Long userId, Long cartId, Long cartItemId, Integer quantity);

    public boolean removeProductFromCartItem(Long userId, Long cartId, Long cartItemId, Long productId);
}
