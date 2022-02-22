package com.starbux.mapper;

import com.starbux.dto.OrderDto;
import com.starbux.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {
    public Order orderDtoFromOrder(OrderDto orderDto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(orderDto, Order.class);
    }

    public OrderDto orderDtoFromOrder(Order order) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(order, OrderDto.class);
    }
}
