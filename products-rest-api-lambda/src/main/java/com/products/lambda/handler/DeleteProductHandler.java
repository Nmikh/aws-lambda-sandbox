package com.products.lambda.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.products.lambda.repository.ProductRepository;
import org.apache.log4j.Logger;

import java.util.Map;

public class DeleteProductHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final Logger logger = Logger.getLogger(this.getClass());
    private final ProductRepository productRepository;

    public DeleteProductHandler() {
        productRepository = new ProductRepository();
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context context) {
        Map<String, String> pathParameters = apiGatewayProxyRequestEvent.getPathParameters();
        String id = pathParameters.get("id");

        productRepository.delete(id);

        APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent = new APIGatewayProxyResponseEvent();
        apiGatewayProxyResponseEvent.setStatusCode(200);

        return apiGatewayProxyResponseEvent;
    }
}
