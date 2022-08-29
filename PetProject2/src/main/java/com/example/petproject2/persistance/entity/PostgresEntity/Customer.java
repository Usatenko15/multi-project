package com.example.petproject2.persistance.entity.PostgresEntity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "customer_name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<CustomerProduct> customerProducts = new HashSet<>();
}
