package com.example.petproject2.persistance.repository.mongorepository;

import com.example.petproject2.persistance.entity.MongoEntity.MongoShoppingCart;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BucketMongoRepository extends MongoRepository<MongoShoppingCart, String> {
}
