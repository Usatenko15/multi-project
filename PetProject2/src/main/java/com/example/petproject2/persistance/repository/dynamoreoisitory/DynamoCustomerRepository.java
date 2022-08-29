package com.example.petproject2.persistance.repository.dynamoreoisitory;

import com.example.petproject2.persistance.entity.DynamoEntity.DynamoCustomer;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@EnableScan
@Repository
public interface DynamoCustomerRepository extends PagingAndSortingRepository<DynamoCustomer, String> {
}
