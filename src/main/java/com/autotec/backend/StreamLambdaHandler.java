package com.autotec.backend;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.serverless.proxy.spring.SpringBootProxyHandlerBuilder;
import com.amazonaws.serverless.proxy.spring.SpringBootRequestHandler;

/**
 * Lambda handler for API Gateway using aws-serverless-java-container.
 */
public class StreamLambdaHandler extends SpringBootRequestHandler<AwsProxyRequest, AwsProxyResponse> {

    private static final SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> HANDLER;

    static {
        try {
            HANDLER = new SpringBootProxyHandlerBuilder<AwsProxyRequest>()
                    .defaultProxy()
                    .asyncInit()
                    .springBootApplication(BackendApplication.class)
                    .buildAndInitialize();
        } catch (ContainerInitializationException e) {
            throw new RuntimeException("Could not initialize Spring Boot application", e);
        }
    }

    public StreamLambdaHandler() {
        super(HANDLER);
    }
}
