package com.products.lambda.repository;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;

public class DynamoDBAdapter {

    private static DynamoDBAdapter dbAdapter;

    private final AmazonDynamoDB client;
    private DynamoDBMapper mapper;


    private DynamoDBAdapter() {
        client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.US_EAST_2)
                .build();
    }

    public static DynamoDBAdapter getInstance() {
        if (dbAdapter == null) {
            dbAdapter = new DynamoDBAdapter();
        }

        return dbAdapter;
    }

    public AmazonDynamoDB getDbClient() {
        return client;
    }

    public DynamoDBMapper createDbMapper(DynamoDBMapperConfig mapperConfig) {
        if (client != null)
            mapper = new DynamoDBMapper(client, mapperConfig);

        return mapper;
    }
}
