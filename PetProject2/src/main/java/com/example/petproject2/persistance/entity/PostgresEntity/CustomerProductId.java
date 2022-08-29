package com.example.petproject2.persistance.entity.PostgresEntity;

import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerProductId implements Serializable {
    private Long customer;
    private Long product;
}
