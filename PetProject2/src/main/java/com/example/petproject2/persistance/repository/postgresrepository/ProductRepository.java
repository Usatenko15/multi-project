package com.example.petproject2.persistance.repository.postgresrepository;

import com.example.petproject2.persistance.entity.PostgresEntity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}