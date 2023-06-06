package com.barelyconscious.worlds.data.dynamodb.model;

import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@Data
@DynamoDbBean
public class RecipeItem {
    public static final String TABLE_NAME = "recipes";
    public static final String PK_RECIPE_TYPE = "id";
    public static final String SK_RECIPE_ID = "type";

    public static final String RECIPE_TYPE = PK_RECIPE_TYPE;
    public static final String RECIPE_ID = SK_RECIPE_ID;
    public static final String RECIPE_NAME = "name";
    public static final String RECIPE_DESCRIPTION = "description";

    private String id;
    private String type;
    private String name;
    private String description;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    @DynamoDbSortKey
    public String getType() {
        return type;
    }
}
