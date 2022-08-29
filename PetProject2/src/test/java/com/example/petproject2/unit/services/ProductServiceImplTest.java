package com.example.petproject2.unit.services;

import com.example.petproject2.domain.model.ProductModel;
import com.example.petproject2.domain.services.ProductServiceImpl;
import com.example.petproject2.persistance.repository.PostgresRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private PostgresRepository repository;
    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void findAllProductsCallsService() {
        //given

        //when
        productService.findAllProducts();

        //then
        verify(repository).findAllProducts();
    }

    @Test
    void findAllProducts() {
        ProductModel product1 = new ProductModel();
        product1.setProductId("1l");
        product1.setName("fdd");

        ProductModel product2 = new ProductModel();
        product2.setProductId("2l");
        product2.setName("f1d4d");

        List<ProductModel> products = List.of(product1,product2);
        when(repository.findAllProducts()).thenReturn(products);
        assertEquals(products, productService.findAllProducts());
    }

    @Test
    void findByProductIdCallsService() {
        //given
        String productId = "1";

        //when
        productService.findProductById(productId);

        //then
        verify(repository).findProductById(productId);
    }

    @Test
    void findProductById() {
        ProductModel product = new ProductModel();
        product.setProductId("1");
        product.setName("fdd");

        when(repository.findProductById(product.getProductId())).thenReturn(product);
        assertEquals(product,productService.findProductById(product.getProductId()));
    }

    @Test
    void saveProductCallsService() {
        //given
        ProductModel productModel = new ProductModel();

        //when
        productService.saveProduct(productModel);

        //then
        verify(repository).saveProduct(productModel);
    }

    @Test
    void saveProduct() {
        ProductModel product = new ProductModel();
        product.setProductId("1");
        product.setName("fdd");

        when(repository.saveProduct(product)).thenReturn(product);
        assertEquals(product,productService.saveProduct(product));
    }
}