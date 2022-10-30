package com.example.petproject2.persistance.mappers;

import com.example.petproject2.domain.model.ShoppingCartModel;
import com.example.petproject2.domain.model.CustomerModel;
import com.example.petproject2.domain.model.ProductModel;
import com.example.petproject2.persistance.entity.MongoEntity.MongoShoppingCart;
import com.example.petproject2.persistance.entity.MongoEntity.MongoCustomer;
import com.example.petproject2.persistance.entity.MongoEntity.MongoProduct;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MongoMapper {
    MongoCustomer toEntity(CustomerModel customerModel);
    CustomerModel toModel(MongoCustomer customer);
    MongoProduct toEntity(ProductModel productModel);
    ProductModel toModel(MongoProduct product);
    MongoShoppingCart toEntity(ShoppingCartModel shoppingCartModel);
    ShoppingCartModel toModel(MongoShoppingCart bucket);

    List<ProductModel> toModel(List<MongoProduct> products);
}
