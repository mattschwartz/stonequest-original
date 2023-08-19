package com.barelyconscious.worlds.game.item.tags;

import com.barelyconscious.worlds.game.item.ItemTag;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResourceItemTag implements ItemTag {
    CLOTH("Cloth"),
    ORE("Ore"),
    METAL("Metal"),
    WOOD("Wood"),
    HERB("Herb"),
    LEATHER("Leather"),
    TECH("Tech");

    private final String tagName;
}
