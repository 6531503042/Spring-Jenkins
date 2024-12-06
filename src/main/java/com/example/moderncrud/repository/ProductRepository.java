package com.example.moderncrud.repository;

import com.example.moderncrud.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
} 