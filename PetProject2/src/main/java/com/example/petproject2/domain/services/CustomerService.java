package com.example.petproject2.domain.services;

import com.example.petproject2.domain.model.ShoppingCartModel;
import com.example.petproject2.domain.model.CustomerModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {

    List<CustomerModel> findAllCustomers();
    CustomerModel createCustomer(CustomerModel customerModel);

    CustomerModel saveProductToCustomer(String customerId, String productId);

    CustomerModel findById(String customerId);

    void deleteCustomerById(String customerId);

    CustomerModel editCustomer(String customerId, CustomerModel customerModel);

    ShoppingCartModel getCustomerBucket(String customerId);

    ShoppingCartModel addProductToBucket(String customerId, String productId);
}
