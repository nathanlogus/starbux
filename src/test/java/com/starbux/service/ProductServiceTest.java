package com.starbux.service;

import com.starbux.dto.ProductDto;
import com.starbux.mapper.ProductMapper;
import com.starbux.model.Product;
import com.starbux.model.ProductType;
import com.starbux.repository.ProductRepository;
import com.starbux.service.implementation.ProductServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void getAllProducts() {
        List<Product> productList = new ArrayList<Product>();
        productList.add(new Product("Coffe", BigDecimal.ONE, ProductType.DRINK, Currency.getInstance("EUR")));
        productList.add(new Product("Latte", BigDecimal.ONE, ProductType.DRINK, Currency.getInstance("EUR")));
        productList.add(new Product("Mocha", BigDecimal.ONE, ProductType.DRINK, Currency.getInstance("EUR")));
        when(productRepository.findAll()).thenReturn(productList);

        List<Product> result = productService.getAllProducts();
        assertEquals(productList.size(), result.size());
    }

    @Test
    public void getProductById() {
        Product product = new Product("Coffe", BigDecimal.ONE, ProductType.DRINK, Currency.getInstance("EUR"));
        when(productRepository.findById(any())).thenReturn(Optional.of(product));
        when(productRepository.getById(any())).thenReturn(product);
        Product result = productService.getProductById(1L);
        assertEquals(product.getId(), result.getId());
        assertEquals(product.getName(), result.getName());
        assertEquals(product.getProductType(), result.getProductType());
        assertEquals(product.getPrice(), result.getPrice());
        assertEquals(product.getCurrency(), result.getCurrency());
    }

    @Test
    public void createProduct() {
        Product product = new Product("Coffe", BigDecimal.ONE, ProductType.DRINK, Currency.getInstance("EUR"));
        when(productRepository.saveAndFlush(any())).thenReturn(product);
        Product result = productService.createProduct(productDtoFromProduct(product));
        assertEquals(product.getId(), result.getId());
        assertEquals(product.getName(), result.getName());
        assertEquals(product.getProductType(), result.getProductType());
        assertEquals(product.getPrice(), result.getPrice());
        assertEquals(product.getCurrency(), result.getCurrency());
    }

    @Test
    public void updateProduct() {
        Product product = new Product("Coffe", BigDecimal.ONE, ProductType.DRINK, Currency.getInstance("EUR"));
        when(productRepository.findById(any())).thenReturn(Optional.of(product));
        when(productRepository.getById(any())).thenReturn(product);
        when(productRepository.saveAndFlush(any())).thenReturn(product);
        Product result = productService.updateProduct(1L, productDtoFromProduct(product));
        assertEquals(product.getId(), result.getId());
        assertEquals(product.getName(), result.getName());
        assertEquals(product.getProductType(), result.getProductType());
        assertEquals(product.getPrice(), result.getPrice());
        assertEquals(product.getCurrency(), result.getCurrency());
    }

    @Test
    public void deleteProduct() {
        Product product = new Product("Coffe", BigDecimal.ONE, ProductType.DRINK, Currency.getInstance("EUR"));
        when(productRepository.findById(any())).thenReturn(Optional.of(product));
        boolean result = productService.deleteProduct(1L);
        assertEquals(true, result);
        verify(productRepository, times(1)).deleteById(any());
    }

    private Product productFromDto(ProductDto productDto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(productDto, Product.class);
    }

    private ProductDto productDtoFromProduct(Product product) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(product, ProductDto.class);
    }
}