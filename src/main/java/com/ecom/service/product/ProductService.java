package com.ecom.service.product;

import com.ecom.dto.product.ProductRequest;
import com.ecom.dto.product.ProductResponse;
import com.ecom.exceptions.product.ProductNotFoundException;
import com.ecom.models.product.Product;
import com.ecom.repository.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;


    public ProductService (ProductRepository productRepository ){
        this.productRepository = productRepository;


    }
    public ProductResponse createProduct(ProductRequest productRequest){
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setBrand(productRequest.getBrand());
        product.setCategory( productRequest.getCategory());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        product.setImages(productRequest.getImages());
       LocalDateTime now = LocalDateTime.now();
        product.setUpdatedAt(now);
        product.setCreatedAt(now);
        Product savedProduct = productRepository.save(product);

        ProductResponse response = new ProductResponse();

        response.setId(savedProduct.getId());
        response.setName(savedProduct.getName());
        response.setBrand(savedProduct.getBrand());
        response.setCategory(savedProduct.getCategory());
        response.setDescription(savedProduct.getDescription());
        response.setPrice(savedProduct.getPrice());
        response.setQuantity(savedProduct.getQuantity());
        response.setImages(savedProduct.getImages());
        response.setCreatedAt(savedProduct.getCreatedAt());
        response.setUpdatedAt(savedProduct.getUpdatedAt());

        return response;


    }

    public ProductResponse getProduct(String id){
    Optional<Product> product =   productRepository.findById(id);
    if (product.isEmpty()){
        throw new ProductNotFoundException("Product not found");
    }
    Product savedProduct = product.get();
    ProductResponse productResponse = new ProductResponse();
    productResponse.setId(savedProduct.getId());
    productResponse.setName(savedProduct.getName());
    productResponse.setBrand(savedProduct.getBrand());
    productResponse.setCategory(savedProduct.getCategory());
    productResponse.setPrice(savedProduct.getPrice());
    productResponse.setQuantity(savedProduct.getQuantity());
    productResponse.setDescription(savedProduct.getDescription());
    productResponse.setImages(savedProduct.getImages());
    productResponse.setUpdatedAt(savedProduct.getUpdatedAt());
    productResponse.setCreatedAt(savedProduct.getCreatedAt());
    return productResponse;

    }
}
