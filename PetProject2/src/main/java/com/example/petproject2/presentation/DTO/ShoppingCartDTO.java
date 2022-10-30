package com.example.petproject2.presentation.DTO;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ShoppingCartDTO {
    private String shoppingCartId;
    private double summary;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    List<ProductDTO> products = new ArrayList<>();
}
