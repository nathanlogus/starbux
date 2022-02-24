package com.starbux.repository;

import com.starbux.model.Product;
import com.starbux.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("productRepository")
public interface ProductRepository extends JpaRepository<Product, Long> {
    public List<Product> findAllByProductType(ProductType productType);
}
