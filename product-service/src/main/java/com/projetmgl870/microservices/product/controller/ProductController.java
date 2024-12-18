package com.projetmgl870.microservices.product.controller;

import com.projetmgl870.microservices.product.dto.ProductRequest;
import com.projetmgl870.microservices.product.dto.ProductResponse;
import com.projetmgl870.microservices.product.model.Product;
import com.projetmgl870.microservices.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {


    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest){

        return productService.createProduct(productRequest);

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProduct(){
        return productService.getAllProducts();
    }
}
