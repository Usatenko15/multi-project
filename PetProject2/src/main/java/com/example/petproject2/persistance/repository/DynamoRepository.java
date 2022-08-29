package com.example.petproject2.persistance.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.example.petproject2.domain.model.CustomerModel;
import com.example.petproject2.domain.model.ProductModel;
import com.example.petproject2.persistance.entity.DynamoEntity.DynamoCustomer;
import com.example.petproject2.persistance.entity.DynamoEntity.DynamoProduct;
import com.example.petproject2.persistance.mappers.DynamoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DynamoRepository implements MainRepository {
    @Autowired
    DynamoDBMapper dynamoDBMapper;
//    @Autowired
//    DynamoCustomerRepository dynamoCustomerRepository;
    @Autowired
    private DynamoMapper mapper;

    @Override
    public List<CustomerModel> findAllCustomers() {
        PaginatedScanList<DynamoCustomer> customers = dynamoDBMapper.scan(DynamoCustomer.class, new DynamoDBScanExpression());
        return customers.parallelStream().map(customer -> {
            var customerModel = mapper.toModel(customer);
            customerModel.setProducts(customer.getProducts().parallelStream().map(s -> {
                DynamoProduct product = (dynamoDBMapper.load(DynamoProduct.class, s));
                var productModel = mapper.toModel(product);
                productModel.setCustomers(product.getCustomers().parallelStream()
                        .map(s1 -> mapper.toModel(dynamoDBMapper.load(DynamoCustomer.class, s1)))
                        .collect(Collectors.toList()));
                return productModel;
            }).collect(Collectors.toList()));
            return customerModel;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CustomerModel saveCustomer(CustomerModel customerModel) {
        DynamoCustomer customer = mapper.toEntity(customerModel);
        dynamoDBMapper.save(customer);
        return mapper.toModel(customer);
    }

    @Override
    @Transactional
    public CustomerModel saveProductToCustomer(String customerId, String productId) {
        DynamoCustomer customer = dynamoDBMapper.load(DynamoCustomer.class, customerId);
        DynamoProduct product = dynamoDBMapper.load(DynamoProduct.class, productId);
        customer.getProducts().add(productId);
        product.getCustomers().add(customerId);
        dynamoDBMapper.save(customer);
        dynamoDBMapper.save(product);
        return mapper.toModel(customer);
    }

    @Override
    public CustomerModel findById(String customerId) {
        DynamoCustomer customer = dynamoDBMapper.load(DynamoCustomer.class, customerId);
        var customerModel = mapper.toModel(customer);
        customerModel.setProducts(customer.getProducts().parallelStream().map(s -> {
            DynamoProduct product = (dynamoDBMapper.load(DynamoProduct.class, s));
            var productModel = mapper.toModel(product);
            productModel.setCustomers(product.getCustomers().parallelStream()
                    .map(s1 -> mapper.toModel(dynamoDBMapper.load(DynamoCustomer.class, s1)))
                    .collect(Collectors.toList()));
            return productModel;
        }).collect(Collectors.toList()));
        return customerModel;
    }

    @Override
    public List<ProductModel> findAllProducts() {
        PaginatedScanList<DynamoProduct> products = dynamoDBMapper.scan(DynamoProduct.class, new DynamoDBScanExpression());
        return products.parallelStream().map(product -> {
            var productModel = mapper.toModel(product);
            productModel.setCustomers(product.getCustomers().parallelStream().map(s -> {
                DynamoCustomer customer = (dynamoDBMapper.load(DynamoCustomer.class, s));
                var customerModel = mapper.toModel(customer);
                customerModel.setProducts(customer.getProducts().parallelStream()
                        .map(s1 -> mapper.toModel(dynamoDBMapper.load(DynamoProduct.class, s1)))
                        .collect(Collectors.toList()));
                return customerModel;
            }).collect(Collectors.toList()));
            return productModel;
        }).collect(Collectors.toList());
    }

    @Override
    public ProductModel findProductById(String productId) {
        DynamoProduct product = dynamoDBMapper.load(DynamoProduct.class, productId);
        var productModel = mapper.toModel(product);
        productModel.setCustomers(product.getCustomers().parallelStream().map(s -> {
            DynamoCustomer customer = (dynamoDBMapper.load(DynamoCustomer.class, s));
            var customerModel = mapper.toModel(customer);
            customerModel.setProducts(customer.getProducts().parallelStream()
                    .map(s1 -> mapper.toModel(dynamoDBMapper.load(DynamoProduct.class, s1)))
                    .collect(Collectors.toList()));
            return customerModel;
        }).collect(Collectors.toList()));
        return productModel;
    }

    @Override
    public ProductModel saveProduct(ProductModel productModel) {
        DynamoProduct product = mapper.toEntity(productModel);
        dynamoDBMapper.save(product);
        return mapper.toModel(product);
    }
}
