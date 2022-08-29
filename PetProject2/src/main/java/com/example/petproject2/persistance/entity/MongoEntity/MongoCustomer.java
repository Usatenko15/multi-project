package com.example.petproject2.persistance.entity.MongoEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Getter
@Setter
@NoArgsConstructor
public class MongoCustomer {
  @Id
    private String customerId;
    private String name;

    @DBRef
    List<MongoProduct> mongoProducts = new ArrayList<>();
}
