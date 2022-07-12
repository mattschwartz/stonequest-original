package com.barelyconscious.game.entity.hero.recipe;

import com.barelyconscious.game.entity.item.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CraftingProducts {

    private final List<Item> items;
}
