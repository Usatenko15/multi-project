package com.example.petproject2.persistance.repository.postgresrepository;

import com.example.petproject2.persistance.entity.PostgresEntity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BucketRepository extends JpaRepository<ShoppingCart, Long> {
}