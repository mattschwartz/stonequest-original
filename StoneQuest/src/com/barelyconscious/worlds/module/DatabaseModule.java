package com.barelyconscious.worlds.module;

import com.barelyconscious.worlds.common.exception.InvalidGameConfigurationException;
import com.barelyconscious.worlds.game.World;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import org.apache.commons.lang3.StringUtils;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.io.InputStream;
import java.net.URI;
import java.util.Objects;
import java.util.Properties;

import static com.google.common.base.Preconditions.checkArgument;

public class DatabaseModule extends AbstractModule {

    private final String propertiesFilepath;

    public DatabaseModule(final String propertiesFilepath) {
        checkArgument(StringUtils.isNotBlank(propertiesFilepath),
            "propertiesFile cannot be blank");
        this.propertiesFilepath = propertiesFilepath;
    }

    @Override
    protected void configure() {
        try {
            final Properties properties = new Properties();
            final InputStream inputStream = Objects.requireNonNull(this.getClass().getClassLoader()
                    .getResource(propertiesFilepath))
                .openStream();

            properties.load(inputStream);
            Names.bindProperties(binder(), properties);
        } catch (final Exception ex) {
            throw new InvalidGameConfigurationException(String.format(
                "Failed while trying to load properties from filepath='%s': %s",
                propertiesFilepath,
                ex.getMessage()));
        }
    }

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
