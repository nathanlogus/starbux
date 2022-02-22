package com.starbux.mapper;

import com.starbux.dto.CartDto;
import com.starbux.dto.CartItemDto;
import com.starbux.model.Cart;
import com.starbux.model.CartItem;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CartMapper {
    public CartDto cartDtoFromCart(Cart cart) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(cart, CartDto.class);
    }

    public CartItemDto cartItemDtoFromCartItem(CartItem cartItem) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(cartItem, CartItemDto.class);
    }
}
