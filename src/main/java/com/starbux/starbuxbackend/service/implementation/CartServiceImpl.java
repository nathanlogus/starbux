package com.starbux.starbuxbackend.service.implementation;

import com.starbux.starbuxbackend.dto.CartDto;
import com.starbux.starbuxbackend.dto.CartItemDto;
import com.starbux.starbuxbackend.model.Cart;
import com.starbux.starbuxbackend.model.CartItem;
import com.starbux.starbuxbackend.model.Product;
import com.starbux.starbuxbackend.model.User;
import com.starbux.starbuxbackend.repository.CartItemRepository;
import com.starbux.starbuxbackend.repository.CartRepository;
import com.starbux.starbuxbackend.repository.ProductRepository;
import com.starbux.starbuxbackend.repository.UserRepository;
import com.starbux.starbuxbackend.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    ProductRepository productRepository;


    @Override
    public List<CartDto> getUserCarts(Long userId) {
        List<Cart> userCarts = userRepository.findById(userId).get().getCarts();
        return userCarts.stream().map(cart -> cartDtoFromCart(cart)).collect(Collectors.toList());
    }

    @Override
    public CartDto getCart(Long userId, Long cartId) {
        Optional<Cart> matchingObject =
                userRepository.findById(userId)
                        .get().getCarts().stream()
                        .filter(cart -> cart.getId().equals(cartId)).findFirst();
        if (matchingObject.isPresent()) {
            matchingObject.get().setSubtotal(matchingObject.get().getCartItems().stream()
                    .map(x -> x.getPrice().multiply(BigDecimal.valueOf(x.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
            return cartDtoFromCart(matchingObject.get());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Requested cart was not found or doesn't belong to that user!");
    }

    @Override
    public CartDto createCart(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            User user = userRepository.findById(userId).get();
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setSubtotal(BigDecimal.valueOf(0));
            return cartDtoFromCart(cartRepository.saveAndFlush(cart));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Requested user was not found or couldn't create a new shopping cart!");
    }

    @Override
    public CartItemDto createCartItem(Long userId, Long cartId) {
        if (userRepository.findById(userId).isPresent() && cartRepository.findById(cartId).isPresent()) {
            Optional<Cart> cart = cartRepository.findById(cartId);
            if (cart.isPresent()) {
                CartItem cartItem = new CartItem();
                cartItem.setCart(cart.get());
                cartItem.setPrice(BigDecimal.valueOf(0));
                cartItem.setQuantity(0);
                return cartItemDtoFromCartItem(cartItemRepository.saveAndFlush(cartItem));
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
    public CartItemDto addProductToCartItem(Long userId, Long cartId, Long cartItemId, Long productId) {
        if (userRepository.findById(userId).isPresent() &&
                cartRepository.findById(cartId).isPresent() &&
                cartItemRepository.findById(cartItemId).isPresent()) {
            Optional<Cart> cart = cartRepository.findById(cartId);
            Optional<Product> product = productRepository.findById(productId);
            if (product.isPresent()) {
                CartItem cartItem = cartItemRepository.findById(cartItemId).get();
                cartItem.getProducts().add(product.get());
                cartItem.setCart(cart.get());
                cartItem.setQuantity(1);
                cartItem.setPrice(cartItem.getProducts().stream()
                        .map(x -> x.getPrice())
                        .reduce(BigDecimal.ZERO, BigDecimal::add));
                return cartItemDtoFromCartItem(cartItemRepository.saveAndFlush(cartItem));
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't add product to cart item!");
    }

    @Override
    public CartItemDto updateCartItemQuantity(Long userId, Long cartId, Long cartItemId, Integer quantity) {
        if (userRepository.findById(userId).isPresent() &&
                cartRepository.findById(cartId).isPresent() &&
                cartItemRepository.findById(cartItemId).isPresent()) {
                CartItem cartItem = cartItemRepository.findById(cartItemId).get();
                cartItem.setQuantity(quantity);
                cartItem.setPrice(cartItem.getProducts().stream()
                        .map(x -> x.getPrice())
                        .reduce(BigDecimal.ZERO, BigDecimal::add));
                return cartItemDtoFromCartItem(cartItemRepository.saveAndFlush(cartItem));
            }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't update product quantity on cart item!");
    }

    @Override
    public boolean removeProductFromCartItem(Long userId, Long cartId, Long cartItemId, Long productId) {
        if (userRepository.findById(userId).isPresent() &&
                cartRepository.findById(cartId).isPresent() &&
                cartItemRepository.findById(cartItemId).isPresent()) {
            Optional<Cart> cart = cartRepository.findById(cartId);
            CartItem cartItem = cartItemRepository.findById(cartItemId).get();
            cartItem.getProducts().removeIf(product -> product.getId().equals(productId));
            cartItem.setCart(cart.get());
            cartItem.setQuantity(1);
            cartItem.setPrice(cartItem.getProducts().stream()
                    .map(x -> x.getPrice())
                    .reduce(BigDecimal.ZERO, BigDecimal::add).multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            CartItem response = cartItemRepository.saveAndFlush(cartItem);
            if (!response.getProducts().stream().anyMatch(product -> product.getId().equals(productId))) {
                return true;
            }
        }
        return false;
    }

    private Cart cartFromDto(CartDto cartDto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(cartDto, Cart.class);
    }

    private CartDto cartDtoFromCart(Cart cart) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(cart, CartDto.class);
    }

    private CartItem cartItemFromDto(CartItemDto cartItemDto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(cartItemDto, CartItem.class);
    }

    private CartItemDto cartItemDtoFromCartItem(CartItem cartItem) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(cartItem, CartItemDto.class);
    }
}
