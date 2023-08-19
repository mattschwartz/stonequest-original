package com.barelyconscious.worlds.entity;

import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.game.item.Item;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * A crafting building which can be used to create items. Crafters
 * offer a selection of recipes that can be used to create items.
 */
public class CrafterBuilding extends Building {

    /**
     * The recipes provided by this crafter
     */
//    private final List<Recipe> knownRecipes;

    public CrafterBuilding(
        String name,
        Vector transform
    ) {
        super(name, transform);
    }

    public List<Item> craft(
        // final Recipe recipe,
        List<Item> ingredients
    ) {
        return Lists.newArrayList();
    }
}
