package com.autotec.backend.config;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class DynamoDBConfig {
    @Bean
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder().region(Region.US_EAST_2).credentialsProvider(ProfileCredentialsProvider.create("autotec")).build();
    }

    @Bean
    public DynamoDbEnhancedClient enhancedClient(DynamoDbClient client) {
        return DynamoDbEnhancedClient.builder()
            .dynamoDbClient(client)
            .build();
    }

    @Bean
    public DynamoDbTable<com.autotec.backend.model.User> userTable(DynamoDbEnhancedClient enhancedClient) {
        return enhancedClient.table("User", TableSchema.fromBean(com.autotec.backend.model.User.class));
    }

    @Bean
    public DynamoDbTable<com.autotec.backend.model.Services> serviceTable(DynamoDbEnhancedClient enhancedClient) {
        return enhancedClient.table("Service", TableSchema.fromBean(com.autotec.backend.model.Services.class));
    }

    @Bean
    public DynamoDbTable<com.autotec.backend.model.Appointment> appointmentTable(DynamoDbEnhancedClient enhancedClient) {
        return enhancedClient.table("Appointment", TableSchema.fromBean(com.autotec.backend.model.Appointment.class));
    }

    @Bean
    public DynamoDbTable<com.autotec.backend.model.Product> productTable(DynamoDbEnhancedClient enhancedClient) {
        return enhancedClient.table("Product", TableSchema.fromBean(com.autotec.backend.model.Product.class));
    }

    @Bean
    public static BeanFactoryPostProcessor disableRestartClassLoader() {
        return beanFactory -> System.setProperty("spring.devtools.restart.enabled", "false");
    }

}

