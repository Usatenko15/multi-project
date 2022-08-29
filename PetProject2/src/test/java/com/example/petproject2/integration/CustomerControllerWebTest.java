package com.example.petproject2.integration;

import com.example.petproject2.domain.services.CustomerService;
import com.example.petproject2.presentation.controllers.CustomerController;
import com.example.petproject2.presentation.mapper.MainMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
public class CustomerControllerWebTest {
    @MockBean
    private CustomerService customerService;

    @MockBean
    private MainMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createCustomer() throws Exception {


        mockMvc.perform(post("http://localhost:8090/api/v1/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"customer\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void getCustomers() throws Exception {
        mockMvc.perform(get("http://localhost:8090/api/v1/customer"))
                .andExpect(status().isOk());
    }

    @Test
    void getByCustomerID() throws Exception {
        mockMvc.perform(get("http://localhost:8090/api/v1/customer/{id}", 1l))
                .andExpect(status().isOk());
    }
}
