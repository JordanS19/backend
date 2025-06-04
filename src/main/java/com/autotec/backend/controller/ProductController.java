package com.autotec.backend.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.autotec.backend.model.Product;
import com.autotec.backend.service.ProductService;

@Controller
public class ProductController {
    
    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @QueryMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @QueryMapping
    public Product getProductById(@Argument String id) {
        return productService.getProductById(id).orElse(null);
    }

    @QueryMapping
    public List<Product> searchProductsByName(@Argument String name) {
        return productService.searchProductsByName(name);
    }

    @MutationMapping
    public Product createProduct(
        @Argument String name,
        @Argument String description,
        @Argument double price,
        @Argument int stock
    ) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        return productService.createProduct(product);
    }

    @MutationMapping
    public Product updateProduct(
        @Argument String id,
        @Argument String name,
        @Argument String description,
        @Argument Double price,
        @Argument Integer stock
    ) {
        Product p = new Product();
        p.setId(id);
        p.setName(name);
        p.setDescription(description);
        if (price != null) p.setPrice(price);
        if (stock != null) p.setStock(stock);
        return productService.updateProduct(p);
    }

    @MutationMapping
    public Boolean deleteProduct(@Argument String id){
        return productService.deleteProduct(id);
    }


}
