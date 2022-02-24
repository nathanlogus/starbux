package com.starbux.service.implementation;

import com.starbux.mapper.OrderMapper;
import com.starbux.model.Cart;
import com.starbux.model.CartItem;
import com.starbux.model.Order;
import com.starbux.model.ProductType;
import com.starbux.repository.CartRepository;
import com.starbux.repository.OrderRepository;
import com.starbux.repository.UserRepository;
import com.starbux.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderMapper orderMapper;

    @Override
    public Order createOrder(Long userId, Long cartId) {
        if (orderRepository.findByCartId(cartId) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This cart already have an order!");
        }
        if (userRepository.findById(userId).isPresent() &&
                cartRepository.findById(cartId).isPresent()) {
            Order order = new Order();
            order.setUser(userRepository.getById(userId));
            order.setCart(cartRepository.getById(cartId));
            order.setOrderDate(new Date());
            order.setOriginalTotal(order.getCart().getCartItems().stream()
                    .map(x -> x.getPrice().multiply(BigDecimal.valueOf(x.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
            order.setTotalWithDiscount(calculateDiscounts(order.getCart()));
            return orderRepository.saveAndFlush(order);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't close cart to order!");
    }

    @Override
    public Order getOrder(Long userId, Long cartId) {
        return orderRepository.findByCartId(cartId);
    }

    public BigDecimal calculateDiscounts(Cart cart) {
        boolean quarterPriceEnable = false;
        boolean threeDrinkEnable = false;
        BigDecimal threeDrinkDiscount = BigDecimal.ZERO;
        BigDecimal quarterPriceDiscount = BigDecimal.ZERO;

        BigDecimal originalPrice = cart.getCartItems().stream()
                .map(x -> x.getPrice().multiply(BigDecimal.valueOf(x.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Integer totalDrinks = cart.getCartItems().stream()
                .map(x -> x.getProducts().stream().filter(
                                product -> product.getProductType().equals(ProductType.DRINK))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList()).size();
        if (originalPrice.intValue() > 12) {
            quarterPriceDiscount = originalPrice.subtract(originalPrice.multiply(BigDecimal.valueOf(0.25)));
            quarterPriceEnable = true;
        }
        if (totalDrinks > 3) {
            CartItem lowestCartItem = cart.getCartItems().stream()
                    .min(Comparator.comparing(cartItem -> cartItem.getPrice()))
                    .orElseThrow(NoSuchElementException::new);
            threeDrinkDiscount = originalPrice.subtract(lowestCartItem.getPrice());
            threeDrinkEnable = true;
        }
        if (quarterPriceEnable && threeDrinkEnable) {
            return quarterPriceDiscount.doubleValue() < threeDrinkDiscount.doubleValue() ?
                    quarterPriceDiscount : threeDrinkDiscount;
        } else if (quarterPriceEnable) {
            return quarterPriceDiscount;
        } else if (threeDrinkEnable) {
            return threeDrinkDiscount;
        }
        return originalPrice;
    }

}
