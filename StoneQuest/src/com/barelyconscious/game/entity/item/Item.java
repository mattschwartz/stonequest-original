package com.barelyconscious.game.entity.item;


import com.barelyconscious.game.entity.resources.WSprite;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Item {

    private final int itemId;
    private final int itemLevel;
    private final String name;
    private final String description;
    private final WSprite sprite;
    private final List<ItemRequirement> requirements;
}
