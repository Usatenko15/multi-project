package com.example.petproject2.persistance.mappers;

import com.example.petproject2.domain.model.CustomerModel;
import com.example.petproject2.domain.model.ProductModel;
import com.example.petproject2.persistance.entity.DynamoEntity.DynamoCustomer;
import com.example.petproject2.persistance.entity.DynamoEntity.DynamoProduct;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DynamoMapper {
    default DynamoCustomer toEntity(CustomerModel customerModel){
        DynamoCustomer customer = new DynamoCustomer();
        customer.setName(customerModel.getName());
        return customer;

    }
    default CustomerModel toModel(DynamoCustomer customer){
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
    default DynamoProduct toEntity(ProductModel productModel){
        DynamoProduct product = new DynamoProduct();
        product.setName(productModel.getName());
        return product;
    }
    default ProductModel toModel(DynamoProduct product){
        ProductModel productModel = new ProductModel();
        productModel.setProductId(product.getProductId());
        productModel.setName(product.getName());
        return productModel;
    }
}
