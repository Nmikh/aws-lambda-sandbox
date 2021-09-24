package com.products.lambda.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.products.lambda.model.Product;
import com.products.lambda.repository.ProductRepository;
import org.apache.log4j.Logger;

import java.util.Map;

public class GetProductHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final Logger logger = Logger.getLogger(this.getClass());
    private final ProductRepository productRepository;

    public GetProductHandler() {
        productRepository = new ProductRepository();
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context context) {
        APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent = new APIGatewayProxyResponseEvent();

        Map<String, String> pathParameters = apiGatewayProxyRequestEvent.getPathParameters();
        String id = pathParameters.get("id");

        Product product = productRepository.get(id);
        if (product == null) {
            apiGatewayProxyResponseEvent.setStatusCode(404);
        } else {
            apiGatewayProxyResponseEvent.setStatusCode(200);
            apiGatewayProxyResponseEvent.setBody(product.toString());
        }

        return apiGatewayProxyResponseEvent;
    }
}
