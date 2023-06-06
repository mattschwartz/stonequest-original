package com.barelyconscious.worlds.testgamedata;

import com.barelyconscious.worlds.data.dynamodb.model.RecipeItem;
import com.barelyconscious.worlds.module.DatabaseModule;
import com.google.inject.Guice;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

@Log4j2
public final class BuildTestDatabase {

    private BuildTestDatabase() {
    }

    public static void main(String[] args) {
        final String propertiesFilePath = "Worlds.properties";
        var injector = Guice.createInjector(new DatabaseModule(propertiesFilePath));
        var ddb = injector.getInstance(DynamoDbClient.class);
        createTableIfNotExists(ddb);

        var ddb2 = injector.getInstance(DynamoDbEnhancedClient.class);
        var table = ddb2.table(RecipeItem.TABLE_NAME, TableSchema.fromBean(RecipeItem.class));

        var newItem = new RecipeItem();
        newItem.setId("test");
        newItem.setType("test");

        table.putItem(newItem);

        log.info("yo dog i created item...");

        System.out.println("created item...");

        var existingItem = table.getItem(t -> t.key(Key.builder()
            .partitionValue("test")
            .sortValue("test")
            .build()));

        System.out.println("Found item: " + existingItem);


        createItems(ddb);
    }

    private static void createItems(DynamoDbClient ddb) {
    }

    private static void createTableIfNotExists(DynamoDbClient ddb) {
        try {
            DescribeTableResponse describeTableResponse = ddb.describeTable(DescribeTableRequest.builder()
                .tableName(RecipeItem.TABLE_NAME)
                .build());

            if (describeTableResponse.table().tableStatus() == TableStatus.ACTIVE) {
                System.out.println("Table already exists");
                return;
            }
        } catch (ResourceNotFoundException ex) {
        }

        System.out.println("Table does not exist... Creating");

        ddb.createTable(CreateTableRequest.builder()
            .tableName(RecipeItem.TABLE_NAME)
            .keySchema(
                KeySchemaElement.builder()
                    .attributeName(RecipeItem.SK_RECIPE_ID)
                    .keyType(KeyType.HASH).build(),
                KeySchemaElement.builder()
                    .attributeName(RecipeItem.PK_RECIPE_TYPE)
                    .keyType(KeyType.RANGE)
                    .build())
            .attributeDefinitions(
                AttributeDefinition.builder()
                    .attributeName(RecipeItem.SK_RECIPE_ID)
                    .attributeType("S")
                    .build(),
                AttributeDefinition.builder()
                    .attributeName(RecipeItem.PK_RECIPE_TYPE)
                    .attributeType("S")
                    .build())
            .billingMode(BillingMode.PAY_PER_REQUEST)
            .build());
    }

}
