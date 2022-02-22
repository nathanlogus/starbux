package com.starbux.service;

import com.starbux.dto.OrderDto;

public interface OrderService {
    public OrderDto createOrder(Long userId, Long cartId);

    public OrderDto getOrder(Long userId, Long cartId);
}
