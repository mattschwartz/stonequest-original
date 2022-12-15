package com.barelyconscious.worlds.entity.hero.recipe;

import com.barelyconscious.worlds.entity.item.tags.CraftingToolItemTag;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Recipe {

    private final String name;
    private final String description;

    /**
     * Tools required of the recipe, such as Pestle & Mortar and Heat Source
     */
    private final List<CraftingToolItemTag> requiredTools;

    /**
     * The ingredients required of the Recipe, such as Willow Bark and Water
     */
    private final List<CraftingIngredient> ingredients;

    /**
     * The results of the recipe if player has the required tools and components.
     */
    private final CraftingProducts products;
}
