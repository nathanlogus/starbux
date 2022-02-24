package com.starbux.service.implementation;

import com.starbux.dto.ProductDto;
import com.starbux.mapper.ProductMapper;
import com.starbux.model.Product;
import com.starbux.model.ProductType;
import com.starbux.repository.ProductRepository;
import com.starbux.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service("productService")
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductMapper productMapper;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long productId) {
        if (productRepository.findById(productId).isPresent())
            return productRepository.getById(productId);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Requested product was not found!");
    }

    @Override
    public Product createProduct(ProductDto productDto) {
        log.info("Creating product: {}", productDto);
        if (Stream.of(ProductType.values()).anyMatch(v -> v.name().equals(productDto.getProductType().name()))) {
            return productRepository.saveAndFlush(productMapper.productFromDto(productDto));
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product type not valid!");
    }

    @Override
    public Product updateProduct(Long productId, ProductDto productDto) {
        if (productRepository.findById(productId).isPresent()) {
            log.info("Updating product: {}", productId);
            Product productObject = productRepository.findById(productId).get();
            productObject.setName(productDto.getName());
            productObject.setProductType(productDto.getProductType());
            productObject.setPrice(productDto.getPrice());
            productObject.setCurrency(productDto.getCurrency());
            return productRepository.saveAndFlush(productObject);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Requested user was not found!");
    }

    @Override
    public boolean deleteProduct(Long productId) {
        if (productRepository.findById(productId).isPresent()) {
            log.info("Deleting user with Id: {}", productId);
            productRepository.deleteById(productId);
            return true;
        }
        return false;
    }

}
