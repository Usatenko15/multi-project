package com.example.petproject2.domain.model;

import com.example.petproject2.persistance.entity.MongoEntity.MongoCustomer;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"products"})
@NoArgsConstructor
public class CustomerModel {
    private String customerId;
    private String name;
    List<ProductModel> products = new ArrayList<>();

    public CustomerModel(MongoCustomer customer) {
        this.customerId = customer.getCustomerId();
        this.name = customer.getName();
    }
}
