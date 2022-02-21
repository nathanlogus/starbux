package com.starbux.starbuxbackend.repository;

import com.starbux.starbuxbackend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
