package com.starbux.starbuxbackend.repository;

import com.starbux.starbuxbackend.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
