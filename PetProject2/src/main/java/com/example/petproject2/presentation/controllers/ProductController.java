package com.example.petproject2.presentation.controllers;

import com.example.petproject2.domain.services.ProductService;
import com.example.petproject2.presentation.DTO.ProductDTO;
import com.example.petproject2.presentation.mapper.MainMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final MainMapper mapper;
    private static final Logger LOG = LoggerFactory.getLogger(CustomerController.class);

    @GetMapping()
    public List<ProductDTO> findAll() {
        LOG.info("findAll called from ProductController ");
        return productService.findAllProducts().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{productId}")
    public ProductDTO findById(@PathVariable String productId) {
        LOG.info("findById called from ProductController ");
        return mapper.toDTO(productService.findProductById(productId));
    }

    @PostMapping()
    public ProductDTO saveProduct(@RequestBody ProductDTO productDTO) {
        LOG.info("saveProduct called from ProductController ");
        return mapper.toDTO(productService.saveProduct(mapper.toModel(productDTO)));
    }

    @PatchMapping("/{productId}")
    public ProductDTO editProduct(@PathVariable String productId, @RequestBody ProductDTO productDTO) {
        LOG.info("editProduct called from ProductController ");
        return mapper.toDTO(productService.editProduct(productId, mapper.toModel(productDTO)));
    }

    @DeleteMapping("/{productId}")
    public void deleteById(@PathVariable String productId) {
        LOG.info("deleteById called from ProductController ");
        productService.deleteProductById(productId);
    }
}
