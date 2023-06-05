package com.barelyconscious.worlds.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;

public class DatabaseModule extends AbstractModule {

    @Provides
    @Singleton
    DynamoDbClient providesAmazonDynamoDB(
        @Named("dynamodb.useLocal") boolean useLocal,
        @Named("dynamodb.endpoint") String endpoint,
        @Named("dynamodb.region") String region
    ) {
        if (useLocal) {
            return DynamoDbClient.builder()
                .region(Region.of(region))
                .endpointOverride(
                    URI.create(endpoint)
                )
                .build();
        } else {
            return null;
        }
    }
}
