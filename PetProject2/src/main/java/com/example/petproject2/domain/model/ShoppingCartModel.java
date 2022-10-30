package com.example.petproject2.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ShoppingCartModel {
    private String shoppingCartId;
    private double summary;
    List<ProductModel> products;
}
