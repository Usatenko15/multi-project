package com.example.petproject2.domain.services;

import com.example.petproject2.domain.model.ProductModel;
import com.example.petproject2.persistance.repository.PostgresRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final PostgresRepository repository;

    public List<ProductModel> findAllProducts(){
        return repository.findAllProducts();
    }

    public ProductModel findProductById(String productId){
        return repository.findProductById(productId);
    }

    @Transactional
    public ProductModel saveProduct(ProductModel productModel){
        return repository.saveProduct(productModel);
    }
}
