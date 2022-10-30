package com.example.petproject2.persistance.repository;

import com.example.petproject2.domain.model.ShoppingCartModel;
import com.example.petproject2.domain.model.CustomerModel;
import com.example.petproject2.domain.model.ProductModel;
import com.example.petproject2.persistance.entity.MongoEntity.MongoShoppingCart;
import com.example.petproject2.persistance.entity.MongoEntity.MongoCustomer;
import com.example.petproject2.persistance.entity.MongoEntity.MongoProduct;
import com.example.petproject2.persistance.mappers.MongoMapper;
import com.example.petproject2.persistance.repository.mongorepository.BucketMongoRepository;
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
    private final BucketMongoRepository bucketRepository;
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

    @Transactional(readOnly = true)
    public List<ProductModel> findAllProducts(){
        List<MongoProduct> products = productRepository.findAll();
        return products.stream().map(mongoMapper::toModel).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductModel findProductById(String productId){
        return mongoMapper.toModel(
                productRepository.findById(productId).orElseThrow());
    }

    @Transactional
    public ProductModel saveProduct(ProductModel productModel){
        MongoProduct product = mongoMapper.toEntity(productModel);

        return mongoMapper.toModel(productRepository.save(product));
    }

    @Transactional
    public ProductModel editProduct(String productId, ProductModel productModel) {
        productRepository.findById(productId).orElseThrow();
        productModel.setProductId(productId);
        MongoProduct product = mongoMapper.toEntity(productModel);

        return mongoMapper.toModel(productRepository.save(product));
    }

    @Transactional
    public CustomerModel editCustomer(String customerId, CustomerModel customerModel) {
        customerRepository.findById(customerId).orElseThrow();
        customerModel.setCustomerId(customerId);
        MongoCustomer customer = mongoMapper.toEntity(customerModel);

        return mongoMapper.toModel(customerRepository.save(customer));
    }

    @Transactional
    public void deleteProduct(String productId) {
        productRepository.deleteById(productId);
    }

    @Transactional
    public void deleteCustomer(String customerId) {
        customerRepository.deleteById(customerId);
    }
    @Transactional
    public ShoppingCartModel createBucket(CustomerModel customerModel) {
        MongoCustomer customer = customerRepository.findById(customerModel.getCustomerId()).orElseThrow();
        customer.setShoppingCard(new MongoShoppingCart());
        customerRepository.save(customer);
        return mongoMapper.toModel(customer.getShoppingCard());
    }

    @Transactional
    public ShoppingCartModel addProductToBucket(String customerId, String productId) {
        MongoCustomer customer = customerRepository.findById(customerId).orElseThrow();
        MongoProduct product = productRepository.findById(productId).orElseThrow();
        customer.getShoppingCard().getProducts().add(product);
        return mongoMapper.toModel(customer.getShoppingCard());
    }
}
