package com.autotec.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.autotec.backend.model.Services;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

@Service
public class ServicesService {
    private final DynamoDbTable<Services> serviceTable;

    public ServicesService(DynamoDbTable<Services> serviceTable) {
        this.serviceTable = serviceTable;
    }

    public Services createService(Services services) {
        services.setId(UUID.randomUUID().toString());
        serviceTable.putItem(services);
        return services;
    }

    public Optional<Services> getServiceById(String id) {
        return Optional.ofNullable(serviceTable.getItem(r -> r.key(k -> k.partitionValue(id))));
    }

    public List<Services> getAllServices() {
        return serviceTable.scan().items().stream().toList();
    }

    public Services updateService(String id, String name, String description, Float price, Integer durationMinutes) {
        Services existing = serviceTable.getItem(r -> r.key(k -> k.partitionValue(id)));
        if (existing == null) return null;

        if (name != null) existing.setName(name);
        if (description != null) existing.setDescription(description);
        if (price != null) existing.setPrice(price);
        if (durationMinutes != null) existing.setDurationMinutes(durationMinutes); 

        serviceTable.putItem(existing);
        return existing;
    }

    public boolean deleteService(String id) {
        Services services = serviceTable.getItem(r -> r.key(k -> k.partitionValue(id)));
        if (services != null) {
            serviceTable.deleteItem(services);
            return true;
        }
        return false;
    }
}
