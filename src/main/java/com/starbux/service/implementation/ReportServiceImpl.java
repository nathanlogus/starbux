package com.starbux.service.implementation;

import com.starbux.dto.ProductDto;
import com.starbux.dto.ToppingsPerDrinkDto;
import com.starbux.dto.UserDto;
import com.starbux.model.ProductType;
import com.starbux.model.User;
import com.starbux.repository.OrderRepository;
import com.starbux.repository.UserRepository;
import com.starbux.service.ReportService;
import com.starbux.dto.AmountPerCustomerDto;
import com.starbux.model.Product;
import com.starbux.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
    OrderRepository orderRepository;

    @Override
    public List<AmountPerCustomerDto> amountPerCustomerReport() {
        return userRepository.findAll().stream().map(user -> {
            AmountPerCustomerDto amountCustomer = new AmountPerCustomerDto();
            amountCustomer.setUser(userDtoFromUser(user));
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
            toppingsPerDrink.setDrink(productDtoFromProduct(drink));
            return toppingsPerDrink;
        }).collect(Collectors.toList());
    }

    private UserDto userDtoFromUser(User user) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, UserDto.class);
    }
    
    private ProductDto productDtoFromProduct(Product product){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(product, ProductDto.class);
    }
}
