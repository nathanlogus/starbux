package com.starbux.service;

import com.starbux.dto.CartDto;
import com.starbux.dto.CartItemDto;
import com.starbux.mapper.CartMapper;
import com.starbux.model.Cart;
import com.starbux.model.CartItem;
import com.starbux.model.User;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    }

    @Test
    public void createCartItem() {
    }

    @Test
    public void deleteCartItem() {
    }

    @Test
    public void addProductToCartItem() {
    }

    @Test
    public void updateCartItemQuantity() {
    }

    @Test
    public void removeProductFromCartItem() {
    }
    
    public User setupTest(){
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