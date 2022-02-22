package com.starbux.service;

import com.starbux.dto.ProductDto;

import java.util.List;

public interface ProductService {
    public List<ProductDto> getAllProducts();

    public ProductDto getProductById(Long productId);

    public ProductDto createProduct(ProductDto product);

    public ProductDto updateProduct(Long productId, ProductDto product);

    public boolean deleteProduct(Long productId);
}
