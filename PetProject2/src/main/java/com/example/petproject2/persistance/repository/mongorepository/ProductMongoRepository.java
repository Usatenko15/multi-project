package com.example.petproject2.persistance.repository.mongorepository;

import com.example.petproject2.persistance.entity.MongoEntity.MongoProduct;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductMongoRepository extends MongoRepository<MongoProduct, String> {
}
