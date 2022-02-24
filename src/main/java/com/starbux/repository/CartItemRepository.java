package com.starbux.repository;

import com.starbux.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("cartItemRepository")
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
