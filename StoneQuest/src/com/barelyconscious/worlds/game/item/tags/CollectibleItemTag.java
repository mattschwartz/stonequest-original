package com.barelyconscious.worlds.game.item.tags;

import com.barelyconscious.worlds.game.item.ItemTag;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CollectibleItemTag implements ItemTag {

    COLLECTIBLE("Collectible"),
    ;

    private final String tagName;
}
