package com.example.moderncrud.service;

import com.example.moderncrud.entity.Product;
import com.example.moderncrud.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository);
    }

    @Test
    void getAllProducts_ShouldReturnFilteredProducts() {
        // Arrange
        Product product1 = new Product(1L, "Product 1", "Description 1", 10.0);
        Product product2 = new Product(2L, "Product 2", "Description 2", null);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        // Act
        List<Product> result = productService.getAllProducts();

        // Assert
        assertEquals(1, result.size());
        assertEquals(product1, result.get(0));
        verify(productRepository).findAll();
    }
} 