package com.barelyconscious.worlds.game.hero.recipe;

import com.barelyconscious.worlds.game.item.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CraftingProducts {

    private final List<Item> items;
}
