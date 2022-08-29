package com.example.petproject2.domain.services;

import com.example.petproject2.domain.model.ProductModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<ProductModel> findAllProducts();
    ProductModel findProductById(String productId);
    ProductModel saveProduct(ProductModel productModel);
}
