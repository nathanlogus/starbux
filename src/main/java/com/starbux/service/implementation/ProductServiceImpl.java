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
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductMapper productMapper;

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream().map(product -> productMapper.productDtoFromProduct(product)).collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(Long productId) {
        if (productRepository.findById(productId).isPresent())
            return productMapper.productDtoFromProduct(productRepository.findById(productId).get());
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Requested product was not found!");
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        log.info("Creating product: {}", productDto);
        if (Stream.of(ProductType.values()).anyMatch(v -> v.name().equals(productDto.getProductType().name()))) {
            return productMapper.productDtoFromProduct(productRepository.saveAndFlush(productMapper.productFromDto(productDto)));
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product type not valid!");
    }

    @Override
    public ProductDto updateProduct(Long productId, ProductDto productDto) {
        if (productRepository.findById(productId).isPresent()) {
            log.info("Updating product: {}", productId);
            Product productObject = productRepository.findById(productId).get();
            productObject.setName(productDto.getName());
            productObject.setProductType(productDto.getProductType());
            productObject.setPrice(productDto.getPrice());
            productObject.setCurrency(productDto.getCurrency());
            return productMapper.productDtoFromProduct(productRepository.saveAndFlush(productObject));
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
