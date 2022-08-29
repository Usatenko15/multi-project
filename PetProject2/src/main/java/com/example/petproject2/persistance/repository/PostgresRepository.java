package com.example.petproject2.persistance.repository;

import com.example.petproject2.domain.model.CustomerModel;
import com.example.petproject2.domain.model.ProductModel;
import com.example.petproject2.persistance.entity.PostgresEntity.Customer;
import com.example.petproject2.persistance.entity.PostgresEntity.CustomerProduct;
import com.example.petproject2.persistance.entity.PostgresEntity.Product;
import com.example.petproject2.persistance.mappers.PostgresMapper;
import com.example.petproject2.persistance.repository.postgresrepository.CustomerProductRepository;
import com.example.petproject2.persistance.repository.postgresrepository.CustomerRepository;
import com.example.petproject2.persistance.repository.postgresrepository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PostgresRepository implements MainRepository {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final CustomerProductRepository customerProductRepository;
    private final PostgresMapper postgresMapper;


    @Transactional(readOnly = true)
    public List<CustomerModel> findAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(postgresMapper::toModel).collect(Collectors.toList());
    }

    @Transactional
    public CustomerModel saveCustomer(CustomerModel customerModel) {
        Customer customer = postgresMapper.toEntity(customerModel);
        return postgresMapper.toModel(customerRepository.save(customer));
    }

    @Transactional
    public CustomerModel saveProductToCustomer(String customerId, String productId) {
        Customer customer = customerRepository.findById(Long.parseLong(customerId)).orElseThrow();
        Product product = productRepository.findById(Long.parseLong(productId)).orElseThrow();
        CustomerProduct customerProduct = new CustomerProduct();
        customerProduct.setCustomer(customer);
        customerProduct.setProduct(product);
        customerProductRepository.save(customerProduct);
        return postgresMapper.toModel(customer);
    }

    @Transactional(readOnly = true)
    public CustomerModel findById(String customerId) {
        Customer customer = customerRepository.findById(Long.parseLong(customerId)).orElseThrow();
        return postgresMapper.toModel(customer);
    }


    public List<ProductModel> findAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(postgresMapper::toModel).collect(Collectors.toList());
    }

    public ProductModel findProductById(String productId) {
        return postgresMapper.toModel(productRepository.findById(Long.parseLong(productId)).orElseThrow());
    }

    @Transactional
    public ProductModel saveProduct(ProductModel productModel) {
        Product product = postgresMapper.toEntity(productModel);
        return postgresMapper.toModel(productRepository.save(product));
    }

}
