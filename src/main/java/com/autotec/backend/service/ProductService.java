package com.autotec.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.autotec.backend.model.Product;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

@Service
public class ProductService {
    private final DynamoDbTable<Product> productTable;

    public ProductService(DynamoDbTable<Product> productTable) {
        this.productTable = productTable;
    }

    public List<Product> getAllProducts(){
        return productTable.scan().items().stream().toList();
    }

    public Optional<Product> getProductById(String id) {
        return Optional.ofNullable(productTable.getItem(r -> r.key(k -> k.partitionValue(id))));
    }
    
    public List<Product> searchProductsByName(String name) {
        return productTable.scan().items().stream().filter(p -> p.getName().toLowerCase().contains(name.toLowerCase())).toList();
    }

    public Product createProduct(Product product) {
        product.setId(UUID.randomUUID().toString());
        productTable.putItem(product);
        return product;
    }

    public Product updateProduct(String id, String name, String description, Double price, Integer stock) {
        Product existing = productTable.getItem(r -> r.key(k -> k.partitionValue(id)));
        if (existing == null) return null;

        if (name != null) existing.setName(name);
        if (description != null) existing.setDescription(description);
        if (price != null) existing.setPrice(price);
        if (stock != null) existing.setStock(stock);

        productTable.putItem(existing);

        return existing;
    }

    public boolean deleteProduct(String id) {
        Product existing = productTable.getItem(r -> r.key(k -> k.partitionValue(id)));
        if (existing == null) return false;
        productTable.deleteItem(existing);
        return true;
    }

}
