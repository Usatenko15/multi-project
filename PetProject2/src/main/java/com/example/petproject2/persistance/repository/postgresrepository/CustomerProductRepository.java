package com.example.petproject2.persistance.repository.postgresrepository;

import com.example.petproject2.persistance.entity.PostgresEntity.Customer;
import com.example.petproject2.persistance.entity.PostgresEntity.CustomerProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerProductRepository extends JpaRepository<CustomerProduct, Customer> {
    List<CustomerProduct> findCustomerProductByProductProductId(Long productId);
    List<CustomerProduct> findCustomerProductByCustomerCustomerId(Long customerId);

}