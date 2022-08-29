package com.example.petproject2.persistance.repository.mongorepository;

import com.example.petproject2.persistance.entity.MongoEntity.MongoCustomer;
import com.example.petproject2.persistance.entity.MongoEntity.MongoProduct;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CustomerMongoRepository extends MongoRepository<MongoCustomer, String> {
   List<MongoProduct> findMongoCustomerByMongoProductsContains(MongoProduct product);
}
