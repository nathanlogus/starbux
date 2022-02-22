package com.starbux.service.implementation;

import com.starbux.dto.AmountPerCustomerDto;
import com.starbux.dto.ToppingsPerDrinkDto;
import com.starbux.mapper.ProductMapper;
import com.starbux.mapper.UserMapper;
import com.starbux.model.ProductType;
import com.starbux.repository.ProductRepository;
import com.starbux.repository.UserRepository;
import com.starbux.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;
    
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
            return toppingsPerDrink;
        }).collect(Collectors.toList());
    }

}
