package com.products.lambda.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.products.lambda.model.Product;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductRepository {
    private static final String PRODUCTS_TABLE_NAME = System.getenv("PRODUCTS_TABLE_NAME");

    private final Logger logger = Logger.getLogger(this.getClass());

    private DynamoDBAdapter dbAdapter;

    private AmazonDynamoDB client;
    private DynamoDBMapper mapper;

    public ProductRepository() {
        DynamoDBMapperConfig mapperConfig = DynamoDBMapperConfig.builder()
                .withTableNameOverride(new DynamoDBMapperConfig.TableNameOverride(PRODUCTS_TABLE_NAME))
                .build();

        dbAdapter = DynamoDBAdapter.getInstance();
        client = dbAdapter.getDbClient();
        mapper = dbAdapter.createDbMapper(mapperConfig);
    }

    public List<Product> list() {
        DynamoDBScanExpression scanExp = new DynamoDBScanExpression();
        List<Product> results = mapper.scan(Product.class, scanExp);
        results.forEach(p -> logger.info("Products DB list: " + p.toString()));

        return results;
    }

    public void save(Product product) {
        logger.info("Products DB save: " + product.toString());
        this.mapper.save(product);
    }

    public Product get(String id) {
        Product product = null;

        Map<String, AttributeValue> av = new HashMap();
        av.put(":v1", new AttributeValue().withS(id));

        DynamoDBQueryExpression<Product> queryExp = new DynamoDBQueryExpression<Product>()
                .withKeyConditionExpression("id = :v1")
                .withExpressionAttributeValues(av);

        PaginatedQueryList<Product> result = this.mapper.query(Product.class, queryExp);
        if (result.size() > 0) {
            product = result.get(0);
            logger.info("Products DB get (OK):" + product.toString());
        } else {
            logger.info("Products DB get (NOT_FOUND)");
        }

        return product;
    }

    public Boolean delete(String id) {
        Product product = get(id);
        if (product == null) {
            logger.info("Products DB delete(NOT_FOUND)");
            return false;
        }

        logger.info("Products DB delete: " + product.toString());
        mapper.delete(product);

        return true;
    }
}
