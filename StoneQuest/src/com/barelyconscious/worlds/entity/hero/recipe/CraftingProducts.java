package com.barelyconscious.worlds.entity.hero.recipe;

import com.barelyconscious.worlds.entity.item.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CraftingProducts {

    private final List<Item> items;
}
