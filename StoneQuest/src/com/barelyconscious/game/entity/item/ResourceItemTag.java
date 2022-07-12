package com.barelyconscious.game.entity.item;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResourceItemTag implements ItemTag {
    CLOTH("Cloth"),
    ORE("Ore")
    ;

    private final String tagName;
}
