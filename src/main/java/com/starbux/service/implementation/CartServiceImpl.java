package com.starbux.service.implementation;

import com.starbux.mapper.CartMapper;
import com.starbux.model.Cart;
import com.starbux.model.CartItem;
import com.starbux.model.Product;
import com.starbux.model.User;
import com.starbux.repository.CartItemRepository;
import com.starbux.repository.CartRepository;
import com.starbux.repository.ProductRepository;
import com.starbux.repository.UserRepository;
import com.starbux.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service("cartService")
public class CartServiceImpl implements CartService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartMapper cartMapper;


    @Override
    public List<Cart> getUserCarts(Long userId) {
        return userRepository.getById(userId).getCarts();
    }

    @Override
    public Cart getCart(Long userId, Long cartId) {
        Cart cart = cartRepository.getById(cartId);
        cart.setSubtotal(cart.getCartItems().stream()
                .map(x -> x.getPrice().multiply(BigDecimal.valueOf(x.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        return cartRepository.save(cart);
    }

    @Override
    public Cart createCart(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            User user = userRepository.getById(userId);
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setSubtotal(BigDecimal.valueOf(0));
            return cartRepository.saveAndFlush(cart);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Requested user was not found or couldn't create a new shopping cart!");
    }

    @Override
    public CartItem createCartItem(Long userId, Long cartId) {
        if (userRepository.findById(userId).isPresent() && cartRepository.findById(cartId).isPresent()) {
            Cart cart = cartRepository.getById(cartId);
            if (cartRepository.findById(cartId).isPresent()) {
                CartItem cartItem = new CartItem();
                cartItem.setCart(cart);
                cartItem.setPrice(BigDecimal.valueOf(0));
                cartItem.setQuantity(0);
                return cartItemRepository.saveAndFlush(cartItem);
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't create cart item!");
    }

    @Override
    public boolean deleteCartItem(Long userId, Long cartId, Long cartItemId) {
        if (userRepository.findById(userId).isPresent() &&
                cartRepository.findById(cartId).isPresent() &&
                cartItemRepository.findById(cartItemId).isPresent()) {
            cartItemRepository.deleteById(cartItemId);
            return true;
        }
        return false;
    }

    @Override
    public CartItem addProductToCartItem(Long userId, Long cartId, Long cartItemId, Long productId) {
        if (userRepository.findById(userId).isPresent() &&
                cartRepository.findById(cartId).isPresent() &&
                cartItemRepository.findById(cartItemId).isPresent()) {
            Cart cart = cartRepository.getById(cartId);
            Product product = productRepository.getById(productId);
            if (productRepository.findById(productId).isPresent()) {
                CartItem cartItem = cartItemRepository.findById(cartItemId).get();
                cartItem.getProducts().add(product);
                cartItem.setCart(cart);
                cartItem.setQuantity(1);
                cartItem.setPrice(cartItem.getProducts().stream()
                        .map(x -> x.getPrice())
                        .reduce(BigDecimal.ZERO, BigDecimal::add));
                CartItem createdItem = cartItemRepository.saveAndFlush(cartItem);
                cart.setSubtotal(cart.getCartItems().stream()
                        .map(x -> x.getPrice().multiply(BigDecimal.valueOf(x.getQuantity())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add));
                cartRepository.saveAndFlush(cart);
                return createdItem;
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't add product to cart item!");
    }

    @Override
    public CartItem updateCartItemQuantity(Long userId, Long cartId, Long cartItemId, Integer quantity) {
        if (userRepository.findById(userId).isPresent() &&
                cartRepository.findById(cartId).isPresent() &&
                cartItemRepository.findById(cartItemId).isPresent()) {
            CartItem cartItem = cartItemRepository.findById(cartItemId).get();
            cartItem.setQuantity(quantity);
            cartItem.setPrice(cartItem.getProducts().stream()
                    .map(x -> x.getPrice())
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
            CartItem updatedItem = cartItemRepository.saveAndFlush(cartItem);
            Cart cart = cartRepository.getById(cartId);
            cart.setSubtotal(cart.getCartItems().stream()
                    .map(x -> x.getPrice().multiply(BigDecimal.valueOf(x.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
            cartRepository.saveAndFlush(cart);
            return updatedItem;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't update product quantity on cart item!");
    }

    @Override
    public boolean removeProductFromCartItem(Long userId, Long cartId, Long cartItemId, Long productId) {
        if (userRepository.findById(userId).isPresent() &&
                cartRepository.findById(cartId).isPresent() &&
                cartItemRepository.findById(cartItemId).isPresent()) {
            Cart cart = cartRepository.getById(cartId);
            CartItem cartItem = cartItemRepository.findById(cartItemId).get();
            cartItem.getProducts().removeIf(product -> product.getId().equals(productId));
            cartItem.setCart(cart);
            cartItem.setQuantity(1);
            cartItem.setPrice(cartItem.getProducts().stream()
                    .map(x -> x.getPrice())
                    .reduce(BigDecimal.ZERO, BigDecimal::add).multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            CartItem response = cartItemRepository.saveAndFlush(cartItem);
            cart.setSubtotal(cart.getCartItems().stream()
                    .map(x -> x.getPrice().multiply(BigDecimal.valueOf(x.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
            cartRepository.saveAndFlush(cart);
            if (!response.getProducts().stream().anyMatch(product -> product.getId().equals(productId))) {
                return true;
            }
        }
        return false;
    }
}
