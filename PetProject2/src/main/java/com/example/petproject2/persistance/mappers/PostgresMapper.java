package com.example.petproject2.persistance.mappers;

import com.example.petproject2.domain.model.CustomerModel;
import com.example.petproject2.domain.model.ProductModel;
import com.example.petproject2.persistance.entity.PostgresEntity.Customer;
import com.example.petproject2.persistance.entity.PostgresEntity.Product;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PostgresMapper {
    Customer toEntity(CustomerModel customerModel);

    private CustomerModel toMiniModel(Customer customer) {
        if (customer == null) {
            return null;
        }

        CustomerModel customerModel = new CustomerModel();

        if (customer.getCustomerId() != null) {
            customerModel.setCustomerId(String.valueOf(customer.getCustomerId()));
        }
        customerModel.setName(customer.getName());
        return customerModel;
    }

    private ProductModel toMiniModel(Product product) {
        if (product == null) {
            return null;
        }

        ProductModel productModel = new ProductModel();

        if (product.getProductId() != null) {
            productModel.setProductId(String.valueOf(product.getProductId()));
        }
        productModel.setName(product.getName());
        return productModel;
    }

    default CustomerModel toModel(Customer customer) {
        if (customer == null) {
            return null;
        }

        CustomerModel customerModel = toMiniModel(customer);

        List<ProductModel> productModels = customer.getCustomerProducts().stream().map(customerProduct -> {
            ProductModel productModel = toMiniModel(customerProduct.getProduct());

            List<CustomerModel> customerProductModels = customerProduct.getProduct().getCustomerProducts().stream().
                    map(customerProduct1 -> toMiniModel(customerProduct1.getCustomer())).collect(Collectors.toList());
            productModel.setCustomers(customerProductModels);

            return productModel;
        }).collect(Collectors.toList());

        customerModel.setProducts(productModels);

        return customerModel;
    }

    Product toEntity(ProductModel productModel);

    default ProductModel toModel(Product product) {
        if (product == null) {
            return null;
        }

        ProductModel productModel = toMiniModel(product);

        List<CustomerModel> customerModels = product.getCustomerProducts().stream().map(customerProduct -> {
            CustomerModel customerModel = toMiniModel(customerProduct.getCustomer());

            List<ProductModel> productModels = customerProduct.getCustomer().getCustomerProducts().stream().
                    map(customerProduct1 -> toMiniModel(customerProduct1.getProduct())).collect(Collectors.toList());
            customerModel.setProducts(productModels);

            return customerModel;
        }).collect(Collectors.toList());

        productModel.setCustomers(customerModels);

        return productModel;
    }
}
