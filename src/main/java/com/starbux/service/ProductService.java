package com.starbux.service;

import com.starbux.dto.ProductDto;
import com.starbux.model.Product;

import java.util.List;

public interface ProductService {
    public List<Product> getAllProducts();

    public Product getProductById(Long productId);

    public Product createProduct(ProductDto product);

    public Product updateProduct(Long productId, ProductDto product);

    public boolean deleteProduct(Long productId);
}
