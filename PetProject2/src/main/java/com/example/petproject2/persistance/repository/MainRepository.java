package com.example.petproject2.persistance.repository;

import com.example.petproject2.domain.model.ShoppingCartModel;
import com.example.petproject2.domain.model.CustomerModel;
import com.example.petproject2.domain.model.ProductModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MainRepository {
    List<CustomerModel> findAllCustomers();
    CustomerModel saveCustomer(CustomerModel customerModel);
    CustomerModel saveProductToCustomer(String customerId, String productId);
    CustomerModel findById(String customerId);
    List<ProductModel> findAllProducts();
    ProductModel findProductById(String productId);
    ProductModel saveProduct(ProductModel productModel);

    ProductModel editProduct(String productId, ProductModel productModel);
    CustomerModel editCustomer(String customerId, CustomerModel customerModel);
    void deleteProduct(String productId);
    void deleteCustomer(String customerId);
    ShoppingCartModel createBucket(CustomerModel customer);
    ShoppingCartModel addProductToBucket(String customerId, String productId);
}
