package com.barelyconscious.game.entity.item.tags;

import com.barelyconscious.game.entity.item.ItemTag;
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
