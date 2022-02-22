package com.starbux.starbuxbackend.service.implementation;

import com.starbux.starbuxbackend.dto.AmountPerCustomerDto;
import com.starbux.starbuxbackend.dto.ToppingsPerDrinkDto;
import com.starbux.starbuxbackend.dto.UserDto;
import com.starbux.starbuxbackend.model.User;
import com.starbux.starbuxbackend.repository.OrderRepository;
import com.starbux.starbuxbackend.repository.UserRepository;
import com.starbux.starbuxbackend.service.ReportService;
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
        
        return null;
    }

    private UserDto userDtoFromUser(User user) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, UserDto.class);
    }
}
