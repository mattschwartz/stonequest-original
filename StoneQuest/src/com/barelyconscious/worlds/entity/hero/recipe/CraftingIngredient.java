package com.barelyconscious.worlds.entity.hero.recipe;

import com.barelyconscious.worlds.entity.item.tags.ResourceItemTag;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CraftingIngredient {

    private final ResourceItemTag resource;
    private final int itemId;
    private final int amount;
}
