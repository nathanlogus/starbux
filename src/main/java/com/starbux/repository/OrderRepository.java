package com.starbux.repository;

import com.starbux.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    public Order findByCartId(Long cartId);
}
