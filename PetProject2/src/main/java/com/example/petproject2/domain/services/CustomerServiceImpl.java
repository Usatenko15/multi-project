package com.example.petproject2.domain.services;

import com.example.petproject2.domain.model.ProductModel;
import com.example.petproject2.domain.model.ShoppingCartModel;
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
    public CustomerModel createCustomer(CustomerModel customerModel) {
        CustomerModel customer = repository.saveCustomer(customerModel);
        repository.createBucket(customer);
        return customer;
    }

    @Transactional
    public CustomerModel saveProductToCustomer(String customerId, String productId) {
        return repository.saveProductToCustomer(customerId, productId);
    }

    @Transactional(readOnly = true)
    public CustomerModel findById(String customerId) {
        return repository.findById(customerId);
    }

    @Transactional
    public void deleteCustomerById(String customerId) {
        repository.deleteCustomer(customerId);
    }

    @Transactional
    public CustomerModel editCustomer(String customerId, CustomerModel customerModel) {
        return repository.editCustomer(customerId, customerModel);
    }

    @Transactional(readOnly = true)
    public ShoppingCartModel getCustomerBucket(String customerId) {
        CustomerModel customerModel = findById(customerId);
        customerModel.getShoppingCard().setSummary(customerModel.getShoppingCard().getProducts().stream().mapToDouble(ProductModel::getPrice).sum());
        return customerModel.getShoppingCard();
    }

    @Transactional
    public ShoppingCartModel addProductToBucket(String customerId, String productId) {
        return repository.addProductToBucket(customerId, productId);
    }
}
