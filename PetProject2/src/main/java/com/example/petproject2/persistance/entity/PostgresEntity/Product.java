package com.example.petproject2.persistance.entity.PostgresEntity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "product_name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<CustomerProduct> customerProducts = new HashSet<>();
}
