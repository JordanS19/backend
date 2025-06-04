package com.autotec.backend.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.autotec.backend.model.Services;
import com.autotec.backend.service.ServicesService;

@Controller
public class ServiceController {
    private final ServicesService servicesService;

    public ServiceController(ServicesService servicesService) {
        this.servicesService = servicesService;
    }

    @MutationMapping
    public Services createService(
        @Argument String name,
        @Argument String description,
        @Argument Float price,
        @Argument Integer durationMinutes
    ) {
        Services service = new Services();
        service.setName(name);
        service.setDescription(description);
        service.setPrice(price);
        service.setDurationMinutes(durationMinutes);
        return servicesService.createService(service);
    }

    @QueryMapping
    public Services getServiceById(@Argument String id) {
        return servicesService.getServiceById(id).orElse(null);
    }

    @QueryMapping
    public List<Services> getAllServices() {
        return servicesService.getAllServices();
    }

    @MutationMapping
    public Services updateService(
        @Argument String id,
        @Argument String name,
        @Argument String description,
        @Argument Float price,
        @Argument Integer durationMinutes
    ) {
        return servicesService.updateService(id, name, description, price, durationMinutes);
    }

    @MutationMapping
    public Boolean deleteService(@Argument String id){
        return servicesService.deleteService(id);
    }
}
