package com.example.petproject2.persistance.repository.postgresrepository;

import com.example.petproject2.persistance.entity.PostgresEntity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}