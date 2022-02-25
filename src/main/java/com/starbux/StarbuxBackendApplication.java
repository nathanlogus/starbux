package com.starbux;

import com.starbux.model.*;
import com.starbux.repository.CartItemRepository;
import com.starbux.repository.CartRepository;
import com.starbux.repository.ProductRepository;
import com.starbux.repository.UserRepository;
import com.starbux.service.implementation.CartServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;

@EnableSwagger2
@SpringBootApplication
@Slf4j
public class StarbuxBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarbuxBackendApplication.class, args);
    }

    @Bean
    public CommandLineRunner setup(UserRepository userRepository, ProductRepository productRepository, CartRepository cartRepository,
                                   CartItemRepository cartItemRepository, CartServiceImpl cartService) {
        return (args) -> {
            User user1 = userRepository.save(new User("User1"));
            User user2 = userRepository.save(new User("User2"));
            Product product1 = productRepository.save(new Product("Black Coffe", BigDecimal.valueOf(4), ProductType.DRINK, Currency.getInstance("EUR")));
            Product product2 = productRepository.save(new Product("Latte", BigDecimal.valueOf(5), ProductType.DRINK, Currency.getInstance("EUR")));
            Product product3 = productRepository.save(new Product("Mocha", BigDecimal.valueOf(6), ProductType.DRINK, Currency.getInstance("EUR")));
            Product product4 = productRepository.save(new Product("Tea", BigDecimal.valueOf(3), ProductType.DRINK, Currency.getInstance("EUR")));
            Product product5 = productRepository.save(new Product("Milk", BigDecimal.valueOf(2), ProductType.TOPPING, Currency.getInstance("EUR")));
            Product product6 = productRepository.save(new Product("Hazelnut Syrup", BigDecimal.valueOf(3), ProductType.TOPPING, Currency.getInstance("EUR")));
            Product product7 = productRepository.save(new Product("Chocolate Sauce", BigDecimal.valueOf(5), ProductType.TOPPING, Currency.getInstance("EUR")));
            Product product8 = productRepository.save(new Product("Lemon", BigDecimal.valueOf(2), ProductType.TOPPING, Currency.getInstance("EUR")));
            Cart cart = new Cart(BigDecimal.ZERO);
            cart.setSubtotal(BigDecimal.valueOf(6));
            cart.setUser(userRepository.getById(1L));
            cart = cartRepository.saveAndFlush(cart);
            CartItem cartItem = new CartItem(1, BigDecimal.valueOf(6));
            cartItem.setCart(cart);
            cartItem.setProducts(Arrays.asList(product1, product5));
            cartItem = cartItemRepository.saveAndFlush(cartItem);
            log.info("The sample data has been generated!");
        };
    }
}
