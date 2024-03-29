package com.barelyconscious.worlds.game.item.tags;

import com.barelyconscious.worlds.game.item.ItemTag;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConsumableItemTag implements ItemTag {

    POTABLE("Potable"),
    EDIBLE("Edible"),
    ;

    private final String tagName;
}
