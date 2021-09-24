package com.products.lambda.handler;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.products.lambda.model.Product;
import com.products.lambda.repository.ProductRepository;
import org.apache.log4j.Logger;

public class CreateProductHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final Logger logger = Logger.getLogger(this.getClass());
    private final ProductRepository productRepository;

    public CreateProductHandler() {
        productRepository = new ProductRepository();
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context context) {
        String body = apiGatewayProxyRequestEvent.getBody();
        logger.info("input body: " + body);

        Product product = Product.getProduct(body);
        productRepository.save(product);

        APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent = new APIGatewayProxyResponseEvent();
        apiGatewayProxyResponseEvent.setStatusCode(200);

        return apiGatewayProxyResponseEvent;
    }
}
