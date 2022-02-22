package com.starbux.repository;

import com.starbux.model.Product;
import com.starbux.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    public List<Product> findAllByProductType(ProductType productType);
}
