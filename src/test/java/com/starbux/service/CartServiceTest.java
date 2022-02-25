package com.starbux.service;

import com.starbux.dto.CartDto;
import com.starbux.dto.CartItemDto;
import com.starbux.mapper.CartMapper;
import com.starbux.model.*;
import com.starbux.repository.CartItemRepository;
import com.starbux.repository.CartRepository;
import com.starbux.repository.ProductRepository;
import com.starbux.repository.UserRepository;
import com.starbux.service.implementation.CartServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class CartServiceTest {
    @Mock
    UserRepository userRepository;

    @Mock
    CartRepository cartRepository;

    @Mock
    CartItemRepository cartItemRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    private CartMapper cartMapper;

    @InjectMocks
    private CartServiceImpl cartService;

    @Test
    public void getUserCarts() {
        User user = setupTest();
        when(userRepository.getById(any())).thenReturn(user);

        List<Cart> result = cartService.getUserCarts(1L);
        assertEquals(user.getCarts().size(), result.size());
    }

    @Test
    public void getCart() {
        Cart cart = new Cart(BigDecimal.ZERO);
        CartItem cartItem = new CartItem(1, BigDecimal.ZERO);
        cart.setCartItems(Arrays.asList(cartItem));
        when(cartRepository.getById(any())).thenReturn(cart);
        when(cartRepository.save(any())).thenReturn(cart);

        Cart result = cartService.getCart(1L, 1L);
        assertEquals(cart.getSubtotal(), result.getSubtotal());
        assertEquals(cart.getCartItems().size(), result.getCartItems().size());
    }

    @Test
    public void createCart() {
        User user = setupTest();
        Cart cart = new Cart(BigDecimal.ZERO);
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(cartRepository.saveAndFlush(any())).thenReturn(cart);

        Cart result = cartService.createCart(1L);
        assertEquals(cart.getSubtotal(), result.getSubtotal());
    }

    @Test
    public void createCartItem() {
        User user = setupTest();
        Cart cart = new Cart(BigDecimal.ZERO);
        CartItem cartItem = new CartItem(1, BigDecimal.ZERO);
        cartItem.setCart(cart);
        cart.setCartItems(Arrays.asList(cartItem));
        user.setCarts(Arrays.asList(cart));
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(cartRepository.findById(any())).thenReturn(Optional.of(cart));
        when(cartRepository.getById(any())).thenReturn(cart);
        when(cartItemRepository.saveAndFlush(any())).thenReturn(cartItem);

        CartItem result = cartService.createCartItem(1L, 1L);
        assertEquals(cartItem.getQuantity(), result.getQuantity());
        assertEquals(cartItem.getPrice(), result.getPrice());
        assertEquals(cartItem.getCart(), result.getCart());
    }

    @Test
    public void deleteCartItem() {
        User user = setupTest();
        Cart cart = new Cart(BigDecimal.ZERO);
        CartItem cartItem = new CartItem(1, BigDecimal.ZERO);
        cartItem.setCart(cart);
        cart.setCartItems(Arrays.asList(cartItem));
        user.setCarts(Arrays.asList(cart));
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(cartRepository.findById(any())).thenReturn(Optional.of(cart));
        when(cartItemRepository.findById(any())).thenReturn(Optional.of(cartItem));

        boolean result = cartService.deleteCartItem(1L, 1L, 1L);
        assertEquals(true, result);
        verify(cartItemRepository, times(1)).deleteById(any());
    }

    @Test
    public void addProductToCartItem() {
        User user = setupTest();
        Cart cart = new Cart(BigDecimal.ZERO);
        CartItem cartItem = new CartItem(1, BigDecimal.ZERO);
        Product product = new Product("Coffe", BigDecimal.ONE, ProductType.DRINK, Currency.getInstance("EUR"));
        cartItem.setProducts(Arrays.asList(product));
        cartItem.setCart(cart);
        cart.setCartItems(Arrays.asList(cartItem));
        user.setCarts(Arrays.asList(cart));
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(cartRepository.findById(any())).thenReturn(Optional.of(cart));
        when(cartItemRepository.findById(any())).thenReturn(Optional.of(cartItem));
        when(cartRepository.getById(any())).thenReturn(cart);
        when(productRepository.getById(any())).thenReturn(product);
        when(productRepository.findById(any())).thenReturn(Optional.of(product));
        when(cartItemRepository.getById(any())).thenReturn(cartItem);
        when(cartItemRepository.saveAndFlush(any())).thenReturn(cartItem);
        when(cartRepository.saveAndFlush(any())).thenReturn(cart);

        CartItem result = cartService.addProductToCartItem(1L, 1L, 1L, 1L);
        assertEquals(cartItem.getProducts(), result.getProducts());
    }

    @Test
    public void updateCartItemQuantity() {
        User user = setupTest();
        Cart cart = new Cart(BigDecimal.ZERO);
        CartItem cartItem = new CartItem(2, BigDecimal.ZERO);
        Product product = new Product("Coffe", BigDecimal.ONE, ProductType.DRINK, Currency.getInstance("EUR"));
        cartItem.setProducts(Arrays.asList(product));
        cartItem.setCart(cart);
        cart.setCartItems(Arrays.asList(cartItem));
        user.setCarts(Arrays.asList(cart));
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(cartRepository.findById(any())).thenReturn(Optional.of(cart));
        when(cartItemRepository.findById(any())).thenReturn(Optional.of(cartItem));
        when(cartItemRepository.getById(any())).thenReturn(cartItem);
        when(cartItemRepository.saveAndFlush(any())).thenReturn(cartItem);
        when(cartRepository.getById(any())).thenReturn(cart);
        when(cartRepository.saveAndFlush(any())).thenReturn(cart);

        CartItem result = cartService.updateCartItemQuantity(1L, 1L, 1L, 2);
        assertEquals(cartItem.getProducts(), result.getProducts());
    }

    @Test
    public void removeProductFromCartItem() {
    }

    public User setupTest() {
        User user = new User("User1");
        Cart cart = new Cart(BigDecimal.ZERO);
        Cart cart2 = new Cart(BigDecimal.ZERO);
        cart.setUser(user);
        cart2.setUser(user);
        user.setCarts(Arrays.asList(cart, cart2));
        return user;
    }

    public CartDto cartDtoFromCart(Cart cart) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(cart, CartDto.class);
    }

    public CartItemDto cartItemDtoFromCartItem(CartItem cartItem) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(cartItem, CartItemDto.class);
    }
}