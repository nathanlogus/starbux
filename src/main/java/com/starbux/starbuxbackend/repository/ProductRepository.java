package com.starbux.starbuxbackend.repository;

import com.starbux.starbuxbackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
