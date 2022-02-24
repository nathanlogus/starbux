package com.starbux.service;

import com.starbux.model.Order;

public interface OrderService {
    public Order createOrder(Long userId, Long cartId);

    public Order getOrder(Long userId, Long cartId);
}
