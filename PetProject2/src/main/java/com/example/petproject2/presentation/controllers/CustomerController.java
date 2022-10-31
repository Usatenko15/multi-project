package com.example.petproject2.presentation.controllers;

import com.example.petproject2.domain.services.CustomerService;
import com.example.petproject2.presentation.DTO.ShoppingCartDTO;
import com.example.petproject2.presentation.DTO.CustomerDTO;
import com.example.petproject2.presentation.mapper.MainMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final MainMapper mapper;
    private static final Logger LOG = LoggerFactory.getLogger(CustomerController.class);

    @GetMapping()
    public List<CustomerDTO> findAll() {
        LOG.info("findAll called from CustomerController");
        return customerService.findAllCustomers().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @PostMapping()
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        LOG.info("saveCustomer called from CustomerController ");
        return mapper.toDTO(customerService.createCustomer(mapper.toModel(customerDTO)));
    }

    @GetMapping("/{customerId}")
    public CustomerDTO findById(@PathVariable String customerId) {
        LOG.info("findById called from CustomerController ");
        return mapper.toDTO(customerService.findById(customerId));
    }

    @PostMapping("/{customerId}/product/{productId}")
    public CustomerDTO saveProductToCustomer(@PathVariable String customerId, @PathVariable String productId) {
        LOG.info("saveProductToCustomer called from CustomerController ");
        return mapper.toDTO(customerService.saveProductToCustomer(customerId, productId));
    }

    @PatchMapping("/{customerId}")
    public CustomerDTO editCustomer(@PathVariable String customerId, @RequestBody CustomerDTO customerDTO) {
        LOG.info("editCustomer called from CustomerController ");
        return mapper.toDTO(customerService.editCustomer(customerId, mapper.toModel(customerDTO)));
    }

    @DeleteMapping("/{customerId}")
    public void deleteById(@PathVariable String customerId) {

        LOG.info("deleteById called from CustomerController ");
        customerService.deleteCustomerById(customerId);
    }

    @GetMapping("/{customerId}/bucket")
    public ShoppingCartDTO getCustomerBucket(@PathVariable String customerId) {
        LOG.info("getCustomerBucket called from CustomerController ");
        return mapper.toDTO(customerService.getCustomerBucket(customerId));
    }

    @PostMapping("/{customerId}/bucket/product/{productId}")
    public void addProductToCustomer(@PathVariable String customerId, @PathVariable String productId) {
        LOG.info("addProductToCustomer called from CustomerController ");
        customerService.addProductToBucket(customerId, productId);
    }
}
