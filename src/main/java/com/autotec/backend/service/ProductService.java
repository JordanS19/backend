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

    public Product updateProduct(Product newData) {
        Product existing = productTable.getItem(r -> r.key(k -> k.partitionValue(newData.getId())));
        if (existing == null) return null;

        if (newData.getName() != null) existing.setName(newData.getName());
        if (newData.getDescription() != null) existing.setDescription(newData.getDescription());
        if (newData.getPrice() != 0.0) existing.setPrice(newData.getPrice());
        if (newData.getStock() != 0) existing.setStock(newData.getStock());

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
