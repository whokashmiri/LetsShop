package com.ecom.controller.product;


import com.ecom.dto.product.ProductRequest;

import com.ecom.dto.product.ProductResponse;
import com.ecom.service.product.ProductService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;
    public ProductController( ProductService productService){
        this.productService = productService;
    }


    @PostMapping("/add-product")
    public ResponseEntity<ProductResponse> addProduct(
            @Valid @RequestBody ProductRequest productRequest) {

        ProductResponse productResponse = productService.createProduct(productRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productResponse);
    }

    @GetMapping("/get-product")
    public ResponseEntity<ProductResponse> getProduct( @RequestParam String id){
        return ResponseEntity.ok(productService.getProduct(id));
    }

}
