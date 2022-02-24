package com.starbux.repository;

import com.starbux.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("cartRepository")
public interface CartRepository extends JpaRepository<Cart, Long> {
}
