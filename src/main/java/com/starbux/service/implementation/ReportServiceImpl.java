package com.starbux.service.implementation;

import com.starbux.dto.AmountPerCustomerDto;
import com.starbux.dto.ToppingsPerDrinkDto;
import com.starbux.mapper.ProductMapper;
import com.starbux.mapper.UserMapper;
import com.starbux.model.Cart;
import com.starbux.model.Product;
import com.starbux.model.ProductType;
import com.starbux.repository.CartRepository;
import com.starbux.repository.ProductRepository;
import com.starbux.repository.UserRepository;
import com.starbux.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;


@Slf4j
@Service("reportService")
public class ReportServiceImpl implements ReportService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public List<AmountPerCustomerDto> amountPerCustomerReport() {
        return userRepository.findAll().stream().map(user -> {
            AmountPerCustomerDto amountCustomer = new AmountPerCustomerDto();
            amountCustomer.setUser(userMapper.userDtoFromUser(user));
            amountCustomer.setAmount(user.getOrders().stream()
                    .map(order -> order.getTotalWithDiscount())
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
            return amountCustomer;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ToppingsPerDrinkDto> toppingsPerDrink() {
        return productRepository.findAllByProductType(ProductType.DRINK).stream().map(drink -> {
            ToppingsPerDrinkDto toppingsPerDrink = new ToppingsPerDrinkDto();
            toppingsPerDrink.setDrink(productMapper.productDtoFromProduct(drink));
            List<Cart> cartList = cartRepository.findAll().stream().filter(cart -> {
                return cart.getCartItems().stream().anyMatch(cartItem -> cartItem.getProducts().stream().anyMatch(product -> product.equals(drink)));
            }).collect(Collectors.toList());
            List<Product> toppingList = new ArrayList<>();
            cartList.forEach(cart -> cart.getCartItems().forEach(cartItem -> cartItem.getProducts().forEach(product -> {
                if (product.getProductType().equals(ProductType.TOPPING)) toppingList.add(product);
            })));
            Product mostUsedTopping = toppingList.stream()
                    .reduce(BinaryOperator.maxBy((o1, o2) -> Collections.frequency(toppingList, o1) -
                            Collections.frequency(toppingList, o2))).orElse(null);
            if (mostUsedTopping != null)
                toppingsPerDrink.setMostUsedTopping(productMapper.productDtoFromProduct(mostUsedTopping));
            return toppingsPerDrink;
        }).collect(Collectors.toList());
    }

}
