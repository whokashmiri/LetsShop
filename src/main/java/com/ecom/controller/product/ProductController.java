package com.ecom.controller.product;


import com.ecom.dto.product.ProductRequest;

import com.ecom.dto.product.ProductResponse;
import com.ecom.models.category.Category;
import com.ecom.service.product.ProductService;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

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

    @GetMapping("/all-products")
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
      List<ProductResponse>  productResponseList = productService.getProducts();
      return  ResponseEntity.ok(productResponseList);
    }
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchByName(@RequestParam String name){
      List<ProductResponse> productResponseList =  productService.getProductByName(name);
      return ResponseEntity.ok(productResponseList);
    }
    @GetMapping("/category")
    public ResponseEntity<List<ProductResponse>> searchByCategory(@RequestParam String categoryId){
        List<ProductResponse> productResponseList = productService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(productResponseList);
    }
    @GetMapping("/filter")
    public ResponseEntity<Page<ProductResponse>> filterProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ProductResponse> productResponseList =
                productService.getProductsByCategoryAndBrand(
                        category,
                        brand,
                        minPrice,
                        maxPrice,
                        sort,
                        page,
                        size
                );

        return ResponseEntity.ok(productResponseList);
    }


}
