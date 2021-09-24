package com.products.lambda.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.products.lambda.model.Product;
import com.products.lambda.repository.ProductRepository;
import org.apache.log4j.Logger;

import java.util.List;

public class ListProductsHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final Logger logger = Logger.getLogger(this.getClass());
    private final ProductRepository productRepository;

    public ListProductsHandler() {
        productRepository = new ProductRepository();
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context context) {
        List<Product> productList = productRepository.list();
        String productsJson = Product.getProductsJson(productList);

        APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent = new APIGatewayProxyResponseEvent();
        apiGatewayProxyResponseEvent.setStatusCode(200);
        apiGatewayProxyResponseEvent.setBody(productsJson);

        return apiGatewayProxyResponseEvent;
    }
}
