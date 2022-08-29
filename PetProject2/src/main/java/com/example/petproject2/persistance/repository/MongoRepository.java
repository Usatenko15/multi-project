package com.example.petproject2.persistance.repository;

import com.example.petproject2.domain.model.CustomerModel;
import com.example.petproject2.domain.model.ProductModel;
import com.example.petproject2.persistance.entity.MongoEntity.MongoCustomer;
import com.example.petproject2.persistance.entity.MongoEntity.MongoProduct;
import com.example.petproject2.persistance.mappers.MongoMapper;
import com.example.petproject2.persistance.repository.mongorepository.CustomerMongoRepository;
import com.example.petproject2.persistance.repository.mongorepository.ProductMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MongoRepository implements MainRepository {
    private final CustomerMongoRepository customerRepository;
    private final ProductMongoRepository productRepository;
    private final MongoMapper mongoMapper;


    @Transactional(readOnly = true)
    public List<CustomerModel> findAllCustomers() {
        List<MongoCustomer> customers = customerRepository.findAll();
        return customers.stream().map(mongoMapper::toModel).collect(Collectors.toList());
    }

    @Transactional
    public CustomerModel saveCustomer(CustomerModel customerModel) {
        MongoCustomer customer = mongoMapper.toEntity(customerModel);
        return mongoMapper.toModel(customerRepository.save(customer));
    }

    @Transactional
    public CustomerModel saveProductToCustomer(String customerId, String productId) {
        CustomerModel customer = mongoMapper.toModel(customerRepository.findById(customerId).orElseThrow());
        ProductModel product = mongoMapper.toModel(productRepository.findById(productId).orElseThrow());
        customer.getProducts().add(product);
        product.getCustomers().add(customer);
        customerRepository.save(mongoMapper.toEntity(customer));
        return customer;
    }
    @Transactional(readOnly = true)
    public CustomerModel findById(String customerId) {
        MongoCustomer customerEntity = customerRepository.findById(customerId).orElseThrow();
        return mongoMapper.toModel(customerEntity);
    }

    public List<ProductModel> findAllProducts(){
        List<MongoProduct> products = productRepository.findAll();
        return products.stream().map(mongoMapper::toModel).collect(Collectors.toList());
    }

    public ProductModel findProductById(String productId){
        return mongoMapper.toModel(
                productRepository.findById(productId).orElseThrow());
    }

    @Transactional
    public ProductModel saveProduct(ProductModel productModel){
        MongoProduct product = mongoMapper.toEntity(productModel);

        return mongoMapper.toModel(productRepository.save(product));
    }
}
