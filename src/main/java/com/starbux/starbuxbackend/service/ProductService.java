package com.starbux.starbuxbackend.service;

import com.starbux.starbuxbackend.dto.ProductDto;

import java.util.List;

public interface ProductService {
    public List<ProductDto> getAllProducts();

    public ProductDto getProductById(Long productId);

    public ProductDto createProduct(ProductDto product);

    public ProductDto updateProduct(Long productId, ProductDto product);

    public boolean deleteProduct(Long productId);
}
