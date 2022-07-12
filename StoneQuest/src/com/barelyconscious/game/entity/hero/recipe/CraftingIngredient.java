package com.barelyconscious.game.entity.hero.recipe;

import com.barelyconscious.game.entity.item.ResourceItemTag;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CraftingIngredient {

    private final ResourceItemTag resource;
    private final int amount;
}
