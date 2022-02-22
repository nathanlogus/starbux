package com.starbux.starbuxbackend.service;

import com.starbux.starbuxbackend.dto.OrderDto;

public interface OrderService {
    public OrderDto createOrder(Long userId, Long cartId);

    public OrderDto getOrder(Long userId, Long cartId);
}
