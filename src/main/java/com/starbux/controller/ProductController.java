package com.starbux.controller;


import com.starbux.dto.ProductDto;
import com.starbux.mapper.ProductMapper;
import com.starbux.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProductList() {
        return ResponseEntity.ok(productService.getAllProducts().stream().map(product -> productMapper.productDtoFromProduct(product)).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productMapper.productDtoFromProduct(productService.getProductById(id)));
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto product) {
        return new ResponseEntity(productMapper.productDtoFromProduct(productService.createProduct(product)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto product) {
        return ResponseEntity.ok(productMapper.productDtoFromProduct(productService.updateProduct(id, product)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteProduct(@PathVariable Long id) {
        boolean isRemoved = productService.deleteProduct(id);
        if (isRemoved) {
            return new ResponseEntity("Succesfully removed product!", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
