package com.barelyconscious.game.entity.item;

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
