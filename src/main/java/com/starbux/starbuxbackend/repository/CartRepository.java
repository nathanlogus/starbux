package com.starbux.starbuxbackend.repository;

import com.starbux.starbuxbackend.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
