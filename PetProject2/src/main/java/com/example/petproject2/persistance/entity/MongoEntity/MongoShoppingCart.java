package com.example.petproject2.persistance.entity.MongoEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Document
@Getter
@Setter
@NoArgsConstructor
public class MongoShoppingCart {
    @Id
    private String shoppingCardId;

    @DBRef
    private Set<MongoProduct> products = new HashSet<>();
}
