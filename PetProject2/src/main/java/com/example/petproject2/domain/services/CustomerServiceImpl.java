package com.example.petproject2.domain.services;

import com.example.petproject2.domain.model.CustomerModel;
import com.example.petproject2.persistance.repository.PostgresRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final PostgresRepository repository;

    @Transactional(readOnly = true)
    public List<CustomerModel> findAllCustomers() {
        return repository.findAllCustomers();
    }

    @Transactional
    public CustomerModel saveCustomer(CustomerModel customerModel) {
        return repository.saveCustomer(customerModel);
    }

    @Transactional
    public CustomerModel saveProductToCustomer(String customerId, String productId) {
        return repository.saveProductToCustomer(customerId, productId);
    }

    @Transactional(readOnly = true)
    public CustomerModel findById(String customerId) {
        return repository.findById(customerId);
    }
}
