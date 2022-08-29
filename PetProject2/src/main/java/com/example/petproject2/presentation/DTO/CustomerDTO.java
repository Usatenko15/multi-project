package com.example.petproject2.presentation.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CustomerDTO {
    private String customerId;
    private String name;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ProductDTO> products = new ArrayList<>();
}
