package com.example.petproject2.integration;

import com.example.petproject2.domain.services.ProductService;
import com.example.petproject2.presentation.controllers.ProductController;
import com.example.petproject2.presentation.mapper.MainMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerWebTest {
    @MockBean
    private ProductService productService;

    @MockBean
    private MainMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createProduct() throws Exception {


        mockMvc.perform(post("http://localhost:8090/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"customer\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void getProducts() throws Exception {
        mockMvc.perform(get("http://localhost:8090/api/v1/product"))
                .andExpect(status().isOk());
    }

    @Test
    void getByProductID() throws Exception {
        mockMvc.perform(get("http://localhost:8090/api/v1/product/{id}", 1l))
                .andExpect(status().isOk());
    }
}
