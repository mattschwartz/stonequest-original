package com.barelyconscious.worlds.data.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;

@Data
@DynamoDBTable(tableName = RecipeItem.TABLE_NAME)
public class RecipeItem {
    public static final String TABLE_NAME = "recipes";
    public static final String HK_RECIPE_ID = "type";
    public static final String RK_RECIPE_TYPE = "id";

    public static final String RECIPE_TYPE = RK_RECIPE_TYPE;
    public static final String RECIPE_ID = HK_RECIPE_ID;
    public static final String RECIPE_NAME = "name";
    public static final String RECIPE_DESCRIPTION = "description";

    @DynamoDBHashKey(attributeName = HK_RECIPE_ID)
    private String id;

    @DynamoDBRangeKey(attributeName = RK_RECIPE_TYPE)
    private String recipeType;

    private String name;
    private String description;
}
