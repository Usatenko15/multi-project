package com.example.petproject2.presentation.mapper;

import com.example.petproject2.domain.model.CustomerModel;
import com.example.petproject2.domain.model.ProductModel;
import com.example.petproject2.presentation.DTO.CustomerDTO;
import com.example.petproject2.presentation.DTO.ProductDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MainMapper {

    CustomerDTO toDTO(CustomerModel customerModel);
    ProductDTO toDTO(ProductModel productModel);
    CustomerModel toModel(CustomerDTO customerDTO);
    ProductModel toModel(ProductDTO productDTO);
    List<CustomerDTO> toDTOs(List<CustomerModel> customersModel);
    List<ProductDTO> toDTO(List<ProductModel> productsModel);
}
