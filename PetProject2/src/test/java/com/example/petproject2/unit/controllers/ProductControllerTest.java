package com.example.petproject2.unit.controllers;

import com.example.petproject2.domain.model.ProductModel;
import com.example.petproject2.domain.services.ProductService;
import com.example.petproject2.presentation.DTO.ProductDTO;
import com.example.petproject2.presentation.controllers.ProductController;
import com.example.petproject2.presentation.mapper.MainMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {
    @Mock
    private ProductService productService;
    @Mock
    MainMapper mapper;

    @Captor
    private ArgumentCaptor<ProductModel> productModelArgumentCaptor;

    @InjectMocks
    private ProductController productController;

    @Test
    void findAllProductsCallsProductService() {
        //given

        //when
        productController.findAll();

        //then
        verify(productService).findAllProducts();
    }

    @Test
    void findAllProductsCallsMapsDTOsToModel() {
        //given
        var productModel = new ProductModel();

        //when
        when(productService.findAllProducts()).thenReturn(List.of(productModel));

        productController.findAll();

        //then
        verify(mapper).toDTO(productModelArgumentCaptor.capture());
        assertEquals(productModelArgumentCaptor.getValue(), productModel);
    }

    @Test
    void findAllProductsReturnsMappedProducts() {
        //given
        var productDTO = new ProductDTO();
        productDTO.setProductId("1");
        productDTO.setName("Product");

        //when
        when(productService.findAllProducts()).thenReturn(List.of(new ProductModel()));
        when(mapper.toDTO(any(ProductModel.class))).thenReturn(productDTO);

        //then
        assertEquals(productDTO, productController.findAll().get(0));
    }

    @Test
    void findProductByIdCallsProductService() {
        //given
        String productId = "1";

        //when
        productController.findById(productId);

        //then
        verify(productService).findProductById(productId);
    }

    @Test
    void findByIdCallMappedProduct() {
        String productId = "1";

        var productDTO = new ProductDTO();
        productDTO.setName("Product");
        productDTO.setProductId("1");

        var productModel = new ProductModel();
        productModel.setName("Product");
        productModel.setProductId("1");


        //when
        when(productService.findProductById(productId)).thenReturn(productModel);
        when(mapper.toDTO(any(ProductModel.class))).thenReturn(productDTO);

        productController.findById(productId);

        //then
        verify(mapper).toDTO(productModel);
        assertEquals(productController.findById(productId), productDTO);
    }

    @Test
    void saveProductCallsMapsModelToDTO() {
        //given
        var productDTO = new ProductDTO();
        productDTO.setProductId("1");
        productDTO.setName("Product");

        //when
        productController.saveProduct(productDTO);

        //then
        verify(mapper).toModel(productDTO);
    }

    @Test
    void saveProductCallsServiceSave() {
        //given
        var productDTO = new ProductDTO();
        productDTO.setProductId("1");
        productDTO.setName("Product");

        var productModel = new ProductModel();
        productModel.setName("Product");

        //when
        when(mapper.toModel(any(ProductDTO.class))).thenReturn(productModel);
        productModel.setProductId("1");
        productController.saveProduct(productDTO);

        //then
        verify(productService).saveProduct(productModel);
    }

    @Test
    void saveProductReturnsMappedProduct() {
        //given
        var productDTO = new ProductDTO();
        productDTO.setName("Product");

        var productDTO1 = new ProductDTO();
        productDTO1.setProductId("1");
        productDTO1.setName("Product");

        var productModel = new ProductModel();
        productModel.setName("Product");

        //when
        when(mapper.toModel(any(ProductDTO.class))).thenReturn(productModel);
        productModel.setProductId("1");
        when(productService.saveProduct(any(ProductModel.class))).thenReturn(productModel);
        productDTO.setProductId("1");
        when(mapper.toDTO(any(ProductModel.class))).thenReturn(productDTO);

        productController.saveProduct(productDTO);

        //then
        assertEquals(productController.saveProduct(productDTO), productDTO1);
    }
}